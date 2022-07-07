package com.kk.ffmpegdemo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

import com.kk.ffmpegdemo.utils.FFUtils;


public class FFVideoView extends SurfaceView {

    Surface mSurface;

    public FFVideoView(Context context) {
        super(context);

        init();
    }

    public FFVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public FFVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FFVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
        getHolder().setFormat(PixelFormat.RGBA_8888);
        mSurface = getHolder().getSurface();

    }

    public void playVideo(final String videoPath) {
        new Thread(() -> {
            Log.d("FFVideoView", "run: playVideo");
            FFUtils.playVideo(videoPath, mSurface);
        }).start();
    }
}
