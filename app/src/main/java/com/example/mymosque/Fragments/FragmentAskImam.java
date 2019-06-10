package com.example.mymosque.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymosque.Adapter.AdapterAskimamRV;
import com.example.mymosque.Models.AskImam;
import com.example.mymosque.R;
import com.example.mymosque.Retrofit.ApiClient;
import com.example.mymosque.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAskImam extends Fragment {



    private View askImamView;
    private EditText questionEdittext;
    private Button   sendBtn;
    private int primaryMosqueId,userId;
    private RecyclerView recyclerView;
    private RelativeLayout Layout_noMsg;
    private ApiInterface apiInterface;
    private String question;
    private ArrayList<HashMap<String, String>> AnswersList ;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);











    }//end of onCreate method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        askImamView = inflater.inflate(R.layout.fragment_ask_imam, container, false);

        initComponents();

        listeners();

        getAnswersFromImam();











        return askImamView;
    }
    //End onCreateView Method



    private void initComponents(){



        //<For Toolbar>
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Ask Imam");
        //</For Toolbar>


        primaryMosqueId = 19;
        userId = 147;

        questionEdittext =(EditText) askImamView.findViewById(R.id.msgEDT);
        sendBtn= askImamView.findViewById(R.id.BTN_SEND);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


        linearLayoutManager =  new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        Layout_noMsg = askImamView.findViewById(R.id.Layout_noMsg);
        recyclerView = askImamView.findViewById(R.id.RV_Questions_Answers);
    }

    private void listeners() {


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setQuestionIntoServer(primaryMosqueId,userId,questionEdittext.getText().toString());

                questionEdittext.setText("");
                InputMethodManager inputManager = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);

                // check if no view has focus:
                View currentFocusedView = getActivity().getCurrentFocus();
                if (currentFocusedView != null) {
                    inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }


            }
        });
    }


    private void setQuestionIntoServer(int mosqueId,int userId1,String question1){


        Log.d("testingEditText",questionEdittext.getText().toString());
        Call<ArrayList<AskImam>> call = apiInterface.AskImam(mosqueId,userId1,question1);

        call.enqueue(new Callback<ArrayList<AskImam>>() {
            @Override
            public void onResponse(Call<ArrayList<AskImam>> call, Response<ArrayList<AskImam>> response) {

                Toast.makeText(getActivity(),"your question to imam has been sent",Toast.LENGTH_LONG).show();



            }

            @Override
            public void onFailure(Call<ArrayList<AskImam>> call, Throwable t) {

                Toast.makeText(getActivity(),"problem connectiong to server",Toast.LENGTH_LONG).show();

            }
        });




    }


    private void getAnswersFromImam(){

        Call<ArrayList<AskImam>> call = apiInterface.imamAnswers(primaryMosqueId,userId);

        call.enqueue(new Callback<ArrayList<AskImam>>() {
            @Override
            public void onResponse(Call<ArrayList<AskImam>> call, Response<ArrayList<AskImam>> response) {

                AnswersList  = new ArrayList<>();

                for (int i = 0 ; i < response.body().size() ; i++){

                    String questiion = response.body().get(i).getQuestiion();
                    String anwer = response.body().get(i).getAnwer();

                    HashMap<String, String> data_Hashmap = new HashMap<>();

                    // adding each child node to HashMap key => value

                    data_Hashmap.put("questiion_", questiion);
                    data_Hashmap.put("answer", anwer);

                    AnswersList.add(data_Hashmap);

                    Log.d("question",""+questiion);
                    Log.d("answer",""+anwer);


                }

                recyclerView.setLayoutManager(linearLayoutManager);

                Collections.reverse(AnswersList);
                AdapterAskimamRV adapter = new AdapterAskimamRV(getActivity(), AnswersList);

                if(adapter.getItemCount() != 0){
                    Layout_noMsg.setVisibility(View.GONE);
                }

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();








            }

            @Override
            public void onFailure(Call<ArrayList<AskImam>> call, Throwable t) {

                Toast.makeText(getActivity(),"problem connectiong to server",Toast.LENGTH_LONG).show();

            }
        });







    }








    }












