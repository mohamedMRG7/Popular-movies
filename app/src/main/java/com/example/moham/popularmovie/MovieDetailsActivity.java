package com.example.moham.popularmovie;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.popularmovie.adapter.ReviewAdapter;
import com.example.moham.popularmovie.adapter.TrialersAdapter;
import com.example.moham.popularmovie.background.BackGroundTask;
import com.example.moham.popularmovie.connection.CheckConnection;
import com.example.moham.popularmovie.contentProvider.ContentProviderContract;
import com.example.moham.popularmovie.movieDpResponse.NetworkUtilies;
import com.example.moham.popularmovie.movie.MovieDetails;
import com.example.moham.popularmovie.sqliteDp.DpContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity implements BackGroundTask.TrailersAndReviewsResponse {


    @BindView(R.id.tv_overview) TextView overView;
    @BindView(R.id.tv_rating) TextView userRating;
    @BindView(R.id.tv_releasedate)TextView date;
    @BindView(R.id.img_moiveposter)ImageView poster;
    @BindView(R.id.rv_trialers)RecyclerView trailersList;
    @BindView(R.id.rv_reviews)RecyclerView reviewsList;
    @BindView(R.id.trials)TextView trailertitle;
    @BindView(R.id.reviews)TextView reviewstitle;
    @BindView(R.id.sc_scroll) NestedScrollView scrollView;
    private  final String KEY_TRAILPOSITION ="trailerpos";
    private  final String KEY_REVIEWPOSITION ="reviewpos";
    private  final String KEY_SCROLLVIEWPOSITION ="scrollviewpos";
    private LinearLayoutManager trialLayoutManage;
    Parcelable position;
    private MovieDetails details;
    private LinearLayoutManager reviewLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        details=  getIntent().getParcelableExtra(MainActivity.MOVIE_INTENT_KEY);


        trialLayoutManage =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        reviewLayoutManager =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);


        if (savedInstanceState !=null)
        {
            trialLayoutManage.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_TRAILPOSITION));

            reviewLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_REVIEWPOSITION));

            scrollView.scrollTo(0,savedInstanceState.getInt(KEY_SCROLLVIEWPOSITION));
        }




        setTitle(details.getTitle());

        overView.setText(details.getOverview());

        userRating.setText(details.getUserRating());

        date.setText(details.getDate());

        Picasso.with(this).load(details.getPoster()).into(poster);

        if (CheckConnection.isOnline(this)) {
            BackGroundTask.MovieInfoResponse(details.getId(), this, NetworkUtilies.TRIALS_TAG);
            BackGroundTask.MovieInfoResponse(details.getId(),this,NetworkUtilies.REVIEWS_TAG);
        }
        else
        {
            trailertitle.setVisibility(View.INVISIBLE);
            reviewstitle.setVisibility(View.INVISIBLE);
            Toast.makeText(this,R.string.detailsnoconnectionmessage,Toast.LENGTH_LONG).show();
        }




    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Parcelable trailerpos=trialLayoutManage.onSaveInstanceState();
        Parcelable reviewpos=reviewLayoutManager.onSaveInstanceState();
        int scrollpos=scrollView.getVerticalScrollbarPosition();

        outState.putParcelable(KEY_TRAILPOSITION, trailerpos);
        outState.putParcelable(KEY_REVIEWPOSITION, reviewpos);
        outState.putInt(KEY_SCROLLVIEWPOSITION,scrollpos);
    }

    @Override
    protected void onResume() {
        super.onResume();




    }




    private void setUpTrailersRecyclerView(String jsonSting)
    {
        TrialersAdapter adapter=new TrialersAdapter(jsonSting,this);

        trailersList.setLayoutManager(trialLayoutManage);
        trailersList.setAdapter(adapter);

    }

    private void setUpReviewsRecyclerView(String jsonString)
    {
        ReviewAdapter adapter=new ReviewAdapter(jsonString);

        reviewsList.setLayoutManager(reviewLayoutManager);
        reviewsList.setAdapter(adapter);
    }

    @Override
    public void trailer(String jsonString) {
        if (jsonString!=null)
            setUpTrailersRecyclerView(jsonString);
    }

    @Override
    public void reviews(String jsonString) {

        if (jsonString!=null)
            setUpReviewsRecyclerView(jsonString);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.movidetailsmenu, menu);
        MenuItem item= menu.getItem(0);
        if (checkIfExist(details.getId()))
            item.setIcon(R.drawable.ic_fav_on);
        else
            item.setIcon(R.drawable.ic_fav_off);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.menu_favourite:
                if (checkIfExist(details.getId())) {
                  deleteMovieFromDp();
                  item.setIcon(R.drawable.ic_fav_off);
                }
                else {
                   addMovieToDb();
                   item.setIcon(R.drawable.ic_fav_on);
                }


        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkIfExist(String id)
    {
        Uri uri=ContentProviderContract.FavouriteEntry.CONTENT_URI.buildUpon().appendPath(id).build();
        Cursor cursor=getContentResolver().query(uri,null,null,null,null);
        if (cursor!=null)
             if (cursor.getCount()==0)
                 return false;
             else
                 return true;

        return false;
    }

    private void addMovieToDb()
    {
        ContentValues values = new ContentValues();
        values.put(DpContract.FavouriteDpEntry.COLUMN_TITLE, details.getTitle());
        values.put(DpContract.FavouriteDpEntry.COLUMN_DATE, details.getDate());
        values.put(DpContract.FavouriteDpEntry.COLUMN_OVERVIEW, details.getOverview());
        values.put(DpContract.FavouriteDpEntry.COLUMN_MOVIE_ID, details.getId());
        values.put(DpContract.FavouriteDpEntry.COLUMN_POSTER, details.getPoster());
        values.put(DpContract.FavouriteDpEntry.COLUMN_USERRAITNG, details.getUserRating());
        getContentResolver().insert(ContentProviderContract.FavouriteEntry.CONTENT_URI, values);
    }

    private void deleteMovieFromDp()
    {
        Uri uri=ContentProviderContract.FavouriteEntry.CONTENT_URI.buildUpon().appendPath(details.getId()).build();
        getContentResolver().delete(uri
                ,null,null);
    }
}
