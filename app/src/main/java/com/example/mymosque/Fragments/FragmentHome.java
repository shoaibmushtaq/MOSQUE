package com.example.mymosque.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.Toast;

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
import static android.content.Context.MODE_PRIVATE;
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
    private MetaModel metaModel;
    private boolean isLoading = false;
    private ProgressBar progressBar;
    private LinksModel linksModel;
    private SharedPreferences userPreferences;
    private int userId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        //intializing view and inflate the layout xml file
        homeView = inflater.inflate(R.layout.fragmenthome, container, false);

        userPreferences = getContext().getSharedPreferences("USER_PREFERENCE", Context.MODE_PRIVATE);
        userId = userPreferences.getInt("ID",0);

        // initializing decalared components
        initComponents();


        //fetching data from server and show it in recycler view
        fetchDataFromServerAndPopulateView();

        //listeners for components
        listeners();

        //getting shared preferences of latitude and longitude
        SharedPreferences prefs = getActivity().getSharedPreferences("LatLong", MODE_PRIVATE);
        String longitude = prefs.getString("Long","");
        String latitude = prefs.getString("Lat","");



        return homeView;
    }//End onCreateView Method


    //method to initialize components
    private void initComponents(){

        //<For Toolbar>
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        //</For Toolbar>

        //initializing varriables
        recyclerView = (RecyclerView) homeView.findViewById(R.id.RV_masajidList);
        search_masjid = (EditText) homeView.findViewById(R.id.edit_txt_masjid);
        humbburger = (ImageView) homeView.findViewById(R.id.humburgerIcon);
        mDrawerLayout = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar = homeView.findViewById(R.id.progress_bar);
        search_masjid = homeView.findViewById(R.id.edit_txt_masjid);
        recyclerView.setLayoutManager(layoutManager);


    }


    //method to set listener for components
    private void listeners(){

        //on scroll listener of recycler view
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

        //on click listener for humburger button to show navigation drawer
        humbburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });


        //on touch listener for search masjid edit text to set focus to true
        search_masjid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }});


        //text change listener for search masjid edit text to search based on user input
        search_masjid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                //update the adapter to show list of data which starts with given letter
                if (adapter != null) {
                    filter(editable.toString());
                }

            }
        });




    }


    //method to fetch mosque list data from server and set it in recycler view
    private void fetchDataFromServerAndPopulateView() {


                Call<MasjidArrayList> call = apiInterface.getMosqueList(userId,pageNo);

                call.enqueue(new Callback<MasjidArrayList>() {
                    @Override
                    public void onResponse(Call<MasjidArrayList> call, Response<MasjidArrayList> response) {

                        masjidArrayList = response.body();

                        Log.d("aaa",""+response.code());

                        mosqueDataList = masjidArrayList.getMasjidModelArrayList();

                        linksModel = masjidArrayList.getLinksModel();
                        metaModel = masjidArrayList.getMetaModel();


                        adapter = new RV_FindMasajid(getActivity(), mosqueDataList);
                        recyclerView.setAdapter(adapter);
                        pageNo++;


                    }

                    @Override
                    public void onFailure(Call<MasjidArrayList> call, Throwable t) {

                    }
                });


    }


    //method to fetch data on scroll listener of recycler view
    private void fetchDataForPagination(){

        Call<MasjidArrayList> call = apiInterface.getMosqueList(userId,pageNo);

        call.enqueue(new Callback<MasjidArrayList>() {
            @Override
            public void onResponse(Call<MasjidArrayList> call, Response<MasjidArrayList> response) {



                if (pageNo >= metaModel.getLast_page() + 1){


                    Toast.makeText(getContext(),"No more data to show",Toast.LENGTH_SHORT).show();
                }


                masjidArrayList = response.body();

                ArrayList<MasjidModel> mosqueDataListPagination = masjidArrayList.getMasjidModelArrayList();
                linksModel = masjidArrayList.getLinksModel();
                metaModel = masjidArrayList.getMetaModel();


                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0; i < mosqueDataListPagination.size(); i++) {


                            mosqueDataList.add(mosqueDataListPagination.get(i));

                        }

                        adapter.notifyDataSetChanged();
                        isLoading = false;
                        progressBar.setVisibility(View.GONE);

                    }
                }, 3000);




                pageNo++;


            }

            @Override
            public void onFailure(Call<MasjidArrayList> call, Throwable t) {

                Toast.makeText(getActivity(),"error fetching data from server",Toast.LENGTH_LONG).show();

            }
        });












    }


    //this method will filter data based on search
    private void filter(String text) {
        ArrayList<MasjidModel> filteredList = new ArrayList<>();

        for (MasjidModel item : mosqueDataList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }




}
