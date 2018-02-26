package com.example.moham.popularmovie.movie;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by moham on 2/20/2018.
 */

public class MovieDetails implements Parcelable {


    private String title;
    private String poster;
    private String overview;
    private String user_rating;
    private String date;

    public MovieDetails(String title, String poster, String overview, String user_rating, String date) {
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.user_rating = user_rating;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.poster);
        dest.writeString(this.overview);
        dest.writeString(this.user_rating);
        dest.writeString(this.date);
    }

    protected MovieDetails(Parcel in) {
        this.title = in.readString();
        this.poster = in.readString();
        this.overview = in.readString();
        this.user_rating = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<MovieDetails> CREATOR = new Parcelable.Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel source) {
            return new MovieDetails(source);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };
}
