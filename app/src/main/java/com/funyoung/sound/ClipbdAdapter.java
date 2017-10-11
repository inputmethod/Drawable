package com.funyoung.sound;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.Recycler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.funyoung.drawable.R.id;
import com.funyoung.drawable.R.layout;
import com.funyoung.sound.ClipbdAdapter.ViewHolder;

import java.lang.reflect.Field;
import java.util.List;

public class ClipbdAdapter extends Adapter<ViewHolder>{

    private final Context mCtx;
    private OnClipDataClickListener mOnClipDataClickListener;
    private final RecyclerView mRv;

    private final List<DataItem> dataSet;
    public ClipbdAdapter(Context ctx, RecyclerView rv, List<DataItem> data) {
        this.mCtx = ctx;
        this.mRv = rv;
        dataSet = data;
    }

    public void setOnClipDataClickListener(OnClipDataClickListener l) {
        this.mOnClipDataClickListener = l;
    }

    @Override
    public ClipbdAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(this.mCtx, layout.including_sound_3_item_container, null);
        ClipbdAdapter.ViewHolder holder = new ClipbdAdapter.ViewHolder(view);
        view.setTag(holder);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mOnClipDataClickListener) {
//                    mOnClipDataClickListener.onClipData();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ClipbdAdapter.ViewHolder holder, int position) {
        DataItem item = this.dataSet.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return this.dataSet.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public ImageView indicator;


        public ViewHolder(View itemView) {
            super(itemView);
            this.icon = itemView.findViewById(id.sound_icon_view);
            this.indicator = itemView.findViewById(id.iv_select_indicator);

            itemView.setTag(this);
        }

        public void bind(DataItem item) {
//            icon.setImageResource();
        }
    }
}
