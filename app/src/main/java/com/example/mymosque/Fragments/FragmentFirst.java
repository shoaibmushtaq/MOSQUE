package com.example.mymosque.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymosque.API;
import com.example.mymosque.BegningTimes;
import com.example.mymosque.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentFirst extends Fragment {

    View v;
    TextView sehritime,fajarstarttime,fajarJamaatTime,sunriseTime,ZuhurStartTime,ZuhurJamaatTime,AsrStartTime,AsrJamaatTime,MaghribStartTime,MaghribJammatTime,IShaStartTime,IshaJammatTime;

ArrayList<BegningTimes> begningTimesArrayList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);










    }//end of onCreate method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_first, container, false);

begningTimesArrayList=new ArrayList<>();
        //String date=get_curr_date();
        sehritime=(TextView) v.findViewById(R.id.sehritime);
        fajarstarttime=(TextView) v.findViewById(R.id.txt_fajr_start_time);
        fajarJamaatTime=(TextView) v.findViewById(R.id.txt_fajr_jamaat_time);
        sunriseTime=(TextView) v.findViewById(R.id.txt_sunrise_time);
        ZuhurStartTime=(TextView) v.findViewById(R.id.txt_zuhr_start_time);
        ZuhurJamaatTime=(TextView) v.findViewById(R.id.txt_zuhr_jamaat_time);
        AsrStartTime=(TextView) v.findViewById(R.id.txt_asr_start_time);
        AsrJamaatTime=(TextView) v.findViewById(R.id.txt_asr_jamaat_time);
        MaghribStartTime=(TextView) v.findViewById(R.id.txt_maghrib_start_time);
        MaghribJammatTime=(TextView) v.findViewById(R.id.txt_maghrib_jamaat_time);
        IShaStartTime=(TextView) v.findViewById(R.id.txt_isha_start_time);
        IshaJammatTime=(TextView) v.findViewById(R.id.txt_isha_jamaat_time);

fetch_json("http://masjidi.co.uk/api/",38,"30-5-2019");









        return v;
    }//End onCreateView Method
    public String get_curr_date(){
        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        Log.d("date",formattedDate);


        return formattedDate;
    }

    public void fetch_json(String url,int userid,String date) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        //pass interface refverence to retrofit and it will generate class automatically
       API apiInterface = retrofit.create(API.class);
//making the call object
        Call<List<BegningTimes>> call = apiInterface.getbeginingtimes(userid,date);
        //calling enqueue method
        call.enqueue(new Callback<List<BegningTimes>>() {
            @Override
            public void onResponse(Call<List<BegningTimes>> call, Response<List<BegningTimes>> response) {
                //sehritime.setText(response.body().get(0).getSehri_ends());
                fajarstarttime.setText(response.body().get(0).getFajr());
                fajarJamaatTime.setText(response.body().get(0).getJamaat1());
                ZuhurStartTime.setText(response.body().get(0).getZhuhr());
                ZuhurJamaatTime.setText(response.body().get(0).getJamaat2());
                AsrStartTime.setText(response.body().get(0).getAsr());
                AsrJamaatTime.setText(response.body().get(0).getJamaat3());
                MaghribStartTime.setText(response.body().get(0).getMaghrib());
                MaghribJammatTime.setText(response.body().get(0).getJamaat4());
                IShaStartTime.setText(response.body().get(0).getIsha());
                IshaJammatTime.setText(response.body().get(0).getJamaat5());


            }




            @Override
            public void onFailure(Call<List<BegningTimes>> call, Throwable t) {

                Log.d("failure",t.getCause().toString());
            }
        });
    }


    }
