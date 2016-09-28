package com.bp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Tim on 19.07.2016.
 */
public class MethodStateSwitch extends Method {
    @Override
    /**
     * method to jump to the described destination
     * @author Tim
     * @param destination string to be fetched from the android.provider.Settings class describing the settingPage to jump to
     */
    public void method(String destination) {
        try{
            Intent intent = new Intent(destination);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }catch(ActivityNotFoundException e){

            Log.d("Error", "parameter for methodStateSwitch was wrong");
        }
    }
}
