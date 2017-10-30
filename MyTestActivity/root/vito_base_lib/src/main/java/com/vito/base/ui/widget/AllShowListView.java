package com.vito.base.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by LC on 2016/3/10.
 * email:liuchaolqx@163.com
 */
public class AllShowListView extends ListView {
    public AllShowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AllShowListView(Context context) {
        super(context);
    }

    public AllShowListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}


