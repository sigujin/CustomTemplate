package com.vito.base.update;

/**
 * Created by pc on 2016/11/24.
 */

public class UpdateConfig {
    private static UpdateConfig mInstance;
    // 获取更新状态的地址
    private String mGetUpdateURL;
    // APP标识
    private String mSysType;
    private String mBaseUrl;

    public static UpdateConfig getInstance() {
        if (mInstance == null)
            mInstance = new UpdateConfig();
        return mInstance;
    }

    public String getGetUpdateURL() {
        return mGetUpdateURL;
    }

    public UpdateConfig setGetUpdateURL(String getUpdateURL) {
        mGetUpdateURL = getUpdateURL;
        return this;
    }

    public String getSysType() {
        return mSysType;
    }

    public UpdateConfig setSysType(String sysType) {
        mSysType = sysType;
        return this;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public UpdateConfig setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
        return this;
    }
}
