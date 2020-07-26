package com.example.splashscreen;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreen.adapter.BookingListAdapter;
import com.example.splashscreen.model.BookingList;
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

    ApiInterface apiInterface;
    PrefManager prefManager;
    Context context;
    
    List<BookingList.DataBean> dataBeans;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarBooking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(this);
        context = this;

        fetchDataBooking();
        fetchDataListBooking();
    }

    private void fetchDataBooking() {
        apiInterface.getBookingUser(prefManager.getEmailr(),prefManager.getTokenUser()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")){
                            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                            bookingDate.setText(jsonObject1.getString("date"));
                            bookingService.setText(jsonObject1.getString("nama_service"));
                            bookingPrice.setText(jsonObject1.getString("harga"));
                            
                            
                        }else{
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
            }
        });
    }

    private void fetchDataListBooking() {
        apiInterface.getListBooking().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")){
                            dataBeans = new ArrayList<>();
                            Gson gson = new Gson();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0 ; i < jsonArray.length() ; i++){
                                BookingList.DataBean dataBean = gson.fromJson(jsonArray.get(i).toString(), BookingList.DataBean.class);
                                dataBeans.add(dataBean);
                            }

                            BookingListAdapter bookingListAdapter = new BookingListAdapter(context, dataBeans);
                            recyclerListBooking.setAdapter(bookingListAdapter);
                            recyclerListBooking.setLayoutManager(new LinearLayoutManager(context));
                        }else{
                            Toast.makeText(context, ""+ jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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