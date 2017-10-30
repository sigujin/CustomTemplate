package com.vito.base.ui.widget.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.vito.base.R;
import com.vito.base.utils.ViewFindUtils;

import cn.iwgang.countdownview.CountdownView;

public class SimpleGuideBanner extends BaseIndicatorBanner<Integer, SimpleGuideBanner> {
    CountdownView mJumpView;
    RelativeLayout mJumpLay;

    int mJumpBgRid = -1;
    private OnJumpClickL onJumpClickL;

    public SimpleGuideBanner(Context context) {
        this(context, null, 0);
    }

    public SimpleGuideBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleGuideBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBarShowWhenLast(false);
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == mDatas.size() - 1)
//                    mJumpView.start(3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(mContext, R.layout.adapter_simple_guide, null);
        ImageView iv = ViewFindUtils.find(inflate, R.id.iv);
        RelativeLayout mBtnLayout = ViewFindUtils.find(inflate, R.id.rl_jump);
        ImageView imageView = ViewFindUtils.find(inflate, R.id.iv_start);
        final Integer resId = mDatas.get(position);

        if (position == mDatas.size() - 1) {
//            mBtnLayout.setVisibility(VISIBLE);
            imageView.setVisibility(VISIBLE);
            mJumpView = ViewFindUtils.find(inflate, R.id.cd_jump);
            mJumpLay = ViewFindUtils.find(inflate, R.id.rl_jump);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onJumpClickL != null)
                        onJumpClickL.onJumpClick();
                }
            });
            mJumpLay.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onJumpClickL != null)
                        onJumpClickL.onJumpClick();
                }
            });

            mJumpView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                @Override
                public void onEnd(CountdownView cv) {
                    if (onJumpClickL != null)
                        onJumpClickL.onCountDownEnd();
                }
            });

        } else {
            mBtnLayout.setVisibility(GONE);
        }

        Glide.with(mContext)
                .load(resId)
                .into(iv);

        if (mJumpBgRid != -1)
            mJumpView.setBackgroundResource(mJumpBgRid);

        return inflate;
    }

    public void setOnJumpClickL(OnJumpClickL onJumpClickL) {
        this.onJumpClickL = onJumpClickL;
    }

    public void setJumpBGColor(int rid) {
        mJumpBgRid = rid;
        if (mJumpView != null)
            mJumpView.setBackgroundResource(rid);
    }

    public interface OnJumpClickL {
        void onJumpClick();

        void onCountDownEnd();
    }

}
