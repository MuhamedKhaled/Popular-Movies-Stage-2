package com.example.mohamed.popularmovies.Retrofit;

import com.example.mohamed.popularmovies.model.MovieCredits;
import com.example.mohamed.popularmovies.model.MovieReviews;
import com.example.mohamed.popularmovies.model.MovieVideos;
import com.example.mohamed.popularmovies.model.MoviesPage;
import com.example.mohamed.popularmovies.model.Movie;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mohamed on 09/03/18.
 */

public interface RetrofitInterface {


    // put API key after equal
    // it should be like "movie/popular?api_key=61487d687a6d7s"
    // in both services
    @GET("movie/popular?api_key=ac695c241478dc6d192486daec5515b0")
    Call<MoviesPage> getPopularMovies();

    @GET("movie/top_rated?api_key=ac695c241478dc6d192486daec5515b0")
    Call<MoviesPage> getTopRatedMovies();

     @GET("movie/{id}?api_key=ac695c241478dc6d192486daec5515b0")
     Call<Movie> getMovie(@Path("id") long id);

    @GET("movie/{id}/videos?api_key=ac695c241478dc6d192486daec5515b0")
    Call<MovieVideos> getMovieVideos(@Path("id") long id);

    @GET("movie/{id}/reviews?api_key=ac695c241478dc6d192486daec5515b0")
    Call<MovieReviews> getMovieReviews(@Path("id") long id);

    @GET("movie/{id}/credits?api_key=ac695c241478dc6d192486daec5515b0")
    Call<MovieCredits> getMovieCredits(@Path("id") long id);
}
