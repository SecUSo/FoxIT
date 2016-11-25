package com.bp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ich on 13.11.2016.
 */

public class BackgroundService extends Service {

    Context context;
    List<ApplicationInfo> apps;

    @Override
public int onStartCommand(Intent intent,int flags,int startId){
apps=fetchALL_APPS();

Log.d("MyApp","Service running");
context =getApplicationContext();
Timer timer =new Timer();
        timer.schedule(new CheckApps(),0,5000);


        return Service.START_NOT_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class CheckApps extends TimerTask {
        public void run(){
            String isChange= checkForChanges();
            if(!isChange.equals("false")){
                Log.d("MyApp","Hello World!xxx");

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.paw)
                                .setContentTitle("Neue Umfrage verfügbar!")
                                .setContentText("App"+isChange +" wurde deinstalliert");
// Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(context, Home.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(Home.class);
// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
                mNotificationManager.notify(15, mBuilder.build());
            }
        }}



   public String checkForChanges() {

       List<ApplicationInfo> newApps = fetchALL_APPS();
       if (apps.size() <= newApps.size()) {
           Log.d("MyApp","i:"+Integer.toString(apps.size())+"n:"+Integer.toString(newApps.size()));
           return "false";
       } else {
           Log.d("MyApp","i:"+Integer.toString(apps.size())+"n:"+Integer.toString(newApps.size()));
           final PackageManager pm = context.getPackageManager();
           for(int i=0;i<apps.size();i++){
            if(!pm.getApplicationLabel(apps.get(i)).toString().equals(pm.getApplicationLabel(newApps.get(i)).toString())){

                return pm.getApplicationLabel(apps.get(i)).toString();
            }
           }









           Log.d("MyApp","i:"+Integer.toString(apps.size())+"n:"+Integer.toString(newApps.size())+"True");
           return "true";
       }

   }

    /**
     * methodLeft to retrieve all apps without entering the database
     * @author Tim
     * @return list of retrieved apps
     */
    public List<ApplicationInfo> fetchALL_APPS(){
        if(context==null){Log.d("MyApp","context is Null");}
        final PackageManager pm = getApplicationContext().getPackageManager();
        //get a list of installed apps.
        if(pm==null){Log.d("MyApp","pm is Null");}
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        Collections.sort(packages, new Comparator<ApplicationInfo>() {
            @Override
            public int compare(ApplicationInfo lhs, ApplicationInfo rhs) {

                String leftAppName=pm.getApplicationLabel(lhs).toString();
                String rightAppName=pm.getApplicationLabel(rhs).toString();

                if (leftAppName.equals(rightAppName)) {
                    return 0;
                }
                if (leftAppName == null) {
                    return -1;
                }
                if (rightAppName == null) {
                    return 1;
                }
                return leftAppName.compareTo(rightAppName);
            }

        });

        return packages;
    }





}
