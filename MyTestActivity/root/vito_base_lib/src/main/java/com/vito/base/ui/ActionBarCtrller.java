package com.vito.base.ui;

import android.graphics.drawable.Drawable;

/**
 * Created by pc on 2017/2/28.
 */

public class ActionBarCtrller {
    private int mActionBarBg;
    private int mLeftImgRes;
    private int mLeftTextColor;
    private int mLeftTextSize;
    private int mRightImgRes;
    private int mTitleTextColor;
    private int mTitleTextSize;

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    public static void setInstance(ActionBarCtrller instance) {
        sInstance = instance;
    }

    private Drawable mDrawable;
    private static ActionBarCtrller sInstance;

    public static ActionBarCtrller getInstance() {
        if (sInstance == null)
            sInstance = new ActionBarCtrller();
        return sInstance;
    }

    public void setmLeftImgRes(int leftImgRes) {
        mLeftImgRes = leftImgRes;
    }

    public int getActionBarBg() {
        return mActionBarBg;
    }

    public void setActionBarBg(int actionBarBg) {
        mActionBarBg = actionBarBg;
    }

    public int getLeftImgRes() {
        return mLeftImgRes;
    }

    public void setLeftImgRes(int leftImgRes) {
        mLeftImgRes = leftImgRes;
    }

    public int getLeftTextColor() {
        return mLeftTextColor;
    }

    public void setLeftTextColor(int leftTextColor) {
        mLeftTextColor = leftTextColor;
    }

    public int getLeftTextSize() {
        return mLeftTextSize;
    }

    public void setLeftTextSize(int leftTextSize) {
        mLeftTextSize = leftTextSize;
    }

    public int getRightImgRes() {
        return mRightImgRes;
    }

    public void setRightImgRes(int rightImgRes) {
        mRightImgRes = rightImgRes;
    }

    public int getTitleTextColor() {
        return mTitleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        mTitleTextColor = titleTextColor;
    }

    public int getTitleTextSize() {
        return mTitleTextSize;
    }

    public void setTitleTextSize(int titleTextSize) {
        mTitleTextSize = titleTextSize;
    }
}
