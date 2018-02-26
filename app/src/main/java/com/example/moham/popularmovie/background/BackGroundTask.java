package com.example.moham.popularmovie.background;

import android.os.AsyncTask;

import com.example.moham.popularmovie.dp_response.NetworkUtilies;

/**
 * Created by moham on 2/26/2018.
 */

public class BackGroundTask {

    public static void MovieDpresponse(String sort ,JsonRespons jsonRespons)
    {
       new Response(jsonRespons).execute(sort);
    }


    static class Response extends AsyncTask<String ,Void,String>
    {
        JsonRespons jsonRespons;

        public Response(JsonRespons jsonRespons) {
            this.jsonRespons = jsonRespons;
        }

        @Override
        protected String doInBackground(String... strings) {
            String sort=strings[0];
            String jsonresponse= NetworkUtilies.getConnectonResponse(sort);

            if (jsonresponse!=null)
                return jsonresponse;
            else
                return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            jsonRespons.response(s);

        }
    }


    public  interface JsonRespons
    {
        void response(String jsonString);
    }
}
