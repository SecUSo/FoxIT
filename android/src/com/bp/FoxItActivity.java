package com.bp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Calendar;

/**
 * device and user specific content is retrieved and saved in database
 * including installed apps, respective permissions & settings.
 */
public class FoxItActivity extends AppCompatActivity {

    private static Context context;

    /**
     * @author Tim
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FoxItActivity.context = getApplicationContext();
        ValueKeeper v=ValueKeeper.getInstance();
        FoxItApplication myApp = (FoxItApplication) this.getApplication();
        v.reviveInstance();
    }


    @Override
    public void onStart()
    {
        super.onResume();


        FoxItActivity.context = getApplicationContext();
        ValueKeeper v=ValueKeeper.getInstance();
        FoxItApplication myApp = (FoxItApplication) this.getApplication();
        if(v.getFreshlyStartet()){

            Intent mServiceIntent = new Intent(this, BackgroundService.class);
            startService(mServiceIntent);
            //v.reviveInstance();

            //  new reviveValueTask().execute();

            v.setTimeOfFirstAccess(System.currentTimeMillis());
        }


        if (myApp.wasInBackground||v.getFreshlyStartet())
        {
            Log.d("MyApp","Was in Background");
            v.setFreshlyStartet(false);
            v.setTimeOfLastAccess(System.currentTimeMillis());
        }

        myApp.stopActivityTransitionTimer();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        ValueKeeper v=ValueKeeper.getInstance();
        v.fillApplicationAccessAndDuration(System.currentTimeMillis());
        v.fillApplicationStartAndDuration(System.currentTimeMillis());
        v.fillApplicationStartAndActiveCDuration(System.currentTimeMillis());
        ((FoxItApplication) this.getApplication()).startActivityTransitionTimer();
        new SaveValueTask().execute();
    }

    public static Context getAppContext() {
        return FoxItActivity.context;
    }

    public int getNumberOfCurrentEvaluation(){
        ValueKeeper v=ValueKeeper.getInstance();
        return v.getCurrentEvaluation();
    }


    public boolean shouldEvaluationBeDisplayed(){
        ValueKeeper v=ValueKeeper.getInstance();
        Calendar currentTime = Calendar.getInstance();

        Log.d("MyApp","currentEvalin Fox:"+Integer.toString(v.getCurrentEvaluation()));

        if(v.timeOfEvaluation.length>v.getCurrentEvaluation()){
            return v.timeOfEvaluation[v.getCurrentEvaluation()]<currentTime.getTimeInMillis();}else{
            return false;
        }
    }


    public boolean setTrophyUnlocked(String trophyName){
       ValueKeeper v=ValueKeeper.getInstance();
        if(v.animationList.containsKey(trophyName)){
            v.animationList.put(trophyName,true);
            return true;
        }else{
            v.animationList.put(trophyName,true);
        return false;
        }

    }

}
