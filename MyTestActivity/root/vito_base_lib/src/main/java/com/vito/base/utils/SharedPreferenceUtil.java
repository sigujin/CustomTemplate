package com.vito.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SharedPreferenceUtil {

    /**
     * 登录的userId
     */
    public static final String USERID = "loginid";
    /**
     * 用户注册手机号
     */
    public static final String TELNUMBER = "telnumber";
    /**
     * 登录密码
     */
    public static final String PASSWORD = "password";

    /**
     * 是否自动登录
     */
    public static final String AUTOLOGIN = "isAutoLogin";

    /**
     * 是否记住密码
     */
    public static final String REMEMBERPASSWORD = "isRememberPassword";

    public static int getIntInfoFromSharedPreferences(Context ctx, String info_cat, String info_name) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(info_cat, Context.MODE_PRIVATE);
        int age = sharedPreferences.getInt(info_name, -1);
        return age;
    }

    public static String getStringInfoFromSharedPreferences(Context ctx, String info_cat, String info_name) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(info_cat, Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(info_name, "");
        return str;
    }

    public static boolean getBoolInfoFromSharedPreferences(Context ctx, String info_cat, String info_name, boolean def) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(info_cat, Context.MODE_PRIVATE);
        boolean re = sharedPreferences.getBoolean(info_name, def);
        return re;
    }

    public static void setIntInfoFromSharedPreferences(Context ctx, String info_cat, String info_name, int info_value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(info_cat, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(info_name, info_value);
        editor.commit();//提交修改
    }

    public static void setStringInfoFromSharedPreferences(Context ctx, String info_cat, String info_name, String info_value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(info_cat, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(info_name, info_value);
        editor.commit();//提交修改
    }

    public static void setBoolInfoFromSharedPreferences(Context ctx, String info_cat, String info_name, boolean info_value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(info_cat, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putBoolean(info_name, info_value);
        editor.commit();//提交修改
    }

    public static void setStringListFromSharedPreferences(Context ctx, String info_cat, String info_name, ArrayList<String> info_value_list) {
        String info_value = "";
        for (String s : info_value_list) {
            info_value += "," + s;
        }
        if (info_value.equals("")){
            info_value = "0";
        }else{
            info_value = info_value.substring(1);
        }
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(info_cat, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(info_name, info_value);
        editor.commit();//提交修改
    }

    public static void setStringMapListFromSharedPreferences(Context ctx, String info_cat, String info_name, ArrayList<Map<String, String>> info_value_list) {
        JSONArray mJsonArray = new JSONArray();
        for (int i = 0; i < info_value_list.size(); i++) {
            Map<String, String> itemMap = info_value_list.get(i);
            Iterator<Map.Entry<String, String>> iterator = itemMap.entrySet().iterator();
            JSONObject object = new JSONObject();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                try {
                    object.put(entry.getKey().replace("&quot;", ""), entry.getValue().replace("&quot;", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mJsonArray.put(object);
        }
        SharedPreferences sp = ctx.getSharedPreferences(info_cat, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(info_name, mJsonArray.toString());
        editor.commit();
    }

    public static ArrayList<Map<String, String>> getStringMapListFromSharedPreferences(Context context, String info_cat, String info_name) {
        ArrayList<Map<String, String>> datas = new ArrayList<Map<String, String>>();
        SharedPreferences sp = context.getSharedPreferences(info_cat, Context.MODE_PRIVATE);
        String result = sp.getString(info_name, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                Map<String, String> itemMap = new HashMap<String, String>();
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        itemMap.put(name, value);
                    }
                }
                datas.add(itemMap);
            }
        } catch (JSONException e) {

        }

        return datas;
    }
}
