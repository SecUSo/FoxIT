package com.bp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Tim on 25.06.2016.
 */
public class AnalysisRequestFragment extends Fragment {
    View view; //the fragments view, useful for usages outside of onCreateView

    Fragment thisFragment; //this fragment useful to be called in another callClassMethod

    /**
     * @author Tim
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    /**
     * defines the layout of this fragment and provides the yes and no button behavior
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_analysis_request, container, false);
        thisFragment = this;

        LinearLayout button = (LinearLayout) view.findViewById(R.id.whole_frame);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {getActivity().onBackPressed();}
        });
        //add the yes button's behavior
        Button yesButton = (Button) view.findViewById(R.id.button_yes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity().getApplicationContext(),Analysis.class);
                startActivity(i);
            }
        });
        //remove this fragment if the no button is pressed
        Button noButton = (Button) view.findViewById(R.id.button_no);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().remove(thisFragment).commit();
            }

        });

        return view;
    }




}
