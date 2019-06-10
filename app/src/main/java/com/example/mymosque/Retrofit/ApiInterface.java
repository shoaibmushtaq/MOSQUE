package com.example.mymosque.Retrofit;


import com.example.mymosque.Models.AskImam;
import com.example.mymosque.Models.MasjidModel;
import com.example.mymosque.Models.Message;
import com.example.mymosque.Models.NotificationModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    //declaring abstract method to get list from server , here the model is an arraylist
    @GET("getMosquesList/147")
    Call<MasjidArrayList> getMosqueList(@Query("page") int page);

    @FormUrlEncoded
    @POST("requestmosque")
    Call<String> addmosque(
            @Field("pname")  String mosqueName,
            @Field("mname")  String userName,
            @Field("contact")  String userContact
    );

    @FormUrlEncoded
    @POST("feedback/{u_id}")
    Call<Message> Feedback(
            @Path("u_id") int id,
            @Field("msg")  String message,
            @Field("contact")  String contact,
            @Field("name")  String name
    );

    @FormUrlEncoded
    @POST("Question")
    Call<ArrayList<AskImam>> AskImam(
            @Field("m_id") int  m_id,
            @Field("u_id") int  u_id,
            @Field("question") String  question
    );

    @GET("ask/{m_id}/{u_id}")
    Call<ArrayList<AskImam>> imamAnswers(
            @Path("m_id") int  m_id,
            @Path("u_id") int  u_id
    );

    @GET("farvoriate/{u_id}")
    Call<ArrayList<MasjidModel>> getFavoriteList(
            @Path("u_id") int userid);

    @GET("getNotification/{u_id}")
    Call<ArrayList<NotificationModel>> getNotifications(
            @Path("u_id") int userid);









}
