package ${packageName}.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.vito.base.action.Action;
import com.vito.base.entity.GroupAppData;
import com.vito.base.entity.TabEntity;
import com.vito.base.ui.fragments.BaseFragment;
import com.vito.base.ui.fragments.FragmentFactory;
import com.vito.base.ui.widget.SwitchViewPagerView;
import com.vito.base.utils.FileUtils;
import com.vito.base.utils.ResourceUtils;
import com.vito.base.utils.network.httplibslc.RequestVo;
import com.vito.base.utils.network.jsonpaser.JsonLoader;
import ${packageName}.R;
import ${packageName}.utils.Comments;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by lenovo24 on 2017/4/20.
 */
@ContentView(R.layout.fg_main)
public class MainActivityFragment extends BaseFragment {
    @ViewInject(R.id.switch_vp)
    private SwitchViewPagerView mSwitchView;
    private GroupAppData mHomeData;
    private boolean mInitActionFlag;
    private static final int GET_HOME_DATA = 0;

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void onJsonDataGetSuccess(Object re_data, int reqcode) {
        if (!isAdded())
            return;
        switch (reqcode){
            case GET_HOME_DATA:
                mHomeData = (GroupAppData) re_data;
                initView();
                break;
            default:
        }
    }

    @Override
    public void onJsonDataGetFailed(int re_code, String re_info, int reqcode) {
        Log.i("MainActivityFragment", re_code + ":" + re_info);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void InitContent() {
        super.InitContent();

        //禁止左右滑动
//        mSwitchView.setPageEnabled(false);
        JsonLoader jsonRequest = new JsonLoader(getActivity(), this);
        RequestVo rv = new RequestVo();
        rv.requestUrl = FileUtils.getDataPathDefaultAssets(getActivity(), Comments.FG_HOME_DATA);
        jsonRequest.load(rv, GroupAppData.class, null, null, GET_HOME_DATA);
        checkStartHisAction();
    }

    void initView() {
        try {
            ArrayList<CustomTabEntity> entitys = new ArrayList<>();
            ArrayList<Fragment> fgs = new ArrayList<>();
            for (int i = 0; i < mHomeData.getGroupItemData().size(); i++) {
                entitys.add(new TabEntity(mHomeData.getGroupItemData().get(i).getTitleText(),
                        ResourceUtils.getResourceID(getActivity().getResources(), mHomeData.getGroupItemData().get(i).getmImageResourceName_nor(),
                                "drawable", getActivity().getApplication().getPackageName()),
                        ResourceUtils.getResourceID(getActivity().getResources(), mHomeData.getGroupItemData().get(i).getmImageResourceName(),
                                "drawable", getActivity().getApplication().getPackageName())
                ));

                String fragmentName = mHomeData.getGroupItemData().get(i).getmAction().getFragmentName();
                int index = fragmentName.lastIndexOf(".");
                if (index == -1) {
                    Log.d(TAG, "error pacakage name");
                    return;
                }
                String packageName = fragmentName.substring(0, index);
                String clazzName = fragmentName.substring(index + 1, fragmentName.length());

                BaseFragment fg = FragmentFactory.getInstance().createFragment(packageName, clazzName);
                fg.setArguments(GenFragmentBundle());
                fgs.add(fg);
                mSwitchView.setTabEntities(entitys);
                mSwitchView.setFragments(fgs);
                mSwitchView.setFragmentManager(getChildFragmentManager());
                mSwitchView.show();
                mSwitchView.setOffscreenPageLimit(mHomeData.getGroupItemData().size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Bundle GenFragmentBundle() {
        Bundle bun = new Bundle();
        return bun;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    public synchronized void onEventMainThread(Action event) {
        String eventtype = event.getmActionType();
        if (eventtype.equals("MoveToFirstPage")) {
            mSwitchView.setCurrentTabIndex(0);
        }else if (eventtype.equals("MoveToSecondPage")){
            mSwitchView.setCurrentTabIndex(1);
        }
    }
}
