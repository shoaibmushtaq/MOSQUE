package com.example.mymosque.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mymosque.API;
import com.example.mymosque.Adapter.AdapterNearestummah;
import com.example.mymosque.MonthlyTime;
import com.example.mymosque.NearestJummah;
import com.example.mymosque.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class ResultNearestJummah extends Fragment {
    TextView TextViewCouldNot;
    RelativeLayout NotFoundLayout;
    String PostalCode,City;
    View v;
    ArrayList<NearestJummah> nearestJummahs;
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
    v=inflater.inflate(R.layout.fragment_favorite, container, false);
        NotFoundLayout=v.findViewById(R.id.NotFoundLayout_);
        TextViewCouldNot=v.findViewById(R.id.textCouldnot_);
        SharedPreferences MosqueData = getActivity().getSharedPreferences("PassNearestMosqueData", Context.MODE_PRIVATE);
        PostalCode=MosqueData.getString("PostalCode","");
        City=MosqueData.getString("City","");
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        ImageView backbutton = (ImageView) toolbar.findViewById(R.id.backButton);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Result Nearest Jummah");
        nearestJummahs=new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.RV_FavoriteList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        return v;
    }
public void fetch(String url,int userid,String city,int postcode){
    Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
    //pass interface refverence to retrofit and it will generate class automatically
    API apiInterface = retrofit.create(API.class);
    Call<List<NearestJummah>> call;
    if(PostalCode.equals("")){
        call = apiInterface.getJummafromCity(userid, city);

    }else if(City.equals("")){
         call = apiInterface.getJummafromPostalCode(userid,postcode);

    }else {
        call = apiInterface.getJummafromboth(userid, city,postcode);
    }
call.enqueue(new Callback<List<NearestJummah>>() {

    @Override
    public void onResponse(Call<List<NearestJummah>> call, Response<List<NearestJummah>> response) {
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("PostalCodePass", MODE_PRIVATE);
        preferences.edit().clear().apply();
        SharedPreferences preferencesss = getActivity().getSharedPreferences("CityPass", MODE_PRIVATE);
        preferencesss.edit().clear().apply();
        SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("PassNearestMosqueData", MODE_PRIVATE).edit();
        editor.putString("PostalCode","");
        editor.putString("City","");
        editor.apply();
        nearestJummahs=(ArrayList<NearestJummah>) response.body();
        AdapterNearestummah nearestummah=new AdapterNearestummah(getContext(),nearestJummahs);
        recyclerView.setAdapter(nearestummah);

    }

    @Override
    public void onFailure(Call<List<NearestJummah>> call, Throwable t) {

    }
});

}

}
