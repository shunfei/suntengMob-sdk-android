package com.sunteng.suntengmob_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suntengmob.sdk.Ad;
import com.suntengmob.sdk.AdService;
import com.suntengmob.sdk.core.BannerAdView;
import com.suntengmob.sdk.core.InterstitialAd;
import com.suntengmob.sdk.core.NativeAd;
import com.suntengmob.sdk.core.NativeAdView;
import com.suntengmob.sdk.core.SplashAd;
import com.suntengmob.sdk.core.SplashManager;
import com.suntengmob.sdk.listener.AdDisplayListener;
import com.suntengmob.sdk.listener.BannerAdListener;
import com.suntengmob.sdk.listener.InterstitialAdLoadedListener;
import com.suntengmob.sdk.listener.NativeAdLoadedListener;
import com.suntengmob.sdk.listener.SplashAdLoadedListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String appKey = "8hME_QwQ2GkZT9.VDIwvwSY4*Skjg?Uf";
    public static final String publisherId = "2";
    public static final String appId = "38";
    private Button showSplashAdBtn, showInterstitialAdBtn, showBannerBtn,showNativeAd,showMoreNativeAd;
    private LinearLayout parent_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //避免签名包在非静默安装的情况下，首次启动后进入某一个页面点击home键，再点击桌面图标应用重启问题。
        if(!isTaskRoot()){
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        AdService.init(this,publisherId,appKey,appId);
        AdService.setDebug(true);
        showSplashAdBtn = (Button) findViewById(R.id.splash);
        showSplashAdBtn.setOnClickListener(this);
        showInterstitialAdBtn = (Button) findViewById(R.id.interstitial);
        showInterstitialAdBtn.setOnClickListener(this);
        showBannerBtn = (Button) findViewById(R.id.banner);
        showBannerBtn.setOnClickListener(this);
        showNativeAd = (Button) findViewById(R.id.nativeAd);
        showNativeAd.setOnClickListener(this);
        showMoreNativeAd = (Button) findViewById(R.id.nativeAd_more);
        showMoreNativeAd.setOnClickListener(this);
        parent_layout = (LinearLayout)findViewById(R.id.parent_layout);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.banner:
                showBanner();
                break;
            case R.id.interstitial:
                showInterstitalAd();
                break;
            case R.id.splash:
                showSplashAd();
                break;
            case R.id.nativeAd:
                showNativeAd();
                break;
            case R.id.nativeAd_more:
                Intent intent = new Intent(this, RecyclerViewActivity.class);
                intent.putExtra("placementId", 52);
                intent.putExtra("isPreload", true);
                startActivity(intent);
                break;
        }
    }

    private void showNativeAd(){
        NativeAd ad = new NativeAd();
        ad.setPlacementId(52);
        ad.loadAd(new NativeAdLoadedListener() {
            @Override
            public void onReceiveAd(NativeAd ad) {
                showNativeAdView(ad);
            }

            @Override
            public void onFailedToReceiveAd(int placementId, int code) {
                switch (code){
                    case AdService.CODE_BACK_AMOUNT:
                        Toast.makeText(getApplicationContext(), "原生广告返量",Toast.LENGTH_SHORT).show();
                        break;
                    case AdService.CODE_BLANK_RESPONSE:
                        Toast.makeText(getApplicationContext(), "原生广告留白",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "原生广告展示失败"+code,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void showNativeAdView(NativeAd ad) {
        NativeAdView nativeAdView = (NativeAdView) LayoutInflater.from(this).inflate(R.layout.ad_native_layout, null);
        TextView titleView = (TextView)nativeAdView.findViewById(R.id.ad_view_title);
        TextView descriptionView = (TextView)nativeAdView.findViewById(R.id.ad_view_body);
        Button actionButton = (Button)nativeAdView.findViewById(R.id.ad_view_action_button);

        ImageView iconView = (ImageView)nativeAdView.findViewById(R.id.ad_view_header_image);
        ImageView mediaView = (ImageView)nativeAdView.findViewById(R.id.ad_view_image);
        ImageView logoView = (ImageView)nativeAdView.findViewById(R.id.item_logo_img);

        titleView.setText(ad.getTitle());
        descriptionView.setText(ad.getDescription());
        actionButton.setText(ad.getButtonContent());

        List<NativeAd.Image> images = ad.getImages();
        mediaView.setImageDrawable(images.get(0).getDrawable());

        NativeAd.Image icon_image = ad.getIconImage();
        iconView.setImageDrawable(icon_image.getDrawable());

        NativeAd.Image logo_image = ad.getLogoImage();
        logoView.setImageDrawable(logo_image.getDrawable());

        ad.registerView(nativeAdView);
        parent_layout.addView(nativeAdView, 0);
    }

    private void showBanner(){
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        BannerAdView bannerAdView = new BannerAdView(this, 38 ,1080, 200); //默然自适应屏幕
        bannerAdView.setAdListener(new BannerAdListener() {
            @Override
            public void onSwitched(BannerAdView adView) {
                Toast.makeText(getApplicationContext(),
                        "banner切换了",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onShowSuccess(BannerAdView adView) {
                Toast.makeText(getApplicationContext(),
                        "banner显示成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onShowFailed(BannerAdView adView , int code) {
                switch (code){
                    case AdService.CODE_BACK_AMOUNT:
                        Toast.makeText(getApplicationContext(), "banner返量",Toast.LENGTH_SHORT).show();
                        break;
                    case AdService.CODE_BLANK_RESPONSE:
                        Toast.makeText(getApplicationContext(), "banner留白",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "banner展示失败",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        //将banner广告添加到容器
        addContentView(bannerAdView, layoutParams);
    }


    private void showSplashAd(){
        final int placementId = 37; //广告位id
        SplashManager.getIns().loadAd(placementId, new SplashAdLoadedListener() {
            @Override
            public void onReceiveAd(SplashAd splashAd) {
                splashAd.showAd(new AdDisplayListener() {
                    @Override
                    public void onAdDisplayed(Ad ad) {
                        //当开屏成功显示后会回调
                        Toast.makeText(getApplicationContext(), "开屏显示了",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        //当点击开屏广告
                        Toast.makeText(getApplicationContext(), "开屏被点击",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClosed(Ad ad) {
                        //当开屏广告关闭时会回调
                        Toast.makeText(getApplicationContext(), "开屏关闭",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailedToReceiveAd(int placementId, int code) {
                //当开屏广告显示失败时会回调
                switch (code){
                    case AdService.CODE_BACK_AMOUNT:
                        Toast.makeText(getApplicationContext(), "开屏返量",Toast.LENGTH_SHORT).show();
                        break;
                    case AdService.CODE_BLANK_RESPONSE:
                        Toast.makeText(getApplicationContext(), "开屏留白",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "开屏展示失败",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


    private void showInterstitalAd(){
        InterstitialAd interstitialAd = new InterstitialAd(); //实例化一个插屏广告
        interstitialAd.setPlacementId(39);
        interstitialAd.loadAd( new InterstitialAdLoadedListener() {
            @Override
            public void onReceiveAd(InterstitialAd interstitialAd) {

                //加载完成广告会回调onReceiveAd(),在此时便可调用showAd()进行广告展示
                interstitialAd.showAd(new AdDisplayListener() {
                    @Override
                    public void onAdDisplayed(Ad ad) {
                        //当插屏广告展现
                        Toast.makeText(getApplicationContext(),"插屏展示",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onAdClicked(Ad ad) {
                        Toast.makeText(getApplicationContext(),"插屏点击",Toast.LENGTH_SHORT).show();
                        //当用户点击广告
                    }
                    @Override
                    public void onAdClosed(Ad ad) {
                        //当用户点击关闭广告或在广告界面按下back键
                        Toast.makeText(getApplicationContext(),"插屏关闭",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailedToReceiveAd(int placementId , int code) {
                //当广告加载失败时会回调onFailedToReceiveAd();
                switch (code){
                    case AdService.CODE_BACK_AMOUNT:
                        Toast.makeText(getApplicationContext(), "插屏返量",Toast.LENGTH_SHORT).show();
                        break;
                    case AdService.CODE_BLANK_RESPONSE:
                        Toast.makeText(getApplicationContext(), "插屏留白",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "插屏展示失败",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


}
