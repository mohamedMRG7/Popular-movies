package com.example.moham.popularmovie.contentProvider;

import android.net.Uri;

import com.example.moham.popularmovie.sqliteDp.DpContract;

/**
 * Created by moham on 2/28/2018.
 */

public class ContentProviderContract {



    public static final String AUTHORITY="com.example.moham.popularmovie";
    public static final Uri BASE_CONTENT_URL=Uri.parse("content://"+AUTHORITY);
    public static final String PATH_FAVOURITE= DpContract.FavouriteDpEntry.TABLE_NAME;

    public static class FavouriteEntry
    {
        public static final Uri CONTENT_URI=BASE_CONTENT_URL.buildUpon().appendPath(PATH_FAVOURITE).build();
    }



}
