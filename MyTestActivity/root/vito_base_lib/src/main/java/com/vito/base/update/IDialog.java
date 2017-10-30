package com.vito.base.update;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.vito.base.R;

import java.util.List;

/**
 * @author 作者: ZCS
 * @version 创建时间：2014年11月19日 下午4:10:05 类说明
 */
public class IDialog extends Dialog {
    Context context;
    LinearLayout ll_main;
    LinearLayout ll_title;
    LinearLayout ll_message;
    LinearLayout ll_btn;
    ImageView iv_title;
    List<View> btn;

    boolean isShowTitle;
    boolean isShowBtn;
    // LinearLayout ll_title_context;
    int winWidth;
    int winHeight;
    LinearLayout ll_check;
    private int dialogWidth;

    public IDialog(Context context) {
        //android:style/Theme.Dialog
        //super(context,android.R.style.Theme_Dialog);
        super(context);
        getWindow().setBackgroundDrawableResource(R.drawable.shape_main);
        //getWindow().setBackgroundDrawable(drawable);

        WindowManager wm = (WindowManager) context
                .getSystemService(context.WINDOW_SERVICE);
        winWidth = wm.getDefaultDisplay().getWidth();
        winHeight = wm.getDefaultDisplay().getHeight();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCancelable(false);
        this.context = context;
        initData();

    }

    private void initData() {
        ll_main = new LinearLayout(context);
        ll_title = new LinearLayout(context);
        ll_message = new LinearLayout(context);
        ll_btn = new LinearLayout(context);
        iv_title = new ImageView(context);
        ll_check = new LinearLayout(context);

        ll_check.setGravity(Gravity.CENTER_VERTICAL);
        ll_check.setBackgroundColor(Color.WHITE);
        ll_check.setPadding(3, 3, 3, 3);
        ll_check.setOrientation(LinearLayout.HORIZONTAL);

        //ll_main.setBackgroundColor(Color.TRANSPARENT);
        //ll_main.setBackgroundResource(R.drawable.shape_main);
        //ll_main.setPadding(0, 5, 0, 5);
        //ll_title.setBackgroundResource(R.drawable.shape_top);
        //ll_btn.setBackgroundResource(R.drawable.shape_bot);
        ll_message.setMinimumHeight(50);
        ll_btn.setMinimumHeight(50);

        ll_btn.setGravity(Gravity.CENTER);
        ll_message.setGravity(Gravity.CENTER);

        ll_message.setBackgroundColor(Color.WHITE);
        //ll_btn.setPadding(0, 8, 0, 0);
        ll_title.setPadding(0, 15, 0, 15);
        ll_message.setPadding(0, 0, 0, 10);
        //ll_btn.setPadding(0, 15, 0, 15);
        //ll_message.setPadding(5, 0, 5, 5);
        ll_main.setOrientation(LinearLayout.VERTICAL);
        ll_title.setOrientation(LinearLayout.HORIZONTAL);
        ll_btn.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params1 = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        LayoutParams params2 = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);


        ll_main.setLayoutParams(params1);
        ll_title.setLayoutParams(params2);
        ll_message.setLayoutParams(params2);
        ll_btn.setLayoutParams(params2);
        ll_check.setLayoutParams(params2);

