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
public class PermissionListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    String[] permissionArray = {"Diese App benötigt keine Berechtigungen."}; //contains the permissions displayed in the listView
    Context context;
    ApplicationInfo currentApp;           //the App whose permission are on display
    static boolean firstTimeScrollhint = true;  //true=the scrollHint has to be displayed, false= the listView has been scrolled
    int appRating;


    @Override
    /**fills the fragments layout and provides behavior for the permissionHeadline
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        context = getActivity().getApplicationContext();
        setFragmentInActivity(); //IMPORTANT! informs StartScreen-activity of the existence of this fragment

        View itemView = getActivity().getLayoutInflater().inflate(R.layout.fragment_permission_list, container, false);

        //attaches the OnClickEvent for switching to the androidAppSetting
        Button settingsButton = (Button) itemView.findViewById(R.id.app_settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyApp", "onClick");
                Intent intentForSettingSwitch = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", currentApp.packageName, null));//pm.getApplicationLabel(currentApp).toString(), null));
                intentForSettingSwitch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentForSettingSwitch);
            }

        });

        //inserts the app name into the headline
        PackageManager pm = context.getPackageManager();
        TextView appName = (TextView) itemView.findViewById(R.id.text_permission_app_name);
        appName.setText(pm.getApplicationLabel(currentApp).toString());

        //setting the headline's icon
        ImageView icon = (ImageView) itemView.findViewById(R.id.image_permission_app_icon);
        try {
            icon.setImageDrawable(pm.getApplicationIcon(currentApp.packageName));

        } catch (PackageManager.NameNotFoundException e) {
            //if no icon was found
            e.printStackTrace();
        }

        //determines the permissions for the app currently looked at
        try {
            PackageInfo packageInfo = pm.getPackageInfo(currentApp.packageName, PackageManager.GET_PERMISSIONS);

            //Get Permissions
            String[] requestedPermissions = packageInfo.requestedPermissions;
            //inserts the number of permissions into the headline
            TextView permissionCount = (TextView) itemView.findViewById(R.id.text_app_permissions);

            if (requestedPermissions == null) {
                permissionCount.setText("0");
            } else {
                permissionCount.setText(Integer.toString(requestedPermissions.length));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //sets the scrollHint to visible
        if (firstTimeScrollhint) {
            if (getActivity() instanceof StartScreen) {
                ((StartScreen) getActivity()).setScrollMessageVisiblility(true);
            }
        }

        //links the onClickEvent for returning to AppListFragment to the headline
        RelativeLayout headline = (RelativeLayout) itemView.findViewById(R.id.app_headline_frame);
        switch (appRating) {
            case (0):
                headline.setBackgroundColor(Color.argb(255, 2, 174, 0));
                break;  //Green
            case (1):
                headline.setBackgroundColor(Color.argb(255, 255, 165, 0));
                break; //Orange
            case (2):
                headline.setBackgroundColor(Color.argb(255, 174, 2, 0));
                break; //Red
        }
        headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof StartScreen) {
                    ((StartScreen) getActivity()).onPermissionListHeadlinePressed();
                }
            }

        });

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
        ArrayAdapter<String> adapter = new MyListAdapter_permission();   //new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,permissionArray);//new MyListAdapter_permission();
        setListAdapter(adapter);
        //on item click an PermissionDescriptionFragment is created. (the OnItemClick() methodLeft)
        getListView().setOnItemClickListener(this);
        //on scroll the scrollHint disappears
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (getActivity() instanceof StartScreen) {
                    ((StartScreen) getActivity()).setScrollMessageVisiblility(false);
                    firstTimeScrollhint = false;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }


    @Override
    /**
     * if the fragment is closed the scrollHint disappears
     */
    public void onStop() {
        super.onStop();
        if (getActivity() instanceof StartScreen) {
            ((StartScreen) getActivity()).setScrollMessageVisiblility(false);
        }

    }

    @Override
    /**
     * on Item Click the permissionDescriptionFragment is created
     * @author Tim
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String permissionName= permissionArray[position];
        if(!(permissionName.equals("Gefährliche Berechtigungen:")||permissionName.equals("Normale Berechtigungen:")||permissionName.equals("Harmlose Berechtigungen:")||permissionName.equals("Andere Berechtigungen:")||permissionName.equals("Diese App benötigt keine Berechtigungen."))){
        //Fragment is created
        PermissionDescriptionFragment fragment = new PermissionDescriptionFragment();
        Bundle permissionNameBundle = new Bundle();
        permissionNameBundle.putString("permissionName", permissionArray[position]);
        permissionNameBundle.putInt("appRating", appRating);
        fragment.setArguments(permissionNameBundle);

        //add fragment so the activitys' context
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = childFragmentManager.beginTransaction();

        transaction.add(R.id.permission_frame, fragment, "permissionDescription");
        transaction = transaction.addToBackStack("permissionDescription");
        transaction.commit();
        RelativeLayout settingFrame = (RelativeLayout) getActivity().findViewById(R.id.list_frame);
        settingFrame.setVisibility(View.GONE);
        if (getActivity() instanceof StartScreen) {
            ((StartScreen) getActivity()).setScrollMessageVisiblility(false);
        }
        firstTimeScrollhint = false;}
    }

    @Override
    /**methodLeft to set the fragment's arguments from the outside
     * @author Tim
     * @param arg Bundle holding an ArrayList calles "permissions" holding an app's permissions
     *
     */
    public void setArguments(Bundle arg) {
        ArrayList<String> permissions = arg.getStringArrayList("permissions");
        permissionArray = permissions.toArray(new String[permissions.size()]);
        appRating = arg.getInt("appRating");
    }


    /**
     * class to define the way the settings are displayed in the listView
     *
     * @author Tim
     */
    private class MyListAdapter_permission extends ArrayAdapter<String> {
        public MyListAdapter_permission() {
            //defining the listView's layout for single entries
            super(context, R.layout.layout_permission, permissionArray);
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
                itemView = getActivity().getLayoutInflater().inflate(R.layout.layout_permission, parent, false);
            }

            //setting the text for the permission's name
            TextView permissionName = (TextView) itemView.findViewById(R.id.text_permission_name);
            // permissionName.setText(permissionArray[position]);
            if (!(permissionArray[position].equals("Dangerous:") || permissionArray[position].equals("Normal:") || permissionArray[position].equals("Harmless:"))) {
                //permissionName.setText(permissionArray[position].substring(permissionArray[position].indexOf(".")+1,permissionArray[position].length()));
                String[] array = permissionArray[position].split("\\.");
                if (array.length == 3) {
                    permissionName.setText(array[2].replace("_", " "));

                } else {
                    permissionName.setText(permissionArray[position]);
                }
            } else {
                permissionName.setText(permissionArray[position]);
            }


            //sets the permissionIcon
            Drawable permissionIcon = getPermissionDrawable(permissionArray[position]);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.image_app_icon);
            imageView.setImageDrawable(permissionIcon);

            return itemView;
        }

        @Nullable
        /**
         * determines the icon for a single permission
         * also defined in PermissionDescriptionFragment, probably bad
         * @author Tim
         * @param permission the permission whose icon is to be fetched
         */
        private Drawable getPermissionDrawable(String permission) {
            PackageManager mPackageManager = context.getPackageManager();
            Drawable drawable = null; //the icon
            //try to get an icon for the permission
            try {
                PermissionInfo permissionInfo = mPackageManager.getPermissionInfo(permission, 0);
                //icons are only defined for a group of permissions.
                PermissionGroupInfo groupInfo = mPackageManager.getPermissionGroupInfo(permissionInfo.group, 0);
                drawable = ContextCompat.getDrawable(context, groupInfo.icon);
            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
            //if there is no icon do nothing

            }

            return drawable;
        }
    }

    /**
     * used in AppListFragment to tell this Fragment which app it is describing
     *  @author Tim
     * @param app
     */
    public void setCurrentApp(ApplicationInfo app) {
        currentApp = app;
    }


    /**
     * informs the startScreenActivity that this fragment is created
     * (used for removing PermissionDescriptionFragment without errors)
     * @author Tim
     */
    private void setFragmentInActivity() {
        if (getActivity() instanceof StartScreen) {
            StartScreen startScreen = (StartScreen) getActivity();
            startScreen.permissionList = this;
        }
    }

    /**
     * setts the Permission's listView visible, called when permissionDescriptionFragment is destroid
     * otherwise permissionList would stay invisible
     * @author Tim
     */
    public void setListVisible() {
        RelativeLayout settingFrame = (RelativeLayout) getActivity().findViewById(R.id.list_frame);
        settingFrame.setVisibility(View.VISIBLE);
    }


}






