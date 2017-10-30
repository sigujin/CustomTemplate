package com.vito.base.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;

/**
 * Created by lenovo on 2016/6/30.
 */
public class AsymmetricAllShowGridView extends AsymmetricGridView {
    public AsymmetricAllShowGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
