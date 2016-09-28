package com.bp;

/**
 * General Database-API
 * Created by noah on 29.05.16. :)
 * @author: Noah
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DBHandler extends SQLiteOpenHelper {

    //DB-Version is updated, when changes in structur apply
    private static final int DB_VERSION = 22;
    //table-names
    private static final String TABLE_APPS = "apps";
    private static final String TABLE_LESSIONS = "lessions";
    private static final String TABLE_CLASSES = "classes";
    private static final String TABLE_PERMISSIONS = "permissions";
    private static final String TABLE_SETTINGS = "rawdata";
    //column-names
    static final String COLUMN_APPNAME = "name";
    static final String COLUMN_PERMISSIONS = "permissions";
    static final String COLUMN_APPDESCRIPTION = "description";

    static final String COLUMN_SETTING = "parameter";
    static final String COLUMN_INITIAL = "initial";
    private static final String COLUMN_GOODNAME = "propername";
    private static final String COLUMN_SETTINGDESCRIPTION = "sdescription";
    //type: for readout - 0=String, 1=BOOLEAN, 2=int
    static final String COLUMN_TYPE = "type";

    private static final String COLUMN_LECTURENAME = "name";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_COURSE = "course";
    //0=not available yet; 1=not yet read; 2=not yet finished; 3=finished
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_LEVEL = "level";
    private static final String COLUMN_DELAY = "delay";
    private static final String COLUMN_FREETIME = "freeat";
    private static final String COLUMN_LECTURETYPE = "ltype";
    private static final String COLUMN_EICHELN = "eicheln";

    private static final String COLUMN_COURSENAME = "course_name";
    private static final String COLUMN_COURSEDESCRIPTION = "description";

    private static final String COLUMN_PERMISSIONNAME = "pname";
    private static final String COLUMN_PERMISSIONDESCRIPTION = "pdescription";
    private static final String COLUMN_PERMISSIONNICENAME = "propername";
    private static final String COLUMN_PERMISSIONLEVEL = "plevel";
    //file-name
    private static final String DB_NAME = "rawdata.db";

    /**
     * Constructor
     * @param context is most times the reference 'this' for refering
     * @param name name of the database
     * @param factory in pratice, irrelevant for us. (usually given null)
     * @param version version of the DB, for update purposes
     */
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    /**
     * executed at creation of a DB
     * @param db DB that is created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL-queries to create the tables with specified properties
        String query = "CREATE TABLE "+ TABLE_SETTINGS + "(" +
                COLUMN_SETTING + " TEXT PRIMARY KEY, " +
                COLUMN_TYPE + " INTEGER DEFAULT 0, " +
                COLUMN_INITIAL + " TEXT DEFAULT '-99', " +
                COLUMN_GOODNAME + " TEXT, " +
                COLUMN_SETTINGDESCRIPTION + " TEXT DEFAULT 'hier fehlt leider eine Beschreibung. Du kannst gerne eine anfordern!'" +
                ");";
        db.execSQL(query);
        String query2 = "CREATE TABLE "+ TABLE_APPS + "("+
                COLUMN_APPNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PERMISSIONS + " TEXT, " +
                COLUMN_APPDESCRIPTION + " TEXT " +
                ");";
        db.execSQL(query2);
        String query3 = "CREATE TABLE "+ TABLE_CLASSES + "(" +
                COLUMN_COURSENAME+ " TEXT PRIMARY KEY, " +
                COLUMN_COURSEDESCRIPTION + " TEXT " +
                ");";
        db.execSQL(query3);
        String query4 = "CREATE TABLE "+ TABLE_LESSIONS + "(" +
                COLUMN_LECTURENAME + " TEXT PRIMARY KEY, " +
                COLUMN_LEVEL + " INTEGER, " +
                COLUMN_STATUS + " INTEGER, "+
                COLUMN_COURSE + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_DELAY + " INTEGER, "+
                COLUMN_LECTURETYPE + " INTEGER, " +
                COLUMN_FREETIME + " INTEGER, "+
                COLUMN_EICHELN + " INTEGER"+
                ");";
        db.execSQL(query4);
        String query5 = "CREATE TABLE "+TABLE_PERMISSIONS+ "("+
                COLUMN_PERMISSIONNAME + " TEXT PRIMARY KEY, "+
                COLUMN_PERMISSIONDESCRIPTION + " TEXT DEFAULT 'Diese Permission hat leider bisher keine Beschreibung', "+
                COLUMN_PERMISSIONNICENAME + " TEXT DEFAULT 'Diese Permission hat leider bisher keinen besseren Namen', "+
                COLUMN_PERMISSIONLEVEL + " INTEGER DEFAULT -1"+
                ");";
        db.execSQL(query5);

    }

    /**
     * executed when the version number has changed, overrides the DB
     * @param db updated DB
     * @param oldVersion old version number
     * @param newVersion new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //deletes all tables and creates new (empty) DB
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_APPS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LESSIONS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PERMISSIONS);
        onCreate(db);
    }

    /**
     * function for adding setting-parameters to the rawdata-table
     * @param name name (primary key) of the setting
     * @param type data type of the setting-value (generally saved as TEXT, but for reading purposes)
     * @param value String to save as value of the setting
     */
    public void addParameter(String name, int type, String value){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SETTING,name);
        values.put(COLUMN_INITIAL, value);
        values.put(COLUMN_TYPE, type);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SETTINGS, null, values);
        db.close();
    }

    public void addParamColumn(ContentValues[] values){
        SQLiteDatabase db = getWritableDatabase();
        Cursor count = db.rawQuery("SELECT COUNT(*) AS count FROM "+ TABLE_SETTINGS +";",null);
        count.moveToFirst();
        Log.d("DBHandler","PARAM-Column Länge von values:"+values.length);
        int i=0;


        if (count.getInt(0)>0){
            Log.d("DBHandler","Rawdata hat einträge");
            count.close();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.GERMANY);
            String date = df.format(Calendar.getInstance(TimeZone.getDefault()).getTime());


            db.execSQL("ALTER TABLE "+ TABLE_SETTINGS +" ADD COLUMN '(" +date+ ")' TEXT;");
            for (ContentValues value:values
                 ) {
                if (value!=null){
                    db.execSQL("UPDATE "+ TABLE_SETTINGS +" SET '("+date+")' = \""+value.getAsString(COLUMN_INITIAL)
                            +"\" WHERE "+ COLUMN_SETTING +" = '"+value.getAsString(COLUMN_SETTING)+"'");
                    i++;
                }

            }

        } else{
            Log.d("DBHandler","Rawdata hat keine Einträge");
            count.close();
            for (ContentValues value:values
                 ) {
                if (value!=null){
                    db.insert(TABLE_SETTINGS,null, value);
                    i++;
                }

            }
        }
        Log.d("DBHandler","Rawdata eingefügt: "+i);
        db.close();

    }

    /**
     * function to remove a setting from the rawdata-table
     * @param name name of setting to be deleted
     */
    public void removeParameter(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SETTINGS + " WHERE " + COLUMN_SETTING + "=\"" + name + "\";");
        db.close();
    }

    /**
     * function to add an app to the app-database
     * @param appname name of the app-package to be added
     * @param permissions permissions in the form of semicolon-parted string
     * @param description description, which was retrieved with the app
     */
    public void addApp(String appname, String permissions, String description){ ContentValues values = new ContentValues();
        values.put(COLUMN_APPNAME,appname);
        values.put(COLUMN_PERMISSIONS,permissions);
        values.put(COLUMN_APPDESCRIPTION,description);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_APPS, null, values);
        db.close();
    }

    /**
     * function to add apps in a whole list of ContentValues
     * @param values the list
     */
    public void addAppColumn(ContentValues[] values){
        SQLiteDatabase db = getWritableDatabase();
        Cursor count = db.rawQuery("SELECT COUNT(*) AS count FROM "+ TABLE_APPS+";",null);
        count.moveToFirst();
        Log.d("DBHandler","APP-Column Länge von values:"+values.length);
        int i=0;


        if (count.getInt(0)>0){
            Log.d("DBHandler","Apps hat einträge");
            count.close();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.GERMANY);
            String date = df.format(Calendar.getInstance(TimeZone.getDefault()).getTime());


            db.execSQL("ALTER TABLE "+TABLE_APPS+" ADD COLUMN '(" +date+ ")' TEXT;");
            for (ContentValues value:values
                    ) {
                if (value!=null){
                    db.execSQL("UPDATE "+TABLE_APPS+" SET '("+date+")' = 'active' WHERE "+COLUMN_APPNAME+" = '"+value.getAsString(COLUMN_APPNAME)+"'");
                    i++;
                }
            }

        } else{
            Log.d("DBHandler","Apps hat keine Einträge");
            count.close();
            for (ContentValues value:values
                    ) {
                if (value!=null){
                    db.insert(TABLE_APPS,null, value);
                    i++;
                }

            }
        }
        Log.d("DBHandler","Apps eingefügt: "+i);
        db.close();

    }


    /**
     * function to remove an app from the app-table
     * @param name name (primary key) of app to be removed
     */
    public void removeApp(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_APPS + " WHERE " + COLUMN_APPNAME + "=\"" + name + "\";");
        db.close();
    }

    /**
     * function to retrieve information about a specific app
     * @param name name of app of interest
     * @return String of semicolon-parted permissions of the app     *
     */
    public String getAppPermissions(String name){
        SQLiteDatabase db = getWritableDatabase();
        //create a cursor, that gets the row that contains the asked app
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_APPS + " WHERE " + COLUMN_APPNAME + "=\"" + name + "\";",null);
        if (c!=null&&c.getCount()>0) {
            c.moveToFirst();
            //permissions are saved in result String
            String result= (c.getString(c.getColumnIndex(COLUMN_PERMISSIONS)));
            c.close();
            db.close();
            return result;
        }
        db.close();
        return "App not found";




    }


    /**
     * function to give a String of the complete table of parameters
     * @return a String that contains the TEXT from the parameter-table
     */
    public String printParams(){
        String dbstring= "";
        SQLiteDatabase db = getWritableDatabase();
        // get all rows of rawdata
        String query = "SELECT * FROM " + TABLE_SETTINGS + " WHERE 1;";
        //Cursor over all rows
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        //repeat over all rows
        while (!c.isAfterLast()){
            //append all info from the row to the String where there is a parameter name
            if (c.getString(c.getColumnIndex(COLUMN_SETTING))!=null){
                dbstring += c.getString(c.getColumnIndex(COLUMN_SETTING))+" "+
                        c.getString(c.getColumnIndex(COLUMN_TYPE))+" "+
                        c.getString(c.getColumnIndex(COLUMN_INITIAL));
                //append newline
                dbstring += "\n";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return dbstring;
    }

    /**
     * give a String that contains the info from App-table
     * @return a String that contains the TEXT from the app-table
     */
    public String printApps(){
        String dbstring= "";
        SQLiteDatabase db = getWritableDatabase();
        // get all rows of apps
        String query = "SELECT * FROM " + TABLE_APPS + " WHERE 1;";
        //Cursor over all rows
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        //repeat over all rows
        while (!c.isAfterLast()){
            //append all info from the row to the String where there is a parameter name
            if (c.getString(c.getColumnIndex(COLUMN_APPNAME))!=null){
                dbstring += c.getString(c.getColumnIndex(COLUMN_APPNAME))+" "+
                        c.getString(c.getColumnIndex(COLUMN_PERMISSIONS))+" "+
                        c.getString(c.getColumnIndex(COLUMN_APPDESCRIPTION));
                //append newline
                dbstring += "\n";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return dbstring;
    }

    /**
     * updates the Lession-Table from CSV-input
     * @param theLessions the List of the lessions
     */
    public void updateLessions(ArrayList<String[]> theLessions){
        SQLiteDatabase db = getWritableDatabase();
        long time = System.currentTimeMillis();
       // db.execSQL("DROP TABLE IF EXISTS "+TABLE_LESSIONS);
        for (String[] lessionArray:theLessions) {
            String lessionString = createLessionString(lessionArray).replace("'","''");
            if (checkIfInside(TABLE_LESSIONS,COLUMN_LECTURENAME+" = \'"+lessionArray[1]+"\'")){
                db.execSQL("UPDATE "+ TABLE_LESSIONS +" SET "+COLUMN_CONTENT+" = \'"+lessionString+"\', "+COLUMN_COURSE+" = \'"+lessionArray[0]+
                        "\' WHERE "+COLUMN_LECTURENAME+" = \'"+lessionArray[1]+"\';");
            } else {
                db.execSQL("INSERT INTO "+ TABLE_LESSIONS +" VALUES(\""+lessionArray[1]+"\", \""+lessionArray[2]+"\", \""+lessionArray[4]+"\", \""+lessionArray[0]+"\", \""+lessionString+"\", \""+lessionArray[3]+"\", 1, \""+time+"\", \""+lessionArray[5]+"\");");
            }
        }

    }

    /**
     * updates the Classes-Table
     */
    void updateClasses(ArrayList<String[]> classesList){
        //insert all the classes from the file
        SQLiteDatabase db = getWritableDatabase();
        for (String[] clas:classesList){
            db.execSQL("INSERT OR REPLACE INTO "+TABLE_CLASSES+" VALUES(\'"+clas[0]+"\', \'"+clas[1]+"\');");
        }

        //safety-measure: inserting all classes that are specified from a lession in the DB,
        // so that every lession is reachable
        Cursor cursor = db.rawQuery("SELECT DISTINCT "+COLUMN_COURSE+" FROM "+TABLE_LESSIONS+";",null);
        cursor.moveToFirst();
        //repeat over all rows
        while (!cursor.isAfterLast()){
            if (cursor.getString(cursor.getColumnIndex(COLUMN_COURSE))!=null){
                db.execSQL("INSERT OR IGNORE INTO "+TABLE_CLASSES+" VALUES(\'"+cursor.getString(cursor.getColumnIndex(COLUMN_COURSE))+"\', \'no description\');");
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

    }

    /**
     * returns an ArrayList of all existant Classes in the DB
     * @return ArrayList of ClassObjects
     */
    ArrayList<ClassObject> getClasses(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_CLASSES +" WHERE 1;",null);
        ArrayList<ClassObject> result = new ArrayList<ClassObject>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            result.add(new ClassObject(cursor.getString(cursor.getColumnIndex(COLUMN_COURSENAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_COURSEDESCRIPTION))));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * creates the needed String for the internal lession-management from the CSV-input
     * @param lessionArray Array with content of one lession
     * @return the needed String
     */
    private String createLessionString(String[] lessionArray){
        StringBuffer sb = new StringBuffer();
            sb.append("[name~" + lessionArray[1]+"]");
            //iterates over the parts of the Array which contains the actual slides
            for(int i=6;i<lessionArray.length;i++){
                String[] slides = lessionArray[i].split("_");
                slides[0]=slides[0].toLowerCase();
                    sb.append("[" + (i - 6) + "~type~" + slides[0] + "\'");
                    switch (slides[0]) {
                        case "text":
                            sb.append("text~" + slides[1]);
                            break;
                        case "quiz4":
                            sb.append("points~" + slides[1] + "\'text~" + slides[2] + "\'answer1text~" + slides[3] + "\'answer1solution~" +
                                    slides[4] + "\'answer2text~" + slides[5] + "\'answer2solution~" +
                                    slides[6] + "\'answer3text~" + slides[7] + "\'answer3solution~" +
                                    slides[8] + "\'answer4text~" + slides[9] + "\'answer4solution~" +
                                    slides[10]);
                            break;
                        case "button":
                            sb.append("text~" + slides[1] + "\'buttonText~" + slides[2] + "\'method~" + slides[3] + "\'methodParameter~" + slides[4]);
                            break;
                        case "question":
                            sb.append("text~" + slides[1] + "\'buttonText~" + slides[2] + "\'method~" + slides[3] + "\'methodParameter~" + slides[4] + "\'buttonText2~" + slides[5] + "\'method2~" + slides[6] + "\'methodParameter2~" + slides[7]);
                            break;
                        case "certificate":
                            sb.append("successText~" + slides[1] + "\'failureText~" + slides[2] + "\'pointsNeeded~" + slides[3]);
                            break;
                        default:
                            sb.append("text~" + slides[1]);
                    }
                    if (lessionArray[i].contains("next")) {
                        sb.append("\'next~" + lessionArray[i].substring(lessionArray[i].lastIndexOf("next") + 4, lessionArray[i].lastIndexOf("next") + 5));
                    }
                    if (lessionArray[i].contains("back")) {
                        sb.append("\'back~" + lessionArray[i].substring(lessionArray[i].lastIndexOf("back") + 4, lessionArray[i].lastIndexOf("back") + 5));
                    }
                    sb.append("]");
            }

        return sb.toString();
    }

    /**
     * checks, if a where-clause results in entries in the specified table
     * @param table table to be searched in
     * @param whereclause description of content searched for
     * @return true/false
     */
    private Boolean checkIfInside(String table, String whereclause) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+table+" WHERE "+whereclause+";",null);
        if (cursor.getCount()<=0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    /**
     * gives all Lections that belong to the specified class and are activated
     * @param courseName identifier of the class
     * @return ArrayList of LectionObjects
     */
    public ArrayList<LectionObject> getLectionsFromDB(String courseName){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_LESSIONS +" WHERE "
                +COLUMN_COURSE+"=\'"+courseName+"\' AND "+COLUMN_STATUS+" IS NOT 0",null);
        ArrayList<LectionObject> result = new ArrayList<LectionObject>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            result.add(new LectionObject(
                    cursor.getString(cursor.getColumnIndex(COLUMN_LECTURENAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LECTURETYPE)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_DELAY)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_FREETIME)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_EICHELN))));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return result;
    }


    private Pair<String, Integer> individualSetting(String name) {
        switch (name){
            case "accessibility_display_magnification_auto_update": return (new Pair<>("Eingabehilfe: Display-Zoom kontinuierlich",1));
            case "accessibility_display_magnification_enabled" : return (new Pair<>("Eingabehilfe: Display-Zoom",1));
            case "accessibility_display_magnification_scale" : return (new Pair<>("Eingabehilfe: Display-Zoom-Faktor",2));
            case "accessibility_script_injection": return (new Pair<>("Eingabehilfe: individuelle Skripte einfügen",1));
            case "airplane_mode_on": return (new Pair<>("Flugmodus",1));
            case "airplane_mode_radios": return (new Pair<>("Bei Flugmodus deaktivierte Funktionen",0));
            case "accessibility_script_injection_url": return (new Pair<>("Eingabehilfe: Skript-Adresse",0));
            case "accessibility_web_content_key_bindings": return (new Pair<>("Eingabehilfe: Tastenzuweisung bei Webinhalten",0));
            case "airplane_mode_toggleable_radios": return (new Pair<>("Bei Flugmodus schaltbare Funktionen",0));
            case "android_id": return (new Pair<>("Android ID (systemeigen)",2));
            case "app_idle_constants": return (new Pair<>("Erlaubter Leerlauf einer App",0));
            case "assisted_gps_enabled": return (new Pair<>("Netzwerkunterstütztes GPS",1));
            case "audio_safe_volume_state": return (new Pair<>("Anzeige von Hörschädigungshinweis",1));
            case "auto_time": return (new Pair<>("Automatischer Abruf von Zeit/Datum",1));
            case "auto_time_zone": return (new Pair<>("Automatischer Abruf der Zeitzone",1));
            case "bluetooth_on": return (new Pair<>("Bluetooth",1));
            case "call_auto_retry": return (new Pair<>("Automatische Wahlwiederholung",1));
            case "car_dock_sound": return (new Pair<>("Auto-Dockingstations-Ton",0));
            case "car_undock_sound": return (new Pair<>("Auto-Dockingstation-Endton",0));
            case "cdma_cell_broadcast_sms": return (new Pair<>("SMS-Broadcast per CDMA erhalten",1));
            case "backup_enabled": return (new Pair<>("System-Backup aktiviert",1));
            case "data_roaming": return (new Pair<>("Daten-Roaming",1));
            case "default_install_location": return (new Pair<>("Standard App-Speicherort",0));
            case "desk_dock_sound": return (new Pair<>("Dockingstation-Anschlusston",0));
            case "desk_undock_sound": return (new Pair<>("Dockingstation-Trennton",0));
            case "device_name": return (new Pair<>("Gerätename",0));
            case "device_provisioned": return (new Pair<>("Sperre bei mehreren Nutzern",1));
            case "dock_audio_media_enabled": return (new Pair<>("Medienwiedergabe über Dockingstation",1));
            case "dock_sounds_enabled": return (new Pair<>("Tonwiedergabe über Dockingstation",1));
            case "double_tap_to_wake": return (new Pair<>("Aufwecken des Bildschirms durch doppeltes Tippen",1));
            case "dropbox:data_app_anr": return (new Pair<>("Dropbox-ANR",0));
            case "dropbox:data_app_crash": return (new Pair<>("Dropbox-Crash",0));
            case "dropbox:data_app_wtf": return (new Pair<>("Dropbox-WTF",0));
            case "emergency_tone": return (new Pair<>("Notfall-Ton",1));
            case "facelock_detection_threshold": return (new Pair<>("Gesichtsentsperrungsschwelle",0));
            case "facelock_liveliness_recognition_threshold": return (new Pair<>("Gesichtsentsperrungsschwelle bei Bewegung",0));
            case "facelock_max_center_movement": return (new Pair<>("Gesichtsentsperrungsschwelle bei Bewegung in der Mitte",0));
            case "guest_user_enabled": return (new Pair<>("Gastprofil aktiviert",1));
            case "heads_up_notifications_enabled": return (new Pair<>("Heads-up Benachrichtigungen",1));
            case "immersive_mode_confirmations": return (new Pair<>("Bestätigung für Vollbildmodus",1));
            case "input_methods_subtype_history": return (new Pair<>("Historie für Eingabemethoden",1));
            case "install_non_market_apps": return (new Pair<>("Apps unbekannter Quellen installieren",1));
            case "locationPackagePrefixWhitelist": return (new Pair<>("Erlaubnisliste für Ortungsprogramm",0));
            case "locationPackagePrefixBlacklist": return (new Pair<>("Ausschlussliste für Ortungsprogramm",0));
            case "location_providers_allowed": return (new Pair<>("aktivierte Ortungsdienste",0));
            case "lock_screen_allow_private_notifications": return (new Pair<>("Sensible Benachrichtigungsinhalte auf dem Sperrbildschirm",1));
            case "lock_screen_owner_info_enabled": return (new Pair<>("Besitzerinformationen auf Sperrbildschirm",1));
            case "lock_sound": return (new Pair<>("Ton bei Bildschirmsperre",0));
            case "lockscreen.disabled": return (new Pair<>("Sperrbildschirm deaktiviert",1));
            case "lockscreen.options": return (new Pair<>("aktivierte Sperrbildschirm-Optionen",0));
            case "long_press_timeout": return (new Pair<>("Erkennung von gedrückt halten (ms)",2));
            case "low_battery_sound": return (new Pair<>("Ton bei niedrigem Akkustand",0));
            case "masterLocationPackagePrefixBlacklist": return (new Pair<>("Ausschlussliste für ",0));
            case "masterLocationPackagePrefixWhitelist": return (new Pair<>("Erlaubnisliste für ",0));
            case "adb_enabled": return (new Pair<>("Android Debug Funktion",1));
            case "bluetooth_adress": return (new Pair<>("Bluetooth-Adresse",0));
            case "bluetooth_name": return (new Pair<>("Bluetooth Anzeigename",0));
            case "ble_scan_always_enabled": return (new Pair<>("Bluetooth Low Energy Scan",1));
            case "bluetooth_addr_valid": return (new Pair<>("Valide Bluetooth-Adresse",1));
            case "development_settings_enabled": return (new Pair<>("Entwickleroptionen",1));
            case "doze_enabled": return (new Pair<>("Atmungseffekt bei ausgeschaltetem Bildschirm und Nachricht",1));
            case "enable_accessability_global_gesture_enabled": return (new Pair<>("Eingabehilfe: Gesten überall",1));
            case "lock_screen_lock_after_timeout": return (new Pair<>("Sperrbildschirm aktivierung nach (ms)",2));
            case "low_power_trigger_level": return (new Pair<>("Kritischer Akkustand bei (%)",2));
            case "mobile_data": return (new Pair<>("Mobiler Datenverkehr",1));
            case "mock_location": return (new Pair<>("Falsche Standorte",1));
            case "mode_ringer": return (new Pair<>("Benachrichtigungs-Modus",2));
            case "multi_sim_data_call": return (new Pair<>("VoIP mit MultiSIM",1));
            case "multi_sim_sms": return (new Pair<>("SMS mit MultiSIM",1));
            case "multi_sim_sms_prompt": return (new Pair<>("Abfrage bei SMS mit MultiSIM",1));
            case "multi_sim_voice_call": return (new Pair<>("Anrufe mit MultiSIM",1));
            case "netstats_enabled": return (new Pair<>("Netzwerkstatistiken",1));
            case "network_scoring_provisioned": return (new Pair<>("Beschränkte Netzwerkzählung",1));
            case "nfc_default_route": return (new Pair<>("NFC Standard Modus",0));
            case "package_verifier_enable": return (new Pair<>("App-Verifizierung",1));
            case "package_verifier_user_consent": return (new Pair<>("App-Verifizierung nach Nutzer-Einwilligung",1));
            case "power_sounds_enabled": return (new Pair<>("Töne für Akkustand und Laden",1));
            case "preferred_network_mode1": return (new Pair<>("Bevorzugte mobile Netze",2));
            case "preferred_network_mode": return (new Pair<>("Bevorzugte mobile Netze",2));
            case "screensaver_activate_on_dock": return (new Pair<>("Bildschirmschoner in Dockingstation aktivieren",1));
            case "screensaver_activate_on_sleep": return (new Pair<>("Bildschirmschoner bei Inaktivität aktivieren",1));
            case "screensaver_enabled": return (new Pair<>("Bildschirmschoner",1));
            case "selected_input_method_subtype": return (new Pair<>("Untereinstellung der aktuellen Eingabemethode",2));
            case "send_action_app_error": return (new Pair<>("Einen App-Fehler melden",1));
            case "serial_blacklist": return (new Pair<>("Ausschlussliste für Seriennummern",0));
            case "set_install_location": return (new Pair<>("Den App-Installationsort setzen",1));
            case "show_note_about_notification_hiding": return (new Pair<>("Nachricht bei Verstecken von Benachrichtigungen anzeigen",1));
            case "show_processes": return (new Pair<>("Laufende Prozesse anzeigen",1));
            case "sleep_timeout": return (new Pair<>("Verzögerung, bis der Prozessor in den Schlaf-Modus geht",2));
            case "sms_outgoing_check_interval_ms": return (new Pair<>("Zeitintervall, in dem die Menge ausgehender SMS geprüft werden",2));
            case "sms_outgoing_check_max_count": return (new Pair<>("Menge an SMS, die in dem Intervall gesendet werden dürfen",2));
            case "speak_password": return (new Pair<>("Eingabehilfen: Passwörter aussprechen",1));
            case "ssl_session_cache": return (new Pair<>("Modus des SSL-Caches",0));
            case "stay_on_while_plugged_in": return (new Pair<>("Display anlassen, wenn Strom angeschlossen ist.",1));
            case "usb_mass_storage_enabled": return (new Pair<>("USB-Speicher",1));
            case "use_google_mail": return (new Pair<>("Gmail-Referenzen werden zu Google aufgelöst",0));
            case "wifi_max_dhcp_retry_count": return (new Pair<>("Maximale Anzahl von Versuchen eine Verbindung aufzubauen",2));
            case "wifi_mobile_data_transition_wakelock_timeout_ms": return (new Pair<>("Maximale Dauer zum Aufbau einer mobilen Datenverbindung vor dem Übergang in den Sleep-Modus (ms)",2));
            case "wifi_networks_available_notification_on": return (new Pair<>("Wiederholte Meldung verfügbarer Netze",1));
            case "wifi_networks_available_repeat_delay": return (new Pair<>("Zeit zur wiederholten Meldung verfügbarer Netze (s)",2));
            case "wifi_num_open_networks_kept": return (new Pair<>("Anzahl gespeicherter Wifi-Access-Points",2));
            case "wifi_on": return (new Pair<>("Wifi",1));
            case "wifi_sleep_policy_default": return (new Pair<>("Deafult-Einstellung für den Wifi-Sleep-Modus",2));
            case "wifi_sleep_policy": return (new Pair<>("Aktiver Wifi-Sleep-Modus",2));
            case "wifi_sleep_policy_never": return (new Pair<>("Wifi-Sleep-Modus immer aus",2));
            case "wifi_sleep_policy_never_while_plugged": return (new Pair<>("Wifi-Sleep-Modus beim Aufladen des Handys aus",1));
            case "wifi_watchdog_on": return (new Pair<>("Regelmäßige Wifi-Suche",1));
            case "window_animation_scale": return (new Pair<>("Faktor zum Skalieren normaler Animationen",0));

            default: {
                name = name.replace("_", " ");
                name = name.substring(0,1).toUpperCase() + name.substring(1);
                return (new Pair<>(name,0));
            }
        }
    }

    /**
     * returns the whole specified column from TABLE_SETTINGS as an Array
     * @param columnName identifier of the column
     * @return the StringArray with all entries from this column
     */
    private String[] returnColumn(String columnName) {
        //create String array with correct length
        Cursor count = getWritableDatabase().rawQuery("SELECT COUNT(*) AS count FROM " + TABLE_SETTINGS + ";", null);
        count.moveToFirst();
        String[] settingsArray = new String[count.getInt(0)];
        count.close();
        //fill array with DB-entries
        Cursor c = getWritableDatabase().rawQuery("SELECT " + columnName + " FROM " + TABLE_SETTINGS + " WHERE 1;", null);
        if (c != null) {
            c.moveToFirst();
            int i = 0;
            while (!c.isAfterLast()) {
                settingsArray[i] = (c.getString(c.getColumnIndex(columnName)));
                i++;
                c.moveToNext();
            }
            c.close();
        }

        close();
        return settingsArray;

    }

    /**
     * updates the COLUMN_GOODNAME of a setting with the proper name
     * @param fieldname setting-identifier
     * @param nicename proper name to be saved with identifier
     */
    private void updateName(String fieldname, String nicename) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE "+ TABLE_SETTINGS +" SET "+COLUMN_GOODNAME
                +" = \'"+nicename+"\' WHERE "+ COLUMN_SETTING +" = \'"+fieldname+"\';");
        db.close();
    }

    /**
     * go through all the default names and convert them into readable ones
     * @author Lena
     */
    void insertGoodNames() {
        String[] oldNames = returnColumn(COLUMN_SETTING);

        for (String name : oldNames) {
            String goodName = individualSetting(name).first;
            updateName(name, goodName);
        }
    }

    /**
     * updates the permission-table with their data from CSV-input
     * @param permissionlist the List of permissions from CSV
     */
    void updatePermissions(ArrayList<String[]> permissionlist){
        SQLiteDatabase db= getWritableDatabase();
        for (String[] array: permissionlist){
            if(array.length==1){
                db.execSQL("INSERT OR REPLACE INTO "+TABLE_PERMISSIONS+" (\'"+COLUMN_PERMISSIONNAME+"\') VALUES(\'"+array[0]+"\');");
            }
            if(array.length==2){
                db.execSQL("INSERT OR REPLACE INTO "+TABLE_PERMISSIONS+" (\'"+COLUMN_PERMISSIONNAME+"\', \'"+COLUMN_PERMISSIONDESCRIPTION+"\') VALUES(\'"+array[0]+"\', \'"+array[1].replace("'","''")+"\');");
            }
            if(array.length==3){
                db.execSQL("INSERT OR REPLACE INTO "+TABLE_PERMISSIONS+" (\'"+COLUMN_PERMISSIONNAME+"\', \'"+COLUMN_PERMISSIONDESCRIPTION+"\', \'"+COLUMN_PERMISSIONNICENAME+"\') VALUES(\'"+array[0]+"\', \'"+array[1].replace("'","''")+"\', \'"+array[2].replace("'","''")+"\');");
            }
            if(array.length==4){
                db.execSQL("INSERT OR REPLACE INTO "+TABLE_PERMISSIONS+" (\'"+COLUMN_PERMISSIONNAME+"\', \'"+COLUMN_PERMISSIONDESCRIPTION+"\', \'"+COLUMN_PERMISSIONNICENAME+"\', \'"+COLUMN_PERMISSIONLEVEL+"\') VALUES(\'"+array[0]+"\', \'"+array[1].replace("'","''")+"\', \'"+array[2].replace("'","''")+"\', \'"+array[3]+"\');");
            }

        }


    }

    /**
     * get the description of a permissionidentifier
     * @param permissionname the identifier of the permission
     * @return a String with the text of description
     */
    String getPermissionDescription(String permissionname){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PERMISSIONS+" WHERE "+COLUMN_PERMISSIONNAME+" = \'"+permissionname+"\';",null);
        if (cursor.getColumnCount()>1&&cursor.getCount()>0) {
            cursor.moveToFirst();
            String result = cursor.getString(cursor.getColumnIndex(COLUMN_PERMISSIONDESCRIPTION));
            cursor.close();
            return result;
        } else{
            cursor.close();
            return "Diese Permission hat noch keine Beschreibung.";
        }
    }

    /**
     * get the good name of a permissionidentifier
     * @param permissionname the identifier
     * @return the good name as String (or default apologise)
     */
    String getPermissionNiceName(String permissionname){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PERMISSIONS+" WHERE "+COLUMN_PERMISSIONNAME+" = \'"+permissionname+"\';",null);
        if (cursor.getCount()>2) {
            cursor.moveToFirst();
            String result = cursor.getString(cursor.getColumnIndex(COLUMN_PERMISSIONNICENAME));
            cursor.close();
            return result;
        } else{
            cursor.close();
            return "Diese Permission hat noch keinen besseren Namen.";
        }
    }

    /**
     * get the level of the asked permission
     * @param permissionname permissionidentifier
     * @return level as integer
     */
    int getPermissionLevel(String permissionname){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PERMISSIONS+" WHERE "+COLUMN_PERMISSIONNAME+" = \'"+permissionname+"\';",null);
        if (cursor.getCount()>3){
            cursor.moveToFirst();
            int result= cursor.getInt(cursor.getColumnIndex(COLUMN_PERMISSIONLEVEL));
            cursor.close();
            return result;
        }else{
            cursor.close();
            return -1;
        }

    }

    /**
     * change the lection's status from unread (1) to read (2)
     * @param lectionName name of the lection to be changed
     */
    public void changeLectionToRead(String lectionName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_LESSIONS + " SET " + COLUMN_STATUS + " = 2 WHERE " + COLUMN_LECTURENAME + " = \'" + lectionName + "\';");
        db.close();

    }

    /**
     * change the lection's status from unread (1) or read (2) to solved (3)
     * @param lectionName name of the lection to be changed
     */
    public void changeLectionToSolved(String lectionName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_LESSIONS + " SET " + COLUMN_STATUS + " = 3 WHERE " + COLUMN_LECTURENAME + " = \'" + lectionName + "\';");
        db.close();

}

    /**
     * try to change the lection's status from locked (0) to unlocked (1) and return true on
     * success or false if the lection could not been found
     * @param lectionName name of the lection to be changed
     * @return true/false wether the lection was found
     */
    public boolean changeLectionToUnlocked(String lectionName){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LESSIONS + " WHERE " + COLUMN_LECTURENAME + " = \'" + lectionName + "\';", null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                db.execSQL("UPDATE " + TABLE_LESSIONS + " SET " + COLUMN_STATUS + " = 1 WHERE " + COLUMN_LECTURENAME + " = \'" + lectionName + "\';");
                cursor.close();
                db.close();
                return true;
            }
        }
        db.close();
        return false;
    }

    /**
     *  set a lections nextFreeTime according to the input
     * @param lectionName name of the lection to be changed
     * @param nextFreeTime the time the lection is available again in ms
     */
    public void setLectionNextFreeTime(String lectionName, long nextFreeTime){
        SQLiteDatabase db = getWritableDatabase();
        long time = System.currentTimeMillis() + nextFreeTime;
        db.execSQL("UPDATE " + TABLE_LESSIONS + " SET " + COLUMN_FREETIME + " = \'" + time + "\' WHERE " + COLUMN_LECTURENAME + " = \'" + lectionName + "\';");
        db.close();
    }

    /**
     * method to get the proportion of solved to all lessions in a specified Class
     * @param classname the class to get the proportion for
     * @return a String with "x/y" (x<y)
     */
    public String countSolvedLessions(String classname){
        SQLiteDatabase db = getWritableDatabase();
        Cursor solved = db.rawQuery("SELECT COUNT(*) AS count FROM "+TABLE_LESSIONS+" WHERE "+COLUMN_STATUS+" = 3 AND "+COLUMN_COURSE+" = \'"+classname+"\';",null);
        Cursor all = db.rawQuery("SELECT COUNT(*) AS count FROM "+TABLE_LESSIONS+" WHERE "+COLUMN_STATUS+" > 0 AND "+COLUMN_COURSE+" = \'"+classname+"\';",null);
        solved.moveToFirst();
        all.moveToFirst();
        int solvedcount=solved.getInt(0);
        int allcount=all.getInt(0);
        solved.close();
        all.close();
        db.close();
        return Integer.toString(solvedcount)+"/"+ Integer.toString(allcount);
    }

    public void updateSettingDescriptions(ArrayList<String[]> settingsData){
        SQLiteDatabase db = getWritableDatabase();
        for (String[] setting:settingsData){
            if (checkIfInside(TABLE_SETTINGS,COLUMN_SETTING+" = \'"+setting[0]+"\'")){
                if (setting.length==1){
                }else if (setting.length==2){
                    db.execSQL("UPDATE "+TABLE_SETTINGS+" SET "
                            +COLUMN_TYPE+" = "+setting[1]
                            +" WHERE "+COLUMN_SETTING+" = \'"+setting[0]+"\';");
                }else if (setting.length==3){
                    db.execSQL("UPDATE "+TABLE_SETTINGS+" SET "
                            +COLUMN_TYPE+" = "+setting[1]+", "
                            +COLUMN_GOODNAME+" = \'"+setting[2]+"\' "
                            +"WHERE "+COLUMN_SETTING+" = \'"+setting[0]+"\';");
                }else
                    db.execSQL("UPDATE "+TABLE_SETTINGS+" SET "
                            +COLUMN_TYPE+" = "+setting[1]+", "
                            +COLUMN_GOODNAME+" = \'"+setting[2]+"\', "
                            +COLUMN_SETTINGDESCRIPTION+" = \'"
                            +setting[3]+"\' " +
                            "WHERE "+COLUMN_SETTING+" = \'"+setting[0]+"\';");
            }else{
                if (setting.length==1){
                    db.execSQL("INSERT INTO "+TABLE_SETTINGS+"("
                            +COLUMN_SETTING
                            +") VALUES(\'"+
                            setting[0]+"\')");
                }else if (setting.length==2){
                    db.execSQL("INSERT INTO "+TABLE_SETTINGS+"("
                            +COLUMN_SETTING+", "
                            +COLUMN_TYPE
                            +") VALUES(\'"+
                            setting[0]+"\', \'"+setting[1]+"\')");
                }else if (setting.length==3){
                    db.execSQL("INSERT INTO "+TABLE_SETTINGS+"("
                            +COLUMN_SETTING+", "
                            +COLUMN_TYPE+", "
                            +COLUMN_GOODNAME
                            +") VALUES(\'"+
                            setting[0]+"\', \'"+setting[1]+"\', \'"+setting[2]+"\')");
                }else
                db.execSQL("INSERT INTO "+TABLE_SETTINGS+"("
                        +COLUMN_SETTING+", "
                        +COLUMN_TYPE+", "
                        +COLUMN_GOODNAME+", "
                        +COLUMN_SETTINGDESCRIPTION+") VALUES(\'"+
                        setting[0]+"\', \'"+setting[1]+"\', \'"+setting[2]+"\', \'"+setting[3]+"\')");
            }
        }
        renameEmptySettings();

    }

    private void renameEmptySettings(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+COLUMN_SETTING+" FROM "+TABLE_SETTINGS+" WHERE "+COLUMN_GOODNAME+" IS NULL;",null);
        if (cursor!=null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String name = cursor.getString(0);
                name = name.replace("_", " ");
                name = name.substring(0,1).toUpperCase() + name.substring(1);
                db.execSQL("UPDATE "+TABLE_SETTINGS+" SET "+COLUMN_GOODNAME+" = \'"+name+"\' WHERE "+COLUMN_SETTING+" = \'"+cursor.getString(0)+"\';");
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();

    }

    public String[] getSettingsFromDB(){
        //create String array with correct length
        String[] settingsArray;
        Cursor count = getWritableDatabase().rawQuery("SELECT COUNT(*) AS count FROM "+TABLE_SETTINGS +" WHERE "+COLUMN_INITIAL+" IS NOT '-99';",null);
        count.moveToFirst();
        settingsArray = new String[count.getInt(0)];
        count.close();
        //fill array with DB-entries
        Cursor c  =getWritableDatabase().rawQuery("SELECT "+COLUMN_GOODNAME+", "+COLUMN_INITIAL+" FROM "+TABLE_SETTINGS +" WHERE "+COLUMN_INITIAL+" IS NOT '-99';",null);
        if (c!=null) c.moveToFirst();
        int i=0;
        while (c!=null&&!c.isAfterLast()){
            settingsArray[i]=(c.getString(c.getColumnIndex(COLUMN_GOODNAME))+";"+c.getString(c.getColumnIndex(COLUMN_INITIAL)));
            i++;
            c.moveToNext();
        }
        if (c!=null) c.close();
        return settingsArray;

    }


}
