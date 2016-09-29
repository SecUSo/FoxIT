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

/**
 * Created by Ich on 25.06.2016.
 */
public class LegalInformationFragment extends Fragment{

    @Override
    /**
     * @author Tim
     */
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    /**fills the layout with the permission name and description
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view=inflater.inflate(R.layout.fragment_legal_information,container,false);



        return view;
    }

    @Override
    /**
     * Enables to pass arguments to the fragment
     * @author Tim
     */
    public void setArguments(Bundle arg){

    }

}
