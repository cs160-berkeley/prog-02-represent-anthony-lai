package com.example.sturmgewehr44.democrazy;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonToken;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import android.util.JsonReader;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class CongressionalViewMobile extends AppCompatActivity implements LocationListener {

    private ImageButton msen1Button;
    private ImageButton msen2Button;
    private ImageButton msen3Button;
    private ImageButton msen4Button;
    private ImageButton msen5Button;
    int cases;
    private GoogleApiClient mGoogleApiClient;
    Double latitude = 0.0;
    Double longitude = 0.0;
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    int district;
    String state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view_mobile);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            cases = Integer.parseInt(extras.getString("cases"));
            TextView header = (TextView) findViewById(R.id.header);
            if (extras.getString("TYPE").equals("ZIP")) {
                String title = "Congressmen for ";
                title += extras.getString("ZIPCODE");
                header.setText(title);
                pullData(title);
            } else {
                String title = extras.getString("state");
                title += "'s ";
                title += "239th ";
                title += "district";
                header.setText(title);
                cases = 1;
                mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }



//            ((TextView) findViewById(R.id.sen1)).setText(extras.getString("sen1"));
//            ((TextView) findViewById(R.id.par1)).setText(extras.getString("par1"));
            ((TextView) findViewById(R.id.ema1)).setText(extras.getString("ema1"));
            ((TextView) findViewById(R.id.web1)).setText(extras.getString("web1"));
            ((TextView) findViewById(R.id.twe1)).setText(extras.getString("twe1"));
            ((TextView) findViewById(R.id.twi1)).setText(extras.getString("twi1"));
//            ((ImageButton) findViewById(R.id.sen1Image)).setBackground(getBackgroundColor(extras.getString("par1")));

//            ((TextView) findViewById(R.id.sen2)).setText(extras.getString("sen2"));
//            ((TextView) findViewById(R.id.par2)).setText(extras.getString("par2"));
            ((TextView) findViewById(R.id.ema2)).setText(extras.getString("ema2"));
            ((TextView) findViewById(R.id.web2)).setText(extras.getString("web2"));
            ((TextView) findViewById(R.id.twe2)).setText(extras.getString("twe2"));
            ((TextView) findViewById(R.id.twi2)).setText(extras.getString("twi2"));
//            ((ImageButton) findViewById(R.id.sen2image)).setBackground(getBackgroundColor(extras.getString("par2")));

//            ((TextView) findViewById(R.id.sen3)).setText(extras.getString("sen3"));
//            ((TextView) findViewById(R.id.par3)).setText(extras.getString("par3"));
            ((TextView) findViewById(R.id.ema3)).setText(extras.getString("ema3"));
            ((TextView) findViewById(R.id.web3)).setText(extras.getString("web3"));
            ((TextView) findViewById(R.id.twe3)).setText(extras.getString("twe3"));
            ((TextView) findViewById(R.id.twi3)).setText(extras.getString("twi3"));
