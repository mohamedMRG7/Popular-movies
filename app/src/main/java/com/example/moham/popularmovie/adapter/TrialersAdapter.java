package com.example.moham.popularmovie.adapter;

import android.content.Context;
import android.content.Intent;
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

public class TrialersAdapter  extends RecyclerView.Adapter<TrialersAdapter.TrailerAdapterViewholder>{

    String jsonString;

    Context context;
    public TrialersAdapter(String jsonString,Context context) {
    this.jsonString=jsonString;
    this.context=context;
    }

    @Override
    public TrailerAdapterViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.rv_trialer_item,parent,false);

        return new TrailerAdapterViewholder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewholder holder, int position) {
       MovieDetails details=JsonUtilies.getMovieTrial(jsonString,position);
        holder.traierName.setText(details.getTrialName());

    }

    @Override
    public int getItemCount() {
        return JsonUtilies.getTrailersCount(jsonString);
    }



    class TrailerAdapterViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_trailername) TextView traierName;
        public TrailerAdapterViewholder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            MovieDetails details=JsonUtilies.getMovieTrial(jsonString,getAdapterPosition());
            Intent intent=new Intent(Intent.ACTION_VIEW,details.getTrialLinke());
            context.startActivity(intent);
        }
    }

}
