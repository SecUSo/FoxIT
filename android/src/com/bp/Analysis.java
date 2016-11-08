package com.bp;

import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

/**
 * device and user specific content is retrieved and saved in database
 * including installed apps, respective permissions & settings.
 */
public class Analysis extends FoxItActivity {

    DBHandler dbHandler;
    Toolbar toolbar;

    /**
     * while all the data is retrieved, a loading symbol will be shown on screen
     *
     * @param savedInstanceState
     * @author Tim
     * @author Noah
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        //sets our toolbar as the action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        //analyse();
        new ExternAnalysis(this).execute(this);
        // Timer: Open StartScreen after 7 Seconds
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), StartScreen.class);
                startActivity(i);
            }
        }, 3000L);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * combines two arrays
     *
     * @param first  array
     * @param second array
     * @return result a new array containing the content of input arrays
     * @author Noah
     */
    public static ContentValues[] combineArraysP2(ContentValues[] first, ContentValues[] second) {
        int length = first.length + second.length;
        ContentValues[] result = new ContentValues[length + 2];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * retrieves the names of installed apps on device as well as additional info, such as requested permissions
     * inserts data into database
     *
     * @author Noah
     */
    public void getALL_APPS() {
       // DBHandler dbHandler = new DBHandler(this, null, null, 1);
        final PackageManager pm = getPackageManager();

        // get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        StringBuilder sb = new StringBuilder();
        StringBuffer bb = new StringBuffer(sb);

        ContentValues[] appValues = new ContentValues[packages.size()];
        ContentValues tempValue = new ContentValues(3);
        int counter = 0;

        for (ApplicationInfo applicationInfo : packages) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);

                // get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;
                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        bb.append(requestedPermissions[i] + ";");
                    }
                } else {
                    bb.append("NULL");
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.d("MyApp", "Error!"+e);
                e.printStackTrace();
            }

            tempValue.put(DBHandler.COLUMN_APPNAME, applicationInfo.packageName);
            tempValue.put(DBHandler.COLUMN_PERMISSIONS, bb.toString());
            tempValue.put(DBHandler.COLUMN_APPDESCRIPTION, pm.getApplicationLabel(applicationInfo).toString());
            appValues[counter] = new ContentValues(tempValue);
            tempValue.clear();
            counter++;
            bb.setLength(0);
        }
        new DBWrite(this).execute("addAppColumn",appValues);
        //dbHandler.addAppColumn(appValues);
    }


    /**
     * determine the state of the lock screen and writes it into the database
     *
     * @return 0      -none
     * 131072 -pin
     * 65536  -pattern
     * 262144 -password
     * 1      -API 23: existent protection, unclear which kind
     * 66     -exception
     * @author Tim
     */
    public ContentValues getPASSWORT_QUALITY() {
        int value;
        String name;
        ContentValues cv = new ContentValues(3);

        //In case of APK != M there is a LOCK_PATTERN_UTILS
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String LOCK_PATTERN_UTILS = "com.android.internal.widget.LockPatternUtils";
            name = "lock_pattern_utils";

            try {
                Class<?> lockPatternUtilsClass = Class.forName(LOCK_PATTERN_UTILS);
                Object lockPatternUtils = lockPatternUtilsClass.getConstructor(Context.class).newInstance(this);
                Method method = lockPatternUtilsClass.getMethod("getActivePasswordQuality");
                int lockProtectionLevel = Integer.valueOf(String.valueOf(method.invoke(lockPatternUtils)));
                value = lockProtectionLevel;
                // Then check if lockProtectionLevel == DevicePolicyManager.TheConstantForWhicheverLevelOfProtectionYouWantToEnforce, and return true if the check passes, false if it fails
            } catch (Exception ex) {
                ex.printStackTrace();
                value = 66;
            }
        }
        //In case of APK = M there is a KeyguardManager
        else {
            name = "keyguard_manager";
            KeyguardManager a = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if (a.isDeviceSecure()) {
                value = 1;
            } else {
                value = 0;
            }
        }
        cv.put(DBHandler.COLUMN_SETTING, name);
        cv.put(DBHandler.COLUMN_TYPE, 2);
        cv.put(DBHandler.COLUMN_INITIAL, value);
        return cv;
    }

    /**
     * determines the status of location mode and writes it into the database
     *
     * @return 0- LOCATION_MODE_OFF
     * 1- LOCATION_MODE_SENSORS_ONLY
     * 2- LOCATION_MODE_BATTERY_SAVING
     * 3- LOCATION_MODE_HIGH_ACCURACY
     * 6- API 23: An aber keine genauen Angaben
     * @author Tim
     */
    public ContentValues getLOCATION_MODE() {
        int locationMode = 0;
        String name;
        // If APK < KITKAT Settings.Secure.LOCATION_PROVIDERS_ALLOWED
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            name = "location_providers_allowed";

            String providers = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (TextUtils.isEmpty(providers)) {
                locationMode = 0;
            } else {
                if (providers.contains(LocationManager.GPS_PROVIDER)) {
                    locationMode = 6;
                }
            }
        }
        // If APK >= KITKAT Settings.Secure.LOCATION_MODE
        else {
            name = "location_mode";

            try {
                locationMode = Settings.Secure.getInt(this.getContentResolver(),
                        Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                locationMode = 0;
            }
        }
        ContentValues cv = new ContentValues(3);
        cv.put(DBHandler.COLUMN_SETTING, name);
        cv.put(DBHandler.COLUMN_INITIAL, locationMode);
        cv.put(DBHandler.COLUMN_TYPE, 2);
        return cv;
    }


    /**
     * takes a location of the android provider and inserts its parameter to the database
     *
     * @param uri
     * @param projection
     * @return int number of inserted parameters
     * @author Lena
     */
    ContentValues[] getSETTINGS(Uri uri, String[] projection) {

        ContentResolver cR = getContentResolver();
        String selection = null;
        String[] selectionArguments = null;
        String selectionOrder = null;
        String name = "";
        String value = "";
        Cursor cursor = cR.query(uri, projection, selection, selectionArguments, selectionOrder);
        cursor.moveToFirst();

        ContentValues[] contentValues = new ContentValues[cursor.getCount() - 1];
        ContentValues tempValue = new ContentValues(3);
        int counter = 0;
        int col = 0;
        int type = 0; // type: String

        // parse all lines of the table
        while (cursor.moveToNext()) {
            name = cursor.getString(col);
            // if (cursor.isLast()) return null;
            value = cursor.getString(col + 1);
            type = 0;
            if (value == null) type = 3;
            else {
                // type check: 0 = String 1 = boolean 2 = Number 3 = null
                if (value.matches("-?\\d+(\\.\\d+)?")) type = 2; // type: Number
                if (type == 2 && (value.equals("1") || value.equals("0"))) {
                    type = 1; // type: Boolean
                }
                // ignore location_mode setting, since it is imported separately
                if (!value.equalsIgnoreCase("location_mode")) {
                    tempValue.put(DBHandler.COLUMN_SETTING, name);
                    tempValue.put(DBHandler.COLUMN_INITIAL, value);
                    //Log.d("SETTINGS", name + "\n " + value + "\n");
                    tempValue.put(DBHandler.COLUMN_TYPE, type);
                    contentValues[counter] = new ContentValues(tempValue);
                    tempValue.clear();
                } else {
                    counter--;
                }
                counter++;
            }
        }
        cursor.close();
        return contentValues;
    }
}
