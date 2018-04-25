package com.example.mohamed.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Created by mohamed on 08/03/18.
 */

public class Movie implements Parcelable {

    @SerializedName("id")
    private long id;

    @SerializedName("original_title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterUrl;

    @SerializedName("vote_average")
    private float rating;

    @SerializedName("overview")
    private String overview;

    @SerializedName("backdrop_path")
    private String backDropUrl;

    @SerializedName("runtime")
    private int duration;

    @SerializedName("vote_count")
    private int voteCount;

//    private ArrayList<MovieCredits> credits;
//
//    private ArrayList<MovieReviews> reviews;
//
//    private ArrayList<MovieVideos> videos;

    public Movie() {}

    public Movie(long id, String title, String releaseDate, String posterUrl, float rating, String overview, String backDropUrl, int duration, int voteCount, long creditsId, long reviewsId ,long videosId) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.overview = overview;
        this.backDropUrl = backDropUrl;
        this.duration = duration;
        this.voteCount = voteCount;
//        this.credits.setTargetId(creditsId);
//        this.reviews.setTargetId(reviewsId);
//        this.videos.setTargetId(videosId);
    }

    protected Movie(Parcel in) {
        id = in.readLong();
        title = in.readString();
        releaseDate = in.readString();
        posterUrl = in.readString();
        rating = in.readFloat();
        overview = in.readString();
        backDropUrl = in.readString();
        duration = in.readInt();
        voteCount = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public float getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackDropUrl() {
        return backDropUrl;
    }

    public int getDuration() {
        return duration;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setBackDropUrl(String backDropUrl) {
        this.backDropUrl = backDropUrl;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

//    public ArrayList<MovieCredits> getCredits() {
//        return credits;
//    }
//
//    public void setCredits(ArrayList<MovieCredits> credits) {
//        this.credits = credits;
//    }
//
//    public ArrayList<MovieReviews> getReviews() {
//        return reviews;
//    }
//
//    public void setReviews(ArrayList<MovieReviews> reviews) {
//        this.reviews = reviews;
//    }
//
//    public ArrayList<MovieVideos> getVideos() {
//        return videos;
//    }
//
//    public void setVideos(ArrayList<MovieVideos> videos) {
//        this.videos = videos;
//    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", rating=" + rating +
                ", overview='" + overview + '\'' +
                ", backDropUrl='" + backDropUrl + '\'' +
                ", duration=" + duration +
                ", voteCount=" + voteCount +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(posterUrl);
        dest.writeFloat(rating);
        dest.writeString(overview);
        dest.writeString(backDropUrl);
        dest.writeInt(duration);
        dest.writeInt(voteCount);
    }
}
