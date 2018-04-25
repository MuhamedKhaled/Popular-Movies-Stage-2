package com.example.mohamed.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.mohamed.popularmovies.R;
import com.example.mohamed.popularmovies.model.Movie;
import com.example.mohamed.popularmovies.model.MoviePrev;
import com.example.mohamed.popularmovies.model.MoviesPage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>{

    public final Context context;
    public ArrayList<Movie> movies;
    private final OnMovieClickListener onMovieClickListener;


    public MoviesAdapter(Context context,OnMovieClickListener onMovieClickListener) {
        this.context = context;
        this.onMovieClickListener = onMovieClickListener;

    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movies_list_item, parent, false);
        return new MoviesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.with(context)
                .load(context.getString(R.string.movies_db_poster_base_url_poster_w342) + movie.getPosterUrl())
                .placeholder(R.drawable.poster_ph)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (movies == null) return 0;
        return movies.size();
    }

    public void updateList(ArrayList<Movie> Movies) {
        this.movies = Movies;
        notifyDataSetChanged();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;


        public MoviesViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.MovielistItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("here ---> ", Long.toString((movies.get(getAdapterPosition())).getId()));
            onMovieClickListener.onMovieClicked(movies.get(getAdapterPosition()));
        }
    }

    public interface OnMovieClickListener {
         void onMovieClicked(Movie movie);
    }



}
