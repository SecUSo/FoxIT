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

    String[] lectionDescriptionArray = {
            "[name~Deep, Dark, - Lost!][0~type~text'text~Eine Einführung ins Deep Web und Dark Web.][1~type~text'text~Allgemein lässt sich das World Wide Web (WWW) in zwei Bereiche einteilen: Surface Web und Deep Web.\n" +
                    "][2~type~text'text~Surface Web:\n" +
                    "Unter dem Surface Web versteht man diejenigen Inhalte und Webseiten, welche über Suchmaschinen wie z.B. Google, Bing, Yahoo oder DuckDuckGo gefunden werden können. ][3~type~text'text~Deep Web:\n" +
                    "Hierzu zählen alle nicht referenzierten Inhalte des World Wide Web. Wird z.B. über die Webseite einer Fluggesellschaft nach Flügen gesucht, so sind diese Ergebnisse nicht referenziert.][4~type~text'text~Die Flüge liegen in einer Datenbank der Fluggesellschaft und können über eine allgemeine Suchmaschine nicht gefunden werden. Sie zählen damit zum Deep Web. \n" +
                    "Im weiteren Sinne sind dies also Daten, die zwar online verfügbar, aber “vergraben” sind.][5~type~text'text~Dark Web:\n" +
                    "Das Dark Web ist ein Teil des Deep Web. Normalerweise verweisen Webseiten auf andere Seiten, welche wiederum auf weitere verweisen u.s.w.. \n" +
                    "Es gibt jedoch Webseiten, auf die keine Verweise existieren. Kennt man die genaue URL sind sie erreichbar, ansonsten bleiben sie verborgen. Dies gilt z.B. für unveröffentliche Bilder oder Blogeinträge, sowie Subdomain-Namen.][6~type~text'text~Ein weiteres Beispiel des Dark Web sind virtuelle private Netzwerke (VPNs). Das Internet ist ein Netzwerk bestehend aus Netzwerken, von denen einige privat sind. \n" +
                    "Um sich z.B. mit dem eigenen Firmennetzwerk von zuhause aus zu verbinden, sind spezielle Einstellungen oder Software nötig. Für normale Internetnutzer bleiben die Firmendaten verborgen.][7~type~text'text~TOR:\n" +
                    "Ein bekanntes VPN des Dark Web ist das Darknet Tor (früher: The onion routing). Oberstes Ziel des Tor-Netzes ist es, Verbindungsdaten anonym zu halten. In der Realität ist dies ein Magnet für illegale Tätigkeiten bishin zu Menschen-, Waffen-, und Drogenhandel.][8~type~text'text~Dennoch schafft Tor auch freien Internetzugang in stark zensierten Regionen oder anonyme Kommunikation. \n" +
                    "So war das Tor-Projekt 2011 Gewinner des Preises für gesellschaftlichen Nutzen der Free Software Foundation. ]\n" +
                    "[solved~false]",
            "[name~Deep und Dark Web Quiz][0~type~quiz4'text~Das Deep Web ist'answer1text~Größer als das Surface Web'answer1solution~true'answer2text~Das Gleiche wie das Darknet'answer2solution~false'answer3text~Teil des World Wide Web'answer3solution~true'answer4text~Ein abgegrenzter Bereich des Internets'answer4solution~false'successText~Du hast Recht.'failureText~Leider nicht \n" +
                    "ganz richtig.'points~3][1~type~quiz4'text~Typische Teile eines Deep Web sind z.B.'answer1text~Bibliothek-Datenbänke'answer1solution~true'answer2text~Alle Inhalte, die man nicht über Verlinkungen erreichen kann'answer2solution~true'answer3text~Webseiteninterne Suchergebnisse'answer3solution~true'answer4text~Suchmaschinenergebnisse'answer4solution~false'successText~Das ist Richtig.'failureText~Das solltest du dir nochmal\n" +
                    "angucken.'points~3][2~type~quiz4'text~Legal ist…'answer1text~Der Zugriff auf das Tor-Netzwerk'answer1solution~true'answer2text~Alle Aktivitäten innerhalb des Tor-Netzwerkes'answer2solution~false'answer3text~Einen Dienst im Tor-Netzwerk anzubieten'answer3solution~true'answer4text~Sich den Client “Onion Proxy” herunterzuladen'answer4solution~true'successText~Sehr gut!'failureText~Wenn du es nicht weißt, recherchier doch mal\n" +
                    "im Internet.'points~3][3~type~quiz4'text~Das Tor-Netzwerk'answer1text~Ist bekannt für Anonymität'answer1solution~true'answer2text~Ist zu 100% anonym'answer2solution~false'answer3text~Ist an dem Aufbau eines VPN angelehnt'answer3solution~true'answer4text~Ist erschaffen worden, um anonym Torheiten begehen zu können'answer4solution~false'successText~Das stimmt.'failureText~Nicht ganz \n" +
                    "Richtig.'points~3][4~type~certificate'successText~Bravo!\n" +
                    "Du hast bestanden. Das war garnicht so einfach!'failureText~Schade.\n" +
                    "Du hast leider nicht bestanden. Aber wenn du fleißig lernst, klappt es sicher beim nächsten mal!'pointsNeeded~12]"};

    //"[4~type~quiz4'text~Das Tor-Netzwerk'answer1text~Ist bekannt für Anonymität'answer1solution~true'answer2text~Ist zu 100% anonym'answer2solution~false'answer3text~Ist an dem Aufbau eines VPN angelehnt'answer3solution~true'answer4text~Ist erschaffen worden, um anonym Torheiten begehen zu können'answer4solution~false'successText~Das stimmt.'failureText~Nicht ganz Richtig.'points~3]\n" +
    //"[5~type~quiz4'text~Routing'answer1text~beschreibt das Weiterleiten von Informationen in Rechnernetzen'answer1solution~true'answer2text~Beschreibt das Einrichten eines Computers'answer2solution~false'answer3text~Ist eine Grundlage moderner Telekommunikation'answer3solution~true'answer4text~Verliert in letzter Zeit immer mehr Bedeutung'answer4solution~false'successText~Richtig!'failureText~Leider falsch. Aber so hast du auch was gelernt.'points~3]\n" +
    //"[6~type~certificate'successText~Bravo!\n" +
    //"Du hast bestanden. Das war garnicht so einfach!'failureText~Schade.\n" +
    //"Du hast leider nicht bestanden. Aber wenn du fleißig lernst, klappts sicher beim nächsten mal!'pointsNeeded~12]"};


    String[] classList = {};

    String[] firstLection = {"[name~Meine Einstellung gegenüber Privatsphäre][0~type~text'text~Hallo! \nIn dieser Lektion geht es darum, ein bisschen etwas über Privatsphäre am Smartphone zu erfahren und erste Impulse für einen besseren Umgang mit Apps und Einstellungen zu setzten. Wir starten damit, herauszufinden was Privatsphäre für dich ist.]" +
            "[1~type~question'text~Also: \nInteressiert dich Privatsphäre?'buttonText~Ja'callClassMethod~slideSwitch'methodParameter~2'buttonText2~Nein'method2~'methodParameter2~]" +
            "[2~type~question'text~Sehr gut! \n Hast du schon einmal aktiv etwas für deine Privatsphäre am Smartphone gemacht?’buttonText~Ja’callClassMethod~’methodParameter~’buttonText2~Nein’method2~’methodParameter2~]" + "[solved~false]",
            "[name~Kleines Quiz][0~type~quiz4'text~Sollte man am Smartphone eine Bildschirmsperre einrichten?’answer1text~Braucht man nicht’answer1solution~false’answer2text~Entsperrmuster, damit bin ich sicher’answer2solution~false’answer3text~Hat jemand anderes mein Handy, kann ich eh nichts mehr tun. Nein’answer3solution~false’answer4text~Zahlencode oder Passwort, nur dann bin ich sicher’answer4solution~true’successText~Das stimmt! Nur mit Passwort oder Zahlencode ist man größtenteils gesichert!’failureText~Eher nicht… Um dein Smartphone zu sichern solltest du auf jeden Fall einen Zahlencode oder ein Passwort verwenden!’points~10][solved~false]"
    };

    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        // sets our toolbar as the actionbar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //ClassObject test = new ClassObject("Deep Web", lectionDescriptionArray);
        //test.setDescriptionText("Eine Einführung ins Deep Web und Dark Web. Bereiche des World Wide Web jenseits der Suchmaschinen.");

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

            DBHandler dbHandler = new DBHandler(getAppContext(), null, null, 21);
            TextView solved = (TextView) itemView.findViewById(R.id.textView_solvedLessons);
            solved.setText(dbHandler.getNumberOfSolvedLessons(classObjectList.get(position).getName()));
            dbHandler.close();


            return itemView;
        }


    }
}
