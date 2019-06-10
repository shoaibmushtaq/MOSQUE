package com.example.mymosque.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymosque.MainActivity;
import com.example.mymosque.R;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class FragmentNearestJummah extends Fragment {


    View v;
Button search_btn;
EditText cityname,postalcode;
String selectedCity;
String postcode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);










    }//end of onCreate method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_nearest_jummah, container, false);

        //For Toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Nearest Jummah");
        //For Toolbar
search_btn=(Button) v.findViewById(R.id.BTN_Search_);
cityname=(EditText) v.findViewById(R.id.EDT_Town_city_);
postalcode=(EditText) v.findViewById(R.id.EDT_PostalCode_);
        SharedPreferences CityEd = getActivity().getSharedPreferences("CityPass", MODE_PRIVATE);
        selectedCity=CityEd.getString("city","");
cityname.setText(selectedCity);
postcode=postalcode.getText().toString();
        cityname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new GetCity();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Screen_Area, myFragment).addToBackStack(null).commit();



            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(postalcode.getText().toString().trim().equals("") && selectedCity.equals("")){

                    Toast.makeText(getActivity(),"Please Fill First Postal Code or City",Toast.LENGTH_SHORT).show();


                }else {
                    SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("PassNearestMosqueData", MODE_PRIVATE).edit();
                    editor.putString("PostalCode",postalcode.getText().toString().trim());
                    editor.putString("City",selectedCity);

                    editor.apply();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.Screen_Area,new ResultNearestJummah()).commit();

                }

            }
        });






        return v;
    }//End onCreateView Method




}
