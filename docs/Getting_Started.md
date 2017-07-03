# SuntengMob SDK V3.1.1


## 目录
* [一、导入sdk](#一、导入sdk)
* [二、配置AndroidManifest.xml文件](#二、配置AndroidManifest.xml文件)
* [三、SDK初始化](#三、SDK初始化)
* [四、插屏广告展示](#四、插屏广告展示)
* [五、开屏广告展示](#五、开屏广告展示)
* [六、展示横幅广告(Banner)](#六、展示横幅广告(Banner))
* [七、原生广告——图文信息流(NativeAd)](#七、原生广告——图文信息流(NativeAd))
* [八、原生广告——视频信息流(VideoNativeAd)](#八、原生广告——视频信息流(VideoNativeAd))
* [九、原生视频广告](#九、原生视频广告)
* [十、视频贴片广告](#十、视频贴片广告)
* [十一、广告展示失败时的一些错误码](#十一、广告展示失败时的一些错误码)
* [十二、适配Android7.0](#十二、适配Android7.0)
* [十三、其他](#十三、其他)
	* [设置Debug模式](#设置Debug模式)
	* [配置SDK的混淆规则](#配置SDK的混淆规则)
	* [位置信息获取开关](#位置信息获取开关)
	* [清除广告缓存文件](#清除广告缓存文件)

<div id="一、导入sdk" />

### 一、导入sdk 
    将sdk解压后的libs目录下的jar文件导入到工程指定的libs目录

<div id="二、配置AndroidManifest.xml文件" />

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
<activity android:name="com.sunteng.ads.video.core.VideoActivity"
              android:configChanges="keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize"
              tools:ignore="UnusedAttribute"
              android:theme="@android:style/Theme.NoTitleBar" />
<!-- 以上是申明Activity：-->
```

<div id="三、SDK初始化" />

### 三、SDK初始化 

#### **调用初始化：**

在主 Activity 的 `onCreate()` 中调用下面静态方法：
        
`AdService.init(Activity activity, String appSecret);`
         
 >**注意:**
 > appSecret 需从 sunteng 获取


<div id="四、插屏广告展示" />

### 四、插屏广告展示

```java
/**
 * 请求插屏广告示例代码
 * adUnitID 从官网获取(测试广告位 2-38-43)
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
      // 插屏请求未完成
    }
}  

/*插屏广告监听器*/
 InterstitialListener interstitialListener = new InterstitialListener() {
    @Override
    public void onAdLoaded() {
       // 广告加载完成会回调onReceiveAd() 此时可调用showAd()进行广告展示
    }

    @Override
    public void onAdShowFailed(int errorCode) {
        //当插屏广告展示失败
        //errorCode 是错误码，具体原因请查阅下文的失败错误码。
    }

    @Override
    public void onAdShowSuccess() {
        //当插屏广告展现
    }

    @Override
    public void onAdClose() {
        //当用户点击关闭广告或在广告界面按下back键
    }

    @Override
    public void onAdClick() {
        //当用户点击广告
    }
};
```
           
 > **注意:**
 `InterstitialListener` 中的` onAdShowFailed(int code)`，code是错误码，具体原因请查阅下文的失败错误码。

<div id="五、开屏广告展示" />

### 五、开屏广告展示

开屏广告用于 App 启动页，开发者需为开屏广告提供一个父布局容器，可自由控制开屏广告的展示区域和尺寸。

```java    
/**
* 请求加载开屏广告示例代码
*/
SplashAd mSplashAd = null;
String adUnitId = "2-38-48"; // 测试广告位
private void loadSplash(){
  mSplashAd = new SplashAd(adUnitId);
  mSplashAd.setAdListener(new SplashAdListener() {
      @Override
      public void onAdLoaded() { 
           // 开屏加载完成
      }
      
      @Override
      public void onFailed(int errorCode) {
          // 开屏加载失败
      }

      @Override
      public void onAdShowSuccess() {
          // 开屏展示完成
      }

      @Override
      public void onAdClose() {
          // 开屏被关闭
      }

      @Override
      public void onAdClick() {
          // 开屏被点击
      }
  });
  
  // 第一次可以调用 loadAd() 方法主动请求广告
  // 开屏广告也具有自动预缓存功能
  // 展示广告前可使用 isReady() 方法判断广告是否准备好
  mSplashAd.loadAd() 
}
 
 /**
  * 展示开屏广告的方法
  * viewGroup 提供给开屏广告展示的父布局容器
  */
  mSplashAd.showAd(ViewGroup viewGroup)
  
/**
 * 展示开屏广告示例代码
 * 开屏广告如果 isReady() 判断为 false 时会自动缓存广告资源等待被下次调用
 */
  private void showSplash(){
        if (mSplashAd != null && mSplashAd.isReady()){
            mSplashAd.showAd(mSplashParent);
            mSplashParent.setVisibility(View.VISIBLE);
        }else {
            // 开屏未请求完成
        }
  }
```
> **注意:** 开屏广告展示完成之后会自动缓存下一次广告内容，SDK 也提供 `loadAd()` 作为主动请求广告的方法


<div id="六、展示横幅广告(Banner)" /> 

### 六、展示横幅广告(Banner)

为方便开发者自定义横幅广告尺寸，提供了不同的 BannerAdView 的构造器。

```java
/**
 * 默认尺寸构造器（Banner 的宽高都是 WRAP_CONTENT 效果）
 * @param context 上下文
 * @param adUnitID 广告位Id 
 */
public BannerAdView(Context context, String adUnitID)  
  
/**
 * 自定义尺寸构造器
 * @param context 上下文
 * @param adUnitID 广告位Id 
 * @param width 广告的宽度
 * @param height 广告的高度 (宽度和高度计量单位为px)
 */
 public BannerAdView(Context context, String adUnitID, int width, int height)
```

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
            // 展示成功
         }
         
         @Override
         public void onAdClose(BannerAd ad) {
             // 关闭成功
         }
         
         @Override
         public void onAdClick(BannerAd ad) {
            // 广告被点击
         }
         
         @Override
         public void onAdShowFailed(int errorCode, BannerAd ad) {
             // 广告展示失败
          }
     });
     //将banner广告添加到父容器
     addContentView(bannerAdView, layoutParams);
  }
```

> **注意:**
 
> `BannerAdView` 构造方法中的4个参数分别为：上下文参数context，广告位Id，广告的宽度，广告的高度，宽度和高度计量单位为px。
 
> 处于展示中的 Banner 具有自动刷新内容的功能，刷新的启用和时间间隔可在 SSP 管理后台中操作。
 
<div id="七、原生广告——图文信息流(NativeAd)" /> 

### 七、原生广告——图文信息流(NativeAd)

**1、加载单个原生广告**
    
原生广告（信息流广告）是内嵌于 App 原生内容中的广告形式，我们提供了 NativeAdView 布局让开发者可以快速自定义展示内容。
    

> NativeAdView 是 FrameLayout 的子类，作为展示原生广告的父布局容器，开发者可以定义 NativeAdView 布局中的元素类型和排列方式，展示内容从广告请求成功后的 NativeAd 中获取。

> NativeAd 包括 Title（标题文本）, Description（描述文本）, ButtonContent（引导按钮文本）, IconImage（Icon图片）, LogoImage（Logo图片）, Images（1-3多张图片展示素材）
    
```java    
/**
* 示例代码：
* 实例化一个NativeAd，并调用loadAd()方法发起广告请求
*/
 final String adUnitId = "2-38-90";//广告位id
 NativeAd ad = new NativeAd(adUnitId);
 //ad.disableImageResourcePreload(); // 设置图片资源是否预加载，设置后可利用第三方图片加载库 load 图片 url
 ad.setNativeAdListener(new NativeAdListener() {
     @Override
     public void onReceiveAd(NativeAd ad) {
         // Native广告加载完成
         showNativeAdView(ad);
     }

     @Override
     public void onFailed(NativeAd ad, int code) {
         switch (code){
             case SDKCode.CODE_BACK_AMOUNT:
                 // 原生广告返量
             case SDKCode.CODE_BLANK_RESPONSE:
                 // 原生广告留白
                 break;
             default:
                // 原生广告展示失败
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
**注意：SDK 默认会预下载广告中的用到的资源图片，可以关闭该功能使用第三方图片加载库实现。** 
>a) 调用 `ad.disableImageResourcePreload()` 可以关闭图片预加载。调用该方法后，`NativeAd.Image`或者其子类中的`getDrawabl()`方法将会返回一个空值。此时展示`NativeAd`的图片，需调用`getUrl()`方法获取图片的URL，然后自行加载（可利用第三方图片加载库完成，如 Glide 或 Fresco 等）

>b) 示例代码中的原生广告布局容器`R.layout.ad_native_layout`是`com.sunteng.ads.nativead.core.NativeAdView`包裹的自定义布局，开发者可以自定义布局中的内容。**`NativeAdView`是`FrameLayout`的子类;**

>c) `NativeAdView`的数据填充完毕之后，需要调用`ad.registerView(nativeAdView);`方法,否则SDK无法统计到用户的行为。

**2、加载多个原生广告**

加载多个原生广告适合一次缓存多个广告的场景，适合在 ListView 或 RecyclerView 等列表式布局中使用。

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
 

<div id="八、原生广告——视频信息流(VideoNativeAd)" /> 

### 八、原生广告——视频信息流(VideoNativeAd)

#### **展示单个视频信息流广告**

原生广告（信息流视频广告）是将视频广告内容内嵌于 App 原生内容中的广告形式，我们提供了 VideoNativeAdView 布局让开发者可以快速自定义展示内容。视频播放组件内置于 MadieView 中，播放控制依赖于 SDK 内部完成。
    

> VideoNativeAdView 是 FrameLayout 的子类，作为展示原生广告的父布局容器，开发者可以定义 VideoNativeAdView 布局中的元素类型和排列方式，展示内容从广告请求成功后的 VideoNativeAd 实例中获取。

> VideoNativeAd 包括 Title（标题文本）, Description（描述文本）, ButtonContent（引导按钮文本）, IconImage（Icon图片）, LogoImage（Logo图片）, 视频展示素材



```java
// 请求单个视频信息流广告
 String adUnitId = "2-43-82"; //测试广告位id
 VideoNativeAd videoNativeAd = new VideoNativeAd(adUnitId);
 videoNativeAd.setNativeAdListener(new VideoNativeAdListener() {
            @Override
            public void onDisplayAd(VideoNativeAd ad) {
                // 视频信息流广告曝光
            }

            @Override
            public void onClickAd(VideoNativeAd ad) {
                // 视频信息流广告被点击            
            }

            @Override
            public void onReceiveAd(VideoNativeAd ad) {
                // 视频信息流广告加载完成，可以展示
                showVideoNativeAdView(ad);
            }

            @Override
            public void onFailed(VideoNativeAd ad, int code) {
                // 广告展示失败后会回调，code 对应下面的业务错误码表格
            }
        });
 // 请求广告
 videoNativeAd.loadAd();
 
 
 // 展示视频信息流广告
 void showVideoNativeAdView(VideoNativeAd videoNativeAd) {
 
 			// 判断广告素材资源的可用性
			if(!videoNativeAd.isReady()){
		    // 广告未加载完成，重新请求
			}
			// 检查广告是否过期
			if(videoNativeAd.checkAdIsExpire()){
			   // 广告过期，重新请求
			}
			
        // 获取自定义的信息流视频布局 VideoNativeAdView
        VideoNativeAdView nativeAdView = (VideoNativeAdView) LayoutInflater.from(this).inflate(R.layout.ad_video_native_layout, null);
        
        // 获取自定义展示内容的布局控件
        // 展示标题内容的 TextView
        TextView titleView = (TextView)nativeAdView.findViewById(com.sunteng.ads.nativead.video.R.id.ad_view_title);
        // 展示描述文本的 TextView
        TextView descriptionView = (TextView)nativeAdView.findViewById(com.sunteng.ads.nativead.video.R.id.ad_view_body);
        // 展示指定动作的 Button
        Button actionButton = (Button)nativeAdView.findViewById(com.sunteng.ads.nativead.video.R.id.ad_view_action_button);
        // 展示图标的 ImageView
        ImageView iconView = (ImageView)nativeAdView.findViewById(com.sunteng.ads.nativead.video.R.id.ad_view_header_image);
        // 展示视频素材的 MediaView
        MediaView mediaView = (MediaView)nativeAdView.findViewById(com.sunteng.ads.nativead.video.R.id.ad_mediaView);
        // 展示logo的 ImageView
        ImageView logoView = (ImageView)nativeAdView.findViewById(com.sunteng.ads.nativead.video.R.id.item_logo_img);

	    // 使用广告请求成功后的 VideoNativeAd 实例获取广告填充内容
	    // 填充文本内容
        titleView.setText(videoNativeAd.getTitle());
        descriptionView.setText(videoNativeAd.getDescription());
        actionButton.setText(videoNativeAd.getButtonContent());
        
       // 填充视频内容
        mediaView.setNativeAd(videoNativeAd);

       // 填充图像内容
        VideoNativeAd.Image icon_image = videoNativeAd.getIconImage();
        iconView.setImageDrawable(icon_image.getDrawable());

        VideoNativeAd.Image logo_image = videoNativeAd.getLogoImage();
        logoView.setImageDrawable(logo_image.getDrawable());
        
        // 注册填充好内容的 VideoNativeAdView 到 VideoNativeAd 实例
        videoNativeAd.registerView(nativeAdView);
    }
```

> 为方便开发者对视频播放器的控制需求，SDK 提供如下接口控制播放器相关属性。

#### **广告视频内容是否自适应（默认开启，全局有效）**

```java
/**
 * 取消广告视频内容自适应（避免拉伸内容）
 * 视频内容可能会无法铺满整个播放器
 */
 VideoNativeAd.disableVideoContentAdaptive();
 
/**
 * 设置广告视频内容自适应，视频画面铺满整个播放器
 * 默认开启
 */
 VideoNativeAd.enableVideoContentAdaptive();
```
 
#### **设置广告视频静音播放功能（默认关闭，各广告位独立控制）**

```java
/**
 * 启用视频静音播放功能
 */
VideoNativeAd videoNativeAd.enableMutePlayVideo();

/**
 * 禁用视频静音播放功能
 */
VideoNativeAd videoNativeAd.disableMutePlayVideo();
```

#### **设置广告视频自动播放功能（默认关闭，各广告位独立控制）**

```java
/**
 * 启用视频自动播放功能
 */
VideoNativeAd videoNativeAd.enableAutoPlayVideo();

/**
 * 禁用视频自动播放功能
 * 默认禁用
 */
VideoNativeAd videoNativeAd.disableAutoPlayVideo();

```

> **自动播放控制逻辑：**
> 
> **1.视图可见性定义：**视图处于 VISIBLE 状态且未被其他 view 覆盖，未处于锁屏、系统控制栏下拉、弹窗状态下。
> 
> 2.当包含了播放器视图（MediaView）的页面布局创建完成或是被动态添加到页面布局中时，视图处于可见状态时开始自动播放。
> 
> 3.对于ScrollView 、ListView 、RecyclerView 等滑动布局中，当播放器视图（MediaView）全部滑出屏幕完全不可见时视频自动暂停，视图滑入屏幕且可见区域占比为超高一半时开始自动播放。

#### **2、加载多个视频信息流广告**

加载多个视频信息流广告适合一次缓存多个广告的场景，适合在 ListView 或 RecyclerView 等列表式布局中使用。

> SDK 内部完成了创建多个 VideoNativeAd 实例，分别进行广告请求，存放在一个缓存队列中，开发者可以从队列中取出缓存的广告实例。开发者也也可以自行实现。

```java
// 设置广告位 ID 和缓存广告队列的数量
VideoNativeAdsManager mNativeAdsManager = new VideoNativeAdsManager(adUnitId, 10);

// 请求广告
mNativeAdsManager.loadAds(new VideoNativeAdsManager.LoadAdsListener() {
            @Override
            public void onLoadedAds(String adUnitId, int failedCount) {
            // 请求完成的回调中会返回请求失败的数量
                Log.e(TAG,"onFailedAds count = " + failedCount);
            }
        });
        
// 请求成功后，获取缓存队列中的 VideoNativeAd 实例       
VideoNativeAd videoNativeAd = mNativeAdsManager.nextCacheAd();

// 判断广告素材资源的可用性
videoNativeAd.isReady();
// 检查广告是否过期
videoNativeAd.checkAdIsExpire();
...

 // 退出时也可以释放缓存在队列中的广告实例
 mNativeAdsManager.release();
        
```
 具体使用可参考 Samlpe 中的基于 RecyclerView 实现的代码示例。
 

<div id="九、原生视频广告" /> 

### 九、原生视频广告

**设置视频广告在播放中是否可关闭**

  
 ```java 
 VideoAdService.setCloseVideoEnable(boolean closeEnable); 
 ```

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

<div id="十、视频贴片广告" /> 

### 十、视频贴片广告  

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

<div id="十一、广告展示失败时的一些错误码" /> 

### 十一、广告展示失败时的一些错误码
 
```java
  SDKCode.CODE_UNKNOWN_ERROR = -1;//异步请求过程中发生错误
  SDKCode.CODE_HTTP_ERROR = 1; //处理http请求的过程中发生错误
  SDKCode.CODE_PRELOAD_FIALED = 2; //广告资源加载失败
  SDKCode.CODE_BAD_NETWORK = 0;//无网络或无网络权限
  SDKCode.CODE_BLANK_RESPONSE = 201 // 竞价请求失败,返回留白    
  SDKCode.CODE_BACK_AMOUNT = 202; //竞价请求失败,返回返量
```    
 
 
    
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


<div id="十二、适配Android7.0" />

### 十二、适配Android7.0（如果不需要支持可直接跳过本段）
> 如果App需要支持Android7.0以上的设备，sdk提供了Android7.0多窗口模式和文件共享等相关特性的支持。  

**1、Activity分屏适配**
Manifest中注册SplashActivity时候，需要加上*android:resizeableActivity="false"*

**2、文件共享**
**a) 在7.0中下载安装apk文件共享必须使用FileProvider，所以需要在AndroidManifest.xml配置如下代码：**

```xml
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
```

> 注意：如果你的项目之前已经在AndroidManifest.xml配置过FileProvider，sdk的AdService类提供了在代码中统一配置FileProvider的接口设置authorities:

```java
 public static void setFileProviderAuthorities(String authorities);

```

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

<div id="十三、其他" />	

### 十三、其他

<div id="设置Debug模式" />	

#### **设置Debug模式**  

`AdService.setIsDebugModel(boolean debug);`
>传入参数为true时，为debug模式，有日志输出，默认值为true。发布正式版本时候请关闭debug模式。

<div id="配置SDK的混淆规则" />	

#### **配置SDK的混淆规则**

> 注意： SDK 支持的系统版本 API 需大于 14，因此需要在 AndroidManifest.xml 文件中指定了 android:targetSdkVersion>=14。如果需要兼容 Android 7.0 以上平台，请务必参照上面所述的 7.0 适配指导要求。

**混淆要求：** 请在打包 apk 在混淆文件中加入如下混淆规则配置：

```
-keep class com.sunteng.** { *; }
-keep interface com.sunteng.** { *; }
-dontwarn com.sunteng.**
```

<div id="位置信息获取开关" />	

#### **位置信息获取开关**  

 `AdService.setLocationEnable(boolean enable);`
 
>传入参数为 true 时，获取用户当前地理位置，默认值为 true 。

<div id="清除广告缓存文件" />	

#### **清除广告缓存文件**
 
 `AdServices.releaseAllAdResource();`

广告每次请求加载会产生一定量的资源缓存文件，开发者可以在闲时进行统一清理缓存（比如程序退出时，每次广告服务初始化前等），应避免在有广告展现的情况下清除资源。 



