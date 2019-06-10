package com.example.mymosque.Models;

import com.google.gson.annotations.SerializedName;

public class NotificationModel {

    @SerializedName("n_id")
    private int n_id;

    @SerializedName("m_id")
    private String m_id;

    @SerializedName("file_name")
    private String file_name;

    @SerializedName("file_path")
    private String file_path;

    @SerializedName("type")
    private String type;

    @SerializedName("discription")
    private String description;

    @SerializedName("timestamp")
    private String timestamp;

    public int getN_id() {
        return n_id;
    }

    public String getM_id() {
        return m_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
