package com.example.suntengmobdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.suntengmob.sdk.listener.SplashAdListener;

public class MainActivity extends Activity implements OnClickListener {

	private Button btnShowInter = null;
	private Button btnShowBanner = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdService.init(this, "c-183"); //初始化
        showSplashAd(); // 展示开屏
        
        setContentView(R.layout.activity_main);
        
       btnShowInter = (Button)findViewById(R.id.showInter);
       btnShowInter.setOnClickListener(this);
       
       btnShowBanner = (Button)findViewById(R.id.showBanner);
       btnShowBanner.setOnClickListener(this);
    }
    
    /**
     * 显示开屏广告
     */
    private void showSplashAd(){
        final int placementid = 37; //广告位id
        SplashManager.getIns().showSplash(placementid, new SplashAdListener() {
            @Override
            public void onShowSuccess() {
            //当开屏成功显示后会回调
                Toast.makeText(getApplicationContext(), 
                   "开屏显示了",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onShowFailed() {
            //当开屏广告显示失败时会回调
                Toast.makeText(getApplicationContext(), 
                        "显示开屏失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSplashClosed() {
            //当开屏广告关闭时会回调
                Toast.makeText(getApplicationContext(), 
                  "开屏关闭",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSplashClick() {
                //当点击开屏广告
                Toast.makeText(getApplicationContext(),
                    "开屏被点击",Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    /**
     * 展示banner广告条
     */
    private void showBanner(){
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;

       final int placementId = 38; //广告位置id
        BannerAdView bannerAdView = new BannerAdView(this, placementId); //默然自适应屏幕
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
            public void onShowFailed(BannerAdView adView) {
                Toast.makeText(getApplicationContext(),
                        "banner显示失败",Toast.LENGTH_SHORT).show();
            }
        });
        //将banner广告添加到容器
        addContentView(bannerAdView, layoutParams);
    }
    
    /**
     * 显示插屏广告
     */
    private void showInterstitalAd(){
      final Ad interstitialAd = new InterstitialAd();
      interstitialAd.setPlacementId(39); //设置广告位
       interstitialAd.loadAd(new AdEventListener() {
		
		@Override
		public void onReceiveAd(Ad arg0) {
			Toast.makeText(MainActivity.this, "onReceiveAd", Toast.LENGTH_SHORT).show();
			interstitialAd.showAd(new AdDisplayListener() {
				
				@Override
				public void onAdDisplayed(Ad arg0) {
					Toast.makeText(MainActivity.this, "onAdDisplayed", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onAdClosed(Ad arg0) {
					Toast.makeText(MainActivity.this, "onAdClosed", Toast.LENGTH_SHORT).show();

				}
				
				@Override
				public void onAdClicked(Ad arg0) {
					Toast.makeText(MainActivity.this, "onAdClicked", Toast.LENGTH_SHORT).show();

				}
			});
		}
		
		@Override
		public void onFailedToReceiveAd(Ad arg0) {
			Toast.makeText(MainActivity.this, "onFailedToReceiveAd", Toast.LENGTH_SHORT).show();

		}
	});
       
    }
    
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.showInter) {
			showInterstitalAd();
		}else if(v.getId() == R.id.showBanner) {
			showBanner(); //展示banner
		}
	}



}
