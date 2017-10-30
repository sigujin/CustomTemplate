package com.vito.base.update;

import org.codehaus.jackson.annotate.JsonProperty;

public class UpdateGsonBean {
    //@JsonProperty("isUpdate")
    //private int isUpdate;

    @JsonProperty("total")
    public int total;

    @JsonProperty("obj")
    public Object obj;

    @JsonProperty("params")
    public String params;

    @JsonProperty("success")
    public boolean success;

    @JsonProperty("msg")
    public String msg;

    @JsonProperty("pageSize")
    public int pageSize;

    @JsonProperty("curPage")
    public int curPage;

    @JsonProperty("data1")
    public Object data1;

    @JsonProperty("pagetotal")
    public Object pagetotal;

    @JsonProperty("data")
    public UpdateInfoBean[] updateInfo;

    public int getTotal() {
        return total;
    }

    public UpdateInfoBean[] getUpdateInfo() {
        return updateInfo;
    }
}
