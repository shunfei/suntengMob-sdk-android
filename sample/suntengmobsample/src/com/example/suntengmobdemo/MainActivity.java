package com.example.suntengmobdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.suntengmob.sdk.Ad;
import com.suntengmob.sdk.AdService;
import com.suntengmob.sdk.core.InterstitialAd;
import com.suntengmob.sdk.listener.AdDisplayListener;
import com.suntengmob.sdk.listener.AdEventListener;


public class MainActivity extends Activity implements OnClickListener {

	private Button button = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdService.init(this, "c-183"); //初始化
        setContentView(R.layout.activity_main);
        
        button = (Button)findViewById(R.id.showInter);
       button.setOnClickListener(this);
    }
    
    /**
     * 显示插屏广告
     */
    private void showInterstitalAd(){
      final Ad interstitialAd = new InterstitialAd();
      interstitialAd.setPlacementId(25); //设置广告位
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
		showInterstitalAd();
	}

}
