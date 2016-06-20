# SuntengMob SDK V2.0.0
### 1、导入sdk
         将sdk解压后的libs目录下的jar文件导入到工程指定的libs目录
### 2、更新AndroidManifest.xml文件
     <!-- 请将下面权限配置代码复制到AndroidManifest.xml文件中：-->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
         <!-- 以下是申明Activity：-->
        <activity android:name="com.suntengmob.sdk.activity.InterstitialActivity"
                  android:theme="@android:style/Theme.Translucent"
                  android:configChanges="orientation|keyboardHidden|screenSize" />
            
       <activity android:name="com.suntengmob.sdk.activity.BrowserAdActivity"
                 android:configChanges="orientation|keyboardHidden|screenSize" />

        <activity android:name="com.suntengmob.sdk.activity.SplashActivity"
                  android:screenOrientation="portrait"
                  android:configChanges="orientation|keyboardHidden|screenSize"/>
        <!-- 以上是申明Activity：-->

#### 3、初始化 

- **调用初始化**：
            在主Activity的onCreate()调用下面静态方法
             AdService.init(Activity activity, String customId );
             
             
 >**注意:**
 > 1、其中customId为客户ID。

### 4、插屏广告展示

    /**
     * 显示插屏广告
     */
    private void showInterstitalAd(){
        Ad interstitialAd = new InterstitialAd(); //实例化一个插屏广告
        interstitialAd.setPlacementId(25); //设置广告id（非常重要）
        interstitialAd.loadAd(new AdEventListener() {
                    @Override
                    public void onReceiveAd(Ad ad) {
                    //加载完成广告会回调onReceiveAd(),在此时便可调用showAd()进行广告展示
                        ad.showAd(new AdDisplayListener() {
                            @Override
                            public void onAdDisplayed(Ad ad) {
                                //当插屏广告展现
                            }
                            @Override
                            public void onAdClicked(Ad ad) {
                                //当用户点击广告
                            }
                            @Override
                            public void onAdClosed(Ad ad) {
                               //当用户点击关闭广告或在广告界面按下back键
                            }
                        });
                    }
                    @Override
                    public void onFailedToReceiveAd(Ad ad) {
                        //当广告加载失败时会回调onFailedToReceiveAd();
                    }
        });
    }
  > **注意: **
 > 必须要设置广告位id 

### 5、开屏广告展示

    /**
     * 显示开屏广告
     */
    private void showSplashAd(){
        final int placementid = 49; //广告位id
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
  > **注意: **
 > 必须要设置广告位id，开屏广告监听器可以设置为null
 
### 6、展示Banner广告

       /**
     * 添加一个banner广告
     */
    private void showBanner(){
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;

       final int placementId = 50; //广告位置id
        BannerAdView bannerAdView = new BannerAdView(this, placementId); 
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
                Util.printInfo("Show Banner onShowFailed");
                Toast.makeText(getApplicationContext(),
                        "banner显示失败",Toast.LENGTH_SHORT).show();
            }
        });
        //将banner广告添加到容器
        addContentView(bannerAdView, layoutParams);
    }
  > **注意: **
 > 必须要设置广告位id。

