package com.sunteng.ads.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sunteng.ads.R;
import com.sunteng.ads.commonlib.AdServices;
import com.sunteng.ads.splash.core.SplashAd;
import com.sunteng.ads.splash.listener.SplashAdListener;
import com.sunteng.ads.video.api.VideoAdService;

/**
 * Created by xiaozhonggao on 2017/3/21.
 */
public class SplashActivity extends Activity {

    private String appSecret = "8hME_QwQ2GkZT9.VDIwvwSY4*Skjg?Uf";
    private Context mContext;
    private RelativeLayout mAdLayout;
    private SplashAd mSplashAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //避免签名包在非静默安装的情况下，首次启动后进入某一个页面点击home键，再点击桌面图标应用重启问题。
        if(!isTaskRoot()){
            finish();
            return;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//      getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        initVar();
        initView();
        if (mSplashAd.isReady()){
            showSplash();
        }else {
            mAdLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    skipToMain();
                }
            }, 2000);
        }
    }

    private void initView(){
         mAdLayout = (RelativeLayout) findViewById(R.id.ads_layout);
    }

    private void initVar(){
        mContext = this;
        AdServices.init(this, appSecret);
        AdServices.setLocationEnabled(true);
        AdServices.setIsDebugModel(true);
        VideoAdService.setCloseVideoEnable(false);
        mSplashAd = new SplashAd("2-38-48");
        mSplashAd.setAdListener(new SplashAdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onFailed(int errorCode) {
                mAdLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        skipToMain();
                    }
                }, 2000);
            }

            @Override
            public void onAdShowSuccess() {
            }

            @Override
            public void onAdClose() {
                mAdLayout.setVisibility(View.GONE);
                skipToMain();
            }

            @Override
            public void onAdClick() {
            }
        });
    }


    private void showSplash(){
        if (mSplashAd != null && mSplashAd.isReady()){
            mSplashAd.showAd(mAdLayout);
            mAdLayout.setVisibility(View.VISIBLE);
        }else {
            Toast.makeText(mContext, "开屏未请求完成", Toast.LENGTH_SHORT).show();
        }
    }


    private void skipToMain(){
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        mContext.startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {

    }
}
