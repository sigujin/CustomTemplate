//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vito.base.utils.network.httplibslc;

import android.text.TextUtils;

import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.io.IOException;
import java.net.HttpCookie;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class HttpRequest {
    private static final String TAG = "HttpRequest";
    public static String JSESSIONID = null;
    private static HttpRequest mInstance = null;
    public static String SESSIONID_name = "SID";
    public HttpCookie mCookieList = null;
    String cookie = null;
    private int HTTP_RQUEST_TIMEOUT = 10000;
    private myX509TrustManager xtm = new myX509TrustManager();
    private myHostnameVerifier hnv = new myHostnameVerifier();

    private HttpRequest() {
        mInstance = this;
    }

    public static HttpRequest newInstance() {
        return new HttpRequest();
    }

    public static HttpRequest getInstance() {
        return mInstance == null ? new HttpRequest() : mInstance;
    }

    public static String generatReqUrl(String url, Map<String, String> params) {
        if (url == null) {
            return null;
        } else {
            if (!url.endsWith("?"))
                url += "?";

            StringBuilder builder = new StringBuilder(url);
            Iterator var4 = params.entrySet().iterator();

            while (var4.hasNext()) {
                Entry entry = (Entry) var4.next();
                if (entry.getKey() != null && entry.getValue() != null) {
                    builder.append((String) entry.getKey()).append("=").append((String) entry.getValue());
                    builder.append("&");
                }
            }

            return builder.toString();
        }
    }

    public void setRequestTimeOut(int timeout) {
        this.HTTP_RQUEST_TIMEOUT = timeout;
    }

    public void setSessionIDName(String name) {
        this.SESSIONID_name = name;
    }

    public String postRequest(RequestVo requestVo) {

        RequestParams rp = new RequestParams(requestVo.requestUrl);

        if (requestVo.isAsJsonContent)
            rp.setAsJsonContent(true);
        Map headMap = requestVo.requestHeadMap;
        if (headMap != null) {
            Iterator paramsMap = headMap.entrySet().iterator();
            while (paramsMap.hasNext()) {
                Entry pairs = (Entry) paramsMap.next();
                rp.addHeader((String) pairs.getKey(), (String) pairs.getValue());
            }
        }

        Map dataMap = requestVo.requestDataMap;
        if (dataMap != null) {
            Iterator paramsMap = dataMap.entrySet().iterator();
            while (paramsMap.hasNext()) {
                Entry pairs = (Entry) paramsMap.next();
                rp.addBodyParameter((String) pairs.getKey(), (String) pairs.getValue());
            }
        }


//
        if (JSESSIONID != null && !JSESSIONID.isEmpty()) {
            rp.addHeader("Cookie", SESSIONID_name + "=" + JSESSIONID);
//            rp.setUseCookie(false);
            rp.addHeader("Content-Type", "application/json;charset=UTF-8");
        } else
            rp.setUseCookie(true);

        String re = null;
        String bodyContent = "";
        if (!TextUtils.isEmpty(requestVo.jsonParam)) {
            bodyContent = requestVo.jsonParam;
        }
        rp.setBodyContent(bodyContent);
        try {
//            re = x.http().requestSync(HttpMethod.POST,rp, String.class);
            re = x.http().postSync(rp, String.class);


            DbCookieStore instance = DbCookieStore.INSTANCE;
            List<HttpCookie> cookies = instance.getCookies();
            for (HttpCookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();

                if (SESSIONID_name.equals(name) && requestVo.requestUrl.contains(cookie.getDomain())) {
                    JSESSIONID = value;
                    mCookieList = cookie;
                    break;
                }
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return re;
    }

    public String getRequest(RequestVo requestVo) {
        String step_orign = requestVo.requestUrl;
        if (!step_orign.contains("?"))
            step_orign += "?";

        Map dataMap = requestVo.requestDataMap;
        if (dataMap != null) {
            Iterator paramsMap = dataMap.entrySet().iterator();
            while (paramsMap.hasNext()) {
                Entry pairs = (Entry) paramsMap.next();
                step_orign += (String) pairs.getKey();
                step_orign += "=";
                step_orign += (String) pairs.getValue();
                step_orign += "&";
            }
        }

        RequestParams rp = new RequestParams(step_orign);
        Map headMap = requestVo.requestHeadMap;
        if (headMap != null) {
            Iterator paramsMap = headMap.entrySet().iterator();
            while (paramsMap.hasNext()) {
                Entry pairs = (Entry) paramsMap.next();
                rp.addHeader((String) pairs.getKey(), (String) pairs.getValue());
            }
        }

        if (JSESSIONID != null && !JSESSIONID.isEmpty()) {
            rp.addHeader("Cookie", SESSIONID_name + "=" + JSESSIONID);
//            rp.setUseCookie(false);
            rp.addHeader("Content-Type", "application/json;charset=UTF-8");
        } else
            rp.setUseCookie(true);

        String re = null;
        try {
            re = x.http().requestSync(HttpMethod.GET, rp, String.class);
            DbCookieStore instance = DbCookieStore.INSTANCE;
            List<HttpCookie> cookies = instance.getCookies();
            for (HttpCookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                if (SESSIONID_name.equals(name) && requestVo.requestUrl.contains(cookie.getDomain())) {
                    JSESSIONID = value;
                    mCookieList = cookie;
                    break;
                }
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return re;
    }

    class myX509TrustManager implements X509TrustManager {
        @Override
        public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
                throws CertificateException {

        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

    class myHostnameVerifier implements HostnameVerifier {

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
