package com.example.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreen.adapter.BookingListAdapter;
import com.example.splashscreen.model.BookingList;
import com.example.splashscreen.utils.LoadingDialog;
import com.example.splashscreen.utils.PrefManager;
import com.example.splashscreen.utils.apihelpers.ApiInterface;
import com.example.splashscreen.utils.apihelpers.UtilsApi;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {

    @BindView(R.id.toolbarBooking)
    Toolbar toolbarBooking;
    @BindView(R.id.bookingDate)
    TextView bookingDate;
    @BindView(R.id.bookingService)
    TextView bookingService;
    @BindView(R.id.bookingPrice)
    TextView bookingPrice;
    @BindView(R.id.recyclerListBooking)
    RecyclerView recyclerListBooking;


    LoadingDialog loadingDialog;
    ApiInterface apiInterface;
    PrefManager prefManager;
    Context context;

    List<BookingList.DataBean> dataBeans;
    @BindView(R.id.btnCancel1)
    Button btnCancel1;
    @BindView(R.id.btnFimish)
    Button btnFimish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarBooking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingDialog = new LoadingDialog(this);

        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(this);
        context = this;

        fetchDataBooking();
        fetchDataListBooking();
    }

    private void fetchDataBooking() {
        loadingDialog.startLoadingDialog();
        apiInterface.getBookingUser(prefManager.getEmailr(), prefManager.getTokenUser()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        loadingDialog.dismissLoadingDialog();
                        if (jsonObject.getString("status").equals("200")) {
                            final JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                            bookingDate.setText(jsonObject1.getString("date"));
                            bookingService.setText(jsonObject1.getString("nama_service"));
                            bookingPrice.setText(jsonObject1.getString("harga"));
                            btnCancel1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        cancelorder(jsonObject1.getString("id_booking"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            btnFimish.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        cancelorder(jsonObject1.getString("id_booking"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        } else {
                            Toast.makeText(BookingActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(BookingActivity.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissLoadingDialog();
            }
        });
    }

    private void cancelorder(String id) {
        loadingDialog.startLoadingDialog();
        apiInterface.getCancelOrder(id, prefManager.getEmailr(), prefManager.getTokenUser()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        loadingDialog.dismissLoadingDialog();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")) {
                            Toast.makeText(context, ""+ jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AntrianBarberActivity2.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(BookingActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                loadingDialog.dismissLoadingDialog();
            }
        });
    }


    private void fetchDataListBooking() {
        loadingDialog.startLoadingDialog();
        apiInterface.getListBooking().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        loadingDialog.dismissLoadingDialog();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")) {
                            dataBeans = new ArrayList<>();
                            Gson gson = new Gson();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                BookingList.DataBean dataBean = gson.fromJson(jsonArray.get(i).toString(), BookingList.DataBean.class);
                                dataBeans.add(dataBean);
                            }

                            BookingListAdapter bookingListAdapter = new BookingListAdapter(context, dataBeans);
                            recyclerListBooking.setAdapter(bookingListAdapter);
                            recyclerListBooking.setLayoutManager(new LinearLayoutManager(context));
                        } else {
                            Toast.makeText(context, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Connection Problem", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissLoadingDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}