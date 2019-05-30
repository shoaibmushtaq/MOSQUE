package com.example.mymosque;

import com.google.gson.annotations.SerializedName;

public class BegningTimes {

@SerializedName("s_id")
    private int s_id;
    private String m_id;
    private String date;
    private String jamaat;
    private String is_friday;
    private String Sehri_ends;
    private String Fajr;
    private String Sunrise;
    private String Jamaat1;
    private String Zhuhr;

    public String getSehri_ends() {
        return Sehri_ends;
    }

    public String getFajr() {
        return Fajr;
    }

    public String getSunrise() {
        return Sunrise;
    }

    public String getJamaat1() {
        return Jamaat1;
    }

    public String getZhuhr() {
        return Zhuhr;
    }

    public String getJamaat2() {
        return Jamaat2;
    }

    public String getAsr() {
        return Asr;
    }

    public String getJamaat3() {
        return Jamaat3;
    }

    public String getMaghrib() {
        return Maghrib;
    }

    public String getJamaat4() {
        return Jamaat4;
    }

    public String getIsha() {
        return Isha;
    }

    public String getJamaat5() {
        return Jamaat5;
    }

    public String getTimestamp() {
        return timestamp;
    }

    private String Jamaat2;
    private String Asr;
    private String Jamaat3;
    private String Maghrib;
    private String Jamaat4;
    private String Isha;
    private String Jamaat5;
    private String timestamp;


}
