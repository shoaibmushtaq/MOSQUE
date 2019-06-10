package com.example.mymosque;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DuaArrayList {
    @SerializedName("data")
    private ArrayList<Dua> duaArrayList;
    public ArrayList<Dua> getDuaArrayList(){
        return duaArrayList;
    }


}
