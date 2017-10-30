package com.vito.base;

import android.app.Application;

import com.vito.base.utils.ToastShow;

import org.xutils.x;

/**
 * Created by pc on 2016/11/23.
 */

public class VitoBaseLib {
    /**
     * 初始化一切 / 在Application的onCreate里调用
     *
     */
    public static void init(Application application) {
        ToastShow.init(application);
        x.Ext.init(application);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
    }
}
