package com.realaicy.mb.android.apptna.ui.fragment.msg;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.jude.rollviewpager.Util;
import com.realaicy.mb.android.apptna.DataProvider;
import com.realaicy.mb.android.apptna.R;
import com.realaicy.mb.android.apptna.viewholder.PersonViewHolder;

public class MsgReadedFragment extends Fragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    private View mRootView;

    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter adapter;
    private Handler handler = new Handler();

    private int page = 0;
    private boolean hasNetWork = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MsgReadedFragment");
            mRootView = inflater.inflate(R.layout.fr_msg_read,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }


        recyclerView = (EasyRecyclerView) mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, Util.dip2px(getContext(),0.5f), Util.dip2px(getContext(),72),0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);


        adapter = new RecyclerArrayAdapter(getContext()) {

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }
        };


        recyclerView.setAdapterWithProgress(adapter);
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                adapter.resumeMore();
            }

            @Override
            public void onNoMoreClick() {
                adapter.resumeMore();
            }
        });
        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                adapter.remove(position);
                return true;
            }
        });
        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });


        recyclerView.setRefreshListener(this);
        onRefresh();





        return mRootView;
    }
    //第四页会返回空,意为数据加载结束
    @Override
    public void onLoadMore() {
        Log.i("EasyRecyclerView","onLoadMore");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新
                if (!hasNetWork) {
                    adapter.pauseMore();
                    return;
                }
                adapter.addAll(DataProvider.getPersonList(page));
                page++;
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        page = 0;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                //刷新
                if (!hasNetWork) {
                    adapter.pauseMore();
                    return;
                }
                adapter.addAll(DataProvider.getPersonList(page));
                page=1;
            }
        }, 2000);
    }

}