//            ((ImageButton) findViewById(R.id.sen3image)).setBackground(getBackgroundColor(extras.getString("par3")));

            if (cases >= 2) {
                ((TextView) findViewById(R.id.sen4)).setText(extras.getString("sen4"));
                ((TextView) findViewById(R.id.par4)).setText(extras.getString("par4"));
                ((TextView) findViewById(R.id.ema4)).setText(extras.getString("ema4"));
                ((TextView) findViewById(R.id.web4)).setText(extras.getString("web4"));
                ((TextView) findViewById(R.id.twe4)).setText(extras.getString("twe4"));
                ((TextView) findViewById(R.id.twi4)).setText(extras.getString("twi4"));
                ((ImageButton) findViewById(R.id.sen4image)).setBackground(getBackgroundColor(extras.getString("par4")));
            } else {
                ((LinearLayout) findViewById(R.id.case2)).setVisibility(View.GONE);
            }
            if (cases >= 3) {
                ((TextView) findViewById(R.id.sen5)).setText(extras.getString("sen5"));
                ((TextView) findViewById(R.id.par5)).setText(extras.getString("par5"));
                ((TextView) findViewById(R.id.ema5)).setText(extras.getString("ema5"));
                ((TextView) findViewById(R.id.web5)).setText(extras.getString("web5"));
                ((TextView) findViewById(R.id.twe5)).setText(extras.getString("twe5"));
                ((TextView) findViewById(R.id.twi5)).setText(extras.getString("twi5"));
                ((ImageButton) findViewById(R.id.sen5image)).setBackground(getBackgroundColor(extras.getString("par5")));

            } else {
                ((LinearLayout) findViewById(R.id.case3)).setVisibility(View.GONE);
            }
        } else {
        }
        msen1Button = (ImageButton) findViewById(R.id.sen1Image);
        msen2Button = (ImageButton) findViewById(R.id.sen2image);
        msen3Button = (ImageButton) findViewById(R.id.sen3image);
        msen4Button = (ImageButton) findViewById(R.id.sen4image);
        msen5Button = (ImageButton) findViewById(R.id.sen5image);


        msen1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detIntent = new Intent(getBaseContext(), DetailedViewMobile.class);
                detIntent.putExtras(intent.getExtras());
                detIntent.putExtra("VALUE", "1");
                detIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(detIntent);
            }
        });

        msen2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detIntent = new Intent(getBaseContext(), DetailedViewMobile.class);
                detIntent.putExtras(intent.getExtras());
                detIntent.putExtra("VALUE", "2");
                detIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(detIntent);
            }
        });


        msen3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detIntent = new Intent(getBaseContext(), DetailedViewMobile.class);
                detIntent.putExtras(intent.getExtras());
                detIntent.putExtra("VALUE", "3");
                detIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(detIntent);
            }
        });

        msen4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detIntent = new Intent(getBaseContext(), DetailedViewMobile.class);
                detIntent.putExtras(intent.getExtras());
                detIntent.putExtra("VALUE", "4");
                detIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(detIntent);
            }
        });

        msen5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detIntent = new Intent(getBaseContext(), DetailedViewMobile.class);
                detIntent.putExtras(intent.getExtras());
                detIntent.putExtra("VALUE", "5");
                detIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(detIntent);
            }
        });

    }

    public void pullData(String zipcode) {
        try {
            InputStream in = new GetDataAsynch().execute(zipcode).get();
            readJsonStream(in);
        } catch(java.net.MalformedURLException error) {
            System.out.println("fucka");
        } catch(java.io.IOException error) {
            System.out.println("fuckb");
        } catch(java.lang.InterruptedException error) {
            System.out.println("c");
        } catch(java.util.concurrent.ExecutionException error) {
            System.out.println("d");
        }
    }

    public void pullData() {
        try {
            InputStream in = new GetDataAsynch().execute(Double.toString(latitude), Double.toString(longitude)).get();
            readJsonStream(in);
        } catch(java.net.MalformedURLException error) {
            System.out.println("fucka");
        } catch(java.io.IOException error) {
            System.out.println("fuckb");
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
            return;
        } finally {
            reader.close();
        }}

    public void readMessagesArray(JsonReader reader) throws IOException {
        System.out.println(reader);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            System.out.println(name);
            if (name.equals("results")) {
                reader.beginArray();
                int i = 1;
                int j = 1;
                String first = "";
                String last = "";
                String chamber = "";
                String party = "";
                while (reader.hasNext()) {
                    reader.beginObject();
                    while(reader.hasNext()) {
                        String field = reader.nextName();
                        System.out.println(field);
                        if (field.equals("chamber")) {
                            chamber = reader.nextString();
                        } else if (field.equals("first_name")) {
                            first = reader.nextString();
                        } else if (field.equals("last_name")) {
                            last = reader.nextString();
                        } else if (field.equals("party")) {
                            String abrev = reader.nextString();
                            switch (abrev) {
                                case "D":   party = "Democratic Party";
                                            break;
                                case "R":   party = "Republican Party";
                                            break;
                                default:    party = "Independant";
                                            break;
                            }
                        }

                        else if (field.equals("fec_ids")) {
                            reader.beginArray();
                            while (reader.hasNext()) {
                                System.out.println(reader.nextString());
                            }
                            reader.endArray();
                        } else if (field.equals("in_office")) {
                            reader.nextBoolean();
                        } else if (field.equals("votesmart_id")) {
                            reader.nextInt();
                        } else if (field.equals("district")) {
                            if (reader.peek() == JsonToken.NUMBER) {
                                district = reader.nextInt();
                            } else {
                                reader.nextNull();
                            }
                        } else {
                            if (reader.peek() == JsonToken.NULL) {
                                System.out.println("null");
                                reader.nextNull();
                            } else {
                                System.out.println(reader.nextString());
                            }

                        }
                    }
                    reader.endObject();

                    System.out.println(i);
                    System.out.println(i);
                    if (chamber.equals("senate")) {
                        if (i == 1) {
                            ((TextView) findViewById(R.id.sen1)).setText(first + " " + last);
                            ((TextView) findViewById(R.id.par1)).setText(party);
                            ((ImageButton) findViewById(R.id.sen1Image)).setBackground(getBackgroundColor(party));
                        } else {
                            ((TextView) findViewById(R.id.sen2)).setText(first + " " + last);
                            ((TextView) findViewById(R.id.par2)).setText(party);
                            ((ImageButton) findViewById(R.id.sen2image)).setBackground(getBackgroundColor(party));
                        }
                        i++;
                    } else {
                        if (j == 1) {
                            ((TextView) findViewById(R.id.sen3)).setText(first + " " + last);
                            ((TextView) findViewById(R.id.par3)).setText(party);
                            ((ImageButton) findViewById(R.id.sen3image)).setBackground(getBackgroundColor(party));
                        } else if (j == 2) {
                            ((TextView) findViewById(R.id.sen4)).setText(first + " " + last);
                            ((TextView) findViewById(R.id.par4)).setText(party);
                            ((ImageButton) findViewById(R.id.sen4image)).setBackground(getBackgroundColor(party));
                        }
                        j++;
                    }

                }
                reader.endArray();
            } else if (name.equals("count")) {
                cases = reader.nextInt();
                System.out.println(cases);
            } else if (name.equals("page")) {
                reader.beginObject();
                while (reader.hasNext()) {
                    reader.nextName();
                    reader.nextInt();
                }
                reader.endObject();
            }
        }
        reader.endObject();
        return;
    }

