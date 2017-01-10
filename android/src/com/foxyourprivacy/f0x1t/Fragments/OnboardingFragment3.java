package com.foxyourprivacy.f0x1t.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxyourprivacy.f0x1t.R;

/**
 * Created by Ich on 25.06.2016.
 */
public class OnboardingFragment3 extends Fragment {
    String onboardingText; //the permission described by the fragment
    int icon;

    @Override
    /**
     * @author Hannah
     */
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    /**
     * @author Hannah
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding3, container, false);

        return view;
    }

}
