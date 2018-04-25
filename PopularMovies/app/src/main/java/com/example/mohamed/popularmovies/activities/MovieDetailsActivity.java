package com.example.mohamed.popularmovies.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.example.mohamed.popularmovies.DB.queryMethods;
import com.example.mohamed.popularmovies.R;
import com.example.mohamed.popularmovies.Retrofit.RetrofitInterface;
import com.example.mohamed.popularmovies.Retrofit.ServiceGenerator;
import com.example.mohamed.popularmovies.adapter.ActorsAdapter;
import com.example.mohamed.popularmovies.adapter.ActorsListItemDecoration;
import com.example.mohamed.popularmovies.adapter.ReviewsAdapter;
import com.example.mohamed.popularmovies.adapter.VideosAdapter;
import com.example.mohamed.popularmovies.databinding.ActivityMovieDetailsBinding;
import com.example.mohamed.popularmovies.model.Actor;
import com.example.mohamed.popularmovies.model.Movie;
import com.example.mohamed.popularmovies.model.MovieCredits;
import com.example.mohamed.popularmovies.model.MovieReviews;
import com.example.mohamed.popularmovies.model.MovieVideos;
import com.example.mohamed.popularmovies.model.Review;
import com.example.mohamed.popularmovies.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity implements ReviewsAdapter.OnReviewClickListener, VideosAdapter.OnVideoSelectedListener{
    public static final String MOVIE_EXTRA_KEY = "movieExtra";
    private static final int TOOLBAR_TITLE_VISIBILITY_THRESHOLD = 20; // 20px
    private static final int DEFAULT_SCRIM_ANIMATION_DURATION = 400;
    private static final int MAX_CAST_SIZE = 10;
    private static final int MAX_REVIEWS_SIZE = 10;


    private ActivityMovieDetailsBinding binding;
    private Toolbar toolbar;
    private boolean isToolbarTitleVisible = false;
    private String VideoId;
    private boolean inFavorites;


    private ActorsAdapter actorsAdapter;
    private ReviewsAdapter reviewsAdapter;
    private VideosAdapter videosAdapter;

    private Movie movie = new Movie();
    private MovieVideos moviesVideos = new MovieVideos();
    private MovieCredits movieCredits = new MovieCredits();
    private MovieReviews movieReviews = new MovieReviews();
    private queryMethods queryMethods = new queryMethods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_movie_details);
        setupUi();

        Intent intent = getIntent();
        if (intent == null) {
            closewithError();
            return;
        }

        long id = intent.getLongExtra(MOVIE_EXTRA_KEY, -1L);
        if (id == -1L) {
            closewithError();
            return;
        }

  //      Log.d("my ID ", Long.toString(id));
        LoadMovieDetails(id);

        inFavorites = queryMethods.checkMovieInFavorite(id,this);
        updateAddToFavoritesButton(inFavorites);



    }
    private void updateAddToFavoritesButton(Boolean isInFavorites) {
        FancyButton favoritesButton = binding.movieDetailsLayout.addToFavoriteButton;
        if (isInFavorites) {
            favoritesButton.setText(getString(R.string.remove_from_favorites));
            favoritesButton.setIconResource("\uf00d");
        } else {
            favoritesButton.setText(getString(R.string.add_to_favorites));
            favoritesButton.setIconResource("\uf004");
        }
    }

    private void LoadMovieDetails(long id){

        getMovieDetails(id);
        getMovieVideos(id);
        getMoviesCredits(id);
        getMovieReviews(id);

    }

    private void getMovieReviews(long id){

        final RetrofitInterface service = new ServiceGenerator(this).createService(RetrofitInterface.class);
        Call<MovieReviews> call = service.getMovieReviews(id);
        Log.d("URL ", " "+call.request().url());
        call.enqueue(new Callback<MovieReviews>() {
            @Override
            public void onResponse(Call<MovieReviews> call, Response<MovieReviews> response) {
                Log.d("Movies Details", call.request().url().toString());
                if(response.isSuccessful()){
                    MovieReviews movieReviews = response.body();
//                    Log.d("success",movieReviews.getReviews().get(0).getContent());
           //         Log.d("success",Long.toString(movieReviews.getReviewsCount()));
                    Ui_Binding_MovieReviews(movieReviews);

                }


            }

            @Override
            public void onFailure(Call<MovieReviews> call, Throwable t) {
                Log.d("Failure---------->", t.getMessage());
                t.printStackTrace();
            }

        });

    }

    private void Ui_Binding_MovieReviews(MovieReviews movieReviews){
        this.movieReviews = movieReviews;

        if (movieReviews.getReviews() == null || movieReviews.getReviews().size() <= 0) {
            Toast.makeText(this,"Couldn't load movie reviews",Toast.LENGTH_SHORT).show();

        } else {
            List<Review> list = movieReviews.getReviews().subList(0, Math.min(movieReviews.getReviews().size(), MAX_REVIEWS_SIZE));
            reviewsAdapter.updateData(new ArrayList<>(list));
        }
    }

    private void getMoviesCredits(long id){

        final RetrofitInterface service = new ServiceGenerator(this).createService(RetrofitInterface.class);
        Call<MovieCredits> call = service.getMovieCredits(id);
        Log.d("URL ", " "+call.request().url());
        call.enqueue(new Callback<MovieCredits>() {
            @Override
            public void onResponse(Call<MovieCredits> call, Response<MovieCredits> response) {
                Log.d("Movies Details", call.request().url().toString());
                if(response.isSuccessful()){
                    MovieCredits movieCredits = response.body();
                    Log.d("success",movieCredits.getCast().get(0).getName());
                    Log.d("success",Long.toString(movieCredits.getCast().size()));
                    Ui_Binding_MovieCredits(movieCredits);

                }


            }

            @Override
            public void onFailure(Call<MovieCredits> call, Throwable t) {
                Log.d("Failure---------->", t.getMessage());
                t.printStackTrace();
            }

        });
    }

    private void Ui_Binding_MovieCredits(MovieCredits movieCredits){
        this.movieCredits = movieCredits;
        if (movieCredits.getCast() == null || movieCredits.getCast().size() <= 0) {
            Toast.makeText(this,"Couldn't load movie cast",Toast.LENGTH_SHORT).show();


        } else {
            List<Actor> list = movieCredits.getCast().subList(0, Math.min(movieCredits.getCast().size(), MAX_CAST_SIZE));
            Collections.sort(list, (s1, s2) -> s1.getOrder() - s2.getOrder());
            actorsAdapter.updateData(new ArrayList<>(list));
        }
    }

    private void getMovieVideos(long id){

        final RetrofitInterface service = new ServiceGenerator(this).createService(RetrofitInterface.class);
        Call<MovieVideos> call = service.getMovieVideos(id);
        Log.d("URL ", " "+call.request().url());
        call.enqueue(new Callback<MovieVideos>() {
            @Override
            public void onResponse(Call<MovieVideos> call, Response<MovieVideos> response) {
                Log.d("Movies Details", call.request().url().toString());
                if(response.isSuccessful()){
                    MovieVideos movieVideos = response.body();
//                    Log.d("success",movieVideos.getResults().get(0).getName());
//                    Log.d("success",Long.toString(movieVideos.getResults().size()));
                    Ui_Binding_MovieVideos(movieVideos);

                }


            }

            @Override
            public void onFailure(Call<MovieVideos> call, Throwable t) {
                Log.d("Failure---------->", t.getMessage());
                t.printStackTrace();
            }

        });


    }

    private void Ui_Binding_MovieVideos(MovieVideos moviesVideos){
        this.moviesVideos = moviesVideos;
        if (moviesVideos.getResults() == null || moviesVideos.getResults().size() <= 0) {
            Toast.makeText(this,"Couldn't load any videos",Toast.LENGTH_SHORT).show();

        } else {
            videosAdapter.updateData(moviesVideos.getResults());
            VideoId = moviesVideos.getResults().get(0).getKey();
        }

    }

    private void getMovieDetails(Long id){

        final RetrofitInterface service = new ServiceGenerator(this).createService(RetrofitInterface.class);
        Call<Movie> call = service.getMovie(id);
        Log.d("URL ", " "+call.request().url());
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.d("Movies Details", call.request().url().toString());
                if(response.isSuccessful()){
                    Movie movie = response.body();
                  //  Log.d("success",Long.toString(movie.getId()));
                   // Log.d("success",movie.getTitle());
                    Ui_Binding_MovieDetails(movie);
                }


            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("Failure---------->", t.getMessage());
                t.printStackTrace();
            }

        });

    }

    private void Ui_Binding_MovieDetails(Movie movie){

        ProgressBarVisibility(false);
        handleUiDetails(true);
        binding.setHandlers(this);
        binding.setMovie(movie);
        this.movie = movie;

        Picasso.with(MovieDetailsActivity.this)
                .load(getString(R.string.movies_db_poster_base_url_backdrop_w780) + movie.getBackDropUrl())
                .into(binding.movieBackdrop);

        Picasso.with(this)
                .load(getString(R.string.movies_db_poster_base_url_poster_w500) + movie.getPosterUrl())
                .placeholder(R.drawable.poster_ph)
                .into(binding.movieDetailsLayout.moviePoster);
        }

    private void setupUi(){
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        // Make ProgressBar Visible
        ProgressBarVisibility(true);

        // Make appBarRoot & detailsScrollView Invisible
        handleUiDetails(false);

        binding.collapsingToolbarLayout.setScrimAnimationDuration(DEFAULT_SCRIM_ANIMATION_DURATION);
        startAlphaAnimation(binding.toolbarTitle, 0, View.INVISIBLE);

        binding.appBarRoot.addOnOffsetChangedListener((appBarLayout, negOffset) -> {
            int totalScroll = appBarLayout.getTotalScrollRange();
            int currentScroll = totalScroll + negOffset;
            int toolbarHeight = toolbar.getHeight();

            if (currentScroll <= toolbarHeight + TOOLBAR_TITLE_VISIBILITY_THRESHOLD) {
                if (!isToolbarTitleVisible) {
                    startAlphaAnimation(binding.toolbarTitle, DEFAULT_SCRIM_ANIMATION_DURATION, View.VISIBLE);
                    isToolbarTitleVisible = true;
                }
            } else {
                if (isToolbarTitleVisible) {
                    startAlphaAnimation(binding.toolbarTitle, DEFAULT_SCRIM_ANIMATION_DURATION, View.INVISIBLE);
                    isToolbarTitleVisible = false;
                }
            }
        });

        actorsAdapter = new ActorsAdapter(this);
        RecyclerView actorsList = binding.movieDetailsLayout.actorsList;
        LinearLayoutManager actorsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        actorsList.setLayoutManager(actorsLayoutManager);
        actorsList.setNestedScrollingEnabled(true);
        actorsList.addItemDecoration(new ActorsListItemDecoration(25));
        actorsList.setHasFixedSize(true);
        actorsList.setAdapter(actorsAdapter);

        reviewsAdapter = new ReviewsAdapter(this, this);
        RecyclerView reviewsList = binding.movieDetailsLayout.reviewsList;
        LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewsList.setNestedScrollingEnabled(true);
        reviewsList.setLayoutManager(reviewsLayoutManager);
        reviewsList.setHasFixedSize(true);
        reviewsList.setAdapter(reviewsAdapter);

        videosAdapter = new VideosAdapter(this, this);
        RecyclerView videosList = binding.movieDetailsLayout.videosList;
        videosList.setNestedScrollingEnabled(true);
        LinearLayoutManager videosLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        videosList.addItemDecoration(new ActorsListItemDecoration(30));
        videosList.setLayoutManager(videosLayoutManager);
        videosList.setHasFixedSize(true);
        videosList.setAdapter(videosAdapter);

    }

    private void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation =
                (visibility == View.VISIBLE) ? new AlphaAnimation(0f, 1f) : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private void handleUiDetails(boolean visible){
        if(visible){
            binding.appBarRoot.setVisibility(View.VISIBLE);
            binding.detailsScrollView.setVisibility(View.VISIBLE);
        }

        else {
            binding.appBarRoot.setVisibility(View.INVISIBLE);
            binding.detailsScrollView.setVisibility(View.INVISIBLE);
        }

    }

    private void ProgressBarVisibility(boolean visible) {
        if (visible)
            binding.movieDetailsProgressBar.setVisibility(View.VISIBLE);

        else
         binding.movieDetailsProgressBar.setVisibility(View.GONE);

    }

    private void closewithError() {
        Toast.makeText(this, "Error Loading Movie Details", Toast.LENGTH_LONG).show();
        finish();
    }

    public void openTrailer(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public void openVideo(View view) {
        openTrailer(this, VideoId);
    }
    @Override
    public void onReviewClicked(Review review) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(review.getUrl()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No app found to handle this request", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onVideoSelected(Video video) {
        openTrailer(this, video.getKey());
    }

    public void onClickAddToFavorites(View v) {
        if (inFavorites) {
            inFavorites=false;
            updateAddToFavoritesButton(false);
            queryMethods.removeMovieFromFavorites(movie.getId(),this);
            Toast.makeText(this,"Removed From favorites", Toast.LENGTH_LONG).show();

        } else {
            inFavorites=true;
            updateAddToFavoritesButton(true);
            queryMethods.addMovieToFavorites(movie,this);
            Toast.makeText(this,"Added to favorites", Toast.LENGTH_LONG).show();
        }
    }


}
