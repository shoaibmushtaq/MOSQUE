package com.example.mymosque;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.mymosque.Models.PrimaryMosqueData;
import com.example.mymosque.Models.UserId;
import com.example.mymosque.Retrofit.ApiClient;
import com.example.mymosque.Retrofit.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;

public class PermissionActivity extends AppCompatActivity {


    //declaring login button
    private Button loginBtn;

    //declaring emi number varriable
    private String emiNumber = "";

    //declaring api interface instance
    private ApiInterface apiInterface;

    //Declaring permission tag and permission array in which every neeeded permission is stored
    private int PERMISSION_REQUEST_CODE = 1240;
    private String[] PERMISSIONS = new String[]
            {
                    READ_PHONE_STATE,
                    ACCESS_COARSE_LOCATION,
                    ACCESS_FINE_LOCATION
            };

    //declaring shared preference related varriables
    private SharedPreferences sharedPreferences;
    private int userId  = 0;
    private int id      = 0;
    private ProgressDialog progressDialog;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("preparing your data");

        progressDialog.show();

        //intializing shared preference to store user details and set user id to 0 from shared preference
        sharedPreferences = getSharedPreferences("USER_PREFERENCE", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("ID", 0);

        //declaring and initializing retrofit
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //intializing button
        loginBtn = (Button) findViewById(R.id.permissionBtn);


        //Login button on click listener
        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(PermissionActivity.this, MainActivity.class));
                finish();
            }
        });

        //check if the user has given permission or not
        if (hasPermissions(this, PERMISSIONS))
        {
            if (userId == 0)
            {
                //calling method to get emi number and location
                myTelephoneManager();
            } else
            {
                try
                {
                    //calling methods to get primary mosque id and location
                    getPrimaryMosqueId(userId);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        //if the user has not given permissions previously then this method will be called
        else
        {
            //calling function
            requestStoragePermission();
        }
    }


    //this method will ask user for necessory permissions
    private void requestStoragePermission()
    {
        //if the user has denied all permissions then an custom alert box will be displayed telling user that permissions are required to run application
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_PHONE_STATE) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION))
        {
            //displaying custom alert dialog box
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Permission necessary");
            alertBuilder.setMessage("Permissions are required otherwise app will not work");
            alertBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    ActivityCompat.requestPermissions(PermissionActivity.this, new String[]
                            {
                                    READ_PHONE_STATE,
                                    ACCESS_COARSE_LOCATION,
                                    ACCESS_FINE_LOCATION
                            }, PERMISSION_REQUEST_CODE);
                }
            });
            alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                    finish();
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        }
        else
        {
            //if permissions are not given then ask permissions again
            ActivityCompat.requestPermissions(this, new String[]
                    {
                            READ_PHONE_STATE,
                            ACCESS_COARSE_LOCATION,
                            ACCESS_FINE_LOCATION
                    }, PERMISSION_REQUEST_CODE);
        }
    }
    //end of request storage permissions method


    //this method is checking if permissions are given or not
    private boolean hasPermissions(Context context, String[] permissions)
    {
        if (context != null && permissions != null)
        {
            for (String permission : permissions)
            {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }
        return true;
    }

    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //If the user grant permission
        if (requestCode == PERMISSION_REQUEST_CODE)
        {
            if (grantResults.length > 0)
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                {
                    myTelephoneManager();
                }
                //otherwise this code will run
                else {
                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }


    //this method is getting emi number of user
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ServiceCast", "MissingPermission", "HardwareIds"})
    public void myTelephoneManager()
    {
        TelephonyManager telecomManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                emiNumber = telecomManager.getImei();
            } else {
                emiNumber = telecomManager.getDeviceId();
            }
        setEmiNumberIntoServer();
    }

    //this method is creating user from given emi number and pass the given id to getprimarymosque method
    private void setEmiNumberIntoServer()
    {
        Call<UserId> call = apiInterface.GetUserID(emiNumber);
        call.enqueue(new Callback<UserId>()
        {
            @Override
            public void onResponse(@NonNull Call<UserId> call, @NonNull Response<UserId> response)
            {
                if (response.code() == 200)
                {
                    try {
                        id = Integer.parseInt(response.body().getUser_id());
                        SharedPreferences.Editor editor = PermissionActivity.this.getSharedPreferences("USER_PREFERENCE", MODE_PRIVATE).edit();
                        editor.putInt("ID", Integer.parseInt(String.valueOf(id)));
                        editor.putString("EMI", response.body().getEmi_number());
                        editor.apply();

                        //Method to get primary mosque from shared preferences
                        getPrimaryMosqueId(id);

                    } catch (Exception E)
                    {
                        E.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserId> call, @NonNull Throwable t)
            {

            }
        });
    }

    //this method is getting primary mosque from server
    private void getPrimaryMosqueId(int id)
    {
        Call call = apiInterface.getprimrary(id);
        call.enqueue(new Callback<PrimaryMosqueData>()
        {
            @Override
            public void onResponse( retrofit2.Call<PrimaryMosqueData> call,  Response<PrimaryMosqueData> response)
            {
                if (response.code() == 200)
                {
                    try {

                        //Getting saved information from shared preferences of Primary Mosque
                        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("GetPrimaryMosque", MODE_PRIVATE).edit();
                        editor.putInt("PM_ID", response.body().getID());
                        editor.putString("PM_NAME", response.body().getName());
                        editor.putString("PM_URL", response.body().getImageurl());
                        editor.putString("PM_Address", response.body().getAddress());
                        editor.putString("PM_Milesaway", "");
                        editor.putString("PM_longitude", response.body().getLongtitude());
                        editor.putString("PM_latitude", response.body().getLatitude());
                        editor.putString("PM_TopicsName", response.body().getTopics_name());
                        editor.apply();

                        progressDialog.hide();

                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t)
            {

            }
        });
    }


    }
