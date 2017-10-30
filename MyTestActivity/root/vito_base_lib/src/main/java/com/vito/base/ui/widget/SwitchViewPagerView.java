package com.vito.base.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.vito.base.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/6/27.
 */
public class SwitchViewPagerView extends LinearLayout {

    JazzyViewPager mJViewPager;
    CommonTabLayout mTabLayout;
    FragmentManager mFragmentManager;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public SwitchViewPagerView(Context context) {
        super(context);
        initView();
    }

    public SwitchViewPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        obtainAttributes(context, attrs);
    }

    public SwitchViewPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        obtainAttributes(context, attrs);
    }

    public void setFragmentManager(FragmentManager in_fgManager) {
        mFragmentManager = in_fgManager;
    }

    public void setOffscreenPageLimit(int in_page) {
        mJViewPager.setOffscreenPageLimit(in_page);
    }

    public void setCurrentTabIndex(int in_index) {
        mTabLayout.setCurrentTab(in_index);
        mJViewPager.setCurrentItem(in_index);
    }

    void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_switch_viewpager, this);

        mJViewPager = (JazzyViewPager) findViewById(R.id.jvp);
        mTabLayout = (CommonTabLayout) findViewById(R.id.common_tl);
        mJViewPager.setOffscreenPageLimit(3);

        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mJViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        mJViewPager.setAdapter(new MyPagerAdapter(((FragmentActivity) (getContext())).getSupportFragmentManager()));

        mJViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    public int px2dp(float px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public int px2sp(float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public void setTabEntities(ArrayList<CustomTabEntity> in_list) {
        mTabEntities = in_list;
    }

    public void setFragments(ArrayList<Fragment> in_list) {
        mFragments = in_list;
    }

    public void show() {
        mTabLayout.setTabData(mTabEntities);
        mJViewPager.setAdapter(new MyPagerAdapter(mFragmentManager));
        if (mJViewPager != null) {
            for (int i = 0; i < mFragments.size(); i++)
                mJViewPager.setObjectForPosition(mFragments.get(i), i);
        }

    }

    public void setTextColors(int unselectcolor, int selectcolor) {
        mTabLayout.setTextUnselectColor(unselectcolor);
        mTabLayout.setTextSelectColor(selectcolor);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, com.flyco.tablayout.R.styleable.CommonTabLayout);
        /*text*/
        mTabLayout.setTextSelectColor(ta.getColor(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_textSelectColor, Color.parseColor("#ffffff")));
        mTabLayout.setTextUnselectColor(ta.getColor(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_textUnselectColor, Color.parseColor("#AAffffff")));
        mTabLayout.setTextsize(
                px2sp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_textsize, sp2px(10f))));
        mTabLayout.setTextBold(ta.getBoolean(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_textBold, false));
        mTabLayout.setTextAllCaps(ta.getBoolean(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_textAllCaps, false));

        /*icon*/
        mTabLayout.setIconVisible(ta.getBoolean(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_iconVisible, true));
        mTabLayout.setIconGravity(ta.getInt(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_iconGravity, Gravity.TOP));
        mTabLayout.setIconWidth(
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_iconWidth, dp2px(0))));
        mTabLayout.setIconHeight(
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_iconHeight, dp2px(0))));
        mTabLayout.setIconMargin(
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_iconMargin, dp2px(2.5f))));

        /*Indicator*/
        mTabLayout.setIndicatorStyle(ta.getInt(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_style, 0));
        mTabLayout.setIndicatorColor(ta.getColor(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_color, Color.parseColor("#ffffff")));

        mTabLayout.setIndicatorHeight(
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_height, dp2px(2.0f))));
        mTabLayout.setIndicatorWidth(
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_width,
                        dp2px(mTabLayout.getIndicatorStyle() == 1 ? 10.0f : -1.0f))));

        mTabLayout.setIndicatorCornerRadius(
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_corner_radius, dp2px(0))));
        mTabLayout.setIndicatorMargin(
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_margin_left, dp2px(0))),
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_margin_top,
                        dp2px(mTabLayout.getIndicatorStyle() == 2 ? 7 : 0))),
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_margin_right, dp2px(0))),
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_margin_bottom,
                        dp2px(mTabLayout.getIndicatorStyle() == 2 ? 7 : 0)))
        );

        mTabLayout.setIndicatorAnimEnable(ta.getBoolean(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_anim_enable, true));
        mTabLayout.setIndicatorBounceEnable(ta.getBoolean(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_bounce_enable, true));
        mTabLayout.setIndicatorAnimDuration(ta.getInt(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_anim_duration, -1));
        mTabLayout.setIndicatorGravity(ta.getInt(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_indicator_gravity, Gravity.BOTTOM));

        /*Underline*/
        mTabLayout.setUnderlineColor(ta.getColor(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_underline_color, Color.parseColor("#ffffff")));
        mTabLayout.setUnderlineHeight(
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_underline_height, dp2px(0))));
        mTabLayout.setUnderlineGravity(ta.getInt(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_underline_gravity, Gravity.BOTTOM));

        /*divider*/
        mTabLayout.setDividerColor(ta.getColor(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_divider_color, Color.parseColor("#ffffff")));
        mTabLayout.setDividerWidth(
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_divider_width, dp2px(0))));
        mTabLayout.setDividerPadding(
                px2dp(ta.getDimension(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_divider_padding, dp2px(12))));

        mTabLayout.setUnderlineColor(ta.getColor(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_underline_color, Color.parseColor("#ffffff")));

        mTabLayout.setBackgroundColor(
                ta.getColor(com.flyco.tablayout.R.styleable.CommonTabLayout_tl_tabBackgroundColor, Color.parseColor("#ffffff")));
        ta.recycle();

        TypedArray ta2 = context.obtainStyledAttributes(attrs, R.styleable.JazzyViewPager);
        int effect = ta2.getInt(R.styleable.JazzyViewPager_style, 0);
        String[] transitions = getResources().getStringArray(R.array.jazzy_effects);
        mJViewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.valueOf(transitions[effect]));
        mJViewPager.setFadeEnabled(ta2.getBoolean(R.styleable.JazzyViewPager_fadeEnabled, false));
        mJViewPager.setOutlineEnabled(ta2.getBoolean(R.styleable.JazzyViewPager_outlineEnabled, false));
        mJViewPager.setOutlineColor(ta2.getColor(R.styleable.JazzyViewPager_outlineColor, Color.WHITE));
        switch (effect) {
            case 6:
            case 8:
                mJViewPager.setFadeEnabled(true);
        }

        ta2.recycle();

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabEntities.get(position).getTabTitle();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
