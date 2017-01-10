package com.foxyourprivacy.f0x1t.AsyncTasks;

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
        } else if (objects[0] == "updateLessions") {
            dbHandler.updateLessions((ArrayList<String[]>) objects[1]);
        } else if (objects[0] == "updateClasses") {
            dbHandler.updateClasses((ArrayList<String[]>) objects[1]);
        } else if (objects[0] == "updatePermissions") {
            dbHandler.updatePermissions((ArrayList<String[]>) objects[1]);
        } else if (objects[0] == "changeLectionToRead") {
            dbHandler.changeLectionToRead((String) objects[1]);
        } else if (objects[0] == "changeLectionToSolved") {
            dbHandler.changeLectionToSolved((String) objects[1]);
        } else if (objects[0] == "changeLectionToUnlocked") {
            dbHandler.changeLectionToUnlocked((String) objects[1]);
        } else if (objects[0] == "setLectionNextFreeTime") {
            dbHandler.setLectionNextFreeTime((String) objects[1], (long) objects[2]);
        } else if (objects[0] == "updateSettingDescriptions") {
            dbHandler.updateSettingDescriptions((ArrayList<String[]>) objects[1]);
        } else if (objects[0] == "insertIndividualData") {
            dbHandler.insertIndividualData((HashMap<String, String>) objects[1]);
        } else if (objects[0] == "changeIndividualValue") {
            dbHandler.changeIndividualValue((String) objects[1], (String) objects[2]);
        } else if (objects[0] == "insertIndividualValue") {
            dbHandler.insertIndividualValue((String) objects[1], (String) objects[2]);
        } else if (objects[0] == "clearAndSetValueKeeper") {
            dbHandler.clearValueKeeper();
            dbHandler.insertIndividualData((HashMap<String, String>) objects[1]);
        } else if (objects[0] == "clearValueKeeper") {
            dbHandler.clearValueKeeper();
        }

        dbHandler.close();
        return null;
    }
}
