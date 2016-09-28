package com.bp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        DBHandler dbHandler = new DBHandler(this,null,null,1);
        dbHandler.updateLessions(readCSV(R.raw.lektionen));
        dbHandler.updatePermissions(readCSV(R.raw.permissions));
        dbHandler.updateClasses(readCSV(R.raw.classes));
        dbHandler.updateSettingDescriptions(readCSV(R.raw.settings));


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
        transaction = transaction.addToBackStack("permissionDescription");
        transaction.commit();

    }

    public ArrayList readCSV(int input){
        InputStream is = getResources().openRawResource(input);
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

}
