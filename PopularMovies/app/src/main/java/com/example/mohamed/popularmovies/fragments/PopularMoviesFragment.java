package com.example.mohamed.popularmovies.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohamed.popularmovies.R;
import com.example.mohamed.popularmovies.Retrofit.RetrofitInterface;
import com.example.mohamed.popularmovies.Retrofit.ServiceGenerator;
import com.example.mohamed.popularmovies.adapter.MoviesAdapter;
import com.example.mohamed.popularmovies.model.Movie;
import com.example.mohamed.popularmovies.model.MoviesPage;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesFragment extends Fragment
        implements MoviesAdapter.OnMovieClickListener {

    protected  OnMoviesSelectedListener mListener;
    protected RecyclerView moviesRecyclerView;
    protected MoviesAdapter adapter;
    protected GridLayoutManager layoutManager;

    public PopularMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.movies_fragment, container, false);

        boolean isInPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int spanCount = isInPortrait ? 2 : 4;

        moviesRecyclerView = root.findViewById(R.id.MoviesList);
        adapter = new MoviesAdapter(getActivity(),this);
        layoutManager = new GridLayoutManager(getActivity(),spanCount,GridLayoutManager.VERTICAL,false);
        moviesRecyclerView.setLayoutManager(layoutManager);
        moviesRecyclerView.setAdapter(adapter);

        ((TextView)(getActivity().findViewById(R.id.toolbarTitle))).setText("Popular Movies");


        LoadPopularMovies();
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMoviesSelectedListener) {
            mListener = (OnMoviesSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void LoadPopularMovies(){

        final RetrofitInterface service =
                new ServiceGenerator(getActivity()).createService(RetrofitInterface.class);
        Call<MoviesPage> call = service.getPopularMovies();
        Log.d("URL ", " "+call.request().url());
        call.enqueue(new Callback<MoviesPage>() {
            @Override
            public void onResponse(Call<MoviesPage> call, Response<MoviesPage> response) {
                Log.d("PopularMovies", call.request().url().toString());
                if(response.isSuccessful()){
                    MoviesPage page = response.body();
                    adapter.updateList(page.getMovies());

                    Log.d("success",Integer.toString(page.getMovies().size()));
                    Log.d("success",Integer.toString(page.getPage()));

                }
            }

            @Override
            public void onFailure(Call<MoviesPage> call, Throwable t) {
                Log.d("Failure---------->", t.getMessage());
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onMovieClicked(Movie movie) {
        mListener.onMovieSelected(movie);
    }

    public interface OnMoviesSelectedListener {
        void onMovieSelected(Movie movie);
    }
}

