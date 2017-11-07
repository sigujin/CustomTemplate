package ${packageName}.fragment;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ${packageName}.R;
import ${packageName}.account.LoginCtrller;
import ${packageName}.account.LoginInfo;
import ${packageName}.account.LoginResultCallBack;
import com.vito.base.ui.fragments.BaseFragment;
import com.vito.base.ui.fragments.FragmentFactory;
import com.vito.base.utils.ToastShow;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by lenovo24 on 2017/4/19.
 */
@ContentView(R.layout.fg_login)
public class LoginFragment extends BaseFragment {
    @ViewInject(R.id.cb_login)
    CheckBox mAutoCheckBox;
    @ViewInject(R.id.cb_remember_password)
    CheckBox mRememberCheckbox;
    boolean mIsAutoLogin = false;
    boolean mIsRememberPwd = false;
    LoginResultCallBack mloginback = new LoginResultCallBack() {
        @Override
        public void LoginSuccess(String in_str) {
            hideWaitDialog();
            BaseFragment vitoRootFragment = (BaseFragment) FragmentFactory
                    .getInstance().createFragment(MainActivityFragment.class);
            changeMainFragment(vitoRootFragment, false);
        }


        @Override
        public void LoginFail(String re) {
            // TODO Auto-generated method stub
            ToastShow.toastShow(re, ToastShow.LENGTH_SHORT);
            Log.d("jsonStr:", re);
//            BaseFragment vitoRootFragment = (BaseFragment) FragmentFactory
//                    .getInstance().createFragment(MainActivityFragment.class);
//            changeMainFragment(vitoRootFragment, false);
            hideWaitDialog();
        }

        @Override
        public void PushDeviceTokenOk() {

        }

        @Override
        public void PushDeviceTokenFail() {

        }
    };
    @ViewInject(R.id.et_username)
    private EditText mUnameEditText;
    @ViewInject(R.id.et_password)
    private EditText mPWDEditText;
    @ViewInject(R.id.tv_sign_up)
    private TextView mSignUpTv = null;
    @ViewInject(R.id.tv_reset_pwd)
    private TextView mResetTv = null;
    private ILoginSuccess mLoginSuccess = null;
    private LoginInfo mLoginInfo = null;
    private String mUnameStr = null;
    //    ECInitParams.LoginAuthType mLoginAuthType = ECInitParams.LoginAuthType.NORMAL_AUTH;
    private String mPwdStr = null;

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
//        changeSystemStatusBarBg(R.color.colorPrimary2);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void InitContent() {
        mLoginInfo = LoginInfo.getInstance();
        mPWDEditText.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mSignUpTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        // 新建一个可以添加属性的文本对象
        SpannableString ss_phone = new SpannableString("手机号");
        SpannableString ss_password = new SpannableString("密码");
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);
        AbsoluteSizeSpan ass_password = new AbsoluteSizeSpan(16, true);
        // 附加属性到文本
        ss_phone.setSpan(ass, 0, ss_phone.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_password.setSpan(ass_password, 0, ss_password.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        mUnameEditText.setHint(new SpannedString(ss_phone)); // 一定要进行转换,否则属性会消失
        mPWDEditText.setHint(new SpannedString(ss_password)); // 一定要进行转换,否则属性会消失
        // 记住密码
        mIsRememberPwd = true/* mLoginInfo.isRememberPassword() */;
        // 自动登录
        mIsAutoLogin = mLoginInfo.isAutoLogin();
        if (mIsRememberPwd) {
            String username = mLoginInfo.getUsername();
            String password = mLoginInfo.getPassword();
            mUnameEditText.setText(username);
            mPWDEditText.setText(password);
            mRememberCheckbox.setChecked(true);
        }

        if (mIsAutoLogin) {
            mAutoCheckBox.setChecked(true);
        }

        // 是否自动登录
        mAutoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked)
                    mRememberCheckbox.setChecked(true);
            }
        });

        // 是否记住密码
        mRememberCheckbox
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        // TODO Auto-generated method stub
                        if (!isChecked)
                            mAutoCheckBox.setChecked(false);
                    }
                });
        // 登录按钮

        mSignUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BaseFragment fg = (BaseFragment) FragmentFactory.getInstance().createFragment(SignUpFragment.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("type", "1");
//                fg.setArguments(bundle);
//                changeMainFragment(fg, true);
            }
        });
        mResetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BaseFragment fg = (BaseFragment) FragmentFactory.getInstance().createFragment(SignUpFragment.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("type", "2");
//                fg.setArguments(bundle);
//                changeMainFragment(fg, true);
            }
        });

        if (mIsAutoLogin)
            login(null);

