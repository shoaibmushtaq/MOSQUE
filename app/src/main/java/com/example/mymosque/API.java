package com.example.mymosque;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {


    @GET("Duas?")
    Call<DuaArrayList> getduas(@Query("page=") int page);
    @GET("Duas")
    Call<String> getlinks();
}
