package com.example.mymosque.Retrofit;

import com.example.mymosque.Models.LinksModel;
import com.example.mymosque.Models.MasjidModel;
import com.example.mymosque.Models.MetaModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MasjidArrayList {


    @SerializedName("data")
    private ArrayList<MasjidModel> masjidModelArrayList;

    @SerializedName("links")
    private LinksModel linksModel;

    @SerializedName("meta")
    private MetaModel metaModel;

    private ArrayList<MasjidModel> favouriteArrayList;



    public ArrayList<MasjidModel> getMasjidModelArrayList() {

        return masjidModelArrayList;
    }

    public LinksModel getLinksModel() {


        return linksModel;
    }

    public MetaModel getMetaModel() {

        return metaModel;
    }

    public ArrayList<MasjidModel> getFavouriteArrayList() {
        return favouriteArrayList;
    }
}
