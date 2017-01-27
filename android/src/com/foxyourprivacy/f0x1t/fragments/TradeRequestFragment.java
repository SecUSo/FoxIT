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
import com.foxyourprivacy.f0x1t.activities.LectionListActivity;
import com.foxyourprivacy.f0x1t.activities.TrophyRoomActivity;
import com.foxyourprivacy.f0x1t.lessonmethods.Method;
import com.foxyourprivacy.f0x1t.lessonmethods.MethodFactory;

/**
 * Created by Tim on 25.06.2016.
 */
public class TradeRequestFragment extends Fragment {
    View view; //the fragments view, useful for usages outside of onCreateView
    int price = 0; //the price of the article of commerce
    String articleOfCommerce; //the name of the item on display
    Fragment thisFragment; //this fragment useful to be called in another callClassMethod

    /**
     * @author Tim
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //display the item's price
        TextView priceText = (TextView) view.findViewById(R.id.text_amount);
        priceText.setText(Integer.toString(price));

    }

    @Override
    /**
     * defines the layout of this fragment and provides the yes and no button behavior
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_trade_request, container, false);
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
                boolean tradeSuccessful = false;
                Activity activity = getActivity();
                //call the purchase callClassMethod of the current activity
                if (price > o.getAcornCount()) {
                    //display the animation description
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Du hast nicht genügend Eicheln um diese Animation zu kaufen.", Toast.LENGTH_SHORT).show();


                } else {

                    if (activity instanceof LectionListActivity) {
                        LectionListActivity lectionListActivity = (LectionListActivity) activity;
                        tradeSuccessful = lectionListActivity.purchase(articleOfCommerce);
                    }

                    if (activity instanceof TrophyRoomActivity) {
                        TrophyRoomActivity trophyRoomActivity = (TrophyRoomActivity) activity;
                        tradeSuccessful = trophyRoomActivity.purchase(articleOfCommerce);
                    }

                    //if the trade was successful change the acornCount
                    if (tradeSuccessful) {
                        MethodFactory mf = new MethodFactory(getActivity());
                        Method changeAcornCount = mf.createMethod("changeAcornCount");
                        changeAcornCount.callClassMethod("-" + Integer.toString(price));
                    }
                }
                //remove this fragment after the trade is finished
                getActivity().onBackPressed();
            }
        });
        //remove this fragment if the no button is pressed
        Button noButton = (Button) view.findViewById(R.id.button_no);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
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
        price = arg.getInt("price");
        articleOfCommerce = arg.getString("target");

    }


}
