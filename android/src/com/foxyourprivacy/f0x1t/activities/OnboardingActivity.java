package com.foxyourprivacy.f0x1t.activities;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.TapAdapter_onboarding;
import com.foxyourprivacy.f0x1t.ValueKeeper;

public class OnboardingActivity extends FoxITActivity {

    /**
     * @author Hannah
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        //defining the tabs and the tab bar
        TapAdapter_onboarding adapter = new TapAdapter_onboarding(getFragmentManager());
        ViewPager mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }



    @Override
    public void onStart() {
        super.onStart();
        ValueKeeper v = ValueKeeper.getInstance();
        //DBHandler db=new DBHandler(this,null,null,1);
        if (!v.onboardingStartedBefore) {

            //db.changeIndividualValue("onboardingStartedBefore",Boolean.toString(true));
            SettingsActivity sa = new SettingsActivity();
            sa.updateLessions(this, (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
            sa.updatePermissions(this, (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
            sa.updateSettings(this, (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
            v.onboardingStartedBefore = true;
        }


        //db.close();
    }

    @Override
    public void onBackPressed() {

    }
}