//    public void readMessage(JsonReader reader) throws IOException {
//        long id = -1;
//        String text = null;
////        User user = null;
//        List geo = null;
//        int count = -1;
//
//        reader.beginObject();
//        while (reader.hasNext()) {
//            String name = reader.nextName();
//            if (name.equals("count")) {
//                count = reader.nextInt();
//                System.out.println(count);
//                System.out.println("success");
//            } else if (name.equals("text")) {
//                text = reader.nextString();
//            } else if (name.equals("geo") && reader.peek() != JsonToken.NULL) {
//                geo = readDoublesArray(reader);
//            } else if (name.equals("user")) {
//                readSenator(reader);
//            } else {
//                reader.skipValue();
//            }
//        }
//        reader.endObject();
//        return;
//    }

//    public List readDoublesArray(JsonReader reader) throws IOException {
//        List doubles = new ArrayList();
//
//        reader.beginArray();
//        while (reader.hasNext()) {
//            doubles.add(reader.nextDouble());
//        }
//        reader.endArray();
//        return doubles;
//    }

//    public void readSenator(JsonReader reader) throws IOException {
//        String name = null;
//        reader.beginObject();
//        while (reader.hasNext()) {
//            String field = reader.nextName();
////            if name.equals("")
//        }
//    }

    public Drawable getBackgroundColor(String party) {
        switch (party) {
            case "Democratic Party": return (Drawable) new ColorDrawable(0xFFB0CEFF);
            case "Communist Party": return (Drawable) new ColorDrawable(0xFF970018);
            case "Republican Party": return (Drawable) new ColorDrawable(0xFFFFB6B6);
            case "National Socialist Party": return (Drawable) new ColorDrawable(0xFF000000);
            case "Conservative Party": return (Drawable) new ColorDrawable(0xFF0087DC);
        }
        return new ColorDrawable();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (Math.abs(latitude - location.getLatitude()) > .06 && Math.abs(longitude - location.getLongitude()) > .06) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            System.out.println(latitude);
            System.out.println(longitude);
            pullData();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String Provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

}
