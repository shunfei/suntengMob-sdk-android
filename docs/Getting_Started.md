# SuntengMob SDK V3.1.0
### 一、导入sdk
    将sdk解压后的libs目录下的jar文件导入到工程指定的libs目录
### 二、配置AndroidManifest.xml文件  

```xml
<!-- 请将下面权限配置代码复制到AndroidManifest.xml文件中：-->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

<!-- 以下是申明Activity：-->
<activity android:name="com.sunteng.ads.interstitial.activity.InterstitialActivity"
          android:theme="@android:style/Theme.Translucent"
          android:configChanges="orientation|keyboardHidden|screenSize|screenLayout|smallestScreenSize" />
<activity android:name="com.sunteng.ads.commonlib.activity.BrowserAdActivity"
          android:configChanges="orientation|keyboardHidden|screenSize|screenLayout|smallestScreenSize" />
<activity android:name="com.sunteng.ads.splash.activity.SplashActivity"
          android:screenOrientation="portrait"
          android:resizeableActivity="false"
          android:configChanges="orientation|keyboardHidden|screenSize|screenLayout|smallestScreenSize"/>
<activity android:name="com.sunteng.ads.video.core.VideoActivity"
              android:configChanges="keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize"
              tools:ignore="UnusedAttribute"
              android:theme="@android:style/Theme.NoTitleBar" />
<!-- 以上是申明Activity：-->
```
### 三、SDK初始化 

**调用初始化**：  
在主Activity的`onCreate()`调用下面静态方法：
        
`AdService.init(Activity activity, String appSecret);`
         
 >**注意:**
 > appSecret 需从 sunteng 获取

### 四、插屏广告展示
```java
/**
 * 请求插屏广告示例代码
 * adUnitID 从官网获取
 */
 InterstitialAd mInterstitialAd = null;
 
 /*加载插屏广告示例代码*/
  private void loadInterstitialAd(){
    if (null == mInterstitialAd){
        mInterstitialAd = new InterstitialAd("2-38-43");
    }
    mInterstitialAd.setListener(interstitialListener);
    mInterstitialAd.loadAd();
}

/*展示插屏广告示例代码*/
private void showInterstitialAd(){ // 调用展示方法前先调用方法判断，也可在回调中调用 showAd 方法
    if (mInterstitialAd != null && mInterstitialAd.isReady()){
        mInterstitialAd.showAd();
    }else {
        Toast.makeText(mContext, "插屏请求未完成", Toast.LENGTH_SHORT).show();
    }
}  

/*插屏广告监听器*/
 InterstitialListener interstitialListener = new InterstitialListener() {
    @Override
    public void onAdLoaded() {
       //加载完成广告会回调onReceiveAd(),也可以在此时便可调用showAd()进行广告展示
        Toast.makeText(mContext, "竞价成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdShowFailed(int errorCode) {
        Toast.makeText(mContext, "展示失败，errorCode:"+errorCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdShowSuccess() {
        //当插屏广告展现
        Toast.makeText(mContext, "展示成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClose() {
        //当用户点击关闭广告或在广告界面按下back键
        Toast.makeText(mContext, "关闭", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClick() {
        //当用户点击广告
        Toast.makeText(mContext, "点击", Toast.LENGTH_SHORT).show();
    }
};
```
           
 > **注意:**
 `InterstitialListener` 监听中的` onAdShowFailed(int code)`，code是错误码，具体原因请查阅下文的失败错误码。

### 五、开屏广告展示

```java    
/**
* 请求加载开屏广告示例代码
*/
SplashAd mSplashAd = null;
String adUnitId = "2-38-48";
private void loadSplash(){
  mSplashAd = new SplashAd(adUnitId);
  mSplashAd.setAdListener(new SplashAdListener() {
      @Override
      public void onAdLoaded() {
          Toast.makeText(mContext, "开屏加载完成", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onFailed(int errorCode) {
          Toast.makeText(mContext, "开屏加载失败", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onAdShowSuccess() {
          Toast.makeText(mContext, "开屏展示完成", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onAdClose() {
          Toast.makeText(mContext, "开屏被关闭", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onAdClick() {
          Toast.makeText(mContext, "开屏被点击", Toast.LENGTH_SHORT).show();
      }
  });
}

// 开屏广告如果 isReady() 判断为 false 时会自动缓存广告资源等待被下次调用
private void showSplash(){
  if (mSplash != null && mSplash.isReady()){
      mSplash.showAd();
  }else {
      Toast.makeText(mContext, "开屏请求未完成", Toast.LENGTH_SHORT).show();
  }
}
```
  > 也提供 `loadAd()` 的方法可以手动请求广告，如下代码:
  `mSplash.loadAd()`

 
