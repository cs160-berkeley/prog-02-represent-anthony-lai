package com.example.sturmgewehr44.democrazy;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Sturmgewehr44 on 2/26/16.
 */
public class PhoneToWatchService extends Service {

    private GoogleApiClient mApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
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
        mApiClient.disconnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Bundle extras = intent.getExtras();
        final String fall = extras.getString("cases"); //used to be from 1 to 3
        final int num = Integer.parseInt(fall);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mApiClient.connect();
                sendMessage("/case", fall);
//                sendMessage("ZIPCODE", extras.getString("ZIPCODE"));
                sendMessage("ZIPCODE", "00000");

                sendMessage("stateshort", extras.getString("stateshort"));
                sendMessage("county", extras.getString("county"));
                sendMessage("obama", extras.getString("obama"));
                sendMessage("romney", extras.getString("romney"));

                for (int indice = 0;  indice < num + 2; indice++) {
//                    System.out.println(indice + 1);
//                    String a = extras.getString("sen" + Integer.toString(indice + 1));
//                    System.out.println(a);
//                    byte[] a1 = a.getBytes();
//                    String b = extras.getString("par" + Integer.toString(indice + 1));
//                    System.out.println(b);
//                    byte[] b1 = b.getBytes();
//                    String c = extras.getString("VALUE" + Integer.toString(indice + 1));
//                    System.out.println(c);
//                    byte[] c1 = c.getBytes();
//                    String d = extras.getString("HANDLE" + Integer.toString(indice + 1));
//                    System.out.println(d);
//                    byte[] d1 = d.getBytes();
//                    String e = extras.getString("ROLE" + Integer.toString(indice + 1));
//                    System.out.println(e);
//                    byte[] e1 = e.getBytes();
//                    String f = extras.getString("END" + Integer.toString(indice + 1));
//                    System.out.println(f);
//                    byte[] f1 = f.getBytes();
                    sendMessage("sen" + Integer.toString(indice + 1), extras.getString("sen" + Integer.toString(indice + 1)));
                    sendMessage("par" + Integer.toString(indice + 1), extras.getString("par" + Integer.toString(indice + 1)));
                    sendMessage("VALUE" + Integer.toString(indice + 1), extras.getString("VALUE" + Integer.toString(indice + 1)));
                    sendMessage("HANDLE" + Integer.toString(indice + 1), extras.getString("HANDLE" + Integer.toString(indice + 1)));
                    sendMessage("ROLE" + Integer.toString(indice + 1), extras.getString("ROLE" + Integer.toString(indice + 1)));
                    sendMessage("END" + Integer.toString(indice + 1), extras.getString("END" + Integer.toString(indice + 1)));
//                    sendMessage("VALUE" + Integer.toString(indice + 1), "");
//                    sendMessage("HANDLE" + Integer.toString(indice + 1), "");
//                    sendMessage("ROLE" + Integer.toString(indice + 1), "");
//                    sendMessage("END" + Integer.toString(indice + 1), "");
                }
                SystemClock.sleep(500);
                sendMessage("/START", "START");
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendMessage( final String path, final String text ) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), path, text.getBytes() ).await();
                }
            }
        }).start();
    }


}