        System.out.println("屏幕宽度" + winWidth);
        dialogWidth = winWidth * 3 / 4;
        System.out.println(winWidth * 3 / 4);
        this.setContentView(ll_main, new ViewGroup.LayoutParams(dialogWidth,
                LayoutParams.WRAP_CONTENT));

    }

    /**
     * 设置头部
     *
     * @param v
     */
    public void setTitleView(View v) {
        ll_title.addView(v);

    }

    /**
     * 文字标头
     *
     * @param v
     */
    public void setTitleView(CharSequence txt) {
        setTitleView(txt, 0, 0);
    }

    /**
     * 文字标头
     *
     * @param txt文字
     * @param color 颜色
     */
    public void setTitleView(CharSequence txt, int color) {
        setTitleView(txt, color, 0);
    }

    /**
     * 文字标头
     *
     * @param txt   文字
     * @param color 颜色
     * @param size  文字大小
     */
    public void setTitleView(CharSequence txt, int color, float size) {
        if (TextUtils.isEmpty(txt))
            return;

        TextView tv = new TextView(context);
        tv.setBackgroundColor(Color.TRANSPARENT);
        tv.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//		tv.setGravity(Gravity.CENTER);
//		Drawable drawable = context.getResources().getDrawable(R.drawable.update_dialog_icon);
//		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//		tv.setCompoundDrawables(drawable, null, null, null);
        //tv.setPadding(5, 5, 5, 5);
        tv.setTextSize(23);
        tv.setTextColor(0xff4aacff);
//		tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗

        if (color != 0) {
            tv.setTextColor(color);
        }
        if (size != 0) {
            tv.setTextSize(size);
        }
        tv.setText(txt);
        setTitleView(tv);
    }

    /**
     * 中间内容
     */
    public void setMessage(String text) {
        TextView tv = new TextView(context);
        tv.setSingleLine(false);
        tv.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.CENTER_VERTICAL);
        //tv.setPadding(5, 5, 5, 5);
        tv.setPadding(20, 10, 20, 10);
        tv.setTextSize(15);
        tv.setTextColor(Color.BLACK);
        tv.setText(text);
        setContextView(tv);
    }

    /**
     * 设置中间内容
     *
     * @param v
     */
    public void setContextView(View v) {

        if (ll_message.getChildCount() > 0) {
            ll_message.removeAllViews();
        }
        ll_message.addView(v);
    }

    /**
     * checkbox
     */

    public void setCheckBox(String text, OnCheckedChangeListener listener) {
        CheckBox cb = new CheckBox(context);
        cb.setText(text);
        cb.setTextColor(Color.BLACK);
        setCheckBoxView(cb);
        cb.setOnCheckedChangeListener(listener);
    }

    public void setCheckBoxView(View v) {
        ll_check.addView(v);
    }

    /**
     * 确定按钮
     *
     * @param text
     * @param onClickListener
     */
    public void setPositiveButton(CharSequence text,
                                  final View.OnClickListener onClickListener) {
        addBtn(text, onClickListener);

    }

    /**
     * 取消按钮
     *
     * @param text
     * @param onClickListener
     */
    public void setNegativeButton(CharSequence text,
                                  final View.OnClickListener onClickListener) {

        addBtn(text, onClickListener);

    }

    private void addBtn(CharSequence text, final View.OnClickListener onClickListener) {
        TextView btn = new TextView(context);
        btn.setTextColor(0xff4aacff);
        /*LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		layoutParams.set(10, 10, 10, 10);*/
        //btn.setLayoutParams(layoutParams);
        btn.setBackgroundColor(Color.TRANSPARENT);
        btn.setGravity(Gravity.CENTER);
        //btn.setPadding(0, 5, 0, 5);
        btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        btn.setText(text);
        btn.setTextSize(18);
        btn.setOnClickListener((View.OnClickListener) onClickListener);
        setBottomBtnView(btn);

    }

    /**
     * 设置头部图片
     *
     * @param iconId
     */
    public void setIcon(int iconId) {
        iv_title.setVisibility(View.VISIBLE);
        iv_title.setImageResource(iconId);
        iv_title.setPadding(50, 10, 10, 0);
        ll_title.addView(iv_title);
    }

    /**
     * 设置头部图片
     *
     * @param iconId
     */
    public void setIcon(Bitmap bitmap) {
        //iv_title.setVisibility(View.VISIBLE);
        iv_title.setImageBitmap(bitmap);
        ll_title.addView(iv_title);
    }

    /**
     * 设置底部控件
     *
     * @param v
     */
    public void setBottomBtnView(View v) {
        v.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f));
        v.setPadding(0, 15, 0, 15);
        if (ll_btn.getChildCount() >= 1) {
            View view = new View(context);

            view.setBackgroundColor(Color.GRAY);
            view.setLayoutParams(new LayoutParams(1,
                    LayoutParams.MATCH_PARENT));
            ll_btn.addView(view);
        }

        if (v != null) {
            ll_btn.addView(v);
        }
    }

    /**
     * 加条线
     */
    private void addDivideLine() {
        if (ll_title.getChildCount() > 0) {
            ll_main.addView(ll_title);
            View vLine = new View(context);
            vLine.setBackgroundColor(0xff4aacff);
            vLine.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 3));
            ll_main.addView(vLine);
        }
        if (ll_message.getChildCount() > 0) {
            ll_main.addView(ll_message);
        }

        if (ll_btn.getChildCount() > 0) {
            ll_main.addView(ll_check);
        }

        if (ll_btn.getChildCount() > 0) {
            ll_main.addView(ll_btn);
        }

        if (ll_btn.getChildCount() > 0) {
            View vLine = new View(context);
            vLine.setBackgroundColor(Color.GRAY);
            vLine.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
            ll_main.addView(vLine, ll_main.getChildCount() - 1);
        }

    }


    @Override
    public void show() {
        addDivideLine();

        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public View getCurrentFocus() {
        return super.getCurrentFocus();
    }

    @Override
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void setTitle(CharSequence title) {
        setTitleView(title, 0, 0);
        //super.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        // TODO Auto-generated method stub
        setTitleView(this.getContext().getText(titleId), 0, 0);
        super.setTitle(titleId);
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        // TODO Auto-generated method stub
        return super.getLayoutInflater();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            this.dismiss();
        }
        return super.onKeyDown(keyCode, event);

    }


}
