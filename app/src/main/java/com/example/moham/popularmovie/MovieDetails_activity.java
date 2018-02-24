package com.example.moham.popularmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moham.popularmovie.movie.MovieDetails;
import com.squareup.picasso.Picasso;

public class MovieDetails_activity extends AppCompatActivity {

    private TextView overview,userRating,date;
    private ImageView poster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        MovieDetails details= (MovieDetails) getIntent().getSerializableExtra(MainActivity.MOVIE_INTENT_KEY);
        setTitle(details.getTitle());

        overview=findViewById(R.id.tv_overview);
        overview.setText(details.getOverview());

        userRating=findViewById(R.id.tv_rating);
        userRating.setText(details.getUser_rating());

        date=findViewById(R.id.tv_releasedate);
        date.setText(details.getDate());

        poster=findViewById(R.id.img_moiveposter);
        Picasso.with(this).load(details.getPoster()).into(poster);
    }
}
