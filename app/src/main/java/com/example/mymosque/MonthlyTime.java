package com.example.mymosque;

import com.google.gson.annotations.SerializedName;

public class MonthlyTime {
    public String getFajr() {
        return Fajr;
    }

    public String getZhuhr() {
        return Zhuhr;
    }

    public String getAsr() {
        return Asr;
    }

    public String getMaghrib() {
        return Maghrib;
    }

    public String getIsha() {
        return Isha;
    }

    @SerializedName("Fajr")
    private String Fajr;
    private String Zhuhr;
    private String Asr;
    private String Maghrib;
    private String Isha;
    private String Date;

    public String getDate() {
        return Date;
    }
}
