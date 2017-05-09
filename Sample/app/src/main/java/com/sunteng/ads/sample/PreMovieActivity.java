package com.sunteng.ads.sample;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.sunteng.ads.R;
import com.sunteng.ads.commonlib.AdServices;
import com.sunteng.ads.instreamad.InStreamAdService;
import com.sunteng.ads.instreamad.core.InStreamAd;
import com.sunteng.ads.instreamad.listener.InStreamAdListener;

/**
 * Created by xiaozhonggao on 2017/4/24.
 */
public class PreMovieActivity extends Activity implements MediaPlayer.OnCompletionListener {
    private InStreamAd mAd;
    private Context mContext;
    private RelativeLayout mAdLayout;
    private VideoView mVideoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pre_activity);
        AdServices.setIsDebugModel(true);
//        InStreamAdService.hideTimeProgress();
//        InStreamAdService.hideDetailBtn();
        initVar();
        initView();
        loadAd();
    }

    private void initVar(){
        mContext = this;
        mAd = InStreamAdService.createInStreamAd(this, "2-38-142");
        mAd.setAdListener(new InStreamAdListener() {
            @Override
            public void onLoadedSuccess() {
                Toast.makeText(mContext, "贴片加载成功", Toast.LENGTH_SHORT).show();
                showAd();
            }

            @Override
            public void onLoadFailed(int errorCode) {
                Toast.makeText(mContext, "贴片加载失败,code： "+errorCode, Toast.LENGTH_SHORT).show();
                startPlayMovie();
            }

            @Override
            public void onAdClick() {
                Toast.makeText(mContext, "贴片被点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFinished() {
                Toast.makeText(mContext, "贴片播放完成", Toast.LENGTH_SHORT).show();
                startPlayMovie();
            }

            @Override
            public void onAdError(int errorCode) {
                Toast.makeText(mContext, "贴片播放错误，code: "+errorCode, Toast.LENGTH_SHORT).show();
                startPlayMovie();
            }
        });
    }

    private void startPlayMovie(){
        if (mVideoPlayer != null){
            Uri videoUri = Uri.parse("http://oiy2vmk35.bkt.clouddn.com/fengyaji");
            mVideoPlayer.setVideoURI(videoUri);
            mVideoPlayer.setMediaController(new MediaController(this));
            mVideoPlayer.start();
            mVideoPlayer.setOnCompletionListener(this);
        }
    }

    private void initView(){
        mAdLayout = (RelativeLayout) findViewById(R.id.video_player_layout);
        mVideoPlayer = (VideoView) findViewById(R.id.video_player);

    }

    private void loadAd(){
        if (mAd != null){
            mAd.loadAd();
        }
    }

    private void showAd(){
        if (mAd == null){
            Toast.makeText(mContext, "贴片广告未请求完成", Toast.LENGTH_SHORT).show();
        }else {
            mAd.showAd(this, mAdLayout);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAd != null){
            mAd.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAd != null){
            mAd.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAd != null){
            mAd.onDestroy();
            mAd = null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        if (width > height){
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            mAdLayout.setLayoutParams(params);
        }else {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    dip2px(mContext, 250));
            mAdLayout.setLayoutParams(params);
        }
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(this, "电影放完啦啦~", Toast.LENGTH_SHORT).show();
    }
}
