package com.bp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

/**
 * Created by Lynn on 28.09.2016.
 */

public class AnimationWindow extends AndroidFragmentApplication {

    public void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        //config
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.hideStatusBar = false;
        initializeForView(new Fox(),cfg);

//        //show notification bar
//        RelativeLayout layout = new RelativeLayout(this);
//        layout.addView(initializeForView(new Fox(), cfg));
//        setContentView(layout);
    }

}
