package com.bp;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Tim on 25.06.2016.
 */
public class AcornCountFragment extends Fragment {
    View view; //the fragments view, useful for usages outside of onCreateView
    int acornCount; //amount of acorn currently on display

    /**
     *@author Tim
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //fetch the current acornCount from the ObserverSingleton
        acornCount=ObserverSingleton.getInstance().getAcornCount();
        //and display it
        TextView text=(TextView) view.findViewById(R.id.text_acorn_count);
                text.setText(Integer.toString(acornCount));
    }

    @Override
    /**
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view=inflater.inflate(R.layout.fragment_acorn_count,container,false);
        return view;
    }

    /**
     * Method for changing the displayed acornCount from the outside
     * Called in MethodChangeAcornCount
     * @author Tim
     * @param newText the new text resembling the new acornCount
     */
    public void changeText(String newText){
        TextView text=(TextView) view.findViewById(R.id.text_acorn_count);
        text.setText(newText);
        text.setTextColor(Color.BLACK);

    }


}
