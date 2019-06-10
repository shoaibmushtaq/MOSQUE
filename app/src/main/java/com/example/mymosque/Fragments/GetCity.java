package com.example.mymosque.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mymosque.API;
import com.example.mymosque.Adapter.AdapterCity;
import com.example.mymosque.City;
import com.example.mymosque.MonthlyTime;
import com.example.mymosque.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GetCity extends Fragment {
ArrayList<City> cities;
RecyclerView recyclerView;
AdapterCity adapterCity;
LinearLayoutManager manager;
View v;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_city, container, false);
        recyclerView=v.findViewById(R.id.PostalCode_RV);
        manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        fetchcities("http://masjidi.co.uk/api/");
        return v;
    }
public void fetchcities(String url){
    Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
    API apiInterface = retrofit.create(API.class);
//making the call object
    Call<List<City>> call = apiInterface.getCitiesList();
    call.enqueue(new Callback<List<City>>() {
        @Override
        public void onResponse(Call<List<City>> call, Response<List<City>> response) {
            cities=(ArrayList<City>) response.body();
            adapterCity=new AdapterCity(getContext(),cities);
            recyclerView.setAdapter(adapterCity);

        }

        @Override
        public void onFailure(Call<List<City>> call, Throwable t) {
            Toast.makeText(getContext(),"unable to fetch"+t.getCause(),Toast.LENGTH_LONG).show();
        }
    });


}
}