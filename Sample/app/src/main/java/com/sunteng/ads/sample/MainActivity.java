package com.sunteng.ads.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunteng.ads.R;
import com.sunteng.ads.banner.BannerAdView;
import com.sunteng.ads.banner.core.BannerAd;
import com.sunteng.ads.banner.listener.BannerAdListener;
import com.sunteng.ads.commonlib.SDKCode;
import com.sunteng.ads.interstitial.core.InterstitialAd;
import com.sunteng.ads.interstitial.listener.InterstitialListener;
import com.sunteng.ads.nativead.NativeAdView;
import com.sunteng.ads.nativead.core.NativeAd;
import com.sunteng.ads.nativead.listener.NativeAdListener;
import com.sunteng.ads.splash.core.SplashAd;
import com.sunteng.ads.splash.listener.SplashAdListener;
import com.sunteng.ads.video.api.FullScreenVideoAd;
import com.sunteng.ads.video.api.VideoAd;
import com.sunteng.ads.video.api.VideoAdListener;
import com.sunteng.ads.video.api.VideoAdService;
import com.sunteng.ads.video.api.WindowVideoAd;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, VideoAdListener {

    private Context mContext;

    private Button mBannerBtn, mPreMovieBtn;
    private Button mLoadInterstitialBtn, mShowInterstitialBtn;
    private Button mLoadSplash, mShowSplash;

    private InterstitialAd mInterstitialAd;
    private FullScreenVideoAd mFullScreenVideoAd;
    private WindowVideoAd mWindowVideoAd;
    private RelativeLayout mWindowVideoParent, mSplaashLayout;
    private LinearLayout mAdsParent;
    private SplashAd mSplashAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVar();
        initView();
    }

    private void initVar(){
        mContext = this;
    }

    private void initView(){
        mBannerBtn = (Button) findViewById(R.id.banner);
        mBannerBtn.setOnClickListener(this);
        mLoadInterstitialBtn = (Button) findViewById(R.id.loadInterstitial);
        mLoadInterstitialBtn.setOnClickListener(this);
        mShowInterstitialBtn = (Button) findViewById(R.id.showInterstitial);
        mShowInterstitialBtn.setOnClickListener(this);
        findViewById(R.id.bt_load_window_video).setOnClickListener(this);
        findViewById(R.id.bt_show_window_video).setOnClickListener(this);
        findViewById(R.id.bt_load_full_video).setOnClickListener(this);
        findViewById(R.id.bt_show_full_video).setOnClickListener(this);
        findViewById(R.id.bt_show_native_ad).setOnClickListener(this);
        findViewById(R.id.bt_show_native_ads).setOnClickListener(this);

        mWindowVideoParent = (RelativeLayout) findViewById(R.id.rl_window_parent);
        mSplaashLayout = (RelativeLayout) findViewById(R.id.rl_splash_parent);
        mAdsParent = (LinearLayout) findViewById(R.id.ll_ads_parent);
        mLoadSplash = (Button) findViewById(R.id.loadSplash);
        mLoadSplash.setOnClickListener(this);
        mShowSplash = (Button) findViewById(R.id.showSplash);
        mShowSplash.setOnClickListener(this);
        mPreMovieBtn = (Button) findViewById(R.id.preMovie);
        mPreMovieBtn.setOnClickListener(this);

    }


    private void loadSplash(){
        mSplashAd = new SplashAd("2-38-37");
        mSplashAd.setAdListener(new SplashAdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(mContext, "开屏请求成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(int errorCode) {
                Toast.makeText(mContext, "开屏请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdShowSuccess() {
                Toast.makeText(mContext, "开屏展示成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClose() {
                mSplashAd = null;
                Toast.makeText(mContext, "开屏关闭成功", Toast.LENGTH_SHORT).show();
                mSplaashLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAdClick() {
                Toast.makeText(mContext, "开屏被点击", Toast.LENGTH_SHORT).show();
            }
        });
        mSplashAd.loadAd();
    }

    private void showSplash(){
      if (mSplashAd != null && mSplashAd.isReady()){
          mSplashAd.showAd(mSplaashLayout);
          mSplaashLayout.setVisibility(View.VISIBLE);
      }else {
          Toast.makeText(mContext, "开屏未请求完成", Toast.LENGTH_SHORT).show();
      }
    }

    private void loadInterstitialAd(){
        if (null == mInterstitialAd){
            mInterstitialAd = new InterstitialAd("2-38-39");
        }
        mInterstitialAd.setListener(interstitialListener);
        mInterstitialAd.loadAd();
    }

    private void showInterstitialAd(){
        if (mInterstitialAd != null && mInterstitialAd.isReady()){
            mInterstitialAd.showAd();
        }else {
            Toast.makeText(mContext, "插屏请求未完成", Toast.LENGTH_SHORT).show();
        }
    }

    InterstitialListener interstitialListener = new InterstitialListener() {
        @Override
        public void onAdLoaded() {
            Toast.makeText(mContext, "竞价成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdShowFailed(int errorCode) {
            Toast.makeText(mContext, "展示失败，errorCode:"+errorCode, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdShowSuccess() {
            Toast.makeText(mContext, "展示成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdClose() {
            Toast.makeText(mContext, "关闭", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdClick() {
            Toast.makeText(mContext, "点击", Toast.LENGTH_SHORT).show();
        }
    };

    private void showBanner(){
        BannerAdView bannerAdView = new BannerAdView(mContext, "2-38-38" );
        bannerAdView.setAdListener(new BannerAdListener() {
            @Override
            public void onAdShowSuccess(BannerAd ad) {
                Toast.makeText(mContext, "展示成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClose(BannerAd ad) {
                Toast.makeText(mContext, "关闭成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClick(BannerAd ad) {
                Toast.makeText(mContext, "点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdShowFailed(int errorCode, BannerAd ad) {
                Toast.makeText(mContext, "展示失败,errorCode："+errorCode, Toast.LENGTH_SHORT).show();
            }
        });
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        addContentView(bannerAdView, layoutParams);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.banner:
                showBanner();
                break;
            case R.id.loadInterstitial:
                loadInterstitialAd();
                break;
            case R.id.showInterstitial:
                showInterstitialAd();
                break;
            case R.id.bt_load_window_video:
                loadWindowVideo();
                break;
            case R.id.bt_show_window_video:
                showWindowVideo();
                break;
            case R.id.bt_load_full_video:
                loadFullVideo();
                break;
            case R.id.bt_show_full_video:
                showFullVideo();
                break;
            case R.id.bt_show_native_ad:
                showNativeAd();
                break;
            case R.id.bt_show_native_ads:
                showNativeAds();
                break;
            case R.id.loadSplash:
                loadSplash();
                break;
            case R.id.showSplash:
                showSplash();
                break;
            case R.id.preMovie:
                showPreMovie();
                break;
        }
    }

    private void showPreMovie(){
        Intent i = new Intent(this, PreMovieActivity.class);
        startActivity(i);
    }

    private void showNativeAds() {
        Log.i("testDemo", "nativeAd_more");
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        intent.putExtra("isPreload", true);
        startActivity(intent);
    }

    private void showNativeAd(){
        Log.i("testDemo", "showNativeAd");
        final String adUnitId = "2-38-52";//广告位id
        NativeAd ad = new NativeAd(adUnitId);
        ad.setNativeAdListener(new NativeAdListener() {
            @Override
            public void onReceiveAd(NativeAd ad) {
                Toast.makeText(getApplicationContext(), "Native广告加载完成",Toast.LENGTH_SHORT).show();
                showNativeAdView(ad);
            }

            @Override
            public void onFailed(NativeAd ad, int code) {
                switch (code){
                    case SDKCode.CODE_BACK_AMOUNT:
                        Toast.makeText(getApplicationContext(), "原生广告返量",Toast.LENGTH_SHORT).show();
                        break;
                    case SDKCode.CODE_BLANK_RESPONSE:
                        Toast.makeText(getApplicationContext(), "原生广告留白",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "原生广告展示失败"+code,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        ad.loadAd();
    }

    private void showNativeAdView(NativeAd nativeAd) {
        Log.i("testDemo", "showNativeAdView");
        NativeAdView nativeAdView = (NativeAdView) LayoutInflater.from(this).inflate(R.layout.ad_native_layout, null);
        TextView titleView = (TextView)nativeAdView.findViewById(R.id.ad_view_title);
        TextView descriptionView = (TextView)nativeAdView.findViewById(R.id.ad_view_body);
        Button actionButton = (Button)nativeAdView.findViewById(R.id.ad_view_action_button);

        ImageView iconView = (ImageView)nativeAdView.findViewById(R.id.ad_view_header_image);
        ImageView mediaView = (ImageView)nativeAdView.findViewById(R.id.ad_view_image);
        ImageView logoView = (ImageView)nativeAdView.findViewById(R.id.item_logo_img);

        titleView.setText(nativeAd.getTitle());
        descriptionView.setText(nativeAd.getDescription());
        actionButton.setText(nativeAd.getButtonContent());

        List<NativeAd.Image> images = nativeAd.getImages();
        mediaView.setImageDrawable(images.get(0).getDrawable());

        NativeAd.Image icon_image = nativeAd.getIconImage();
        iconView.setImageDrawable(icon_image.getDrawable());

        NativeAd.Image logo_image = nativeAd.getLogoImage();
        logoView.setImageDrawable(logo_image.getDrawable());

        nativeAd.registerView(nativeAdView);
        mAdsParent.addView(nativeAdView, 0);
    }


    private void showFullVideo() {
        if (mFullScreenVideoAd == null || !mFullScreenVideoAd.isReady()){
            Toast.makeText(this, "视频未加载完成，请提示完成后再展示", Toast.LENGTH_SHORT).show();
            return;
        }
        mFullScreenVideoAd.showVideo(MainActivity.this);
    }

    private void loadFullVideo() {
        if (mFullScreenVideoAd == null){
            mFullScreenVideoAd = VideoAdService.createFullModelVideoAd(MainActivity.this, "2-38-42");
        }
        mFullScreenVideoAd.setVideoAdListener(this);
        mFullScreenVideoAd.loadAd();
    }

    private void showWindowVideo() {
        if (mWindowVideoAd == null || !mWindowVideoAd.isReady()){
            Toast.makeText(this, "视频未加载完成，请提示完成后再展示", Toast.LENGTH_SHORT).show();
            return;
        }
        mWindowVideoParent.setVisibility(View.VISIBLE);
        mWindowVideoAd.showVideo(MainActivity.this, mWindowVideoParent);
    }

    private void loadWindowVideo() {
        if (mWindowVideoAd == null){
            VideoAdService.setCloseVideoEnable(false);
            mWindowVideoAd = VideoAdService.createWindowModelVideoAd(MainActivity.this, "2-38-42");
        }
        mWindowVideoAd.setVideoAdListener(this);
        mWindowVideoAd.loadAd();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWindowVideoAd != null){
            mWindowVideoAd.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWindowVideoAd != null){
            mWindowVideoAd.onResume();
        }
    }

    @Override
    public void onLoadSuccess(VideoAd videoAd) {
        Toast.makeText(this, "视频加载完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadFailed(VideoAd videoAd, int errorCode) {
        Toast.makeText(this, "视频加载失败 code=" + errorCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVideoAdFinished(VideoAd videoAd, int code) {
        if (videoAd instanceof WindowVideoAd){
            if (mWindowVideoParent.getVisibility() == View.VISIBLE){
                mWindowVideoParent.setVisibility(View.GONE);
            }
        }
        Toast.makeText(this, "视频展示完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlayError(VideoAd videoAd, int errorCode) {
        Toast.makeText(this, "视频展示失败 code=" + errorCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReadyPlay(VideoAd videoAd) {
        // TODO 可以设置自动播放
        if (videoAd instanceof WindowVideoAd){
            videoAd.playAlreadyPreparedVideo();
        }
        Toast.makeText(this, "视频准备好播放", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickAd(int i) {
        Toast.makeText(this, "视频 onClickAd ", Toast.LENGTH_SHORT).show();
    }
}
