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
    private int district;
    private String state;
    private static final String TWITTER_KEY = "If5CSwJEhO6mThm2B9p91Iq0F";
    private static final String TWITTER_SECRET = "9QlTmQYUuTPiYCl8PZEYXTcMziaEaWHvu1NH0uKN5Zd4G4xu6Z";
    private String id1 = "";
    private String id2 = "";
    private String id3 = "";
    private String id4 = "";
    private String id5 = "";
    private String handle1 = "";
    private String handle2 = "";
    private String handle3 = "";
    private String handle4 = "";
    private String handle5 = "";
    private String party1 = "";
    private String party2 = "";
    private String party3 = "";
    private String party4 = "";
    private String party5 = "";
    private String term_end1 ="";
    private String term_end2 ="";
    private String term_end3 ="";
    private String term_end4 ="";
    private String term_end5 ="";
    private String zip = "";
    private int finished = 0;
    private String county = "";
    private String stateshort = "";
    private Double obamavote;
    private Double romneyvote;


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
        ((LinearLayout) findViewById(R.id.all)).setVisibility(View.GONE);

//        Intent watchIntent = new Intent(getParent().getBaseContext(), PhoneToWatchService.class);

        if (extras != null) {
            if (extras.getString("TYPE").equals("ZIP")) {
                zip = extras.getString("ZIPCODE");
                pullData(zip);
                googleGeo(zip);
            } else {
                cases = 3;
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
                detIntent.putExtra("VALUE", id1);
                detIntent.putExtra("HANDLE", handle1);
                detIntent.putExtra("PARTY", party1);
                detIntent.putExtra("ROLE", "Senator");
                detIntent.putExtra("END", term_end1);
                detIntent.putExtra("NAME", ((TextView) findViewById(R.id.sen1)).getText());
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
                detIntent.putExtra("HANDLE", handle2);
                detIntent.putExtra("PARTY", party2);
                detIntent.putExtra("ROLE", "Senator");
                detIntent.putExtra("END", term_end2);
                detIntent.putExtra("NAME", ((TextView) findViewById(R.id.sen2)).getText());
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
                detIntent.putExtra("HANDLE", handle3);
                detIntent.putExtra("PARTY", party3);
                detIntent.putExtra("ROLE", "Representative");
                detIntent.putExtra("END", term_end3);
                detIntent.putExtra("NAME", ((TextView) findViewById(R.id.sen3)).getText());
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
                detIntent.putExtra("HANDLE", handle4);
                detIntent.putExtra("PARTY", party4);
                detIntent.putExtra("ROLE", "Representative");
                detIntent.putExtra("END", term_end4);
                detIntent.putExtra("NAME", ((TextView) findViewById(R.id.sen4)).getText());
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
                detIntent.putExtra("HANDLE", handle5);
                detIntent.putExtra("PARTY", party5);
                detIntent.putExtra("ROLE", "Representative");
                detIntent.putExtra("END", term_end5);
                detIntent.putExtra("NAME", ((TextView) findViewById(R.id.sen5)).getText());
                detIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(detIntent);
            }
        });

    }

    public void googleGeo() {
        try {
            InputStream in = new GetDataAsynch().execute("https://maps.googleapis.com/maps/api/geocode/json?&latlng="+ Double.toString(latitude) + "," + Double.toString(longitude) + "&key=AIzaSyBefULUFma1aPV2dVtpOJ9cX85WCcGFDY4").get();
            readJsonStreamGoogle(in);
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

    public void googleGeo(String zip) {
        try {
            InputStream in = new GetDataAsynch().execute("https://maps.googleapis.com/maps/api/geocode/json?address=" + zip + "&key=AIzaSyBefULUFma1aPV2dVtpOJ9cX85WCcGFDY4").get();
            readJsonStreamGoogle(in);
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

    public void pullData(String zipcode) {
        try {
            InputStream in = new GetDataAsynch().execute("https://congress.api.sunlightfoundation.com/legislators/locate?zip="+ zipcode +"&apikey=a96714973c0748038c1b2e35ebdc690a").get();
            readJsonStream(in);
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

    public void pullData() {
        try {
            InputStream in = new GetDataAsynch().execute("https://congress.api.sunlightfoundation.com/legislators/locate?latitude=" + Double.toString(latitude) + "&longitude="+ Double.toString(longitude) +"&apikey=a96714973c0748038c1b2e35ebdc690a").get();
            readJsonStream(in);
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
            return;
        } finally {
            reader.close();
        }
    }

    public void readJsonStreamGoogle(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            readMessagesArrayGoogle(reader);
            return;
        } finally {
            reader.close();
        }
    }

    public void readMessagesArray(JsonReader reader) throws IOException {
        reader.beginObject();
        Intent watch = new Intent(getBaseContext(), PhoneToWatchService.class);
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
                String term = "";
                while (reader.hasNext()) {
                    reader.beginObject();
                    while(reader.hasNext()) {
                        String field = reader.nextName();
                        if (field.equals("chamber")) {
                            chamber = reader.nextString();
                        } else if (field.equals("bioguide_id")) {
                            id = reader.nextString();
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
                        } else if (field.equals("oc_email")) {
                            email = reader.nextString();
                        } else if (field.equals("website")) {
                            website = reader.nextString();
                        } else if (field.equals("twitter_id")) {
                            handle = reader.nextString();
                        } else if (field.equals("state")) {
                            stateshort = reader.nextString();
                        } else if (field.equals("state_name")) {
                            state = reader.nextString();
                        } else if (field.equals("district")) {
                            if (reader.peek() == JsonToken.NUMBER) {
                                district = reader.nextInt();
                            } else {
                                reader.nextNull();
                            }
                        } else if (field.equals("term_end")) {
                            term = reader.nextString();
                        } else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();
                    if (chamber.equals("senate")) {
                        if (i == 1) {
                            handle1 = handle;
                            id1 = id;
                            party1 = party;
                            term_end1 = term;
                            showData(R.id.sen1, R.id.par1, R.id.ema1, R.id.sen1Image, R.id.web1, R.id.twe1, R.id.box1, R.id.twi1, first + " " + last, party, email, website, handle, 1, id, term, watch);
                        } else {
                            handle2 = handle;
                            id2 = id;
                            party2 = party;
                            term_end2 = term;
                            showData(R.id.sen2, R.id.par2, R.id.ema2, R.id.sen2image, R.id.web2, R.id.twe2, R.id.box2, R.id.twi2, first + " " + last, party, email, website, handle, 2, id, term, watch);
                        }
                        i++;
                    } else {
                        if (j == 1) {
                            handle3 = handle;
                            id3 = id;
                            party3 = party;
                            term_end3 = term;
                            showData(R.id.sen3, R.id.par3, R.id.ema3, R.id.sen3image, R.id.web3, R.id.twe3, R.id.box3, R.id.twi3, first + " " + last, party, email, website, handle, 3, id, term, watch);
                            String title = state;
                            title += " District ";
                            title += district;
                            ((TextView) findViewById(R.id.header)).setText(title);

                        } else if (j == 2) {
                            handle4 = handle;
                            id4 = id;
                            party4 = party;
                            term_end4 = term;
                            showData(R.id.sen4, R.id.par4, R.id.ema4, R.id.sen4image, R.id.web4, R.id.twe4, R.id.box4, R.id.twi4, first + " " + last, party, email, website, handle, 4, id, term, watch);
                        } else if (j == 3) {
                            handle5 = handle;
                            id5 = id;
                            party5 = party;
                            term_end5 = term;
                            showData(R.id.sen5, R.id.par5, R.id.ema5, R.id.sen5image, R.id.web5, R.id.twe5, R.id.box5, R.id.twi5, first + " " + last, party, email, website, handle, 5, id, term, watch);
                        }
                        j++;
                    }
                }
                if (j <= 3) {
                    ((LinearLayout) findViewById(R.id.box5)).setVisibility(View.GONE);
                }
                if (j <= 2) {
                    ((LinearLayout) findViewById(R.id.box4)).setVisibility(View.GONE);
                } else {
                    String title = "Congressmen for ";
                    title += zip;
                    ((TextView) findViewById(R.id.header)).setText(title);
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

    public void readMessagesArrayGoogle(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            System.out.println(name);
            if (name.equals("results")) {
                reader.beginArray();
                county = "";
                String types = "";
                while (reader.hasNext()) {
                    reader.beginObject();
                    while(reader.hasNext()) {
                        String field = reader.nextName();
                        if (field.equals("address_components")) {
                            reader.beginArray();
                            while (reader.hasNext()) {
                                reader.beginObject();
                                while (reader.hasNext()) {
                                    String field2 = reader.nextName();
                                    if (field2.equals("long_name") && reader.peek() == JsonToken.STRING) {
                                        county = reader.nextString();
                                    } else if (field2.equals("short_name") && reader.peek() == JsonToken.STRING) {
                                        county = reader.nextString();
                                    } else if (field2.equals("types")) {
                                        reader.beginArray();
                                        while (reader.hasNext()) {
                                            String type = reader.nextString();
                                            if (type.equals("administrative_area_level_2")) {
                                                System.out.println(county);
                                                reader.close();
                                                getVote();
                                                return;
                                            }
                                        }
                                        reader.endArray();
                                    } else {
                                        reader.skipValue();
                                    }
                                }
                                reader.endObject();
                            }
                            reader.endArray();
                        } else {
                            reader.skipValue();
                        }
                    }
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return;
    }

    public void showData(int sen, int par, int ema, final int image, int web, int twe, int box, final int twi, String name, String party, String email, String website, String handle, int pos, String bio_id, String term, Intent watchIntent) {

        String k = Integer.toString(pos);
        watchIntent.putExtra("VALUE" + k, bio_id);
        watchIntent.putExtra("HANDLE" + k, handle);
        watchIntent.putExtra("par" + k, party);
        watchIntent.putExtra("ROLE" + k, "Senator");
        watchIntent.putExtra("END" + k, term);
        watchIntent.putExtra("sen" + k, name);

        ((TextView) findViewById(sen)).setText(name);
        ((TextView) findViewById(par)).setText(party);
        ((TextView) findViewById(ema)).setText(email);
        ((LinearLayout) findViewById(box)).setBackground(getBackgroundColor(party));
        ((TextView) findViewById(web)).setText(website);
        ((TextView) findViewById(twe)).setText("@" + handle);
        final String twit = handle;

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        final Intent cIntent = watchIntent;

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
                            finished += 1;
                            System.out.println(finished);
                            if (finished == cases) {
                                ((LinearLayout) findViewById(R.id.all)).setVisibility(View.VISIBLE);
                                ((LinearLayout) findViewById(R.id.b)).setVisibility(View.GONE);
                                cIntent.putExtra("stateshort", stateshort);
                                cIntent.putExtra("county", county);
                                cIntent.putExtra("obama", Double.toString(obamavote));
                                cIntent.putExtra("romney", Double.toString(romneyvote));
                                cIntent.putExtra("cases", Integer.toString(cases - 2));
                                startService(cIntent);
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

    public void getVote() {
        try {
            InputStream in = new GetDataAsynch().execute("https://raw.githubusercontent.com/cs160-sp16/voting-data/master/election-county-2012.json").get();
            readJsonStreamVote(in);
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

    public void readJsonStreamVote(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            readMessagesArrayVote(reader);
            return;
        } finally {
            reader.close();
        }
    }

    public void readMessagesArrayVote(JsonReader reader) throws IOException {
        boolean found = false;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String field = reader.nextName();
                if (field.equals("state-postal")) {
                    if (!reader.nextString().equals(stateshort)) {
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        break;
                    }
                } else if (field.equals("county-name")) {
                    String mycounty = reader.nextString() + " County";
                    if (mycounty.equals(county)) {
                        found = true;
                    } else {
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        reader.skipValue();
                        break;
                    }
                } else if (field.equals("obama-percentage")) {
                    obamavote = reader.nextDouble();
                } else if (field.equals("romney-percentage")) {
                    romneyvote = reader.nextDouble();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            if (found) {
                reader.close();
                return;
            }
        }
        reader.endArray();
        return;
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
            googleGeo();
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
