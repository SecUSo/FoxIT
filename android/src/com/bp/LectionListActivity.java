package com.bp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

//Displayes the lections corresponding to a certain course and manages their usage
public class LectionListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //List of lectionDescribtions, send by ClassListActivity
    static String[] lectionStringArray; //has to be static for now
    static String className;//has to be static for now
    static String classDescriptionText; //Text describing the class currently on display
    boolean descriptionVisible = false; //true if the classDescription is visible
    ArrayAdapter<String> adapter;

    //list of lectionObjects generated to fill the ListView
    public ArrayList<LectionObject> lectionObjectList = new ArrayList<>();

    Toolbar toolbar;

    /**
     * @author Tim
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lection_list);
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        // sets our toolbar as the actionbar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Toolbar");
        }


        //retrieve the lectionDescriptions
        if (getIntent().getStringArrayExtra("lection") != null) {
            lectionStringArray = getIntent().getStringArrayExtra("lection");
            //lectionStringArray = createLessionsString(readCSV()); //get from CSV  TODO: klären welchen von beiden richtig ist
        }

        //retrieve the className
        if (getIntent().getStringExtra("name") != null) {
            className = getIntent().getStringExtra("name");
        }

        if (getIntent().getStringExtra("description") != null) {
            classDescriptionText = getIntent().getStringExtra("description");
        }

        if (getIntent().getIntExtra("icon", 0) != 0) {
            ImageView icon = (ImageView) findViewById(R.id.image_class_icon);
            icon.setImageDrawable(ContextCompat.getDrawable(this, getIntent().getIntExtra("icon", 0)));
        }


        lectionObjectList = dbHandler.getLectionsFromDB(className);
        Log.d("lectionObjectList=empty", Boolean.toString(lectionObjectList.isEmpty()));
        lectionStringArray = new String[lectionObjectList.size()];

        TextView name = (TextView) findViewById(R.id.text_class_name);
        name.setText(className);

        int lectionNumber = lectionObjectList.size();
        int solvedLectionNumber = 0;
        for (LectionObject l : lectionObjectList) {
            if (l.getProcessingStatus() == 3) {
                solvedLectionNumber++;
            }
        }

        TextView solved = (TextView) findViewById(R.id.text_percentage_solved);
        solved.setText(Integer.toString(solvedLectionNumber) + "/" + Integer.toString(lectionNumber));

        TextView description = (TextView) findViewById(R.id.description_text);
        description.setText(classDescriptionText);


        /**
         * Display the descriptionText if the headline is pressed
         * @author Tim
         */
        View.OnClickListener expandText = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (descriptionVisible) {
                    //hide the description if it is visible
                    TextView classDescription = (TextView) findViewById(R.id.description_text);
                    classDescription.setVisibility(View.GONE);
                    //make the icon visible
                    ImageView moreVertImage = (ImageView) findViewById(R.id.more_vert_image);
                    moreVertImage.setVisibility(View.VISIBLE);

                    descriptionVisible = false;
                } else {
                    //show the description Text if it is invisible
                    TextView classDescription = (TextView) findViewById(R.id.description_text);
                    classDescription.setVisibility(View.VISIBLE);
                    //hide the icon
                    ImageView moreVertImage = (ImageView) findViewById(R.id.more_vert_image);
                    moreVertImage.setVisibility(View.GONE);

                    descriptionVisible = true;
                }
            }

        };

        // show the classDescription on Click
        RelativeLayout descriptionFrame = (RelativeLayout) findViewById(R.id.description_frame);
        descriptionFrame.setOnClickListener(expandText);

        // show the classDescription on Click
        RelativeLayout classHeadline = (RelativeLayout) findViewById(R.id.class_headline_frame);
        classHeadline.setOnClickListener(expandText);


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

        if (lectionObjectList.get(position).getProcessingStatus() == 0) {

            Bundle tradeInfos = new Bundle();
            tradeInfos.putString("target", lectionObjectList.get(position).getLectionName());

            //add the acornCountFragment to the activity's context
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            TradeRequestFragment_lection tradeRequest = new TradeRequestFragment_lection();
            tradeRequest.setArguments(tradeInfos);
            //add the fragment to the count_frame RelativeLayout
            transaction.add(R.id.count_frame, tradeRequest, "count");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();


        } else {
            if (lectionObjectList.get(position).getNextfreetime() > (System.currentTimeMillis())) {


            } else {

                Log.d("MyApp", "CurrentTime" + Long.toString((System.currentTimeMillis()) % Integer.MAX_VALUE));
                Log.d("MyApp", "duTime" + Long.toString(lectionObjectList.get(position).getNextfreetime()));


                Intent intent = new Intent(getApplicationContext(), LectionActivity.class);
                //to inform lectionActivity which lection is to be displayed
                Log.d("how many lections inLOL", Integer.toString(lectionObjectList.size()));
                Log.d("position is", Integer.toString(position));
                Log.d("thenameis", lectionObjectList.get(position).getLectionName());

                Log.d("thecontentis", lectionObjectList.get(position).getContent());
                intent.putExtra("lection", lectionObjectList.get(position).getContent());
                intent.putExtra("name", lectionObjectList.get(position).getLectionName());
                intent.putExtra("type", lectionObjectList.get(position).getType());
                intent.putExtra("delay", lectionObjectList.get(position).getDelaytime());
                intent.putExtra("freetime", lectionObjectList.get(position).getNextfreetime());
                intent.putExtra("status", lectionObjectList.get(position).getProcessingStatus());
                intent.putExtra("acorn", lectionObjectList.get(position).getReward());
                startActivity(intent);
            }
        }


    }

    /**
     * @author Tim
     * reloads the listEntrys afterwards,
     */
    @Override
    public void onResume() {
        ListView lectionList = (ListView) findViewById(R.id.headline_frame);
        //creates the listView
        adapter = new MyListAdapter_lection();
        lectionList.setAdapter(adapter);
        lectionList.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    /**
     * class to define the way the settings are displayed in the listView by defining how the content is displayed in the list entries
     *
     * @author Tim
     */
    private class MyListAdapter_lection extends ArrayAdapter<String> {
        public MyListAdapter_lection() {
            //here is defined what layout the listView uses for a single entry
            super(getApplicationContext(), R.layout.layout_settings, lectionStringArray);

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
                itemView = getLayoutInflater().inflate(R.layout.layout_lection_listentry, parent, false);
            }

            LectionObject lection = lectionObjectList.get(position);
            //new LectionObject(lectionStringArray[position]);

            //setting the text for lectionName
            TextView lectionName = (TextView) itemView.findViewById(R.id.text_lection_name);
            lectionName.setText(lection.getLectionName());
            if (lection.getProcessingStatus() == 0) {
                lectionName.setTextColor(Color.GRAY);
            } else {
                lectionName.setTextColor(Color.BLACK);
            }


            //setting the stars
            ImageView solvedIcon = (ImageView) itemView.findViewById(R.id.image_lection_solved);
            if (lection.getNextfreetime() > System.currentTimeMillis()) {
                solvedIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_hourglass_empty_black_48dp));
            } else {

                solvedIcon.setImageDrawable(getIcon(lection.getProcessingStatus()));
            }
            return itemView;
        }

        /**
         * returns the fitting starIcon for a lections state of solving
         *
         * @param processingStatus the lections processingStatus accessible with "processingStatus"
         * @return
         * @author Tim
         */
        Drawable getIcon(int processingStatus) {
            Context context = getApplicationContext();
            switch (processingStatus) {
                case 0:
                    return ContextCompat.getDrawable(context, R.mipmap.ic_lock_outline_black_48dp);
                case 1:
                    return ContextCompat.getDrawable(context, R.mipmap.stern_leer);
                case 2:
                    return ContextCompat.getDrawable(context, R.mipmap.stern_halbvoll);
                case 3:
                    return ContextCompat.getDrawable(context, R.mipmap.stern_voll2);
                default:
                    return ContextCompat.getDrawable(context, R.mipmap.stern_leer);
            }
        }

    }


    /**
     * handles the purchase of a lection
     *
     * @return if the purchase was successful
     * @author Tim
     */
    public boolean purchase(String articleOfCommerce) {
        for (LectionObject l : lectionObjectList) {
            if (l.getLectionName().equals(articleOfCommerce)) {
                //sets the lections status to unlocked
                l.setProcessingStatus(1);
                //update the listView
                adapter.notifyDataSetChanged();
                DBHandler dbHandler = new DBHandler(this, null, null, 1);
                dbHandler.changeLectionToUnlocked(articleOfCommerce);
                dbHandler.close();
                return true;
            }
        }
        return false;
    }
}




