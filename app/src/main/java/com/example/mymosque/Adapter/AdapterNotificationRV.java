package com.example.mymosque.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.mymosque.Models.NotificationModel;
import com.example.mymosque.NotificationDetailScreen;
import com.example.mymosque.R;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class AdapterNotificationRV extends RecyclerView.Adapter<AdapterNotificationRV.NotificationViewHolder> {


    //declaring varriables
        private ArrayList<NotificationModel>modelList;
        private Context mContext;
        private static final String TAG = "AdapterNotificationRV";



        public AdapterNotificationRV(Context Context, ArrayList<NotificationModel> Names) {
            this.mContext = Context;
            this.modelList = Names;

        }


        @NonNull
        @Override
        public NotificationViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){

            //inflating layout
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_rv, parent, false);
            return new NotificationViewHolder(view);


        }

        @Override
        public void onBindViewHolder (@NonNull NotificationViewHolder holder, final int position){


            holder.description.setText(modelList.get(position).getDescription());
            holder.timeStamp.setText(modelList.get(position).getTimestamp());


            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences.Editor editor = mContext.getSharedPreferences("NotificationFragment", MODE_PRIVATE).edit();
                    editor.putString("M_Filename", modelList.get(position).getFile_name());
                    editor.putString("M_Filepath", modelList.get(position).getFile_path());
                    editor.putString("M_Description_", modelList.get(position).getDescription());
                    editor.putString("M_Type", modelList.get(position).getType());
                    editor.putString("M_Time", modelList.get(position).getTimestamp());

                    String notificationId = String.valueOf(modelList.get(position).getN_id());
                    editor.putString("N_ID", notificationId);
                    editor.putString("M_ID",modelList.get(position).getM_id());
                    editor.apply();
                    Intent intent= new Intent(mContext, NotificationDetailScreen.class);
                    mContext.startActivity(intent);



                }
            });



        }

        @Override
        public int getItemCount () {
            return modelList.size();
        }



        //view holder class
        public class NotificationViewHolder extends RecyclerView.ViewHolder {

            TextView description, timeStamp;
            RelativeLayout layout;


            public NotificationViewHolder(View itemView) {
                super(itemView);

                description = itemView.findViewById(R.id.Text_Description);
                timeStamp = itemView.findViewById(R.id.Time_);
                layout =itemView.findViewById(R.id.layoutNotification);


            }
        }


    }

