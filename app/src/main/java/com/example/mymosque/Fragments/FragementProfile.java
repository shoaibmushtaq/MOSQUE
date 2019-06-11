package com.example.mymosque.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymosque.MainActivity;
import com.example.mymosque.MapsActivity;
import com.example.mymosque.Models.PrimaryMosque;
import com.example.mymosque.Models.PrimaryMosqueData;
import com.example.mymosque.R;
import com.example.mymosque.Retrofit.ApiClient;
import com.example.mymosque.Retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FragementProfile extends Fragment {


    //declaring varriables
    View v;
    SharedPreferences.Editor editor;
    TextView mosqueName, mosqueAddress, mosqueMiles;
    ImageView mosqueImageView;
    String userID;
    String mosqueImage,mosquename,mosqueID, primaryMosqueId,mosqueaddress,mosquemiles,restoredText,longitude,latitude;
    Button askImam;
    Button mosqueBtn;
    Button prayerTimes;
    Button viewOnMap;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }//end of onCreate method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        //<For Toolbar>
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Profile");
        //</For Toolbar>



        //getting mosque data from shared preferences
        SharedPreferences prefs = getActivity().getSharedPreferences("PassMosqueData", MODE_PRIVATE);
        mosquename=prefs.getString("M_name","not Defined");
        mosqueImage=prefs.getString("M_Image_","not Defined");
        mosqueID=prefs.getString("M_ID","not Defined");
        mosqueaddress=prefs.getString("M_address_","not Defined");
        mosquemiles=prefs.getString("M_Miles_","not Defined");
        longitude=prefs.getString("M_Longitude_","not Defined");
        latitude=prefs.getString("M_Latitude_","not Defined");

        SharedPreferences prefss = getActivity().getSharedPreferences("GetPrimaryMosque", MODE_PRIVATE);
        primaryMosqueId = String.valueOf(prefss.getInt("PM_ID", 0));

        SharedPreferences UserPerfs = getActivity().getSharedPreferences("USER_PREFERENCE", MODE_PRIVATE);
        userID = String.valueOf(UserPerfs.getInt("ID", 0));



        TextView MosqueName=v.findViewById(R.id.HeadingTxt1);
        MosqueName.setText(mosquename);

         askImam = v.findViewById(R.id.AskImamBTN);
         mosqueBtn = (Button) v.findViewById(R.id.MosqueBTN);
         prayerTimes =(Button)v.findViewById(R.id.PrayerTimesBTN);
         viewOnMap =(Button)v.findViewById(R.id.VIewonMapBTN);
         mosqueAddress =v.findViewById(R.id.addressTxt2);
         mosqueAddress.setText(mosqueaddress);
         mosqueMiles =v.findViewById(R.id.Txt_Miles);
        mosqueAddress.setText(mosqueaddress);


        mosqueMiles.setText(mosquemiles);

        mosqueImageView = v.findViewById(R.id.img_);

        if(primaryMosqueId.equals(mosqueID)) {

            askImam.setVisibility(View.VISIBLE);
            mosqueBtn.setVisibility(View.GONE);

        }

        else {
            askImam.setVisibility(View.INVISIBLE);
            prayerTimes.setVisibility(View.INVISIBLE);
        }


        Picasso.get().load(mosqueImage).into(mosqueImageView);



        //setting onclick listener
        mosqueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setPrimaryMosque(Integer.parseInt(userID), Integer.parseInt(mosqueID));

            }
        });

        //setting onclick listener for view on map
        viewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("Location", MODE_PRIVATE).edit();
                editor.putString("Latitude",latitude);
                editor.putString("Longitude", longitude);
                editor.apply();

                startActivity(new Intent(getActivity(), MapsActivity.class));
            }
        });

        //setting onclick listener for ask imam
        askImam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentAskImam fragmentAskImam = new FragmentAskImam();
                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Screen_Area, fragmentAskImam);
                transaction.commit();

            }
        });

        //setting onclick listener for prayer times
        prayerTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentPrayerTimes fragmentPrayerTimes = new FragmentPrayerTimes();
                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Screen_Area, fragmentPrayerTimes);
                transaction.commit();

            }
        });




        return v;
    }//End onCreateView Method



    //setting primary mosque
    public void setPrimaryMosque(int userID , int mosqueId){

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<PrimaryMosque> call = apiInterface.SetPrimary(userID,mosqueId);

        call.enqueue(new Callback<PrimaryMosque>() {
            @Override
            public void onResponse(Call<PrimaryMosque> call, Response<PrimaryMosque> response) {


                GetPrimaryMosQueID(userID);

                askImam.setVisibility(View.VISIBLE);
                prayerTimes.setVisibility(View.VISIBLE);
                mosqueBtn.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<PrimaryMosque> call, Throwable t) {

            }
        });

    }


    //getting primary mosque from server
    public  void  GetPrimaryMosQueID(int U){

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<PrimaryMosqueData> call = apiInterface.getprimrary(U);
        call.enqueue(new Callback<PrimaryMosqueData>() {
            @Override
            public void onResponse(Call<PrimaryMosqueData> call, Response<PrimaryMosqueData> response) {

                try {

                    editor = getActivity().getSharedPreferences("GetPrimaryMosque", MODE_PRIVATE).edit();
                    editor.putInt("PM_ID", response.body().getID());
                    editor.putString("PM_NAME", response.body().getName());
                    editor.putString("PM_URL", response.body().getImageurl());
                    editor.putString("PM_Address", response.body().getAddress());
//                    editor.putString("PM_Milesaway", response.body().get;
                    editor.putString("PM_longitude", response.body().getLongtitude());
                    editor.putString("PM_latitude", response.body().getLatitude());
                    editor.putString("PM_TopicsName", response.body().getTopics_name());
                    editor.apply();


                } catch (NullPointerException ex) {
                    ex.printStackTrace();

                }


            }
            @Override
            public void onFailure(Call<PrimaryMosqueData> call, Throwable t) {
                Toast.makeText(getActivity(),"Server Problem Contact to System Support",Toast.LENGTH_LONG).show(); }
        });

}


}



