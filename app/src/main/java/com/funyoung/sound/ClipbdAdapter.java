package com.funyoung.sound;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.funyoung.drawable.R;
import com.funyoung.utilities.CompatibilityUtils;
import com.funyoung.utilities.IconBkgDrawable;
import com.funyoung.utilities.LayoutUtils;
import com.funyoung.utilities.UIUtil;

import java.lang.reflect.Field;
import java.util.List;

public class ClipbdAdapter extends RecyclerView.Adapter<ClipbdAdapter.ViewHolder>{

    private Context mCtx;
    private OnClipDataClickListener mOnClipDataClickListener;
    private RecyclerView mRv;

    private int mTipsColor;
    private int mClipDataColor;
    private int mItemBkgColor;
    private int mDelBkgNormalColor;
    private int mDelBkgPressedColor;
    private int mItemBkgPressedColor;

    private final List<DataItem> dataSet;
    public ClipbdAdapter(Context ctx, RecyclerView rv, List<DataItem> data) {
        mCtx = ctx;
        mRv = rv;
        this.dataSet = data;
    }

    public void setOnClipDataClickListener(OnClipDataClickListener l) {
        mOnClipDataClickListener = l;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mCtx, R.layout.including_sound_4_item_container, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(-1, -2);
        view.setLayoutParams(lp);

        ViewHolder holder = new ViewHolder(view);

        applySkin(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataItem item = dataSet.get(position);
        holder.tv.setText(item.str);
        holder.tv.setTag(item);

        if (LayoutUtils.isRTL()) {
            holder.tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        } else {
            holder.tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        }

        applySkin(holder);
        applySkin(holder, item);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void update() {
        int count = mRv.getChildCount();
        for (int i = 0; i < count; i++) {
            View itemView = mRv.getChildAt(i);
            final ViewHolder holder = (ViewHolder) itemView.getTag();

            if (LayoutUtils.isRTL()) {
                holder.tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            } else {
                holder.tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            }

            // for changing launguage
            DataItem item = (DataItem) holder.tv.getTag();
            holder.tv.setText(item.str);
        }

        try {
            Field f = RecyclerView.class.getDeclaredField("mRecycler");
            f.setAccessible(true);
            RecyclerView.Recycler recycler = (RecyclerView.Recycler) f.get(mRv);

            f = RecyclerView.Recycler.class.getDeclaredField("mCachedViews");
            f.setAccessible(true);
            final List<RecyclerView.ViewHolder> cachedViews = (List<RecyclerView.ViewHolder>) f.get(recycler);
            for (RecyclerView.ViewHolder h : cachedViews) {
                ViewHolder holder = (ViewHolder) h;

                if (LayoutUtils.isRTL()) {
                    holder.tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                } else {
                    holder.tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                }

                // for changing launguage
                DataItem item = (DataItem) holder.tv.getTag();
                holder.tv.setText(item.str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public ImageView icon;
        public ImageView indicator;


        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            icon = itemView.findViewById(R.id.sound_icon_view);
            indicator = itemView.findViewById(R.id.iv_select_indicator);

            itemView.setTag(this);
        }
    }

    private void applySkin(ViewHolder holder, DataItem item) {
        if (item.isTips) {
            holder.tv.setTextColor(mTipsColor);
        } else {
            holder.tv.setTextColor(mClipDataColor);
        }
    }

    private void applySkin(ViewHolder holder) {
//        IconBkgDrawable d = null;
//        Drawable drawable = holder.delete.getBackground();
//        if (drawable == null || drawable instanceof  IconBkgDrawable == false) {
//            d = new IconBkgDrawable(mDelBkgNormalColor, mDelBkgPressedColor).assemble();
//            CompatibilityUtils.callViewSetBackground(holder.delete, d);
//        } else {
//            d = (IconBkgDrawable) drawable;
//            d.setIconBkgColor(mDelBkgNormalColor, mDelBkgPressedColor);
//        }

        IconBkgDrawable d1 = null;
        Drawable drawable1 = holder.tv.getBackground();

        int newPressedColor = UIUtil.mixColor(mItemBkgColor, mItemBkgPressedColor);
        if (drawable1 == null || drawable1 instanceof  IconBkgDrawable == false) {
            d1 = new IconBkgDrawable(mItemBkgColor, newPressedColor).assemble();
            CompatibilityUtils.callViewSetBackground(holder.tv, d1);
        } else {
            d1 = (IconBkgDrawable) drawable1;
            d1.setIconBkgColor(mItemBkgColor, newPressedColor);
        }

        holder.itemView.setBackgroundColor(mItemBkgColor);
    }

    public void loadSkin() {
//        mTipsColor = SkinAccessor.Clipboard.tipsColor();
//        mClipDataColor = SkinAccessor.Clipboard.clipdataColor();
//        mItemBkgColor = SkinAccessor.SettingBoard.backgroundColor();
//        mItemBkgPressedColor = SkinAccessor.Clipboard.clipDataBkgPressedColor();
//
//
//        mDelIconColor = SkinAccessor.SettingBoard.iconNormalColor();
//        mDelBkgNormalColor = SkinAccessor.Clipboard.delBkgNormalColor();
//        mDelBkgPressedColor = SkinAccessor.Clipboard.delBkgPressedColor();

        applyViewsSkin();
    }

    private void applyViewsSkin() {
        int count = mRv.getChildCount();
        for (int i = 0; i < count; i++) {
            View itemView = mRv.getChildAt(i);
            final ViewHolder holder = (ViewHolder) itemView.getTag();
            applySkin(holder);
            DataItem item = (DataItem) holder.tv.getTag();
            if (item != null) {
                applySkin(holder, item);
            }
        }

        try {
            Field f = RecyclerView.class.getDeclaredField("mRecycler");
            f.setAccessible(true);
            RecyclerView.Recycler recycler = (RecyclerView.Recycler) f.get(mRv);

            f = RecyclerView.Recycler.class.getDeclaredField("mCachedViews");
            f.setAccessible(true);
            final List<RecyclerView.ViewHolder> cachedViews = (List<RecyclerView.ViewHolder>) f.get(recycler);
            for (RecyclerView.ViewHolder h : cachedViews) {
                ViewHolder holder = (ViewHolder) h;
                applySkin(holder);
                DataItem item = (DataItem) holder.tv.getTag();
                if (item != null) {
                    applySkin(holder, item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
