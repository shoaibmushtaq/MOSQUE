package com.example.mymosque.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class FragmentMasajidList extends Fragment {


    //Declaring varriables
    private View masjidListView;
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
    private ProgressBar progressBar;

    private ArrayList<String> MasajidName = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);









    }//end of onCreate method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //intializing view and inflate the layout xml file
        masjidListView = inflater.inflate(R.layout.fragment_masajid_list, container, false);


        // initializing decalared components
        initComponents();


        //fetching data from server and show it in recycler view
        fetchDataFromServerAndPopulateView();

        //listeners for components
        listeners();


        return masjidListView;
    }

//End onCreateView Method




    //method to initialize components
    private void initComponents(){

        //<For Toolbar>
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Masajid List");
        //</For Toolbar>

        //initializing varriables
        recyclerView = (RecyclerView) masjidListView.findViewById(R.id.RV_NearestMasajidList);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = masjidListView.findViewById(R.id.progress_bar);


    }



    private void listeners() {

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
    }

    //method to fetch mosque list data from server and set it in recycler view
    private void fetchDataFromServerAndPopulateView() {


        Call<MasjidArrayList> call = apiInterface.getMosqueList(pageNo);

        call.enqueue(new Callback<MasjidArrayList>() {
            @Override
            public void onResponse(Call<MasjidArrayList> call, Response<MasjidArrayList> response) {

                masjidArrayList = response.body();

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


        Call<MasjidArrayList> call = apiInterface.getMosqueList(pageNo);

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


}
