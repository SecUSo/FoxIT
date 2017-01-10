package com.foxyourprivacy.f0x1t.Slides;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.foxyourprivacy.f0x1t.AsyncTasks.DownloadImageTask;
import com.foxyourprivacy.f0x1t.R;

/**
 * Created by Tim on 25.06.2016.
 */
public class WebImageSlide extends Slide {
    View view;

    /* fills the slide's layout by calling fillLayout
     *@author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.layout_slide_web_image, container, false);
        fillLayout();
        return view;
    }


    /**
     * fills the slide's layout
     *
     * @author Tim
     */
    @Override
    public void fillLayout() {


        ImageView image = (ImageView) view.findViewById(R.id.image_web);
        String imageName = parameter.get("text");
        Log.d("MyApp", imageName);
        switch (imageName) {
            case "apfel": {
                image.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.mipmap.ic_hourglass_empty_black_48dp));
                break;
            }

            default:
                new DownloadImageTask((ImageView) view.findViewById(R.id.image_web)).execute(parameter.get("text"));
        }

    }


}