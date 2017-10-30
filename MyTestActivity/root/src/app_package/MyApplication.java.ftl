package ${packageName};

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.vito.base.VitoBaseLib;
import com.vito.base.action.Action;
import com.vito.base.action.LoginSuccessfullyAction;
import com.vito.base.ui.fragments.FragmentFactory;
import com.vito.base.utils.CrashHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo24 on 2017/4/19.
 */

public class MyApplication extends MultiDexApplication {

    private static final String TAG = MyApplication.class.getSimpleName();
    private CrashHandler mCrashHandler = null;
    public static boolean isLogin = false;
    private Activity mActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        mCrashHandler = CrashHandler.getInstance();
        mCrashHandler.init(this);
        VitoBaseLib.init(this);
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

}