### 六、展示横幅广告(Banner)

```java    
/**
 * 添加一个banner广告的的示例代码
 */
 private void showBanner(){
     //设置banner在父容器中的位置
     FrameLayout.LayoutParams layoutParams =
             new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
     layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;

     final String adUnitID = "2-54-63"; //广告位id
     BannerAdView bannerAdView = new BannerAdView(this, adUnitID ,bannerWidth, bannerHeight); 
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
             Util.printInfo("Show Banner onShowFailed,error code:"+code);
          }
     });
     //将banner广告添加到父容器
     addContentView(bannerAdView, layoutParams);
  }
```
  > **注意:**
 > `BannerAdView` 构造方法中的4个参数分别为：上下文参数context，广告位Id，广告的宽度，广告的高度，宽度和高度计量单位为px。
 
### 七、原生广告(NativeAd)

**1、加载单个原生广告**
    
```java    
/**
*示例代码：
* 实例化一个NativeAd，并调用loadAd()方法发起广告请求
*/
 final String adUnitId = "2-38-90";//广告位id
 NativeAd ad = new NativeAd(adUnitId);
 //ad.disableImageResourcePreload(); // 设置图片资源是否预加载，设置后可利用第三方图片加载库 load 图片 url
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
```    
>a) `ad.disableImageResourcePreload()；`作用为关闭图片预加载，SDK会默认加载广告中的用到的资源图片，若开发者调用了这个方法，再调用`NativeAd.Image`或者其子类中的`getDrawabl()`方法时，将会获得一个空值,此时开发者若要展示`NativeAd`的图片，需要调用`getUrl()`方法获取图片的URL，然后自行处理（可利用第三方图片加载库完成，如 Glide 或 Fresco 等）；

>b) `R.layout.ad_native_layout`是一个`com.sunteng.ads.nativead.core.NativeAdView`包裹的自定义布局，开发者需要根据自己的展示需要自定义布局中的内容，并且`NativeAdView`是`FrameLayout`的子类;

>c) `NativeAdView`的数据填充完毕之后，需要调用`ad.registerView(nativeAdView);`方法,否则SDK无法统计到用户的行为。

**2、加载多个原生广告**

```java
/**
* 示例代码
* 预加载多个NativeAd备用
*/
final String adUnitId = "2-38-90";

NativeAdsManager mNativeAdsManager = new NativeAdsManager(adUnitId, 5);
mNativeAdsManager.loadAds(new NativeAdsManager.LoadAdsListener() {
    @Override
    public void onLoadedAds(String adUnitId , int failedCount) {
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
```
    
>a) `NativeAdsManager`的构造方法需要两个参数，第一个是广告位ID,第二个是本次请求的原生广告的数量，缓存数量为1-20个之间，如果传入的值大于20，会只缓存20个；

>b) `NativeAdsManager.LoadAdsListener`是所有广告竞价全部完成之后的回调，`onLoadedAds()`的两个参数分别为：广告位ID，本次竞价失败广告数量；

>c) *nextCacheAd()*方法会循环获取队列中缓存的广告；

>d) `NativeAdsManager`中也提供`disableImageResourcePreload()`方法设置不预加载图片资源。

 具体使用可参考 Samlpe 中的基于 RecyclerView 实现的代码示例。
 
### 八、原生视频广告
**设置视频广告在播放中是否可关闭**
  ``` VideoAdService.setCloseVideoEnable(boolean closeEnable); ```

> 用于控制视频播放界面左上脚关闭按钮是否展示的开关，用户可以在播放中点击关闭按钮关闭广告。

**创建视频广告组件**
```java
/**
 * 创建一个全屏模式视频广告组件
 * @param activity 视频 activity
 * @param adUnitID 视频广告位id
 * @return FullScreenVideoAd 全屏模式视频广告组件
 */
 VideoAdService.createFullModelVideoAd(Activity activity, String adUnitID);
```
```java
/**
 * 创建一个窗口模式视频广告组件
 * @param activity 视频 activity
 * @param adUnitID 视频广告位id
 * @return WindowVideoAd 窗口模式视频广告组件
 */
VideoAdService.createWindowModelVideoAd(Activity activity, String adUnitID);

String adUnitId = "2-38-14";
WindowVideoAd windoeVideAd = VideoAdService.createWindowModelVideoAd(DemoActivity.this, adUnitId);
FullScreenVideoAd fullScreenVideoAd = VideoAdService.createFullModelVideoAd(DemoActivity.this, adUnitId);
```

