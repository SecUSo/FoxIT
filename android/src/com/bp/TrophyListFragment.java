package com.bp;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.bp.Trophies.AcornTrophy;
import com.bp.Trophies.TrophyObject;

/**
 * @author Tim
 * presents and structures the user device settings
 */
public class TrophyListFragment extends Fragment {

    /*
    an array holding all of the displayed settings
     */
    TrophyObject[] trophyArray;
    View view;
    /*
    context of current activity
     */

    Context context;
    GridView gridView;

    /**
     * shows a setting list
     * @author Tim
     * @param inflater
     * @param container
     * @param saveInstanceState
     * @return list view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        view = inflater.inflate(R.layout.fragment_trophy_list, container, false);

        TrophyObject[] trophyArray = {
                new AcornTrophy("Baumhaus Kapitalist", 40, "Geradezu dick vor Eicheln.", true, R.mipmap.acorn_not,R.mipmap.acorn_finish),
                new AcornTrophy("Schnüffler", 50, "Na? Wer kommt denn da nicht aus den Federn?", false, R.mipmap.fox_not,R.mipmap.fox_finish),
                new AcornTrophy("Frischling", 50, "Na? Wer kommt denn da nicht aus den Federn?", false, R.mipmap.boar_not,R.mipmap.boar_finish),
                new AcornTrophy("Halbzeit", 50, "Na? Wer kommt denn da nicht aus den Federn?", false, R.mipmap.clock_not,R.mipmap.clock_finish),
                new AcornTrophy("Privacy Shield", 50, "Na? Wer kommt denn da nicht aus den Federn?", false, R.mipmap.shield_not,R.mipmap.shield_finish),
                new AcornTrophy("Nachteule", 50, "Na? Wer kommt denn da nicht aus den Federn?", false, R.mipmap.owl_not,R.mipmap.owl_finish), //TODO:Relly strange behavior
                new AcornTrophy("Early Bird", 50, "Na? Wer kommt denn da nicht aus den Federn?", false, R.mipmap.bird_not,R.mipmap.bird_finish),
                new AcornTrophy("Power User", 50, "Na? Wer kommt denn da nicht aus den Federn?", false, R.mipmap.rocket_not,R.mipmap.rocket_finish)

        };
        this.trophyArray = trophyArray;


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


        context = getActivity().getApplicationContext();
        gridView = (GridView) view.findViewById(R.id.grid_trophy);

        gridView.setAdapter(new TrophyViewAdapter(getActivity().getApplicationContext()));
        //display the BigTrophyView Fragment if the trophy is unlocked or an toast message otherwise
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ValueKeeper o=ValueKeeper.getInstance();
                if (o.isTrophyUnlocked(trophyArray[position].getName())) {
                    //Fragment is created
                    BigTrophyViewFragment fragment = new BigTrophyViewFragment();
                    Bundle trophyName = new Bundle();
                    trophyName.putString("trophyName", trophyArray[position].getName());
                    trophyName.putInt("icon",trophyArray[position].getIconSolved());
                    fragment.setArguments(trophyName);

                    //add fragment so the activitys' context
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();

                    transaction.add(R.id.big_trophy_view_frame, fragment, "bigTrophyView");
                    transaction = transaction.addToBackStack("bigTrophyView");
                    transaction.commit();
                    //make an light grey relativeLayout visible for a nice visual effect
                    RelativeLayout bigTrophyFrame = (RelativeLayout) getActivity().findViewById(R.id.big_trophy_view_frame);
                    bigTrophyFrame.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            trophyArray[position].getToastDescription(), Toast.LENGTH_SHORT).show();

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
        private Context context;

        public TrophyViewAdapter(Context context) {
            this.context = context;

        }
        /**
         * defines how a single animationObject is displayed
         * @author Tim
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ValueKeeper v=ValueKeeper.getInstance();
            View gridView;
            trophyArray[position].checkScore();
            if (convertView == null) {

                gridView = new View(context);

                // get the layout for the trophy from xml
                gridView = inflater.inflate(R.layout.layout_trophy, null);

                // set trophy status in textView
                TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
                if (trophyArray[position].isVisibleScore()&&!v.isTrophyUnlocked(trophyArray[position].getName())) {
                    // limits amount of the left number with the amount of right number (eg. 30/30)
                    if (trophyArray[position].getScoreCurrently() > trophyArray[position].getScoreNeeded()) {
                        textView.setText(Integer.toString(trophyArray[position].getScoreNeeded()) + "/" + Integer.toString(trophyArray[position].getScoreNeeded()));
                    } else {
                        textView.setText(Integer.toString(trophyArray[position].getScoreCurrently()) + "/" + Integer.toString(trophyArray[position].getScoreNeeded()));
                    }
                } else {
                    textView.setText("");
                }
                TextView trophyName = (TextView) gridView.findViewById(R.id.text_trophy_name);
                trophyName.setText(trophyArray[position].getName());

                // set trophy image
                ImageView imageView = (ImageView) gridView
                        .findViewById(R.id.grid_item_image);


                //change the trophy's color whether it's unlocked
                RelativeLayout trophyFrame = (RelativeLayout) gridView
                        .findViewById(R.id.trophy_frame);

                if (v.isTrophyUnlocked(trophyArray[position].getName())) {
                    // set image based on selected text
                    trophyFrame.setBackgroundColor(Color.WHITE);
                    imageView.setImageResource(trophyArray[position].getIconSolved());
                } else {
                    trophyFrame.setBackgroundColor(Color.WHITE);
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

