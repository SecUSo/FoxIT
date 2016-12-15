package com.bp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;

/**
 * Created by Ich on 30.07.2016.
 */
public class MethodChangeAcornCount extends Method{

    /**
     * change the acornCount in ValueKeeper and manage fragment visualising  the change
     * @aauthor Tim
     * @param acornDifference the amount the acornCount is raised lowered, has to be an Integer-String
     */
    @Override
    public void callClassMethod(String acornDifference){
        final int amount=Integer.parseInt(acornDifference);

        //add the acornCountFragment to the activity's context
        final FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        final AcornCountFragment count= new AcornCountFragment();
        //add the fragment to the count_frame RelativeLayout
        if((activity.findViewById(R.id.count_frame) != null) && !activity.isFinishing()) {
            transaction.add(R.id.count_frame, count, "count");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }

        //add the animation
        final Handler handler =new Handler();
        //after 1250ms the old grey text is replaced by the new black acornCount
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueKeeper observer= ValueKeeper.getInstance();
                observer.changeAcornCountBy(amount);
                if(activity.findViewById(R.id.count_frame)!=null) {
                count.changeText(Integer.toString(ValueKeeper.getInstance().getAcornCount()));}
            }
        },1250);
        if((activity.findViewById(R.id.count_frame)!=null)&&!activity.isFinishing()) {
            //after 4000ms the Fragment disappears
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.remove(count);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.commitAllowingStateLoss();
                }
            }, 4000);
        }
    }
}
