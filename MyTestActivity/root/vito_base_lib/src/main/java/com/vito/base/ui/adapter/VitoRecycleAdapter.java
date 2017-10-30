package com.vito.base.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.vito.base.ui.viewholder.VitoViewHolder;

import java.util.ArrayList;


/**
 * Created by pc on 2016/7/11.
 */
public abstract class VitoRecycleAdapter<T, V extends VitoViewHolder> extends RecyclerView.Adapter<V> {

    protected Context mContext;
    protected ArrayList<T> mList;
    protected View.OnClickListener mOnClickListener;

    public VitoRecycleAdapter(ArrayList<T> list, Context context) {
        mList = list;
        mContext = context;
    }

    public VitoRecycleAdapter(ArrayList<T> list, Context context, View.OnClickListener onClickListener) {
        mList = list;
        mContext = context;
        mOnClickListener = onClickListener;
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public void onBindViewHolder(V holder, int position) {
        T itemBean = mList.get(position);
        holder.setPosition(position);
        holder.bindView(itemBean);
        if (mOnClickListener != null) {
            holder.setOnItemClickListener(mOnClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(ArrayList<T> in_List) {
        mList = in_List;
    }

    public T getItem(int in_pos) {
        return mList.get(in_pos);

    }

    public void clearData() {
        mList.clear();
    }

    public void addData(ArrayList<T> in_List) {
        mList.addAll(in_List);
    }

    public ArrayList<T> getData(){
        return mList;
    }
}
