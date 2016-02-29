package com.example.sturmgewehr44.democrazy;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailedViewMobile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view_mobile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (intent != null) {
            int fall = Integer.parseInt(extras.getString("VALUE"));
            setInfo(intent, fall);
        } else {
            System.out.println("scr");
        }
    }

    public void setInfo(Intent intent, int fall) {
        Bundle extras = intent.getExtras();
        String senator = "";
        switch (fall) {
            case 1:
                senator = extras.getString("sen1");
                ((TextView) findViewById(R.id.title)).setText(senator);
                ((TextView) findViewById(R.id.party)).setText(extras.getString("par1"));
                ((ImageView) findViewById(R.id.image)).setBackground(getBackgroundColor(extras.getString("par1")));
                ((ImageView) findViewById(R.id.image)).setImageResource(R.drawable.fdr);
                break;
            case 2:
                senator = extras.getString("sen2");
                ((TextView) findViewById(R.id.title)).setText(senator);
                ((TextView) findViewById(R.id.party)).setText(extras.getString("par2"));
                ((ImageView) findViewById(R.id.image)).setBackground(getBackgroundColor(extras.getString("par2")));
                ((ImageView) findViewById(R.id.image)).setImageResource(R.drawable.stalin);
                break;
            case 3:
                senator = extras.getString("sen3");
                ((TextView) findViewById(R.id.title)).setText(senator);
                ((TextView) findViewById(R.id.party)).setText(extras.getString("par3"));
                ((ImageView) findViewById(R.id.image)).setBackground(getBackgroundColor(extras.getString("par3")));
                ((ImageView) findViewById(R.id.image)).setImageResource(R.drawable.wendell);
                break;
            case 4:
                senator = extras.getString("sen4");
                ((TextView) findViewById(R.id.title)).setText(senator);
                ((TextView) findViewById(R.id.party)).setText(extras.getString("par4"));
                ((ImageView) findViewById(R.id.image)).setBackground(getBackgroundColor(extras.getString("par4")));
                ((ImageView) findViewById(R.id.image)).setImageResource(R.drawable.hitler);
                break;
            case 5:
                senator = extras.getString("sen5");
                ((TextView) findViewById(R.id.title)).setText(senator);
                ((TextView) findViewById(R.id.party)).setText(extras.getString("par5"));
                ((ImageView) findViewById(R.id.image)).setBackground(getBackgroundColor(extras.getString("par5")));
                ((ImageView) findViewById(R.id.image)).setImageResource(R.drawable.churchill);
                break;

        }
        ArrayList<String> moreinfo = getMoreInfo(senator);
        ((TextView) findViewById(R.id.role)).setText(moreinfo.get(0));
        ((TextView) findViewById(R.id.term)).setText(moreinfo.get(3));
        ((TextView) findViewById(R.id.slogan)).setText(moreinfo.get(1));
        ((TextView) findViewById(R.id.bio)).setText(moreinfo.get(2));
        ((TextView) findViewById(R.id.committees)).setText(moreinfo.get(4));
        ((TextView) findViewById(R.id.bills)).setText(moreinfo.get(5));
    }


    public ArrayList<String> getMoreInfo(String senator) {
        ArrayList<String> inf = new ArrayList<String>();
        inf.add("General Secretary of the Central Committee of the Communist Party of the Soviet Union");
        inf.add("\"Victory or Gulag, Comrades!\"");
        inf.add("   Comrade Stalin was one of the first members of the Politiburo, alongside Lenin, [Redacted], [Redacted], [Redacted], [Redacted], [Redacted], and [Redacted]. His glorious leadership during the past few years has seen the enemies of the working classes of Soviet Union purged. Under his leadership, the Soviet State has made an alliance with the German peoples, so that both nations may live in harmony with the bounties from Poland.");
        inf.add("Term ends:" + "1953");
        inf.add("1941- : Chairman, State Defense Committee\n" +
                "1920- : Chairman, Workers' and Peasants' Inspectorate of the Russian SFSR\n" +
                "1917-1920: People's Commissar, Nationalities of the Russian SFSR\n");
        inf.add("1942: Order No. 227\n" +
                "1941: Order No. 270");
        return inf;
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
