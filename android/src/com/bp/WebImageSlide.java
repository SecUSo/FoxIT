package com.bp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Tim on 25.06.2016.
 */
public class WebImageSlide extends Slide {
    View view;

    /* fills the slide's layout by calling fillLayout
     *@author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view=inflater.inflate(R.layout.layout_slide_web_image,container,false);
        fillLayout();
        return view;
    }


    /**fills the slide's layout
     * @author Tim
     */
    @Override
    void fillLayout() {
        new DownloadImageTask((ImageView) view.findViewById(R.id.image_web))
                .execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");

    }


}