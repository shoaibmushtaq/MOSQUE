package com.example.mymosque.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mymosque.MainActivity;
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

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class FragmentHome extends Fragment {


    //Declaring varriables
    private View homeView;
    private ImageView humbburger;
    private EditText search_masjid;
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RV_FindMasajid adapter;
    private ApiInterface apiInterface;
    private int pageNo = 1;
    private MasjidArrayList masjidArrayList;
    private ArrayList<MasjidModel> mosqueDataList;
    private LinksModel linksModel;
    private MetaModel metaModel;
    private boolean isLoading = false;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //intializing view and inflate the layout xml file
        homeView = inflater.inflate(R.layout.fragmenthome, container, false);

        // initializing decalared components
        initComponents();



        //fetching data from server and show it in recycler view
        fetchDataFromServerAndPopulateView();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mosqueDataList.size() - 1) {
                        //bottom of list!
                        fetchDataForPagination();
                        isLoading = true;
                    }
                }


            }
        });




        //listeners for components
        listeners();








        return homeView;
    }//End onCreateView Method


    private void initComponents(){

        //<For Toolbar>
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        //</For Toolbar>

        recyclerView = (RecyclerView) homeView.findViewById(R.id.RV_masajidList);
        search_masjid = (EditText) homeView.findViewById(R.id.edit_txt_masjid);
        humbburger = (ImageView) homeView.findViewById(R.id.humburgerIcon);
        mDrawerLayout = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


    }

    private void listeners(){

        humbburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        search_masjid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }});




    }

    public void fetchDataFromServerAndPopulateView() {

        Call<MasjidArrayList> call = apiInterface.getMosqueList(pageNo);

        call.enqueue(new Callback<MasjidArrayList>() {
            @Override
            public void onResponse(Call<MasjidArrayList> call, Response<MasjidArrayList> response) {

                masjidArrayList = response.body();

                mosqueDataList = masjidArrayList.getMasjidModelArrayList();
                linksModel = masjidArrayList.getLinksModel();
                metaModel = masjidArrayList.getMetaModel();

                recyclerView.setLayoutManager(layoutManager);
                adapter = new RV_FindMasajid(getActivity(), mosqueDataList);
                recyclerView.setAdapter(adapter);
                pageNo++;


            }

            @Override
            public void onFailure(Call<MasjidArrayList> call, Throwable t) {

            }
        });








    }

    public void fetchDataForPagination(){



        Call<MasjidArrayList> call = apiInterface.getMosqueList(pageNo);

        call.enqueue(new Callback<MasjidArrayList>() {
            @Override
            public void onResponse(Call<MasjidArrayList> call, Response<MasjidArrayList> response) {



                masjidArrayList = response.body();

                ArrayList<MasjidModel> mosqueDataListPagination = masjidArrayList.getMasjidModelArrayList();
                linksModel = masjidArrayList.getLinksModel();
                metaModel = masjidArrayList.getMetaModel();



                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0; i < mosqueDataListPagination.size(); i++) {


                            mosqueDataList.add(mosqueDataListPagination.get(i));

                        }



                        adapter.notifyDataSetChanged();
                        isLoading = false;

                    }
                }, 2000);

                pageNo++;

























            }

            @Override
            public void onFailure(Call<MasjidArrayList> call, Throwable t) {

            }
        });












    }





}
