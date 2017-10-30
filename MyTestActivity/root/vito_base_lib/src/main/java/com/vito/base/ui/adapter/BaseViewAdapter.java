package com.vito.base.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * 公用的ListView，默认包含一张图片，一行标题，一行内容
 *
 * @author bianxh
 */
@SuppressLint("UseSparseArrays")
public class BaseViewAdapter<T> extends BaseAdapter {
    public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    public List<T> mData;
    public Context mContext;
    public int mLayoutId;

    public BaseViewAdapter(Context context, int layoutId) {
        mContext = context;
        mLayoutId = layoutId;
        mData = new ArrayList<T>();
    }

    public List<T> getData() {
        // TODO Auto-generated method stub
        return mData ;
    }

    public void setData(List<T> in_itemdata) {
        // TODO Auto-generated method stub
        mData = in_itemdata;
    }

    public void removeData(int pos) {
        // TODO Auto-generated method stub
        mData.remove(pos);
    }

    public void removeData(T pos) {
        // TODO Auto-generated method stub
        mData.remove(pos);
    }

    public void addData(List<T> in_itemdata) {
        // TODO Auto-generated method stub
        mData.addAll(in_itemdata);
    }

    public void addData(int location ,List<T> in_itemdata) {
        // TODO Auto-generated method stub
        mData.addAll(location,in_itemdata);
    }

    public void addData(int location ,T in_itemdata) {
        // TODO Auto-generated method stub
        mData.add(location,in_itemdata);
    }

    public void addData(T in_itemdata) {
        // TODO Auto-generated method stub
        mData.add(in_itemdata);
    }

    public void clearData() {
        // TODO Auto-generated method stub
        mData.clear();
    }

    @Override
    public T getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(mLayoutId, null);
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
