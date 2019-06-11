package com.example.mymosque;

/*public class RV_FindMasajid {
}*/


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymosque.Fragments.FragementProfile;
import com.example.mymosque.Models.Favourite;
import com.example.mymosque.Models.MasjidModel;
import com.example.mymosque.Retrofit.ApiClient;
import com.example.mymosque.Retrofit.ApiInterface;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.google.maps.android.SphericalUtil.computeDistanceBetween;

public class RV_FindMasajid extends RecyclerView.Adapter<RV_FindMasajid.ViewHolder> {

    //declaring list and context varriables
    private ArrayList<MasjidModel> masajidNames = new ArrayList<MasjidModel>();
    private Context mContext;
    private double currentLatitude, currentlongitude, destinationLatitude, destinationlongitude;


    //declaring constructor to initialize above varriables with arguments
    public RV_FindMasajid(Context Context, ArrayList<MasjidModel> Names) {
        this.mContext = Context;
        this.masajidNames = Names;

    }


    //return viewholder class with view
    @NonNull
    @Override
    public RV_FindMasajid.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_masajid_item, parent, false);
        return new ViewHolder(view);


    }

    //setting viewholder items with data from list
    @Override
    public void onBindViewHolder(@NonNull RV_FindMasajid.ViewHolder holder, final int position) {

        MasjidModel putData = masajidNames.get(position);

        //setting holder values
        holder.masajidName.setText(masajidNames.get(position).getName());
        holder.address.setText(masajidNames.get(position).getAddress());
        Picasso.get().load(masajidNames.get(position).getImageUrl()).into(holder.image);


        //checking if the favourite button is set or not
        if (masajidNames.get(position).getFarvoriate() == 0) {

            //if  not set then unfill heart
            holder.likeImage.setBackgroundResource(R.drawable.ic_color_heart_unfill);

        }

        else if (masajidNames.get(position).getFarvoriate() == 1) {

            //otherwise fill heart
            holder.likeImage.setBackgroundResource(R.drawable.ic_color_heart_fill);

        }



        //setting click listener for favourites button
        holder.likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences UserPerfs = mContext.getSharedPreferences("USER_PREFERENCE", MODE_PRIVATE);
                String UserID = String.valueOf(UserPerfs.getInt("ID", 0));

                if (putData.getFarvoriate_id() == -1 && putData.getFarvoriate() == 0 ){
                    holder.likeImage.setBackgroundResource(R.drawable.ic_color_heart_fill);
                    Toast.makeText(mContext,"Added to Favourite : ",Toast.LENGTH_LONG).show();
                    SetFavouriteServer(putData.getID(),Integer.valueOf(UserID));
                }

                else if ( putData.getFarvoriate_id() != -1 && putData.getFarvoriate() == 1 ) {
                    holder.likeImage.setBackgroundResource(R.drawable.ic_color_heart_unfill);
                    Toast.makeText(mContext, "Un-Favourite : ", Toast.LENGTH_LONG).show();

                    SetUnFavouriteServer(putData.getFarvoriate_id(), 0, position, view);

                }


                else if ( putData.getFarvoriate_id() != -1 && putData.getFarvoriate() == 0  ){
                    holder.likeImage.setBackgroundResource(R.drawable.ic_color_heart_fill);
                    Toast.makeText(mContext,"Added to Favourite : ",Toast.LENGTH_LONG).show();
                    SetUnFavouriteServer(putData.getFarvoriate_id(),1,position,view);





                }

            }
        });

        //getting user location from shared preferences
        SharedPreferences prefs = mContext.getSharedPreferences("LatLong", MODE_PRIVATE);
        currentlongitude = Double.parseDouble(prefs.getString("Long", ""));
        currentLatitude = Double.parseDouble(prefs.getString("Lat", ""));


        //getting masjid location from arraylist
        destinationlongitude = Double.parseDouble(masajidNames.get(position).getLongtitude());
        destinationLatitude = Double.parseDouble(masajidNames.get(position).getLatitude());


        //calculating distance between two locations
        LatLng CurreLatlng = new LatLng(currentLatitude, currentlongitude);
        LatLng DesLatlng = new LatLng(destinationLatitude, destinationlongitude);
        Double meters = computeDistanceBetween(DesLatlng, CurreLatlng);
        double miles = meters / 1609.344;

        holder.milesAway.setText(String.format("%.2f", miles));


        //setting on click listener for whole lyout
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences.Editor editor = mContext.getSharedPreferences("PassMosqueData", MODE_PRIVATE).edit();
                editor.putString("M_ID", String.valueOf(masajidNames.get(position).getID()));
                editor.putString("M_name", masajidNames.get(position).getName());
                editor.putString("M_Image_", masajidNames.get(position).getImageUrl());
                editor.putString("M_address_", masajidNames.get(position).getAddress());
                editor.putString("M_Miles_", String.format("%.2f", miles));
                editor.putString("M_Longitude_", String.valueOf(destinationlongitude));
                editor.putString("M_Latitude_", String.valueOf(destinationLatitude));
                editor.apply();

                AppCompatActivity activity = (AppCompatActivity) mContext;
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Screen_Area, new FragementProfile()).commit();


            }
        });

    }


    //return the size of list
    @Override
    public int getItemCount() {

        return masajidNames.size();
    }


    //setting the list with search list
    public void filterList(ArrayList<MasjidModel> filteredList) {
        masajidNames = filteredList;
        notifyDataSetChanged();
    }


    //viewholder class to hold and initialize layout components
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView masajidName, address, milesAway;
        ImageView image, likeImage;


        public ViewHolder(View itemView) {
            super(itemView);


            masajidName = itemView.findViewById(R.id.txt_Masajid);
            image = itemView.findViewById(R.id.img_);
            address = itemView.findViewById(R.id.MasajidAddress);
            milesAway = itemView.findViewById(R.id.TextMiles);
            likeImage = itemView.findViewById(R.id.Like_Image_);


        }
    }



    //this method will set favourite to server
    public void SetFavouriteServer(int M, int U) {


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Favourite> call = apiInterface.SetFavourite(M, U);

        call.enqueue(new Callback<Favourite>() {
            @Override
            public void onResponse(Call<Favourite> call, Response<Favourite> response) {

                //Toast.makeText(mContext,"Add to the Favourite",Toast.LENGTH_LONG);
                Toast.makeText(mContext, "Add to the Favourite : " + response.body().getName(), Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<Favourite> call, Throwable t) {


                Toast.makeText(mContext, "Server Problem Contact to System Support", Toast.LENGTH_LONG).show();


            }
        });


    }



    //this method will et unfavourite to server
    public  void SetUnFavouriteServer(int id,int F,int p,View v){
        
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Favourite> call = apiInterface.Unfavourite(id,F);


        call.enqueue(new Callback<Favourite>() {
            @Override
            public void onResponse(Call<Favourite> call, Response<Favourite> response) {

                // Log.e("okayjanu",response.toString());
               /* AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Screen_Area,new FragmentHome()).commit();*/


                masajidNames.get(p).setFarvoriate_id(response.body().getFarvoriate_id());
                masajidNames.get(p).setFarvoriate(response.body().getFarvoriate());



            }

            @Override
            public void onFailure(Call<Favourite> call, Throwable t) {


                Toast.makeText(mContext,"Server Problem Contact to System Support",Toast.LENGTH_LONG).show();


            }
        });

    }//End of Function




}



