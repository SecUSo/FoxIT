package com.bp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;

/**
 * Created by Ich on 30.07.2016.
 */
public class MethodChangeTokenCount extends Method{

    /**
     * change the acornCount in ValueKeeper and manage fragment visualising  the change
     * @aauthor Tim
     * @param tokenDifference the amount the tokenCount is raised lowered, has to be an Integer-String
     */
    @Override
    public void callClassMethod(String tokenDifference){
        final int amount=Integer.parseInt(tokenDifference);

        //add the acornCountFragment to the activity's context
        final FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        final TokenCountFragment count= new TokenCountFragment();
        //add the fragment to the count_frame RelativeLayout
        transaction.add(R.id.count_frame, count, "tokenCount");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();


        //add the animation
        final Handler handler =new Handler();
        //after 1250ms the old grey text is replaced by the new black acornCount
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueKeeper observer= ValueKeeper.getInstance();
                observer.changeTokenCountBy(amount);
                count.changeText(Integer.toString(ValueKeeper.getInstance().getTokenCount()));
            }
        },1250);
        //after 4000ms the Fragment disappears
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              FragmentTransaction transaction= manager.beginTransaction();
            transaction.remove(count);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        },4000);

    };
}
