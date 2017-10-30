package com.vito.base.utils.network.httplibslc;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/7/13.
 */
public interface UpLoadFilesCallBack {
    public void onUpLoadFileOk(ArrayList in_list, int re_code);

    public Object OperReDataInBG(Object in_obj);

    public void onUpLoadFileFaile(int re_code, String re_info);
}
