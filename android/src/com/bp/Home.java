package com.bp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

public class Home extends FoxItActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        RelativeLayout lectionButton = (RelativeLayout) findViewById(R.id.firstLayout);

        RelativeLayout settingsButton = (RelativeLayout) findViewById(R.id.sixtLayout);

        settingsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(i);
            }
        });


        lectionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ClassListActivity.class);
                startActivity(i);
            }
        });

        RelativeLayout trophyButton = (RelativeLayout) findViewById(R.id.fifthLayout);


        trophyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),TrophyRoomActivity.class);
                startActivity(i);


            }

        });

        RelativeLayout animationButton = (RelativeLayout) findViewById(R.id.thirdLayout);

        animationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),AnimationLauncher.class);
                startActivity(i);

            }

        });

        }


    @Override
    public void onStart(){
    super.onStart();
        ValueKeeper v=ValueKeeper.getInstance();
        DBHandler dbHandler = new DBHandler(this,null,null,1);
        if ((!dbHandler.checkIfInside(dbHandler.getWritableDatabase(),DBHandler.TABLE_PERSONAL,DBHandler.COLUMN_KEY+" = \'analysisDoneBefore\'")||dbHandler.getIndividualValue("analysisDoneBefore").equals("false"))&&!v.analysisDoneBefore.equals(true)){//v.analysisDoneBefore){//!dbHandler.checkIfInside(dbHandler.TABLE_PERSONAL,dbHandler.COLUMN_KEY+" = \'firstrun\'")){//!v.wasEvaluationDisplayed){
            Log.d("Home","Not analysisDoneBefore");
            v.wasEvaluationDisplayed=true;
            dbHandler.close();
            Intent intent = new Intent(getApplicationContext(),OnboardingActivity.class);
            startActivity(intent);
        }else{
            Log.d("Home","analysisDoneBefore");
            if(shouldEvaluationBeDisplayed()){
                Intent intent = new Intent(getApplicationContext(),LectionActivity.class);
                String className="Deep Web";//"Evaluation";
                int position=0;//getNumberOfCurrentEvaluation();
                ArrayList<LectionObject> lectionObjectList= dbHandler.getLectionsFromDB(className);

                //[name~EvaluationTest][0~texte'text~Eine Einführuark Net.;scalee_Allgemein lässt sich einteilen: Surface Web und Deep Web.][solved~false]
//                Evaluation;AppEvaluation;0;1;6;0;scalee_Wir haben bemerkt, dass du in letzter Zeit eine App deinstalliert hast. Wie sehr hatte das mit dem Schutz deiner Privatsphäre zu tun?;;;;;;;
                String evaluationLection;
                if(true){
                    //TODO: ^ and \/ (if von true und never used?!)
                    evaluationLection=  "[name~EvaluationTest][0~type~texte'text~Eine Einführuark Net.][1~type~scalee'text~Allgemein lässt sich einteilen: Surface Web und Deep Web.][solved~false]";
                }
                intent.putExtra("lection","[name~EvaluationTest][0~type~texte'text~Eine Einführuark Net.][1~type~scalee'text~Allgemein lässt sich einteilen: Surface Web und Deep Web.][solved~false]");
                intent.putExtra("name", "timeEval:"+Integer.toString(v.currentEvaluation));
                intent.putExtra("type", 0);//lectionObjectList.get(position).getType());
                intent.putExtra("delay", 0);//lectionObjectList.get(position).getDelaytime());
                intent.putExtra("freetime", 0);//lectionObjectList.get(position).getNextfreetime());
                intent.putExtra("status", -99);//lectionObjectList.get(position).getProcessingStatus());
                intent.putExtra("acorn", 0);//lectionObjectList.get(position).getReward());
                startActivity(intent);

            }else{
                if(v.deinstalledApps.size()>0){
                    Intent intent = new Intent(getApplicationContext(),LectionActivity.class);
                    String className="Deep Web";//"Evaluation";
                    int position=0;//getNumberOfCurrentEvaluation();
                    ArrayList<LectionObject> lectionObjectList= dbHandler.getLectionsFromDB(className);

                    //[name~EvaluationTest][0~texte'text~Eine Einführuark Net.;scalee_Allgemein lässt sich einteilen: Surface Web und Deep Web.][solved~false]
//                Evaluation;AppEvaluation;0;1;6;0;scalee_Wir haben bemerkt, dass du in letzter Zeit eine App deinstalliert hast. Wie sehr hatte das mit dem Schutz deiner Privatsphäre zu tun?;;;;;;;
                    String evaluationLection;
                    if(true){
                        //TODO: ^ and \/ (if von true und never used?!)
                        evaluationLection=  "[name~EvaluationTest][0~type~texte'text~Eine Einführuark Net.][1~type~scalee'text~Allgemein lässt sich einteilen: Surface Web und Deep Web.][solved~false]";
                    }
                    intent.putExtra("lection","[name~EvaluationTest][0~type~texte'text~"+v.deinstalledApps.get(0)+"][1~type~scalee'text~Allgemein lässt sich einteilen: Surface Web und Deep Web.][solved~false]");
                    intent.putExtra("name", "appEval:"+v.deinstalledApps.get(0));
                    intent.putExtra("type", -99);//lectionObjectList.get(position).getType());
                    intent.putExtra("delay", 0);//lectionObjectList.get(position).getDelaytime());
                    intent.putExtra("freetime",0.0);// lectionObjectList.get(position).getNextfreetime());
                    intent.putExtra("status",0);// lectionObjectList.get(position).getProcessingStatus());
                    intent.putExtra("acorn", 0);//lectionObjectList.get(position).getReward());
                    startActivity(intent);
                }

            }

        }


        dbHandler.close();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_activities, menu);
        menu.findItem(R.id.goOn).setVisible(false);
        menu.findItem(R.id.goBack).setVisible(false);
        menu.findItem(R.id.action_options).setVisible(false);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new DBUploadTask().execute(this);

    }
}
