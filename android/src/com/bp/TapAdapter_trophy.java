package com.bp;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Ich on 04.08.2016.
 */

//Class which defines the Tabs in StartScreen
public class TapAdapter_trophy extends FragmentPagerAdapter {

    Activity activity; //reference to TrophyRoomActivity

    /**
     * @param fm
     * @param activity      reference to StartScreen
     * @author Tim
     */
    public TapAdapter_trophy(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    /**
     * Fills the TabAdapter with the Fragments to be displayed
     *
     * @param position describes which tab is pressed
     * @return
     * @author Tim
     */

    @Override
    public android.app.Fragment getItem(int position) {
        if (position == 0) {
            //adds TrophyListFragment
            TrophyListFragment trophyList = new TrophyListFragment();
            return trophyList;
        } else {
            //adds AnimationListFragment
            AnimationListFragment animationListFragment=new AnimationListFragment();
            ((TrophyRoomActivity)activity).setAnimationListFragment(animationListFragment);
            return animationListFragment;
        }

    }

    /**
     * defines the number of tabs presented (2)
     *
     * @return the number of Tabs
     * @author Tim
     */
    @Override
    public int getCount() {
        return 2;
    }

    /**
     * define how the Tabs are called
     *
     * @param position which tab is currently named
     * @return the name of the tab
     * @author Tim
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Trophen";
        } else {
            return "Animationen";
        }
    }
}
