package com.example.mymosque.Retrofit;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    //declaring abstract method to get list from server , here the model is an arraylist
    @GET("getMosquesList/147")
    Call<MasjidArrayList> getMosqueList(@Query("page") int page);


}
