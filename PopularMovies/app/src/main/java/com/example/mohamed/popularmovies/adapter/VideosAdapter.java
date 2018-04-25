package com.example.mohamed.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mohamed.popularmovies.databinding.VideosListItemBinding;
import com.example.mohamed.popularmovies.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {
    private Context context;
    private OnVideoSelectedListener listener;
    private ArrayList<Video> videos;

    public VideosAdapter(Context context, OnVideoSelectedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        VideosListItemBinding itemBinding = VideosListItemBinding.inflate(inflater, parent, false);
        return new VideoViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.bind(video);
    }

    @Override
    public int getItemCount() {
        if (videos == null) return 0;
        return videos.size();
    }

    public void updateData(ArrayList<Video> newVideos) {
        this.videos = newVideos;
        notifyDataSetChanged();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private final VideosListItemBinding itemBinding;

        public VideoViewHolder(VideosListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(Video video) {
            Picasso.with(context).load("https://img.youtube.com/vi/" + video.getKey() + "/mqdefault.jpg").into(itemBinding.videoThumbnail);
            itemBinding.setVideo(video);
            itemBinding.setHandlers(this);
            itemBinding.executePendingBindings();
        }

        public void openVideo(View v) {
            listener.onVideoSelected(videos.get(getAdapterPosition()));
        }
    }

    public interface OnVideoSelectedListener {
        void onVideoSelected(Video video);
    }
}
