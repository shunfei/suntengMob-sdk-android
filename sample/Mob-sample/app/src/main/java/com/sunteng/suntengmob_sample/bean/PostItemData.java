/*
 * Copyright (c)  2011-2016.  SUNTENG Corporation. All rights reserved File :
 * Creation :  16-11-17 上午11:15
 * Description : PostItemData.java
 * Author : baishixian@sunteng.com
 */

package com.sunteng.suntengmob_sample.bean;

/**
 * ViewVisibility
 * Created by baishixian on 2016/11/11.
 */
public class PostItemData {

    public PostItemData(String message, int icon) {
        this.message = message;
        this.icon = icon;
    }

    private String message;
    private int icon;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
