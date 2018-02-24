package com.example.moham.popularmovie.dp_response;

import com.example.moham.popularmovie.movie.MovieDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by moham on 2/20/2018.
 */

public class JsonUtilies {

    private static final String ORIGINAL_TITLE="original_title";
    private static final String POSTER_IMAGE="poster_path";
    private static final String OVERVIEW="overview";
    private static final String USER_RATING="vote_average";
    private static final String RELEAS_DATE="release_date";
    private static final String MOVIES="results";
    private static final String BASE_POSTERURI="http://image.tmdb.org/t/p/w342/";


    public static MovieDetails getmovieDetails(String jsonStirng,int position)
    {

        try {
            JSONObject mainobject=new JSONObject(jsonStirng);
            JSONArray listofmoviesarr=mainobject.getJSONArray(MOVIES);
            JSONObject movieItem=listofmoviesarr.getJSONObject(position);

            String title=movieItem.optString(ORIGINAL_TITLE);
            String poster=movieItem.optString(POSTER_IMAGE);
            String overview=movieItem.optString(OVERVIEW);
            String user_rating=movieItem.optString(USER_RATING);
            String date=movieItem.optString(RELEAS_DATE);

            String poster_Url=BASE_POSTERURI+poster;
            return new MovieDetails(title,poster_Url,overview,user_rating,date);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    return null;
    }

    public static int getmoviescount(String jsonString)
    {

        JSONObject mainobject;
        try {
            mainobject = new JSONObject(jsonString);
            JSONArray listofmoviesarr=mainobject.getJSONArray(MOVIES);

            return listofmoviesarr.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    return 0;
    }

}
