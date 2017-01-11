package com.foxyourprivacy.f0x1t.Animation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.foxyourprivacy.f0x1t.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonFragment extends Fragment {
    Button button;
    String label;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animation_page,container, false);
        button = (Button) view.findViewById(R.id.button_animation);
        label = getArguments().getString("button");
        button.setText(label);

        // BUTTON ACTION!!!
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (label) {
                    case "HIDE":
                        getActivity().findViewById(R.id.tale).setVisibility(View.VISIBLE);
                        getActivity().findViewById(R.id.head).setVisibility(View.GONE);
                        getActivity().findViewById(R.id.hide).setVisibility(View.GONE);
//                        button.setText("Hide2");
                        break;
                    case "PLAY":
                        button.setText("Play2");
                        break;
                    case "LAUGH":
                        button.setText("LAUGH2");
                        break;
                    case "Activity4":
                        //...
                        break;
                    case "Activity5":
                        //...
                        break;
                    default:
                        //...
                }
            }
        });
        return view;
    }



}
