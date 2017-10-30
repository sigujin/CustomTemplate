package com.vito.base.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.vito.base.R;

/**
 * Created by lenovo on 2016/6/29.
 */
public class ScrollNoticeView extends LinearLayout {

    private static final String TAG = "LILITH";
    private Context mContext;
    private ViewFlipper viewFlipper;
    private View scrollTitleView;
    private int mTextColor;

    /**
     * 构造
     *
     * @param context
     */
    public ScrollNoticeView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public ScrollNoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();

    }

    public void setmImageRId(int mImageRId) {
        ((ImageView) (scrollTitleView.findViewById(R.id.notice_img))).setImageResource(mImageRId);
    }

    public void setNoticeTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public void setBackgroundColor(int mBackgroundColor) {
        scrollTitleView.setBackgroundColor(mBackgroundColor);
    }

    /**
     * 网络请求后返回公告内容进行适配
     */
    public void bindNotices(String[] in_str) {
        // TODO Auto-generated method stub
        viewFlipper.removeAllViews();
        int i = 0;
        while (i < in_str.length) {
            String text = in_str[i];
            TextView textView = new TextView(mContext);
            textView.setText(text);
            textView.setTextColor(mTextColor);
            LayoutParams lp = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            viewFlipper.addView(textView, lp);
            textView.setTag(i);
            i++;
        }
    }

    private void init() {
        bindLinearLayout();
    }

    /**
     * 初始化自定义的布局
     */
    protected void bindLinearLayout() {
        mTextColor = mContext.getResources().getColor(android.R.color.black);
        scrollTitleView = LayoutInflater.from(mContext).inflate(
                R.layout.view_scrollnotice, null);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        addView(scrollTitleView, layoutParams);

        viewFlipper = (ViewFlipper) scrollTitleView
                .findViewById(R.id.flipper_scrollTitle);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));
        viewFlipper.startFlipping();
        View v = viewFlipper.getCurrentView();
    }
}
