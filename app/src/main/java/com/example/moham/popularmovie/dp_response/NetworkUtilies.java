package com.example.moham.popularmovie.dp_response;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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


    public static final String API_KEY= BuildConfig.API_KEY;



    public static String getConnectonResponse(String sortby)
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




}
