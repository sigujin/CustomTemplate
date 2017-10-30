//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vito.base.utils.network.httplibslc;

import java.util.Map;

public class RequestVo {
    public String requestUrl;
    public Map<String, String> requestHeadMap;
    public Map<String, String> requestDataMap;
    public String jsonParam;
    public boolean isAsJsonContent = false;

    public RequestVo() {
    }

    public RequestVo(String requestUrl, Map<String, String> requestHeadMap, Map<String, String> requestDataMap) {
        this.requestUrl = requestUrl;
        this.requestHeadMap = requestHeadMap;
        this.requestDataMap = requestDataMap;
    }

    public String toString() {
        return "RequestVo [requestUrl=" + this.requestUrl + ", requestHeadMap = " + this.requestHeadMap + ", requestDataMap=" + this.requestDataMap + "]";
    }
}
