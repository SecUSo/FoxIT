package com.bp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class TrophyRoomActivity extends FoxItActivity {

    TapAdapter_trophy adapter; //defines the content of the tabs, SettingListfragment and AppListFragment
    ViewPager mViewPager;
    AnimationListFragment animationListFragment;
    Toolbar toolbar;

    @Override
    /**sets up the tap adapter
     * @author Tim
     */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy_room);


        //defining the tabs and the tab bar
        adapter = new TapAdapter_trophy(getFragmentManager(), this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //bigTrophyLayoutView adds an graphical effect and it disappears if its klicked
        RelativeLayout bigTrophyViewFrame = (RelativeLayout) findViewById(R.id.big_trophy_view_frame);
        bigTrophyViewFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * handle the purchase of animations
     * @author Tim
     * @param articleOfCommerce name of an animation
     * @return if the purchase was successful
     */
    public boolean purchase(String articleOfCommerce) {
        ValueKeeper o = ValueKeeper.getInstance();
        //reloads the gridView to be up to date
        animationListFragment.refreshAllAnimations();
        //unlock the animation
        return o.unlockAnimation(articleOfCommerce);

    }

    /**
     * necessary for purchase to find the fragment
     * @author Tim
     * @param animationListFragment
     */
    public void setAnimationListFragment(AnimationListFragment animationListFragment) {
        this.animationListFragment = animationListFragment;
    }


    @Override
    /**overrides the back button behavior for bigTrophyViewFrame to nicely disappear
     * @author Tim
     */
    public void onBackPressed() {
        RelativeLayout BigTrophyViewFrame = (RelativeLayout) findViewById(R.id.big_trophy_view_frame);
        if (BigTrophyViewFrame.getVisibility() == View.GONE) {
            super.onBackPressed();
        } else {
            //if BigTrophyViewFrame is visible hide it
            BigTrophyViewFrame.setVisibility(View.GONE);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_activities, menu);
        menu.findItem(R.id.action_options).setVisible(false);
        menu.findItem(R.id.goOn).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.goBack) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}




