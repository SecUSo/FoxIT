package com.bp;

import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;

import static com.bp.Analysis.combineArraysP2;

/**
 * Created by noah on 11/8/16.
 */
/**
 * fetches all installed apps and the device settings to pass it into the database
 *
 * @author Noah
 */
public class ExternAnalysis extends AsyncTask {

    Context analysis;

    public ExternAnalysis(Context con) {
        analysis=con;
    }

    @Override
    /**
     * fetches all installed apps and the device settings to pass it into the database
     *
     * @author Noah
     */
    protected Object doInBackground(Object[] objects) {
// Get all apps
        ((Analysis)objects[0]).getALL_APPS();

        // Get all settings
        Uri uri_global = Settings.Global.CONTENT_URI;
        String[] proj_global = new String[]{Settings.Global.NAME, Settings.Global.VALUE};
        Uri uri_secure = Settings.Secure.CONTENT_URI;
        String[] proj_secure = new String[]{Settings.Secure.NAME, Settings.Secure.VALUE};
        ContentValues[] firstArray = ((Analysis)objects[0]).getSETTINGS(uri_global, proj_global);
        ContentValues[] secondArray = ((Analysis)objects[0]).getSETTINGS(uri_secure, proj_secure);
        ContentValues[] resultArray = combineArraysP2(firstArray, secondArray);
        resultArray[resultArray.length - 2] = ((Analysis)objects[0]).getLOCATION_MODE();
        resultArray[resultArray.length - 1] = ((Analysis)objects[0]).getPASSWORT_QUALITY();
return resultArray;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        new DBWrite(analysis).execute("addParamColumn",o);
        new DBWrite(analysis).execute("insertIndividualValue","firstrun","true");


    }
}
