package com.bp;
import android.app.Fragment;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ich on 25.06.2016.
 */
public class TrophyNotificationFragment extends Fragment{
    View view; //the fragments view, useful for usages outside of onCreateView
    String name="default";
    /**
     *@author Tim
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //and display it
        TextView text=(TextView) view.findViewById(R.id.text_trophy_name);
                text.setText(name);

        ImageView trophyIcon=(ImageView) view.findViewById(R.id.image_trophy_symbol);
        switch (name){
            case "Neuling": trophyIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.mipmap.goodapprating));

        }
    }

    @Override
    /**
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view=inflater.inflate(R.layout.fragment_trophy_notification,container,false);
        return view;
    }

    /**
     * tells the fragment the information needed for this trade, called whenever this fragment gets created
     * @author Tim
     * @param arg
     */
    public void setArguments(Bundle arg) {
        name = arg.getString("name");

    }



}
