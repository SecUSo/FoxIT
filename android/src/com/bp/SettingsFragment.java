package com.bp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Hannah on 24.09.2016.
 */
public class SettingsFragment extends ListFragment implements AdapterView.OnItemClickListener {
    String settingsText; //the setting described by the fragment
    int icon;
    View view;
    String theAnalysisEntry="Analyse wiederholen";
    Context context;
    String[] profileListItems = {
            "Analyse wiederholen",
            "Hilfe",
            "Impressum",
            "Debugging",
            "Rechtliche Informationen"
    };
    HashMap<String, Fragment> fragmentList = new HashMap<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        fragmentList.put("Persönliche Daten", new ProfilFragment());
        fragmentList.put("Analyse wiederholen", new LegalInformationFragment());
        fragmentList.put("Hilfe", new FAQFragment());
        fragmentList.put("Impressum", new LegalInformationFragment());
        fragmentList.put("Debugging", new CSVRefreshFragment());
        fragmentList.put("Rechtliche Informationen",new LegalInformationFragment_libGDX());

        context=getActivity().getApplicationContext();
        List<String> profileList = new ArrayList<>(Arrays.asList(profileListItems));



        view = inflater.inflate(R.layout.fragment_settings, container, false);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(profileListItems[position].equals(theAnalysisEntry)){
            //add the TradeRequestFragment to the activity's context
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            //AnalysisRequestFragment tradeRequest = new AnalysisRequestFragment();
            AnalysisRequestFragment analysisRequest=new AnalysisRequestFragment();
            //add the fragment to the count_frame RelativeLayout
            transaction.add(R.id.request_frame, analysisRequest, "count");
            transaction.addToBackStack("analysisRequest");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }else{
        Fragment newPage = fragmentList.get(profileListItems[position]);
        if (newPage != null) {

            //add fragment to the activitys' context
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.add(R.id.second_fragment_frame, newPage, "setting");
            transaction = transaction.addToBackStack("setting");
            transaction.commit();

            RelativeLayout firstFragment = (RelativeLayout) getActivity().findViewById(R.id.first_fragment_frame);
            firstFragment.setVisibility(View.GONE);
        }
        }

    }


    @Override
    /** defines the listView
     * @author Tim
     */
    public void onActivityCreated(Bundle savedInstanceState) {
        //filling the listView with data
        super.onActivityCreated(savedInstanceState);
        //creates the listView
        ArrayAdapter<String> adapter = new SettingsFragment.MyListAdapter_permission();   //new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,permissionArray);//new MyListAdapter_permission();
        setListAdapter(adapter);
        //on item click an PermissionDescriptionFragment is created. (the OnItemClick() methodLeft)
        getListView().setOnItemClickListener(this);

    }

    /**
     * @Override /**
     * Enables to pass arguments to the fragment
     * @author Tim
     * <p>
     * public void setArguments(Bundle arg){
     * <p>
     * onboardingText=arg.getString("onboardingText");
     * icon=arg.getInt("icon");
     * }
     */


    /**
     * class to define the way the settings are displayed in the listView
     *
     * @author Tim
     */
    private class MyListAdapter_permission extends ArrayAdapter<String> {
    public MyListAdapter_permission() {
        //defining the listView's layout for single entries
        super(context, R.layout.list_item_profile, profileListItems);
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
            itemView = getActivity().getLayoutInflater().inflate(R.layout.list_item_profile, parent, false);
        }


        TextView fragmentName= (TextView) itemView.findViewById(R.id.text_fragment_name);
        fragmentName.setText(profileListItems[position]);


        return itemView;
    }


}

}
