package com.bp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class OnboardingActivity extends AppCompatActivity {

    TapAdapter_onboarding adapter; //defines the content of the tabs, OnboardingFragment-OnboardingFragment4
    ViewPager mViewPager;

    @Override
    /**
     * @author Hannah
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        //defining the tabs and the tab bar
        adapter=new TapAdapter_onboarding(getFragmentManager(),this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        SettingsActivity sa = new SettingsActivity();
        sa.updateLessions(this);
        sa.updatePermissions(this);
        sa.updateSettings(this);
    }
}
