package com.foxyourprivacy.f0x1t.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.foxyourprivacy.f0x1t.AsyncTasks.DBUploadTask;
import com.foxyourprivacy.f0x1t.DBHandler;
import com.foxyourprivacy.f0x1t.LectionObject;
import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.ValueKeeper;

import java.util.ArrayList;

public class Home extends FoxITActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        RelativeLayout lectionButton = (RelativeLayout) findViewById(R.id.firstLayout);

        RelativeLayout settingsButton = (RelativeLayout) findViewById(R.id.sixtLayout);

        settingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });


        lectionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ClassListActivity.class);
                startActivity(i);
            }
        });

        RelativeLayout trophyButton = (RelativeLayout) findViewById(R.id.fifthLayout);


        trophyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), TrophyRoomActivity.class);
                startActivity(i);


            }

        });

        RelativeLayout animationButton = (RelativeLayout) findViewById(R.id.thirdLayout);

        animationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), AnimationLauncher.class);
                startActivity(i);

            }

        });

    }


    @Override
    public void onStart() {
        super.onStart();
        ValueKeeper v = ValueKeeper.getInstance();
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        if ((!dbHandler.checkIfInside(dbHandler.getWritableDatabase(), DBHandler.TABLE_PERSONAL, DBHandler.COLUMN_KEY + " = \'analysisDoneBefore\'") || dbHandler.getIndividualValue("analysisDoneBefore").equals("false")) && !v.analysisDoneBefore.equals(true)) {//v.analysisDoneBefore){//!dbHandler.checkIfInside(dbHandler.TABLE_PERSONAL,dbHandler.COLUMN_KEY+" = \'firstrun\'")){//!v.wasEvaluationDisplayed){
            v.wasEvaluationDisplayed = true;
            dbHandler.close();
            Intent intent = new Intent(getApplicationContext(), OnboardingActivity.class);
            startActivity(intent);
        } else {
            if (shouldEvaluationBeDisplayed()) {

                //Upload DB
                new DBUploadTask().execute(this);

                Intent intent = new Intent(getApplicationContext(), LectionActivity.class);
                String className = "Deep Web";//"Evaluation";
                int position = 0;//getNumberOfCurrentEvaluation();
                ArrayList<LectionObject> lectionObjectList = dbHandler.getLectionsFromDB(className);

                //[name~EvaluationTest][0~texte'text~Eine Einführuark Net.;scalee_Allgemein lässt sich einteilen: Surface Web und Deep Web.][solved~false]
//                Evaluation;AppEvaluation;0;1;6;0;scalee_Wir haben bemerkt, dass du in letzter Zeit eine App deinstalliert hast. Wie sehr hatte das mit dem Schutz deiner Privatsphäre zu tun?;;;;;;;
                String evaluationLection;
                intent.putExtra("lection", "[name~EvaluationTest][0~type~text'text~Da du unsere App jetzt ein bisschen benutzt hast, möchten wir dir ein paar Fragen stellen.][1~type~scalee'text~Ich fühle mich vor Datendiebstahl sicher.\n" +
                        "][2~type~scalee'text~Ich finde das Thema digitale Privatsphäre sehr interessant.\n" +
                        "][3~type~scalee'text~Ich weiß, wie ich meine digitale Privatsphäre schützen kann.\t\n" +
                        "][4~type~scalee'text~Ich versuche die Privatheitsbedingungen auf meinem Smartphone zu verbessern.\n" +
                        "][5~type~texte'text~Hier kannst du Verbesserungsvorschläge für die App loswerden.][solved~false]");
                intent.putExtra("name", "timeEval:" + Integer.toString(v.currentEvaluation));
                intent.putExtra("type", 0);//lectionObjectList.get(position).getType());
                intent.putExtra("delay", 0);//lectionObjectList.get(position).getDelaytime());
                intent.putExtra("freetime", 0L);//lectionObjectList.get(position).getNextfreetime());
                intent.putExtra("status", -99);//lectionObjectList.get(position).getProcessingStatus());
                intent.putExtra("acorn", 0);//lectionObjectList.get(position).getReward());
                startActivity(intent);

            } else {
                Log.d("Home", "analysis done & no evaluation");
                if (v.deinstalledApps.size() > 0) {
                    Log.d("Home", "deinstalled >0");
                    Intent intent = new Intent(getApplicationContext(), LectionActivity.class);
                    String className = "Deep Web";//"Evaluation";
                    int position = 0;//getNumberOfCurrentEvaluation();
                    ArrayList<LectionObject> lectionObjectList = dbHandler.getLectionsFromDB(className);

                    //[name~EvaluationTest][0~texte'text~Eine Einführuark Net.;scalee_Allgemein lässt sich einteilen: Surface Web und Deep Web.][solved~false]
//                Evaluation;AppEvaluation;0;1;6;0;scalee_Wir haben bemerkt, dass du in letzter Zeit eine App deinstalliert hast. Wie sehr hatte das mit dem Schutz deiner Privatsphäre zu tun?;;;;;;;

                    intent.putExtra("lection", "[name~EvaluationTest][0~type~text'text~Wir haben bemerkt, dass du die App " + v.deinstalledApps.get(0) + " deinstalliert hast.][1~type~scalee'text~Hast du die App gelöscht um deine Privatsphäre besser zu schützen?][2~type~texte'text~Was hat dich an der deinstallierten App gestört?][solved~false]");
                    intent.putExtra("name", "appEval:" + v.deinstalledApps.get(0));
                    intent.putExtra("type", -99);//lectionObjectList.get(position).getType());
                    intent.putExtra("delay", 0);//lectionObjectList.get(position).getDelaytime());
                    intent.putExtra("freetime", 0L);// lectionObjectList.get(position).getNextfreetime());
                    intent.putExtra("status", 0);// lectionObjectList.get(position).getProcessingStatus());
                    intent.putExtra("acorn", 0);//lectionObjectList.get(position).getReward());
                    startActivity(intent);
                }

            }
            dbHandler.close();

        }


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

}
