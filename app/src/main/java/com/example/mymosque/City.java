package com.example.mymosque;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("city")
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
