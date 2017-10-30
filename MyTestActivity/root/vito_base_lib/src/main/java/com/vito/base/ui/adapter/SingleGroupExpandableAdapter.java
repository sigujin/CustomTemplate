package com.vito.base.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;

/**
 * Created by pc on 2016/12/19.
 */

public class SingleGroupExpandableAdapter<V> extends BaseExpandableListAdapter {
    public ArrayList<V> mChildList;//子层
    public Context mContext;
    private int mGroupLayoutId;
    private int mChildLayoutId;

    public SingleGroupExpandableAdapter(Context context, int groupLayoutId, int childLayoutId) {
        mContext = context;
        mGroupLayoutId = groupLayoutId;
        mChildLayoutId = childLayoutId;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList == null ? 0 : mChildList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.get(childPosition);
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

    public void setData(ArrayList<V> childList) {
        mChildList = childList;
    }
}
