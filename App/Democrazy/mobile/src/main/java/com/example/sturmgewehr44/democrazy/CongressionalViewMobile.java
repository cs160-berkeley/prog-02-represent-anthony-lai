package com.example.sturmgewehr44.democrazy;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import io.fabric.sdk.android.Fabric;

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
    private static final String TWITTER_KEY = "If5CSwJEhO6mThm2B9p91Iq0F";
    private static final String TWITTER_SECRET = "9QlTmQYUuTPiYCl8PZEYXTcMziaEaWHvu1NH0uKN5Zd4G4xu6Z";
    private String id1 = "";
    private String id2 = "";
    private String id3 = "";
    private String id4 = "";
    private String id5 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view_mobile);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        if (extras != null) {
            cases = Integer.parseInt(extras.getString("cases"));
            TextView header = (TextView) findViewById(R.id.header);
            if (extras.getString("TYPE").equals("ZIP")) {
                String title = "Congressmen for ";
                title += extras.getString("ZIPCODE");
                header.setText(title);
                pullData(extras.getString("ZIPCODE"));
            } else {
                cases = 1;
                mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
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
                if (id1.equals("")) {
                    return;
                }
                Intent detIntent = new Intent(getBaseContext(), DetailedViewMobile.class);
                detIntent.putExtras(intent.getExtras());
                detIntent.putExtra("VALUE", id1);
//                detIntent.putExtra("VALUE", "1");
                detIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(detIntent);
            }
        });

        msen2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id2.equals("")) {
                    return;
                }
                Intent detIntent = new Intent(getBaseContext(), DetailedViewMobile.class);
                detIntent.putExtras(intent.getExtras());
                detIntent.putExtra("VALUE", id2);
                detIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(detIntent);
            }
        });


        msen3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id3.equals("")) {
                    return;
                }
                Intent detIntent = new Intent(getBaseContext(), DetailedViewMobile.class);
                detIntent.putExtras(intent.getExtras());
                detIntent.putExtra("VALUE", id3);
                detIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(detIntent);
            }
        });

        msen4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id4.equals("")) {
                    return;
                }
                Intent detIntent = new Intent(getBaseContext(), DetailedViewMobile.class);
                detIntent.putExtras(intent.getExtras());
                detIntent.putExtra("VALUE", id4);
                detIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(detIntent);
            }
        });

        msen5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id5.equals("")) {
                    return;
                }
                Intent detIntent = new Intent(getBaseContext(), DetailedViewMobile.class);
                detIntent.putExtras(intent.getExtras());
                detIntent.putExtra("VALUE", id5);
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
                String email = "";
                String website = "";
                String handle = "";
                String id = "";
                while (reader.hasNext()) {
                    reader.beginObject();
                    while(reader.hasNext()) {
                        String field = reader.nextName();
                        System.out.println(field);
                        if (field.equals("chamber")) {
                            chamber = reader.nextString();
                        } else if (field.equals("bioguide_id")) {
                            id = reader.nextString();
                        }
                        else if (field.equals("first_name")) {
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
                        } else if (field.equals("oc_email")) {
                            email = reader.nextString();
                        } else if (field.equals("website")) {
                            website = reader.nextString();
                        } else if (field.equals("twitter_id")) {
                            handle = reader.nextString();
                        } else if (field.equals("state_name")) {
                            state = reader.nextString();
                        } else if (field.equals("fec_ids")) {
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
                    System.out.println(j);
                    if (chamber.equals("senate")) {
                        if (i == 1) {
                            id1 = id;
                            showData(R.id.sen1, R.id.par1, R.id.ema1, R.id.sen1Image, R.id.web1, R.id.twe1, R.id.box1, R.id.twi1, first + " " + last, party, email, website, handle);
                        } else {
                            id2 = id;
                            showData(R.id.sen2, R.id.par2, R.id.ema2, R.id.sen2image, R.id.web2, R.id.twe2, R.id.box2, R.id.twi2, first + " " + last, party, email, website, handle);
                        }
                        i++;
                    } else {
                        if (j == 1) {
                            id3 = id;
                            showData(R.id.sen3, R.id.par3, R.id.ema3, R.id.sen3image, R.id.web3, R.id.twe3, R.id.box3, R.id.twi3, first + " " + last, party, email, website, handle);
                            String title = state;
                            title += " District ";
                            title += district;
                            ((TextView) findViewById(R.id.header)).setText(title);

                        } else if (j == 2) {
                            id4 = id;
                            showData(R.id.sen4, R.id.par4, R.id.ema4, R.id.sen4image, R.id.web4, R.id.twe4, R.id.box4, R.id.twi4, first + " " + last, party, email, website, handle);
                        } else if (j == 5) {
                            id5 = id;
                            showData(R.id.sen5, R.id.par5, R.id.ema5, R.id.sen5image, R.id.web5, R.id.twe5, R.id.box5, R.id.twi5, first + " " + last, party, email, website, handle);
                        }
                        j++;
                    }

                }
                if (j <= 3) {
                    ((LinearLayout) findViewById(R.id.box5)).setVisibility(View.GONE);
                }
                if (j <= 2) {
                    ((LinearLayout) findViewById(R.id.box4)).setVisibility(View.GONE);
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

    public void showData(int sen, int par, int ema, final int image, int web, int twe, int box, final int twi, String name, String party, String email, String website, String handle) {
        ((TextView) findViewById(sen)).setText(name);
        ((TextView) findViewById(par)).setText(party);
        ((TextView) findViewById(ema)).setText(email);
        ((LinearLayout) findViewById(box)).setBackground(getBackgroundColor(party));
        ((TextView) findViewById(web)).setText(website);
        ((TextView) findViewById(twe)).setText("@" + handle);
        final String twit = handle;

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> appSessionResult) {
                AppSession session = appSessionResult.data;
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
                twitterApiClient.getStatusesService().userTimeline(null, twit, 1, null, null, false, false, false, true, new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> listResult) {
                        for(Tweet tweet: listResult.data) {
                            ((TextView) findViewById(twi)).setText(tweet.text);
                            try {
                                ((ImageButton) findViewById(image)).setImageBitmap(new DownloadImageAsynch().execute(tweet.user.profileImageUrl.replace("_normal", "")).get());
                            } catch(java.lang.InterruptedException error) {
                                System.out.println("c");
                            } catch(java.util.concurrent.ExecutionException error) {
                                System.out.println("d");
                            }
                        }
                    }
                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                    }
                });
            }
            @Override
            public void failure(TwitterException e) {
                e.printStackTrace();
            }
        });
    }

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
