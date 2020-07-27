package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.splashscreen.adapter.HairAdapter;
import com.example.splashscreen.utils.PrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragmen extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btnCekBooking)
    CardView btnCekBooking;
    @BindView(R.id.btnLogout)
    Button btnLogout;


    PrefManager prefManager;

    public HomeFragmen(){

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup group,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_home, group, false);
        ButterKnife.bind(this, view);
        prefManager = new PrefManager(view.getContext());

        HairAdapter hairAdapter = new HairAdapter(view.getContext());
        recycler.setAdapter(hairAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

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
}