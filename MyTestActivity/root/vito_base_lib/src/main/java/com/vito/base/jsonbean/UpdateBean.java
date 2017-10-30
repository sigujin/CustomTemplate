package com.vito.base.jsonbean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by pc on 2016/9/6.
 */
public class UpdateBean implements Serializable {
    final public static String FORCE_UPDATE = "0";
    final public static String NORMAL_UPDATE = "1";

    @JsonProperty("versionCode")
    String mVersionCode;
    @JsonProperty("downloadUrl")
    String mDownloadUrl;
    @JsonProperty("remark")
    String mRemark;
    @JsonProperty("isForce")
    String mIsForce;

    public String getVersionCode() {
        return mVersionCode;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public String getRemark() {
        return mRemark;
    }

    public String getIsForce() {
        return mIsForce;
    }
}
