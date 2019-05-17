package com.example.mymosque.Fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymosque.Adapter.AdapterDuas;
import com.example.mymosque.R;

public class FragmentDua extends Fragment {

    View v;
    private RecyclerView recyclerView;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








    }//end of onCreate method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_dua, container, false);

        //<For Toolbar>
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Duas");
        //</For Toolbar>


        recyclerView = (RecyclerView)v.findViewById(R.id.RV_DUAS);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        AdapterDuas recyclerViewAdapter = new AdapterDuas(getActivity());
        recyclerView.setAdapter(recyclerViewAdapter);







        return v;
    }//End onCreateView Method




























}
