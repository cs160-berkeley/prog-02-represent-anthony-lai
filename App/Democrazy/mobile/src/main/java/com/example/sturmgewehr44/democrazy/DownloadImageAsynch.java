package com.example.sturmgewehr44.democrazy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sturmgewehr44 on 3/10/16.
 */
public class DownloadImageAsynch extends AsyncTask<String, Void, Bitmap> {
    ImageButton bmImage;

//    public DownloadImageTask(ImageButton bmImage) {
//        this.bmImage = bmImage;
//    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
//        bmImage.setImageBitmap(result);
    }

    private Exception exception;

//    protected void onPreExecute() {
//        System.out.println("starting");
//    }
//
//
//    protected void onPostExecute(String response) {
//        if(response == null) {
//            response = "THERE WAS AN ERROR";
//        }
//        System.out.println("done");
//    }
}


//new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
//        .execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");
//
//public void onClick(View v) {
//        startActivity(new Intent(this, IndexActivity.class));
//        finish();
//
//        }
