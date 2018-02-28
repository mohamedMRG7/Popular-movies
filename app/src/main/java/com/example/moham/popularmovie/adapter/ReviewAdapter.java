package com.example.moham.popularmovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moham.popularmovie.R;
import com.example.moham.popularmovie.movieDpResponse.JsonUtilies;
import com.example.moham.popularmovie.movie.MovieDetails;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by moham on 2/27/2018.
 */

public class ReviewAdapter  extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>{

    String jsonString;

    public ReviewAdapter(String jsonString) {
        this.jsonString = jsonString;
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.rv_review_item,parent,false);

        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {

        MovieDetails details= JsonUtilies.getMovieReviews(jsonString,position);
        holder.reviewerName.setText(details.getReviewrName());
        holder.review.setText(details.getReview());

    }

    @Override
    public int getItemCount() {
        return JsonUtilies.getReviwsCount(jsonString);
    }

    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.tv_reviewrName)TextView reviewerName;
    @BindView(R.id.tv_review)TextView review;
    public ReviewAdapterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
}
