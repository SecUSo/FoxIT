package com.foxyourprivacy.f0x1t.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.ValueKeeper;
import com.foxyourprivacy.f0x1t.activities.LessonListActivity;
import com.foxyourprivacy.f0x1t.lessonmethods.Method;
import com.foxyourprivacy.f0x1t.lessonmethods.MethodFactory;

/**
 * Created by Tim on 25.06.2016.
 */
public class TradeRequestFragment_lesson extends Fragment {
    View view; //the fragments view, useful for usages outside of onCreateView
    int price = 1;  //the token amount to unlock this lesson
    String articleOfCommerce; //the lesson to be unlocked
    Fragment thisFragment;

    /**
     * @author Tim
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView priceText = (TextView) view.findViewById(R.id.text_amount);
        priceText.setText(Integer.toString(price));
    }

    @Override
    /**
     * defines the layout of this fragment and provides the yes and no button behavior
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_trade_request_lesson, container, false);
        thisFragment = this;

        LinearLayout button = (LinearLayout) view.findViewById(R.id.whole_frame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //add the yes button's behavior
        Button yesButton = (Button) view.findViewById(R.id.button_yes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueKeeper o = ValueKeeper.getInstance();
                if (price > o.getTokenCount()) {
                    //display the animation description
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Du hast nicht genug Tokens um diese Lektion zu kaufen.", Toast.LENGTH_SHORT).show();


                } else {
                    boolean tradeSuccessful = false;
                    Activity activity = getActivity();
                    //call the purchase callClassMethod of LessonListActivity
                    if (activity instanceof LessonListActivity) {
                        LessonListActivity lessonListActivity = (LessonListActivity) activity;
                        tradeSuccessful = lessonListActivity.purchase(articleOfCommerce);
                    }

                    //if the trade was successful change the tokenCount
                    if (tradeSuccessful) {
                        MethodFactory m = new MethodFactory(getActivity());
                        Method changeTokenCount = m.createMethod("changeTokenCount");
                        changeTokenCount.callClassMethod("-" + Integer.toString(price));
                    }
                }
                //remove this fragment after the trade is finished
                getActivity().onBackPressed();//getFragmentManager().beginTransaction().remove(thisFragment).commit();
            }
        });
        //remove this fragment if the no button is pressed
        Button noButton = (Button) view.findViewById(R.id.button_no);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();//getFragmentManager().beginTransaction().remove(thisFragment).commit();
            }

        });

        return view;
    }

    /**
     * tells the fragment the information needed for this trade, called whenever this fragment gets created
     *
     * @param arg
     * @author Tim
     */
    public void setArguments(Bundle arg) {
        if (arg.getInt("price") != 0) {
            price = arg.getInt("price");
        }
        articleOfCommerce = arg.getString("target");
    }

}
