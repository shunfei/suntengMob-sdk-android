/*
 * Copyright (c)  2011-2016.  SUNTENG Corporation. All rights reserved File :
 * Creation :  16-11-17 上午11:15
 * Description : NormalRecyclerViewAdapter.java
 * Author : baishixian@sunteng.com
 */

package com.sunteng.ads.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunteng.ads.R;
import com.sunteng.ads.nativead.video.MediaView;
import com.sunteng.ads.nativead.video.VideoNativeAd;
import com.sunteng.ads.nativead.video.VideoNativeAdView;
import com.sunteng.ads.nativead.video.VideoNativeAdsManager;
import com.sunteng.ads.sample.bean.PostItemData;
import com.sunteng.ads.sample.listener.RecyclerViewItemClickListener;

import java.util.ArrayList;

/**
 * ViewVisibility
 * Created by baishixian on 2016/11/11.
 */
public class VideoNativeAdRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final ArrayList<PostItemData> mPostItemData;
    private final ArrayList<VideoNativeAd> mNativeAds1;
    private final RecyclerViewItemClickListener mRecyclerViewItemClickListener;

    private int ad1DisplayFrequency = 4;

    public static final int TYPE_AD_1 = 0;
    public static final int TYPE_INFO = 1;
    private VideoNativeAdsManager mNativeAdsManager;


    public VideoNativeAdRecyclerViewAdapter(Context context, VideoNativeAdsManager videoNativeAdsManager, ArrayList<PostItemData> postItemData, RecyclerViewItemClickListener listener) {
        this.mContext = context;
        this.mPostItemData = postItemData;
        this.mRecyclerViewItemClickListener = listener;
        this.mNativeAdsManager = videoNativeAdsManager;
        this.mNativeAds1 = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {

        if (position % ad1DisplayFrequency == 0){
            return TYPE_AD_1;
        }
        return TYPE_INFO;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_INFO){
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_layout_2, parent, false);
            viewHolder = new InfoViewHolder(view);
        }else if (viewType == TYPE_AD_1){
            View view = LayoutInflater.from(mContext).inflate(R.layout.ad_video_native_layout, parent, false);
            viewHolder = new AdViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null){
            Log.e("VideoNativeAdapter", "ViewHolder is null");
            return;
        }
        if (holder instanceof InfoViewHolder){

            InfoViewHolder infoViewHolder = (InfoViewHolder)holder;
            
            //Calculate where the next postItem index is by subtracting ads we've shown.

            PostItemData postItemData = mPostItemData.get(position/ad1DisplayFrequency);

            infoViewHolder.mTextView.setText(postItemData.getMessage());
            infoViewHolder.mImageView.setImageResource(Integer.valueOf(postItemData.getIcon()));
            
        }else if (holder instanceof AdViewHolder) {

            AdViewHolder adViewHolder = (AdViewHolder) holder;

            VideoNativeAd nativeAd;

            //Calculate where the next postItem index is by subtracting ads we've shown.
            if (mNativeAds1.size() > position / ad1DisplayFrequency) {
                nativeAd = mNativeAds1.get(position / ad1DisplayFrequency);
            } else {
                nativeAd = mNativeAdsManager.nextCacheAd();
                if (nativeAd != null) {
                    nativeAd.enableAutoPlayVideo();
                    mNativeAds1.add(nativeAd);
                }

            }

            if (nativeAd == null) {
                return;
            }

            adViewHolder.mAdActionButton.setText(nativeAd.getButtonContent());
            adViewHolder.mAdBodyTextView.setText(nativeAd.getDescription());

            adViewHolder.mHeaderImageView.setImageDrawable(nativeAd.getIconImage().getDrawable());
            adViewHolder.mLogoImageView.setImageDrawable(nativeAd.getLogoImage().getDrawable());

            adViewHolder.mMediaView.setNativeAd(nativeAd);

            adViewHolder.mAdTileTextView.setText(nativeAd.getTitle());
            adViewHolder.mAdView.setTag(position);
            nativeAd.registerView((VideoNativeAdView) adViewHolder.itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mPostItemData.size() + mNativeAds1.size();
    }

    class InfoViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mTextView;

        public InfoViewHolder(View itemView) {
            super(itemView);
            final View view = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerViewItemClickListener.onClickItem(view);
                }
            });
            mImageView = (ImageView)itemView.findViewById(R.id.item_icon_img);
            mTextView = (TextView)itemView.findViewById(R.id.item_info_text);
        }
    }

    class AdViewHolder extends RecyclerView.ViewHolder {

        VideoNativeAdView mAdView;
        ImageView mHeaderImageView;
        ImageView mLogoImageView;
        MediaView mMediaView;
        TextView mAdTileTextView;
        TextView mAdBodyTextView;
        Button mAdActionButton;


        public AdViewHolder(View itemView) {
            super(itemView);
            final View view = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerViewItemClickListener.onClickItem(view);
                }
            });

            if (itemView instanceof VideoNativeAdView) {
                mAdView = (VideoNativeAdView) itemView;
            }
            mAdTileTextView = (TextView) itemView.findViewById(R.id.ad_view_title);
            mAdBodyTextView = (TextView) itemView.findViewById(R.id.ad_view_body);
            mAdActionButton = (Button) itemView.findViewById(R.id.ad_view_action_button);
            mHeaderImageView = (ImageView) itemView.findViewById(R.id.ad_view_header_image);
            mLogoImageView = (ImageView) itemView.findViewById(R.id.item_logo_img);
            mMediaView = (MediaView) itemView.findViewById(R.id.ad_mediaView);
        }
    }
}
