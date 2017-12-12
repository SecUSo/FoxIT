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

public class DBRead extends AsyncTask<String, Void, Object> {
    Context context;

    public DBRead(Context thecontext) {
        context = thecontext;
    }

    @Override
    protected Object doInBackground(String... objects) {
        DBHandler dbHandler = new DBHandler(context, null, null, 1);
        Object result;
        if (objects[0] == "getClasses") {
            result = dbHandler.getClasses();
        } else if (objects[0] == "getNumberOfSolvedLessons") {
            result = dbHandler.getNumberOfSolvedLessons(objects[1]);
        } else if (objects[0] == "getLessonsFromDB") {
            result = dbHandler.getLessonsFromDB(objects[1]);
        } else if (objects[0] == "getPermissionDescription") {
            result = dbHandler.getPermissionDescription(objects[1]);
        } else if (objects[0] == "getSettingsFromDB") {
            result = dbHandler.getSettingsFromDB();
        } else if (objects[0] == "getIndividualData") {
            result = dbHandler.getIndividualData();
        } else if (objects[0] == "getIndividualValue") {
            result = dbHandler.getIndividualValue(objects[1]);
        }else{
            result = "no method found";
        }


        dbHandler.close();
        return result;
    }
}
