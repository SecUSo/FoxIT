package com.bp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

/**
 * Created by noah on 11/9/16.
 */

public class GetSettingsAsync extends AsyncTask<Void,Void,String[]> {

    Context context;

    public GetSettingsAsync(Context con) {
        context=con;
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        DBHandler dbHandler = new DBHandler(context,null,null,1);
        String[] result = dbHandler.getSettingsFromDB();
        //dbHandler.insertIndividualValue("firstrun","true");
        ValueKeeper v=ValueKeeper.getInstance();
        v.analysisDoneBefore=true;
        dbHandler.insertIndividualValue("analysisDoneBefore",Boolean.toString(true));
        dbHandler.close();
        return result;
    }

    @Override
    protected void onPostExecute(final String[] strings) {
        super.onPostExecute(strings);
        // Timer: Open StartScreen after 7 Seconds
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(context.getApplicationContext(), StartScreen.class);
                i.putExtra("settings",strings);
                context.startActivity(i);
            }
        }, 3000L);
    }
}
