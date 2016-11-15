package com.realaicy.mb.android.apptna.ui.fragment.msg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SegmentTabLayout;
import com.realaicy.mb.android.apptna.R;
import com.realaicy.mb.android.apptna.utils.ViewFindUtils;

import java.util.ArrayList;

public class MessageFragment extends Fragment {

    private final String[] mTitles = {
            "已读", "未读", "fresh","all"
    };
    private View mRootView;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MessageFragment");
            mRootView = inflater.inflate(R.layout.fr_msg,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }


        mFragments.add(new MsgReadedFragment());
        mFragments.add(new MsgUnReadFragment());
        mFragments.add(new MsgFreshFragment());
        mFragments.add(new MsgAllFragment());

        SegmentTabLayout tabLayout_4 = ViewFindUtils.find(mRootView, R.id.tl_msg);

        tabLayout_4.setTabData(mTitles, getActivity(), R.id.fl_change, mFragments);

        //显示未读红点
        tabLayout_4.showDot(1);

        return mRootView;
    }
}
