package com.example.mymosque.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.mymosque.R;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

public class FragementProfile extends Fragment {


    View v;
    SharedPreferences.Editor editor,EditPerivous;
    TextView  MosqueName ,MosqueAddress,MosqueMiles;
    ImageView mosqueImageView;
    String mosqueImage,mosquename,mosqueID,PrimaryMosQueID,mosqueaddress,mosquemiles,restoredText,longitude,latitude;



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

        SharedPreferences prefs = getActivity().getSharedPreferences("PassMosqueData", MODE_PRIVATE);
        mosquename=prefs.getString("M_name","not Defined");
        mosqueImage=prefs.getString("M_Image_","not Defined");
        mosqueID=prefs.getString("M_ID","not Defined");
        mosqueaddress=prefs.getString("M_address_","not Defined");
        mosquemiles=prefs.getString("M_Miles_","not Defined");
        longitude=prefs.getString("M_Longitude_","not Defined");
        latitude=prefs.getString("M_Latitude_","not Defined");


        TextView MosqueName=v.findViewById(R.id.HeadingTxt1);
        MosqueName.setText(mosquename);

       Button mosqueBtn = (Button) v.findViewById(R.id.MosqueBTN);
       // Button AskImam= (Button) v.findViewById(R.id.AskImamBTN);
        Button ParyerTimes=(Button)v.findViewById(R.id.PrayerTimesBTN);
       Button  ViewOnMap=(Button)v.findViewById(R.id.VIewonMapBTN);
        MosqueAddress=v.findViewById(R.id.addressTxt2);
        MosqueAddress.setText(mosqueaddress);

        MosqueMiles=v.findViewById(R.id.Txt_Miles);
      //  MosqueMiles.setText(mosquemiles);

        MosqueAddress.setText(mosqueaddress);
        MosqueMiles.setText(mosquemiles);

        mosqueImageView = v.findViewById(R.id.img_);

        Picasso.get().load(mosqueImage).into(mosqueImageView);



        mosqueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Screen_Area,new FragmentMyMasjid()).commit();
            }
        });







        return v;
    }//End onCreateView Method










}
