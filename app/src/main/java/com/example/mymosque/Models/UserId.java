package com.example.mymosque.Models;

import com.google.gson.annotations.SerializedName;

public class UserId {


        @SerializedName("emi_Number")
        String emi_number;

        @SerializedName("u_id")
        String  user_id;

        public UserId(String emi_number, String user_id) {
            this.emi_number = emi_number;
            this.user_id = user_id;
        }

        public String getEmi_number() {
            return emi_number;
        }

        public void setEmi_number(String emi_number) {
            this.emi_number = emi_number;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }

