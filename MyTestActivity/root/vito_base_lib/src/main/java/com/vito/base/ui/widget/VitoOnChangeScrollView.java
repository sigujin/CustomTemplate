package com.vito.base.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by lenovo on 2016/6/30.
 */
public class VitoOnChangeScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    public VitoOnChangeScrollView(Context context) {
        super(context);
    }

//    public VitoPullToRefreshScrollView(Context context, Mode mode) {
//        super(context, mode);
//    }
//
//    public VitoPullToRefreshScrollView(Context context, Mode mode, AnimationStyle style) {
//        super(context, mode, style);
//    }

    public VitoOnChangeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface ScrollViewListener {

        void onScrollChanged(VitoOnChangeScrollView scrollView, int x, int y, int oldx, int oldy);

    }
}
