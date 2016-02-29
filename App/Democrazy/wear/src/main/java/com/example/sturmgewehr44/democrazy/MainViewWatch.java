package com.example.sturmgewehr44.democrazy;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;
import android.content.res.Resources;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.WindowInsets;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;


public class MainViewWatch extends AppCompatActivity {

    private TextView mTextView;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_watch);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        ctx = this;
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                mTextView.setText("dogg");
                System.out.println("u suck");
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                int cases = 0;
                if (extras != null) {
                    cases = Integer.parseInt(extras.getString("cases"));
                } else {
                    System.out.println("first");
                }
                final Resources res = getResources();
                final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
//        pager.setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
//            @Override
//            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
//                // Adjust page margins:
//                //   A little extra horizontal spacing between pages looks a bit
//                //   less crowded on a round display.
//                final boolean round = insets.isRound();
//                int rowMargin = res.getDimensionPixelOffset(2);
//                int colMargin = res.getDimensionPixelOffset(2);
//                pager.setPageMargins(rowMargin, colMargin);
//                return insets;
//            }
//        });
                FragmentManager fragmentManager = getFragmentManager();
                GridPagerAdapter gpa = new GridPagerAdapter(ctx, fragmentManager);
                if (cases == 0) {
                    System.out.println("fal");
                } else {
                    ArrayList<String> data = new ArrayList<String>();
                    for (int i = 0; i < cases + 2; i++) {
                        data.add(extras.getString("sen" + Integer.toString(i + 1)));
                        data.add(extras.getString("par" + Integer.toString(i + 1)));
                        data.add(Integer.toString(i + 1));
                    }
                    gpa.overridePages(cases + 2, data);
                }
                pager.setAdapter(gpa);
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                FaceFragment fragment = new FaceFragment();
//                fragmentTransaction.add(R.id.fragz, fragment);
//                fragmentTransaction.commit();
            }
        });

    }
}
