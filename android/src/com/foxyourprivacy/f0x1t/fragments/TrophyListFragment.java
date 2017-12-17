package com.foxyourprivacy.f0x1t.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
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

import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.ValueKeeper;
import com.foxyourprivacy.f0x1t.trophies.AcornTrophy;
import com.foxyourprivacy.f0x1t.trophies.TrophyObject;

/**
 * @author Tim
 *         presents and structures the user device settings
 */
public class TrophyListFragment extends Fragment {

    /*
    an array holding all of the displayed settings
     */
    private TrophyObject[] trophyArray;
    private View view;
    /*
    context of current activity
     */

    /**
     * shows a setting list
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

        trophyArray = new TrophyObject[]{
                new AcornTrophy(getString(R.string.capitalist), 60, getString(R.string.capitalistHint), getString(R.string.capitalistDescription), true, R.mipmap.acorn_not, R.mipmap.acorn_finish),
                new AcornTrophy(getString(R.string.sniffer), 50, getString(R.string.snifferHint), getString(R.string.snifferDescription), false, R.mipmap.fox_not, R.mipmap.fox_finish),
                new AcornTrophy(getString(R.string.freshman), 50, getString(R.string.freshmanHint), getString(R.string.freshmanDescription), false, R.mipmap.boar_not, R.mipmap.boar_finish),
                new AcornTrophy(getString(R.string.halftime), 50, getString(R.string.halftimeHint), getString(R.string.halftimeDescription), false, R.mipmap.clock_not, R.mipmap.clock_finish),
                new AcornTrophy(getString(R.string.privacyShield), 50, getString(R.string.privacyShieldHint), getString(R.string.privacyShieldDescription), false, R.mipmap.shield_not, R.mipmap.shield_finish),
                new AcornTrophy(getString(R.string.owl), 50, getString(R.string.owlHint), getString(R.string.owlDescription), false, R.mipmap.owl_not, R.mipmap.owl_finish),
                new AcornTrophy(getString(R.string.earlyBird), 50, getString(R.string.earlyBirdHint), getString(R.string.earlyBirdDescription), false, R.mipmap.bird_not, R.mipmap.bird_finish),
                new AcornTrophy(getString(R.string.powerUser), 50, getString(R.string.powerUserHint), getString(R.string.powerUserDescription), false, R.mipmap.rocket_not, R.mipmap.rocket_finish)

        };
        return view;
    }

    /**
     * defines the specifications of the fragment list view
     *
     * @param savedInstanceState
     * @author Tim
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (TrophyObject t : trophyArray) {
            t.checkScore();
            t.updateScore();
        }


        GridView gridView = view.findViewById(R.id.grid_trophy);

        gridView.setAdapter(new TrophyViewAdapter());
        //display the BigTrophyView Fragment if the trophy is unlocked or an toast message otherwise
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ValueKeeper o = ValueKeeper.getInstance();
                if (o.isTrophyUnlocked(trophyArray[position].getName())) {
                    //Fragment is created
                    BigTrophyViewFragment fragment = new BigTrophyViewFragment();
                    Bundle trophyName = new Bundle();
                    trophyName.putString("trophyName", trophyArray[position].getName());
                    trophyName.putInt("icon", trophyArray[position].getIconSolved());
                    trophyName.putString("trophyDescription", trophyArray[position].getTrophyDescription());
                    fragment.setArguments(trophyName);

                    //add fragment so the activitys' context
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();

                    transaction.add(R.id.big_trophy_view_frame, fragment, "bigTrophyView");
                    transaction = transaction.addToBackStack("bigTrophyView");
                    transaction.commit();
                    //make an light grey relativeLayout visible for a nice visual effect
                    RelativeLayout bigTrophyFrame = getActivity().findViewById(R.id.big_trophy_view_frame);
                    bigTrophyFrame.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            trophyArray[position].getToastDescription(), Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    /**
     * defines the specifications of the GridView
     *
     * @author Tim
     */
    private class TrophyViewAdapter extends BaseAdapter {

        public TrophyViewAdapter() {

        }

        /**
         * defines how a single animationObject is displayed
         *
         * @param position index of the view in the Trophylist
         * @param convertView
         * @param parent
         * @return the view to display on the position
         * @author Tim
         */
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getActivity().getLayoutInflater();//(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ValueKeeper v = ValueKeeper.getInstance();
            View gridView;
            trophyArray[position].checkScore();
            if (convertView == null) {

                //gridView = new View(context);

                // get the layout for the trophy from xml
                gridView = inflater.inflate(R.layout.layout_trophy, null);//TODO maybe parent instead of null? nope

                // set trophy status in textView
                TextView textView = gridView.findViewById(R.id.grid_item_label);
                if (trophyArray[position].isVisibleScore() && !v.isTrophyUnlocked(trophyArray[position].getName())) {
                    // limits amount of the left number with the amount of right number (eg. 30/30)
                    textView.setText(getString(R.string.gridTrophyLabel, trophyArray[position].getScoreNeeded(), trophyArray[position].getScoreNeeded()));
                } else {
                    textView.setText("");
                }
                TextView trophyName = gridView.findViewById(R.id.text_trophy_name);
                trophyName.setText(trophyArray[position].getName());

                // set trophy image
                ImageView imageView = gridView
                        .findViewById(R.id.grid_trophy_image);


                //change the trophy's color whether it's unlocked
                if (v.isTrophyUnlocked(trophyArray[position].getName())) {
                    // set image based on selected text
                    //trophyFrame.setBackgroundColor(getResources().getColor(R.color.trophyBack));
                    imageView.setImageResource(trophyArray[position].getIconSolved());
                } else {
                    //trophyFrame.setBackgroundColor(getResources().getColor(R.color.trophyBack));
                    imageView.setImageResource(trophyArray[position].getIcon());
                }

            } else {
                gridView = convertView;
            }

            return gridView;
        }

        @Override
        public int getCount() {
            return trophyArray.length;
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

}

