package com.example.splashscreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AntrianBarberActivity2 extends AppCompatActivity {
    private int STORAGE_PERMISSION_CODE = 1;

    private boolean doubleBack;
    private Toast backToast;

    @BindView(R.id.botomNavBar)
    BottomNavigationView bottombar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antrian_barber2);
        ButterKnife.bind(this);
        changeFragment(new HomeFragmen(), HomeFragmen.class
                .getSimpleName());
        navbarr();

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestStoragePermission();
        }

    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(AntrianBarberActivity2.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    private void navbarr() {
        Intent intent = getIntent();
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.home:
                        changeFragment(new HomeFragmen(), HomeFragmen.class
                        .getSimpleName());
                        break;
                    case R.id.account:
                        changeFragment(new ProfilFragmen(), ProfilFragmen.class
                        .getSimpleName());
                        break;
                    default:
                        changeFragment(new HomeFragmen(), HomeFragmen.class
                                .getSimpleName());
                }
                return true;
            }
        });
    }

    public void changeFragment(Fragment fragment, String tag){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment current = manager.getPrimaryNavigationFragment();
        if (current != null){
            transaction.hide(current);
        }

        Fragment temp = manager.findFragmentByTag(tag);
        if (temp == null){
            temp = fragment;
            transaction.add(R.id.container, temp, tag);
        }else {
            transaction.show(temp);
        }

        transaction.setPrimaryNavigationFragment(temp);
        transaction.setReorderingAllowed(true);
        transaction.commitAllowingStateLoss();
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