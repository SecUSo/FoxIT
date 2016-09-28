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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LectionListActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener {

    //List of lectionDescribtions, send by ClassListActivity
    static String[] lectionStringArray; //has to be static for now
    static String className;//has to be static for now
    static String classDescriptionText; //Text describing the class currently on display
    boolean descriptionVisible=false; //true if the classDescription is visible
    ArrayAdapter<String> adapter;

    //list of lectionObjects generated to fill the ListView
    public ArrayList<LectionObject> lectionObjectList= new ArrayList<>();

    Toolbar toolbar;

    /**
     * @author Tim
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lection_list);
        DBHandler dbHandler = new DBHandler(this,null,null,1);

        // sets our toolbar as the actionbar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        if(toolbar!=null){
        toolbar.setTitle("Toolbar");}

        //retrieve the className
        if(getIntent().getStringExtra("name")!=null){
        className= getIntent().getStringExtra("name");}

        if(getIntent().getStringExtra("description")!=null){
            classDescriptionText=getIntent().getStringExtra("description");
        }

        lectionObjectList = dbHandler.getLectionsFromDB(className);
        //this Array is needed for the Views but doesn't need content
        lectionStringArray = new String[lectionObjectList.size()];

        TextView name=(TextView) findViewById(R.id.text_class_name);
        name.setText(className);
        TextView solved=(TextView) findViewById(R.id.text_percentage_solved);
        solved.setText(dbHandler.countSolvedLessions(className));

        TextView description=(TextView) findViewById(R.id.description_text);
        description.setText(classDescriptionText);

        /**
         * Display the descriptionText if the headline is pressed
         * @author Tim
         */
        View.OnClickListener expandText =new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(descriptionVisible){
                    //hide the description if it is visible
                    TextView classDescription = (TextView) findViewById(R.id.description_text);
                    classDescription.setVisibility(View.GONE);
                    //make the icon visible
                    ImageView moreVertImage = (ImageView) findViewById(R.id.more_vert_image);
                    moreVertImage.setVisibility(View.VISIBLE);

                    descriptionVisible=false;
                }else{
                    //show the description Text if it is invisible
                    TextView classDescription = (TextView) findViewById(R.id.description_text);
                    classDescription.setVisibility(View.VISIBLE);
                    //hide the icon
                    ImageView moreVertImage = (ImageView) findViewById(R.id.more_vert_image);
                    moreVertImage.setVisibility(View.GONE);

                    descriptionVisible=true;}
            }

        };

        // show the classDescription on Click
        RelativeLayout descriptionFrame = (RelativeLayout) findViewById(R.id.description_frame);
        descriptionFrame.setOnClickListener(expandText);

        // show the classDescription on Click
        RelativeLayout classHeadline= (RelativeLayout) findViewById(R.id.class_headline_frame);
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
     * @author Tim
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //if lession wasn't activated yet
        if(lectionObjectList.get(position).getProcessingStatus()==0) {

            Bundle tradeInfos =new Bundle();
            tradeInfos.putString("target",lectionObjectList.get(position).getLectionName());

            //add the acornCountFragment to the activity's context
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            TradeRequestFragment_lection tradeRequest= new TradeRequestFragment_lection();
            tradeRequest.setArguments(tradeInfos);
            //add the fragment to the count_frame RelativeLayout
            transaction.add(R.id.count_frame, tradeRequest, "count");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();

        //if lession has an active delay
        }else{if(lectionObjectList.get(position).getNextfreetime()> System.currentTimeMillis()){


        }else{
        Intent intent = new Intent(getApplicationContext(), LectionActivity.class);
        //to inform lectionActivity which lection is to be displayed
        Log.d("how many lections inLOL", Integer.toString(lectionObjectList.size()));
        Log.d("position is", Integer.toString(position));
        Log.d("thenameis",lectionObjectList.get(position).getLectionName());

        Log.d("thecontentis",lectionObjectList.get(position).getContent());
        intent.putExtra("lection",lectionObjectList.get(position).getContent());
        intent.putExtra("name",lectionObjectList.get(position).getLectionName());
        intent.putExtra("type",lectionObjectList.get(position).getType());
        intent.putExtra("delay",lectionObjectList.get(position).getDelaytime());
        intent.putExtra("freetime",lectionObjectList.get(position).getNextfreetime());
        intent.putExtra("status",lectionObjectList.get(position).getProcessingStatus());
        intent.putExtra("acorn",lectionObjectList.get(position).getReward());
        startActivity(intent);}}

    }

    /**
     * @author Tim
     * just an experiment if you can change the listEntrys afterwards,
     * I will remove it at given time
     */
    @Override
    public void onResume(){
        super.onResume();
        ListView lectionList =(ListView) findViewById(R.id.headline_frame);
        //creates the listView
        adapter = new MyListAdapter_lection();
        lectionList.setAdapter(adapter);
        lectionList.setOnItemClickListener(this);
    }

    /**class to define the way the settings are displayed in the listView
     * @author Tim
     */
    private class MyListAdapter_lection extends ArrayAdapter<String> {
        public MyListAdapter_lection(){
            //here is defined what layout the listView uses for a single entry
            super(getApplicationContext(),R.layout.layout_settings,lectionStringArray);

        }

        /**
         * Here is defined how the XML-Layout is filled  by the data stored in the lectionStringArray.
         * @author Tim
         * @param position position in the array used
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //convertView has to be filled with layout_app if it's null
            View itemView =convertView;
            if(itemView==null){
                itemView=getLayoutInflater().inflate(R.layout.layout_lection_listentry,parent,false);
            }

            LectionObject lection = lectionObjectList.get(position);
                    //new LectionObject(lectionStringArray[position]);

            //setting the text for lectionName
            TextView lectionName = (TextView) itemView.findViewById(R.id.text_lection_name);
            lectionName.setText(lection.getLectionName());
            if(lection.getProcessingStatus()==0){
                lectionName.setTextColor(Color.GRAY);
            }else{
                lectionName.setTextColor(Color.BLACK);
            }


            //setting the stars
            ImageView solvedIcon =(ImageView) itemView.findViewById(R.id.image_lection_solved);
            solvedIcon.setImageDrawable(getIcon(lection.getProcessingStatus()));

            return itemView;
        }

        /**
         * returns the fitting starIcon for a lections state of solving
         * @author Tim
         * @param processingStatus the lections processingStatus accessible with "getProcessingStatus"
         * @return the Drawable icon
         *
         */
        Drawable getIcon(int processingStatus){
            Context context=getApplicationContext();
            switch(processingStatus){
                case 0: return ContextCompat.getDrawable(context,R.mipmap.stern_leer);
                case 1: return ContextCompat.getDrawable(context,R.mipmap.stern_leer);
                case 2: return ContextCompat.getDrawable(context,R.mipmap.stern_halbvoll);
                case 3: return ContextCompat.getDrawable(context,R.mipmap.stern_voll2);
                default: return  ContextCompat.getDrawable(context,R.mipmap.stern_leer);
            }
        }

    }


    /**handles the purchase of a lection
     * @author Tim
     * @param articleOfCommerce
     * @return if the purchase was successful
     */
    public boolean purchase(String articleOfCommerce){
         for(LectionObject l:lectionObjectList){
             if(l.getLectionName().equals(articleOfCommerce)){
                //sets the lections status to unlocked
                l.setProcessingStatus(1);
                //update the listView
                adapter.notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }




    /**
     * @author HannahD
     *public void onBackPressed() {
    */

    private ArrayList readCSV(){
        InputStream is = getResources().openRawResource(R.raw.lektionen);
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

    private String[] createLessionsString(ArrayList<String[]> theLessions){
        String[] result =new String[theLessions.size()];
        Log.d("LectionListActivity","create Lession Strings wurde gestartet");
        StringBuffer sb = new StringBuffer();
        int counter = 0;

        for (String[] lessionArray:theLessions) {
            sb.append("[name~" + lessionArray[0]+"]");
            for(int i=1;i<lessionArray.length;i++){
                Log.d("eine Slide",lessionArray[i]);
                String[] slides = lessionArray[i].split("_");
                slides[0]=slides[0].toLowerCase();
                Log.d("der Typ: ",slides[0]);
                if(!(slides[0].equals("read")||slides[0].equals("finished")||slides[0].equals("false"))) {
                    sb.append("[" + (i - 1) + "~type~" + slides[0] + "\'");
                    switch (slides[0]) {
                        case "text":
                            sb.append("text~" + slides[1]);
                            break;
                        case "quiz3":
                            sb.append("points~" + slides[1] + "\'text~" + slides[2] + "\'answer1text~" + slides[3] + "\'answer1solution~" +
                                    slides[4] + "\'answer2text~" + slides[5] + "\'answer2solution~" +
                                    slides[6] + "\'answer3text~" + slides[7] + "\'answer3solution~" +
                                    slides[8]);
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
                }else sb.append("[solved~"+slides[0]+"]");
            }
            result[counter]=sb.toString();
            sb.setLength(0);
            Log.d("Eine Lektion",result[counter]);
            counter++;
        }
        return result;
    }
}