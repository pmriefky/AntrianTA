package com.example.splashscreen.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

import com.example.splashscreen.R;

public class LoadingDialog {
    private Context context;
    private AlertDialog alertDialog;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setView(LayoutInflater.from(context).inflate(R.layout.loading_dialog, null));

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissLoadingDialog(){
        alertDialog.dismiss();
    }
}
