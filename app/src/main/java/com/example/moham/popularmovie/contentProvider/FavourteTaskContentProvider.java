package com.example.moham.popularmovie.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.moham.popularmovie.sqliteDp.DpContract;
import com.example.moham.popularmovie.sqliteDp.DpHelper;

/**
 * Created by moham on 2/28/2018.
 */

public class FavourteTaskContentProvider extends ContentProvider {

    private DpHelper dp;

    public static final int ALLMOVIES=100;
    public static final int MOVIE_WITH_ID=101;

    public static final UriMatcher sUriMatcher =buildUriMatcher();

    @Override
    public boolean onCreate() {

        dp=new DpHelper(getContext());
        return true;
    }


    public static UriMatcher buildUriMatcher (  )
    {
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(ContentProviderContract.AUTHORITY,ContentProviderContract.PATH_FAVOURITE,ALLMOVIES);
        uriMatcher.addURI(ContentProviderContract.AUTHORITY,ContentProviderContract.PATH_FAVOURITE+"/*",MOVIE_WITH_ID);


    return uriMatcher;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor=null;
        int matcher=sUriMatcher.match(uri);
        SQLiteDatabase database=dp.getReadableDatabase();

        switch (matcher)
        {
            case ALLMOVIES:
                cursor=database.query(DpContract.FavouriteDpEntry.TABLE_NAME,strings,s,strings1,null,null,s1);
                break;
            case MOVIE_WITH_ID:
                String id=uri.getPathSegments().get(1);
                String msellction=DpContract.FavouriteDpEntry.COLUMN_MOVIE_ID+" =? ";
                String [] msellctionargs=new String[]{id};

                cursor=database.query(DpContract.FavouriteDpEntry.TABLE_NAME,
                        null,msellction,msellctionargs,null,null,null);

        }


        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }


        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        SQLiteDatabase database=dp.getWritableDatabase();
        Uri uri1 = null;
        switch (sUriMatcher.match(uri)) {
            case ALLMOVIES:
             long id= database.insert(DpContract.FavouriteDpEntry.TABLE_NAME, null, contentValues);
             if (id>0) {
                 uri1 = ContentUris.withAppendedId(ContentProviderContract.FavouriteEntry.CONTENT_URI, id);
             }
                break;
             }
        getContext().getContentResolver().notifyChange(uri,null);
        return uri1;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        int matcher=sUriMatcher.match(uri);
        SQLiteDatabase database=dp.getWritableDatabase();
        String id=uri.getPathSegments().get(1);
        String msellction=DpContract.FavouriteDpEntry.COLUMN_MOVIE_ID+ "=?";
        String [] msellctionargs=new String []{id};
        int result=0;

        switch (matcher)
        {
            case MOVIE_WITH_ID:
               result= database.delete(DpContract.FavouriteDpEntry.TABLE_NAME,msellction,msellctionargs);
                break;
            case ALLMOVIES:
              result=  database.delete(DpContract.FavouriteDpEntry.TABLE_NAME,null,null);
        }

    if (result!=0)
        getContext().getContentResolver().notifyChange(uri, null);

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
