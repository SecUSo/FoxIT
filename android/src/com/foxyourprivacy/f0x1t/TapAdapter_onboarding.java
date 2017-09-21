package com.foxyourprivacy.f0x1t;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.foxyourprivacy.f0x1t.fragments.OnboardingFragment;
import com.foxyourprivacy.f0x1t.fragments.OnboardingFragment2;
import com.foxyourprivacy.f0x1t.fragments.OnboardingFragment3;
import com.foxyourprivacy.f0x1t.fragments.OnboardingFragment4;


/**
 * Created by Hannah on 04.08.2016.
 * Class which defines the Tabs in Onboarding
 */
public class TapAdapter_onboarding extends FragmentPagerAdapter {

    Activity activity; //reference to onboardingActivity

    /**
     * @param fm
     * @param activity reference to Onboarding
     * @author Hannah
     */
    public TapAdapter_onboarding(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    /**
     * Fills the TabAdapter with the Fragments to be displayed
     *
     * @param position describes which tab is pressed
     * @return
     * @author Hannah
     */
    @Override
    public android.app.Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new OnboardingFragment();
            }
            case 1: {
                return new OnboardingFragment2();
            }
            case 2: {
                return new OnboardingFragment3();
            }

            case 3: {
                return new OnboardingFragment4();

            }
            default: {
                return new OnboardingFragment();
            }
        }

    }

    /**
     * defines the number of tabs presented (4)
     *
     * @return the number of Tabs
     * @author Hannah
     */
    @Override
    public int getCount() {
        return 4;
    }

    /**
     * define how the Tabs are called but we don't want them to be named
     *
     * @param position which tab is currently named
     * @return the name of the tab
     * @author Hannah
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}