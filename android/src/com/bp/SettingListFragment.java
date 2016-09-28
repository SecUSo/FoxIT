package com.bp;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * presents and structures the user device settings
 */
public class SettingListFragment extends ListFragment implements AdapterView.OnItemClickListener{

    /*
    an array holding all of the displayed settings
     */
    String[] settingsArray;

    /*
    context of current activity
     */
    Context context;

    /**
     * shows a setting list
     *
     * @param inflater
     * @param container
     * @param saveInstanceState
     * @return list view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        return inflater.inflate(R.layout.fragment_setting_list, container, false);
    }

    /**
     * defines the specifications of the fragment list view
     *
     * @param savedInstanceState
     * @author Tim
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context= getActivity().getApplicationContext();
        //creates the listView
        ArrayAdapter<String> adapter = new MyListAdapter_settings();
        setListAdapter(adapter);

        //adds the onClickAction defined by onItemClick()
        getListView().setOnItemClickListener(this);
        // on scrolling down the scrollHint disappears
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (getActivity() instanceof StartScreen) {
                    ((StartScreen) getActivity()).setScrollMessageVisiblility(false);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }


    /**methodLeft to set the fragment's arguments from the outside
     * @author Tim
     * @param arg Bundle holding an ArrayList calles "permissions" holding an app's permissions
*/
    @Override
    public void setArguments(Bundle arg){
        ArrayList<String> settings= arg.getStringArrayList("settings");
        settingsArray=settings.toArray(new String[settings.size()]);
        Arrays.sort(settingsArray);
        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Bundle settingsBundle = new Bundle();                  //Bundle to pass arguments to the Fragment
        SettingValueFragment fragment =new SettingValueFragment();
        final PackageManager pm = context.getPackageManager();
                settingsBundle.putString("settingName",settingsArray[position].substring(0,settingsArray[position].indexOf(";")));
                settingsBundle.putString("settingValue",settingsArray[position].substring(settingsArray[position].indexOf(";") + 1, settingsArray[position].length()));
                fragment.setArguments(settingsBundle);


        //add fragment so the activitys' context
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.permissionFrame,fragment,"permission");
        transaction=transaction.addToBackStack("permission");
        transaction.commit();

        //make appListFragment and SettingsListFragment invisible after permissionListFragment is created
        FrameLayout settingFrame= (FrameLayout) getActivity().findViewById(R.id.settingFrame);
        FrameLayout appFrame= (FrameLayout) getActivity().findViewById(R.id.appFrame);
        settingFrame.setVisibility(View.GONE);
        appFrame.setVisibility(View.GONE);
        StartScreen a=(StartScreen) getActivity();
        a.mViewPager.setVisibility(View.GONE);

    }


    /**
     * class to define the way the settings are displayed in the listView
     *
     * @author Tim
     */
    private class MyListAdapter_settings extends ArrayAdapter<String> {

        /**
         * defines what layout the listView uses for a single entry
         *
         * @author Tim
         */
        public MyListAdapter_settings() {
            super(context, R.layout.layout_settings, settingsArray);
        }

        /**
         * defines how the data from the array is inserted into XML-layout
         *
         * @param position    position in the array used
         * @param convertView
         * @param parent
         * @return itemView
         * @author Tim
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //convertView has to be filled with layout_app if null
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.layout_settings, parent, false);
            }

            //set the text for setting type
            TextView settingType = (TextView) itemView.findViewById(R.id.text_setting_type);
            settingType.setText(settingsArray[position].substring(0, settingsArray[position].indexOf(";")) + ":");

            //set the text for the setting's setting
            TextView settingSetting = (TextView) itemView.findViewById(R.id.text_setting_setting);
            String value = settingsArray[position].substring(settingsArray[position].indexOf(";") + 1, settingsArray[position].length());
            if (value.equals("0")) value = "OFF";
            if (value.equals("1")) value = "ON";
            settingSetting.setText(value);
            return itemView;
        }
    }


}

