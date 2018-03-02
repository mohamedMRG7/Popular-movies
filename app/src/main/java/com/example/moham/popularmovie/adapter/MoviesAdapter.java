package com.example.moham.popularmovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moham.popularmovie.MainActivity;
import com.example.moham.popularmovie.R;
import com.example.moham.popularmovie.movieDpResponse.JsonUtilies;
import com.example.moham.popularmovie.movie.MovieDetails;
import com.example.moham.popularmovie.sqliteDp.DpContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by moham on 2/19/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieadapterViewholder>{


    private OnItemClick OnItemClick;
    private String jsonString;
    private Context context;
    private String tag;
    private Cursor cursor;


    public MoviesAdapter(OnItemClick OnItemClick, String jsonString, Context context,String tag) {
        this.OnItemClick = OnItemClick;
        this.jsonString =jsonString;
        this.context=context;
        this.tag=tag;
    }

    public MoviesAdapter(MoviesAdapter.OnItemClick onItemClick, Cursor cursor, Context context,String tag) {
        OnItemClick = onItemClick;
        this.context = context;
        this.cursor = cursor;
        this.tag=tag;
    }

    @Override
    public MovieadapterViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.rv_movie_item,parent,false);

        return new MovieadapterViewholder(view);
    }

    @Override
    public void onBindViewHolder(MovieadapterViewholder holder, int position) {

        if (tag.equals(MainActivity.POPULAR_TAG)) {
            MovieDetails details = JsonUtilies.getmovieDetails(jsonString, position);
            Picasso.with(context).load(details.getPoster()).into(holder.poster);
            holder.movieName.setText(details.getTitle());
        }

        if (tag.equals(MainActivity.SQLITE_TAG))
        {

            cursor.moveToPosition(position);
            String poster=cursor.getString(cursor.getColumnIndex(DpContract.FavouriteDpEntry.COLUMN_POSTER));
            String title=cursor.getString(cursor.getColumnIndex(DpContract.FavouriteDpEntry.COLUMN_TITLE));
            Picasso.with(context).load(poster).into(holder.poster);
            holder.movieName.setText(title);
        }

    }

    @Override
    public int getItemCount() {
        if (tag.equals(MainActivity.POPULAR_TAG))
            return JsonUtilies.getMoviesCount(jsonString);

         if (tag.equals(MainActivity.SQLITE_TAG))
            return cursor.getCount();
    return 0;
    }

    class MovieadapterViewholder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        @BindView(R.id.img_moiveposter)ImageView poster;
        @BindView(R.id.tv_moviename)TextView movieName;

        public MovieadapterViewholder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int positon=getAdapterPosition();
            MovieDetails details =null;
            if (tag.equals(MainActivity.POPULAR_TAG)) {
                 details = JsonUtilies.getmovieDetails(jsonString, positon);
            }else if (tag.equals(MainActivity.SQLITE_TAG))
            {
                cursor.moveToPosition(positon);
                String title=cursor.getString(cursor.getColumnIndex(DpContract.FavouriteDpEntry.COLUMN_TITLE));
                String overview=cursor.getString(cursor.getColumnIndex(DpContract.FavouriteDpEntry.COLUMN_OVERVIEW));
                String movieid=cursor.getString(cursor.getColumnIndex(DpContract.FavouriteDpEntry.COLUMN_MOVIE_ID));
                String date=cursor.getString(cursor.getColumnIndex(DpContract.FavouriteDpEntry.COLUMN_DATE));
                String poster=cursor.getString(cursor.getColumnIndex(DpContract.FavouriteDpEntry.COLUMN_POSTER));
                String userRating=cursor.getString(cursor.getColumnIndex(DpContract.FavouriteDpEntry.COLUMN_USERRAITNG));
                details=new MovieDetails(title,poster,overview,userRating,date,movieid);

            }
            OnItemClick.onClick(details);

        }
    }



   public interface OnItemClick
    {
         void onClick(MovieDetails details);
    }

}
