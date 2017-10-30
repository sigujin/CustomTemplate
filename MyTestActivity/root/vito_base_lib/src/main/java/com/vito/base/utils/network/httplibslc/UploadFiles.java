package com.vito.base.utils.network.httplibslc;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;


public class UploadFiles {

//    public static void UploadFiles(List<String> in_pathlist, String in_url, RequestCallBack in_callback) {
//        // 设置请求参数的编码
//        //RequestParams params = new RequestParams("GBK");
//        RequestParams params = new RequestParams(); // 默认编码UTF-8
//        String str = "";
//        if (in_pathlist.size() > 0) {
//            for (int i = 0; i < in_pathlist.size(); i++) {
//                params.addBodyParameter("files[" + i + "]", new File(in_pathlist.get(i)));
//                if (i < in_pathlist.size() - 1)
//                    str += ",";
//            }
//        }
//        params.addBodyParameter("md5", str);
//
//
//        com.lidroid.xutils.HttpUtils http = new com.lidroid.xutils.HttpUtils();
//        http.send(HttpRequest.HttpMethod.POST,
//                in_url,
//                params,
//                in_callback);
//    }


    //    public static void UploadFile(String in_pathlist, String in_url, RequestCallBack in_callback) {
//        // 设置请求参数的编码
//        //RequestParams params = new RequestParams("GBK");
//        RequestParams params = new RequestParams(); // 默认编码UTF-8
//        params.addBodyParameter("files", new File(in_pathlist));
//
//        com.lidroid.xutils.HttpUtils http = new com.lidroid.xutils.HttpUtils();
//        http.send(HttpRequest.HttpMethod.POST,
//                in_url,
//                params,
//                in_callback);
//    }
//
//    public static void UploadFile(File in_files, String in_url, Map<String, String> in_param, RequestCallBack in_callback) {
//        // 设置请求参数的编码
//        //RequestParams params = new RequestParams("GBK");
//        RequestParams params = new RequestParams(); // 默认编码UTF-8
//        params.addBodyParameter("files", in_files);
//
//        if (in_param != null) {
//            Iterator paramsMap = in_param.entrySet().iterator();
//            while (paramsMap.hasNext()) {
//                Map.Entry pairs = (Map.Entry) paramsMap.next();
//                params.addBodyParameter((String) pairs.getKey(), (String) pairs.getValue());
//            }
//        }
//
////        if(HttpRequest.JSESSIONID != null && !HttpRequest.JSESSIONID.isEmpty() )
////            params.addHeader("Cookie", "SID=" + HttpRequest.JSESSIONID );
//
//        com.lidroid.xutils.HttpUtils http = new com.lidroid.xutils.HttpUtils();
//        http.send(HttpRequest.HttpMethod.POST,
//                in_url,
//                params,
//                in_callback);
//    }
//
//
    public static String UploadFileSync(File in_files, String in_url, Map<String, String> in_param) {
        // 设置请求参数的编码
        //RequestParams params = new RequestParams("GBK");
        RequestParams params = new RequestParams(in_url); // 默认编码UTF-8
        params.setMultipart(true);
        params.addBodyParameter("file", in_files, null);

        if (in_param != null) {
            Iterator paramsMap = in_param.entrySet().iterator();
            while (paramsMap.hasNext()) {
                Map.Entry pairs = (Map.Entry) paramsMap.next();
                params.addBodyParameter((String) pairs.getKey(), (String) pairs.getValue());
            }
        }

        try {
            String re = x.http().postSync(params, String.class);
            return re;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static String UploadFileSync2(File in_files, String in_url, Map<String, String> in_param) {
        // 设置请求参数的编码
        //RequestParams params = new RequestParams("GBK");
        RequestParams params = new RequestParams(in_url); // 默认编码UTF-8
        params.setMultipart(true);
        params.addBodyParameter("files", in_files, null);

        if (in_param != null) {
            Iterator paramsMap = in_param.entrySet().iterator();
            while (paramsMap.hasNext()) {
                Map.Entry pairs = (Map.Entry) paramsMap.next();
                params.addBodyParameter((String) pairs.getKey(), (String) pairs.getValue());
            }
        }

        try {
            String re = x.http().postSync(params, String.class);
            return re;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
