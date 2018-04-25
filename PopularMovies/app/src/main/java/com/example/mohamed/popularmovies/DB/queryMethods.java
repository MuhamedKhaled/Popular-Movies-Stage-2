package com.example.mohamed.popularmovies.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mohamed.popularmovies.model.Movie;

import static com.example.mohamed.popularmovies.DB.FavoriteMoviesContract.FavoritesEntry.COLUMN_NAME_BACKDROP_URL;
import static com.example.mohamed.popularmovies.DB.FavoriteMoviesContract.FavoritesEntry.COLUMN_NAME_ID;
import static com.example.mohamed.popularmovies.DB.FavoriteMoviesContract.FavoritesEntry.COLUMN_NAME_TITLE;
import static com.example.mohamed.popularmovies.DB.FavoriteMoviesContract.FavoritesEntry.TABLE_NAME;

public class queryMethods {

    FavoriteMoviesHelper favoriteMoviesHelper;

    public queryMethods() {
    }

    public boolean addMovieToFavorites(Movie movie, Context context){

        favoriteMoviesHelper = new FavoriteMoviesHelper(context);

        SQLiteDatabase db = favoriteMoviesHelper.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();

        contentValues.put(COLUMN_NAME_ID,movie.getId());
        contentValues.put(COLUMN_NAME_TITLE,movie.getTitle());
        contentValues.put(COLUMN_NAME_BACKDROP_URL, movie.getBackDropUrl());


        long result = db.insert(TABLE_NAME,null ,contentValues);

        if(result == -1)
            return false;


        return true;

    }
    public boolean checkMovieInFavorite(long id , Context context){
        favoriteMoviesHelper = new FavoriteMoviesHelper(context);

        SQLiteDatabase db = favoriteMoviesHelper.getWritableDatabase();
        Cursor cursor = null;
        String sql ="SELECT " +COLUMN_NAME_ID+ " FROM "+TABLE_NAME+" WHERE " +COLUMN_NAME_ID+ " ="+id;

        cursor= db.rawQuery(sql,null);
        Log.d("Cursor Count : " , Integer.toString(cursor.getCount()));

        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public Cursor getAllFavoriteMovies(Context context){
       FavoriteMoviesHelper favoriteMoviesHelper = new FavoriteMoviesHelper(context);
        SQLiteDatabase db = favoriteMoviesHelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;

    }

    public boolean removeMovieFromFavorites(long id,Context context){
        FavoriteMoviesHelper favoriteMoviesHelper = new FavoriteMoviesHelper(context);
        SQLiteDatabase db = favoriteMoviesHelper.getWritableDatabase();

        int x = db.delete(TABLE_NAME, COLUMN_NAME_ID+"="+id, null);

        if(x>0)
            return false;

        return true;
    }

}
