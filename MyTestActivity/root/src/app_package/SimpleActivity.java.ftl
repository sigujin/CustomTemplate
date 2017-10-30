package  ${packageName};

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.vito.base.helper.StartUpHelper;
import com.vito.base.ui.ActionBarCtrller;
import com.vito.base.ui.VitoBaseActivity;
import com.vito.base.ui.fragments.BaseFragment;
import com.vito.base.ui.fragments.FragmentFactory;
import com.vito.base.utils.network.httplibslc.JsonLoaderCallBack;
import ${packageName}.fragment.MainActivityFragment;

public class ${activityClass} extends VitoBaseActivity implements JsonLoaderCallBack, BaseFragment.BackHandledInterface{
   
    boolean mFragmentInited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
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
        newFragment = FragmentFactory.getInstance().createFragment(MainActivityFragment.class);
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

    }

    @Override
    public void onJsonDataGetSuccess(Object re_data, int reqcode) {

    }

    @Override
    public void onJsonDataGetFailed(int re_code, String re_info, int reqcode) {

    }
}
