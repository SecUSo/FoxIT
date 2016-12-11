package com.bp;

/**
 * General Database-API
 * Created by noah on 29.05.16. :)
 * @author: Noah
 */

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class DBHandler extends SQLiteOpenHelper{

    //DB-Version is updated, when changes in structur apply
    private static final int DB_VERSION = 27;
    //table-names
    private static final String TABLE_APPS = "apps";
    private static final String TABLE_LESSIONS = "lessions";
    private static final String TABLE_CLASSES = "classes";
    private static final String TABLE_PERMISSIONS = "permissions";
    private static final String TABLE_SETTINGS = "rawdata";
    public static final String TABLE_PERSONAL = "personalstuff";
    //column-names
    static final String COLUMN_APPNAME = "name";
    static final String COLUMN_PERMISSIONS = "permissions";
    static final String COLUMN_APPDESCRIPTION = "description";

    static final String COLUMN_SETTING = "parameter";
    static final String COLUMN_INITIAL = "initial";
    private static final String COLUMN_GOODNAME = "propername";
    private static final String COLUMN_SETTINGDESCRIPTION = "sdescription";
    private static final String COLUMN_LATEST ="latestvalue";
    //type: for readout - 0=String, 1=BOOLEAN, 2=int
    static final String COLUMN_TYPE = "type";

    private static final String COLUMN_LECTURENAME = "name";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_COURSE = "course";
    //0=not available yet; 1=not yet read; 2=not yet finished; 3=finished
    private static final String COLUMN_STATUS = "status";
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

    public static final String COLUMN_KEY ="key";
    private static final String COLUMN_VALUE = "value";
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
                COLUMN_LATEST + " TEXT DEFAULT '-99', " +
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
        String query6= "CREATE TABLE "+ TABLE_PERSONAL+"("+
                COLUMN_KEY + " TEXT PRIMARY KEY, "+
                COLUMN_VALUE+ " TEXT"+
                ");";
        db.execSQL(query6);

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
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PERSONAL);
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

    /**
     * Inserts a whole Array of Settings to the DB if it's empty or creates a new Column with the
     * current time to it and adds the value to that. In both cases the 'latest' column ist updated
     * with the current value
     * @param values
     */
    public void addParamColumn(ContentValues[] values){
        SQLiteDatabase db = getWritableDatabase();
        //Cursor to count the current Entries in the table
        Cursor count = db.rawQuery("SELECT COUNT(*) AS count FROM "+ TABLE_SETTINGS +";",null);
        count.moveToFirst();
        //Here should an output for the user happen instead of the Log but with similiar values
        Log.d("DBHandler","PARAM-Column Länge von values:"+values.length);
        int i=0;
        //If there are Entries in the table already
        if (count.getInt(0)>0){
            //output for the user, information of existing entries
            Log.d("DBHandler","Rawdata hat einträge");
            count.close();
            //constructing a name for the new column
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.GERMANY);
            String date = df.format(Calendar.getInstance(TimeZone.getDefault()).getTime());
            //adding the new column
            db.execSQL("ALTER TABLE "+ TABLE_SETTINGS +" ADD COLUMN '(" +date+ ")' TEXT;");
            //updating the new and the latest column with the new values in their respective row
            for (ContentValues value:values
                    ) {
                if (value!=null){
                    db.execSQL("UPDATE "+ TABLE_SETTINGS +" SET '("+date+")' = \""+value.getAsString(COLUMN_INITIAL)
                            +"\", "+COLUMN_LATEST+" = \'"+value.getAsString(COLUMN_INITIAL)+"\' WHERE "+ COLUMN_SETTING +" = '"+value.getAsString(COLUMN_SETTING)+"'");

                    i++;
                }
            }

        } else{
            //here should output happen
            Log.d("DBHandler","Rawdata hat keine Einträge");
            count.close();
            //inserting the contentValues directly (as they are all new) and updating the latest column
            for (ContentValues value:values
                    ) {
                if (value!=null){
                    db.insert(TABLE_SETTINGS,null, value);
                    db.execSQL("UPDATE "+TABLE_SETTINGS+" SET "+COLUMN_LATEST+" = \'"+value.getAsString(COLUMN_INITIAL)+"\' WHERE "+COLUMN_SETTING+" = '"+value.getAsString(COLUMN_SETTING)+"'");
                    i++;
                }

            }
        }
        //output for user please
        Log.d("DBHandler","Rawdata eingefügt: "+i);
        db.close();

    }


    /**
     * function to add apps in a whole list of ContentValues
     * @param values the list
     */
    public void addAppColumn(ContentValues[] values){
        SQLiteDatabase db = getWritableDatabase();
        //Cursor for counting the entries
        Cursor count = db.rawQuery("SELECT COUNT(*) AS count FROM "+ TABLE_APPS+";",null);
        count.moveToFirst();
        //output for User here
        Log.d("DBHandler","APP-Column Länge von values:"+values.length);
        int i=0;
        //if there are entries in the table
        if (count.getInt(0)>0){
            //output for User here
            Log.d("DBHandler","Apps hat einträge");
            count.close();
            //creating the name for the new column
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.GERMANY);
            String date = df.format(Calendar.getInstance(TimeZone.getDefault()).getTime());
            //creating the new column
            db.execSQL("ALTER TABLE "+TABLE_APPS+" ADD COLUMN '(" +date+ ")' TEXT;");
            //updating the new column with an active tag to signalise, that it's still installed
            for (ContentValues value:values
                    ) {
                if (value!=null){
                    db.execSQL("UPDATE "+TABLE_APPS+" SET '("+date+")' = 'active' WHERE "+COLUMN_APPNAME+" = '"+value.getAsString(COLUMN_APPNAME)+"'");
                    i++;
                }
            }
        } else{
            //output for User here
            Log.d("DBHandler","Apps hat keine Einträge");
            count.close();
            //inserting all the Apps with their values into the table
            for (ContentValues value:values
                    ) {
                if (value!=null){
                    db.insert(TABLE_APPS,null, value);
                    i++;
                }
            }
        }
        //output for User here
        Log.d("DBHandler","Apps eingefügt: "+i+1);
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
                db.execSQL("UPDATE "+ TABLE_LESSIONS +" SET "+COLUMN_CONTENT+" = \'"+lessionString+"\', "+COLUMN_COURSE+" = \'"+lessionArray[0]+"\', " +
                        "\'"+COLUMN_DELAY+"\' = \'"+lessionArray[2]+"\', \'"+COLUMN_LECTURETYPE+"\' = \'"+lessionArray[5]+"\', \'"+COLUMN_EICHELN+"\' = \'"+lessionArray[4]+
                        "\' WHERE "+COLUMN_LECTURENAME+" = \'"+lessionArray[1]+"\';");
            } else {
                db.execSQL("INSERT INTO "+ TABLE_LESSIONS +" VALUES(\'"+lessionArray[1]+"\', \'"+lessionArray[3]+"\', \'"+lessionArray[0]+"\', \'"+lessionString+"\', \'"+lessionArray[2]+"\', \'"+lessionArray[5]+"\', \'"+time+"\', \'"+lessionArray[4]+"\');");
            }
        }
        db.close();

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
            //puts the input in the desired form depending on the type of slide it is
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
            //appending the next and back functions, if it is found
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
    public Boolean checkIfInside(String table, String whereclause) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+table+" WHERE "+whereclause+";",null);
        if (cursor.getCount()<=0){
            cursor.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    /**
     * gives all Lections that belong to the specified class and are activated
     * @author Noah
     * @param courseName identifier of the class
     * @return ArrayList of LectionObjects
     */
    public ArrayList<LectionObject> getLectionsFromDB(String courseName){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_LESSIONS +" WHERE "
                +COLUMN_COURSE+"=\'"+courseName+"\' AND "+COLUMN_STATUS+" IS NOT -99",null);
        ArrayList<LectionObject> result = new ArrayList<LectionObject>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            result.add(new LectionObject(
                    cursor.getString(cursor.getColumnIndex(COLUMN_LECTURENAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_LECTURETYPE)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_DELAY)),
                    cursor.getLong(cursor.getColumnIndex(COLUMN_FREETIME)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_EICHELN))));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return result;
    }




    /**
     * updates the permission-table with their data from CSV-input
     * @author Noah
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
        db.close();
    }

    /**
     * get the description of a permissionidentifier
     * @author Noah
     * @param permissionname the identifier of the permission
     * @return a String with the text of description
     */
    String getPermissionDescription(String permissionname){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PERMISSIONS+" WHERE "+COLUMN_PERMISSIONNAME+" = \'"+permissionname+"\';",null);
        //if more than the name column is filled and there is an entry
        if (cursor.getColumnCount()>1&&cursor.getCount()>0) {
            cursor.moveToFirst();
            String result = cursor.getString(cursor.getColumnIndex(COLUMN_PERMISSIONDESCRIPTION));
            cursor.close();
            return result;
            //if there is no description for the permission
        } else{
            cursor.close();
            return "Diese Permission hat noch keine Beschreibung.";
        }
    }

    /**
     * get the good name of a permissionidentifier
     * @author Noah
     * @param permissionname the identifier
     * @return the good name as String (or default apologise)
     */
    String getPermissionNiceName(String permissionname){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PERMISSIONS+" WHERE "+COLUMN_PERMISSIONNAME+" = \'"+permissionname+"\';",null);
        //if there are more than 2 Columns are filled, a description is there
        if (cursor.getColumnCount()>2) {
            cursor.moveToFirst();
            String result = cursor.getString(cursor.getColumnIndex(COLUMN_PERMISSIONNICENAME));
            cursor.close();
            return result;
            //if no description is found
        } else {
            cursor.close();
            return "Diese Permission hat noch keinen besseren Namen.";
        }
    }

    /**
     * get the level of the asked permission
     * @author
     * @param permissionname permissionidentifier
     * @return level as integer
     */
    int getPermissionLevel(String permissionname){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PERMISSIONS+" WHERE "+COLUMN_PERMISSIONNAME+" = \'"+permissionname+"\';",null);
        //if there are more than 3 Columns found, there is a level found
        if (cursor.getColumnCount()>3){
            cursor.moveToFirst();
            int result= cursor.getInt(cursor.getColumnIndex(COLUMN_PERMISSIONLEVEL));
            cursor.close();
            return result;
            //when there is no level in the DB
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
     * change the lection's status from unread (1) or read (2) to solved (3)
     * @param lectionName name of the lection to be changed
     */
    public void changeEvaluationToSolved(String lectionName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_LESSIONS + " SET " + COLUMN_STATUS + " = -98 WHERE " + COLUMN_LECTURENAME + " = \'" + lectionName + "\';");
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
        Log.d("new time to insert",Long.toString(time));
        db.execSQL("UPDATE " + TABLE_LESSIONS + " SET " + COLUMN_FREETIME + " = \'" + time + "\' WHERE " + COLUMN_LECTURENAME + " = \'" + lectionName + "\';");
        db.close();
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
        db.close();
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
        Cursor count = getWritableDatabase().rawQuery("SELECT COUNT(*) AS count FROM "+TABLE_SETTINGS +" WHERE "+COLUMN_LATEST+" IS NOT '-99';",null);
        count.moveToFirst();
        settingsArray = new String[count.getInt(0)];
        count.close();
        //fill array with DB-entries
        Cursor c  =getWritableDatabase().rawQuery("SELECT "+COLUMN_GOODNAME+", "+COLUMN_LATEST+", "+COLUMN_SETTING+" FROM "+TABLE_SETTINGS +" WHERE "+COLUMN_LATEST+" IS NOT '-99';",null);
        if (c!=null) c.moveToFirst();
        int i=0;
        while (c!=null&&!c.isAfterLast()){
            settingsArray[i]=(c.getString(c.getColumnIndex(COLUMN_GOODNAME))+";"+c.getString(c.getColumnIndex(COLUMN_LATEST))+";"+c.getString(c.getColumnIndex(COLUMN_SETTING)));
            i++;
            c.moveToNext();
        }
        if (c!=null) c.close();
        return settingsArray;

    }

    public void insertIndividualData(HashMap<String,String> hashMap){
        SQLiteDatabase db = getWritableDatabase();
        Set<String> keys= hashMap.keySet();
        for (String key: keys){
            db.execSQL("INSERT OR REPLACE INTO "+TABLE_PERSONAL+" VALUES(\'"+key+"\', \'"+hashMap.get(key)+"\');");
        }
        db.close();
    }
    public HashMap<String,String> getIndividualData(){
        SQLiteDatabase db = getWritableDatabase();
        HashMap<String,String> result = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PERSONAL+" WHERE 1",null);
        if(cursor!=null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                result.put(cursor.getString(0), cursor.getString(1));
                cursor.moveToNext();
            }
            cursor.close();
        } else {
            result.put("leer","leer");
        }
        return result;
    }
    public void changeIndividualValue(String key,String value){
        SQLiteDatabase db =getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_PERSONAL+" SET "+COLUMN_VALUE+" = \'"+value+"\' WHERE "+COLUMN_KEY+" = \'"+key+"\';");
        db.close();
    }
    public void insertIndividualValue(String key,String value){
        SQLiteDatabase db =getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO "+TABLE_PERSONAL+" VALUES(\'"+key+"\', \'"+value+"\');");
        db.close();
    }
    public String getIndividualValue(String key){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+COLUMN_VALUE+" FROM "+TABLE_PERSONAL+" WHERE "+COLUMN_KEY+" = \'"+key+"\';",null);
        if (cursor==null) return "notfound";
        cursor.moveToFirst();
        String res = cursor.getString(0);
        cursor.close();
        db.close();
        return res;
    }

    public void clearValueKeeper(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PERSONAL,null,null);
        db.close();
    }

    public String unlockDaily(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+COLUMN_LECTURENAME+" FROM "+TABLE_LESSIONS+" WHERE "+COLUMN_STATUS+" IS -99",null);
        if (cursor!=null){
            cursor.moveToFirst();
            String name = cursor.getString(0);
            db.execSQL("UPDATE "+TABLE_LESSIONS+" SET "+COLUMN_STATUS+" = 0 WHERE "+COLUMN_LECTURENAME+" = \'"+name+"\';");
            return name;
        }
        return "There is no new Lession";
    }

    public void uploadDB(){
        try {
            URL url = new URL("https://app.seafile.de/u/d/4a569e6c81/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void exportDB(){
        Log.d("DBHandler","exporting DB");
        String user = ValueKeeper.getInstance().getVpnCode();
        String timestamp = String.valueOf(System.currentTimeMillis());
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "com.bp"+"/databases/"+DB_NAME;
        String backupDBPath = user+DB_NAME+timestamp;
        File currentDB = new File(data,currentDBPath);
        File backupDB = new File(sd,backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
}
