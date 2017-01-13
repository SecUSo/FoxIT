package com.foxyourprivacy.f0x1t.Animation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Lynn on 05.01.2017.
 */

public class SwipeAdapter extends FragmentPagerAdapter {


    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putString("button", "Sitz!");
                break;
            case 1:
                bundle.putString("button", "Wedel mal!");
                break;
            case 2:
                bundle.putString("button", "Kick den Ball!");
                break;
            case 3: bundle.putString("button", "Versteck dich!");
                break;
            case 4: bundle.putString("button", "Flieg!");
                break;
            default:
                bundle.putString("button", "NOTHING");
                break;
        }

        Fragment fragment = new ButtonFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
