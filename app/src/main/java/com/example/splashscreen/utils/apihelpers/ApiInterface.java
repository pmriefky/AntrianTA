package com.example.splashscreen.utils.apihelpers;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> loginApi(@Field("email") String email,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> registerApi(@Field("nama") String nama,
                                   @Field("username") String username,
                                   @Field("no_hp") String no_hp,
                                   @Field("email") String email,
                                   @Field("password") String password);

    @GET("getallservice.php")
    Call<ResponseBody> getHairCut();
}
