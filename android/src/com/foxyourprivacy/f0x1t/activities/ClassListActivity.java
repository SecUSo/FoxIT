package com.foxyourprivacy.f0x1t.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.foxyourprivacy.f0x1t.ClassObject;
import com.foxyourprivacy.f0x1t.DBHandler;
import com.foxyourprivacy.f0x1t.R;

import java.util.ArrayList;

/**
 * Created by Tim on 01.08.2016.
 */
public class ClassListActivity extends FoxITActivity implements AdapterView.OnItemClickListener {

    public ArrayList<ClassObject> classObjectList = new ArrayList<>();


    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // sets our toolbar as the actionbar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);


        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        //classObjectList.add(test);
        classObjectList = dbHandler.getClasses();

        //sort Erstes Tapsen and Daily Lessons on the first 2 slots
        ClassObject tempclass1 = classObjectList.get(0);
        int i = 0;
        while (!classObjectList.get(i).getName().equals("Erstes Tapsen")) {
            i++;
        }
        classObjectList.set(0, classObjectList.get(i));
        classObjectList.set(i, tempclass1);
        ClassObject tempclass2 = classObjectList.get(1);
        int j = 0;
        while (j<classObjectList.size()&&!classObjectList.get(j).getName().equals("Daily Lessons")) {
        if(j<classObjectList.size()-1){
            j++;}
        }
        classObjectList.set(1, classObjectList.get(j));
        classObjectList.set(j, tempclass2);

        dbHandler.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_activities, menu);
        menu.findItem(R.id.action_options).setVisible(false);
        menu.findItem(R.id.goOn).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.goBack) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * onItemClick the Activity is switched to lectionActivity
     *
     * @author Tim
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), LectionListActivity.class);
        //to inform lectionActivity which lection is to be displayed
        intent.putExtra("description", classObjectList.get(position).getDescriptionText());
        intent.putExtra("name", classObjectList.get(position).getName());
        intent.putExtra("icon", getClassIcon(classObjectList.get(position).getName()));
        startActivity(intent);
    }

    /**
     * just an experiment if you can change the listEntrys afterwards
     */
    @Override
    public void onResume() {
        super.onResume();
        //TODO: sollte vielleicht auskommentiert werden oder verändert, so dass die Liste beim zurück-gehen nicht immer wieder von oben angezeigt wird.
        ListView lectionList = (ListView) findViewById(R.id.headline_frame);
        //creates the listView
        ArrayAdapter<ClassObject> adapter = new MyListAdapter_class();
        lectionList.setAdapter(adapter);
        lectionList.setOnItemClickListener(this);
    }

    private int getClassIcon(String className) {
        switch (className) {
            case "Erstes Tapsen":
                return R.mipmap.literature;
            case "Facebook":
                return R.mipmap.class_facebook;
            case "Google":
                return R.mipmap.class_google;
            case "Berechtigungen":
                return R.mipmap.class_berechtigungen;
            case "Messaging-Apps":
                return R.mipmap.class_messanging;
            case "Die Gesellschaft":
                return R.mipmap.class_lighthouse;
            case "Passwörter":
                return R.mipmap.class_passwort;
            case "Datenschutzgesetze":
                return R.mipmap.class_law;
            case "Daily Lessons":
                return R.mipmap.class_daily;
            case "Verschlüsselung":
                return R.mipmap.class_verschlusselung;
            case "root & superuser":
                return R.mipmap.class_hashtag;
            case "Deep Web":
                return R.mipmap.onion;
            default:
                return R.mipmap.ring;

        }
    }

    /**
     * class to define the way the settings are displayed in the listView
     *
     * @author Tim
     */
    private class MyListAdapter_class extends ArrayAdapter<ClassObject> {
        public MyListAdapter_class() {
            //here is defined what layout the listView uses for a single entry
            super(getApplicationContext(), R.layout.layout_settings, classObjectList);

        }

        /**
         * Here ist defined how the XML-Layout is filed  by the data stored in the lectionStringArray.
         *
         * @param position    position in the array used
         * @param convertView
         * @param parent
         * @return
         * @author Tim
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //convertView has to be filled with layout_app if it's null
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().
                        inflate(R.layout.layout_class_listentry,
                                parent,
                                false);
            }

            ImageView classIcon = (ImageView) itemView.findViewById(R.id.image_class_icon);

            classIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), getClassIcon(classObjectList.get(position).getName())));


            //setting the text for className
            TextView className = (TextView) itemView.findViewById(R.id.text_class_name);
            className.setText(classObjectList.get(position).getName());

            DBHandler dbHandler = new DBHandler(getApplicationContext(), null, null, 21);
            TextView solved = (TextView) itemView.findViewById(R.id.textView_solvedLessons);
            solved.setText(dbHandler.getNumberOfSolvedLessons(classObjectList.get(position).getName()));
            dbHandler.close();


            return itemView;
        }


    }
}
