package com.vito.base.action;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 行为json对应的类
 *
 * @author lennon
 */
public class Action implements Cloneable, Serializable {

    /**
     * mActionType  Activity, Fragment
     */
    @JsonProperty("actionType")
    protected String mActionType;
    /**
     * mActionType == Activity, aContentName = packagename
     * mActionType == Fragment, aContentName = R.id.xxx
     */
    @JsonProperty("aContentName")
    protected String mContentName;
    /**
     * mActionType == Activity, mFragmentName = activityname
     * mActionType == Fragment, aFragmentName = fragmentname
     */
    @JsonProperty("aFragmentName")
    protected String mFragmentName;
    @JsonProperty("parameters")
    protected HashMap<String, String> mParameters;

    public String getmActionType() {
        return mActionType;
    }

    public void setmActionType(String intype) {
        mActionType = intype;
    }

    public HashMap<String, String> getmParameters() {
        return mParameters;
    }

    public void setmParameters(HashMap<String, String> mParameters) {
        this.mParameters = mParameters;
    }

    public String getmContentName() {
        return mContentName;
    }

    public void setmContentName(String mContentName) {
        this.mContentName = mContentName;
    }

    public String getmFragmentName() {
        return mFragmentName;
    }

    public void setmFragmentName(String mFragmentName) {
        this.mFragmentName = mFragmentName;
    }

    /**
     * fragment加载的资源id
     *
     * @return
     */
    public String getContentName() {
        return mContentName;
    }

    public void setContentName(String mContentName) {
        this.mContentName = mContentName;
    }

    /**
     * fragment类名(package name+class name)
     *
     * @return
     */
    public String getFragmentName() {
        return mFragmentName;
    }

    public void setFragmentName(String mFragmentName) {
        this.mFragmentName = mFragmentName;
    }

    /**
     * item对应的tag，通常为URL
     *
     * @return
     */
    public HashMap<String, String> getTag() {
        return mParameters;
    }

    public void setTag(HashMap<String, String> mTag) {
        this.mParameters = mTag;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}