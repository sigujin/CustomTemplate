package com.vito.base.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by pc on 2016/8/4.
 */
public class BaseExpandableAdapter<T, V> extends BaseExpandableListAdapter {
    public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    int mGroupLayoutId;
    int mChildLayoutId;
    public ArrayList<T> mGroupList;//父层
    public ArrayList<ArrayList<V>> mChildList;//子层
    private Context mContext;

    public BaseExpandableAdapter(Context context, int groupLayoutId, int childLayoutId) {
        mContext = context;
        mGroupLayoutId = groupLayoutId;
        mChildLayoutId = childLayoutId;
    }

    @Override
    public int getGroupCount() {
        return mGroupList == null ? 0 : mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList == null ? 0 : mChildList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(mGroupLayoutId, null);
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(mChildLayoutId, null);
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setData(ArrayList<T> groupList, ArrayList<ArrayList<V>> childList) {
        mGroupList = groupList;
        mChildList = childList;
    }

    public void clearData() {
        if (mGroupList != null)
            mGroupList.clear();
        if (mChildList != null)
            mChildList.clear();
    }

}
