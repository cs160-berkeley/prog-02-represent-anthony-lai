package com.example.sturmgewehr44.democrazy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.identity.*;

public class MainViewMobile extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "If5CSwJEhO6mThm2B9p91Iq0F";
    private static final String TWITTER_SECRET = "9QlTmQYUuTPiYCl8PZEYXTcMziaEaWHvu1NH0uKN5Zd4G4xu6Z";


    private Button mZipGoButton;
    private Button mLocationButton;
    private TwitterLoginButton loginButton;


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
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("TYPE", "ZIP");
                String zip = "";
                try {
                    zip = mZipCodeBox.getText().toString();
                } catch (Exception e) {
                    zip = "00000";
                }
                ArrayList<String> info = findData(zip);
                sendIntent.putExtra("ZIPCODE", zip);
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
//                startService(sendIntent);
                Intent toCongressionalViewIntent = new Intent(getBaseContext(), CongressionalViewMobile.class);
                toCongressionalViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                toCongressionalViewIntent.putExtras(sendIntent.getExtras());
                startActivity(toCongressionalViewIntent);

            }
        });

        mLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("TYPE", "LOC");
//                startService(sendIntent);
                ArrayList<String> info = findData("00000");
                sendIntent.putExtra("ZIPCODE", "-1");
                int cases = 1;
                sendIntent.putExtra("cases", "1");
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
//                startService(sendIntent);
                Intent toCongressionalViewIntent = new Intent(getBaseContext(), CongressionalViewMobile.class);
                toCongressionalViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                toCongressionalViewIntent.putExtras(sendIntent.getExtras());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }



    public ArrayList<String> findData(String zip) {
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
