package com.vito.base.update;

import org.codehaus.jackson.annotate.JsonProperty;

public class UpdateInfoBean {
    public static final String NEED_UPDATE = "1";
    @JsonProperty("packageName")
    public String package_name;
    @JsonProperty("versionCode")
    public int version;
    @JsonProperty("downloadUrl")
    public String download_url;
    //@JsonProperty("status")
    public String status = "1";
    //@JsonProperty("is_update")
    public String is_update = "1";
    //@JsonProperty("update_key")
    public String update_key;
    //@JsonProperty("title")
    public String title;
    @JsonProperty("fileSize")
    public String file_size;
    @JsonProperty("remark")
    public String content;        //	 更新内容
    @JsonProperty("apkName")
    public String apkName;
    @JsonProperty("versionName")
    public Object versionName;
    //@JsonProperty("apk_id")
    public String apk_id;

    public String toString() {
        return "package_name:" + package_name +
                ",version:" + version +
                ",download_url:" + download_url +
                ",status:" + status +
                ",is_update:" + is_update +
                ",update_key:" + update_key +
                ",title:" + title +
                ",file_size:" + file_size +
                ",content:" + content +
                ",apk_id:" + apk_id;
    }
}
