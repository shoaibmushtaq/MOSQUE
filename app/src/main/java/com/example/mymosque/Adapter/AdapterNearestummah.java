package com.example.mymosque.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mymosque.NearestJummah;
import com.example.mymosque.R;


import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


public class AdapterNearestummah extends RecyclerView.Adapter<AdapterNearestummah.ViewHolder>{



      ArrayList<NearestJummah> modelList;
        private Context mContext;
        private static final String TAG = "AdapterNearestummah";




        public AdapterNearestummah(Context Context, ArrayList<NearestJummah> modelList) {
            this.mContext = Context;
            this.modelList = modelList;

        }
        @NonNull
        @Override
        public AdapterNearestummah.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nearesjummah, parent, false);
            return new AdapterNearestummah.ViewHolder(view);


        }

        @Override
        public void onBindViewHolder (@NonNull AdapterNearestummah.ViewHolder holder, final int position){

            try {



                holder.MasajidName.setText(modelList.get(position).getMosque_name());
                String path = modelList.get(position).getImageurl();
                holder.MasajidAddress.setText(modelList.get(position).getAddress());
                holder.date.setText(modelList.get(position).getTimestamp());
                holder.jummahFirst.setText(modelList.get(position).getJummah1());



               /* holder.Layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences.Editor editor = mContext.getSharedPreferences("Location", MODE_PRIVATE).edit();
                        editor.putString("Longitude",modelList.get(position).get("longitude") );
                        editor.putString("Latitude", modelList.get(position).get("lattitude") );
                        editor.apply();



                        Intent intent= new Intent(mContext, MapsActivity.class);
                        mContext.startActivity(intent);
                    }
                });*/




            }catch (Exception ex){

                ///holder.MasajidMiles.setText("Not Difined");


            }
        }//End of On BindHolder


        @Override
        public int getItemCount () {
            return  modelList.size();
        }
        public  class ViewHolder extends RecyclerView.ViewHolder {

            TextView MasajidName,MasajidAddress,date,jummahFirst,JummahSecond;
            ImageView Image;
            RelativeLayout Layout;

            public ViewHolder(View itemView) {
                super(itemView);

                MasajidName  = itemView.findViewById(R.id.txt_Masajid);
                Image = itemView.findViewById(R.id.img_);
                Layout= itemView.findViewById(R.id.ItemView_);
                MasajidAddress= itemView.findViewById(R.id.txt_address_);
                date=itemView.findViewById(R.id.TextDate);
                jummahFirst=itemView.findViewById(R.id.FirstJummahTime);
                JummahSecond=itemView.findViewById(R.id.SecondJummahTime);



            }
        }







    }




