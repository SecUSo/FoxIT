package com.foxyourprivacy.f0x1t.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.foxyourprivacy.f0x1t.DBHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by noah on 11/8/16.
 */

public class CSVUpdateTask extends AsyncTask<Object, Void, Integer> {
    private Context thecontext;

    public CSVUpdateTask(Context context) {
        thecontext = context;
    }

    @Override
    protected Integer doInBackground(Object... objects) {
        String url = (String) objects[0];
        if (objects[1] != null) {
            DBHandler dbHandler = new DBHandler(thecontext, null, null, 0);
            Log.d("CSVUpdater", (String) objects[1]);
            InputStream is = null;
            try {
                is = getStream(url);
                if (is == null) {
                    is = getHttpStream(url);
                }
            } catch (Exception e) {
                Log.d("CSVDownTask", "inputStream not valid" + e);
            }

            if (is != null) {
                if (objects[1].equals("permissions")) dbHandler.updatePermissions(readStream(is));
                if (objects[1].equals("lessions")) dbHandler.updateLessions(readLessionStream(is));
                if (objects[1].equals("classes")) dbHandler.updateClasses(readStream(is));
                if (objects[1].equals("settings"))
                    dbHandler.updateSettingDescriptions(readStream(is));
                dbHandler.close();
                return 0;
            } else if (objects.length > 2 && objects[2] != null) {
                Log.d("CSVDownTask", "InputStream null, Internet Failure.");
                if (objects[1].equals("permissions"))
                    dbHandler.updatePermissions((ArrayList) objects[2]);
                if (objects[1].equals("lessions")) dbHandler.updateLessions((ArrayList) objects[2]);
                if (objects[1].equals("classes")) dbHandler.updateClasses((ArrayList) objects[2]);
                if (objects[1].equals("settings"))
                    dbHandler.updateSettingDescriptions((ArrayList) objects[2]);
                dbHandler.close();

                return -1;
            }
        }
        return -2;
    }


    private InputStream getStream(String url) {
        InputStream is = null;
        try {
            //configuration of the connection
            URL theURL = new URL(url);
            if (theURL.getProtocol().toLowerCase().equals("https")) {
                HttpsURLConnection scon = (HttpsURLConnection) theURL.openConnection();
                if (scon != null) {
                    scon.setConnectTimeout(5000);
                    scon.setReadTimeout(5000);
                    scon.setRequestMethod("GET");
                    scon.setDoInput(true);
                    //start of the connection
                    scon.connect();
                    int resCode = scon.getResponseCode();
                    Log.d("CSVUpdateTask", "con response is: " + resCode);
                    is = scon.getInputStream();
                }
            }
            if (is == null) {
            }
        } catch (MalformedURLException e) {
            Log.d("CSVUpdateTask:", "URL misformed! " + e);
        } catch (ProtocolException e) {
            Log.d("CSVUpdateTask:", "protocol error! " + e);
        } catch (IOException e) {
            Log.d("CSVUpdateTask:", "https input stream not valid! :(" + e);
        }
        return is;
    }

    private InputStream getHttpStream(String url) {
        InputStream is = null;
        try {

            URL theURL = new URL(url.replace("https", "http"));
            HttpURLConnection con = (HttpURLConnection) theURL.openConnection();
            if (con != null) {
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);
                con.setRequestMethod("GET");
                con.setDoInput(true);
                //start of the connection
                con.connect();
                int resCode = con.getResponseCode();
                Log.d("CSVUpdateTask", "con response is: " + resCode);
                is = con.getInputStream();
            }
        } catch (ProtocolException e) {
            Log.d("CSVUpdateTask:", "protocol error! " + e);
        } catch (MalformedURLException e) {
            Log.d("CSVUpdateTask:", "URL misformed! " + e);
        } catch (IOException e) {
            Log.d("CSVUpdateTask:", "http input stream not valid! :(" + e);
        }
        return is;
    }

    private ArrayList readStream(InputStream is) {
        ArrayList result = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            String templine;
            while ((templine = br.readLine()) != null) {
                String[] csvrow = templine.split(";");
                result.add(csvrow);
            }
        } catch (IOException e) {
            throw new RuntimeException("Input Stream couldn't be read properly: " + e);
        } finally {
            try {
                is.close();
                br.close();
            } catch (IOException e) {
                throw new RuntimeException("Input Stream couldn't be closed properly: " + e);
            }
        }
        return result;

    }

    public ArrayList readLessionStream(InputStream is) {
        ArrayList result = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            String templine;
            String csvrow = "";
            while ((templine = br.readLine()) != null) {
                csvrow += templine;
                if (templine.matches(".*;;;")) {
                    String[] rowarray = csvrow.split(";");
                    result.add(rowarray);
                    // Log.d("SettingsActivity", "row: " + csvrow);
                    csvrow = "";
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Lession stream couldn't be read properly: " + e);
        } finally {
            try {
                is.close();
                br.close();
            } catch (IOException e) {
                throw new RuntimeException("Input Stream couldn't be closed properly: " + e);
            }
        }
        return result;

    }

}
