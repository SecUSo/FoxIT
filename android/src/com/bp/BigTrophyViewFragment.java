package com.bp;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Ich on 25.06.2016.
 */
public class BigTrophyViewFragment extends Fragment{
    String trophyName; //the name of the described trophy
    String trophyDescription;
    int icon;
    /**
     * @author Tim
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**setts the name and image of the trophy view
     * @autor Tim
     * @param inflater
     * @param container
     * @param saveInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view=inflater.inflate(R.layout.fragment_big_trophy_view,container,false);
        TextView trophyName= (TextView) view.findViewById(R.id.text_trophy_name);
        trophyName.setText(this.trophyName);

        TextView trophyText=(TextView) view.findViewById(R.id.text_trophy_describtion);
        trophyText.setText(trophyDescription);

        ImageView trophyIcon=(ImageView) view.findViewById(R.id.image_acorn_symbol);
        trophyIcon.setImageResource(icon);

        return view;
    }

    @Override
    /**
     * Enables to pass arguments to the fragment
     * @author Tim
     */
    public void setArguments(Bundle arg){
        trophyName=arg.getString("trophyName");
        icon=arg.getInt("icon");
        trophyDescription=arg.getString("trophyDescribtion");
    }

}
