package ${packageName}.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vito.base.ui.fragments.BaseFragment;
import ${packageName}.R;

public class SimpleFragment extends BaseFragment{

	@Override
    public boolean onBackPressed() {
        return false;
    }
	
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        TextView tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(40);
        tv.setText(R.string.about_company_copyright1);
        return tv ;
    }
}