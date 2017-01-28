package com.foxyourprivacy.f0x1t.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foxyourprivacy.f0x1t.R;

/**
 * Created by Ich on 25.06.2016.
 */
public class BigTrophyViewFragment extends Fragment {
    String trophyName; //the name of the described trophy
    String trophyDescription;
    int icon;

    /**
     * @param savedInstanceState
     * @author Tim
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * setts the name and image of the trophy view
     *
     * @param inflater
     * @param container
     * @param saveInstanceState
     * @return
     * @autor Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_big_trophy_view, container, false);
        TextView trophyName = (TextView) view.findViewById(R.id.text_trophy_name);
        trophyName.setText(this.trophyName);

        TextView trophyText = (TextView) view.findViewById(R.id.text_trophy_describtion);
        trophyText.setText(trophyDescription);

        ImageView trophyIcon = (ImageView) view.findViewById(R.id.image_acorn_symbol);
        trophyIcon.setImageResource(icon);

        return view;
    }

    @Override
    /**
     * Enables to pass arguments to the fragment
     * @author Tim
     */
    public void setArguments(Bundle arg) {
        trophyName = arg.getString("trophyName");
        icon = arg.getInt("icon");
        trophyDescription = arg.getString("trophyDescribtion");
    }

}
