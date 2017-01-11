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
                        AnimationTale tail = new AnimationTale();
                        startAnimation(R.id.tale, tail, "animationTale");

                        //TODO für alle cases, entweder hardgecoded alle anderen Views auf Gone setzen jeweils oder vielleicht ist das auch garnicht nötig, wenn das visible setzen es schon in den vordergrund holt (eventuell einfach in den Vordergrund holen); oder halt eine Logik die alle anderen Views gone setzt.

                        getActivity().findViewById(R.id.head).setVisibility(View.GONE);
                        getActivity().findViewById(R.id.hide).setVisibility(View.GONE);
//                        button.setText("Hide2");
                        break;
                    case "PLAY":
                        button.setText("Play2");
                        //AnimationPlay play = new AnimationPlay();
                        //startAnimation(R.id.play,play,"animationPlay");
                        //TODO siehe oben
                        break;
                    case "LAUGH":
                        button.setText("LAUGH2");
                        //AnimationLaugh laugh = new AnimationLaugh();
                        //startAnimation(R.id.laugh,laugh,"animationLaugh");
                        //TODO siehe oben
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

    void startAnimation(int id, Fragment fragment, String name) {
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(id, fragment, name);
        transaction.commit();
        getActivity().findViewById(id).setVisibility(View.VISIBLE);
    }



}
