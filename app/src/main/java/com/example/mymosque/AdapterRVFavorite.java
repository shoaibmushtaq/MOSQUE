package com.example.mymosque;




import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymosque.Fragments.FragementProfile;
import com.example.mymosque.Fragments.FragmentFeedback;
import com.example.mymosque.Fragments.FragmentNearestMasajid;
import com.example.mymosque.Models.MasjidModel;
import com.example.mymosque.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterRVFavorite extends RecyclerView.Adapter<com.example.mymosque.AdapterRVFavorite.ViewHolder> implements View.OnClickListener {

    private ArrayList<MasjidModel> MasajidNames = new ArrayList<>();

    private Context mContext;


    private static final String TAG = "RecyclerViewAdapter_F_ADS";



    public AdapterRVFavorite(Context Context, ArrayList<MasjidModel> Names) {
        this.mContext = Context;
        this.MasajidNames = Names;

    }
    @NonNull
    @Override
    public com.example.mymosque.AdapterRVFavorite.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new com.example.mymosque.AdapterRVFavorite.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder (@NonNull com.example.mymosque.AdapterRVFavorite.ViewHolder holder, final int position){

        holder.MasajidName.setText(MasajidNames.get(position).getName());

        holder.MasajidName.setText(MasajidNames.get(position).getName());
        holder.MasajidAddress.setText(MasajidNames.get(position).getAddress());
        Picasso.get().load(MasajidNames.get(position).getImageUrl()).into(holder.Image);


        /*holder.Image.setOnClickListener(this);
        holder.MasajidName.setOnClickListener(this);*/
        holder.itemView.setOnClickListener(this);








    }


    @Override
    public int getItemCount () {
        return MasajidNames.size();
    }

    @Override
    public void onClick(View v) {

    }


    public  class ViewHolder extends RecyclerView.ViewHolder {



        TextView MasajidName,MasajidAddress,MasajidMiles;
        ImageView Image;
        RelativeLayout Layout;


        public ViewHolder(View itemView) {
            super(itemView);


            MasajidName  = itemView.findViewById(R.id.txt_Masajid);
            Image = itemView.findViewById(R.id.img_);
            MasajidAddress= itemView.findViewById(R.id.txt_address_);
            MasajidMiles=itemView.findViewById(R.id.TextMiles);


        }
    }


}



