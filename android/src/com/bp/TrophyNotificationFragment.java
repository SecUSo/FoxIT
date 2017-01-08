package com.bp;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
                text.setText(name+" freigeschaltet!");

        ImageView trophyIcon=(ImageView) view.findViewById(R.id.image_trophy_symbol);
        Drawable image;
        switch (name){
            case "Baumhaus Kapitalist": image =ContextCompat.getDrawable(getActivity(),R.mipmap.acorn_finish);
            case "Schnüffler": image =ContextCompat.getDrawable(getActivity(),R.mipmap.fox_finish);
            case "Frischling":  image =ContextCompat.getDrawable(getActivity(),R.mipmap.boar_finish);
            case "Halbzeit":  image =ContextCompat.getDrawable(getActivity(),R.mipmap.clock_finish);
            case "Privacy Shield":  image =ContextCompat.getDrawable(getActivity(),R.mipmap.shield_finish);
            case "Nachteule":  image =ContextCompat.getDrawable(getActivity(),R.mipmap.owl_finish);
            case "Early Bird":  image =ContextCompat.getDrawable(getActivity(),R.mipmap.bird_finish);
            case "Power User":  image =ContextCompat.getDrawable(getActivity(),R.mipmap.rocket_finish);
            default: image= ContextCompat.getDrawable(getActivity(),R.mipmap.stern_voll2);
        }

        trophyIcon.setImageDrawable(image);

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
