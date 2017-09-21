package com.foxyourprivacy.f0x1t.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.TapAdapter_results;
import com.foxyourprivacy.f0x1t.fragments.PermissionListFragment;

public class AnalysisResults extends FoxITActivity {

    public ViewPager mViewPager; //defines the tabView's content
    public PermissionListFragment permissionList;
    TapAdapter_results adapter; //defines the content of the tabs, SettingListfragment and AppListFragment
    String settingsArray[]; //array with settings fetched from the database
    Toolbar toolbar; //reference to the toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        //sets our toolbar as the actionbar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //get Settings from DB into ListView
        settingsArray = getIntent().getStringArrayExtra("settings");

        //defining the tabs and the tab bar
        adapter = new TapAdapter_results(getFragmentManager(), this, settingsArray);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //defining an event for clicking the tab bar, this is necessary for hiding the permissionList in case the tabBar is pressed
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            //if a new tab is pressed
            public void onTabSelected(TabLayout.Tab tab) {
                //switches the tabs manually, this is necessary because the default behavior is overwritten
                mViewPager.setCurrentItem(tab.getPosition());
                FragmentManager manager = getFragmentManager();
                //sets the Lists visible and the permissionList invisible if currently visible
                mViewPager.setVisibility(View.VISIBLE);
                if (manager.findFragmentByTag("permission") != null) {
                    manager.popBackStack();
                }
            }

            @Override
            //has to be implemented but is not used
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            //called if the current tab is pressed
            public void onTabReselected(TabLayout.Tab tab) {
                FragmentManager manager = getFragmentManager();
                //setts the Lists visible and the permissionList if visible invisible
                mViewPager.setVisibility(View.VISIBLE);
                if (manager.findFragmentByTag("permission") != null) {
                    manager.popBackStack();
                }
            }
        });


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.goOn) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_activities, menu);
        menu.findItem(R.id.action_options).setVisible(false);
        menu.findItem(R.id.goBack).setVisible(false);
        return true;
    }


    /**
     * methodLeft that allows me to set the app list visible and destroy permissionListFragment
     * by clicking the fragments headline
     *
     * @author Tim
     */
    public void onPermissionListHeadlinePressed() {
        //if a fragment was created and added to the BackStack (must be PermissionListFragment)
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            if (permissionList != null) {
                permissionList = null;
            }
            getFragmentManager().popBackStack(); //destroy it
            FrameLayout appFrame = (FrameLayout) findViewById(R.id.appFrame);
            appFrame.setVisibility(View.VISIBLE); //and make the appList visible
            mViewPager.setVisibility(View.VISIBLE);

        } else {
            super.onBackPressed();
        }
    }

    /**
     * sets the visibility  of the scrollHint
     *
     * @param isVisible true= setts it visible, false=invisible
     * @author Tim
     */
    public void setScrollMessageVisiblility(boolean isVisible) {
        RelativeLayout scrollMessage = (RelativeLayout) findViewById(R.id.hintFrame);
        if (isVisible) {
            scrollMessage.setVisibility(View.VISIBLE);
        } else {
            scrollMessage.setVisibility(View.GONE);
        }
    }

    @Override
    /**
     * overrides the behavior of the backButton for it to properly support Fragments and Fragments in Fragments (ChildFragments)
     * @author Tim
     */

    public void onBackPressed() {
        //if there is an fragment
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            //if PermissionDescriptionFragment exists
            if (permissionList != null && permissionList.getChildFragmentManager().getBackStackEntryCount() > 0) {
                permissionList.setListVisible();//make the hidden permissionList visible again
                permissionList.getChildFragmentManager().popBackStack();//destroy the PermissionDescriptionFragment
            } else {//if only PermissionListFragment exists
                if (permissionList != null) {
                    permissionList = null;
                }
                getSupportFragmentManager().popBackStack(); //destroy PermissionListFragment
                FrameLayout appFrame = (FrameLayout) findViewById(R.id.appFrame);
                appFrame.setVisibility(View.VISIBLE); //make the hidden appList visible again
                mViewPager.setVisibility(View.VISIBLE);
            }
        } else {//if no fragments exist go to home
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
            //super.onBackPressed();
        }
    }

}
