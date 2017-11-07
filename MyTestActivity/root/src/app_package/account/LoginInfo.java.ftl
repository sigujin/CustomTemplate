package ${packageName}.account;

import android.content.Context;
import android.util.Log;

import ${packageName}.utils.Comments;
import com.vito.base.utils.SharedPreferenceUtil;

public class LoginInfo {
    private static LoginInfo mInstance = null;
    private static Context mContext = null;
    private final String PREFRENCEFILE = "UserData";

    public LoginInfo() {

    }

    public synchronized static LoginInfo getInstance() {
        if (mInstance == null) {
            mInstance = new LoginInfo();
        }
        return mInstance;
    }

    public static void init(Context context) {
        mContext = context;
    }

    public String getUsername() {
        return SharedPreferenceUtil.getStringInfoFromSharedPreferences(mContext, PREFRENCEFILE, Comments.TELNUMBER);

    }

    public String getPassword() {
        return SharedPreferenceUtil.getStringInfoFromSharedPreferences(mContext, PREFRENCEFILE, Comments.PASSWORD);

    }

    public void saveUserInfo(String username, String password) {
        Log.i("bianxh", "" + username + ", " + password + ", ");
        SharedPreferenceUtil.setStringInfoFromSharedPreferences(mContext, PREFRENCEFILE, Comments.TELNUMBER, username);
        SharedPreferenceUtil.setStringInfoFromSharedPreferences(mContext, PREFRENCEFILE, Comments.PASSWORD, password);
    }

    public void rememberPassword(boolean isRemember) {
        SharedPreferenceUtil.setBoolInfoFromSharedPreferences(mContext, PREFRENCEFILE, Comments.REMEMBERPASSWORD, isRemember);

    }

    public void autoLogin(boolean isAutoLogin) {
        SharedPreferenceUtil.setBoolInfoFromSharedPreferences(mContext, PREFRENCEFILE, Comments.AUTOLOGIN, isAutoLogin);
    }

    public boolean isRememberPassword() {
        return SharedPreferenceUtil.getBoolInfoFromSharedPreferences(mContext, PREFRENCEFILE, Comments.REMEMBERPASSWORD, false);
    }

    public boolean isAutoLogin() {
        return SharedPreferenceUtil.getBoolInfoFromSharedPreferences(mContext, PREFRENCEFILE, Comments.AUTOLOGIN, false);

    }

    public void clearUserInfo() {
        saveUserInfo("", "");
    }

    public void clearUserByLogiinOut() {
        SharedPreferenceUtil.setStringInfoFromSharedPreferences(mContext, PREFRENCEFILE, Comments.PASSWORD, "");
        SharedPreferenceUtil.setBoolInfoFromSharedPreferences(mContext, PREFRENCEFILE, Comments.AUTOLOGIN, false);

    }
}
