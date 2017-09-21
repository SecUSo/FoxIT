package com.foxyourprivacy.f0x1t.asynctasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.foxyourprivacy.f0x1t.DBHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by noah on 11/8/16.
 */

public class DBWrite extends AsyncTask<Object, Void, Void> {
    Context context;

    public DBWrite(Context thecontext) {
        context = thecontext;
    }

    @Override
    protected Void doInBackground(Object... objects) {
        DBHandler dbHandler = new DBHandler(context, null, null, 1);
        if (objects[0] == "addParamColumn") {
            dbHandler.addParamColumn((ContentValues[]) objects[1]);
        } else if (objects[0] == "addAppColumn") {
            dbHandler.addAppColumn((ContentValues[]) objects[1]);
        } else if (objects[0] == "updateLessons") {
            dbHandler.updateLessons((ArrayList<String[]>) objects[1]);
        } else if (objects[0] == "updateClasses") {
            dbHandler.updateClasses((ArrayList<String[]>) objects[1]);
        } else if (objects[0] == "updatePermissions") {
            dbHandler.updatePermissions((ArrayList<String[]>) objects[1]);
        } else if (objects[0] == "changeLessonToRead") {
            dbHandler.changeLessonToRead((String) objects[1], (String) objects[2]);
        } else if (objects[0] == "changeLessonToSolved") {
            dbHandler.changeLessonToSolved((String) objects[1]);
        } else if (objects[0] == "changeLessonToUnlocked") {
            dbHandler.changeLessonToUnlocked((String) objects[1]);
        } else if (objects[0] == "setLessonNextFreeTime") {
            dbHandler.setLessonNextFreeTime((String) objects[1], (String) objects[2], (long) objects[3]);
        } else if (objects[0] == "updateSettingDescriptions") {
            dbHandler.updateSettingDescriptions((ArrayList<String[]>) objects[1]);
        } else if (objects[0] == "insertIndividualData") {
            dbHandler.insertIndividualData((HashMap<String, String>) objects[1]);
        } else if (objects[0] == "changeIndividualValue") {
            dbHandler.changeIndividualValue((String) objects[1], (String) objects[2]);
        } else if (objects[0] == "insertIndividualValue") {
            dbHandler.insertIndividualValue((String) objects[1], (String) objects[2]);
        } else if (objects[0] == "clearAndSetValueKeeper") {
            dbHandler.clearAppsFromVK();
            dbHandler.insertIndividualData((HashMap<String, String>) objects[1]);
        } else if (objects[0] == "clearAppsFromVK") {
            dbHandler.clearAppsFromVK();
        }

        dbHandler.close();
        return null;
    }
}
