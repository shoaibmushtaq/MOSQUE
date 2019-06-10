package com.example.mymosque;

/*public class RV_FindMasajid {
}*/


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymosque.Fragments.FragementProfile;
import com.example.mymosque.Fragments.FragmentFeedback;
import com.example.mymosque.Fragments.FragmentMyMasjid;
import com.example.mymosque.Fragments.FragmentNearestMasajid;
import com.example.mymosque.Models.MasjidModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class RV_FindMasajid extends RecyclerView.Adapter<RV_FindMasajid.ViewHolder> {

        //declaring list and context varriables
        private ArrayList<MasjidModel> MasajidNames = new ArrayList<MasjidModel>();
        private Context mContext;
    double CurrentLatitude,CurrentLongitude,DestinationLatitude,DestinationLongitude;




    //declaring constructor to initialize above varriables with arguments
    public RV_FindMasajid(Context Context, ArrayList<MasjidModel> Names) {
        this.mContext = Context;
        this.MasajidNames = Names;

    }


    //return viewholder class with view
    @NonNull
    @Override
    public RV_FindMasajid.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){



            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_masajid_item, parent, false);
            return new ViewHolder(view);




    }

    //setting viewholder items with data from list
    @Override
    public void onBindViewHolder (@NonNull RV_FindMasajid.ViewHolder holder, final int position){

        holder.MasajidName.setText(MasajidNames.get(position).getName());
        holder.address.setText(MasajidNames.get(position).getAddress());

        Picasso.get().load(MasajidNames.get(position).getImageUrl()).into(holder.Image);


        if(MasajidNames.get(position).getFarvoriate() == 0){

            holder.likeImage.setBackgroundResource(R.drawable.ic_color_heart_unfill);

        }

        else if (MasajidNames.get(position).getFarvoriate() == 1){

            holder.likeImage.setBackgroundResource(R.drawable.ic_color_heart_fill);


        }

        holder.likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MasajidNames.get(position).getFarvoriate_id() == -1 && MasajidNames.get(position).getFarvoriate() == 0 ){



                    holder.likeImage.setBackgroundResource(R.drawable.ic_color_heart_fill);
                    Toast.makeText(mContext,"Added to Favourite : ",Toast.LENGTH_LONG).show();

            }

                else if (MasajidNames.get(position).getFarvoriate_id() == -1 && MasajidNames.get(position).getFarvoriate() == 1){

                    holder.likeImage.setBackgroundResource(R.drawable.ic_color_heart_unfill);
                    Toast.makeText(mContext,"Un-Favourite : ",Toast.LENGTH_LONG).show();

                }
        }

        });

        DestinationLongitude = Double.parseDouble(MasajidNames.get(position).getLongtitude());
        DestinationLatitude = Double.parseDouble(MasajidNames.get(position).getLatitude());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences.Editor editor = mContext.getSharedPreferences("PassMosqueData", MODE_PRIVATE).edit();
                editor.putString("M_ID",String.valueOf(MasajidNames.get(position).getID()) );
                editor.putString("M_name",MasajidNames.get(position).getName());
                editor.putString("M_Image_",MasajidNames.get(position).getImageUrl());
                editor.putString("M_address_",MasajidNames.get(position).getAddress());
//                editor.putString("M_Miles_",String.format("%.2f",MasajidNames.get(position).getMiles()));
                editor.putString("M_Longitude_", String.valueOf(DestinationLongitude));
                editor.putString("M_Latitude_", String.valueOf(DestinationLatitude));
                editor.apply();

                AppCompatActivity activity = (AppCompatActivity) mContext;
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Screen_Area,new FragementProfile()).commit();



            }
        });






    }





    //return the size of list
    @Override
    public int getItemCount () {

        return MasajidNames.size();
    }


    //setting the list with search list
    public void filterList(ArrayList<MasjidModel> filteredList) {
        MasajidNames = filteredList;
        notifyDataSetChanged();
    }


    //viewholder class to hold and initialize layout components
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView MasajidName,address,milesAway;
        ImageView Image, likeImage;


        public ViewHolder(View itemView) {
            super(itemView);


            MasajidName  = itemView.findViewById(R.id.txt_Masajid);
            Image = itemView.findViewById(R.id.img_);
            address = itemView.findViewById(R.id.MasajidAddress);
            milesAway = itemView.findViewById(R.id.TextMiles);
            likeImage = itemView.findViewById(R.id.Like_Image_);




        }
    }


}



