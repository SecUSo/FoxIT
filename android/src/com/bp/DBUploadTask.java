package com.bp;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by noah on 11.12.16.
 */

public class DBUploadTask extends AsyncTask<Activity,Void,String> {

   // String URL ="http://localhost/upload.php";//"gs://foxit-990c7.appspot.com/uploads/dbs/";
    @Override
    protected String doInBackground(Activity... params) {
        String filepath= "/data/" + "com.bp"+"/databases/"+DBHandler.DB_NAME;;
        String uploadURL="http://192.168.2.3/files/upload.php";
        String user = ValueKeeper.getInstance().getVpnCode();
        String timestamp = String.valueOf(System.currentTimeMillis());
        try{
            HttpURLConnection connection;
            DataOutputStream outstream;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1024 * 1024;
            File data = Environment.getDataDirectory();
            File outFile = new File(data,filepath);
            NetworkInfo netInfo = ((ConnectivityManager) params[0].getSystemService(params[0].CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected()&&outFile.isFile()){
                try{
                    FileInputStream inStream = new FileInputStream(outFile);
                    URL url = new URL(uploadURL);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Connection","Keep-Alive");
                    connection.setRequestProperty("ENCTYPE","multipart/form-data");
                    connection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                    connection.setRequestProperty("uploaded_file",user+"_"+timestamp+DBHandler.DB_NAME);

                    outstream = new DataOutputStream(connection.getOutputStream());
                    outstream.writeBytes(twoHyphens+boundary+lineEnd);
                    outstream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + user+"_"+timestamp+DBHandler.DB_NAME + "\"" + lineEnd);
                    outstream.writeBytes(lineEnd);

                    bytesAvailable = inStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    buffer= new byte[bufferSize];
                    bytesRead= inStream.read(buffer,0,bufferSize);

                    while (bytesRead > 0){
                        outstream.write(buffer,0,bufferSize);
                        bytesAvailable=inStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = inStream.read(buffer,0,bufferSize);
                    }

                    outstream.writeBytes(lineEnd);
                    outstream.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                    int responseCode = connection.getResponseCode();
                    String responseMessage = connection.getResponseMessage();

                    InputStream is = connection.getInputStream();
                    BufferedReader read = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuffer response = new StringBuffer();
                    while((line = read.readLine()) != null){
                        response.append(line);
                        response.append('\r');
                    }
                    read.close();
                    Log.d("DBUpload","response is: "+responseCode+responseMessage);
                    Log.d("DBUpload","echo: "+response.toString());

                    is.close();
                    read.close();
                    inStream.close();
                    outstream.flush();
                    outstream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "done.";


    }
}
