package com.bp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ich on 25.06.2016.
 */
public class ButtonSlide extends Slide {
    View view;
    Method method; //instance of Method to define which method is called at Button press

    /**
     *
     * @author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view=inflater.inflate(R.layout.layout_slide_button,container,false);
        fillLayout();
        //attaching the method at the button
        Button button = (Button) view.findViewById(R.id.button_left);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                method.method(parameter.get("methodParameter"));
            }});
        return view;
    }

    /**
     * @author
     */
    @Override
    void fillLayout() {
        TextView text=(TextView) view.findViewById(R.id.text);
        text.setText(parameter.get("text"));

        Button button =(Button) view.findViewById((R.id.button_left));
        button.setText(parameter.get("buttonText"));

        MethodFactory m= new MethodFactory(getActivity());
        method=m.createMethod(parameter.get("method"));
    }


}
