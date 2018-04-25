package com.example.mohamed.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Video {

    @SerializedName("id")
    private String strId;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;
    @SerializedName("size")
    private int size;
    @SerializedName("type")
    private String type;


    public Video() {}

    public Video(String strId, String key, String name, String site, int size, String type, long movieVideosId) {
        this.strId = strId;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }


    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
