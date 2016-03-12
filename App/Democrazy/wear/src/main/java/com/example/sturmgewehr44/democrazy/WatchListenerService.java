package com.example.sturmgewehr44.democrazy;

import android.content.Intent;
import android.util.Log;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Created by Sturmgewehr44 on 2/26/16.
 */


public class WatchListenerService extends WearableListenerService {
    private static final String START = "/START";
    private static final String FALL = "/case";
    private static HashMap<String, String> builds = new HashMap<String, String>();
    private static int Size = -1;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        if(messageEvent.getPath().equalsIgnoreCase( START )) {
            System.out.println(builds.size());
            Intent intent = new Intent(this, MainViewWatch.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            for (String key: builds.keySet()) {
                intent.putExtra(key, builds.get(key));
            }
            intent.putExtra("cases", Integer.toString(Size));
//            System.out.println("Builds size = " + Integer.toString(builds.size()));
            startActivity(intent);
        } else if (messageEvent.getPath().equalsIgnoreCase( FALL )) {
            Size = Integer.parseInt(new String(messageEvent.getData(), StandardCharsets.UTF_8));
        } else {
            String key = messageEvent.getPath();
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            builds.put(key, value);
        }

    }
}
