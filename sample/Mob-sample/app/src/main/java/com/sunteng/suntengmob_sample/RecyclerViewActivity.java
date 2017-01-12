package com.sunteng.suntengmob_sample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sunteng.suntengmob_sample.adapter.NormalRecyclerViewAdapter;
import com.sunteng.suntengmob_sample.bean.PostItemData;
import com.sunteng.suntengmob_sample.listener.RecyclerViewItemClickListener;
import com.suntengmob.sdk.core.NativeAdsManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/22.
 */
public class RecyclerViewActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

    private static final String TAG = "sunteng_mob";
    private RecyclerView recyclerView;
    NormalRecyclerViewAdapter adapter;
    private ProgressDialog progressDialog;
    private NativeAdsManager mNativeAdsManager;
    ArrayList<PostItemData> dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        boolean isPreload = getIntent().getBooleanExtra("isPreload", true);


        final String adUnitId = "2-38-52";

        recyclerView = (RecyclerView)findViewById(R.id.activity_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        recyclerView.setHasFixedSize(true);
        dateList = new ArrayList<>();
        String msg = "RecyclerView小部件比ListView更高级且更具灵活性, 可通过保持有限数量的视图进行非常有效的滚动操作。";
        for (int i = 0 ; i < 100 ; i++){
            PostItemData postItemData = new PostItemData(msg, R.drawable.duzi);
            dateList.add(postItemData);
        }

        showLoading();

        // 一次性请求原生广告的数量为5
        mNativeAdsManager = new NativeAdsManager(adUnitId, 5);
        if(!isPreload){
            mNativeAdsManager.disableImageResourcePreload();
        }
        mNativeAdsManager.loadAds(new NativeAdsManager.LoadAdsListener() {
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
            adapter = new NormalRecyclerViewAdapter(this, mNativeAdsManager, dateList, this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void showLoading() {
        progressDialog = new ProgressDialog(RecyclerViewActivity.this);
        progressDialog.setTitle("请稍等～");
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
        Toast.makeText(RecyclerViewActivity.this, "click item position: " + position , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNativeAdsManager != null){
            mNativeAdsManager.release();
        }
    }

}
