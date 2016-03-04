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

        public Page(int titleRes, int textRes, boolean expansion) {
            this(titleRes, textRes, 0);
            this.expansionEnabled = expansion;
        }

        public Page(int titleRes, int textRes, boolean expansion, float expansionFactor) {
            this(titleRes, textRes, 0);
            this.expansionEnabled = expansion;
            this.expansionFactor = expansionFactor;
        }

        public Page(int titleRes, int textRes, int iconRes) {
            this.titleRes = titleRes;
            this.textRes = textRes;
            this.iconRes = iconRes;
        }

        public Page(int titleRes, int textRes, int iconRes, int gravity) {
            this.titleRes = titleRes;
            this.textRes = textRes;
            this.iconRes = iconRes;
            this.cardGravity = gravity;
        }

        public Page(String sen, String par, String val, String zip) {
            this(0, 0, 0);
            this.senator = sen;
            this.Partei = par;
            this.value = Integer.parseInt(val);
            this.zip = Integer.parseInt(zip);
        }
    }

    private Page[][] PAGES = {
            {
                    new Page("Hitler", "National Socialist Party", "0", "11111"),
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
        String zipcode = info.get(info.size()-1);
        Page[][] Present = new Page[cases][2];
        for (int i = 0; i < cases; i++) {
            Present[i][0] = new Page(info.get(i * 3), info.get(i * 3 + 1), info.get(i * 3 + 2), zipcode);
            Present[i][1] = new Page(info.get(i * 3), info.get(i * 3 + 1), info.get(i * 3 + 2), zipcode);
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
        Fragment fragment;
        if (page.value == 0) {
            fragment = FaceFragment.newInstance(sen, par, getBackgroundInt(par), 0);
            return fragment;
        }
        if (col == 0) {
            fragment = FaceFragment.newInstance(sen, par, getBackgroundInt(par), row + 1);
        } else {
            if (page.zip % 2 == 0) {
                fragment = VoteFragment.newInstance("CA", "Barbarossa County", "Obama: 101% of votes", "Romney: -1% of votes");
            } else {
                fragment = VoteFragment.newInstance("NV", "Barbarossa County", "Obama: 101% of votes", "Romney: -1% of votes");
            }
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