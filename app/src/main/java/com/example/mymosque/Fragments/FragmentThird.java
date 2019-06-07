package com.example.mymosque.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymosque.API;
import com.example.mymosque.Adapter.AdapterRVJamaatTimes;
import com.example.mymosque.Adapter.AdapterRVTimes;
import com.example.mymosque.JamaatTimes;
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

public class FragmentThird extends Fragment {

    private ArrayList<JamaatTimes> JamaatTimesArrayList = new ArrayList<JamaatTimes>();
    View v;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_third, container, false);
        v.findViewById(R.id.thirdfrag);
String date=get_curr_date();
        fetch_json("http://masjidi.co.uk/api/",38,"30-05-2019");
        recyclerView=(RecyclerView) v.findViewById(R.id.RV_jamaat_times);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        return v;
    }


    public String get_curr_date(){
        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        Log.d("date",formattedDate);


        return formattedDate;
    }
    public void fetch_json(String url,int userid,String date)
    {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        //pass interface refverence to retrofit and it will generate class automatically
        API apiInterface = retrofit.create(API.class);
//making the call object
        Call<List<JamaatTimes>> call = apiInterface.getjamaatTimes(userid,date);
        //calling enqueue method
        call.enqueue(new Callback<List<JamaatTimes>>() {
            @Override
            public void onResponse(Call<List<JamaatTimes>> call, Response<List<JamaatTimes>> response) {

                JamaatTimesArrayList=(ArrayList<JamaatTimes>) response.body();
                AdapterRVJamaatTimes adapterRVJamaatTimes=new AdapterRVJamaatTimes(getContext(),JamaatTimesArrayList);
                recyclerView.setAdapter(adapterRVJamaatTimes);




            }




            @Override
            public void onFailure(Call<List<JamaatTimes>> call, Throwable t) {
                Log.d("fasilure",t.getCause().toString());
            }
        });





}}
