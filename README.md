# SuntengMob SDK V1.0.0
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
         <!-- 申明Activity：-->
        <activity android:name="com.suntengmob.sdk.activity.InterstitialActivity"
            android:theme="@android:style/Theme.Translucent"
            android:configChanges="orientation|keyboardHidden|screenSize" />

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
