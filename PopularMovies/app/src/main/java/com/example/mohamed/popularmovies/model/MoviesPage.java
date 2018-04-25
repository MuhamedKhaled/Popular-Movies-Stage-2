package com.example.mohamed.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 09/03/18.
 */

public class MoviesPage {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<Movie> movies;

    public MoviesPage() {
    }

    public int getPage() {
        return page;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "MoviesPage{" +
                "page=" + page +
                ", movies=" + movies +
                '}';
    }
}
