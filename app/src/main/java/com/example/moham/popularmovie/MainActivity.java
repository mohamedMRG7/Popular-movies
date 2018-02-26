package com.example.moham.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moham.popularmovie.adapter.MoviesAdapter;
import com.example.moham.popularmovie.background.BackGroundTask;
import com.example.moham.popularmovie.dp_response.JsonUtilies;
import com.example.moham.popularmovie.dp_response.NetworkUtilies;
import com.example.moham.popularmovie.movie.MovieDetails;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnItemClick,BackGroundTask.JsonRespons{

    private RecyclerView movies;
    private MoviesAdapter adapter;
    private final int numberOfColumns=2;
    private String jsonString;
    public static final String MOVIE_INTENT_KEY ="movie";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



     if (!NetworkUtilies.API_KEY.isEmpty())
     {   if (isOnline())
         BackGroundTask.MovieDpresponse(NetworkUtilies.SORT_POPULAR, this);

        else Toast.makeText(this,"Please check your internet connecton and try again ",Toast.LENGTH_LONG).show();


     }else Toast.makeText(this,"Please add your api key and try again ",Toast.LENGTH_LONG).show();

    }


    private void setupMoviesGridshow(String json)
    {

            movies = findViewById(R.id.rv_movieslist);
            adapter = new MoviesAdapter(this, json,this);
            GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
            movies.setLayoutManager(layoutManager);
            movies.setAdapter(adapter);


    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    public void onClick(int position) {
        MovieDetails movieDetails= JsonUtilies.getmovieDetails(jsonString,position);
       Intent intent=new Intent(MainActivity.this,MovieDetailsActivity.class);
       intent.putExtra(MOVIE_INTENT_KEY,movieDetails);
       startActivity(intent);
    }


    @Override
    public void response(String jsonString) {


        if (jsonString!=null) {
                this.jsonString=jsonString;
                setupMoviesGridshow(jsonString);

        }
            else
            Toast.makeText(this,"No response from server pls check your api key",Toast.LENGTH_LONG).show();

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
                BackGroundTask.MovieDpresponse(NetworkUtilies.SORT_POPULAR,this);
                break;
            case R.id.menu_rate:
                BackGroundTask.MovieDpresponse(NetworkUtilies.SORT_TOPRATED,this);

        }

        return super.onOptionsItemSelected(item);
    }
}
