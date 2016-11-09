# SuntengMob SDK V2.0.3
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
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    
    <!-- 以下是申明Activity：-->
    <activity android:name="com.suntengmob.sdk.activity.InterstitialActivity"
              android:theme="@android:style/Theme.Translucent"
              android:configChanges="orientation|keyboardHidden|screenSize|screenLayout|smallestScreenSize" />
    <activity android:name="com.suntengmob.sdk.activity.BrowserAdActivity"
              android:configChanges="orientation|keyboardHidden|screenSize|screenLayout|smallestScreenSize" />
    <activity android:name="com.suntengmob.sdk.activity.SplashActivity"
              android:screenOrientation="portrait"
              android:resizeableActivity="false"
              android:configChanges="orientation|keyboardHidden|screenSize|screenLayout|smallestScreenSize"/>
    <!-- 以上是申明Activity：-->

#### 3、SDK初始化 

- **调用初始化**：
            在主Activity的onCreate()调用下面静态方法
             AdService.init(Activity activity, String publisherId, String appKey , String appId);
             
             
 >**注意:**
 > 其中publisherId为客户ID，appKey和AppId均需从sunteng获取

### 4、插屏广告展示
    /**
     * 显示插屏广告
     */
    private void showInterstitalAd(){
        Ad interstitialAd = new InterstitialAd(); //实例化一个插屏广告
        interstitialAd.setPlacementId(51);
        interstitialAd.loadAd( new AdEventListener() {
                    @Override
                    public void onReceiveAd(Ad ad) {
                    //加载完成广告会回调onReceiveAd(),在此时便可调用showAd()进行广告展示
                        ad.showAd(new AdDisplayListener() {
                            @Override
                            public void onAdDisplayed(Ad ad) {
                                //插屏广告展现
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
                    public void onFailedToReceiveAd(Ad ad , int code) {
                        //当广告加载失败时会回调onFailedToReceiveAd();
                        //其中广告竞价失败后发生造成的返量或者留白也会回调onFailedToReceiveAd();
                        switch (code){
                            case AdService.CODE_BACK_AMOUNT:
                                //插屏广告竞价失败，结果为返量（无广告返回）
                                break;
                            case AdService.CODE_BLANK_RESPONSE:
                                //插屏竞价失败，结果为留白（无广告返回）
                                break;
                            default:
                                //其他原因导致插屏展示失败
                                break;
                        }
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
        SplashManager.getIns().loadAd(placementid, new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                ad.showAd(new AdDisplayListener() {
                    @Override
                    public void onAdDisplayed(Ad ad) {
                        //当开屏成功显示后会回调
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        //当点击开屏广告
                    }

                    @Override
                    public void onAdClosed(Ad ad) {
                        //当开屏广告关闭时会回调
                    }
                });
            }

            @Override
            public void onFailedToReceiveAd(Ad ad, int code) {
                //当开屏广告显示失败时会回调
                switch (code){
                    case AdService.CODE_BACK_AMOUNT:
                        //开屏竞价失败，结果为返量
                        break;
                    case AdService.CODE_BLANK_RESPONSE:
                        //开屏竞价失败，结果为留白
                        break;
                    default:
                       //其他原因导致开屏展示失败
                        break;
                }
            }
        });
    }
  > **注意: **
 > 必须要设置广告位id
 
### 6、展示Banner广告

       /**
     * 添加一个banner广告
     */
    private void showBanner(){
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;

       final int placementId = 50; //广告位置id
        BannerAdView bannerAdView = new BannerAdView(this, placementId ,bannerWidth, bannerHeight); //默认自适应屏幕
        bannerAdView.setAdListener(new BannerAdListener() {
            @Override
            public void onSwitched(BannerAdView adView) {
                //banner刷新切换成功
            }

            @Override
            public void onShowSuccess(BannerAdView adView) {
                //banner显示成功
            }

            @Override
            public void onShowFailed(BannerAdView adView , int code) {
                Util.printInfo("Show Banner onShowFailed,error code:"+code);
                switch (code){
                    case AdService.CODE_BACK_AMOUNT:
                        //竞价请求失败，banner返量
                        break;
                    case AdService.CODE_BLANK_RESPONSE:
                        //竞价请求失败，banner留白
                        break;
                    default:
                        //其他原因导致banner展示失败
                        break;
                }
            }
        });
        //将banner广告添加到容器
        addContentView(bannerAdView, layoutParams);
    }
  > **注意: **
 > 必须要设置广告位id。  
 
###7、广告展示失败时的一些错误码
    AdService.CODE_UNKNOWN_ERROR = -1;//异步请求过程中发生错误
    AdService.CODE_HTTP_ERROR = 1; //处理http请求的过程中发生错误
    AdService.CODE_BAD_NETWORK = 0;//无网络或无网络权限
    AdService.CODE_BLANK_RESPONSE = 201 // 竞价请求失败,返回留白
    AdService.CODE_BACK_AMOUNT = 202; //竞价请求失败,返回返量

###8、适配Android7.0（如果不需要支持可直接跳过本段）
> 如果App需要支持Android7.0以上的设备，sdk提供了Android7.0多窗口模式和文件共享等相关特性的支持。  

**1、Activity分屏适配**
Manifest中注册SplashActivity时候，需要加上*android:resizeableActivity="false"*

**2、7.0文件共享**
**2.1 在7.0中下载安装apk文件共享必须使用FileProvider，所以需要在AndroidManifest.xml配置如下代码：**

	<!-- 配置provider用于适配7.0, authorities的{{com.sunteng.suntengmob_sample}}部分替换成当前应用包名，
         authorities = "{{BuildConfig.APPLICATION_ID}}.download.download.MobSdk.fileProvider" ,
          provider_paths为创建在xml文件夹内的资源文件 -->
	<provider
        android:name="android.support.v4.content.FileProvider"
        android:authorities="com.sunteng.suntengmob_sample.download.MobSdk.fileProvider"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/provider_paths"/>
        </provider>

**2.2.可以看到在meta-data中，定义了一个资源路径，第二步就是创建res/xml/provider_paths.xml文件：**
> **注意：只需把path中{{com.suntengmob.sdk}}部分替换成你当前项目的包名，复制到文件中即可。**

	<?xml version="1.0" encoding="utf-8"?>
	<paths xmlns:android="http://schemas.android.com/apk/res/android">
	     <!--/storage/emulated/0/Android/com.suntengmob.sdk/files/SSP/-->
   		 <!--把path中{{com.sunteng.suntengmob_sample}}部分替换成项目的包名!-->
  	  	 <external-path name="sunteng_sdk_download_apk" path="Android/data/com.sunteng.suntengmob_sample/files/SSP/"/>
	</paths>
	
###8、其他
**debug模式**
    AdService.setIsDebugModel(boolean debug);
>传入参数为true时，为debug模式，有日志输出，默认值为true。发布正式版本时候请关闭debug模式。

**位置信息获取开关**  
    AdService.setLocationEnable(boolean enable);
>传入参数为true时，获取用户当前地理位置，默认值为true。建议开启。
