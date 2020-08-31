package com.example.splashscreen.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mcontext;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "PrefManager";
    private static final String SESSION_KEY = "SESSION_USER";

    public static final String SP_ID = "spID";
    public static final String SP_TOKEN_USER = "spTokenUser";

    public static final String ALARM = "alarm";



    public PrefManager(Context context){
        this.mcontext = context;
        pref = mcontext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //SESSION LOGIN PREF
    public void saveSession(){
        editor.putBoolean(SESSION_KEY, true);
        editor.commit();
    }

    public boolean getSession(){
        return pref.getBoolean(SESSION_KEY, false);
    }

    public void removeSession(){
        editor.putBoolean(SESSION_KEY, false);
        editor.commit();
    }

    public void spInt(String key, int value){
        editor.putInt(key, value);
        editor.commit();
    }

    public void spString(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public void setAlarm(String key, String tanggal){
        editor.putString(key, tanggal);
        editor.commit();
    }
    public String getAlarm(){
        return  pref.getString(ALARM, "");
    }

    public int getId(){
        return pref.getInt(SP_ID, 0);
    }

    public String getTokenUser(){
        return pref.getString(SP_TOKEN_USER, "");
    }

    public String getEmailr(){
        return pref.getString(SP_ID, "");
    }

}
