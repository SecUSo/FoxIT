package com.foxyourprivacy.f0x1t;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.foxyourprivacy.f0x1t.activities.TrophyRoomActivity;
import com.foxyourprivacy.f0x1t.fragments.AnimationListFragment;
import com.foxyourprivacy.f0x1t.fragments.TrophyListFragment;


/**
 * Class which defines the Tabs in TrophyRoomActivity
 * Created by Tim on 04.08.2016.
 */

public class TapAdapter_trophy extends FragmentPagerAdapter {

    private final Activity activity; //reference to TrophyRoomActivity

    /**
     * @param fm the fragment manager to use
     * @param activity reference to AnalysisResults
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
     * @return Fragment that belongs on that position
     * @author Tim
     */

    @Override
    public android.app.Fragment getItem(int position) {
        if (position == 0) {
            //adds TrophyListFragment
            return new TrophyListFragment();
        } else {
            //adds AnimationListFragment
            AnimationListFragment animationListFragment = new AnimationListFragment();
            ((TrophyRoomActivity) activity).setAnimationListFragment(animationListFragment);
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
            return "Trophäen";
        } else {
            return "Animationen";
        }
    }
}
