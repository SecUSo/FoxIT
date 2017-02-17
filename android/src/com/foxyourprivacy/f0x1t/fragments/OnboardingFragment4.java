package com.foxyourprivacy.f0x1t.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.activities.Analysis;
import com.foxyourprivacy.f0x1t.activities.FoxITActivity;

/**
 * Created by Ich on 25.06.2016.
 */
public class OnboardingFragment4 extends Fragment {

    Button analysisButton;

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

        View view = inflater.inflate(R.layout.fragment_onboarding4, container, false);
        Button button = (Button) view.findViewById(R.id.button_accept);
        analysisButton = button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analysisButton.setText(null);
                analysisButton.setVisibility(View.GONE);
                ((FoxITActivity) getActivity()).setTrophyUnlocked("Schnüffler");
                Intent i = new Intent(getActivity(), Analysis.class);
                startActivity(i);
            }
        });

        return view;
    }


}
