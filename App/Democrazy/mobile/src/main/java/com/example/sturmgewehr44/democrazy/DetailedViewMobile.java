package com.example.sturmgewehr44.democrazy;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class DetailedViewMobile extends AppCompatActivity {

    private static final String TWITTER_KEY = "If5CSwJEhO6mThm2B9p91Iq0F";
    private static final String TWITTER_SECRET = "9QlTmQYUuTPiYCl8PZEYXTcMziaEaWHvu1NH0uKN5Zd4G4xu6Z";
    String bills = "";
    String handle = "";
    String bio_id = "";
    String party = "";
    String committees = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view_mobile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (intent != null) {
            ((LinearLayout) findViewById(R.id.all)).setVisibility(View.GONE);
            bio_id = extras.getString("VALUE");
            handle = extras.getString("HANDLE");
            party = extras.getString("PARTY");
            ((TextView) findViewById(R.id.term)).setText("Term ends: " + extras.getString("END").substring(0, 4));
            ((TextView) findViewById(R.id.role)).setText(extras.getString("ROLE"));
            ((TextView) findViewById(R.id.title)).setText(extras.getString("NAME"));
            pullDataBills(bio_id);
            pullDataCommittee(bio_id);
            findTwitter();
        } else {
            System.out.println("scr");
        }
    }

    public void pullDataCommittee(String bio_id) {
        try {
            InputStream in = new GetDataAsynch().execute("https://congress.api.sunlightfoundation.com/committees?member_ids="+ bio_id + "&apikey=a96714973c0748038c1b2e35ebdc690a").get();
            readJsonStream2(in);
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

    public void readJsonStream2(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            readcom(reader);
            return;
        } finally {
            reader.close();
        }
    }

    public void readcom(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("results")) {
                reader.beginArray();
                String committee = "";
                while (reader.hasNext()) {
                    reader.beginObject();
                    while(reader.hasNext()) {
                        String field = reader.nextName();
                        if (field.equals("name")) {
                            committee = reader.nextString();
                        } else {
                            reader.skipValue();
                        }
                    }
                    committees += committee + "\n";
                    reader.endObject();
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        if (committees.equals("")) {
            ((TextView) findViewById(R.id.committees)).setText("This "+ ((TextView) findViewById(R.id.role)).getText() + " is not currently active on any committee.");
        } else {
            ((TextView) findViewById(R.id.committees)).setText(committees.substring(0, committees.length() - 1));
        }
        reader.endObject();
        return;
    }

    public void pullDataBills(String bio_id) {
        try {
            InputStream in = new GetDataAsynch().execute("https://congress.api.sunlightfoundation.com/bills?sponsor_id="+ bio_id +"&apikey=a96714973c0748038c1b2e35ebdc690a").get();
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
            read(reader);
            return;
        } finally {
            reader.close();
        }
    }

    public void read(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("results")) {
                reader.beginArray();
                int i = 0;
                String date = "";
                String bill = "";
                while (reader.hasNext()) {
                    if (i > 5) {
                        reader.skipValue();
                    } else {
                        reader.beginObject();
                        while(reader.hasNext()) {
                            String field = reader.nextName();
                            if (field.equals("official_title")) {
                                bill = reader.nextString();
                            } else if (field.equals("short_title")) {
                                if (reader.peek() == JsonToken.STRING) {
                                    bill = reader.nextString();
                                } else {
                                    reader.nextNull();
                                }
                            } else if (field.equals("introduced_on")) {
                                date = reader.nextString();
                            } else {
                                reader.skipValue();
                            }
                        }
                        bills += date + ": " + bill + "\n";
                        reader.endObject();
                        i++;
                    }
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        if (bills.equals("")) {
            ((TextView) findViewById(R.id.bills)).setText("This "+ ((TextView) findViewById(R.id.role)).getText() + " has not sponsored any bills.");
        } else {
            ((TextView) findViewById(R.id.bills)).setText(bills.substring(0, bills.length() - 1));
        }
        reader.endObject();
        return;
    }

    public void findTwitter() {
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
                        for (Tweet tweet : listResult.data) {
                            ((TextView) findViewById(R.id.slogan)).setText("@" + handle);
                            ((TextView) findViewById(R.id.bio)).setText(tweet.text);
                            ((TextView) findViewById(R.id.party)).setText(party);
                            try {
                                ((ImageView) findViewById(R.id.image)).setImageBitmap(new DownloadImageAsynch().execute(tweet.user.profileImageUrl.replace("_normal", "")).get());
                                ((ImageView) findViewById(R.id.image)).setBackground(getBackgroundColor(party));
                            } catch (java.lang.InterruptedException error) {
                                System.out.println("c");
                            } catch (java.util.concurrent.ExecutionException error) {
                                System.out.println("d");
                            }
                            ((LinearLayout) findViewById(R.id.all)).setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.b)).setVisibility(View.GONE);
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
            case "Democratic Party":
                return (Drawable) new ColorDrawable(0xFFB0CEFF);
            case "Communist Party":
                return (Drawable) new ColorDrawable(0xFF970018);
            case "Republican Party":
                return (Drawable) new ColorDrawable(0xFFFFB6B6);
            case "National Socialist Party":
                return (Drawable) new ColorDrawable(0xFF000000);
            case "Conservative Party":
                return (Drawable) new ColorDrawable(0xFF0087DC);
        }
        return new ColorDrawable();
    }
}
