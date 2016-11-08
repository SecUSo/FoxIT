package com.bp;

import android.net.ConnectivityManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class OnboardingActivity extends FoxItActivity {

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

    }

    @Override
    public void onStart() {
        super.onStart();
        SettingsActivity sa = new SettingsActivity();
        sa.updateLessions(this, (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE));
        sa.updatePermissions(this, (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE));
        sa.updateSettings(this, (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE));
    }
}
