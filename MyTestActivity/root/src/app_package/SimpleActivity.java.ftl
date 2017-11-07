package  ${packageName};

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import ${packageName}.account.LoginInfo;
import ${packageName}.account.LoginResult;
import ${packageName}.fragment.LoginFragment;
import com.vito.base.helper.StartUpHelper;
import com.vito.base.ui.ActionBarCtrller;
import com.vito.base.ui.VitoBaseActivity;
import com.vito.base.ui.fragments.BaseFragment;
import com.vito.base.ui.fragments.FragmentFactory;
import com.vito.base.update.UpdateConfig;
import com.vito.base.update.UpdateService;
import com.vito.base.utils.ToastShow;
import com.vito.base.utils.network.httplibslc.JsonLoaderCallBack;
import ${packageName}.fragment.MainActivityFragment;

public class ${activityClass} extends VitoBaseActivity implements JsonLoaderCallBack, BaseFragment.BackHandledInterface{
   
    boolean mFragmentInited = false;
    public UpdateService mService = null;
    private BaseFragment mCurrentFragment = null;
    private int mBackKeyPressedTimes = 0;
    boolean mIsLogin = false;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // 已经绑定了LocalService，强转IBinder对象，调用方法得到LocalService对象
            UpdateService.LocalBinder binder = (UpdateService.LocalBinder) service;
            mService = binder.getService();
            mService.setActivity(MainActivity.this);
            mService.startUpdate(0);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 登录配置*/
        LoginInfo.getInstance().init(this);
        /* 升级服务配置 */
        UpdateConfig.getInstance().setBaseUrl("http://wy.bkvito.com/");
        UpdateConfig.getInstance().setSysType("qxjz");
        UpdateConfig.getInstance().setGetUpdateURL("http://wy.bkvito.com/base/apk/apkManage/checkUpApk.htm");
        initFragment();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // 绑定Service，绑定后就会调用mConnetion里的onServiceConnected方法
        Intent intent = new Intent(this, UpdateService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
    }

    private void initFragment() {
        mTextJump.setVisibility(View.GONE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(com.vito.base.R.id.rootfragcontent).setBackgroundResource(android.R.color.white);
                getWindow().setBackgroundDrawableResource(android.R.color.white);
                setStatusBarTintResource(R.color.green07);
            }
        });

        Fragment newFragment = null;
        boolean firstBoot = StartUpHelper.getInstance(this).isFirstOpen();
        if (LoginResult.getInstance().isImIsLoginOK())
            newFragment = FragmentFactory.getInstance().createFragment(MainActivityFragment.class);
        else
            newFragment = FragmentFactory.getInstance().createFragment(LoginFragment.class);

        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (R.id.rootfragcontent != 0) {
            transaction.replace(R.id.rootfragcontent, newFragment);
            transaction.commitAllowingStateLoss();
        }

        mFragmentInited = true;
//        checkPermission(this);
    }

    @Override
    public void initActionBar() {
        super.initActionBar();
        ActionBarCtrller.getInstance().setActionBarBg(getResources().getColor(R.color.actionbar_color_white));
        ActionBarCtrller.getInstance().setLeftImgRes(R.drawable.jz_07);
        ActionBarCtrller.getInstance().setTitleTextSize(getResources().getDimensionPixelSize(R.dimen.text_middle_l));
        ActionBarCtrller.getInstance().setLeftTextSize(getResources().getDimensionPixelSize(R.dimen.text_middle_m));
        ActionBarCtrller.getInstance().setTitleTextColor(getResources().getColor(R.color.actionbar_title_text_color));
        ActionBarCtrller.getInstance().setLeftTextColor(getResources().getColor(R.color.actionbar_left_text_color));
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        mCurrentFragment = selectedFragment;
        if (!(mCurrentFragment instanceof LoginFragment))
            mIsLogin = false;
    }

    @Override
    public void onJsonDataGetSuccess(Object re_data, int reqcode) {

    }

    @Override
    public void onJsonDataGetFailed(int re_code, String re_info, int reqcode) {

    }

    public void onBackPressed() {
        if (!mFragmentInited)
            return;
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {

            Fragment topfragment = getSupportFragmentManager().findFragmentById(R.id.rootfragcontent);
            if (mBackKeyPressedTimes == 0) {
                if ((topfragment instanceof MainActivityFragment) || (topfragment instanceof LoginFragment)) {
                    mBackKeyPressedTimes = 1;
                    ToastShow.toastShow(R.string.login_exit, ToastShow.LENGTH_SHORT);
                } else
                    initFragment();

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            mBackKeyPressedTimes = 0;
                        }
                    }
                }.start();
                return;
            } else if (mBackKeyPressedTimes == 1) {
                super.onBackPressed();
                return;
            }
            super.onBackPressed();
        } else {
            if (mCurrentFragment != null && mCurrentFragment.onBackPressed()) {
                return;
            }
            getSupportFragmentManager().popBackStackImmediate();
            return;
        }
    }
}
