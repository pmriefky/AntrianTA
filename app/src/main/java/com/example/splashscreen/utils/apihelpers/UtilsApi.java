package com.example.splashscreen.utils.apihelpers;

public class UtilsApi {
    public static final String BASE_URL = "http://192.168.43.65/db_antrian/";



    public static ApiInterface getApiService(){
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
