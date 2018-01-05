package com.example.seuxxd.trainingapp;

import android.content.res.AssetManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.VideoView;

import Constant.URLConstant;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

import com.example.seuxxd.trainingapp.R;


import java.io.IOException;

public class VideoPlayerActivity extends AppCompatActivity {


    private static final String TAG = "VideoPlayerActivity";

    @BindView(R.id.player)
    JZVideoPlayerStandard mPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);
//        String url = "http://www.jmzsjy.com/UploadFile/微课/地方风味小吃——宫廷香酥牛肉饼.mp4";
//        String url = "http://101.132.154.189/test/upload/test1.wmv";
//        String mTitle = getIntent().getStringExtra("title");
        String mUrl = getIntent().getStringExtra("equipath");
        String mName = getIntent().getStringExtra("name");
        mUrl = mUrl.substring(1,mUrl.length());
        mUrl = URLConstant.VIDEO_URL + mUrl;
        Log.i(TAG, "onCreate: " + mUrl);
        mPlayer.setUp(mUrl, JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN,mName);
    }


    @Override
    public void onBackPressed() {
//        if (JZVideoPlayer.backPress())
//            return;
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
