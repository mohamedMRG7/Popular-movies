package com.example.moham.popularmovie.background;

import android.os.AsyncTask;

import com.example.moham.popularmovie.movieDpResponse.NetworkUtilies;

/**
 * Created by moham on 2/26/2018.
 */

public class BackGroundTask {

    public static void MovieDpresponse(String sort ,JsonRespons jsonRespons,String tag)
    {
       new Response(jsonRespons,tag).execute(sort);
    }

    public static void MovieInfoResponse(String movieId ,TrailersAndReviewsResponse jsonRespons ,String tag)
    {
        new Response(jsonRespons,tag).execute(movieId);
    }

    static class Response extends AsyncTask<String ,Void,String>
    {
        JsonRespons jsonRespons;
        String tag;
        TrailersAndReviewsResponse trailersAndReviewsResponse;

        public Response(JsonRespons jsonRespons ,String tag) {
            this.jsonRespons = jsonRespons;
            this.tag=tag;
        }

        public Response( TrailersAndReviewsResponse trailersAndReviewsResponse,String tag) {
            this.tag = tag;
            this.trailersAndReviewsResponse = trailersAndReviewsResponse;
        }

        @Override
        protected String doInBackground(String... strings) {
            String sort=strings[0];
            String jsonresponse=null;

            if (tag.equals(NetworkUtilies.MOVIELISET_TAG)) {
                 jsonresponse = NetworkUtilies.getMoviesListResponse(sort);
            }else if (tag.equals(NetworkUtilies.TRIALS_TAG))
                jsonresponse=NetworkUtilies.getMovieTrialsResponse(sort,NetworkUtilies.TRIALS_PATH);
            else if (tag.equals(NetworkUtilies.REVIEWS_TAG))
                jsonresponse=NetworkUtilies.getMovieTrialsResponse(sort,NetworkUtilies.REVIEW_PATH);


            if (jsonresponse!=null)
                return jsonresponse;
            else
                return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (tag.equals(NetworkUtilies.MOVIELISET_TAG))
            jsonRespons.response(s);

            if (tag.equals(NetworkUtilies.TRIALS_TAG))
                trailersAndReviewsResponse.trailer(s);
            else if (tag.equals(NetworkUtilies.REVIEWS_TAG))
                trailersAndReviewsResponse.reviews(s);

        }
    }


    public  interface JsonRespons
    {
        void response(String jsonString);

    }
   public interface TrailersAndReviewsResponse
    {
        void trailer(String jsonString);
        void reviews(String jsonString);
    }
}
