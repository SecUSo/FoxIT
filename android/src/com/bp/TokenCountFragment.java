package com.bp;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ich on 25.06.2016.
 */
public class TokenCountFragment extends Fragment{
    View view; //the fragments view, useful for usages outside of onCreateView
    int tokenCount; //amount of token currently on display

    /**
     *@author Tim
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //fetch the current tokenCount from the ValueKeeper
        tokenCount= ValueKeeper.getInstance().getTokenCount();
        //and display it
        TextView text=(TextView) view.findViewById(R.id.text_token_count);
                text.setText(Integer.toString(tokenCount));
    }

    @Override
    /**
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view=inflater.inflate(R.layout.fragment_token_count,container,false);
        return view;
    }

    /**
     * Method for changing the displayed tokenCount from the outside
     * Called in MethodChangeTokenCount
     * @author Tim
     * @param newText the new text resembling the new acornCount
     */
    public void changeText(String newText){
        TextView text=(TextView) view.findViewById(R.id.text_token_count);
        text.setText(newText);
        text.setTextColor(Color.BLACK);

    }


}
