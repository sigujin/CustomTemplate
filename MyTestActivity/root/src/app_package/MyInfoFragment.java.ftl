package ${packageName}.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vito.base.action.Action;
import com.vito.base.action.ActionParser;
import com.vito.base.entity.GroupAppData;
import com.vito.base.jsonbean.VitoJsonTemplateBean;
import com.vito.base.ui.fragments.BaseFragment;
import com.vito.base.ui.fragments.FragmentFactory;
import com.vito.base.utils.FileUtils;
import com.vito.base.utils.ToastShow;
import com.vito.base.utils.ViewFindUtils;
import com.vito.base.utils.glidetransform.GlideCircleTransform;
import com.vito.base.utils.network.httplibslc.RequestVo;
import com.vito.base.utils.network.jsonpaser.JsonLoader;
import ${packageName}.R;
import ${packageName}.utils.Comments;

import org.codehaus.jackson.type.TypeReference;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Created by lenovo24 on 2017/4/20.
 */
@ContentView(R.layout.fg_my_info)
public class MyInfoFragment extends BaseFragment {
 private static final int RECRUIT = 1001;
    JsonLoader mJsonLoader;
    @ViewInject(R.id.recycler)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.title)
    private TextView tv_title;
    @ViewInject(R.id.image_view)
    private ImageView image_view;
    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.text_id)
    private TextView text_id;
    @ViewInject(R.id.ll_personal_name)
    private LinearLayout ll_personal_name;
    @ViewInject(R.id.ll_info)
    private LinearLayout mInfoLayout;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastShow.toastShort(R.string.empty_fun);
        }
    };

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void InitActionBar() {
        super.InitActionBar();
        mLeftView.setVisibility(View.INVISIBLE);
        tv_title.setText(getResources().getString(R.string.my_info_title));
    }

    @Override
    protected void InitContent() {
        super.InitContent();
        onClick();
        mJsonLoader = new JsonLoader(getActivity(), this);
        RequestVo rv = new RequestVo();
        rv.requestUrl = FileUtils.getDataPathDefaultAssets(getActivity(), Comments.MY_INFO_PATH);
        mJsonLoader.load(rv, null, new TypeReference<ArrayList<GroupAppData>>() {
        }, null, 1);
    }

    void updateViews() {

    }

    @Override
    public void onJsonDataGetSuccess(Object re_data, int reqcode) {
        super.onJsonDataGetSuccess(re_data, reqcode);
        if (!isAdded())
            return;
        switch (reqcode) {
            case 1:
                break;
            case 2:
                VitoJsonTemplateBean bean = (VitoJsonTemplateBean) re_data;
                if (bean.getCode() == 0) {
                    Action mss = new Action();
                    mss.setmActionType("LoginOut");
                    EventBus.getDefault().post(mss);
                } else {
                    ToastShow.toastShort(bean.getMsg());
                }
                break;
        }

    }

    @Override
    public void onJsonDataGetFailed(int re_code, String re_info, int reqcode) {
        super.onJsonDataGetFailed(re_code, re_info, reqcode);
        ToastShow.toastShort(re_info);
    }

    /**
     * 注销登录
     *
     * @param in_view
     */
    private void logout(View in_view) {

    }


    /**
     * 点击事件
     */
    private void onClick() {
       
    }

    private void GetData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        updateViews();
    }
}