> **注意：要区分全屏模式和窗口模式，对于创建的同一个VideoAd而言，只能支持一种模式下的视频播放！**  

**设置广告监听器**

```java
mFullScreenVideoAd.setVideoAdListener(videoAdListener);
mWindowVideoAd.setVideoAdListener(videoAdListener)
VideoAdListener videoAdListener  = new VideoAdListener() {
     @Override
     public void onLoadSuccess(VideoAd videoAd) {
     	 // 所有广告资源下载完
     }

     @Override
     public void onLoadFailed(VideoAd videoAd, int errorCode) {
     	// 广告请求或资源下载失败时回调
     }

     @Override
     public void onVideoAdFinished(VideoAd videoAd, int code) {
	      // 广告显示结束后会回调，窗口视频会移除视频组件
	      if (videoAd instanceof WindowVideoAd){
	      		
	      }
 	}

     @Override
     public void onPlayError(VideoAd videoAd, int errorCode) {
     	// 广告显示失败后会回调
     }

     @Override
     public void onReadyPlay(VideoAd videoAd){
	     // 播放ui组件创建完成回调，用于管理多个窗口视频的自动播放时机
		 if (videoAd instanceof WindowVideoAd){
		     videoAd.playAlreadyPreparedVideo();
		 }
		   Toast.makeText(this, "视频准备好播放", Toast.LENGTH_SHORT).show();
     }

     @Override
     public void onClickAd(int code) {
     	// 广告被点击时回调
     }
 }
```

**视频广告请求和资源下载** 
 `mWindowVideoAd.loadAd();`
`mFullScreenVideoAd.loadAd();`

> 广告预加载包括预先请求广告和广告资源下载

 
**视频广告展示（建议在广告监听器的回调中调用）**
> 展示广告与请求广告进行了分步处理，可预先请求，需要时展示。
> 如果不等加载完成直接调用展示方法则会在播放器中 loading 等待广告资源加载完成后播放。

```java
if (mWindowVideoAd != null || mWindowVideoAd.isReady()){
     mWindowVideoAd.showVideo(DemoActivity.this, mWindowVideoParent);
     return;
}

if (mFullScreenVideoAd != null || mFullScreenVideoAd.isReady()){
     FullScreenVideoAd.showVideo(DemoActivity.this);
     return;
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
    
```  

### 九、视频贴片广告  

**隐藏视频贴片右上角倒计时:**  
`InStreamAdService.hideTimeProgress();`  

**隐藏视频贴片右下角“显示详情”按钮:**  
`InStreamAdService.hideDetailBtn();`  

**设置仅在Wifi环境下请求广告:**  
`InStreamAdService.enableOnlyWifi();`

>注意：上述3个方法都要在实例化贴片广告之前调用。 
 
**创建视频贴片广告:**  
```java
/**
  * 创建一个贴片广告
  * @param activity
  * @param adUnitId 广告位Id
  * @return
  */
InStreamAdService.createInStreamAd(Activity activity, String adUnitId);
```
**加载贴片广告:**  
`mAd.loadAd();` 

**贴片广告使用使用示例代码:**
```java
//请求加载一个贴片广告,需要开发者在合适的时机调用
private void loadAd(){
	 mAd = InStreamAdService.createInStreamAd(this, "1-78-93");
	 //给贴片广告添加监听
	 mAd.setAdListener(new InStreamAdListener() {   
	     @Override
	     public void onLoadedSuccess() {
	         //广告加载成功，展示到界面上
	         //开发者也可以自行决定把广告展示出来的时机
	         showAd();
	     }
	
	     @Override
	     public void onLoadFailed(int errorCode) {
	         //广告加载失败，开发者可以在此处进行自己的处理
	     }
	
	     @Override
	     public void onAdClick() {
	         //贴片广告被点击
	     }
	
	     @Override
	     public void onAdFinished() {
	         //广告播放完成，开发者可以在此处进行自己的处理
	     }
	
	     @Override
	     public void onAdError(int errorCode) {
	         //广告播放错误，开发者可以在此处进行自己的处理
	     }
	 });
	    mAd.loadAd();
}

//展示广告
private void showAd(){
   if (mAd == null){
       Log.i(TAG, "贴片广告未请求");
   }else {
       mAd.showAd(this, mAdLayout);
   }
}

@Override
protected void onResume() {
    super.onResume();
    if (mAd != null){
        mAd.onResume();
    }
}

@Override
protected void onPause() {
    super.onPause();
    if (mAd != null){
        mAd.onPause();
    }
}

@Override
protected void onDestroy() {
    super.onDestroy();
    if (mAd != null){
        mAd.onDestroy();
        mAd = null;
    }
}
```  

