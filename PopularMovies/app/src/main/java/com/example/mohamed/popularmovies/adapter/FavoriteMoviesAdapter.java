package com.example.mohamed.popularmovies.adapter;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamed.popularmovies.R;
import com.example.mohamed.popularmovies.databinding.FavoriteMoviesListItemBinding;
import com.example.mohamed.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;
import static com.example.mohamed.popularmovies.DB.FavoriteMoviesContract.FavoritesEntry.COLUMN_NAME_BACKDROP_URL;
import static com.example.mohamed.popularmovies.DB.FavoriteMoviesContract.FavoritesEntry.COLUMN_NAME_ID;
import static com.example.mohamed.popularmovies.DB.FavoriteMoviesContract.FavoritesEntry.COLUMN_NAME_TITLE;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMovieViewHolder>{

    private OnFavoriteMovieClickedListener listener;
    private Context context;
    private Cursor cursor;

    public FavoriteMoviesAdapter(OnFavoriteMovieClickedListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        FavoriteMoviesListItemBinding itemBinding = FavoriteMoviesListItemBinding.inflate(inflater, parent, false);
        return new FavoriteMovieViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(FavoriteMovieViewHolder holder, int position) {
        Movie movie = getMovieFromCursor(cursor, position);
        holder.bind(movie);
    }

    private Movie getMovieFromCursor(Cursor cursor, int position) {
        boolean success = cursor.moveToPosition(position);
        Movie movie;

        if (success) {
            movie = new Movie();
            movie.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID)));
            movie.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE)));
            movie.setBackDropUrl(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_BACKDROP_URL)));
            return movie;
        } else {
            Log.e("here", "getMovieFromCursor: returning null instead of moviePreview!");
            return null;
        }
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    public void updateCursor(Cursor c) {
        if (c != null) {
            this.cursor = c;
            this.notifyDataSetChanged();
        }
    }

    public class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        private FavoriteMoviesListItemBinding itemBinding;

        public FavoriteMovieViewHolder(FavoriteMoviesListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(Movie movie) {
            Picasso.with(context)
                    .load(context.getString(R.string.movies_db_poster_base_url_backdrop_w780) + movie.getBackDropUrl())
                    .into(itemBinding.favoriteMovieBackdrop);
            itemBinding.setMovie(movie);
            itemBinding.setHandlers(this);
            itemBinding.executePendingBindings();
        }

        public void onClickFavoriteMovie(View v) {
            listener.onFavoriteMovieClicked(getMovieFromCursor(cursor, getAdapterPosition()));
        }
    }

    public interface OnFavoriteMovieClickedListener {
        void onFavoriteMovieClicked(Movie movie);
    }
}
