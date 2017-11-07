package ${packageName}.account;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import ${packageName}.R;
import ${packageName}.utils.Comments;
import com.vito.base.jsonbean.VitoJsonTemplateBean;
import com.vito.base.utils.Util;
import com.vito.base.utils.network.httplibslc.HttpRequest;
import com.vito.base.utils.network.httplibslc.JsonLoaderCallBack;
import com.vito.base.utils.network.httplibslc.RequestVo;
import com.vito.base.utils.network.jsonpaser.JsonLoader;
import com.vito.encrypt.MD5;

import org.codehaus.jackson.type.TypeReference;

import java.util.HashMap;

public class LoginCtrller {
    private static final String TAG = "LoginCtrller";
    private static final int GET_TIME_STAMP = 0;
    private static final int DO_LOGIN = 1;
    private static final int PUSH_DEVICE_TOKEN = 2;
    private static LoginResultCallBack mCallBack;
    private static LoginCtrller mThis = null;
    private String mLoginName;
    private String mLoginPwd;
    private String mTimeStamp;
    private JsonLoader mLoader;
    private Context mContext;
    private JsonLoaderCallBack mJsonLoaderCallBack = new JsonLoaderCallBack() {

        @Override
        public void onJsonDataGetSuccess(Object re_data, int reqcode) {
            switch (reqcode) {
                /* get time stamp */
                case GET_TIME_STAMP:
                    String ts = ((VitoJsonTemplateBean<String>) re_data).getData();
                    Log.i("L_C", "time stamp = " + ts);
                    if (ts != null && !TextUtils.isEmpty(ts)) {
                        mTimeStamp = ts;
                    }
                    DoLogin(mLoginName, mLoginPwd, mTimeStamp);
                    break;
                case DO_LOGIN:
                    VitoJsonTemplateBean<LoginInfoBean> _re_data = (VitoJsonTemplateBean<LoginInfoBean>) re_data;
                    if (_re_data != null && _re_data.getCode() == 0) {
                        LoginInfoBean bean = _re_data.getData();
                        LoginResult.getInstance().setLoginData(bean);
                        LoginResult.getInstance().setIsLoginOK(true);
                        if (mCallBack != null)
                            mCallBack.LoginSuccess(_re_data.getData().getUserId());
                    } else {
                        LoginResult.getInstance().setIsLoginOK(false);
                        String err = "";
                        if (_re_data != null)
                            err += /*_re_data.getCode() + ":" + */_re_data.getMsg();
                        else
                            err += /*":" + */"请检查网络。";
                        if (mCallBack != null)
                            mCallBack.LoginFail(err);
                    }
                    break;
                case PUSH_DEVICE_TOKEN:
                    VitoJsonTemplateBean templateBean = (VitoJsonTemplateBean) re_data;
                    if (mCallBack != null)
                        mCallBack.PushDeviceTokenOk();
                    break;
            }
        }

        @Override
        public void onJsonDataGetFailed(int re_code, String re_info, int reqcode) {
            switch (reqcode) {
                /* get time stamp */
                case GET_TIME_STAMP:
                    if (mCallBack != null)
                        mCallBack.LoginFail(mContext.getString(R.string.stamp_error));
                    break;
                case PUSH_DEVICE_TOKEN:
                    if (mCallBack != null)
                        mCallBack.PushDeviceTokenFail();
            }
        }
    };

    public static LoginCtrller getInstance() {
        if (mThis == null)
            mThis = new LoginCtrller();
        return mThis;
    }

    public void init(Context ctx) {
        mContext = ctx;
        mLoader = new JsonLoader(mContext, mJsonLoaderCallBack);
    }

    public LoginResultCallBack getmCallBack() {
        return mCallBack;
    }

    public void setmCallBack(LoginResultCallBack mCallBack) {
        LoginCtrller.mCallBack = mCallBack;
    }

    public void CheckLogin() {
        boolean autoLogin = LoginInfo.getInstance().isAutoLogin();

        if (autoLogin) { // 自动登录
            AutoLogin();
        } else {
            if (mCallBack != null)
                mCallBack.LoginFail("no_auto");
            mCallBack = null;
        }
    }

    public void AutoLogin() {
        String username = LoginInfo.getInstance().getUsername();
        String userpwd = LoginInfo.getInstance().getPassword();
        if (TextUtils.isEmpty(username)) {//第一次打开应用
            if (mCallBack != null)
                mCallBack.LoginFail("first");
            mCallBack = null;
        } else {
            prepareLogin(username, userpwd);
        }

    }

    public void prepareLogin(String in_uid, String in_pwd) {
        mLoginName = in_uid;
        mLoginPwd = in_pwd;
        HttpRequest.getInstance().JSESSIONID = null;
        getmTimeStamp();
    }

    private void DoLogin(String in_uid, String in_pwd, String mTimeStamp) {
        if (in_uid == null || in_pwd == null || mTimeStamp == null)
            return;
        TelephonyManager tm = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();
        String pwd_md5 = MD5.getMD5(in_pwd);

//        String clientEncryptString = DESTool.encrypt(testCode);
        String send_pwd = MD5.getMD5(pwd_md5 + mTimeStamp);
        RequestVo rv = new RequestVo();
        rv.requestUrl = Comments.LOGIN_URL;
        rv.requestDataMap = new HashMap<String, String>();
        rv.requestDataMap.put("userCode", in_uid);
        rv.requestDataMap.put("password", send_pwd);
        rv.requestDataMap.put("device", deviceid);
        rv.requestDataMap.put("deviceType", Util.getPhoneBrand() + "/" + Util.getPhoneModel());
//        rv.requestDataMap.put("date", mTimeStamp);
        rv.requestDataMap.put("rType", "NORMAL_USER_ROLE_ID");

        mLoader.load(rv, null, new TypeReference<VitoJsonTemplateBean<LoginInfoBean>>() {
        }, JsonLoader.MethodType.MethodType_Post, DO_LOGIN);
    }

    private void getmTimeStamp() {
        RequestVo rv = new RequestVo();
        rv.requestUrl = Comments.GET_TIME_STAMP_URL;
        mLoader.load(rv, null, new TypeReference<VitoJsonTemplateBean<String>>() {
        }, JsonLoader.MethodType.MethodType_Post, GET_TIME_STAMP);
    }

    public void updatePushDeviceToken(String in_token) {
        if (!TextUtils.isEmpty(in_token)) {
            RequestVo rv = new RequestVo();
            rv.requestUrl = Comments.UPDATE_DEVICE_TOKEN;
            rv.requestDataMap = new HashMap<>();
            rv.requestDataMap.put("deviceToken", in_token);
            rv.requestDataMap.put("deviceType", "android");
            rv.requestDataMap.put("appType", Comments.UMENG_APP_NAME);

            mLoader.load(rv, null, new TypeReference<VitoJsonTemplateBean>() {
            }, JsonLoader.MethodType.MethodType_Post, PUSH_DEVICE_TOKEN);
        }
    }
}
