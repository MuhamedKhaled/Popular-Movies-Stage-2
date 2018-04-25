package com.example.mohamed.popularmovies.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mohamed.popularmovies.DB.queryMethods;
import com.example.mohamed.popularmovies.R;
import com.example.mohamed.popularmovies.adapter.FavoriteMoviesAdapter;
import com.example.mohamed.popularmovies.model.Movie;

public class FavoriteMoviesFragment extends Fragment implements FavoriteMoviesAdapter.OnFavoriteMovieClickedListener{


    private OnFragmentInteractionListener mListener;
   // private OnFavoriteMoviesFragmentInteractionListener mListener;
    private FavoriteMoviesAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView moviesFavoritesRecyclerView;

    queryMethods queryMethods = new queryMethods();

    public FavoriteMoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.favorite_movies_fragment, container, false);
        moviesFavoritesRecyclerView = root.findViewById(R.id.favoriteMoviesList);

        adapter = new FavoriteMoviesAdapter(this,getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        moviesFavoritesRecyclerView.setLayoutManager(layoutManager);
        moviesFavoritesRecyclerView.setAdapter(adapter);
        adapter.updateCursor(queryMethods.getAllFavoriteMovies(getActivity()));

        ((TextView)(getActivity().findViewById(R.id.toolbarTitle))).setText("Favorite Movies");

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    @Override
    public void onFavoriteMovieClicked(Movie movie) {
        Log.d("Clicked", "onFavoriteMovieClicked: ");
        mListener.onFavoriteMovieClicked(movie);
    }


    public interface OnFragmentInteractionListener {
        void onFavoriteMovieClicked(Movie movie);
    }
}
