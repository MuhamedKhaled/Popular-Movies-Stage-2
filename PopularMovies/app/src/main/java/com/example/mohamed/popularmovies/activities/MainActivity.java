package com.example.mohamed.popularmovies.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.mohamed.popularmovies.R;

import com.example.mohamed.popularmovies.fragments.FavoriteMoviesFragment;
import com.example.mohamed.popularmovies.fragments.PopularMoviesFragment;
import com.example.mohamed.popularmovies.fragments.TopRatedFragment;
import com.example.mohamed.popularmovies.model.Movie;

public class MainActivity extends AppCompatActivity
        implements FavoriteMoviesFragment.OnFragmentInteractionListener
        ,PopularMoviesFragment.OnMoviesSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener{


    private static final String TAB_INDEX_PREFS_KEY = "tabIndex";

    private BottomNavigationView navigation;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUi();
    }

    private void setupUi() {
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setTextColor(ContextCompat.getColor(this, R.color.white));

        Typeface lato = ResourcesCompat.getFont(this, R.font.lato_regular);
        toolbarTitle.setTypeface(lato);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigation = findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.action_popular:
                PopularMoviesFragment popularMoviesFragment = (PopularMoviesFragment)fragmentManager.findFragmentByTag(PopularMoviesFragment.class.getSimpleName());
                if (popularMoviesFragment==null)popularMoviesFragment = new PopularMoviesFragment();
                transaction.replace(R.id.fragmentContainer,popularMoviesFragment,PopularMoviesFragment.class.getSimpleName());
                break;
            case R.id.action_top_rated:
                TopRatedFragment topRatedFragment = (TopRatedFragment)fragmentManager.findFragmentByTag(TopRatedFragment.class.getSimpleName());
                if (topRatedFragment==null)topRatedFragment = new TopRatedFragment();
                transaction.replace(R.id.fragmentContainer,topRatedFragment,TopRatedFragment.class.getSimpleName());
                break;
            case R.id.action_favorites:
                FavoriteMoviesFragment favoriteMoviesFragment = (FavoriteMoviesFragment)fragmentManager.findFragmentByTag(FavoriteMoviesFragment.class.getSimpleName());
                if (favoriteMoviesFragment==null)favoriteMoviesFragment = new FavoriteMoviesFragment();
                transaction.replace(R.id.fragmentContainer,favoriteMoviesFragment,FavoriteMoviesFragment.class.getSimpleName());
                break;
        }
        transaction.commit();
        return true;
    }


    @Override
    protected void onStop() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putInt(TAB_INDEX_PREFS_KEY, navigation.getSelectedItemId())
                .apply();

        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        int id = PreferenceManager.getDefaultSharedPreferences(this)
                .getInt(TAB_INDEX_PREFS_KEY, R.id.action_popular);
        navigation.setSelectedItemId(id);

    }

    private void startDetailsActivity(Movie movie){
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.MOVIE_EXTRA_KEY, movie.getId());
        startActivity(intent);
    }

    @Override
    public void onMovieSelected(Movie movie) {
        startDetailsActivity(movie);
    }


    @Override
    public void onFavoriteMovieClicked(Movie movie) {
       startDetailsActivity(movie);
    }



}
