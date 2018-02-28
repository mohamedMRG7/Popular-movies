package com.example.moham.popularmovie.movie;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by moham on 2/20/2018.
 */

public class MovieDetails implements Parcelable {


    private String title;
    private String poster;
    private String overview;
    private String userRating;
    private String date;
    private String id;

    private String trialName;
    private Uri trialLinke;

    private String reviewrName;
    private String review;

    public MovieDetails(String title, String poster, String overview, String userRating, String date, String id) {
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.userRating = userRating;
        this.date = date;
        this.id=id;
    }

    public MovieDetails(String trialName, Uri trialLinke) {
        this.trialName = trialName;
        this.trialLinke = trialLinke;
    }

    public MovieDetails(String reviewrName, String review) {
        this.reviewrName = reviewrName;
        this.review = review;
    }

    public String getReviewrName() {
        return reviewrName;
    }

    public String getReview() {
        return review;
    }

    public String getTrialName() {
        return trialName;
    }

    public Uri getTrialLinke() {
        return trialLinke;
    }

    public String getId() {
        return id;
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

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
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
        dest.writeString(this.userRating);
        dest.writeString(this.date);
        dest.writeString(this.id);
    }

    protected MovieDetails(Parcel in) {
        this.title = in.readString();
        this.poster = in.readString();
        this.overview = in.readString();
        this.userRating = in.readString();
        this.date = in.readString();
        this.id = in.readString();
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
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
