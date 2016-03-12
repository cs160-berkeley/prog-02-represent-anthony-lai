package com.example.sturmgewehr44.democrazy;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import java.util.ArrayList;
import java.util.List;

public class WatchToPhoneService extends Service {

    private GoogleApiClient mWatchApiClient;
    private List<Node> nodes = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mWatchApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                })
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWatchApiClient.disconnect();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Bundle extras = intent.getExtras();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mWatchApiClient.connect();
                System.out.println("sending");
                System.out.println(extras.getString("/CASE"));
                if (extras.getString("/CASE").equals("FACE")) {
                    sendMessage("/CASE", "FACE");
                    SystemClock.sleep(500);
                    sendMessage("NAME", extras.getString("sen"));
                    sendMessage("PARTY", extras.getString("par"));
                    sendMessage("VALUE", extras.getString("VALUE"));
                    sendMessage("END", extras.getString("END"));
                    sendMessage("ROLE", extras.getString("ROLE"));
                    sendMessage("HANDLE", extras.getString("HANDLE"));
                    SystemClock.sleep(500);
                    sendMessage("/START", "START");
                } else {
                    sendMessage("/CASE", "SHAKE");
                    SystemClock.sleep(500);
                    sendMessage("/START", "START");
                }
            }
        }).start();

        return START_STICKY;
    }


    private void sendMessage(final String path, final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mWatchApiClient).await();
                for (Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mWatchApiClient, node.getId(), path, text.getBytes()).await();
                }
            }
        }).start();

    }
}