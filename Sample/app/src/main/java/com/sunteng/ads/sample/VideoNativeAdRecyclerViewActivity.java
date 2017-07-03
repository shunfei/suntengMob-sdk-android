package com.sunteng.ads.sample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sunteng.ads.R;
import com.sunteng.ads.nativead.video.VideoNativeAdsManager;
import com.sunteng.ads.sample.adapter.VideoNativeAdRecyclerViewAdapter;
import com.sunteng.ads.sample.bean.PostItemData;
import com.sunteng.ads.sample.listener.RecyclerViewItemClickListener;

import java.util.ArrayList;

/**
 * Created by baishixian on 2016/11/22.
 */
public class VideoNativeAdRecyclerViewActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

    private static final String TAG = "SuntengSdk";
    private RecyclerView recyclerView;
    VideoNativeAdRecyclerViewAdapter adapter;
    private ProgressDialog progressDialog;
    private VideoNativeAdsManager mNativeAdsManager;
    ArrayList<PostItemData> dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        String adUnitId = getIntent().getStringExtra("AdUnitId");
        if (TextUtils.isEmpty(adUnitId)){
            adUnitId = "2-38-183";
        }

        recyclerView = (RecyclerView)findViewById(R.id.activity_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //这里用线性显示 类似于listview
        recyclerView.setHasFixedSize(true);
        dateList = new ArrayList<>();
        String msg = "这是非广告内容的示例数据填充";
        for (int i = 0 ; i < 100 ; i++){
            PostItemData postItemData = new PostItemData(msg, R.drawable.bear);
            dateList.add(postItemData);
        }

        showLoading();

        // 一次性请求视频信息流广告的数量为10
        mNativeAdsManager = new VideoNativeAdsManager(adUnitId, 10);
        mNativeAdsManager.loadAds(new VideoNativeAdsManager.LoadAdsListener() {
            @Override
            public void onLoadedAds(String adUnitId, int failedCount) {
                Log.e(TAG,"onFailedAds count = " + failedCount);
                dismiss();
                showContents();
            }
        });

    }

    private void showContents(){
        if (mNativeAdsManager != null && !dateList.isEmpty()){
            adapter = new VideoNativeAdRecyclerViewAdapter(this, mNativeAdsManager, dateList, this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void showLoading() {
        progressDialog = new ProgressDialog(VideoNativeAdRecyclerViewActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    private void dismiss() {
        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClickItem(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        Toast.makeText(VideoNativeAdRecyclerViewActivity.this, "click item position: " + position , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNativeAdsManager != null){
            mNativeAdsManager.release();
        }
    }

}
