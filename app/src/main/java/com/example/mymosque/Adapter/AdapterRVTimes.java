package com.example.mymosque.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymosque.MonthlyTime;
import com.example.mymosque.R;

import java.util.ArrayList;

public class AdapterRVTimes extends RecyclerView.Adapter<AdapterRVTimes.ViewHolder> {


    private ArrayList<MonthlyTime> monthlyTimes = new ArrayList<>();

    private Context mContext;


    private static final String TAG = "RecyclerViewAdapter_F_ADS";



    public AdapterRVTimes(Context Context, ArrayList<MonthlyTime> monthlyTimes) {
        this.mContext = Context;
        this.monthlyTimes = monthlyTimes;

    }
    @NonNull
    @Override
    public AdapterRVTimes.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.namz_times_item, parent, false);
        return new AdapterRVTimes.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder (@NonNull AdapterRVTimes.ViewHolder holder, final int position){
holder.date.setText(String.valueOf(position));
holder.fajr.setText(monthlyTimes.get(position).getFajr());
holder.zuhur.setText(monthlyTimes.get(position).getZhuhr());
holder.asr.setText(monthlyTimes.get(position).getAsr());
holder.maghrib.setText(monthlyTimes.get(position).getMaghrib());
holder.isha.setText(monthlyTimes.get(position).getIsha());









    }

    @Override
    public int getItemCount () {
        return monthlyTimes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date,fajr,zuhur,asr,maghrib,isha;


        public ViewHolder(View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            fajr=itemView.findViewById(R.id.txt_fajr_time_beginning);
            zuhur=itemView.findViewById(R.id.txt_zuhr_time_beginning);
            asr=itemView.findViewById(R.id.txt_asr_time_beginning);
            maghrib=itemView.findViewById(R.id.txt_maghrib_time_beginning);
            isha=itemView.findViewById(R.id.txt_isha_time_beginning);



        }
    }


}

