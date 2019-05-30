package com.example.mymosque;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {


    @GET("Duas?")
    Call<DuaArrayList> getduas(@Query("page=") int page);
    @GET("Duas")
    Call<String> getlinks();
    @GET("getMontlyPrayerTime/{userid}/{curr_date}/D")
    Call<List<BegningTimes>> getbeginingtimes(@Path("userid") int userid,@Path("curr_date") String curr_date);
    @GET("getMontlyPrayerTime/{userid}/{curr_date}/B")
    Call<List<MonthlyTime>> getmonthlytime(@Path("userid") int userid,@Path("curr_date") String curr_date);
    @GET("getMontlyPrayerTime/{userid}/{curr_date}/J")
    Call<List<JamaatTimes>> getjamaatTimes(@Path("userid") int userid,@Path("curr_date") String curr_date);
}