//        mUnameEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                try {
//                    String temp = s.toString();
//                    String tem = temp.substring(temp.length() - 1, temp.length());
//                    char[] temC = tem.toCharArray();
//                    int mid = temC[0];
//                    if (mid >= 48 && mid <= 57) {//数字
//                        return;
//                    }
//                    if (mid >= 65 && mid <= 90) {//大写字母
//                        return;
//                    }
//                    if (mid > 97 && mid <= 122) {//小写字母
//                        return;
//                    }
//                    s.delete(temp.length() - 1, temp.length());
//                } catch (Exception e) {
//
//                }
//            }
//        });
        mUnameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUnameEditText.hasFocus()) {
//                    mUnameEditText.setBackgroundResource(R.drawable.bg_login_edit);
                }
            }
        });
        mPWDEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPWDEditText.hasFocus()) {
//                    mPWDEditText.setBackgroundResource(R.drawable.bg_login_edit);
                }
            }
        });

    }

    @Event(value = R.id.tv_jump)
    private void jump(View in_view) {
        BaseFragment vitoRootFragment = (BaseFragment) FragmentFactory
                .getInstance().createFragment(MainActivityFragment.class);
        changeMainFragment(vitoRootFragment, false);
    }

    @Event(value = R.id.btn_login)
    private void login(View in_view) {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            if (getActivity() != null) {
                if (getActivity().getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getActivity()
                                    .getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }

        /* start login */
        mUnameStr = mUnameEditText.getText().toString().trim();
        mPwdStr = mPWDEditText.getText().toString().trim();

//        if (!StringUtil.isMobileNO(mUnameStr)) {
//            ToastShow.toastShow(R.string.mobile_no_error, ToastShow.LENGTH_SHORT);
//            return;
//        }

        if (mRememberCheckbox.isChecked()) {
            mLoginInfo.rememberPassword(true);
            mLoginInfo.saveUserInfo(mUnameStr, mPwdStr);
        } else {
            mLoginInfo.rememberPassword(false);
            mLoginInfo.saveUserInfo("", "");
        }

        mLoginInfo.autoLogin(mAutoCheckBox.isChecked());
        if (TextUtils.isEmpty(mUnameStr) || TextUtils.isEmpty(mPwdStr)) {
            Toast.makeText(getActivity(), R.string.empty_message,
                    Toast.LENGTH_SHORT).show();
        } else if (mPwdStr.length() < 6 && mPwdStr.length() > 16) {
            Toast.makeText(getActivity(), R.string.sign_up_pwd_rule,
                    Toast.LENGTH_SHORT).show();
        } else {
            showWaitDialog();
            LoginCtrller.getInstance().setmCallBack(mloginback);
            LoginCtrller.getInstance().prepareLogin(mUnameStr, mPwdStr);
        }
    }

    public void setILoginSuccess(ILoginSuccess iLoginSuccess) {
        mLoginSuccess = iLoginSuccess;
    }

    @Override
    public void onJsonDataGetSuccess(Object re_data, int reqcode) {
    }

    @Override
    public void onJsonDataGetFailed(int re_code, String re_info, int reqcode) {
    }

    public interface ILoginSuccess {
        public void loginSuccess();
    }
}
