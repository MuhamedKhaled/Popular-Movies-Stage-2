package com.example.mohamed.popularmovies.DB;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteMoviesContract {

    private FavoriteMoviesContract(){}

    public static final String AUTHORITY = "com.example.mohamed.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITE_MOVIES = "favoriteMovies";

    public static class FavoritesEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();

        public static final String TABLE_NAME = "favoriteMovies";

        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TIMESTAMP = "timeStamp";
        public static final String COLUMN_NAME_BACKDROP_URL = "backDropUrl";
    }
}
