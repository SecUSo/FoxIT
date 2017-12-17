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
 * Fragment to view the trophy images in big, when they are achieved and clicked on
 * Created by Tim on 25.06.2016.
 */
public class BigTrophyViewFragment extends Fragment {
    private String trophyName; //the name of the described trophy
    private String trophyDescription;
    private int icon;



    /**
     * setts the name and image of the trophy view
     *
     * @param inflater
     * @param container
     * @param saveInstanceState
     * @return
     * @author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_big_trophy_view, container, false);
        TextView trophyName = view.findViewById(R.id.text_trophy_name);
        trophyName.setText(this.trophyName);

        TextView trophyText = view.findViewById(R.id.text_trophy_description);
        trophyText.setText(trophyDescription);

        ImageView trophyIcon = view.findViewById(R.id.image_acorn_symbol);
        trophyIcon.setImageResource(icon);

        return view;
    }

    /**
     * Enables to pass arguments to the fragment
     * @author Tim
     */
    @Override
    public void setArguments(Bundle arg) {
        trophyName = arg.getString("trophyName");
        icon = arg.getInt("icon");
        trophyDescription = arg.getString("trophyDescription");
    }

}
