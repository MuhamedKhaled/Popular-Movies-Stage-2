package com.example.mohamed.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class MovieVideos {

    @SerializedName("id")
    private long id;

    @SerializedName("results")
    private ArrayList<Video> results;

    public MovieVideos() {}

    public MovieVideos(long id, ArrayList<Video> results) {
        this.id = id;
        this.results = results;
    }

    public long getId() {
        return id;
    }

    public ArrayList<Video> getResults() {
        return results;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setResults(ArrayList<Video> results) {
        this.results = results;
    }
}
