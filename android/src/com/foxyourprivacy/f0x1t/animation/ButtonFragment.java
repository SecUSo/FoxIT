package com.foxyourprivacy.f0x1t.animation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.ValueKeeper;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonFragment extends Fragment {
    private String label;
    private ValueKeeper valueKeeper;
//
//    // UNLOCKING
//    HashMap<String, Boolean> unlockedAnimations = ValueKeeper.getInstance().animationList;
//
//    public boolean isUnlocked(String anim){
//        boolean unlocked;
//        for (String s : unlockedAnimations.keySet()) {
//            unlocked = unlockedAnimations.get(s);
//            if (s.equals(anim) && unlocked) return true;
//        }
//        return false;
//    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        valueKeeper=ValueKeeper.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animation_page,container, false);
        Button button = view.findViewById(R.id.button_animation);
        label = getArguments().getString("button");
        button.setText(label);

        // BUTTON ACTION!!!
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (label) {
                    case "fuchsteufelsfröhlich":
                            AnimationTail tail = new AnimationTail();
                        startAnimation(R.id.tail, tail, "animationTale");
                        break;
                    case "klicken zum kicken":
                        if (valueKeeper.isAnimationUnlocked("Spielen")) {
                            AnimationPlay play = new AnimationPlay();
                            startAnimation(R.id.play, play, "animationPlay");
                        }
                        else {showToast();}
                        break;
                    case "setzen statt hetzen":
                        if (valueKeeper.isAnimationUnlocked("Hinsetzen")) {
                            AnimationSit sit = new AnimationSit();
                            startAnimation(R.id.sit, sit, "animationSit");
                        }
                        else {showToast();}
                        break;
                    case "Tarnung aktivieren!":
                        if (valueKeeper.isAnimationUnlocked("Verduften")) {
                            AnimationVanish vanish = new AnimationVanish();
                            startAnimation(R.id.vanish, vanish, "animationVanish");
                        }
                        else {showToast();}
                        break;
                    case "ich bin ein Fuchsonaut!":
                        if (valueKeeper.isAnimationUnlocked("Abheben")) {
                            AnimationFly fly = new AnimationFly();
                            startAnimation(R.id.fly, fly, "animationFly");
                        }
                        else {showToast();}
                        break;
                    default:
                        showToast();

                }
            }
        });
        return view;
    }

    private void showToast() {
        Toast.makeText(getContext(),//getActivity().getApplicationContext(),
                "Diese Animation musst du erst noch mit Eicheln freischalten. Sorry!", Toast.LENGTH_SHORT).show();
    }

    private void startAnimation(int id, Fragment fragment, String name) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        List<Fragment> all = getFragmentManager().getFragments();
        String tag;
        Log.d("df", all.toString());
        for (Fragment frag : all) {
            if (frag != null) {
                tag = frag.getTag();
                if (tag == null || tag.equals("animationSit") || tag.equals("animationPlay") || tag.equals("animationTale")
                || tag.equals("animationVanish") || tag.equals("animationFly")) {
                    transaction.remove(frag);
                }
            }
        }
        transaction.add(id, fragment, name);
        transaction.commit();
        getActivity().findViewById(id).setVisibility(View.VISIBLE);//TODO getActivity könnte null returnen (Problem mit support.v7)
    }



}
