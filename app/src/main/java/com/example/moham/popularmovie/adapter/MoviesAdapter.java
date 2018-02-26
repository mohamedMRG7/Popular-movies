package com.example.moham.popularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moham.popularmovie.R;
import com.example.moham.popularmovie.dp_response.JsonUtilies;
import com.example.moham.popularmovie.movie.MovieDetails;
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

    public MoviesAdapter(OnItemClick OnItemClick, String jsonString, Context context) {
        this.OnItemClick = OnItemClick;
        this.jsonString =jsonString;
        this.context=context;
    }

    @Override
    public MovieadapterViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.rv_movie_item,parent,false);

        return new MovieadapterViewholder(view);
    }

    @Override
    public void onBindViewHolder(MovieadapterViewholder holder, int position) {

        MovieDetails details= JsonUtilies.getmovieDetails(jsonString,position);

        Picasso.with(context).load(details.getPoster()).into(holder.poster);
        holder.movieName.setText(details.getTitle());


    }

    @Override
    public int getItemCount() {
        return JsonUtilies.getmoviescount(jsonString);
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
            OnItemClick.onClick(positon);

        }
    }



   public interface OnItemClick
    {
         void onClick(int position);
    }

}
