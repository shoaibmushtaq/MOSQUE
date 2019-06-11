package com.example.mymosque;




import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mymosque.Models.MasjidModel;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.google.maps.android.SphericalUtil.computeDistanceBetween;

public class AdapterRVFavorite extends RecyclerView.Adapter<com.example.mymosque.AdapterRVFavorite.ViewHolder> {

    private ArrayList<MasjidModel> masajidNames = new ArrayList<>();
    private Context mContext;
    private double currentLatitude , currentLongitude, destinationLatitude,destinationLongitude;
    private static final String TAG = "RecyclerViewAdapter_F_ADS";


    public AdapterRVFavorite(Context context, ArrayList<MasjidModel> names) {
        this.mContext = context;
        this.masajidNames = names;

    }
    @NonNull
    @Override
    public com.example.mymosque.AdapterRVFavorite.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new com.example.mymosque.AdapterRVFavorite.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder (@NonNull com.example.mymosque.AdapterRVFavorite.ViewHolder holder, final int position){

        holder.masajidName.setText(masajidNames.get(position).getName());

        holder.masajidName.setText(masajidNames.get(position).getName());
        holder.masajidAddress.setText(masajidNames.get(position).getAddress());
        Picasso.get().load(masajidNames.get(position).getImageUrl()).into(holder.image);

        destinationLatitude = Double.parseDouble(masajidNames.get(position).getLatitude());
        destinationLongitude = Double.parseDouble(masajidNames.get(position).getLongtitude());

        SharedPreferences prefs = mContext.getSharedPreferences("LatLong", MODE_PRIVATE);
        currentLongitude = Double.parseDouble(prefs.getString("Long",""));
        currentLatitude =Double.parseDouble(prefs.getString("Lat",""));


        //calculating distance between two locations
        LatLng CurreLatlng = new LatLng(currentLatitude, currentLongitude);
        LatLng DesLatlng = new LatLng(destinationLatitude, destinationLongitude);
        Double meters = computeDistanceBetween(DesLatlng, CurreLatlng);
        double miles = meters / 1609.344;

        holder.masajidMiles.setText(String.format("%.2f", miles));


    }


    @Override
    public int getItemCount () {
        return masajidNames.size();
    }




    public  class ViewHolder extends RecyclerView.ViewHolder {



        TextView masajidName, masajidAddress, masajidMiles;
        ImageView image;
        RelativeLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);


            masajidName = itemView.findViewById(R.id.txt_Masajid);
            image = itemView.findViewById(R.id.img_);
            masajidAddress = itemView.findViewById(R.id.txt_address_);
            masajidMiles =itemView.findViewById(R.id.TextMiles);


        }
    }




}



