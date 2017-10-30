package com.vito.base.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vito.base.R;


public class HomeTabView extends LinearLayout {
    ImageView mIconView;
    TextView mTunread;
    TextView mTittle;

    public HomeTabView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tab_item_view, this);
        mTunread = (TextView) findViewById(R.id.unread);
        mIconView = (ImageView) findViewById(R.id.imageview);
        mTittle = (TextView) findViewById(R.id.tittle);
    }

    public HomeTabView(Context context, AttributeSet attrs) {
        super(context, attrs);


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tab_item_view, this);
        mTunread = (TextView) findViewById(R.id.unread);
    }

    public final void setUnreadTips(final String count) {
        if (TextUtils.isEmpty(count) || Integer.valueOf(count) <= 0) {
            mTunread.setVisibility(View.GONE);
            return;
        } else if (Integer.valueOf(count) >= 100)
            mTunread.setText("...");
        else
            mTunread.setText(count);

        mTunread.setVisibility(View.VISIBLE);

    }

    public void setDrawableRId(int dra) {
        mIconView.setImageResource(dra);
    }

    public void setDrawableRes(Drawable dra) {
        mIconView.setImageDrawable(dra);
    }

    public void setTittleStr(String str) {
        mTittle.setText(str);
    }

}
