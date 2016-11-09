package com.sunteng.suntengmob_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.suntengmob.sdk.Ad;
import com.suntengmob.sdk.AdService;
import com.suntengmob.sdk.core.BannerAdView;
import com.suntengmob.sdk.core.InterstitialAd;
import com.suntengmob.sdk.core.SplashManager;
import com.suntengmob.sdk.listener.AdDisplayListener;
import com.suntengmob.sdk.listener.AdEventListener;
import com.suntengmob.sdk.listener.BannerAdListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String appKey = "8hME_QwQ2GkZT9.VDIwvwSY4*Skjg?Uf";
    public static final String publisherId = "2";
    public static final String appId = "38";
    private Button splashBtn,interstitialBtn,bannerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdService.init(this,publisherId,appKey,appId);
        AdService.setDebug(true);
        splashBtn = (Button) findViewById(R.id.splash);
        splashBtn.setOnClickListener(this);
        interstitialBtn = (Button) findViewById(R.id.interstitial);
        interstitialBtn.setOnClickListener(this);
        bannerBtn = (Button) findViewById(R.id.banner);
        bannerBtn.setOnClickListener(this);
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
        }
    }

    private void showBanner(){
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;

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
        final int placementid = 37; //广告位id
        SplashManager.getIns().loadAd(placementid, new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                ad.showAd(new AdDisplayListener() {
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
            public void onFailedToReceiveAd(Ad ad, int code) {
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
        Ad interstitialAd = new InterstitialAd(); //实例化一个插屏广告
        interstitialAd.setPlacementId(39);
        interstitialAd.loadAd( new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {

                //加载完成广告会回调onReceiveAd(),在此时便可调用showAd()进行广告展示
                ad.showAd(new AdDisplayListener() {
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
            public void onFailedToReceiveAd(Ad ad , int code) {
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
