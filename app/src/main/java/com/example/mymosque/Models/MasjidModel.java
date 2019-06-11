package com.example.mymosque.Models;

public class MasjidModel {

    private int ID;
    private String name;
    private String longtitude;
    private String latitude;
    private String imageurl;
    private String address;

    public void setFarvoriate(int farvoriate) {
        this.farvoriate = farvoriate;
    }

    private int farvoriate;
    private int farvoriate_id;
    private int miles;

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public int getFarvoriate_id() {
        return farvoriate_id;
    }

    public void setFarvoriate_id(int farvoriate_id) {
        this.farvoriate_id = farvoriate_id;
    }




    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getImageUrl() {
        return imageurl;
    }

    public String getAddress() {
        return address;
    }

    public int getFarvoriate() {
        return farvoriate;
    }
}
