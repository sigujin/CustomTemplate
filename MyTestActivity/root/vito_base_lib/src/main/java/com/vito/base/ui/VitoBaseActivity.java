package com.vito.base.ui;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.vito.base.R;
import com.vito.base.helper.SystemBarTintManager;
import com.vito.base.utils.network.jsonpaser.JsonLoader;

public class VitoBaseActivity extends FragmentActivity {
    SystemBarTintManager mSystemBarTintManager;
    public TextView mTextJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vito_base);
        mTextJump = (TextView) findViewById(R.id.tv_jump);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            mSystemBarTintManager = new SystemBarTintManager(this);
            mSystemBarTintManager.setStatusBarTintEnabled(true);
//            mSystemBarTintManager.setStatusBarTintResource(R.color.sys_statusbar_bg_color);//通知栏所需颜色
        }
        initActionBar();
    }

    public void setStatusBarTintResource(int in_rid) {
        if (mSystemBarTintManager != null)
            mSystemBarTintManager.setStatusBarTintResource(in_rid);//通知栏所需颜色
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (AsyncTask task : JsonLoader.sTaskSet) {
            task.cancel(true);
        }
        JsonLoader.sTaskSet.clear();
    }

    public void initActionBar() {
    }
}
