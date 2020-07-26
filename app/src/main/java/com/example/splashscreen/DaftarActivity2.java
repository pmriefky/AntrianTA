package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splashscreen.utils.apihelpers.ApiInterface;
import com.example.splashscreen.utils.apihelpers.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarActivity2 extends AppCompatActivity {

    @BindView(R.id.nama)
    EditText nama;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.notelp)
    EditText notelp;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password2)
    EditText password2;
    @BindView(R.id.tombolmasuk)
    Button tombolmasuk;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar2);
        ButterKnife.bind(this);
        apiInterface = UtilsApi.getApiService();
        tombolmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(nama.getText().toString())) {
                    nama.setError("Field Cannot Empty");
                } else if (TextUtils.isEmpty(username.getText().toString())) {
                    username.setError("Field Cannot Empty");
                } else if (TextUtils.isEmpty(notelp.getText().toString())) {
                    notelp.setError("Field Cannot Empty");
                } else if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("Field Cannot Empty");
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Field Cannot Empty");
                } else if (TextUtils.isEmpty(password2.getText().toString())) {
                    password2.setError("Field Cannot Empty");
                } else if (!password.getText().toString().equals(password2.getText().toString())) {
                    Toast.makeText(DaftarActivity2.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                } else {
                    FetchRegister();
                }
            }
        });

    }

    private void FetchRegister() {
        apiInterface.registerApi(nama.getText().toString(),
                username.getText().toString(),
                notelp.getText().toString(),
                email.getText().toString(),
                password.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")) {
                            Toast.makeText(DaftarActivity2.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(DaftarActivity2.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DaftarActivity2.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}