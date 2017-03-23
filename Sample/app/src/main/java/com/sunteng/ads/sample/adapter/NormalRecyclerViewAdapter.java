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

import com.bumptech.glide.Glide;
import com.sunteng.ads.nativead.NativeAdView;
import com.sunteng.ads.nativead.NativeAdsManager;
import com.sunteng.ads.nativead.core.NativeAd;
import com.sunteng.ads.R;
import com.sunteng.ads.sample.bean.PostItemData;
import com.sunteng.ads.sample.listener.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewVisibility
 * Created by baishixian on 2016/11/11.
 */
public class NormalRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final ArrayList<PostItemData> mPostItemDatas;
    private final ArrayList<NativeAd> mNativeAds1;
    private final ArrayList<NativeAd> mNativeAds2;
    private final RecyclerViewItemClickListener mRecyclerViewItemClickListener;

    private int ad1DisplayFrequency = 3;
    private int ad2DisplayFrequency = 5;

    public static final int TYPE_AD_1 = 0;
    public static final int TYPE_AD_2 = 1;
    public static final int TYPE_INFO = 2;
    private NativeAdsManager mNativeAdsManager;


    public NormalRecyclerViewAdapter(Context context, NativeAdsManager nativeAdsManager, ArrayList<PostItemData> postItemData, RecyclerViewItemClickListener listener) {
        this.mContext = context;
        this.mPostItemDatas = postItemData;
        this.mRecyclerViewItemClickListener = listener;
        this.mNativeAdsManager = nativeAdsManager;
        this.mNativeAds1 = new ArrayList<>();
        this.mNativeAds2 = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {

        if (position % ad1DisplayFrequency == 0){
            return TYPE_AD_1;
        }

        if (position % ad2DisplayFrequency == 0){
            return TYPE_AD_2;
        }

        return TYPE_INFO;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_INFO){
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_layout, parent, false);
            viewHolder = new InfoViewHolder(view);
        }else if (viewType == TYPE_AD_1){
            View view = LayoutInflater.from(mContext).inflate(R.layout.ad_item_layout, parent, false);
            viewHolder = new AdViewHolder(view);
        }else if (viewType == TYPE_AD_2){
            View view = LayoutInflater.from(mContext).inflate(R.layout.ad_item_layout_2, parent, false);
            viewHolder = new AdViewHolder2(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null){
            Log.e("NormalRecyclerView", "ViewHolder is null");
            return;
        }
        if (holder instanceof InfoViewHolder){

            InfoViewHolder infoViewHolder = (InfoViewHolder)holder;
            
            //Calculate where the next postItem index is by subtracting ads we've shown.

            PostItemData postItemData = mPostItemDatas.get(position);

            infoViewHolder.mTextView.setText(postItemData.getMessage());
            infoViewHolder.mImageView.setImageResource(Integer.valueOf(postItemData.getIcon()));
            
        }else if (holder instanceof AdViewHolder){
            
            AdViewHolder adViewHolder = (AdViewHolder)holder;

            NativeAd nativeAd;
            
            //Calculate where the next postItem index is by subtracting ads we've shown.
            if (mNativeAds1.size() > position / ad1DisplayFrequency) {
                nativeAd = mNativeAds1.get(position / ad1DisplayFrequency);
            } else {
                nativeAd = mNativeAdsManager.nextCacheAd();
                if (nativeAd != null){
                    mNativeAds1.add(nativeAd);
                }

            }

            if (nativeAd == null){
                return;
            }
            
            adViewHolder.mAdActionButton.setText(nativeAd.getButtonContent());
            adViewHolder.mAdBodyTextView.setText(nativeAd.getDescription());

            if (nativeAd.isPreloadImageResource()){
                adViewHolder.mAdImageView.setImageDrawable(nativeAd.getImages().get(0).getDrawable());
                adViewHolder.mHeaderImageView.setImageDrawable(nativeAd.getIconImage().getDrawable());
                adViewHolder.mLogoImageView.setImageDrawable(nativeAd.getLogoImage().getDrawable());
            }else {
                Glide.with(mContext)
                        .load(nativeAd.getImages().get(0).getUrl())
                        .centerCrop()
                        .placeholder(R.drawable.loading)
                        .crossFade()
                        .into(adViewHolder.mAdImageView);

                Glide.with(mContext)
                        .load(nativeAd.getIconImage().getUrl())
                        .centerCrop()
                        .placeholder(R.drawable.loading)
                        .crossFade()
                        .into(adViewHolder.mHeaderImageView);

                Glide.with(mContext)
                        .load(nativeAd.getLogoImage().getUrl())
                        .centerCrop()
                        .placeholder(R.drawable.loading)
                        .crossFade()
                        .into(adViewHolder.mLogoImageView);
            }


            adViewHolder.mAdTileTextView.setText(nativeAd.getTitle());
            adViewHolder.mAdView.setTag(position);
            nativeAd.registerView((NativeAdView)adViewHolder.itemView);
        }
        else if (holder instanceof AdViewHolder2){

            AdViewHolder2 adViewHolder = (AdViewHolder2)holder;

            NativeAd nativeAd;

            //Calculate where the next postItem index is by subtracting ads we've shown.
            if (mNativeAds2.size() > position / ad2DisplayFrequency) {
                nativeAd = mNativeAds2.get(position / ad2DisplayFrequency);
            } else {
                nativeAd = mNativeAdsManager.nextCacheAd();
                if (nativeAd != null){
                    mNativeAds2.add(nativeAd);
                }
            }

            if (nativeAd == null){
                return;
            }

            List<NativeAd.Image> images = nativeAd.getImages();

            adViewHolder.mHeaderImageView.setImageDrawable(nativeAd.getIconImage().getDrawable());
            adViewHolder.mLogoImageView.setImageDrawable(nativeAd.getLogoImage().getDrawable());

            if (nativeAd.isPreloadImageResource()){
                if (!images.isEmpty()) {
                    adViewHolder.mAdImageView1.setImageDrawable(images.get(0).getDrawable());

                    if (images.size() >= 2){
                        adViewHolder.mAdImageView2.setImageDrawable(images.get(1).getDrawable());
                    }

                    if (images.size() >= 3){
                        adViewHolder.mAdImageView3.setImageDrawable(images.get(2).getDrawable());
                    }
                }
                adViewHolder.mHeaderImageView.setImageDrawable(nativeAd.getIconImage().getDrawable());
                adViewHolder.mLogoImageView.setImageDrawable(nativeAd.getLogoImage().getDrawable());

            }else {
                if (!images.isEmpty()) {
                    Glide.with(mContext)
                            .load(nativeAd.getImages().get(0).getUrl())
                            .centerCrop()
                            .placeholder(R.drawable.loading)
                            .crossFade()
                            .into(adViewHolder.mAdImageView1);

                    if (images.size() >= 2){
                        Glide.with(mContext)
                                .load(nativeAd.getImages().get(1).getUrl())
                                .centerCrop()
                                .placeholder(R.drawable.loading)
                                .crossFade()
                                .into(adViewHolder.mAdImageView2);

                    }

                    if (images.size() >= 3){
                        Glide.with(mContext)
                                .load(nativeAd.getImages().get(2).getUrl())
                                .centerCrop()
                                .placeholder(R.drawable.loading)
                                .crossFade()
                                .into(adViewHolder.mAdImageView3);
                    }
                }

                Glide.with(mContext)
                        .load(nativeAd.getIconImage().getUrl())
                        .centerCrop()
                        .placeholder(R.drawable.loading)
                        .crossFade()
                        .into(adViewHolder.mHeaderImageView);

                Glide.with(mContext)
                        .load(nativeAd.getLogoImage().getUrl())
                        .centerCrop()
                        .placeholder(R.drawable.loading)
                        .crossFade()
                        .into(adViewHolder.mLogoImageView);
            }

            adViewHolder.mAdBodyTextView.setText(nativeAd.getDescription());
            adViewHolder.mAdTileTextView.setText(nativeAd.getTitle());
            adViewHolder.mAdView.setTag(position);

            nativeAd.registerView((NativeAdView)adViewHolder.itemView);
        }

    }

    @Override
    public int getItemCount() {
        return mPostItemDatas.size() + mNativeAds1.size() + mNativeAds2.size();
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

    class AdViewHolder extends RecyclerView.ViewHolder{

        NativeAdView mAdView;
        ImageView mHeaderImageView;
        ImageView mLogoImageView;
        ImageView mAdImageView;
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

            if (itemView instanceof NativeAdView){
                mAdView = (NativeAdView)itemView;
            }
            mAdTileTextView = (TextView) itemView.findViewById(R.id.ad_view_title);
            mAdBodyTextView = (TextView) itemView.findViewById(R.id.ad_view_body);
            mAdImageView = (ImageView) itemView.findViewById(R.id.ad_view_image);
            mAdActionButton = (Button) itemView.findViewById(R.id.ad_view_action_button);
            mHeaderImageView = (ImageView) itemView.findViewById(R.id.ad_view_header_image);
            mLogoImageView = (ImageView) itemView.findViewById(R.id.item_logo_img);
        }
    }

    class AdViewHolder2 extends RecyclerView.ViewHolder{

        NativeAdView mAdView;
        ImageView mHeaderImageView;
        ImageView mLogoImageView;
        ImageView mAdImageView1;
        ImageView mAdImageView2;
        ImageView mAdImageView3;
        TextView mAdTileTextView;
        TextView mAdBodyTextView;


        public AdViewHolder2(View itemView) {
            super(itemView);
            final View view = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerViewItemClickListener.onClickItem(view);
                }
            });

            if (itemView instanceof NativeAdView){
                mAdView = (NativeAdView)itemView;
            }
            mAdTileTextView = (TextView) itemView.findViewById(R.id.ad_view_title);
            mAdBodyTextView = (TextView) itemView.findViewById(R.id.ad_view_body);
            mAdImageView1 = (ImageView) itemView.findViewById(R.id.ad_view_image_1);
            mAdImageView2 = (ImageView) itemView.findViewById(R.id.ad_view_image_2);
            mAdImageView3 = (ImageView) itemView.findViewById(R.id.ad_view_image_3);
            mHeaderImageView = (ImageView) itemView.findViewById(R.id.ad_view_header_image);
            mLogoImageView = (ImageView) itemView.findViewById(R.id.item_logo_img);
        }
    }
}
