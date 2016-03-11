package com.example.sturmgewehr44.democrazy;

import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedInputStream;
import android.util.Log;

/**
 * Created by Sturmgewehr44 on 3/7/16.
 */
public class GetDataAsynch  extends AsyncTask<String, Void, InputStream> {

    protected void onPreExecute() {
        System.out.println("starting");
    }

    protected InputStream doInBackground(String... zip) {
        System.out.println(zip);
            try {
                URL url = new URL(zip[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                return new BufferedInputStream(urlConnection.getInputStream());
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        System.out.println("done");
    }
}
