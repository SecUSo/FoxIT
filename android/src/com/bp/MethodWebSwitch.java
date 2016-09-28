package com.bp;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Ich on 30.07.2016.
 */
public class MethodWebSwitch extends Method {

    /**
     * open the described website in the smartphone's browser
     * @param webPageUrl url with http up front
     */

    @Override
    public void method(String webPageUrl){
        //open the website by intend
        String url = webPageUrl;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    };
}
