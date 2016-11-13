package com.bp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by noah on 11/8/16.
 */

public class CSVDownloadTask extends AsyncTask<String,Void,Void> {
    private Context thecontext;

    public CSVDownloadTask(Context context) {
        thecontext=context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String url = strings[0];
        if (strings[1]!=null){
            DBHandler dbHandler = new DBHandler(thecontext,null, null,0);
            Log.d("CSVUpdater",strings[1]);
            if (strings[1].equals("permissions")) dbHandler.updatePermissions(readStream(getStream(url)));
            if (strings[1].equals("lessions")) dbHandler.updateLessions(readStream(getStream(url)));
            if (strings[1].equals("classes")) dbHandler.updateClasses(readStream(getStream(url)));
            if (strings[1].equals("settings")) dbHandler.updateSettingDescriptions(readStream(getStream(url)));

            dbHandler.close();
        }


        return null;
    }

    private InputStream getStream(String url) {
        InputStream is = null;
        try{
            //configuration of the connection
            URL theURL = new URL(url);
            HttpURLConnection con = (HttpURLConnection) theURL.openConnection();
            con.setConnectTimeout(15000);
            con.setReadTimeout(10000);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            //start of the connection
            con.connect();
            int resCode = con.getResponseCode();
            Log.d("CSVDownloadTask", "con response is: "+resCode);
            is = con.getInputStream();
        } catch (MalformedURLException e) {
            Log.d("CSVDownloadTask:", "URL misformed! "+e);
        } catch (ProtocolException e) {
            Log.d("CSVDownloadTask:", "protocol error! "+e);
        } catch (IOException e) {
            Log.d("CSVDownloadTask:", "input stream not valid! "+e);
        }
        return is;
    }

    private ArrayList readStream(InputStream is){
        ArrayList result = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try{
            String templine;
            while ((templine=br.readLine())!=null){
                String[] csvrow = templine.split(";");
                result.add(csvrow);
            }
        } catch (IOException e){
            throw new RuntimeException("Input Stream couldn't be read properly: "+e);
        } finally {
            try {
                is.close();
                br.close();
            } catch (IOException e){
                throw new RuntimeException("Input Stream couldn't be closed properly: "+e);
            }
        }
        return result;

    }


}
