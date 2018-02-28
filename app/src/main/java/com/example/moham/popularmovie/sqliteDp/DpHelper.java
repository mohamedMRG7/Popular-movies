package com.example.moham.popularmovie.sqliteDp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by moham on 2/28/2018.
 */

public class DpHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME  ="movies.dp";
    private static final int DATABASE_VERSION =1;



    public DpHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_TABLE_FAVOURITE="CREATE TABLE "+DpContract.FavouriteDpEntry.TABLE_NAME +"( "
                +DpContract.FavouriteDpEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DpContract.FavouriteDpEntry.COLUMN_MOVIE_ID+ " TEXT NOT NULL UNIQUE,"
                +DpContract.FavouriteDpEntry.COLUMN_TITLE+" TEXT NOT NULL,"
                +DpContract.FavouriteDpEntry.COLUMN_OVERVIEW+" TEXT NOT NULL,"
                +DpContract.FavouriteDpEntry.COLUMN_DATE+" TEXT NOT NULL,"
                +DpContract.FavouriteDpEntry.COLUMN_POSTER+" TEXT NOT NULL,"
                +DpContract.FavouriteDpEntry.COLUMN_USERRAITNG+" TEXT NOT NULL"+ ");";

        sqLiteDatabase.execSQL(CREATE_TABLE_FAVOURITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DpContract.FavouriteDpEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
