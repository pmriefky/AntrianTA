package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplasgActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splasg2);
        Thread thread = new Thread() {
            public void run() {
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(SplasgActivity2.this, MainActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}