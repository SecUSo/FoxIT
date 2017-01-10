package com.foxyourprivacy.f0x1t;

import android.app.Application;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ich on 18.10.2016.
 */

public class FoxITApplication extends Application {

    private final long MAX_ACTIVITY_TRANSITION_TIME_MS = 10000;
    public boolean wasInBackground;
    private Timer mActivityTransitionTimer;
    private TimerTask mActivityTransitionTimerTask;

    public void startActivityTransitionTimer() {
        this.mActivityTransitionTimer = new Timer();
        this.mActivityTransitionTimerTask = new TimerTask() {
            public void run() {
                FoxITApplication.this.wasInBackground = true;
            }
        };

        this.mActivityTransitionTimer.schedule(mActivityTransitionTimerTask,
                MAX_ACTIVITY_TRANSITION_TIME_MS);
    }

    public void stopActivityTransitionTimer() {
        if (this.mActivityTransitionTimerTask != null) {
            this.mActivityTransitionTimerTask.cancel();
        }

        if (this.mActivityTransitionTimer != null) {
            this.mActivityTransitionTimer.cancel();
        }

        this.wasInBackground = false;
    }

}

