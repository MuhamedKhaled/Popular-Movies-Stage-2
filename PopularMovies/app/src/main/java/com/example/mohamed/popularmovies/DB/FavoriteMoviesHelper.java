package com.example.mohamed.popularmovies.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteMoviesHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "favorite_movies.db";

    private static final String SQL_CREATE_TABLE_FAVORITES_QUERY=
            "CREATE TABLE IF NOT EXISTS " + FavoriteMoviesContract.FavoritesEntry.TABLE_NAME + " (" +
                    FavoriteMoviesContract.FavoritesEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY ON CONFLICT REPLACE," +
                    FavoriteMoviesContract.FavoritesEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL," +
                    FavoriteMoviesContract.FavoritesEntry.COLUMN_NAME_BACKDROP_URL + " TEXT NOT NULL, " +
                    FavoriteMoviesContract.FavoritesEntry.COLUMN_NAME_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    public FavoriteMoviesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITES_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMoviesContract.FavoritesEntry.TABLE_NAME);
        onCreate(db);
    }
}
