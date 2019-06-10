package com.example.mymosque.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymosque.Adapter.AdapterNotificationRV;
import com.example.mymosque.Models.NotificationModel;
import com.example.mymosque.R;
import com.example.mymosque.Retrofit.ApiClient;
import com.example.mymosque.Retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNotification extends Fragment {

    private View notificationView;
    private int userId = 147;
    private ApiInterface apiInterface;
    private ArrayList<NotificationModel> notificationDataList;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AdapterNotificationRV adapter;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        notificationView = inflater.inflate(R.layout.fragment_notification, container, false);


        initComponents();

        getNotificationsFromServer();




        return notificationView;
    }//End onCreateView Method


private void initComponents(){

    //<For Toolbar>
    android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
    toolbar.setVisibility(View.VISIBLE);
    TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
    mTitle.setText("Notification");
    //</For Toolbar>

    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

   linearLayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

   recyclerView = notificationView.findViewById(R.id.Notifications_RV);
   recyclerView.setLayoutManager(linearLayoutManager);

}

private void getNotificationsFromServer(){

    Call<ArrayList<NotificationModel>> call = apiInterface.getNotifications(userId);

    call.enqueue(new Callback<ArrayList<NotificationModel>>() {
        @Override
        public void onResponse(Call<ArrayList<NotificationModel>> call, Response<ArrayList<NotificationModel>> response) {

            notificationDataList = response.body();

            Log.d("testingNotification",notificationDataList+"");

            adapter = new AdapterNotificationRV(getActivity(), notificationDataList);
            recyclerView.setAdapter(adapter);



        }

        @Override
        public void onFailure(Call<ArrayList<NotificationModel>> call, Throwable t) {

        }
    });








}







}
