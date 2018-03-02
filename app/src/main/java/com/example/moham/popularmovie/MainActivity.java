package com.example.moham.popularmovie;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moham.popularmovie.adapter.MoviesAdapter;
import com.example.moham.popularmovie.background.BackGroundTask;
import com.example.moham.popularmovie.connection.CheckConnection;
import com.example.moham.popularmovie.contentProvider.ContentProviderContract;
import com.example.moham.popularmovie.movieDpResponse.NetworkUtilies;
import com.example.moham.popularmovie.movie.MovieDetails;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnItemClick,BackGroundTask.JsonRespons{

    private RecyclerView movies;
    private MoviesAdapter adapter;
    private final int numberOfColumns=2;
    private String tag= POPULAR_TAG;
    public static final String POPULAR_TAG ="popular";
    public static final String MOSTRATED_TAG ="mostrated";
    public static final String SQLITE_TAG="sqlite";
    public static final String MOVIE_INTENT_KEY ="movie";
    private final String GRIDE_POSITION="position";
    private String TAG_KEY="tagkey";
    GridLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutManager = new GridLayoutManager(this, numberOfColumns);


        if (savedInstanceState!=null) {
            layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(GRIDE_POSITION));
            tag=savedInstanceState.getString(TAG_KEY);
        }


     if (!NetworkUtilies.API_KEY.isEmpty())
     {
         if (CheckConnection.isOnline(this)) {
           if (tag.equals(SQLITE_TAG)) {
              Cursor cursor = getContentResolver().query(ContentProviderContract.FavouriteEntry.CONTENT_URI
                        , null, null, null, null);
                setupMoviesGridshow(cursor);

          }
          if (tag.equals(POPULAR_TAG))
                BackGroundTask.MovieDpresponse(NetworkUtilies.SORT_POPULAR, this, NetworkUtilies.MOVIELISET_TAG);

          if (tag.equals(MOSTRATED_TAG))
               BackGroundTask.MovieDpresponse(NetworkUtilies.SORT_TOPRATED, this, NetworkUtilies.MOVIELISET_TAG);
     }

         else Toast.makeText(this, R.string.noconnectionmessage,Toast.LENGTH_LONG).show();


     }else Toast.makeText(this,"Please add your api key and try again ",Toast.LENGTH_LONG).show();



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tag.equals(SQLITE_TAG)) {
            Cursor cursor = getContentResolver().query(ContentProviderContract.FavouriteEntry.CONTENT_URI
                    , null, null, null, null);
            setupMoviesGridshow(cursor);

        }

    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(GRIDE_POSITION,layoutManager.onSaveInstanceState());
        outState.putString(TAG_KEY,tag);
    }

    private void setupMoviesGridshow(String json)
    {

            movies = findViewById(R.id.rv_movieslist);
            adapter = new MoviesAdapter(this, json,this, POPULAR_TAG);

            movies.setLayoutManager(layoutManager);
            movies.setAdapter(adapter);


    }

    private void setupMoviesGridshow(Cursor data)
    {

        movies = findViewById(R.id.rv_movieslist);
        adapter = new MoviesAdapter(this, data,this,SQLITE_TAG);
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        movies.setLayoutManager(layoutManager);
        movies.setAdapter(adapter);


    }


    @Override
    public void onClick(MovieDetails details) {

        Intent intent=new Intent(MainActivity.this,MovieDetailsActivity.class);
       intent.putExtra(MOVIE_INTENT_KEY,details);
       startActivity(intent);
    }


    @Override
    public void response(String jsonString) {


        if (jsonString!=null) {
                setupMoviesGridshow(jsonString);


        }
            else
            Toast.makeText(this,"No response from server pls check your api key and internet connection",Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        switch (id) {
            case R.id.menu_popular:
                BackGroundTask.MovieDpresponse(NetworkUtilies.SORT_POPULAR,this,NetworkUtilies.MOVIELISET_TAG);
                tag= POPULAR_TAG;
                break;
            case R.id.menu_rate:
                BackGroundTask.MovieDpresponse(NetworkUtilies.SORT_TOPRATED,this,NetworkUtilies.MOVIELISET_TAG);
                tag= MOSTRATED_TAG;
                break;

            case R.id.menu_favourite:
                Cursor cursor= getContentResolver().query(ContentProviderContract.FavouriteEntry.CONTENT_URI
                        ,null,null,null,null);
                setupMoviesGridshow(cursor);
                tag=SQLITE_TAG;
        }

        return super.onOptionsItemSelected(item);
    }
}
