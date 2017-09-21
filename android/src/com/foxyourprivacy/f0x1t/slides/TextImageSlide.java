package com.foxyourprivacy.f0x1t.slides;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.SlideImageGetter;

/**
 * Created by noah on 04.06.17.
 */

public class TextImageSlide extends Slide {
    String slidetext = "";
    TextView[] views;
    View rootView;
    Boolean imageInserted = false;

    public TextImageSlide() {
        super();
    }

    public void setArguments(Bundle slidecontent) {
        super.setArguments(slidecontent);
        TextView temp;
        slidetext = slideInfo.replace(";", "<br>");

    }

    @Override
    public void fillLayout() {
//sets the displayed text
        TextView text = (TextView) rootView.findViewById(R.id.textimageslide);
        SlideImageGetter sig = new SlideImageGetter(text, getContext());
        if (getContext() == null) Log.d("isnull", ":(");
        Spanned htmlspanned;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            htmlspanned = Html.fromHtml(slidetext, Html.FROM_HTML_MODE_LEGACY, sig, null);
        } else {
            htmlspanned = Html.fromHtml(slidetext, sig, null);
        }
        text.setText(htmlspanned);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_slide_textimage, container, false);
        fillLayout();
        return rootView;
    }
}
