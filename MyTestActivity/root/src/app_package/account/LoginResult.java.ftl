package ${packageName}.account;

import java.io.Serializable;

public class LoginResult implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static LoginResult mInstance = null;

    LoginInfoBean mLoginData;

    boolean imIsLoginOK = false;

    public static synchronized LoginResult getInstance() {
        if (mInstance == null) {
            mInstance = new LoginResult();
        }
        return mInstance;
    }

    public LoginInfoBean getLoginData() {
        return mLoginData;
    }

    public void setLoginData(LoginInfoBean in_data) {
        mLoginData = in_data;
    }

    public void setIsLoginOK(boolean is_ok) {
        imIsLoginOK = is_ok;
    }

    public boolean isImIsLoginOK() {
        return imIsLoginOK;
    }
}
