package com.example.mymosque;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.MODE_PRIVATE;

public class LocationClass {

    private Context context;

    public LocationClass(Context context) {

        this.context = context;


    }


    //this method will get current location and put the data in shared preference
    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Getting user last location
            FusedLocationProviderClient fusedLocation = LocationServices.getFusedLocationProviderClient(context);
            fusedLocation.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double mLastLongitude = location.getLongitude();
                        double mLastLatitude = location.getLatitude();
                        SharedPreferences.Editor editor = context.getSharedPreferences("LatLong", MODE_PRIVATE).edit();
                        editor.putString("Lat", String.valueOf(mLastLatitude));
                        editor.putString("Long", String.valueOf(mLastLongitude));
                        editor.apply();
                    }
                }
            });
        }
    }

}
