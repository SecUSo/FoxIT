package com.foxyourprivacy.f0x1t.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.foxyourprivacy.f0x1t.Fragments.ParticipantIDFragment;
import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.TapAdapter_onboarding;
import com.foxyourprivacy.f0x1t.ValueKeeper;

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
        adapter = new TapAdapter_onboarding(getFragmentManager(), this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //add the TradeRequestFragment to the activity's context
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //AnalysisRequestFragment tradeRequest = new AnalysisRequestFragment();
        ParticipantIDFragment tradeRequest = new ParticipantIDFragment();
        //add the fragment to the count_frame RelativeLayout
        transaction.add(R.id.whole_screen, tradeRequest, "count");
        transaction.addToBackStack("analysisRequest");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();

    }


    @Override
    public boolean setTrophyUnlocked(String trophyName) {
        return super.setTrophyUnlocked(trophyName);
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
