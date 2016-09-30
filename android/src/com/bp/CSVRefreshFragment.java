package com.bp;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Ich on 25.06.2016.
 */
public class CSVRefreshFragment extends Fragment{


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view=inflater.inflate(R.layout.fragment_csv_refresh,container,false);
        Button classButton = (Button) view.findViewById(R.id.button_class_csv);
        refreshClassButton =classButton;
        classButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler =new Handler();
                refreshClassButton.setBackgroundColor(Color.GREEN);
                DBHandler dbHandler = new DBHandler(getActivity(),null,null,1);
                dbHandler.updateLessions(((SettingsActivity)getActivity()).readCSV(R.raw.lektionen,getActivity()));
                dbHandler.updateClasses(((SettingsActivity)getActivity()).readCSV(R.raw.classes,getActivity()));
                dbHandler.close();

            }
        });

        Button descriptionButton = (Button) view.findViewById(R.id.button_description_csv);
        refreshDescriptionButton =descriptionButton;
        descriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler =new Handler();
                refreshDescriptionButton.setBackgroundColor(Color.GREEN);
                DBHandler dbHandler = new DBHandler(getActivity(),null,null,1);
                dbHandler.updateLessions(((SettingsActivity)getActivity()).readCSV(R.raw.permissions,getActivity()));
                dbHandler.updateClasses(((SettingsActivity)getActivity()).readCSV(R.raw.settings,getActivity()));
                dbHandler.close();
            }
        });


        return view;
    }

    @Override
    /**
     * Enables to pass arguments to the fragment
     * @author Tim
     */
    public void setArguments(Bundle arg){

    }

}
