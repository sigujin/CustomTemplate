package com.vito.base.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by pc on 2016/7/11.
 * according to http://stackoverflow.com/questions/24885223/why-doesnt-recyclerview-have-onitemclicklistener-and-how-recyclerview-is-dif
 */
public abstract class VitoViewHolder<V> extends RecyclerView.ViewHolder implements View.OnClickListener {
    public View mItemView;
    public int mPosition;

    public VitoViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
    }

    public VitoViewHolder(View itemView, IViewHolderElementClicks listener) {
        super(itemView);
        mItemView = itemView;
    }

    public void setPosition(int position) {
        mPosition = position;
        mItemView.setTag(mPosition);
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        mItemView.setOnClickListener(onClickListener);
    }

    public abstract void bindView(V v);

    @Override
    public void onClick(View v) {
        Log.d("qh", "item click");
    }

    public static interface IViewHolderElementClicks {
        public void onClickItem1(View item);

        public void onClickItem2(View item);
    }
}
