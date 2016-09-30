package com.bp;

import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

import static android.R.attr.id;
import static android.view.View.GONE;

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

		// FRAGMENTS
		AnimationTale f_tale = new AnimationTale();
		AnimationHead f_head = new AnimationHead();
		AnimationHide f_hide = new AnimationHide();

		assignFragment(R.id.tale, f_tale, "animationTale");
		assignFragment(R.id.head, f_head, "animationHead");
		assignFragment(R.id.hide, f_hide, "animationHide");

		// BUTTONS
		android.widget.ImageButton buttonTale = (android.widget.ImageButton) findViewById(R.id.button_tale);
		buttonTale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				findViewById(R.id.tale).setVisibility(View.VISIBLE);
				findViewById(R.id.head).setVisibility(View.GONE);
				findViewById(R.id.hide).setVisibility(View.GONE);
			}
		});

		android.widget.ImageButton buttonHead = (android.widget.ImageButton) findViewById(R.id.button_head);
		buttonHead.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				findViewById(R.id.head).setVisibility(View.VISIBLE);
				findViewById(R.id.tale).setVisibility(View.GONE);
				findViewById(R.id.hide).setVisibility(View.GONE);
			}
		});

		android.widget.ImageButton buttonHide = (android.widget.ImageButton) findViewById(R.id.button_hide);
		buttonHide.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				findViewById(R.id.hide).setVisibility(View.VISIBLE);
				findViewById(R.id.head).setVisibility(View.GONE);
				findViewById(R.id.tale).setVisibility(View.GONE);
			}
		});

	}


	public void assignFragment(int id, Fragment fragment, String name) {
		//add fragment so the activity's context
		RelativeLayout layout = (RelativeLayout) findViewById(id);
		layout.setVisibility(GONE);
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
