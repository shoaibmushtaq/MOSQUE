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
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


public class AdapterNotificationRV extends RecyclerView.Adapter<AdapterNotificationRV.NotificationViewHolder> {


        ArrayList<NotificationModel>modelList;
        private Context mContext;


        private static final String TAG = "AdapterNotificationRV";



        public AdapterNotificationRV(Context Context, ArrayList<NotificationModel> Names) {
            this.mContext = Context;
            this.modelList = Names;

        }
        @NonNull
        @Override
        public NotificationViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_rv, parent, false);
            return new NotificationViewHolder(view);


        }

        @Override
        public void onBindViewHolder (@NonNull NotificationViewHolder holder, final int position){



            holder.Description.setText(modelList.get(position).getDescription());
            holder.TimeStamp.setText(modelList.get(position).getTimestamp());


            holder.Layout.setOnClickListener(new View.OnClickListener() {
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

        public class NotificationViewHolder extends RecyclerView.ViewHolder {

            TextView Description,TimeStamp;
            RelativeLayout  Layout;



            public NotificationViewHolder(View itemView) {
                super(itemView);

                Description= itemView.findViewById(R.id.Text_Description);
                TimeStamp  = itemView.findViewById(R.id.Time_);
                Layout=itemView.findViewById(R.id.layoutNotification);





            }
        }


    }

