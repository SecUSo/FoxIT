package com.foxyourprivacy.f0x1t.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foxyourprivacy.f0x1t.R;

/**
 * Created by Ich on 25.06.2016.
 */
public class TrophyNotificationFragment extends Fragment {
    View view; //the fragments view, useful for usages outside of onCreateView
    String name = "default";

    /**
     * @author Tim
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //and display it
        TextView text = (TextView) view.findViewById(R.id.text_trophy_name);
        text.setText(name + " freigeschaltet!");

        ImageView trophyIcon = (ImageView) view.findViewById(R.id.image_trophy_symbol);
        Drawable image;
        switch (name) {
            case "Baumhaus Kapitalist":
                image = ContextCompat.getDrawable(getActivity(), R.mipmap.acorn_finish);
                break;
            case "Schnüffler":
                image = ContextCompat.getDrawable(getActivity(), R.mipmap.fox_finish);
                break;

            case "Frischling":
                image = ContextCompat.getDrawable(getActivity(), R.mipmap.boar_finish);
                break;

            case "Halbzeit":
                image = ContextCompat.getDrawable(getActivity(), R.mipmap.clock_finish);
                break;

            case "Privacy Shield":
                image = ContextCompat.getDrawable(getActivity(), R.mipmap.shield_finish);
                break;

            case "Nachteule":
                image = ContextCompat.getDrawable(getActivity(), R.mipmap.owl_finish);
                break;

            case "Early Bird":
                image = ContextCompat.getDrawable(getActivity(), R.mipmap.bird_finish);
                break;

            case "Power User":
                image = ContextCompat.getDrawable(getActivity(), R.mipmap.rocket_finish);
                break;

            default:
                image = null;
                break;

        }

        trophyIcon.setImageDrawable(image);

    }

    @Override
    /**
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_trophy_notification, container, false);
        return view;
    }

    /**
     * tells the fragment the information needed for this trade, called whenever this fragment gets created
     *
     * @param arg
     * @author Tim
     */
    public void setArguments(Bundle arg) {
        name = arg.getString("name");

    }


}
