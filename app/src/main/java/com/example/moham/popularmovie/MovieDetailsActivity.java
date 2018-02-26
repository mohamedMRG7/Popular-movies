package com.example.moham.popularmovie;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moham.popularmovie.movie.MovieDetails;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {


    @BindView(R.id.tv_overview) TextView overView;
    @BindView(R.id.tv_rating) TextView userRating;
    @BindView(R.id.tv_releasedate)TextView date;
    @BindView(R.id.img_moiveposter)ImageView poster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);
        MovieDetails details= (MovieDetails) getIntent().getParcelableExtra(MainActivity.MOVIE_INTENT_KEY);
        setTitle(details.getTitle());

        overView.setText(details.getOverview());

        userRating.setText(details.getUser_rating());

        date.setText(details.getDate());

        Picasso.with(this).load(details.getPoster()).into(poster);
    }
}
