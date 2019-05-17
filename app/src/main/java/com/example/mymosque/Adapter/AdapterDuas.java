package com.example.mymosque.Adapter;

/*
public class AdapterDuas {
}
*/

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymosque.ItemClickListener;
import com.example.mymosque.R;
import com.silencedut.expandablelayout.ExpandableLayout;

import java.util.HashSet;

public class AdapterDuas extends RecyclerView.Adapter<AdapterDuas.ReyclerViewHolder> {
    private LayoutInflater layoutInflater;
    private HashSet<Integer> expandedPositionSet;
    private Context context;
    private int expanded_position_ = -1;
    private int prev ;

    public AdapterDuas(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        expandedPositionSet = new HashSet<>();
        this.context = context;
    }

   /* @Override
    public ReyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(viewType == 0 ? R.layout.item_duas : R.layout.item_duas, parent, false);

        return new ReyclerViewHolder(v);
    }*/
   @NonNull
   @Override
   public ReyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.item_duas, parent, false);
       return new ReyclerViewHolder(v);
   }

    @Override
    public void onBindViewHolder(@NonNull final ReyclerViewHolder reyclerViewHolder, int position) {

        final AdapterDuas.ReyclerViewHolder dataHolder = reyclerViewHolder;
        if (position == expanded_position_) {
            reyclerViewHolder.showInfo.setVisibility(View.VISIBLE);
            reyclerViewHolder.expandableLayout.setVisibility(View.VISIBLE);
            reyclerViewHolder.relativeLayout.setVisibility(View.GONE);
        } else {
            reyclerViewHolder.relativeLayout.setVisibility(View.VISIBLE);
            reyclerViewHolder.showInfo.setVisibility(View.VISIBLE);
            reyclerViewHolder.expandableLayout.setVisibility(View.VISIBLE);

        }

        dataHolder.setItemClickListener((v, pos) -> {


            // Check for an expanded view, collapse if you find one
            if (expanded_position_ >= 0) {
                prev = expanded_position_;
                notifyItemChanged(prev);
            }

            // Set the current position to "expanded"
            expanded_position_ = dataHolder.getAdapterPosition();
            notifyItemChanged(expanded_position_);
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    /*
        @Override
        public void onBindViewHolder(ReyclerViewHolder holder, int position) {
            holder.updateItem(position);
        }

        @Override
        public int getItemViewType(int position) {


            return position%2;
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class ReyclerViewHolder extends RecyclerView.ViewHolder {
            private ExpandableLayout expandableLayout;
            private TextView showInfo;

            private ReyclerViewHolder(final View view) {
                super(view);
                expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandable_layout);
                showInfo = (TextView) view.findViewById(R.id.Txt_Title);
            }

            private void updateItem(final int position) {
                expandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
                    @Override
                    public void onExpand(boolean expanded) {

                            registerExpand(position, showInfo);

                    }
                });
                expandableLayout.setExpand(expandedPositionSet.contains(position));

            }
        }

        private void registerExpand(int position, TextView textView) {
            if (expandedPositionSet.contains(position)) {

                removeExpand(position);
               // textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                textView.setText("Show description");
                Toast.makeText(context, "Position: " + position + " collapsed!", Toast.LENGTH_SHORT).show();
            } else {

                    addExpand(position);
                    // textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    textView.setText("Hide description");
                    Toast.makeText(context, "Position: " + position + " expanded!", Toast.LENGTH_SHORT).show();


                }
        }

        private void removeExpand(int position) {

                expandedPositionSet.remove(position);

        }

        private void addExpand(int position) {

            expandedPositionSet.add(position);

        }


    */
    public class ReyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private ExpandableLayout expandableLayout;
        private TextView showInfo;
        private RelativeLayout relativeLayout;
        private ItemClickListener itemClickListener;

        ReyclerViewHolder(View itemView) {
            super(itemView);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.hide_layout);
            showInfo = (TextView) itemView.findViewById(R.id.Txt_Title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }

        void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;

        }
    }


}
