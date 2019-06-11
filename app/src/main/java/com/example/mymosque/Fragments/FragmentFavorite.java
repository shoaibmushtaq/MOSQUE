package com.example.mymosque.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.mymosque.AdapterRVFavorite;
import com.example.mymosque.Models.LinksModel;
import com.example.mymosque.Models.MasjidModel;
import com.example.mymosque.Models.MetaModel;
import com.example.mymosque.R;
import com.example.mymosque.RV_FindMasajid;
import com.example.mymosque.Retrofit.ApiClient;
import com.example.mymosque.Retrofit.ApiInterface;
import com.example.mymosque.Retrofit.MasjidArrayList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFavorite extends Fragment {

    private View favouriteView;

    private ArrayList<String> MasajidName = new ArrayList<>();
    //Declaring varriables
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdapterRVFavorite adapter;
    private ApiInterface apiInterface;
    private MasjidArrayList masjidArrayList;
    private ArrayList<MasjidModel> mosqueDataList;
    private int userId;
    private SharedPreferences userPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        favouriteView = inflater.inflate(R.layout.fragment_favorite, container, false);

        //intializing shared preference to store user details and set user id to 0 from shared preference
        userPreferences = getActivity().getSharedPreferences("USER_PREFERENCE", Context.MODE_PRIVATE);
        userId = userPreferences.getInt("ID", 0);

        initComponents();

        fetchFavourites();




        return favouriteView;
    }//End onCreateView Method


    //initializing components
    private void initComponents() {

        //<For Toolbar>
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        ImageView backbutton = (ImageView) toolbar.findViewById(R.id.backButton);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Favorite");
        //</For Toolbar>

         recyclerView = (RecyclerView) favouriteView.findViewById(R.id.RV_FavoriteList);
         layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
         apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
         recyclerView.setLayoutManager(layoutManager);




    }


    //getting favourite mosque list from server and display in recycler view
    private void fetchFavourites(){

        Call<ArrayList<MasjidModel>> call = apiInterface.getFavoriteList(userId);


        call.enqueue(new Callback<ArrayList<MasjidModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MasjidModel>> call, Response<ArrayList<MasjidModel>> response) {

                mosqueDataList= response.body();


                adapter = new AdapterRVFavorite(getActivity(), mosqueDataList);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<ArrayList<MasjidModel>> call, Throwable t) {

            }
        });


    }

}
















