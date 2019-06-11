package com.example.mymosque.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mymosque.MapsActivity;
import com.example.mymosque.R;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.google.maps.android.SphericalUtil.computeDistanceBetween;

public class FragmentMyMosque extends Fragment {


    private View myMosquView;
    private double destinationLatitude,destinationLongitude;
    private SharedPreferences primaryMosquePreference , locationPreference;
    private TextView nameTextView,addressTextView,milesTextView;
    private ImageView mosqueImageView;
    private Button prayertimesBtn, mapButton,askImamBtn;
    private double currentLongitude,currentLatitude;
    private RelativeLayout primaryMosqueLayout , noPrimaryMosqueLayout;
    private Button nextScreenButton;







    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationPreference = getContext().getSharedPreferences("LatLong", Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myMosquView = inflater.inflate(R.layout.fragment_my_masjid, container, false);

        //<For Toolbar>
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("My Mosque");
        //</For Toolbar>

        primaryMosqueLayout = myMosquView.findViewById(R.id.MyPrimaryMosque);
        noPrimaryMosqueLayout=myMosquView.findViewById(R.id.HideLayout);
        nextScreenButton=myMosquView.findViewById(R.id.gotoNextScreen);

        noPrimaryMosqueLayout.setVisibility(View.GONE);


        //getting primary mosque data from shared preferences
        primaryMosquePreference = getActivity().getSharedPreferences("GetPrimaryMosque", MODE_PRIVATE);
        int id = primaryMosquePreference.getInt("PM_ID",0);
        String name = primaryMosquePreference.getString("PM_NAME","");
        String address = primaryMosquePreference.getString("PM_Address","");
        String image =  primaryMosquePreference.getString("PM_URL","");
        destinationLongitude = Double.parseDouble(primaryMosquePreference.getString("PM_longitude",""));
        destinationLatitude =  Double.parseDouble(primaryMosquePreference.getString("PM_latitude",""));


        //getting user location from shared preference
        /* locationPreference = getContext().getSharedPreferences("LatLong", MODE_PRIVATE);
        currentLongitude = Double.parseDouble(locationPreference.getString("Long",""));
        currentLatitude = Double.parseDouble(locationPreference.getString("Lat",""));*/

         currentLongitude = Double.parseDouble(locationPreference.getString("Long", ""));
         currentLatitude = Double.parseDouble(locationPreference.getString("Lat", ""));

        Log.d("sLat",""+locationPreference.getString("Long", ""));
        Log.d("sLong",""+locationPreference.getString("Lat", ""));


        //calculating distance between the current location to mosque
        LatLng CurreLatlng = new LatLng(currentLatitude, currentLongitude);
        LatLng DesLatlng = new LatLng(destinationLatitude, destinationLongitude);
        Double meters = computeDistanceBetween(DesLatlng, CurreLatlng);
        double miles = meters / 1609.344;


        if(id==0) {

            //  Alert(getContext());

            noPrimaryMosqueLayout.setVisibility(View.VISIBLE);
            primaryMosqueLayout.setVisibility(View.GONE);

            nextScreenButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.Screen_Area, new FragmentHome()).commit();

                }
            });
        }

        else {

            noPrimaryMosqueLayout.setVisibility(View.GONE);
            primaryMosqueLayout.setVisibility(View.VISIBLE);
        }



       //getting name from shared preference and set it in nameTextView
         nameTextView = myMosquView.findViewById(R.id.HeadingTxt1);
         nameTextView.setText(name);

        //getting address from shared preference and set it in addressTextView
         addressTextView = myMosquView.findViewById(R.id.addressTxt2);
         addressTextView.setText(address);

        //geeting miles from shared preference and set it in milesTextView
         milesTextView = myMosquView.findViewById(R.id.Txt_Miles);
         milesTextView.setText(String.format("%.2f", miles));

        //geeting image url from shared preference and set it in mosqueImageView using picasso library
         mosqueImageView = myMosquView.findViewById(R.id.img_);
         Picasso.get().load(image).into(mosqueImageView);

         askImamBtn =  myMosquView.findViewById(R.id.PrayerTimesBTN);
         askImamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) myMosquView.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Screen_Area,new FragmentAskImam()).commit();
            }
        });

         prayertimesBtn = myMosquView.findViewById(R.id.MosqueBTN);
         prayertimesBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 AppCompatActivity activity = (AppCompatActivity) myMosquView.getContext();
                 activity.getSupportFragmentManager().beginTransaction().replace(R.id.Screen_Area,new FragmentPrayerTimes()).commit();


             }
         });


         mapButton = (Button) myMosquView.findViewById(R.id.VIewonMapBTN);
         mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("Location", MODE_PRIVATE).edit();
                editor.putString("Latitude",String.valueOf(destinationLatitude));
                editor.putString("Longitude", String.valueOf(destinationLongitude));
                editor.apply();

                startActivity(new Intent(getContext(), MapsActivity.class));

            }
        });


        return myMosquView;
    }//End onCreateView Method







}
