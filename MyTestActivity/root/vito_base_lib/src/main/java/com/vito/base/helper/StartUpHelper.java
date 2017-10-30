package com.vito.base.helper;

/**
 * Created by lenovo on 2016/6/20.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.vito.base.utils.Const;

public class StartUpHelper {

    public static final int LMODE_NEW_INSTALL = 1; // 启动-模式,首次安装-首次启动、覆盖安装-首次启动、已安装-二次启动
    public static final int LMODE_UPDATE = 2;
    public static final int LMODE_AGAIN = 3;
    private static StartUpHelper instance;
    private boolean isOpenMarked = false;
    private int launchMode = LMODE_AGAIN; // 启动-模式
    private SharedPreferences share; // 一般信息
    private Context mContext;

    private StartUpHelper(Context ctx) {
        mContext = ctx;
        share = mContext.getSharedPreferences(Const.PREFER_NAME_GUIDE, Context.MODE_PRIVATE);
        markOpenApp();
    }

    public static StartUpHelper getInstance(Context ctx) {
        if (instance == null)
            instance = new StartUpHelper(ctx);

        return instance;
    }

    // -------启动状态------------------------------------------------------------

    // 标记-打开app,用于产生-是否首次打开
    public void markOpenApp() {
        // 防止-重复调用
        if (isOpenMarked)
            return;
        isOpenMarked = true;

        String lastVersion = share.getString(Const.KEY_FIRSTBOOT, "");
        String thisVersion = getVersionName();

        // 首次启动
        if (TextUtils.isEmpty(lastVersion)) {
            launchMode = LMODE_NEW_INSTALL;
            share.edit().putString(Const.KEY_FIRSTBOOT, thisVersion).commit();
        }
        // 更新
        else if (!thisVersion.equals(lastVersion)) {
            launchMode = LMODE_UPDATE;
            share.edit().putString(Const.KEY_FIRSTBOOT, thisVersion).commit();
        }
        // 二次启动(版本未变)
        else
            launchMode = LMODE_AGAIN;
    }

    public int getLaunchMode() {
        return launchMode;
    }

    // 首次打开,新安装、覆盖(版本号不同)
    public boolean isFirstOpen() {
        return launchMode != LMODE_AGAIN;
    }

    public int getVersion() {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getVersionName() {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
