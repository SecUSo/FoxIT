package com.foxyourprivacy.f0x1t.asynctasks;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.foxyourprivacy.f0x1t.DBHandler;
import com.foxyourprivacy.f0x1t.ValueKeeper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by noah on 11.12.16.
 */

public class DBUploadTask extends AsyncTask<Activity, Void, String> {

    // String URL ="http://localhost/upload.php";//"gs://foxit-990c7.appspot.com/uploads/dbs/";
    @Override
    protected String doInBackground(Activity... params) {
        String filepath = "/data/" + "com.foxyourprivacy.f0x1t" + "/databases/" + DBHandler.DB_NAME;
        String uploadURL = "https://foxit.secuso.org/php/upload.php";
        String user = ValueKeeper.getInstance().getVpnCode();
        String timestamp = String.valueOf(System.currentTimeMillis());
        try {
            DataOutputStream outstream;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1024 * 1024;
            File data = Environment.getDataDirectory();
            File outFile = new File(data, filepath);
            NetworkInfo netInfo = ((ConnectivityManager) params[0].getSystemService(Context.CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected() && outFile.isFile()) {
                Boolean httpsworked = false;

                try {

                    FileInputStream inStream = new FileInputStream(outFile);
                    URL url = new URL(uploadURL);
                    HttpsURLConnection sconnection = (HttpsURLConnection) url.openConnection();
                    sconnection.setConnectTimeout(5000);
                    sconnection.setDoInput(true);
                    sconnection.setDoOutput(true);
                    sconnection.setUseCaches(false);
                    sconnection.setRequestMethod("POST");
                    sconnection.setRequestProperty("Connection", "Keep-Alive");
                    sconnection.setRequestProperty("ENCTYPE", "multipart/form-data");
                    sconnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    sconnection.setRequestProperty("uploaded_file", user + "_" + timestamp + DBHandler.DB_NAME);

                    outstream = new DataOutputStream(sconnection.getOutputStream());

                    //upload password
                    outstream.writeBytes(twoHyphens + boundary + lineEnd);
                    outstream.writeBytes("Content-Disposition: form-data; name=\"pass\"" + lineEnd);
                    outstream.writeBytes("Content-Type: text/plain; charset=US-ASCII" + lineEnd);
                    outstream.writeBytes("Content-Transfer-Encoding: 8bit" + lineEnd);
                    outstream.writeBytes(lineEnd);
                    outstream.writeBytes("4451f14b86cb62b6e262fb2b959f0b5bf1997850be8c1f497b846d6d5fa903fe" + lineEnd);

                    //upload file with description
                    outstream.writeBytes(twoHyphens + boundary + lineEnd);
                    outstream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + user + "_" + timestamp + DBHandler.DB_NAME + "\"" + lineEnd);
                    outstream.writeBytes(lineEnd);

                    bytesAvailable = inStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = inStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        outstream.write(buffer, 0, bufferSize);
                        bytesAvailable = inStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = inStream.read(buffer, 0, bufferSize);
                    }

                    outstream.writeBytes(lineEnd);
                    outstream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    int responseCode = sconnection.getResponseCode();
                    String responseMessage = sconnection.getResponseMessage();
                    httpsworked = true;


                    InputStream is = sconnection.getInputStream();
                    BufferedReader read = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = read.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    is.close();
                    read.close();
                    inStream.close();
                    outstream.flush();
                    outstream.close();
                    Log.d("DBUpload", "https response is: " + responseCode + responseMessage);
                    Log.d("DBUpload", "https echo: " + response.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    httpsworked = false;
                } catch (IOException e) {
                    e.printStackTrace();
                    httpsworked = false;
                }
                if (!httpsworked) {
                    try {
                        URL url = new URL(uploadURL.replace("https", "http"));
                        FileInputStream inStream = new FileInputStream(outFile);
                        HttpURLConnection connection;
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        connection.setUseCaches(false);
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Connection", "Keep-Alive");
                        connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                        connection.setRequestProperty("uploaded_file", user + "_" + timestamp + DBHandler.DB_NAME);

                        outstream = new DataOutputStream(connection.getOutputStream());

                        //upload password
                        outstream.writeBytes(twoHyphens + boundary + lineEnd);
                        outstream.writeBytes("Content-Disposition: form-data; name=\"pass\"" + lineEnd);
                        outstream.writeBytes("Content-Type: text/plain; charset=US-ASCII" + lineEnd);
                        outstream.writeBytes("Content-Transfer-Encoding: 8bit" + lineEnd);
                        outstream.writeBytes(lineEnd);
                        outstream.writeBytes("4451f14b86cb62b6e262fb2b959f0b5bf1997850be8c1f497b846d6d5fa903fe" + lineEnd);

                        //upload file with description
                        outstream.writeBytes(twoHyphens + boundary + lineEnd);
                        outstream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                + user + "_" + timestamp + DBHandler.DB_NAME + "\"" + lineEnd);
                        outstream.writeBytes(lineEnd);

                        bytesAvailable = inStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];
                        bytesRead = inStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {
                            outstream.write(buffer, 0, bufferSize);
                            bytesAvailable = inStream.available();
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = inStream.read(buffer, 0, bufferSize);
                        }

                        outstream.writeBytes(lineEnd);
                        outstream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                        int responseCode = connection.getResponseCode();
                        String responseMessage = connection.getResponseMessage();

                        InputStream is = connection.getInputStream();
                        BufferedReader read = new BufferedReader(new InputStreamReader(is));
                        String line;
                        StringBuilder response = new StringBuilder();
                        while ((line = read.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        read.close();
                        Log.d("DBUpload", "http response is: " + responseCode + responseMessage);
                        Log.d("DBUpload", "http echo: " + response.toString());

                        is.close();
                        read.close();
                        inStream.close();
                        outstream.flush();
                        outstream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "done.";


    }
}
