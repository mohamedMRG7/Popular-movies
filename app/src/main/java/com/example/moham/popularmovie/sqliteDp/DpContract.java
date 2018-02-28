package com.example.moham.popularmovie.sqliteDp;

import android.provider.BaseColumns;

/**
 * Created by moham on 2/28/2018.
 */

public  class DpContract {


    public static class FavouriteDpEntry implements BaseColumns
    {
        public static final String TABLE_NAME="favouritemovies";


        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_POSTER="poster";
        public static final String COLUMN_OVERVIEW="overview";
        public static final String COLUMN_USERRAITNG="rating";
        public static final String COLUMN_DATE="date";
        public static final String COLUMN_MOVIE_ID="movieid";

    }
}
