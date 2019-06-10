package com.example.mymosque;

import com.google.gson.annotations.SerializedName;

public class NearestJummah {
    @SerializedName("mosque_name")
   String mosque_name;
   String Jummah1;
   String Jummah2;
   String imageurl;
   String timestamp;
   String date;
   String m_id;

    public String getMosque_name() {
        return mosque_name;
    }

    public void setMosque_name(String mosque_name) {
        this.mosque_name = mosque_name;
    }

    public String getJummah1() {
        return Jummah1;
    }

    public void setJummah1(String jummah1) {
        Jummah1 = jummah1;
    }

    public String getJummah2() {
        return Jummah2;
    }

    public void setJummah2(String jummah2) {
        Jummah2 = jummah2;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    String address;
   String longtitude;
   String latitude;
}
