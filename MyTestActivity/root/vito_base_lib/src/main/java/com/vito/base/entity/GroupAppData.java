package com.vito.base.entity;

import com.vito.base.action.Action;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupAppData implements Serializable {
    protected int mImageResourceID = 0;
    protected int mImageNorResourceID = 0;

    @JsonProperty("tImgResName")
    protected String mImageResourceName;

    @JsonProperty("tImgResName_normal")
    protected String mImageResourceName_nor;
    @JsonProperty("tText")
    protected String mText;
    @JsonProperty("action")
    protected Action mAction;
    @JsonProperty("content")
    protected ArrayList<GroupAppData> mItemData;

    public String getmImageResourceName() {
        return mImageResourceName;
    }

    public String getmImageResourceName_nor() {
        return mImageResourceName_nor;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public Action getmAction() {
        return mAction;
    }

    public String getTitleText() {
        return mText;
    }

    public ArrayList<GroupAppData> getGroupItemData() {
        // TODO Auto-generated method stub
        return mItemData;
    }

    public void setGroupItemData(ArrayList<GroupAppData> data) {
        mItemData = data;
    }

}
