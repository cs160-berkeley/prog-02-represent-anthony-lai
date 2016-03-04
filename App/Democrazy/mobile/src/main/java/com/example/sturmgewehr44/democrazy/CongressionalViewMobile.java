package com.example.sturmgewehr44.democrazy;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CongressionalViewMobile extends AppCompatActivity {

    private ImageButton msen1Button;
    private ImageButton msen2Button;
    private ImageButton msen3Button;
    private ImageButton msen4Button;
    private ImageButton msen5Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view_mobile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            int cases = Integer.parseInt(extras.getString("cases"));
            TextView header = (TextView) findViewById(R.id.header);
            if (extras.getString("TYPE").equals("ZIP")) {
                String title = "Congressmen for ";
                title += extras.getString("ZIPCODE");
                header.setText(title);
            } else {
                String title = extras.getString("state");
                title += "'s ";
                title += "239th ";
                title += "district";
                header.setText(title);
                cases = 1;
            }

            ((TextView) findViewById(R.id.sen1)).setText(extras.getString("sen1"));
            ((TextView) findViewById(R.id.par1)).setText(extras.getString("par1"));
            ((TextView) findViewById(R.id.ema1)).setText(extras.getString("ema1"));
            ((TextView) findViewById(R.id.web1)).setText(extras.getString("web1"));
            ((TextView) findViewById(R.id.twe1)).setText(extras.getString("twe1"));
            ((TextView) findViewById(R.id.twi1)).setText(extras.getString("twi1"));
            ((ImageButton) findViewById(R.id.sen1Image)).setBackground(getBackgroundColor(extras.getString("par1")));

            ((TextView) findViewById(R.id.sen2)).setText(extras.getString("sen2"));
            ((TextView) findViewById(R.id.par2)).setText(extras.getString("par2"));
            ((TextView) findViewById(R.id.ema2)).setText(extras.getString("ema2"));
            ((TextView) findViewById(R.id.web2)).setText(extras.getString("web2"));
            ((TextView) findViewById(R.id.twe2)).setText(extras.getString("twe2"));
            ((TextView) findViewById(R.id.twi2)).setText(extras.getString("twi2"));
            ((ImageButton) findViewById(R.id.sen2image)).setBackground(getBackgroundColor(extras.getString("par2")));

            ((TextView) findViewById(R.id.sen3)).setText(extras.getString("sen3"));
            ((TextView) findViewById(R.id.par3)).setText(extras.getString("par3"));
            ((TextView) findViewById(R.id.ema3)).setText(extras.getString("ema3"));
            ((TextView) findViewById(R.id.web3)).setText(extras.getString("web3"));
            ((TextView) findViewById(R.id.twe3)).setText(extras.getString("twe3"));
            ((TextView) findViewById(R.id.twi3)).setText(extras.getString("twi3"));
            ((ImageButton) findViewById(R.id.sen3image)).setBackground(getBackgroundColor(extras.getString("par3")));

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
}
