package com.example.sturmgewehr44.democrazy;

import android.content.Intent;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class PhoneListenerService extends WearableListenerService {

    private static final String START = "/START";
    private static final String CASE = "/CASE";
    private static boolean shake;
    private HashMap<String, String> builds = new HashMap<String, String>();
    private String zip = "95758";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if(messageEvent.getPath().equalsIgnoreCase( START )) {
            Intent intent;
            if (shake) {
                looper();
            } else {
                intent = new Intent(this, DetailedViewMobile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                for (String key: builds.keySet()) {
                    intent.putExtra(key, builds.get(key));
                }
                startActivity(intent);
            }

        } else if (messageEvent.getPath().equalsIgnoreCase(CASE)) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            if (value.equals("FACE")) {
                shake = false;
            } else {
                shake = true;
            }
        } else {
            String key = messageEvent.getPath();
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            builds.put(key, value);
        }

    }

    public void looper() {
        Random rand = new Random();
        int value = rand.nextInt(99999);
        zip = Integer.toString(value);
        pullData(zip);
    }

    public void pullData(String zipcode) {
        try {
            InputStream in = new GetDataAsynch().execute("https://congress.api.sunlightfoundation.com/legislators/locate?zip="+ zipcode +"&apikey=a96714973c0748038c1b2e35ebdc690a").get();
            readJsonStream(in);
            return;
        } catch(java.net.MalformedURLException error) {
            System.out.println("a");
        } catch(java.io.IOException error) {
            System.out.println("b");
        } catch(java.lang.InterruptedException error) {
            System.out.println("c");
        } catch(java.util.concurrent.ExecutionException error) {
            System.out.println("d");
        }
    }

    public void readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public void readMessagesArray(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            System.out.println(name);
            if (name.equals("results")) {
                reader.skipValue();
            } else if (name.equals("count")) {
                int cases = reader.nextInt();
                if (cases == 0) {
                    looper();
                } else {
                    Intent toCongressionalViewIntent = new Intent(getBaseContext(), CongressionalViewMobile.class);
                    toCongressionalViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    toCongressionalViewIntent.putExtra("TYPE", "ZIP");
                    toCongressionalViewIntent.putExtra("ZIPCODE", zip);
                    startActivity(toCongressionalViewIntent);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }
}
