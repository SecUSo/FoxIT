package com.bp;

import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.bp.Game;
import android.support.v4.app.FragmentTransaction;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;

import static android.R.attr.format24Hour;
import static android.R.attr.id;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * kicks off the animation
 * includes android toolbar design
 */
public class AnimationLauncher extends ActionBarActivity implements AndroidFragmentApplication.Callbacks {

	Toolbar toolbar;

	/**
	 * start animation via static fragment implementation
	 * show android toolbar
	 * @param savedInstanceState
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		// show toolbar
		toolbar = (Toolbar) findViewById(R.id.my_toolbar);
		setSupportActionBar(toolbar);

		AnimationTale f_tale = new AnimationTale();
		assignFragment(R.id.tale, f_tale, "animationHead");
		android.widget.ImageButton buttonTale = (android.widget.ImageButton) findViewById(R.id.button_tale);
		buttonTale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				findViewById(R.id.tale).setVisibility(View.VISIBLE);
				findViewById(R.id.head).setVisibility(View.GONE);
				findViewById(R.id.hide).setVisibility(View.GONE);
			}
		});
	}

//		HashMap<String, Boolean> unlockedAnimations = ValueKeeper.getInstance().animationList;
//		boolean unlocked = false;
//		for (String s : unlockedAnimations.keySet()) {
//			unlocked = unlockedAnimations.get(s);
//			if(unlocked) {
//
//				if(s.equalsIgnoreCase("Kopfschütteln")) {
//					AnimationHead f_head = new AnimationHead();
//					assignFragment(R.id.head, f_head, "animationHead");
//					android.widget.ImageButton buttonHead = (android.widget.ImageButton) findViewById(R.id.button_head);
//					buttonHead.setOnClickListener(new View.OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							findViewById(R.id.head).setVisibility(View.VISIBLE);
//							findViewById(R.id.tale).setVisibility(View.GONE);
//							findViewById(R.id.hide).setVisibility(View.GONE);
//						}
//					});
//				}
//				if(s.equalsIgnoreCase("Schwanzwedeln")) {
//
//					AnimationTale f_tale = new AnimationTale();
//					assignFragment(R.id.tale, f_tale, "animationTale");
//					android.widget.ImageButton buttonTale = (android.widget.ImageButton) findViewById(R.id.button_tale);
//					buttonTale.setOnClickListener(new View.OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							findViewById(R.id.tale).setVisibility(VISIBLE);
//							findViewById(R.id.head).setVisibility(View.GONE);
//							findViewById(R.id.hide).setVisibility(View.GONE);
//						}
//					});
//				}
//				if(s.equalsIgnoreCase("Halt")) {
//					AnimationHide f_hide = new AnimationHide();
//					assignFragment(R.id.hide, f_hide, "animationHide");
//					android.widget.ImageButton buttonHide = (android.widget.ImageButton) findViewById(R.id.button_hide);
//					buttonHide.setOnClickListener(new View.OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							findViewById(R.id.hide).setVisibility(View.VISIBLE);
//							findViewById(R.id.head).setVisibility(View.GONE);
//							findViewById(R.id.tale).setVisibility(View.GONE);
//						}
//					});
//				}
//			}
//		}
//	}

	public void assignFragment(int id, Fragment fragment, String name) {
		//add fragment so the activity's context
		RelativeLayout layout = (RelativeLayout) findViewById(id);
		if (id == R.id.hide) {
			layout.setVisibility(VISIBLE);
		}
		else {
			layout.setVisibility(GONE);
		}
		android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(id, fragment,name);
		transaction.commit();
	}

	/**
	 * show back button
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

	/**
	 * close callbacks
	 */
	@Override
	public void exit() {
	}
}
