package com.foxyourprivacy.f0x1t.Slides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foxyourprivacy.f0x1t.LectionMethods.Method;
import com.foxyourprivacy.f0x1t.LectionMethods.MethodFactory;
import com.foxyourprivacy.f0x1t.R;

/**
 * Created by Ich on 25.06.2016.
 */
public class ButtonSlide extends Slide {
    View view;
    Method method; //instance of Method to define which callClassMethod is called at Button press

    /**
     * fills the layout by calling fillLayout and defines the buttons behavior
     *
     * @author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.layout_slide_button, container, false);
        fillLayout();
        //attaching the callClassMethod at the button
        Button button = (Button) view.findViewById(R.id.button_left);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                method.callClassMethod(parameter.get("methodParameter"));
            }
        });
        return view;
    }

    /**
     * fills the layout with the specified content
     *
     * @author
     */
    @Override
    public void fillLayout() {
        //set the displayed text
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(parameter.get("text"));
        //set the text displayed on the button
        Button button = (Button) view.findViewById((R.id.button_left));
        button.setText(parameter.get("buttonText"));
        //fetch the designated callClassMethod
        MethodFactory m = new MethodFactory(getActivity());
        method = m.createMethod(parameter.get("method"));
    }


}
