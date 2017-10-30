package com.vito.base.utils;


import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DimenRes;
import com.vito.base.R;

/**
 * Created by lenovo on 2016/11/23.
 */
public class ResourceUtils {

    public static int getResourceID(Resources in_resources, String in_str, String in_srcstr, String in_pkg) {
        int re = 0;
        re = in_resources.getIdentifier(in_str, in_srcstr, in_pkg);
        return re;
    }

    public static int getDrawableResourceID(String in_str) {
        int re = 0;
        try {
            re = R.drawable.class.getDeclaredField(
                    in_str).getInt(null);
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
        return re;
    }

    public static float getDimension(Context context, @DimenRes int resourceId) {
        return context.getResources().getDimension(resourceId);
    }

    public static int getDimensionPixelSize(Context context, @DimenRes int resourceId) {
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
