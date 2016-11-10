package com.realaicy.mb.android.apptna.ui.fragment.msg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.realaicy.mb.android.apptna.R;
import com.realaicy.mb.android.apptna.adapter.c.RealSwipeAdapter;
import com.realaicy.mb.android.apptna.data.DataServer;

public class MsgAllFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener{

    private View mRootView;

    private EasyRefreshLayout easyRefreshLayout;

    private RecyclerView mRecyclerView;
    //private QuickAdapter mQuickAdapter;
    private View notLoadingView;
    private static final int TOTAL_COUNTER = 18;
    private static final int PAGE_SIZE = 6;
    private int delayMillis = 1000;
    private int mCurrentCounter = 0;
    private boolean isErr;

    private static final String TAG = MsgAllFragment.class.getSimpleName();
    private RealSwipeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            Log.e("666", "MsgUnReadFragment");
            mRootView = inflater.inflate(R.layout.fr_msg_all, container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        easyRefreshLayout = (EasyRefreshLayout) mRootView.findViewById(R.id.easylayout);
        easyRefreshLayout.setEnableLoadMore(false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);


        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(20);
        paint.setColor(Color.BLACK);
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "view swiped start: " + pos);

                easyRefreshLayout.setEnablePullToRefresh(false);

                BaseViewHolder holder = ((BaseViewHolder)viewHolder);
                //holder.setTextColor(R.id.realSwipeContainer, Color.WHITE);
                holder.setBackgroundColor(R.id.realSwipeContainer, Color.WHITE);

                ((CardView)viewHolder.itemView).setCardBackgroundColor(Color.YELLOW);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "View reset: " + pos);

                easyRefreshLayout.setEnablePullToRefresh(true);


                BaseViewHolder holder = ((BaseViewHolder)viewHolder);
//                holder.setTextColor(R.id.realSwipeContainer, Color.BLACK);
                holder.setBackgroundColor(R.id.realSwipeContainer, Color.WHITE);
                ((CardView)viewHolder.itemView).setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "View Swiped: " + pos);
                Log.d(TAG, "mAdapter getItemCount " + mAdapter.getItemCount());

                easyRefreshLayout.setEnablePullToRefresh(true);

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(getContext(), R.color.color_light_blue));
                canvas.drawText("Just some text", 0, 40, paint);
            }
        };

        initAdapter();

        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        //mItemDragAndSwipeCallback.setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
        //mAdapter.enableDragItem(mItemTouchHelper);
        //mAdapter.setOnItemDragListener(listener);

        //mAdapter.addHeaderView(getView());
        //mAdapter.addFooterView(getView());


        //addHeadView();
        mRecyclerView.setAdapter(mAdapter);

        initListener();


        return mRootView;
    }


    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    mAdapter.loadComplete();
                    if (notLoadingView == null) {
                        notLoadingView = getActivity().getLayoutInflater().inflate(R.layout.not_loading, (ViewGroup) mRecyclerView.getParent(), false);
                    }
                    mAdapter.addFooterView(notLoadingView);
                } else {
                    if (isErr) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
                                mCurrentCounter = mAdapter.getData().size();
                            }
                        }, delayMillis);
                    } else {
                        isErr = true;
                        Toast.makeText(getActivity(), R.string.network_err, Toast.LENGTH_LONG).show();
                        mAdapter.showLoadMoreFailedView();

                    }
                }
            }

        });
    }


    private void initAdapter() {
        mAdapter = new RealSwipeAdapter(PAGE_SIZE);
        mAdapter.openLoadAnimation();
        mAdapter.openLoadMore(PAGE_SIZE);
        mRecyclerView.setAdapter(mAdapter);
        mCurrentCounter = mAdapter.getData().size();
        mAdapter.setOnLoadMoreListener(this);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addHeadView() {
        View headView = getActivity().getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) mRecyclerView.getParent(), false);
//        ((TextView) headView.findViewById(R.id.tv)).setText("click use custom loading view");
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "use ok!", Toast.LENGTH_LONG).show();
            }
        });
        mAdapter.addHeaderView(headView);
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

                        mAdapter.setNewData(DataServer.getSampleData(PAGE_SIZE));
                        mAdapter.openLoadMore(PAGE_SIZE);
                        mAdapter.removeAllFooterView();
                        mCurrentCounter = PAGE_SIZE;
                        isErr = false;
                        easyRefreshLayout.refreshComplete();
                        Toast.makeText(getContext(), "refresh success", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);

            }
        });
    }



}


