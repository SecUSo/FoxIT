package com.bp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ich on 25.06.2016.
 */
public class TextSlide extends Slide {
    View view;

    /*
     *@author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view=inflater.inflate(R.layout.layout_slide_text,container,false);
        fillLayout();
        return view;
    }


    /**
     * @author Tim
     */
    @Override
    void fillLayout() {
        TextView text=(TextView) view.findViewById(R.id.text);
        text.setText(parameter.get("text"));
        Log.d("MyApp",parameter.toString());
    }


}