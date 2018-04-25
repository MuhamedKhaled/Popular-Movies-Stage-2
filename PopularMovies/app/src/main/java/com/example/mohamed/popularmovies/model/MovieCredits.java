package com.example.mohamed.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieCredits {
    @SerializedName("id")
    private long id;

    @SerializedName("cast")
    private ArrayList<Actor> cast;

    public MovieCredits() {}

    public MovieCredits(long id, ArrayList<Actor> cast) {
        this.id = id;
        this.cast = cast;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Actor> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Actor> cast) {
        this.cast = cast;
    }
}
