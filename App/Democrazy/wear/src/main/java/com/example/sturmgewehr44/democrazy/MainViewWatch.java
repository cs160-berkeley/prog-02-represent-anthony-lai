package com.example.sturmgewehr44.democrazy;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.util.Random;
import android.app.FragmentManager;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;
import android.support.wearable.view.GridViewPager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;


public class MainViewWatch extends AppCompatActivity implements SensorEventListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
//    private static final String TWITTER_KEY = "If5CSwJEhO6mThm2B9p91Iq0F";
//    private static final String TWITTER_SECRET = "9QlTmQYUuTPiYCl8PZEYXTcMziaEaWHvu1NH0uKN5Zd4G4xu6Z";


    private Context ctx;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float current = 0.0F;
    private String zipcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main_view_watch);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        ctx = this;
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                int cases = 0;
                if (extras != null) {
                    cases = Integer.parseInt(extras.getString("cases"));
                    System.out.println(cases);
                    System.out.println(cases);
                    System.out.println(cases);
                } else {
                    System.out.println("first");
                }
                final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
                FragmentManager fragmentManager = getFragmentManager();
                GridPagerAdapter gpa = new GridPagerAdapter(ctx, fragmentManager);
                if (cases != 0) {
                    ArrayList<String> data = new ArrayList<String>();
                    for (int i = 0; i < cases + 2; i++) {
                        data.add(extras.getString("sen" + Integer.toString(i + 1)));
                        data.add(extras.getString("par" + Integer.toString(i + 1)));
                        data.add(Integer.toString(i + 1));
                        data.add(extras.getString("VALUE" + Integer.toString(i + 1)));
                        data.add(extras.getString("HANDLE" + Integer.toString(i + 1)));
                        data.add(extras.getString("ROLE" + Integer.toString(i + 1)));
                        data.add(extras.getString("END" + Integer.toString(i + 1)));
                    }
                    for (String a: data) {
                        System.out.println(a);
                    }
                    zipcode = extras.getString("ZIPCODE");
                    data.add(zipcode);
                    data.add(extras.getString("stateshort"));
                    data.add(extras.getString("county"));
                    data.add(extras.getString("obama"));
                    data.add(extras.getString("romney"));
                    gpa.overridePages(cases + 2, data);
                }
                pager.setAdapter(gpa);
                mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            }
        });
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        if (mAccelerometer != null) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            if (x + y + z != current && x + y + z > 1000) {
                if (zipcode != null) {
                    Intent shake = new Intent(getBaseContext(), WatchToPhoneService.class);
                    shake.putExtra("/CASE", "SHAKE");
                    startService(shake);
                }
                current = x + y + z;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager =  (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
