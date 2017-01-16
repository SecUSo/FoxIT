package com.foxyourprivacy.f0x1t.Animation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.ValueKeeper;

import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonFragment extends Fragment {
    Button button;
    String label;

    // UNLOCKING
    HashMap<String, Boolean> unlockedAnimations = ValueKeeper.getInstance().animationList;

    public boolean isUnlocked(String anim){
        return true;
//        boolean unlocked;
//        for (String s : unlockedAnimations.keySet()) {
//            unlocked = unlockedAnimations.get(s);
//            if (s.equals(anim) && unlocked) return true;
//        }
//        return false;
    }


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
                    case "Wedel mal!":
                        if (isUnlocked("")) {
                            AnimationTail tail = new AnimationTail();
                            startAnimation(R.id.tale, tail, "animationTale");
                        }
                        break;
                    case "Kick den Ball!":
                        if (isUnlocked("")) {
                            AnimationPlay play = new AnimationPlay();
                            startAnimation(R.id.play, play, "animationPlay");
                        }
                        break;
                    case "Sitz!":
                        if (isUnlocked("")) {
                            AnimationSit sit = new AnimationSit();
                            startAnimation(R.id.sit, sit, "animationSit");
                        }
                        break;
                    case "Versteck dich!":
                        if (isUnlocked("")) {
                            AnimationVanish vanish = new AnimationVanish();
                            startAnimation(R.id.vanish, vanish, "animationVanish");
                        }
                        break;
                    case "Flieg!":
                        if (isUnlocked("")) {
                            AnimationFly fly = new AnimationFly();
                            startAnimation(R.id.fly, fly, "animationFly");
                        }
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
        List<Fragment> all = getFragmentManager().getFragments();
        String tag;
        Log.d("df", all.toString());
        for (Fragment frag : all) {
            if (frag != null) {
                tag = frag.getTag();
                if (tag.equals("animationSit") || tag.equals("animationPlay") || tag.equals("animationTale")
                || tag.equals("animationVanish") || tag.equals("animationFly")) {
                    transaction.remove(frag);
                }
            }
        }
        transaction.add(id, fragment, name);
        transaction.commit();
        getActivity().findViewById(id).setVisibility(View.VISIBLE);
    }



}