>注意：  
>a)  一个贴片广告只能使用一次，无法复用，如果需要再展示一个贴片广告，需要重新实例化一个 贴片广告;

>b) 开发者需要在`Activity`的`onResume()`、`onPause()`、`onDestroy()`3个方法中分别调用`InStreamAd`的`onResume()`、`onPause()`、`onDestroy()`。

 
### 十、广告展示失败时的一些错误码
  `SDKCode.CODE_UNKNOWN_ERROR = -1;//异步请求过程中发生错误
   SDKCode.CODE_HTTP_ERROR = 1; //处理http请求的过程中发生错误
   SDKCode.CODE_PRELOAD_FIALED = 2; //广告资源加载失败
   SDKCode.CODE_BAD_NETWORK = 0;//无网络或无网络权限
   SDKCode.CODE_BLANK_RESPONSE = 201 // 竞价请求失败,返回留白
   SDKCode.CODE_BACK_AMOUNT = 202; //竞价请求失败,返回返量`
    
    
    
|播放状态返回码      | 数值  |   含义          |
|:-----       |:-----:| :-------:         |
|VideoAdService.PLAYDONE| 200   |	播放完视频后用户点击了返回  |
|MVideoAdService.INSTALL_APK_DONE      | 201   |  安装了推广的app后返回  |
|VideoAdService.PLAYDONE_SKIPVIDEO| 202  |	用户跳过了广告视频 |
|VideoAdService.LAUNCH_APP| 203   |	用户点击广告打开了推广的app后返回  |
|VideoAdService.OPEN_WEB      | 206   |  用户点击广告打开了推广落地页  |
|VideoAdService.PLAYDONE_CLOSEDETAIL| 208 | 用户看完视频后在推广详情页面点击关闭视频|
|VideoAdService.CLICK_DOWNLOAD| 209 | 用户点击下载推广app|
| VideoAdService.ERROR_NO_VIDEO | 204   |	没有请求到广告内容  |
| VideoAdService.ERROR_DOWNLOAD: | 400 |视频下载中遇到错误|
| VideoAdService.ERROR_PLAYING_VIDEO      | 401   | 播放中遇到错误   |
| VideoAdService.ERROR_CLOSE_VIDEO      | 402   |  点击关闭结束了视播放了 |
| VideoAdService.ERROR_AUTO_PLAY  | 403   |  自动播放失败  |
| VideoAdService.ERROR_MANUAL_DOWNLOAD: | 404 |手动请求下载广告资源失败|

### 十一、适配Android7.0（如果不需要支持可直接跳过本段）
> 如果App需要支持Android7.0以上的设备，sdk提供了Android7.0多窗口模式和文件共享等相关特性的支持。  

**1、Activity分屏适配**
Manifest中注册SplashActivity时候，需要加上*android:resizeableActivity="false"*

**2、文件共享**
**a) 在7.0中下载安装apk文件共享必须使用FileProvider，所以需要在AndroidManifest.xml配置如下代码：**

	<!-- 配置provider用于适配7.0, authorities的{{com.sunteng.ads.sample}}部分替换成当前应用包名，
         authorities = "{{BuildConfig.APPLICATION_ID}}.fileProvider" ,
          provider_paths为创建在xml文件夹内的资源文件 -->
	<provider
        android:name="android.support.v4.content.FileProvider"
        android:authorities="com.sunteng.ads.sample.fileProvider"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/provider_paths"/>
        </provider>

> 注意：如果你的项目之前已经在AndroidManifest.xml配置过FileProvider，sdk的AdService类提供了在代码中统一配置FileProvider的接口设置authorities:

`public static void setFileProviderAuthorities(String authorities);`

**b) 可以看到在meta-data中，定义了一个资源路径，第二步就是创建res/xml/provider_paths.xml文件：**
> **注意：只需把path中{{com.sunteng.ads.sample}}部分替换成你当前项目的包名，复制到文件中即可。**

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <!--/storage/emulated/0/Android/com.sunteng.ads.sample/files/APK/-->
  	<!--把path中{{com.sunteng.ads.sample}}部分替换成项目的包名!-->
 	<external-path name="sunteng_sdk_download_apk" path="Android/data/com.sunteng.ads.sample/files/APK/"/>
</paths>
```
	
### 十二、其他
**debug模式**  

`AdService.setIsDebugModel(boolean debug);`
>传入参数为true时，为debug模式，有日志输出，默认值为true。发布正式版本时候请关闭debug模式。

**位置信息获取开关**  

 `AdService.setLocationEnable(boolean enable);`
 
>传入参数为true时，获取用户当前地理位置，默认值为true。
