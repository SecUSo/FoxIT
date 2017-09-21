package com.foxyourprivacy.f0x1t.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.animation.AnimationTail;
import com.foxyourprivacy.f0x1t.animation.CircularViewPagerHandler;
import com.foxyourprivacy.f0x1t.animation.SwipeAdapter;

import static android.view.View.VISIBLE;
import static com.foxyourprivacy.f0x1t.R.id.view_pager;

/**
 * kicks off the animation
 * includes android toolbar design
 */
public class AnimationLauncher extends ActionBarActivity implements AndroidFragmentApplication.Callbacks {

    Toolbar toolbar;
    ViewPager viewPager;

    /**
     * start animation via static fragment implementation
     * show android toolbar
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        viewPager = (ViewPager) findViewById(view_pager);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);
        viewPager.addOnPageChangeListener(new CircularViewPagerHandler(viewPager));

        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        // show toolbar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //erste Animation bereits beim starten anzeigen
        AnimationTail tail = new AnimationTail();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.tale, tail, "animationTale");
        transaction.commit();
        findViewById(R.id.tale).setVisibility(VISIBLE);
    }

    /**
     * show back button
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_activities, menu);
        menu.findItem(R.id.goOn).setVisible(false);
        menu.findItem(R.id.goBack).setVisible(true);
        menu.findItem(R.id.action_options).setVisible(false);
        return true;
    }

    /**
     * make backButton working correctly
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.goBack) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void exit() {

    }
}
