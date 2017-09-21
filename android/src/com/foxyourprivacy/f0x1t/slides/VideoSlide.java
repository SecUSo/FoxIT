package com.foxyourprivacy.f0x1t.slides;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by noah on 04.06.17.
 */

public class VideoSlide extends Slide {

    @Override
    public void fillLayout() {

    }

    @Override
    public void setArguments(Bundle arg) {
        super.setArguments(arg);
        slideInfo = slideInfo.replaceFirst("[VIDEO]", "");
        MediaPlayer mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mp.setDataSource(slideInfo);
            Log.d("afterAsyncStart", mp.toString());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d("onPrepared", mp.toString());
                mp.start();
            }
        });
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("error!!", what + "  " + extra);
                return false;
            }
        });
        mp.prepareAsync();


    }
}
