package com.vito.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.TypedValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Util {

    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    public static String getPhoneManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    public static String getFromAssets(Context ctx, String fileName) {
        try {
            final String sdcardCompatibleFilePath = ctx.getExternalFilesDir(null) + "/" + fileName;
            File file = new File(sdcardCompatibleFilePath);
            InputStreamReader inputReader = null;
            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(sdcardCompatibleFilePath);
                inputReader = new InputStreamReader(inputStream);
            } else {
                inputReader = new InputStreamReader(ctx
                        .getResources().getAssets().open(fileName));
            }
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getVersionName(Context ctx) {
        try {
            PackageManager manager = ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getVersionCode(Context ctx) {
        try {
            PackageManager manager = ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
