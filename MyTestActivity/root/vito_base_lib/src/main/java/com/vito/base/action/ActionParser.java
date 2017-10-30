package com.vito.base.action;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;

import com.vito.base.R;
import com.vito.base.ui.fragments.BaseFragment;
import com.vito.base.ui.fragments.FragmentFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 动作解析类
 *
 * @author lennon
 */
public class ActionParser {

    private static final String TAG = ActionParser.class.getName();
    private static ActionParser mInstance = null;

    ActionParser() {
        mInstance = this;
    }

    /**
     * 单例方法
     *
     * @return
     */
    public static ActionParser getInstance() {
        return mInstance == null ? new ActionParser() : mInstance;
    }

    /**
     * 解析action
     *
     * @param action
     * @return 成功返回true
     * 失败返回false
     */
    @SuppressWarnings("deprecation")
    public boolean parseAction(Activity in_act, Action action, boolean addtostrack) {
        if (in_act == null || action == null) {
            return false;
        }

        if (action.getmActionType().equals("Fragment")) {
            int id = getIDbyName(action.getContentName());
            String fragmentname = action.getFragmentName();
            int index = fragmentname.lastIndexOf(".");
            if (index == -1) {
                Log.d(TAG, "error pacakage name");
                return false;
            }
            String packageName = fragmentname.substring(0, index);
            String clazzName = fragmentname.substring(index + 1, fragmentname.length());

            BaseFragment newFragment = (BaseFragment) FragmentFactory.getInstance().createFragment(packageName, clazzName);

            if (action.getTag() != null) {
                Bundle bundle = new Bundle();
                Iterator paramsMap = action.getTag().entrySet().iterator();

                while (paramsMap.hasNext()) {
                    Map.Entry pairs = (Map.Entry) paramsMap.next();
                    bundle.putString((String) pairs.getKey(), (String) pairs.getValue());
                }
                newFragment.setArguments(bundle);
            }
            FragmentTransaction transaction
                    = ((FragmentActivity) in_act).getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(id, newFragment);
            if (addtostrack)
                transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        } else if (action.getmActionType().equals("Activity")) {
            String packageName = action.getContentName();
            String activityName = action.getFragmentName();
            if (TextUtils.isEmpty(activityName) && !TextUtils.isEmpty(packageName)) {
                resolveSpecial(in_act, packageName);
                return true;
            }
            HashMap<String, String> tag = action.getmParameters();
            Bundle bundle = new Bundle();
            if (tag != null) {
                Iterator iter = tag.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    String val = (String) entry.getValue();
                    bundle.putString(key, val);
                }
            }

            ComponentName cn = new ComponentName(packageName,
                    activityName);
            Intent intent = new Intent();
            intent.setComponent(cn);
            intent.putExtras(bundle);
            try {
                in_act.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (action.getmActionType().equals("ShareTo")) {
            shareAction(in_act, action);
        }

        return true;
    }

    private void shareAction(Activity in_ctx, Action in_act) {
        in_ctx.startActivity(createShareIntent(in_ctx));
    }

    private Intent createShareIntent(Activity in_ctx) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        String shareStr = in_ctx.getString(R.string.app_share_info);
        shareIntent.setType("text/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareStr);
        return shareIntent;
    }

    /**
     * 根据id资源名称获取资源对应的值
     *
     * @param name ID资源名称
     * @return 资源值
     */
    private int getIDbyName(String name) {

        int id = 0;
        try {
            id = R.id.class.getDeclaredField(name).getInt(null);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return id;
    }

    /**
     * 对 第三方应用 特殊处理
     */

    private void resolveSpecial(Activity activity, String packageName) {
        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
        try {
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
