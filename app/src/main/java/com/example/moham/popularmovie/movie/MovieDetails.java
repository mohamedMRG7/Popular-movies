package com.example.moham.popularmovie.movie;

import java.io.Serializable;

/**
 * Created by moham on 2/20/2018.
 */

public class MovieDetails implements Serializable{


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
}
