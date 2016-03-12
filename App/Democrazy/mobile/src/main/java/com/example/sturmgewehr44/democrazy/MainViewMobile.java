package com.example.sturmgewehr44.democrazy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.identity.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainViewMobile extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "If5CSwJEhO6mThm2B9p91Iq0F";
    private static final String TWITTER_SECRET = "9QlTmQYUuTPiYCl8PZEYXTcMziaEaWHvu1NH0uKN5Zd4G4xu6Z";


    private Button mZipGoButton;
    private Button mLocationButton;
    private TwitterLoginButton loginButton;
    private String zip = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main_view_mobile);

        mZipGoButton = (Button) findViewById(R.id.go_btn);
        mLocationButton = (Button) findViewById(R.id.loc_btn);
        final EditText mZipCodeBox = (EditText) findViewById(R.id.zipEntry);

        mZipGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    zip = mZipCodeBox.getText().toString();
                } catch (Exception e) {
                    zip = "00000";
                }
//                if (zip.equals("99999")) {
//                    int duration = Toast.LENGTH_SHORT;
//                    Toast toast = Toast.makeText(getBaseContext(), "Please enter a valid zip code.", duration);
//                    toast.show();
//                    return;
//                }
                pullData(zip);
//                Intent toCongressionalViewIntent = new Intent(getBaseContext(), CongressionalViewMobile.class);
//                toCongressionalViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                toCongressionalViewIntent.putExtra("TYPE", "ZIP");
//                toCongressionalViewIntent.putExtra("ZIPCODE", zip);
//                startActivity(toCongressionalViewIntent);
            }
        });

        mLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCongressionalViewIntent = new Intent(getBaseContext(), CongressionalViewMobile.class);
                toCongressionalViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                toCongressionalViewIntent.putExtra("TYPE", "LOC");
                startActivity(toCongressionalViewIntent);
            }
        });

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

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

    public void readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            readMessagesArray(reader);
            return;
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
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getBaseContext(), "Please enter a valid zip code.", duration);
                    toast.show();
                    reader.close();
                    return;
                } else {
                    Intent toCongressionalViewIntent = new Intent(getBaseContext(), CongressionalViewMobile.class);
                    toCongressionalViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    toCongressionalViewIntent.putExtra("TYPE", "ZIP");
                    toCongressionalViewIntent.putExtra("ZIPCODE", zip);
                    reader.close();
                    startActivity(toCongressionalViewIntent);
                    return;
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_view_mobile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
