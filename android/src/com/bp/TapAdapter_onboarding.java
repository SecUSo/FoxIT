package com.bp;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Hannah on 04.08.2016.
 * Class which defines the Tabs in Onboarding
 */
public class TapAdapter_onboarding extends FragmentPagerAdapter {

    Activity activity; //reference to onboardingActivity

    /**
     * @param fm
     * @param activity      reference to Onboarding
     * @author Hannah
     */
    public TapAdapter_onboarding(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    /**
     * Fills the TabAdapter with the Fragments to be displayed
     * @param position describes which tab is pressed
     * @return
     * @author Hannah
     */
    @Override
    public android.app.Fragment getItem(int position) {
        switch (position){
            case 0: {  OnboardingFragment settingList = new OnboardingFragment();
                return settingList;}
            case 1:{
                OnboardingFragment2 settingList = new OnboardingFragment2();
                return settingList;}
            case 2:{
                OnboardingFragment3 settingList = new OnboardingFragment3();
                return settingList;}

            case 3:{  OnboardingFragment4 settingList = new OnboardingFragment4();
                return settingList;

            }
            default:{   OnboardingFragment settingList = new OnboardingFragment();
                return settingList;
            }
        }

    }

    /**
     * defines the number of tabs presented (2)
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