package com.realaicy.mb.android.apptna.ui.fragment.msg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.realaicy.mb.android.apptna.R;
import com.realaicy.mb.android.apptna.adapter.c.ItemDragAdapter;
import com.realaicy.mb.android.apptna.adapter.c.RealItemDragAdapter;
import com.realaicy.mb.android.apptna.data.DataServer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TempTest extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener {

    private View mRootView;
    private EasyRefreshLayout easyRefreshLayout;



    private int page = 1;
    private int loadedCount = 6;

    private static final String TAG = TempTest.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private List<String> mData;
    private RealItemDragAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;


    private View notLoadingView;
    private static final int TOTAL_COUNTER = 24;
    private static final int PAGE_SIZE = 6;
    private int delayMillis = 500;
    private int mCurrentCounter = 0;
    private boolean isErr = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            Log.e("666", "MsgUnReadFragment");
            mRootView = inflater.inflate(R.layout.fr_msg_fresh, container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        easyRefreshLayout = (EasyRefreshLayout) mRootView.findViewById(R.id.easylayout);
        easyRefreshLayout.setEnableLoadMore(false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mData = generateData(6);

        mAdapter = new RealItemDragAdapter(mData);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG,"click:"+position);
            }
        });

        mAdapter.openLoadAnimation();
        mAdapter.openLoadMore(PAGE_SIZE);
//        mCurrentCounter = mAdapter.getData().size();
        loadedCount = mAdapter.getData().size();
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.enableSwipeItem();
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "view swiped start: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
                holder.setTextColor(R.id.tv, Color.WHITE);
                ((CardView) viewHolder.itemView).setCardBackgroundColor(Color.YELLOW);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "View reset: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
                holder.setTextColor(R.id.tv, Color.BLACK);
                ((CardView) viewHolder.itemView).setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "View Swiped: " + pos);
                if (mAdapter.getData().size() < PAGE_SIZE && loadedCount < TOTAL_COUNTER) {
                    Log.d(TAG, "Need Load More: ");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addData(generateData(PAGE_SIZE));
//                            mCurrentCounter = mAdapter.getData().size();
                            loadedCount+=PAGE_SIZE;
                        }
                    }, delayMillis);
                }
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(getContext(), R.color.color_light_blue));
            }
        };
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);

        mRecyclerView.setAdapter(mAdapter);

        initListener();

        return mRootView;

    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (loadedCount >= TOTAL_COUNTER) {
                    mAdapter.loadComplete();
                    if (notLoadingView == null) {
                        notLoadingView = getActivity().getLayoutInflater().inflate(R.layout.not_loading, (ViewGroup) mRecyclerView.getParent(), false);
                    }
                    mAdapter.addFooterView(notLoadingView);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addData(generateData(PAGE_SIZE));
//                            mCurrentCounter = mAdapter.getData().size();
                            loadedCount += PAGE_SIZE;
                        }
                    }, delayMillis);
                }
            }

        });
    }


    private List<String> generateData(int size) {

        ArrayList<String> data = new ArrayList(size);
        for (int i = 1; i <= size; i++) {
            if (((page - 1) * PAGE_SIZE + i > TOTAL_COUNTER)) break;
            else
                data.add("item " + ((page - 1) * PAGE_SIZE + i));
        }
        page++;
        return data;
    }

    private View getRealView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.head_view, null);
        view.findViewById(R.id.tv).setVisibility(View.GONE);
        view.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }

    private void initListener() {
        easyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        loadedCount = PAGE_SIZE;
                        mAdapter.openLoadMore(PAGE_SIZE);
                        mAdapter.removeAllFooterView();
//                        mCurrentCounter = PAGE_SIZE;
                        mAdapter.setNewData(generateData(6));
                        easyRefreshLayout.refreshComplete();
                        Toast.makeText(getContext(), "refresh success", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);

            }
        });
    }


}
