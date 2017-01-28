package com.foxyourprivacy.f0x1t.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foxyourprivacy.f0x1t.AnimationObject;
import com.foxyourprivacy.f0x1t.R;

/**
 * presents and structures the user device settings
 */
public class AnimationListFragment extends Fragment {

    /*
    an array holding all of the displayed settings
     */
    AnimationObject[] animationArray;
    View view;
    /*
    context of current activity
     */

    Context context;
    GridView gridView; //the gridView the Animations are displayed in

    /**
     * shows a animation gridView
     *
     * @param inflater
     * @param container
     * @param saveInstanceState
     * @return list view
     * @author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        view = inflater.inflate(R.layout.fragment_trophy_list, container, false);
        //creating the animationObjects - TODO: make it tidy for more animations to be added
        AnimationObject[] animationArray = {
                new AnimationObject("Hinsetzen", "Das Füchslein ruht im Walde, so ruhest auch du.", R.mipmap.animation_sit, 20, false),
                new AnimationObject("Schwanzwedeln", "Erhaben wallt des Fuchses Pracht, und doch ganz sacht... ", R.mipmap.animation_wedeln, 10, false),
                new AnimationObject("Verduften", "Sehen oder gesehen werden...", R.mipmap.animation_vanish, 40, false),
                new AnimationObject("Spielen", "Das Spiel mit dem Ball. Auch der Ballspieler: Spielball. Verspielt.", R.mipmap.animation_play, 30, false),
                new AnimationObject("Abheben", "...und Weitblick gewinnen", R.mipmap.animation_fly, 50, false)};


        // for (AnimationObject ao : animationArray) {
        //   o.addAnimationIfNotContained(ao.getName(), false);
        //ao.getUnlocked());
        //}
        this.animationArray = animationArray;
        return view;
    }

    /**
     * defines the specifications of the GridView
     *
     * @param savedInstanceState
     * @author Tim
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity().getApplicationContext();

        //creating the gridView
        gridView = (GridView) view.findViewById(R.id.grid_trophy);
        gridView.setAdapter(new animationViewAdapter(getActivity().getApplicationContext()));

        //display an tradeRequestFragent on click for buying an animation
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //display the animation description
                Toast.makeText(getActivity().getApplicationContext(),
                        animationArray[position].getToastDescription(), Toast.LENGTH_LONG).show();

                if (!animationArray[position].getUnlocked()) {
                    //tell the new TradeRequestFragment what animation is sold
                    Bundle tradeInfos = new Bundle();
                    tradeInfos.putInt("price", animationArray[position].getPrice());
                    tradeInfos.putString("target", animationArray[position].getName());

                    //add the TradeRequestFragment to the activity's context
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    TradeRequestFragment tradeRequest = new TradeRequestFragment();
                    tradeRequest.setArguments(tradeInfos);
                    //add the fragment to the count_frame RelativeLayout
                    transaction.add(R.id.count_frame, tradeRequest, "count");
                    transaction.addToBackStack("animationTradeRequest");
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.commit();
                }

            }
        });

    }

    /**
     * checks for all displayed animations if they are unlocked and reloads the gridview
     *
     * @author Tim
     */
    public void refreshAllAnimations() {
        //check if unlocked
        for (AnimationObject ao : animationArray) {
            ao.checkUnlocked();
        }

        //reload the gridView
        gridView = (GridView) view.findViewById(R.id.grid_trophy);
        animationViewAdapter i = new animationViewAdapter(getActivity().getApplicationContext());
        i.notifyDataSetChanged();
        gridView.setAdapter(i);

    }

    /**
     * fills the gridView
     */
    private class animationViewAdapter extends BaseAdapter {
        private Context context;

        public animationViewAdapter(Context context) {
            this.context = context;

        }

        /**
         * defines how a single animationObject is displayed
         *
         * @param position
         * @param convertView
         * @param parent
         * @return
         * @author Tim
         */
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;
            animationArray[position].checkUnlocked();
            if (convertView == null) {


                // get the layout for an animation from xml
                gridView = inflater.inflate(R.layout.layout_animation, null);

                // set animation price or unlocked into the textview
                TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
                if (animationArray[position].getUnlocked()) {
                    textView.setText("unlocked");
                } else {
                    textView.setText(Integer.toString(animationArray[position].getPrice()));
                }


                ImageView image = (ImageView) gridView.findViewById(R.id.grid_item_image);
                image.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), animationArray[position].getIcon()));

                TextView name = (TextView) gridView.findViewById(R.id.text_animation_name);
                name.setText(animationArray[position].getName());

                //change the animations color whether it's unlocked
                RelativeLayout trophyFrame = (RelativeLayout) gridView
                        .findViewById(R.id.trophy_frame);
                if (animationArray[position].getUnlocked()) {
                    // set image based on selected text
                    trophyFrame.setBackgroundColor(Color.GREEN);
                } else {
                    trophyFrame.setBackgroundColor(Color.WHITE);
                }

            } else {
                gridView = convertView;
            }

            return gridView;
        }

        @Override
        public int getCount() {
            return animationArray.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }
/*
    @Override
    public void onDestroyView(){
        try{
            AcornCountFragment fragment = (AcornCountFragment) getFragmentManager().findFragmentById(R.id.count_frame);
            FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        } catch (Exception e){
            Log.d("AnimationListFragment","onDestroyView: "+e);
        }
        super.onDestroyView();


    }*/

}

