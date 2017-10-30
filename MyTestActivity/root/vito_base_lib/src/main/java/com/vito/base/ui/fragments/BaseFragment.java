package com.vito.base.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vito.base.R;
import com.vito.base.action.Action;
import com.vito.base.action.LoginSuccessfullyAction;
import com.vito.base.jsonbean.VitoJsonTemplateBean;
import com.vito.base.ui.ActionBarCtrller;
import com.vito.base.ui.VitoBaseActivity;
import com.vito.base.ui.widget.CustomLoadingDialog;
import com.vito.base.utils.network.httplibslc.JsonLoaderCallBack;

import org.xutils.x;

import de.greenrobot.event.EventBus;

public abstract class BaseFragment extends Fragment implements JsonLoaderCallBack {
    protected static final String TAG = BaseFragment.class.getName();
    protected RelativeLayout mActionBar;
    protected Object mTag = null;
    protected View mRootViewGroup;
    protected int mLayoutResId;
    protected TextView mTitle;
    protected FrameLayout mLeftFrame;
    protected FrameLayout mLeftFrame2;
    protected FrameLayout mRightFrame;
    protected FrameLayout mRightFrame2;
    protected ImageView mLeftImage;
    protected ImageView mLeftImage2;
    protected ImageView mRightImage;
    protected ImageView mRightImage2;
    protected TextView mLeftView;
    protected TextView mRightView;
    public View.OnClickListener mActionBarOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == mLeftView.getId()) {
                onClickActionbarLeftBtn(v);
            } else if (v.getId() == mRightView.getId()) {
                onClickActionbarRightBtn(v);
            }
        }
    };
    protected TextView mLeftView2;
    protected TextView mRightView2;
    protected BackHandledInterface mBackHandledInterface;
    protected ICustomFragmentBackListener mCustomDialogEventListener;
    protected int mRequestCode;
    boolean isAddToCurrentFragment = true;
    private InternalReceiver internalReceiver;
    private CustomLoadingDialog mWaitDialog = null;

    public void setAddToCurrentFragment(boolean addToCurrentFragment) {
        isAddToCurrentFragment = addToCurrentFragment;
    }

    /**
     * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     */
    public abstract boolean onBackPressed();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (mRootViewGroup != null) {
            FrameLayout fl = (FrameLayout) mRootViewGroup.getParent();
            if (fl != null)
                fl.removeAllViews();
            return mRootViewGroup;
        } else {
//            mRootViewGroup = (ViewGroup) inflater.inflate(
//                    mLayoutResId, null);
            mRootViewGroup = x.view().inject(this, inflater, container);
//            ViewUtils.inject(this, mRootViewGroup);
            InitActionBar();
            InitContent();
        }

        return mRootViewGroup;
    }

    /**
     * 初始化部分
     */
    public void InitActionBar() {
        mActionBar = (RelativeLayout) mRootViewGroup.findViewById(R.id.actionbar_head);
        if (mActionBar == null)
            return;
        bindAllViews();

        mActionBar.setBackgroundColor(ActionBarCtrller.getInstance().getActionBarBg());
//        mActionBar.setBackgroundDrawable(ActionBarCtrller.getInstance().getDrawable());
        if (mLeftView != null) {
            mLeftView.setOnClickListener(mActionBarOnclickListener);
            Drawable drawable = getResources().getDrawable(ActionBarCtrller.getInstance().getLeftImgRes());
            Rect rect = new Rect(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.setBounds(rect);
            mLeftView.setCompoundDrawables(drawable, null, null, null);
            mLeftView.setTextColor(ActionBarCtrller.getInstance().getLeftTextColor());
            mLeftView.setVisibility(View.VISIBLE);
        }
        if (mRightView != null)
            mRightView.setOnClickListener(mActionBarOnclickListener);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,ActionBarCtrller.getInstance().getTitleTextSize());
        mTitle.setTextColor(ActionBarCtrller.getInstance().getTitleTextColor());
    }

    private void bindAllViews() {
        mTitle = (TextView) mRootViewGroup.findViewById(R.id.title);
        mLeftFrame = (FrameLayout) mRootViewGroup.findViewById(R.id.fl_left_1);
        mLeftFrame2 = (FrameLayout) mRootViewGroup.findViewById(R.id.fl_left_2);
        mRightFrame = (FrameLayout) mRootViewGroup.findViewById(R.id.fl_right_1);
        mRightFrame2 = (FrameLayout) mRootViewGroup.findViewById(R.id.fl_right_2);
        mLeftView = (TextView) mRootViewGroup.findViewById(R.id.left_view);
        mLeftView2 = (TextView) mRootViewGroup.findViewById(R.id.left_view_2);
        mRightView = (TextView) mRootViewGroup.findViewById(R.id.right_view);
        mRightView2 = (TextView) mRootViewGroup.findViewById(R.id.right_view_2);
        mLeftImage = (ImageView) mRootViewGroup.findViewById(R.id.iv_left_1);
        mLeftImage2 = (ImageView) mRootViewGroup.findViewById(R.id.iv_left_2);
        mRightImage = (ImageView) mRootViewGroup.findViewById(R.id.iv_right_1);
        mRightImage2 = (ImageView) mRootViewGroup.findViewById(R.id.iv_right_2);
    }

    /*页面初始化*/
    protected void InitContent() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandledInterface)) {
            this.mBackHandledInterface = null;
        } else {
            this.mBackHandledInterface = (BackHandledInterface) getActivity();
        }
    }

    public FragmentActivity getActivityFromBaseFragmet() {
        if (!isAdded()) {
            return null;
        }
        return super.getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        // 告诉FragmentActivity，当前Fragment在栈顶
        if (isAddToCurrentFragment && mBackHandledInterface != null)
            mBackHandledInterface.setSelectedFragment(this);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        closeSoftInput();
        hideWaitDialog();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    public void showWaitDialog() {
        if (mWaitDialog == null)
            mWaitDialog = new CustomLoadingDialog.Builder(getActivity())
                    .create();
        if (mWaitDialog != null && !mWaitDialog.isShowing())
            mWaitDialog.show();
    }

    public void hideWaitDialog() {
        if (mWaitDialog != null && mWaitDialog.isShowing())
            mWaitDialog.dismiss();
    }

    public void onClickActionbarLeftBtn(View view) {
        boolean isAdded = isAdded();
        if (isAdded) {
            getActivity().onBackPressed();
        }
    }

    public void onClickActionbarRightBtn(View view) {
    }

    public void changeMainFragment(Fragment in_fg, boolean add_to_stack) {
        if (getActivity() == null)
            return;
        FragmentTransaction transaction = ((FragmentActivity) getActivity())
                .getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.rootfragcontent, in_fg, in_fg.getClass().getSimpleName());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (add_to_stack)
            transaction.addToBackStack(in_fg.getClass().getName());
        transaction.commitAllowingStateLoss();
    }

    public void changeMainFragment(Class T, boolean add_to_stack) {
        if (getActivity() == null)
            return;

        Fragment in_fg = FragmentFactory.getInstance().createFragment(T);
        FragmentTransaction transaction = ((FragmentActivity) getActivity())
                .getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.rootfragcontent, in_fg);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (add_to_stack)
            transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    public void setCustomFragmentBackListener(int in_recode, ICustomFragmentBackListener listener) {
        mCustomDialogEventListener = listener;
        mRequestCode = in_recode;
    }

    public void closeSoftInput() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            if (getActivity() != null) {
                if (getActivity().getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getActivity()
                                    .getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

    protected void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    public void setTag(Object tag) {
        mTag = tag;
    }

    public Object getObjTag() {
        return mTag;
    }

    public void checkStartHisAction() {
        if (LoginSuccessfullyAction.getOnLoginSuccessfully() != null) {
            Action mss = new Action();
            mss.setmActionType("DoHisAction");
            EventBus.getDefault().post(mss);
        }

    }

    /**
     * 如果子界面需要拦截处理注册的广播
     * 需要实现该方法
     *
     * @param context
     * @param intent
     */
    protected void handleReceiver(Context context, Intent intent) {
        // 广播处理
        if (intent == null) {
            return;
        }
    }

    protected void registerReceiver(String[] actionArray) {
        if (actionArray == null) {
            return;
        }
        IntentFilter intentfilter = new IntentFilter();
        for (String action : actionArray) {
            intentfilter.addAction(action);
        }
        if (internalReceiver == null) {
            internalReceiver = new InternalReceiver();
        }
        getActivity().registerReceiver(internalReceiver, intentfilter);
    }

    @Override
    public void onJsonDataGetSuccess(Object re_data, int reqcode) {
        if (!(re_data instanceof VitoJsonTemplateBean))
            return;

        int code = ((VitoJsonTemplateBean) re_data).getCode();
        if (code == 11) { // session id  过期
            // 重新登录
            Action mss = new Action();
            mss.setmActionType("ReLogin");
            EventBus.getDefault().post(mss);
        }

        if (isAdded())
            doThingsByJsonOk(re_data, reqcode);
    }

    public void doThingsByJsonOk(Object re_data, int reqcode) {
    }

    public void doThingsByJsonFail(int re_code, String re_info, int reqcode) {
    }

    @Override
    public void onJsonDataGetFailed(int re_code, String re_info, int reqcode) {
        if (isAdded())
            doThingsByJsonFail(re_code, re_info, reqcode);
    }

    public void changeSystemStatusBarBg(int in_rid) {
        if (getActivity() instanceof VitoBaseActivity) {
            ((VitoBaseActivity) getActivity()).setStatusBarTintResource(in_rid);
        }
    }

    public interface BackHandledInterface {
        public void setSelectedFragment(BaseFragment selectedFragment);
    }

    public interface ICustomFragmentBackListener {
        /**
         * Standard activity result: operation canceled.
         */
        public static final int RESULT_CANCELED = 0;
        /**
         * Standard activity result: operation succeeded.
         */
        public static final int RESULT_OK = -1;

        public void OnFragmentBackDataEvent(int requestCode, int resultCode, Object backData);

        public void OnFragmentBackDataEventService(int requestCode, int resultCode, int num, int position, int[] numId, String[] strings);
    }

    public class InternalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            handleReceiver(context, intent);
        }
    }
}
