package com.example.mymosque.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    //Declaring Base Url for retrofit and creating retrofit insttance
    public static final String BASE_URL="http://masjidi.co.uk/api/";
    public static Retrofit retrofit = null;


    //this method will return the instance of retrofit
    public static Retrofit getApiClient(){

        if (retrofit == null){

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }




}
