package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreen.adapter.HairAdapter;
import com.example.splashscreen.utils.PrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AntrianBarberActivity2 extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.btn1)
    Button btn1;
    private boolean doubleBack;
    private Toast backToast;

    PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antrian_barber2);
        ButterKnife.bind(this);

        HairAdapter hairAdapter = new HairAdapter(this);
        recycler.setAdapter(hairAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HairCutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBack) {
            backToast.cancel();
            super.onBackPressed();
            moveTaskToBack(true);
        } else {
            backToast = Toast.makeText(this, "Press back againt to exit ", Toast.LENGTH_SHORT);
            backToast.show();
            doubleBack = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBack = false;
                }
            }, 2000);
        }
    }
}