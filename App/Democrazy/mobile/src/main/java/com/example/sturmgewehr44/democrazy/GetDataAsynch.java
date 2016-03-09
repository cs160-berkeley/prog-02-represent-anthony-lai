package com.example.sturmgewehr44.democrazy;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedInputStream;
import android.util.Log;

/**
 * Created by Sturmgewehr44 on 3/7/16.
 */
public class GetDataAsynch  extends AsyncTask<String, Void, InputStream> {

    private Exception exception;

    protected void onPreExecute() {
        System.out.println("starting");
    }

    protected InputStream doInBackground(String... zip) {
        System.out.println(zip);
        if (zip.length == 1) {
            try {
                zip[0] = "94704";
                URL url = new URL("https://congress.api.sunlightfoundation.com/legislators/locate?zip="+ zip[0] +"&apikey=a96714973c0748038c1b2e35ebdc690a");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                return new BufferedInputStream(urlConnection.getInputStream());
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        } else {
            try {
                URL url = new URL("https://congress.api.sunlightfoundation.com/legislators/locate?latitude=" + zip[0] + "&longitude="+ zip[1] +"&apikey=a96714973c0748038c1b2e35ebdc690a");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                return new BufferedInputStream(urlConnection.getInputStream());
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        System.out.println("done");
    }
}
