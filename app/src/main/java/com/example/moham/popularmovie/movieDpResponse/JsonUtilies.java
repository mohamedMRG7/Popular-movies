package com.example.moham.popularmovie.movieDpResponse;

import android.net.Uri;

import com.example.moham.popularmovie.movie.MovieDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by moham on 2/20/2018.
 */

public class JsonUtilies {

    private static final String ORIGINAL_TITLE = "original_title";
    private static final String POSTER_IMAGE = "poster_path";
    private static final String OVERVIEW = "overview";
    private static final String USER_RATING = "vote_average";
    private static final String RELEAS_DATE = "release_date";
    private static final String MOVIES = "results";
    private static final String BASE_POSTERURI = "http://image.tmdb.org/t/p/w342/";
    private static final String _ID = "id";
    private static final String TRIAL_NAME = "name";
    private static final String TRIAL_KEY = "key";

    private static final String YOUTUBE_BASEURI = "https://www.youtube.com/watch";
    private static final String YOUTUBE_KEY_PARAM = "v";

    private static final String REVIWER_NAME="author";
    private static final String REVIWE="content";
    public static MovieDetails getmovieDetails(String jsonStirng, int position) {

        try {

            JSONObject mainobject = new JSONObject(jsonStirng);
            JSONArray listofmoviesarr = mainobject.getJSONArray(MOVIES);
            JSONObject movieItem = listofmoviesarr.getJSONObject(position);

            String title = movieItem.optString(ORIGINAL_TITLE);
            String poster = movieItem.optString(POSTER_IMAGE);
            String overview = movieItem.optString(OVERVIEW);
            String user_rating = movieItem.optString(USER_RATING);
            String date = movieItem.optString(RELEAS_DATE);
            String id = movieItem.optString(_ID);

            String poster_Url = BASE_POSTERURI + poster;
            return new MovieDetails(title, poster_Url, overview, user_rating, date, id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getMoviesCount(String jsonString) {

        JSONObject mainobject;
        try {
            mainobject = new JSONObject(jsonString);
            JSONArray listofmoviesarr = mainobject.getJSONArray(MOVIES);

            return listofmoviesarr.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }


    public static MovieDetails getMovieTrial(String jsonString, int position) {

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray results = jsonObject.getJSONArray(MOVIES);
            JSONObject object = results.getJSONObject(position);
            String trialName = object.optString(TRIAL_NAME);
            String trialKey = object.optString(TRIAL_KEY);
            Uri trialLink = Uri.parse(YOUTUBE_BASEURI).buildUpon()
                    .appendQueryParameter(YOUTUBE_KEY_PARAM, trialKey).build();

            return new MovieDetails(trialName, trialLink);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getTrailersCount(String jsonString) {

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray results = jsonObject.getJSONArray(MOVIES);

            return results.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static MovieDetails getMovieReviews(String jsonString,int position)
    {
        try {
            JSONObject object=new JSONObject(jsonString);
            JSONArray results=object.getJSONArray(MOVIES);
            JSONObject item=results.getJSONObject(position);
            String reviewrName=item.optString(REVIWER_NAME);
            String review=item.optString(REVIWE);
            return new MovieDetails(reviewrName,review);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    return null;
    }

    public static int getReviwsCount(String jsonString)
    {
        try {
            JSONObject object=new JSONObject(jsonString);
            JSONArray results=object.getJSONArray(MOVIES);

            return results.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
