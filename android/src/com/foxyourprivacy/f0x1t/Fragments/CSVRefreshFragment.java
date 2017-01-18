package com.foxyourprivacy.f0x1t.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.foxyourprivacy.f0x1t.Activities.SettingsActivity;
import com.foxyourprivacy.f0x1t.R;

/**
 * Created by Ich on 25.06.2016.
 */
public class CSVRefreshFragment extends Fragment {


    Button refreshClassButton;
    Button refreshDescriptionButton;

    @Override

    /**
     * @author Tim
     */
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    /**fills the layout with the permission name and description
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_csv_refresh, container, false);
        Button classButton = (Button) view.findViewById(R.id.button_class_csv);
        refreshClassButton = classButton;
        classButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshClassButton.setBackgroundColor(Color.GREEN);
                ((SettingsActivity) getActivity()).updateLessions(getActivity(), (ConnectivityManager) getActivity().getSystemService(android.app.Activity.CONNECTIVITY_SERVICE));
                /*
                DBHandler dbHandler = new DBHandler(getActivity(),null,null,1);
                dbHandler.updateLessons(((SettingsActivity)getActivity()).readCSV(R.raw.lektionen,getActivity()));
                dbHandler.updateClasses(((SettingsActivity)getActivity()).readCSV(R.raw.classes,getActivity()));
                dbHandler.close();
                */

            }
        });

        Button descriptionButton = (Button) view.findViewById(R.id.button_description_csv);
        refreshDescriptionButton = descriptionButton;
        descriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshDescriptionButton.setBackgroundColor(Color.GREEN);
                ((SettingsActivity) getActivity()).updatePermissions(getActivity(), (ConnectivityManager) getActivity().getSystemService(android.app.Activity.CONNECTIVITY_SERVICE));
                ((SettingsActivity) getActivity()).updateSettings(getActivity(), (ConnectivityManager) getActivity().getSystemService(android.app.Activity.CONNECTIVITY_SERVICE));

                /*
                DBHandler dbHandler = new DBHandler(getActivity(),null,null,1);
                dbHandler.updatePermissions(((SettingsActivity)getActivity()).readCSV(R.raw.permissions,getActivity()));
                dbHandler.close();
                */
            }
        });

        Button acornAndTokenButton = (Button) view.findViewById(R.id.button_exportDB);
        acornAndTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SettingsActivity) getActivity()).exportDB();
                //  MethodFactory m=new MethodFactory(getActivity());
                //  Method raiseAcornCount= m.createMethod("changeAcornCount");
                //  raiseAcornCount.callClassMethod("100");
                //  Method raiseTokenCount =m.createMethod("changeTokenCount");
                // raiseTokenCount.callClassMethod("100");
            }
        });


        return view;
    }

    @Override
    /**
     * Enables to pass arguments to the fragment
     * @author Tim
     */
    public void setArguments(Bundle arg) {

    }

}
