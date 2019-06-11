package com.example.mymosque.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymosque.R;
import com.example.mymosque.Retrofit.ApiClient;
import com.example.mymosque.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentAddMosque extends Fragment {

    //declaring varriables
    private View addMosqueView;
    private Button submitBtn;
    private EditText mosqueName,userName,userContact;
    private ApiInterface apiInterface;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //inflating the view
        addMosqueView = inflater.inflate(R.layout.fragment_add_mosque, container, false);


        //initializing components
        initComponents();


        //initializing components listeners
        listeners();



        return addMosqueView;
    }//End onCreateView Method



    //initializing componenets
    private void initComponents(){

        //<For Toolbar>
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Add Mosque");
        //</For Toolbar>

        //initializng edit texts and button
        mosqueName =addMosqueView.findViewById(R.id.EDT_MasajidName_);
        userName=addMosqueView.findViewById(R.id.EDT_YourName_);
        userContact=addMosqueView.findViewById(R.id.EDT_Contact_);
        submitBtn=addMosqueView.findViewById(R.id.BTN_Submit_);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);



    }

    //setting listeners for components
    private void listeners(){


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkValidation();



            }
        });
    }


   //validating
   private void checkValidation(){


    final String name = mosqueName.getText().toString().trim();
    final String user = userName.getText().toString().trim();
    final String contact = userContact.getText().toString().trim();

    if (name.isEmpty()) {
       // ProDialog.dismiss();

        mosqueName.setError("fill this");
        mosqueName.requestFocus();
        return;
    }



    if (user.isEmpty()) {
       // ProDialog.dismiss();
        userName.setError("fill this");
        userName.requestFocus();
        return;
    }


    if (contact.isEmpty()) {
       // ProDialog.dismiss();
        userContact.setError("fill this");
        userContact.requestFocus();
        return;
    }

    Toast.makeText(getActivity(),"Your Mosque Request Sent to the Admin", Toast.LENGTH_SHORT).show();
    mosqueName.setText("");
    userName.setText("");
    userContact.setText("");
    sendAddMosqueRequest(name,user,contact);

}


   //
   private void sendAddMosqueRequest(String mosqueName1,String userName1,String userContact1){


        Call<String> call = apiInterface.addmosque(mosqueName1,userName1,userContact1);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //ProDialog.dismiss();
                Toast.makeText(getActivity(),"Your mosque Request Sent ",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {


                Toast.makeText(getActivity(),"Server Problem Contact to System Support",Toast.LENGTH_LONG).show();


            }
        });






    }
    //End of Function




}
