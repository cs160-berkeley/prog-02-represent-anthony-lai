package com.example.sturmgewehr44.democrazy;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.ArrayList;
import java.util.HashMap;

import java.nio.charset.StandardCharsets;

public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String TOAST = "/send_toast";
    private static final String START = "/START";
    private static final String CASE = "/CASE";
    private static boolean shake;
    private HashMap<String, String> builds = new HashMap<String, String>();

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if(messageEvent.getPath().equalsIgnoreCase( START )) {
            Intent intent;
            if (shake) {
                System.out.println("shake");
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                intent = new Intent(this, CongressionalViewMobile.class);
                sendIntent.putExtra("TYPE", "ZIP");
                String zipcode = builds.get("ZIPCODE");
                ArrayList<String> info = findData(zipcode);
                sendIntent.putExtra("ZIPCODE", zipcode);
                int cases = Integer.parseInt(info.get(1));
                sendIntent.putExtra("cases", info.get(1));
                sendIntent.putExtra("state", info.get(2));
                sendIntent.putExtra("sen1", info.get(3));
                sendIntent.putExtra("par1", info.get(4));
                sendIntent.putExtra("ema1", info.get(5));
                sendIntent.putExtra("web1", info.get(6));
                sendIntent.putExtra("twe1", info.get(7));
                sendIntent.putExtra("twi1", info.get(8));

                sendIntent.putExtra("sen2", info.get(9));
                sendIntent.putExtra("par2", info.get(10));
                sendIntent.putExtra("ema2", info.get(11));
                sendIntent.putExtra("web2", info.get(12));
                sendIntent.putExtra("twe2", info.get(13));
                sendIntent.putExtra("twi2", info.get(14));

                for (int i = 0; i < cases; i++) {
                    sendIntent.putExtra("sen" + Integer.toString(i + 3), info.get((i + 2) * 6 + 3));
                    sendIntent.putExtra("par" + Integer.toString(i + 3), info.get((i + 2) * 6 + 4));
                    sendIntent.putExtra("ema" + Integer.toString(i + 3), info.get((i + 2) * 6 + 5));
                    sendIntent.putExtra("web" + Integer.toString(i + 3), info.get((i + 2) * 6 + 6));
                    sendIntent.putExtra("twe" + Integer.toString(i + 3), info.get((i + 2) * 6 + 7));
                    sendIntent.putExtra("twi" + Integer.toString(i + 3), info.get((i + 2) * 6 + 8));
                }
                startService(sendIntent);
                Intent toCongressionalViewIntent = new Intent(getBaseContext(), CongressionalViewMobile.class);
                toCongressionalViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                toCongressionalViewIntent.putExtras(sendIntent.getExtras());
                startActivity(toCongressionalViewIntent);
            } else {
                System.out.println("face");
                intent = new Intent(this, DetailedViewMobile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                for (String key: builds.keySet()) {
                    intent.putExtra(key, builds.get(key));
                }
                intent.putExtra("cases", Integer.toString(builds.size() / 2 - 2));
                startActivity(intent);
            }

        } else if (messageEvent.getPath().equalsIgnoreCase(CASE)) {
            System.out.println("case");
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

    public ArrayList<String> findData (String zip) {
        ArrayList<String> info = new ArrayList<String>();
        info.add(zip);
        info.add("3");
        info.add("California");

        if (Integer.parseInt(zip) % 2 == 0) {
            info.add("Franklin D. Roosevelt");
            info.add("Democratic Party");
            info.add("roosevelt@senate.gov");
            info.add("newDeal.com");
            info.add("@NewDEALZ");
            info.add("@neversurrender tanks 4 cash brah?");

            info.add("Joseph Stalin");
            info.add("Communist Party");
            info.add("gulag@senate.gov");
            info.add("cccp.com");
            info.add("@not1stepback");
            info.add("@Poland sorry");
        } else {
            info.add("Joseph Stalin");
            info.add("Communist Party");
            info.add("gulag@senate.gov");
            info.add("cccp.com");
            info.add("@not1stepback");
            info.add("@Poland sorry");

            info.add("Franklin D. Roosevelt");
            info.add("Democratic Party");
            info.add("roosevelt@senate.gov");
            info.add("newDeal.com");
            info.add("@NewDEALZ");
            info.add("@neversurrender tanks 4 cash brah?");
        }
        info.add("Wendell Willkie");
        info.add("Republican Party");
        info.add("wellWill@senate.gov");
        info.add("WillkieForPresident.com");
        info.add("@ewNewDeal");
        info.add("Yeah let's just let them borrow tanks, I'm sure they'll be functional afterwards");

        info.add("Adolf Hitler");
        info.add("National Socialist Party");
        info.add("nazi@senate.gov");
        info.add("reich.com");
        info.add("@backstabber");
        info.add("@Poland sorry");

        info.add("Winston Churchill");
        info.add("Conservative Party");
        info.add("pm@senate.gov");
        info.add("ruleBritannia.com");
        info.add("@neverSurrender");
        info.add("@backstabber no Germany wat r u doing sthap. @poland sorry");
        return info;
    }
}
