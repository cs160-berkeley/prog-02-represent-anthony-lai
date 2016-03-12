package com.example.sturmgewehr44.democrazy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.view.Gravity;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;


/**
 * Created by Sturmgewehr44 on 2/27/16.
 */

public class GridPagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;

    public GridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
    }

    /** A simple container for static data in each page */
    private static class Page {
        int titleRes;
        int textRes;
        int iconRes;
        int cardGravity = Gravity.BOTTOM;
        boolean expansionEnabled = true;
        float expansionFactor = 1.0f;
        int expansionDirection = CardFragment.EXPAND_DOWN;
        String senator = "sendd";
        String Partei = "partie";
        int value;
        int zip;
        String romney;
        String obama;
        String county;
        String stateshort;
        String id;
        String handle;
        String end;
        String role;

//        public Page(int titleRes, int textRes, boolean expansion) {
//            this(titleRes, textRes, 0);
//            this.expansionEnabled = expansion;
//        }
//
//        public Page(int titleRes, int textRes, boolean expansion, float expansionFactor) {
//            this(titleRes, textRes, 0);
//            this.expansionEnabled = expansion;
//            this.expansionFactor = expansionFactor;
//        }=

//        public Page(int titleRes, int textRes, int iconRes) {
//            this.titleRes = titleRes;
//            this.textRes = textRes;
//            this.iconRes = iconRes;
//        }

//        public Page(int titleRes, int textRes, int iconRes, int gravity) {
//            this.titleRes = titleRes;
//            this.textRes = textRes;
//            this.iconRes = iconRes;
//            this.cardGravity = gravity;
//        }

        public Page(String sen, String par, String val, String zip, String r, String o, String c, String ss, String id, String handle, String role, String end) {
//            this(0, 0, 0);
            this.senator = sen;
            this.Partei = par;
            this.value = Integer.parseInt(val);
            this.zip = Integer.parseInt(zip);
            this.romney = r;
            this.obama = o;
            this.county = c;
            this.stateshort = ss;
            this.id = id;
            this.handle = handle;
            this.end = end;
            this.role = role;
        }
    }

    private Page[][] PAGES = {
            {
                    new Page("Hitler", "National Socialist Party", "0", "11111", "111", "-11", "Barbarossas", "DD", "", "", "", ""),
            },};
//
//            {
//                    new Page(10, 11, true, 2),
//                    new Page(12, 12, true, 4),
//            },
//            {
//                    new Page(13, 13, true, 3),
//                    new Page(12, 13, true, 1)
//            },
//            {
//                    new Page(11, 11, R.drawable.hitler,
//                            Gravity.CENTER_VERTICAL),
//            },
//
//    };

    public void overridePages(int cases, ArrayList<String> info) {
        String romney = info.get(info.size()-1);
        String obama = info.get(info.size()-2);
        String county = info.get(info.size()-3);
        String stateshort = info.get(info.size()-4);
        String zipcode = info.get(info.size()-5);
        Page[][] Present = new Page[cases][2];
        for (int i = 0; i < cases; i++) {
            Present[i][0] = new Page(info.get(i * 7), info.get(i * 7 + 1), info.get(i * 7 + 2), zipcode, romney, obama, county, stateshort, info.get(i * 7 + 3), info.get(i * 7 + 4), info.get(i * 7 + 5), info.get(i * 7 + 6));
            Present[i][1] = new Page(info.get(i * 7), info.get(i * 7 + 1), info.get(i * 7 + 2), zipcode, romney, obama, county, stateshort, info.get(i * 7 + 3), info.get(i * 7 + 4), info.get(i * 7 + 5), info.get(i * 7 + 6));
        }
        PAGES = Present;
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Page page = PAGES[row][col];
        System.out.println(row);
        System.out.println(col);
        String sen = page.senator;
        String par = page.Partei;
        String romney = page.romney;
        String obama = page.obama;
        String county = page.county;
        String stateshort = page.stateshort;
        String value = page.id;
        String handle = page.handle;
        String end = page.end;
        String role = page.role;
        Fragment fragment;
        if (page.value == 0) {
            fragment = FaceFragment.newInstance(sen, par, getBackgroundInt(par), 0, value, handle, end, role);
            return fragment;
        }
        if (col == 0) {
            fragment = FaceFragment.newInstance(sen, par, getBackgroundInt(par), row + 1, value, handle, end, role);
        } else {
            fragment = VoteFragment.newInstance(stateshort, county, "Obama: "+ obama +"% of votes", "Romney: " + romney + "% of votes");
        }
        return fragment;
    }

    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        return new ColorDrawable(0xFF3c3b6e);
    }

    public int getBackgroundInt(String party) {
        switch (party) {
            case "Democratic Party": return 0xFFB0CEFF;
            case "Communist Party": return 0xFF970018;
            case "Republican Party": return 0xFFFFB6B6;
            case "National Socialist Party": return 0xFF000000;
            case "Conservative Party": return 0xFF0087DC;
        }
        return 0xFFFFFF;
    }

    @Override
    public int getRowCount() {
        return PAGES.length;
    }

    @Override
    public int getColumnCount(int rowNum) {
        return PAGES[rowNum].length;
    }
}