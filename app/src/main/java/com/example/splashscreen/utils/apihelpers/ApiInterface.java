package com.example.splashscreen.utils.apihelpers;

import android.telecom.CallScreeningService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @FormUrlEncoded
    @POST("getidservice.php")
    Call<ResponseBody> getServiceID (@Field("kode") String kode);

    @FormUrlEncoded
    @POST("addbooking.php")
    Call<ResponseBody> bookingApi (@Field("kode_service") String kode_service,
                                   @Field("email") String email,
                                   @Field("token") String token,
                                   @Field("date") String date);

    @FormUrlEncoded
    @POST("getbookinguser.php")
    Call<ResponseBody> getBookingUser(@Field("email") String email,
                                      @Field("token") String token);

    @GET("getAllBookingDetail.php")
    Call<ResponseBody> getListBooking();

    @GET("profil.php")
    Call<ResponseBody> getProfil(@Query("token") String token);

    @FormUrlEncoded
    @POST("updatepass.php")
    Call<ResponseBody> getUpdatePass(@Field("email") String email,
                                     @Field("password_new") String password,
                                     @Field("token") String token);

}
