package com.example.mymosque.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymosque.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class AdapterAskimamRV extends RecyclerView.Adapter<AdapterAskimamRV.ViewHolder>{


    //declaring varriables
    private ArrayList<HashMap<String, String>> modelList;
    private Context mContext;
    private static final String TAG = "AdapterRVFavorite";


    public AdapterAskimamRV(Context Context, ArrayList<HashMap<String, String>> Names) {
        this.mContext = Context;
        this.modelList = Names;

    }


    @NonNull
    @Override
    public AdapterAskimamRV.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){

        //inflating layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_askimam_question_answers, parent, false);
        return new AdapterAskimamRV.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder (@NonNull AdapterAskimamRV.ViewHolder holder, final int position){

        if (Objects.equals(modelList.get(position).get("answer"), "")) {

            holder.answersByImam.setText("Waiting For Imam Answer");
            holder.questionsByUser.setText(modelList.get(position).get("questiion_"));
        }else{


            holder.answersByImam.setText(modelList.get(position).get("answer"));
            holder.questionsByUser.setText(modelList.get(position).get("questiion_"));
        }


    }//End of On BindHolder


    @Override
    public int getItemCount () {
        return  modelList.size();
    }





    //view holder class
    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView answersByImam, questionsByUser;



        public ViewHolder(View itemView) {
            super(itemView);


            answersByImam = itemView.findViewById(R.id.AnswerByImam_Right);
            questionsByUser = itemView.findViewById(R.id.QuestionByUser_Left);


        }
    }










}



