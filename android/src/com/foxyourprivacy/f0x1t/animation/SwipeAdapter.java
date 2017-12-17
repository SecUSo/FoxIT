package com.foxyourprivacy.f0x1t.animation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * This SwipeAdapter is used in the fox animation activity to control the buttons for animation selection.
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
                bundle.putString("button", "setzen statt hetzen");
                break;
            case 1:
                bundle.putString("button", "fuchsteufelsfröhlich");
                break;
            case 2:
                bundle.putString("button", "klicken zum kicken");
                break;
            case 3:
                bundle.putString("button", "Tarnung aktivieren!");
                break;
            case 4:
                bundle.putString("button", "ich bin ein Fuchsonaut!");
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
