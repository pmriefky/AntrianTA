package com.example.splashscreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.splashscreen.adapter.HairAdapter;
import com.example.splashscreen.adapter.ModelRambutAdapter;
import com.example.splashscreen.adapter.ViewPagerAdapter;
import com.example.splashscreen.model.HairCut;
import com.example.splashscreen.model.ModelRambut;
import com.example.splashscreen.model.Promo;
import com.example.splashscreen.utils.AppReceiver;
import com.example.splashscreen.utils.PrefManager;
import com.example.splashscreen.utils.apihelpers.ApiInterface;
import com.example.splashscreen.utils.apihelpers.UtilsApi;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmen extends Fragment {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btnCekBooking)
    CardView btnCekBooking;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.linearHair)
    LinearLayout linearHair;

    PrefManager prefManager;
    ApiInterface apiInterface;
    Context context;

    ArrayList<Promo.DataBean> promoBeans;
    List<HairCut.DataBean> dataBeans;

    ViewPagerAdapter viewPagerAdapter;

    List<ModelRambut.DataBean> dataBeans1;
    @BindView(R.id.recycler1)
    RecyclerView recycler1;


    public HomeFragmen() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_home, group, false);
        ButterKnife.bind(this, view);
        viewPager.requestFocus();
        prefManager = new PrefManager(view.getContext());
        apiInterface = UtilsApi.getApiService();
        context = view.getContext();
        fetchBanner();
        fetchData();
        fetchDataModelRambut();

        /*HairAdapter hairAdapter = new HairAdapter(view.getContext());
        recycler.setAdapter(hairAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));*/

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HairCutActivity.class);
                startActivity(intent);
            }
        });

        btnCekBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BookingActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.removeSession();
                prefManager.spString(PrefManager.SP_ID, "");
                prefManager.spString(PrefManager.SP_TOKEN_USER, "");

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    private void fetchBanner() {
        apiInterface.getPromo().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            promoBeans = new ArrayList<>();
                            Gson gson = new Gson();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Promo.DataBean dataBean = gson.fromJson(jsonArray.get(i).toString(), Promo.DataBean.class);
                                promoBeans.add(dataBean);
                            }

                            viewPagerAdapter = new ViewPagerAdapter(context, promoBeans);

                            viewPager.setAdapter(viewPagerAdapter);
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

            }
        });
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

                            HairAdapter hairAdapter = new HairAdapter(context, dataBeans);
                            recycler.setAdapter(hairAdapter);
                            recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));


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

            }
        });
    }

    private void fetchDataModelRambut() {
        apiInterface.getAllModelRambut().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            dataBeans1 = new ArrayList<>();
                            Gson gson = new Gson();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                ModelRambut.DataBean dataBean = gson.fromJson(jsonArray.get(i).toString(), ModelRambut.DataBean.class);
                                dataBeans1.add(dataBean);
                            }
                            ModelRambutAdapter modelRambutAdapter = new ModelRambutAdapter(context , dataBeans1);
                            recycler1.setAdapter(modelRambutAdapter);
                            recycler1.setLayoutManager(new LinearLayoutManager(context));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                        /*JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Toast.makeText(context, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            dataBeans1 = new ArrayList<>();
                            Gson gson = new Gson();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                ModelRambut.DataBean dataBean = gson.fromJson(jsonArray.get(i).toString(), ModelRambut.DataBean.class);
                                dataBeans1.add(dataBean);
                            }
                            ModelRambutAdapter modelRambutAdapter = new ModelRambutAdapter(context , dataBeans1);
                            recycler1.setAdapter(modelRambutAdapter);
                            recycler1.setLayoutManager(new LinearLayoutManager(context));
                        }*/
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}