package com.bp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bp.DBHandler;


public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //Fragment is created
        SettingsFragment fragment = new SettingsFragment();

        //Bundle permissionName = new Bundle();
        //permissionName.putString("permissionName", permissionArray[position]);
        //permissionName.putInt("appRating", appRating);
        //fragment.setArguments(permissionName);

        //add fragment so the activitys' context
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.first_fragment_frame, fragment, "permissionDescription");
        transaction.commit();

    }

    public ArrayList readCSV(int input, Context context){
        InputStream is = context.getResources().openRawResource(input);
        ArrayList result = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try{
            String templine;
            while ((templine=br.readLine())!=null){
                String[] csvrow = templine.split(";");
                result.add(csvrow);
            }
        } catch (IOException e){
            throw new RuntimeException("CSV file couldn't be read properly: "+e);
        } finally {
            try {
                is.close();
                br.close();
            } catch (IOException e){
                throw new RuntimeException("Input Stream couldn't be closed properly: "+e);
            }
        }
        return result;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_activities, menu);
        menu.findItem(R.id.goOn).setVisible(false);
        menu.findItem(R.id.goBack).setVisible(true);
        menu.findItem(R.id.action_options).setVisible(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.goBack) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void updatePermissions(Context context){
        DBHandler dbHandler = new DBHandler(context,null,null,1);
        dbHandler.updatePermissions(readCSV(R.raw.permissions,context));
        dbHandler.close();
    }
    public void updateLessions(Context context){
        DBHandler dbHandler = new DBHandler(context,null,null,1);
        dbHandler.updateLessions(readCSV(R.raw.lektionen,context));
        dbHandler.updateClasses(readCSV(R.raw.classes,context));
        dbHandler.close();
    }
    public void updateSettings(Context context){
        DBHandler dbHandler = new DBHandler(context,null,null,1);
        dbHandler.updateSettingDescriptions(readCSV(R.raw.settings,context));
        dbHandler.close();
    }


    @Override
    /**
     * overrides the behavior of the backButton for it to properly support Fragments and Fragments in Fragments (ChildFragments)
     * @author Tim
     */

    public void onBackPressed() {
        //if there is an fragment
        RelativeLayout firstFragmentFrame =(RelativeLayout) findViewById(R.id.first_fragment_frame);


        if (firstFragmentFrame.getVisibility()==View.GONE) {
                getFragmentManager().popBackStack(); //destroy PermissionListFragment
                firstFragmentFrame.setVisibility(View.VISIBLE); //make the hidden appList visible again

        } else {//if no fragments exist behave normal
            super.onBackPressed();
        }
    }


}
