package com.realaicy.mb.android.apptna.ui.fragment.msg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.realaicy.mb.android.apptna.R;
import com.realaicy.mb.android.apptna.adapter.c.RealItemDragAdapter;
import com.realaicy.mb.android.apptna.thirdlibext.RealItemSwipeCallback;
import com.realaicy.mb.android.apptna.thirdlibext.RowModel;
import com.realaicy.mb.android.apptna.thirdlibext.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MsgReadedFragment extends Fragment implements RecyclerTouchListener.RecyclerTouchListenerHelper,
        BaseQuickAdapter.RequestLoadMoreListener{
    private static final String TAG = MsgReadedFragment.class.getSimpleName();

    private View mRootView;

    private EasyRefreshLayout easyRefreshLayout;



    RecyclerView mRecyclerView;
    RealItemDragAdapter mAdapter;
    String[] dialogItems;
    private RecyclerTouchListener onTouchListener;
    private OnActivityTouchListener touchListener;


    private List<String> mData;

    private int page = 1;
    private int loadedCount = 6;
    private static final int TOTAL_COUNTER = 26;
    private static final int PAGE_SIZE = 6;
    private View notLoadingView;
    private int delayMillis = 500;
    private ItemTouchHelper mItemTouchHelper;
    private RealItemSwipeCallback mItemDragAndSwipeCallback;

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

        easyRefreshLayout = (EasyRefreshLayout) mRootView.findViewById(R.id.easylayout);
        easyRefreshLayout.setEnableLoadMore(false);


        dialogItems = new String[25];
        for (int i = 0; i < 25; i++) {
            dialogItems[i] = String.valueOf(i + 1);
        }

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        //mAdapter = new MainAdapter(getContext(), getData());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mData = generateData(6);
        mAdapter = new RealItemDragAdapter(mData);
        loadedCount = mAdapter.getData().size();


        mAdapter.openLoadAnimation();
        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.enableSwipeItem();


        mItemDragAndSwipeCallback = new RealItemSwipeCallback(mAdapter);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.RIGHT);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "view swiped start: " + pos);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "View reset: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
                holder.setTextColor(R.id.mainText, Color.BLACK);
                ((RelativeLayout) viewHolder.itemView).setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "View Swiped: " + pos);
                if (mAdapter.getData().size() < PAGE_SIZE && loadedCount < TOTAL_COUNTER) {
                    Log.d(TAG, "after swipe Need Load More: ");
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

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(getContext(), R.color.color_light_blue));
            }
        };
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
        mRecyclerView.setAdapter(mAdapter);



        onTouchListener = new RecyclerTouchListener(getActivity(), mRecyclerView);
        onTouchListener
                .setIndependentViews(R.id.rowButton)
                .setViewsToFade(R.id.rowButton)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        //ToastUtil.makeToast(getContext(), "Row " + (position + 1) + " clicked!");
                        ToastUtil.makeToast(getContext(), "Row " + mData.get(position) + " clicked!");
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                        ToastUtil.makeToast(getContext(), "Button in row " + (position + 1) + " clicked!");
                    }
                })
//                .setLongClickable(true, new RecyclerTouchListener.OnRowLongClickListener() {
//                    @Override
//                    public void onRowLongClicked(int position) {
//                        ToastUtil.makeToast(getContext(), "Row " + (position + 1) + " long clicked!");
//                    }
//                })
                .setSwipeOptionViews(R.id.add, R.id.edit, R.id.change)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        String message = "";
                        if (viewID == R.id.add) {
                            message += "Add";
                        } else if (viewID == R.id.edit) {
                            message += "Edit";
                        } else if (viewID == R.id.change) {
                            message += "Change";
                        }
                        message += " clicked for row " + (position + 1);
                        ToastUtil.makeToast(getContext(), message);
                    }
                })
                ;


        mRootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (touchListener != null) touchListener.getTouchCoordinates(event);
                return true;
            }
        });


        initListener();


        return mRootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.addOnItemTouchListener(onTouchListener); }

    @Override
    public void onPause() {
        super.onPause();
        mRecyclerView.removeOnItemTouchListener(onTouchListener);
    }


    @Override
    public void setOnActivityTouchListener(OnActivityTouchListener listener) {
        this.touchListener = listener;
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
}
