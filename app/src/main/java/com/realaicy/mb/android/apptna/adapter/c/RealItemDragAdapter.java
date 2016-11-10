package com.realaicy.mb.android.apptna.adapter.c;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.realaicy.mb.android.apptna.R;

import java.util.List;

/**
 * Created by luoxw on 2016/6/20.
 */
public class RealItemDragAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {
    public RealItemDragAdapter(List data) {
        super(R.layout.item_draggable_view, data);
    }



    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent,viewType);

    }


    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder) {
        if(this.mOnItemSwipeListener != null && this.itemSwipeEnabled) {
            this.mOnItemSwipeListener.onItemSwiped(viewHolder, this.getViewHolderPosition(viewHolder));
        }

        int pos = this.getViewHolderPosition(viewHolder);
        this.mData.remove(pos);
        notifyDataSetChanged();
        //this.notifyItemRemoved(viewHolder.getAdapterPosition());


    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv, item);
    }
}
