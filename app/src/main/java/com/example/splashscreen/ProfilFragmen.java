package com.example.splashscreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.example.splashscreen.model.Profil;
import com.example.splashscreen.utils.LoadingDialog;
import com.example.splashscreen.utils.PrefManager;
import com.example.splashscreen.utils.apihelpers.ApiInterface;
import com.example.splashscreen.utils.apihelpers.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class ProfilFragmen extends Fragment {

    Uri mImageUri;
    String imagePath;
    @BindView(R.id.username)
    TextView username;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.no_telp)
    TextView no_telp;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.btnprofil)
    Button btnprofil;

    private static final int PICK_IMAGE_REQUEST = 1;

    LoadingDialog loadingDialog;
    ApiInterface apiInterface;
    PrefManager prefManager;
    ArrayList<Profil.DataBean> arrayList;
    Context context;
    @BindView(R.id.foto)
    CircleImageView foto;

    public ProfilFragmen() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_profil, container, false);
        ButterKnife.bind(this, view);
        apiInterface = UtilsApi.getApiService();
        context = view.getContext();
        prefManager = new PrefManager(context);
        loadingDialog = new LoadingDialog(context);
        profil();
        btnprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatepass();
            }
        });
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        return view;
    }

    private void openFileChooser() {
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (options[i].equals("Choose from Gallery")) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST);

                } else if (options[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    private void updatepass() {
        loadingDialog.startLoadingDialog();
        apiInterface.getUpdatePass(prefManager.getEmailr(), password.getText().toString(), prefManager.getTokenUser()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        loadingDialog.dismissLoadingDialog();
                        if (jsonObject.getString("status").equals("200")) {
                            Toast.makeText(context, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, "" + object.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loadingDialog.dismissLoadingDialog();
            }
        });
    }

    private void profil() {
        apiInterface.getProfil(prefManager.getTokenUser()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equals("200")) {
                            JSONArray array = object.getJSONArray("data");
                            username.setText(array.getJSONObject(0).getString("username"));
                            email.setText(array.getJSONObject(0).getString("email"));
                            no_telp.setText(array.getJSONObject(0).getString("no_hp"));

                            Glide
                                    .with(context)
                                    .load(UtilsApi.BASE_URL+array.getJSONObject(0).getString("image"))
                                    .into(foto);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, "" + object.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            mImageUri = data.getData();

            imagePath = getRealPathFromUri(mImageUri);

            updateImage();
        }
    }

    private void updateImage() {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("email", createPartFromString(prefManager.getEmailr() + ""));
        bodyMap.put("token", createPartFromString(prefManager.getTokenUser() + ""));

        File file = new File(imagePath);
        RequestBody propertyImage = RequestBody.create(MediaType.parse("multipart/from-data"), file);
        MultipartBody.Part propertyImagePart = MultipartBody.Part.createFormData("file", file.getName(),propertyImage);

        apiInterface.changeProfileImage(bodyMap, propertyImagePart).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Toast.makeText(context, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        getActivity().startActivity(getActivity().getIntent());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_idx);
        cursor.close();
        return result;
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse("text/plain"), descriptionString);
    }
}


