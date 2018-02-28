package com.example.moham.popularmovie.movieDpResponse;

import android.net.Uri;

import com.example.moham.popularmovie.BuildConfig;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by moham on 2/20/2018.
 */

public class NetworkUtilies {

    private static final String MOVIES_BASE_URL="http://api.themoviedb.org/3/movie";
    private static final String PARAM_KEY="api_key";

    public static String SORT_POPULAR="popular";
    public static final String SORT_TOPRATED="top_rated";

    public static final String REVIEW_PATH="reviews";
    public static final String TRIALS_PATH="videos";

    public static final String MOVIELISET_TAG="movielist";
    public static final String TRIALS_TAG="trials";
    public static final String REVIEWS_TAG="review";



    public static final String API_KEY= BuildConfig.API_KEY;



    public static String getMoviesListResponse(String sortby)
    {

        HttpURLConnection connection=null;
        try {
            Uri uri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendPath(sortby)
                    .appendQueryParameter(PARAM_KEY, API_KEY).build();

            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("//A");
            boolean hasnext = scanner.hasNext();
            if (hasnext)
                return scanner.next();
            else
                return null;
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if (connection!=null)
            connection.disconnect();
        }
    return null;
    }


    public static String getMovieTrialsResponse(String movieId,String path)
    {
        HttpURLConnection connection=null;
        try {
            Uri uri=Uri.parse(NetworkUtilies.MOVIES_BASE_URL).buildUpon()
                    .appendPath(movieId)
                    .appendPath(path)
                    .appendQueryParameter(NetworkUtilies.PARAM_KEY,NetworkUtilies.API_KEY).build();

            URL url=new URL(uri.toString());
            connection= (HttpURLConnection) url.openConnection();
            InputStream in=connection.getInputStream();
            Scanner scanner=new Scanner(in);
            scanner.useDelimiter("//A");
            boolean hasnext=scanner.hasNext();

            if (hasnext)
                return scanner.next();
            else
                return null;


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection !=null)
                connection.disconnect();
        }
    return null;
    }


}
