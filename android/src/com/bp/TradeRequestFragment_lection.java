package com.bp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Tim on 25.06.2016.
 */
public class TradeRequestFragment_lection extends Fragment {
    View view; //the fragments view, useful for usages outside of onCreateView
    int price = 1;  //the token amount to unlock this lection
    String articleOfCommerce; //the lection to be unlocked
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
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_trade_request, container, false);
        thisFragment = this;

        //add the yes button's behavior
        Button yesButton = (Button) view.findViewById(R.id.button_yes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean tradeSuccessful = false;
                Activity activity = getActivity();
                //call the purchase method of LectionListActivity
                if (activity instanceof LectionListActivity) {
                    LectionListActivity lectionListActivity = (LectionListActivity) activity;
                    tradeSuccessful = lectionListActivity.purchase(articleOfCommerce);
                }

                //if the trade was successful change the tokenCount
                if (tradeSuccessful) {
                    MethodFactory m = new MethodFactory(getActivity());
                    Method changeTokenCount = m.createMethod("changeTokenCount");
                    changeTokenCount.method("-" + Integer.toString(price));
                }

                //remove this fragment after the trade is finished
                getActivity().getFragmentManager().beginTransaction().remove(thisFragment).commit();
            }
        });
        //remove this fragment if the no button is pressed
        Button noButton = (Button) view.findViewById(R.id.button_no);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().remove(thisFragment).commit();
            }

        });

        return view;
    }

    /**
     * @author Tim
     * @param arg
     */
    public void setArguments(Bundle arg) {
        if (arg.getInt("price") != 0) {
            price = arg.getInt("price");
        }
        articleOfCommerce = arg.getString("target");
    }

}
