package com.realaicy.mb.android.apptna.adapter.c;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.realaicy.mb.android.apptna.R;
import com.realaicy.mb.android.apptna.data.DataServer;
import com.realaicy.mb.android.apptna.entity.c.Status;
import com.realaicy.mb.android.apptna.ui.fragment.msg.MsgAllFragment;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class RealSwipeAdapter extends BaseItemDraggableAdapter<Status, BaseViewHolder> {

    private static final String TAG = RealSwipeAdapter.class.getSimpleName();


    public RealSwipeAdapter() {
        super( R.layout.tweet, DataServer.getSampleData(100));
    }

    public RealSwipeAdapter(int dataSize) {
        super( R.layout.tweet, DataServer.getSampleData(dataSize));
    }


    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder) {
        int atemp = getViewHolderPosition(viewHolder);
        Log.d(TAG, "atemp: " + atemp);

        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.onItemSwiped(viewHolder, atemp);
        }
        int pos = getViewHolderPosition(viewHolder);
        mData.remove(pos);
        Log.d(TAG, "pos: " + pos);


        int btemp = viewHolder.getAdapterPosition();
        Log.d(TAG, "btemp: " + btemp);

        //notifyDataSetChanged();
        notifyItemRemoved(btemp);
        //this line below gives you the animation and also updates the
        //list items after the deleted item

        int ctemp = getItemCount();
        Log.d(TAG, "ctemp: " + ctemp);

        notifyItemRangeChanged(btemp, ctemp-1);
        //notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setText(R.id.tweetName, item.getUserName())
                .setText(R.id.tweetText, item.getText())
                .setText(R.id.tweetDate, item.getCreatedAt())
                .setVisible(R.id.tweetRT, item.isRetweet())
                .addOnClickListener(R.id.tweetAvatar)
                .addOnClickListener(R.id.tweetName);

//        Glide.with(mContext).load(item.getUserAvatar()).crossFade().placeholder(R.mipmap.def_head).transform(
//                new GlideCircleTransform(mContext)).into((ImageView) helper.getView(R.id.tweetAvatar));
    }


}
