package com.foxyourprivacy.f0x1t.asynctasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

import com.foxyourprivacy.f0x1t.DBHandler;
import com.foxyourprivacy.f0x1t.ValueKeeper;
import com.foxyourprivacy.f0x1t.activities.AnalysisResults;

/**
 * Created by noah on 11/9/16.
 */

public class GetSettingsAsync extends AsyncTask<Void, Void, String[]> {

    Context context;

    public GetSettingsAsync(Context con) {
        context = con;
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        DBHandler dbHandler = new DBHandler(context, null, null, 1);
        String[] result = dbHandler.getSettingsFromDB();
        //dbHandler.insertIndividualValue("firstrun","true");
        ValueKeeper v = ValueKeeper.getInstance();
        v.analysisDoneBefore = true;
        dbHandler.insertIndividualValue("analysisDoneBefore", Boolean.toString(true));
        dbHandler.close();
        return result;
    }

    @Override
    protected void onPostExecute(final String[] strings) {
        super.onPostExecute(strings);
        // Timer: Open AnalysisResults after 7 Seconds
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(context.getApplicationContext(), AnalysisResults.class);
                i.putExtra("settings", strings);
                context.startActivity(i);
            }
        }, 3000L);
    }
}
