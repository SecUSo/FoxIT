package com.foxyourprivacy.f0x1t;

import android.app.Activity;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

import com.foxyourprivacy.f0x1t.Fragments.AppListFragment;
import com.foxyourprivacy.f0x1t.Fragments.SettingListFragment;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Ich on 04.08.2016.
 */

//Class which defines the Tabs in StartScreen
public class TapAdapter extends FragmentPagerAdapter {

    //Activity startScreenActivity; //reference to StartScreen
    String[] settingsArray;

    /**
     * @param fm
     * @param activity      reference to StartScreen
     * @param settingsArray the settings to be displayed by SettingListFragment
     * @author Tim
     */
    public TapAdapter(android.app.FragmentManager fm, Activity activity, String[] settingsArray) {
        super(fm);
        //this.startScreenActivity = activity;
        this.settingsArray = settingsArray;
    }

    /**
     * Fills the TabAdapter with the Fragments to be displayed
     * by creating a new fragment for each page of the TabView
     *
     * @param position describes which tab is pressed
     * @return
     * @author Tim
     */

    @Override
    public android.app.Fragment getItem(int position) {
        if (position == 0) {
            //adds AppListFragment for the second page
            return new AppListFragment();
        } else {
            //adds SettingListFragment for the first page
            SettingListFragment settingList = new SettingListFragment();
            Bundle settings = new Bundle();
            settings.putStringArrayList("settings", new ArrayList<String>(Arrays.asList(settingsArray)));
            settingList.setArguments(settings);
            return settingList;
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
            return "App-Liste";
        } else {
            return "Einstellungen";
        }
    }
}