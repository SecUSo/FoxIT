package com.bp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tim on 11.06.2016.
 */
public class FAQFragment extends ListFragment implements AdapterView.OnItemClickListener {
    String[] questionArray = {"Wo kann ich Wünsche oder Verbesserungsvorschläge abgeben?;Du kannst uns gerne unter foxit@tu-darmstadt.de kontaktieren! Wir helfen gerne und sind für alle Anmerkungen dankbar!","Warum ein Fuchs?;Füchse stehen für Intelligenz und Klugheit. Außerdem sind sie neugierig und schnüffeln viel herum. Genau das tun wir auch! Wir schnüffeln in deiner Privatsphäre herum und helfen dir dabei, deine Daten intelligent auszuwerten und aufzubereiten.","Was bringen mir die Einstellungs-und App-Listen?;Im Großen und Ganzen spiegelt sich hier deine Privatsphäre am Smartphone wider. Am Besten schaust du mal in dem Kurs “Rumgeschnüffelt bei dir” vorbei!","Was bedeutet Privacy Paradox?;Das Privacy Paradox beschreibt den Zwiespalt zwischen dem Wunsch nach Privatsphäre und sozialer Entfaltung und Kontakten."};

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
        isAnswerVisible=new boolean[questionArray.length];

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

        if(isAnswerVisible[position]){
            isAnswerVisible[position]=false;
            adapter.notifyDataSetChanged();
        }else{
            isAnswerVisible[position]=true;
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

            TextView question=(TextView) itemView.findViewById(R.id.text_question);
            question.setText(questionArray[position].substring(0, questionArray[position].indexOf(";")));

            TextView answer=(TextView) itemView.findViewById(R.id.text_answer);
            answer.setText(questionArray[position].substring(questionArray[position].indexOf(";") + 1, questionArray[position].length()));

            if(isAnswerVisible[position]){
                answer.setVisibility(View.VISIBLE);
            }else{
                answer.setVisibility(View.GONE);
            }
            return itemView;
        }

}

}






