# SuntengMob SDK V2.0.4
### 一、导入sdk
    将sdk解压后的libs目录下的jar文件导入到工程指定的libs目录
### 二、配置AndroidManifest.xml文件
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

#### 三、SDK初始化 

- **调用初始化**：
        在主Activity的onCreate()调用下面静态方法：
         AdService.init(Activity activity, String publisherId, String appKey , String appId);
             
 >**注意:**
 > 其中publisherId为客户id，appKey和appId均需从sunteng获取

### 四、插屏广告展示
    /**
     * 显示插屏广告示例代码
     */
    private void showInterstitalAd(){
        Ad interstitialAd = new InterstitialAd(); //实例化一个插屏广告
        interstitialAd.setPlacementId(51);//51为测试广告位id
        interstitialAd.loadAd(new InterstitialAdLoadedListener() {
                    @Override
                    public void onReceiveAd(InterstitialAd interstitialAd) {
                    //加载完成广告会回调onReceiveAd(),在此时便可调用showAd()进行广告展示
                        interstitialAd.showAd(new AdDisplayListener() {
                            @Override
                            public void onAdDisplayed(Ad ad) {
                                //插屏广告展现成功
                            }
                            @Override
                            public void onAdClicked(Ad ad) {
                                //用户点击广告
                            }
                            @Override
                            public void onAdClosed(Ad ad) {
                               //当插屏广告被关闭
                            }
                        });
                    }
                    
                    @Override
                    public void onFailedToReceiveAd(int placementId , int code) {
                        //当广告加载失败时会回调这个方法，广告竞价失败后返回返量或者留白也会回调这个方法;
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
 > **注意:**
 > 必须用*setPlacementId(int placemetnId)*方法设置广告位id;
 AdDisplayListener监听中的*onFailedToReceiveAd(int placementId , int code)*，placementId时广告位id，code是错误码，具体原因请查阅下文的失败错误码。

### 五、开屏广告展示

    /**
     * 显示开屏广告示例代码
     */
    private void showSplashAd(){
        final int placementid = 49; //广告位id
        SplashManager.getIns().loadAd(placementid, new SplashAdLoadedListener() {
            @Override
            public void onReceiveAd(SplashAd splashAd) {
                splashAd.showAd(new AdDisplayListener() {
                    @Override
                    public void onAdDisplayed(Ad ad) {
                        //当开屏成功显示后会回调
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        //当开屏广告被点击时候回调
                    }

                    @Override
                    public void onAdClosed(Ad ad) {
                        //当开屏广告关闭时会回调
                    }
                });
            }

            @Override
            public void onFailedToReceiveAd(int placementId, int code) {
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
 > *SplashManager.getIns().loadAd(placementId, listener)*方法中第一个参数为广告位id，第二个参数是用来监听广告竞价是否成功的回调。
 
### 六、展示横幅广告(Banner)

    /**
     * 添加一个banner广告的的示例代码
     */
    private void showBanner(){
        //设置banner在父容器中的位置
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;

       final int placementId = 50; //广告位置id
        BannerAdView bannerAdView = new BannerAdView(this, placementId ,bannerWidth, bannerHeight); 
        bannerAdView.setAdListener(new BannerAdListener() {
            @Override
            public void onSwitched(BannerAdView adView) {
                //banner刷新切换成功
            }

            @Override
            public void onShowSuccess(BannerAdView adView) {
                //banner展示成功
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
        //将banner广告添加到父容器
        addContentView(bannerAdView, layoutParams);
    }
  > **注意:**
 > BannerAdView构造方法中的4个参数分别为：上下文参数context，广告位Id，广告的宽度，广告的高度，宽度和高度计量单位为px。
 
###七、原生广告(NativeAd)

**1、加载单个原生广告**
    
    /**
     *示例代码：
     * 实例化一个NativeAd，并调用loadAd()方法发起广告请求
     */
    NativeAd ad = new NativeAd();
    //ad.disableImageResourcePreload();
    ad.setPlacementId(place);
    ad.loadAd(new NativeAdLoadedListener() {
        @Override
        public void onReceiveAd(NativeAd ad) {
            //广告竞价成功
            showNativeAdView(ad);
        }

        @Override
        public void onFailedToReceiveAd(int placementId, int code) {
            //广告竞价失败
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
    
    /**
     * 展示NativeAd
     */
    private void showNativeAdView(NativeAd ad) {
        NativeAdView nativeAdView = (NativeAdView) LayoutInflater.from(DemoActivity.this).inflate(R.layout.ad_native_layout, null);
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
        parent_layout.addView(nativeAdView);
    }
    
>a) *ad.disableImageResourcePreload()；*作用为关闭图片预加载，SDK会默认加载广告中的用到的资源图片，若开发者调用了这个方法，再调用NativeAd.Image或者其子类中的getDrawabl()方法时，将会获得一个空值,此时开发者若要展示NativeAd的图片，需要调用getUrl()方法获取图片的URL，然后自行处理；

>b) *R.layout.ad_native_layout*是一个被*com.suntengmob.sdk.core.NativeAdView*包裹的自定义布局，开发者需要根据自己的展示需要自定义布局中的内容，并且NativeAdView是FrameLayout的子类;

>c) NativeAdView的数据填充完毕之后，需要调用*ad.registerView(nativeAdView);*方法,否则SDK无法统计到用户的行为。

**2、加载多个原生广告**
    
     /**
     * 示例代码
     * 预加载多个NativeAd备用
     */
    NativeAdsManager mNativeAdsManager = new NativeAdsManager(102, 20);
    mNativeAdsManager.loadAds(new NativeAdsManager.LoadAdsListener() {
        @Override
        public void onLoadedAds(int placementId , int failedCount) {
            Util.printErrorInfo("onFailedAds count = " + failedCount);
            showContents();
        }
    });


    /**
     *(在adapter中)获取缓存的NativeAd,并展示
     */
    NativeAd ad = mNativeAdsManager.nextCacheAd();
    if (nativeAd == null){
        return;
    }
    adViewHolder.mAdActionButton.setText(nativeAd.getButtonContent());
    adViewHolder.mAdBodyTextView.setText(nativeAd.getDescription());
    adViewHolder.mAdImageView.setImageDrawable(nativeAd.getImages().get(0).getDrawable());
    adViewHolder.mHeaderImageView.setImageDrawable(nativeAd.getIconImage().getDrawable());
    adViewHolder.mAdTileTextView.setText(nativeAd.getTitle());
    adViewHolder.mLogoImageView.setImageDrawable(nativeAd.getLogoImage().getDrawable());
    adViewHolder.mAdView.setTag(position);
    nativeAd.registerView((NativeAdView)adViewHolder.itemView);
    
>a) *NativeAdsManager*的构造方法需要两个参数，第一个是广告位ID,第二个是本次请求的原生广告的数量，缓存数量为1-20个之间，如果传入的值大于20，会只缓存20个；

>b) *NativeAdsManager.LoadAdsListener*是所有广告竞价全部完成之后的回调，*onLoadedAds()*的两个参数分别为：广告位ID，本次竞价失败广告数量；

>c) *nextCacheAd()*方法会循环获取队列中缓存的广告；

>d) *NativeAdsManager*中也提供*disableImageResourcePreload()*方法设置不预加载图片资源。

 
 
###八、广告展示失败时的一些错误码
    AdService.CODE_UNKNOWN_ERROR = -1;//异步请求过程中发生错误
    AdService.CODE_HTTP_ERROR = 1; //处理http请求的过程中发生错误
    AdServices.CODE_PRELOAD_FIALED = 2; //广告资源加载失败
    AdService.CODE_BAD_NETWORK = 0;//无网络或无网络权限
    AdService.CODE_BLANK_RESPONSE = 201 // 竞价请求失败,返回留白
    AdService.CODE_BACK_AMOUNT = 202; //竞价请求失败,返回返量

###九、适配Android7.0（如果不需要支持可直接跳过本段）
> 如果App需要支持Android7.0以上的设备，sdk提供了Android7.0多窗口模式和文件共享等相关特性的支持。  

**1、Activity分屏适配**
Manifest中注册SplashActivity时候，需要加上*android:resizeableActivity="false"*

**2、文件共享**
**a) 在7.0中下载安装apk文件共享必须使用FileProvider，所以需要在AndroidManifest.xml配置如下代码：**

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

**b) 可以看到在meta-data中，定义了一个资源路径，第二步就是创建res/xml/provider_paths.xml文件：**
> **注意：只需把path中{{com.suntengmob.sdk}}部分替换成你当前项目的包名，复制到文件中即可。**

	<?xml version="1.0" encoding="utf-8"?>
	<paths xmlns:android="http://schemas.android.com/apk/res/android">
	     <!--/storage/emulated/0/Android/com.suntengmob.sdk/files/SSP/-->
   		 <!--把path中{{com.sunteng.suntengmob_sample}}部分替换成项目的包名!-->
  	  	 <external-path name="sunteng_sdk_download_apk" path="Android/data/com.sunteng.suntengmob_sample/files/SSP/"/>
	</paths>
	
###十、其他
**debug模式**
    AdService.setIsDebugModel(boolean debug);
>传入参数为true时，为debug模式，有日志输出，默认值为true。发布正式版本时候请关闭debug模式。

**位置信息获取开关**  
    AdService.setLocationEnable(boolean enable);
>传入参数为true时，获取用户当前地理位置，默认值为true。