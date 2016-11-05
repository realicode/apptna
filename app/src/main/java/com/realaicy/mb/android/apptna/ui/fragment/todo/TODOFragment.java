package com.realaicy.mb.android.apptna.ui.fragment.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.realaicy.mb.android.apptna.R;
import com.realaicy.mb.android.apptna.utils.ViewFindUtils;

import java.util.ArrayList;

public class TodoFragment extends Fragment {

    private View mRootView;

    private final String[] mTitles = {
            "流程", "项目", "Android", "前端", "后端", "设计", "工具资源"
    };
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","TodoFragment");
            mRootView = inflater.inflate(R.layout.fr_todo,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }


        mFragments.add(new TodoWFFragment());
        mFragments.add(new TodoPJFragment());

        ViewPager vp = ViewFindUtils.find(mRootView, R.id.vp);
        MyPagerAdapter mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(mAdapter);

        /** 字体加粗,大写 */
        SlidingTabLayout tabLayout_3 = ViewFindUtils.find(mRootView, R.id.tl_s_3);
        tabLayout_3.setViewPager(vp);
        vp.setCurrentItem(4);
        tabLayout_3.showDot(4);

        return mRootView;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
