package com.example.mymosque;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymosque.Fragments.FragmentAddMosque;
import com.example.mymosque.Fragments.FragmentDua;
import com.example.mymosque.Fragments.FragmentFavorite;
import com.example.mymosque.Fragments.FragmentFeedback;
import com.example.mymosque.Fragments.FragmentHome;
import com.example.mymosque.Fragments.FragmentMasajidList;
import com.example.mymosque.Fragments.FragmentMyMosque;
import com.example.mymosque.Fragments.FragmentNearestJummah;
import com.example.mymosque.Fragments.FragmentNearestMasajid;
import com.example.mymosque.Fragments.FragmentNotification;
import com.example.mymosque.Fragments.FragmentQiblaDirection;
import com.example.mymosque.Fragments.FragmentSettings;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.PendingIntent.getActivity;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ImageView Humbburger;
    private DrawerLayout mDrawerLayout;
    private ImageView backbutton;
    private TextView shareapp;
    private DrawerLayout drawerLayout;
    public static final String REQ_TOKEN = "REQ_TOKEN";
    private TextView homeTextView,favouritesTextView,masajidListTextView,nearestMasjidTextView,nearestJummahTextView,qiblaDirectionTextView
                     ,notificationTextView,addMosqueTextView,duaTextView,settingsTextView,feedbackTextView;

    private boolean doubleBackToExitPressedOnce = false;
    private Fragment fragment = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting user's current location
        getLastLocation();




        //it will get the current token and store it in newToken varriable
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.d(REQ_TOKEN, newToken);
            }
        });




        shareapp = (TextView) findViewById(R.id.text12);
        shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }


        });


        //Button Calls Navigation view
        backbutton = (ImageView) findViewById(R.id.backButton);
        Humbburger = (ImageView) findViewById(R.id.humburgerIcon);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Humbburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showHome();

            }


        });


        homeTextView = (TextView) findViewById(R.id.text1);
        homeTextView.setOnClickListener(this);

        feedbackTextView = (TextView) findViewById(R.id.text11);
        feedbackTextView.setOnClickListener(this);

        duaTextView = (TextView) findViewById(R.id.text9);
        duaTextView.setOnClickListener(this);

        masajidListTextView = (TextView) findViewById(R.id.text3);
        masajidListTextView.setOnClickListener(this);

        nearestMasjidTextView = (TextView) findViewById(R.id.text4);
        nearestMasjidTextView.setOnClickListener(this);

        nearestJummahTextView = (TextView) findViewById(R.id.text5);
        nearestJummahTextView.setOnClickListener(this);

        settingsTextView = (TextView) findViewById(R.id.text10);
        settingsTextView.setOnClickListener(this);

        notificationTextView = (TextView) findViewById(R.id.text7);
        notificationTextView.setOnClickListener(this);

        addMosqueTextView = (TextView) findViewById(R.id.text8);
        addMosqueTextView.setOnClickListener(this);

        qiblaDirectionTextView = (TextView) findViewById(R.id.text6);
        qiblaDirectionTextView.setOnClickListener(this);

        favouritesTextView = (TextView) findViewById(R.id.text2);
        favouritesTextView.setOnClickListener(this);
        //


        //initializing shared preference and getting primary mosque id
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("GetPrimaryMosque", MODE_PRIVATE);
        int primaryId = sharedPreferences.getInt("PM_ID", 0);


        //checking if primary mosque id is not equal to zero then display primary mosque fragment
        if (primaryId != 0) {


            /*FragmentMyMosque primaryMosque = new FragmentMyMosque();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.Screen_Area, primaryMosque);
            transaction.commit();*/

            FragmentHome myf = new FragmentHome();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.Screen_Area, myf);
            transaction.commit();


        }

        //else display home fragment
        else {

            FragmentHome myf = new FragmentHome();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.Screen_Area, myf);
            transaction.commit();

        }





        //initializing drawer layout and action bar toggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if (item != null && item.getItemId() == android.R.id.home) {
                    if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        drawerLayout.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                }
                return false;
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();




        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    //end of oncreate method




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }/* else{

            showHome();
        }*/

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;

        } else {

            showHome();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);


    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //this method will direct to home fragment
    private void showHome() {

        fragment = new FragmentHome();
        if (fragment != null) {

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.Screen_Area, fragment, fragment.getTag()).commit();
        }
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        Class fragmentClass = null;

        if (v.getId() == R.id.text1) {
            //do something here if button1 is clicked

            fragmentClass = FragmentMyMosque.class;
        } else if (v.getId() == R.id.text4) {
            fragmentClass = FragmentNearestMasajid.class;
            //do something here if button3 is clicked
            // fragmentClass = FragmentLevel.class;
        } else if (v.getId() == R.id.text5) {
            //do something here if button3 is clicked
            // fragmentClass = FragmentNotification.class;
            fragmentClass = FragmentNearestJummah.class;
        } else if (v.getId() == R.id.text6) {
            //do something here if button3 is clicked
            // fragmentClass = FragmentAboutUS.class;
            fragmentClass = FragmentQiblaDirection.class;
        } else if (v.getId() == R.id.text11) {
            //do something here if button3 is clicked
            fragmentClass = FragmentFeedback.class;
        } else if (v.getId() == R.id.text9) {
            //do something here if button3 is clicked
            fragmentClass = FragmentDua.class;
        } else if (v.getId() == R.id.text3) {
            //do something here if button3 is clicked
            fragmentClass = FragmentMasajidList.class;
        } else if (v.getId() == R.id.text10) {
            //do something here if button3 is clicked
            fragmentClass = FragmentSettings.class;
        } else if (v.getId() == R.id.text7) {
            //do something here if button3 is clicked
            fragmentClass = FragmentNotification.class;
        } else if (v.getId() == R.id.text8) {
            //do something here if button3 is clicked
            fragmentClass = FragmentAddMosque.class;
        } else if (v.getId() == R.id.text2) {
            //do something here if button3 is clicked
            fragmentClass = FragmentFavorite.class;
        }


        if (fragment != null) {

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.Screen_Area, fragment, fragment.getTag()).commit();
        }


        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Screen_Area, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }


    //this method will get current location and put the data in shared preference
    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Getting user last location
            FusedLocationProviderClient fusedLocation = LocationServices.getFusedLocationProviderClient(this);
            fusedLocation.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double mLastLongitude = location.getLongitude();
                        double mLastLatitude = location.getLatitude();
                        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("LatLong", MODE_PRIVATE).edit();
                        editor.putString("Lat", String.valueOf(mLastLatitude));
                        editor.putString("Long", String.valueOf(mLastLongitude));
                        editor.apply();
                    }
                }
            });
        }
    }

}
