package com.foxyourprivacy.f0x1t.slides;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.foxyourprivacy.f0x1t.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Pattern;

/**
 * Created by noah on 04.06.17.
 */

public class VideoSlide extends Slide {

    String content;
    View view;
    VideoView videoView;
    WebView webView;
    MediaController mc;


    @Override
    public void fillLayout() {

    }

    @Override
    public void setArguments(Bundle arg) {
        super.setArguments(arg);
        content = slideInfo.replaceFirst(Pattern.quote("[VIDEO]"), "").replace(";","");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_slide_video, container, false);
        videoView = (VideoView) view.findViewById(R.id.videoView);
        if (!content.contains("iframe")) {
            final ImageButton play = (ImageButton) view.findViewById(R.id.playbutton);
            mc = new MediaController(getContext());
            final TextView waittext = (TextView) view.findViewById(R.id.waittext);

            waittext.setText(R.string.videowaittext);
            waittext.setVisibility(View.VISIBLE);


            //"https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4"

            Log.d("URL", content);
            Uri uri = Uri.parse(content);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    Log.d("prepared", mp.toString());
                    play.setVisibility(View.VISIBLE);
                    waittext.setVisibility(View.INVISIBLE);


                }
            });


            videoView.setMediaController(mc);
            mc.setAnchorView(videoView);


            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (videoView.isPlaying()) {
                        videoView.pause();
                        play.setImageResource(R.drawable.ic_play);
                    } else {
                        videoView.start();
                        play.setImageResource(R.drawable.ic_pause);
                    }
                }
            });
        }else {
            ViewGroup.LayoutParams params = videoView.getLayoutParams();
            params.height = 0;
            videoView.setLayoutParams(params);
            webView = (WebView) view.findViewById(R.id.webvideo);
            String videoSite = "<html><body>Online-Video<br>"+content+"</body></html>";//"<html><body>Video From YouTube<br><iframe width=\"420\" height=\"315\" src=\""+content+"\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

            webView.setWebViewClient(new WebViewClient(){

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return false;
                }

            });
            WebSettings wsettings = webView.getSettings();
            wsettings.setJavaScriptEnabled(true);
            webView.loadData(videoSite, "text/html", "utf-8");
        }

        return view;
    }

}
