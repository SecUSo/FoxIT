package com.foxyourprivacy.f0x1t.Fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.foxyourprivacy.f0x1t.R;

/**
 * Created by Tim on 11.06.2016.
 */
public class FAQFragment extends ListFragment implements AdapterView.OnItemClickListener {
    String[] questionArray = {"Wo kann ich Wünsche oder Verbesserungsvorschläge abgeben?;Du kannst uns gerne unter foxit@psychologie.tu-darmstadt.de kontaktieren! Wir helfen gerne und sind für alle Anmerkungen dankbar!", "Warum ein Fuchs?;Füchse stehen für Intelligenz und Klugheit. Außerdem sind sie neugierig und schnüffeln viel herum. Auch wir buddeln uns durch Einstellungen und Funktionen und machen dich so mit dem Thema Datensicherheit vertraut.", "Was bringen mir die Einstellungs-und App-Listen?;Im Großen und Ganzen spiegelt sich hier deine Privatsphäre am Smartphone wider. Gut erklärt wird dies im Kurs “Erstes Tapsen”.", "Was bedeutet Privacy Paradox?;Das Privacy Paradox beschreibt den Zwiespalt zwischen dem Wunsch nach Privatsphäre und der sozialen Entfaltung sowie praktischem Nutzen.", "Was sind Trophäen?;Trophäen sind kleine Aufmerksamkeiten die du dafür erhälst, wie du die App nutzt.  Sie schalten sich automatisch frei, wenn du bestimmte Check-Points erreicht hast.", "Wieso kann ich nicht alle Lektionen sofort bearbeiten?; Falsch bearbeitete Lektionen werden für einen bestimmten Zeitraum gesperrt, damit du noch einmal in Ruhe über alle Fragen nachdenken kannst. :)", "Wie funktioniert das Punktesystem?; Für vollständig und richtig bearbeitete Lektionen bekommst du Eicheln und Pilze. Eicheln kannst du dafür verwenden, neue Animationen für den Fuchs freizuschalten. Mit Pilzen kannst du gesperrte Lektionen freischalten.", "Ich habe Probleme mit der App, was soll ich tun?; Da unsere App ein Prototyp ist und es sicherlich noch viele unentdeckte Fehler gibt, erstmal Ruhe bewaren. In diesem Fall kontaktiere uns am Besten über foxit@psychologie.tu-darmstadt.de!"};

    Context context;
    boolean[] isAnswerVisible;
    private MyListAdapter_permission adapter;

    @Override
    /**fills the fragments layout and provides behavior for the permissionHeadline
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        context = getActivity().getApplicationContext();
        View itemView = getActivity().getLayoutInflater().inflate(R.layout.fragment_faq, container, false);
        isAnswerVisible = new boolean[questionArray.length];

        return itemView;
    }


    @Override
    /** defines the listView
     * @author Tim
     */
    public void onActivityCreated(Bundle savedInstanceState) {
        //filling the listView with data
        super.onActivityCreated(savedInstanceState);
        //creates the listView
        adapter = new MyListAdapter_permission();   //new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,permissionArray);//new MyListAdapter_permission();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }


    @Override
    /**
     * on Item Click the permissionDescriptionFragment is created
     * @author Tim
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (isAnswerVisible[position]) {
            isAnswerVisible[position] = false;
            adapter.notifyDataSetChanged();
        } else {
            isAnswerVisible[position] = true;
            adapter.notifyDataSetChanged();
        }

    }


    /**
     * class to define the way the settings are displayed in the listView
     *
     * @author Tim
     */
    private class MyListAdapter_permission extends ArrayAdapter<String> {
        public MyListAdapter_permission() {
            //defining the listView's layout for single entries
            super(context, R.layout.layout_faq, questionArray);
        }

        /**
         * Here ist defined how the XML-Layout is filed  by the data stored in the array.
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
                itemView = getActivity().getLayoutInflater().inflate(R.layout.layout_faq, parent, false);
            }

            TextView question = (TextView) itemView.findViewById(R.id.text_question);
            question.setText(questionArray[position].substring(0, questionArray[position].indexOf(";")));

            TextView answer = (TextView) itemView.findViewById(R.id.text_answer);
            answer.setText(questionArray[position].substring(questionArray[position].indexOf(";") + 1, questionArray[position].length()));

            if (isAnswerVisible[position]) {
                answer.setVisibility(View.VISIBLE);
            } else {
                answer.setVisibility(View.GONE);
            }
            return itemView;
        }

    }

}






