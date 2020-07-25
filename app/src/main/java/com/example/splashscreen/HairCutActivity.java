package com.example.splashscreen;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreen.adapter.HairCutAdapter;
import com.example.splashscreen.model.HairCut;
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

public class HairCutActivity extends AppCompatActivity {


    @BindView(R.id.toolbarHair)
    Toolbar toolbarHair;

    PrefManager prefManager;
    ApiInterface apiInterface;

    List<HairCut.DataBean> dataBeans;
    @BindView(R.id.recyclerHairCut)
    RecyclerView recyclerHairCut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_cut);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarHair);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefManager = new PrefManager(this);
        apiInterface = UtilsApi.getApiService();
        fetchData();
    }

    private void fetchData() {
        apiInterface.getHairCut().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            dataBeans = new ArrayList<>();
                            Gson gson = new Gson();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                HairCut.DataBean dataBean = gson.fromJson(jsonArray.get(i).toString(), HairCut.DataBean.class);
                                dataBeans.add(dataBean);
                            }

                            HairCutAdapter hairCutAdapter = new HairCutAdapter(getApplicationContext(), dataBeans);
                            recyclerHairCut.setAdapter(hairCutAdapter);
                            recyclerHairCut.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        }else{
                            Toast.makeText(HairCutActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(HairCutActivity.this, "Connection Problem", Toast.LENGTH_SHORT).show();
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