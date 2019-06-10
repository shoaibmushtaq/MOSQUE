package com.example.mymosque.Fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymosque.Models.Message;
import com.example.mymosque.R;
import com.example.mymosque.Retrofit.ApiClient;
import com.example.mymosque.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentFeedback  extends Fragment {


    //declaring varriables
    private View feedbackView;
    private EditText feedback,contact,name;
    private Button sendFeedback;
    private String feedbackMessage,feedbackName,feedbackContact;
    private ApiInterface apiInterface;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        feedbackView = inflater.inflate(R.layout.fragment_feedback, container, false);

        //initializing components
        initComponents();

        //setting listeners for components
        listeners();




        return feedbackView;
    }//End onCreateView Method

    //initializng components
    private void initComponents(){

        //<For Toolbar>
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("FeedBack");
        //</For Toolbar>

        feedback=feedbackView.findViewById(R.id.message_edit_text);
        name=feedbackView.findViewById(R.id.name_edit_text);
        contact=feedbackView.findViewById(R.id.contact_edit_text);
        sendFeedback=feedbackView.findViewById(R.id.btn_submit);
        feedbackMessage= String.valueOf(feedback.getText());
        feedbackContact= String.valueOf(contact.getText());
        feedbackName= String.valueOf(name.getText());

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);




    }


    //setting listeners
    private void listeners(){

        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidation();


            }
        });

    }

    //validating
    private void checkValidation() {


            feedbackMessage = feedback.getText().toString().trim();
            feedbackContact = contact.getText().toString().trim();
            feedbackName = name.getText().toString().trim();

            if (feedbackMessage.isEmpty()) {
                feedback.setError("fill this");
                feedback.requestFocus();
                return;
            }



            if (feedbackContact.isEmpty()) {
                contact.setError("fill this");
                contact.requestFocus();
                return;
            }


            if (feedbackName.isEmpty()) {
                name.setError("fill this");
                name.requestFocus();
                return;
            }


            sendfeedback(147,feedbackMessage,feedbackContact,feedbackName);
            Toast.makeText(getActivity(),"Your Mosque Request Sent to the Admin", Toast.LENGTH_SHORT).show();
            feedback.setText("");
            contact.setText("");
            name.setText("");


        }


    //send the feedback to server
    public  void  sendfeedback(int id,String Message,String contact,String name) {


        Call<com.example.mymosque.Models.Message> call = apiInterface.Feedback(id, Message, contact, name);

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {

                Toast.makeText(getActivity(), "Your Feedback have been Sent ", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {


                Toast.makeText(getActivity(), "Server Problem Contact to System Support", Toast.LENGTH_LONG).show();

            }
        });


    }



    }
