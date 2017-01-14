package com.foxyourprivacy.f0x1t.Activities;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.foxyourprivacy.f0x1t.AsyncTasks.SaveValueTask;
import com.foxyourprivacy.f0x1t.BackgroundService;
import com.foxyourprivacy.f0x1t.FoxITApplication;
import com.foxyourprivacy.f0x1t.Fragments.TrophyNotificationFragment;
import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.ValueKeeper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * device and user specific content is retrieved and saved in database
 * including installed apps, respective permissions & settings.
 */
public class FoxITActivity extends AppCompatActivity {


    private static Context context;

    public static Context getAppContext() {
        return FoxITActivity.context;
    }

    /**
     * @author Tim
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FoxITActivity.context = getApplicationContext();
        ValueKeeper v = ValueKeeper.getInstance();
        FoxITApplication myApp = (FoxITApplication) this.getApplication();
        if (v.getSizeOfAppStarts() > 1) {
            setTrophyUnlocked("Power User");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();

        FoxITActivity.context = getApplicationContext();
        ValueKeeper v = ValueKeeper.getInstance();
        FoxITApplication myApp = (FoxITApplication) this.getApplication();
        Log.d("FITActivity","freshlystarted: "+v.getFreshlyStarted());
        if (v.getFreshlyStarted()) {

            Intent mServiceIntent = new Intent(this, BackgroundService.class);
            startService(mServiceIntent);
            v.reviveInstance();
            ArrayList<String> deletedApps = v.compareAppLists();
            v.deinstalledApps.addAll(deletedApps);

            Calendar c = Calendar.getInstance();
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY) + 2;


            if (timeOfDay >= 5 && timeOfDay < 9) {
                v.increaseNumberMorning();
                if (v.getNumberOfTimesOpenedAtMorning() > 4) {
                    setTrophyUnlocked("Early Bird");
                }


            } else {
                if (timeOfDay >= 16 || timeOfDay < 5) {

                    v.increaseNumberNight();
                    if (v.getNumberOfTimesOpenedAtNight() > 4) {
                        setTrophyUnlocked("Nachteule");
                    }
                }


            }


            //  new reviveValueTask().execute();
            v.addAppStarts(System.currentTimeMillis());
            v.setTimeOfFirstAccess(System.currentTimeMillis());



        }


        if (myApp.wasInBackground || v.getFreshlyStarted()) {
            Log.d("MyApp", "Was in Background");
            v.setFreshlyStarted(false);
            v.setTimeOfLastAccess(System.currentTimeMillis());
        }

        if(v.getTimeOfLastServerAccess()+259200000<System.currentTimeMillis()){
            v.setTimeOfThisServerAccess();


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
        v.saveInstance();
    }

    public int getNumberOfCurrentEvaluation() {
        ValueKeeper v = ValueKeeper.getInstance();
        return v.getCurrentEvaluation();
    }


    public boolean shouldEvaluationBeDisplayed() {
        ValueKeeper v = ValueKeeper.getInstance();
        Calendar currentTime = Calendar.getInstance();
        int numberOfEvaluation = 0;
        if (v.getCurrentEvaluation() == 0) {
            //   if(db.getIndividualValue("currentEvaluation")!=null){ numberOfEvaluation=Integer.valueOf(db.getIndividualValue("currentEvaluation"));}
        } else {
            numberOfEvaluation = v.getCurrentEvaluation();
        }
        Log.d("MyApp", "currentEvalin Fox:" + Integer.toString(v.getCurrentEvaluation()));

        return v.timeOfEvaluation.length > numberOfEvaluation && v.timeOfEvaluation[numberOfEvaluation] < currentTime.getTimeInMillis();
    }


    public boolean setTrophyUnlocked(String trophyName) {
        ValueKeeper v = ValueKeeper.getInstance();
        if (!(v.trophyList.containsKey(trophyName) && v.trophyList.get(trophyName))) {
            displayTrophyUnlocked(trophyName);
        }
        if (v.trophyList.containsKey(trophyName)) {
            v.trophyList.put(trophyName, true);
            return true;
        } else {
            v.trophyList.put(trophyName, true);
            return false;
        }

    }


    public void displayTrophyUnlocked(String trophyName) {

        //add the acornCountFragment to the activity's context
        final FragmentManager manager = this.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        final TrophyNotificationFragment trophyNotification = new TrophyNotificationFragment();
        Bundle name = new Bundle();
        name.putString("name", trophyName);
        trophyNotification.setArguments(name);
        //add the fragment to the count_frame RelativeLayout
        if (this.findViewById(R.id.count_frame) != null) {
            transaction.add(R.id.count_frame, trophyNotification, "trophy");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }

        //add the animation
        final Handler handler = new Handler();
        //after 1250ms the old grey text is replaced by the new black acornCount

        //if(this.findViewById(R.id.count_frame)!=null) {
        //after 4000ms the Fragment disappears
        //  handler.postDelayed(new Runnable() {
        //    @Override
        //  public void run() {
        //    FragmentTransaction transaction = manager.beginTransaction();
        //  transaction.remove(trophyNotification);
        //  transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //  transaction.commit();
        // }
        //}, 8000);
        //}
    }

}
