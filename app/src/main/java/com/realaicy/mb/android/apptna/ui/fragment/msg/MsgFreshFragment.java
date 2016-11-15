package com.realaicy.mb.android.apptna.ui.fragment.msg;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.realaicy.mb.android.apptna.R;
import com.realaicy.mb.android.apptna.model.User;
import com.realaicy.mb.android.apptna.thirdlibext.swipemenu.VerticalSpaceItemDecoration;
import com.tubb.smrv.SwipeHorizontalMenuLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MsgFreshFragment extends Fragment {

    private View mRootView;

    private EasyRefreshLayout easyRefreshLayout;


    protected Context mContext;
    protected List<User> users;
    protected RecyclerView.Adapter mAdapter;
    protected com.tubb.smrv.SwipeMenuRecyclerView mRecyclerView;
    private Random random = new Random();


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

        mContext = getActivity();
        users = getUsers();

        mRecyclerView = (com.tubb.smrv.SwipeMenuRecyclerView) mRootView.findViewById(R.id.listView);
        mAdapter = new SimpleRvAppAdapter(getContext(), users);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(3));



        return mRootView;

    }

    protected List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        for (int i=0; i<100; i++){
            User user = new User();
            user.setUserId(i+1000);
            user.setUserName("Pobi "+(i+1));
            int num = random.nextInt(4);
            int photoRes = 0;
            if(num == 0){
                photoRes = R.drawable.one;
            }else if(num == 1){
                photoRes = R.drawable.two;
            }else if(num == 2){
                photoRes = R.drawable.three;
            }else if(num == 3){
                photoRes = R.drawable.four;
            }
            user.setPhotoRes(photoRes);
            userList.add(user);
        }
        return userList;
    }

    private class SimpleRvAppAdapter  extends RecyclerView.Adapter{

        protected static final int VIEW_TYPE_ENABLE = 0;
        protected static final int VIEW_TYPE_DISABLE = 1;

        List<User> users;

        public SimpleRvAppAdapter(Context context, List<User> users) {
            this.users = users;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_simple, parent, false);
            return new SimpleRvViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder vh, final int position) {
            final User user = users.get(position);
            final SimpleRvViewHolder myViewHolder = (SimpleRvViewHolder)vh;
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Hi " + user.getUserName(), Toast.LENGTH_SHORT).show();
                }
            });
            myViewHolder.btGood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(myViewHolder.itemView.getContext(), "Good", Toast.LENGTH_SHORT).show();
                }
            });
            myViewHolder.btOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Open " + user.getUserName(), Toast.LENGTH_SHORT).show();
                }
            });
            myViewHolder.btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // must close normal
                    myViewHolder.sml.smoothCloseMenu();
                    users.remove(vh.getAdapterPosition());
                    mAdapter.notifyItemRemoved(vh.getAdapterPosition());
                }
            });

            myViewHolder.btLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Left click", Toast.LENGTH_SHORT).show();
                }
            });

            myViewHolder.tvName.setText(user.getUserName());
            boolean swipeEnable = swipeEnableByViewType(getItemViewType(position));
            myViewHolder.tvSwipeEnable.setText(swipeEnable ? "swipe on" : "swipe off");
            myViewHolder.sml.setSwipeEnable(swipeEnable);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        protected boolean swipeEnableByViewType(int viewType) {
            if(viewType == VIEW_TYPE_ENABLE)
                return true;
            else
                return viewType != VIEW_TYPE_DISABLE;
        }
    }

    public static class SimpleRvViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvSwipeEnable;
        View btGood;
        View btOpen;
        View btDelete;
        View btLeft;
        SwipeHorizontalMenuLayout sml;
        public SimpleRvViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvSwipeEnable = (TextView) itemView.findViewById(R.id.tvSwipeEnable);
            btGood = itemView.findViewById(R.id.btGood);
            btOpen = itemView.findViewById(R.id.btOpen);
            btDelete = itemView.findViewById(R.id.btDelete);
            btLeft = itemView.findViewById(R.id.btLeft);
            sml = (SwipeHorizontalMenuLayout) itemView.findViewById(R.id.sml);
        }
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
                        
                        Toast.makeText(getContext(), "refresh success", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);

            }
        });
    }


}
