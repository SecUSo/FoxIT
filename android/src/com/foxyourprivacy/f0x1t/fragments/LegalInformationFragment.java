package com.foxyourprivacy.f0x1t.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxyourprivacy.f0x1t.R;

/**
 * This fragment shows the legal information to the user and is anchored in the settings
 * Created by Tim on 25.06.2016.
 */
public class LegalInformationFragment extends Fragment {



    /**fills the layout with the permission name and description
     * @author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_legal_information, container, false);
    }

    /**
     * Enables to pass arguments to the fragment
     * @author Tim
     */
    @Override
    public void setArguments(Bundle arg) {

    }

}
