package com.example.mymosque.Fragments;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymosque.R;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;

public class FragmentQiblaDirection extends Fragment implements SensorEventListener {

    private View qiblaDirectionView;
    private static SensorManager sensorManager;
    private TextView englishDate, islamicDate;
    private static double userLongitude;
    private static double userLatitude;
    private float compassCurrentDegree = 0f;
    private float qiblaCurrentDegree = 0f;
    private ImageView compassImg, qiblaImg;
    private double meccaLatitude = 21.422483;
    private double meccaLongitude = 39.826181;
    private float qiblaAngle;
    private float compassDegree, qiblaDegree;
    private static final int PERMISSION_REQUEST_CODE = 1;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) Objects.requireNonNull(getActivity()).getSystemService(SENSOR_SERVICE);

    }


    //end of onCreate method


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //inflating the layout
        qiblaDirectionView = inflater.inflate(R.layout.fragment_qibla_direction, container, false);



        //getting shared preferences of latitude and longitude
        SharedPreferences prefs = getActivity().getSharedPreferences("LatLong", MODE_PRIVATE);
        userLongitude = Double.parseDouble(prefs.getString("Long",""));
        userLatitude =Double.parseDouble(prefs.getString("Lat",""));


        //initializing componenets
        initComponents();

        //set english and islamic date and time
        setDateAndTime();


        Log.d("checkLat",userLatitude+"");
        Log.d("checkLong",userLongitude+"");


        //passing current cordinates and kaaba coordinates and calling get qibla angle method to get qibla
        qiblaAngle = getQiblaAngle(userLatitude, userLongitude, meccaLatitude, meccaLongitude);



        return qiblaDirectionView;
    }
    //End onCreateView Method




    //initializing components
    private void initComponents() {

        //<For Toolbar>
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Qibla Direction");
        //</For Toolbar>


        //initializing text views of english and islamic dates
        englishDate = qiblaDirectionView.findViewById(R.id.txt_qibla_day);
        islamicDate = qiblaDirectionView.findViewById(R.id.txt_qibla_month);


        //initializing imageviews of compass and qibla images
        compassImg = (ImageView) qiblaDirectionView.findViewById(R.id.compassImg);
        qiblaImg = (ImageView) qiblaDirectionView.findViewById(R.id.qiblaImg);


    }


    //set islamic date and time
    private void setDateAndTime() {

        //setting 24 hour formt and set the english date textview with current time
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String englishDateString = dateFormat.format(Calendar.getInstance().getTime());
        englishDate.setText(englishDateString);


        UmmalquraCalendar islamicCalender = new UmmalquraCalendar();
        islamicCalender.get(Calendar.YEAR);
        islamicCalender.get(Calendar.MONTH);
        islamicCalender.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        islamicCalender.get(Calendar.DAY_OF_MONTH);
        String islamicDateString = islamicCalender.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + "-" + islamicCalender.get(Calendar.DAY_OF_MONTH) + "-" + islamicCalender.get(Calendar.YEAR);
        islamicDate.setText(islamicDateString);
    }

    //this event will be called when the direction changes
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        compassDegree = Math.round(sensorEvent.values[0]);
        RotateAnimation compRotate = new RotateAnimation(compassCurrentDegree, -compassDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        compRotate.setDuration(210);
        compRotate.setFillAfter(true);
        compassImg.startAnimation(compRotate);
        compassCurrentDegree = -compassDegree;

        qiblaDegree = Math.round(sensorEvent.values[0]) + qiblaAngle - 90;
        RotateAnimation qiblaRotate = new RotateAnimation(qiblaCurrentDegree, -qiblaDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        qiblaRotate.setDuration(210);
        qiblaRotate.setFillAfter(true);
        qiblaImg.startAnimation(qiblaRotate);
        qiblaCurrentDegree = -qiblaDegree;


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }


    @Override
    public void onResume() {
        super.onResume();

        sensorManager.registerListener((SensorEventListener) this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        qiblaAngle = getQiblaAngle(userLatitude, userLongitude, meccaLatitude, meccaLongitude);

    }

    @Override
    public void onPause() {
        super.onPause();

        sensorManager.unregisterListener((SensorEventListener) this);
        qiblaAngle = getQiblaAngle(userLatitude, userLongitude, meccaLatitude, meccaLongitude);

    }


    //this method will get all the required coordinates as parameters and return the kaaba qibla angle
    private float getQiblaAngle(double lat1, double long1, double lat2, double long2) {
        double angle, dy, dx;
        dy = lat2 - lat1;
        dx = Math.cos(Math.PI / 180 * lat1) * (long2 - long1);
        angle = Math.atan2(dy, dx);
        angle = Math.toDegrees(angle);
        return (float) angle;
    }





}


