package com.foxyourprivacy.f0x1t.Activities;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.foxyourprivacy.f0x1t.FoxITApplication;
import com.foxyourprivacy.f0x1t.ValueKeeper;

/**
 * device and user specific content is retrieved and saved in database
 * including installed apps, respective permissions & settings.
 */
public class FoxITActivity extends AppCompatActivity {

    @Override
    public void onStart() {
        super.onResume();
        ValueKeeper v = ValueKeeper.getInstance();
        FoxITApplication myApp = (FoxITApplication) this.getApplication();
        if (v.getFreshlyStarted()) {
            v.setTimeOfFirstAccess(System.currentTimeMillis());
        }


        if (myApp.wasInBackground || v.getFreshlyStarted()) {
            Log.d("MyApp", "Was in Background");
            v.setFreshlyStarted(false);
            v.setTimeOfLastAccess(System.currentTimeMillis());
        }

        myApp.stopActivityTransitionTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        ValueKeeper v = ValueKeeper.getInstance();
        v.fillApplicationAccessAndDuration(System.currentTimeMillis());
        v.fillApplicationStartAndDuration(System.currentTimeMillis());
        v.fillApplicationStartAndActiveCDuration(System.currentTimeMillis());
        ((FoxITApplication) this.getApplication()).startActivityTransitionTimer();
    }

}
