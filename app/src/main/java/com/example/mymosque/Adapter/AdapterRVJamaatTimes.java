package com.example.mymosque.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymosque.JamaatTimes;
import com.example.mymosque.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class AdapterRVJamaatTimes extends RecyclerView.Adapter<AdapterRVJamaatTimes.ViewHolder> {


    private ArrayList<JamaatTimes> JamaatTimes = new ArrayList<>();

    private Context mContext;


    private static final String TAG = "RecyclerViewAdapter_F_ADS";



    public AdapterRVJamaatTimes(Context Context, ArrayList<com.example.mymosque.JamaatTimes> jamaatTimes) {
        this.mContext = Context;
        this.JamaatTimes = jamaatTimes;

    }
    @NonNull
    @Override
    public AdapterRVJamaatTimes.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jamaat_times_item, parent, false);
        return new AdapterRVJamaatTimes.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder (@NonNull AdapterRVJamaatTimes.ViewHolder holder, final int position){


        holder.fajar.setText(JamaatTimes.get(position).getFajr());
        holder.zuhur.setText(JamaatTimes.get(position).getZhuhr());
        holder.asr.setText(JamaatTimes.get(position).getAsr());
        holder.maghrib.setText(JamaatTimes.get(position).getMaghrib());
        holder.isha.setText(JamaatTimes.get(position).getIsha());









    }

    @Override
    public int getItemCount () {
        return JamaatTimes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


TextView date,fajar,zuhur,asr,maghrib,isha;

        public ViewHolder(View itemView) {
            super(itemView);
date=(TextView) itemView.findViewById(R.id.date);
fajar=(TextView) itemView.findViewById(R.id.txt_fajr_time_beginning);
            zuhur=itemView.findViewById(R.id.txt_zuhr_time_beginning);
            asr=itemView.findViewById(R.id.txt_asr_time_beginning);
            maghrib=itemView.findViewById(R.id.txt_maghrib_time_beginning);
            isha=itemView.findViewById(R.id.txt_isha_time_beginning);




        }
    }


}
