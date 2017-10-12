package com.funyoung.sound;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.funyoung.drawable.R.id;
import com.funyoung.drawable.R.layout;
import com.funyoung.sound.SoundAdapter.ViewHolder;
import com.funyoung.utilities.UtilsForSh;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.typany.keyboard.sound.SoundItem;
import com.typany.keyboard.sound.SoundSkinItem;
import com.typany.keyboard.sound.SoundUtils;

import java.util.List;

public class SoundAdapter extends Adapter<ViewHolder>{
    private static final String TAG = SoundAdapter.class.getSimpleName();

    private final Context mCtx;
    private OnClipDataClickListener mOnClipDataClickListener;
//    private final RecyclerView mRv;

    private final DisplayImageOptions mOptions;

    private final List<SoundItem> dataSet;
    public SoundAdapter(Context ctx, RecyclerView rv, List<SoundItem> data) {
        this.mCtx = ctx;
//        this.mRv = rv;
        dataSet = data;

        SoundUtils.initImageLoader(ctx);
        mOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .showImageOnFail(UtilsForSh.generatePlaceHolderDrawable(ctx))
                .showImageOnLoading(UtilsForSh.generatePlaceHolderDrawable(ctx))
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();
    }

    public void setOnClipDataClickListener(OnClipDataClickListener l) {
        this.mOnClipDataClickListener = l;
    }

    @Override
    public SoundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(this.mCtx, layout.including_sound_3_item_container, null);
        SoundAdapter.ViewHolder holder = new SoundAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundItem model = (SoundItem) view.getTag();
                if (null == mOnClipDataClickListener) {
                    Snackbar.make(view, model.toString(), Snackbar.LENGTH_SHORT).show();
                } else {
                    mOnClipDataClickListener.onClipData(model.toString());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(SoundAdapter.ViewHolder holder, int position) {
        SoundItem item = this.dataSet.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return this.dataSet.size();
    }

    public void addSoundSkinList(List<SoundSkinItem> itemList) {
        for (SoundSkinItem item : itemList) {
            int position = dataSet.size();
            dataSet.add(SoundUtils.convert(item));
            notifyItemChanged(position);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public ImageView indicator;


        public ViewHolder(View itemView) {
            super(itemView);
            this.icon = itemView.findViewById(id.sound_icon_view);
            this.indicator = itemView.findViewById(id.iv_select_indicator);
        }

        public void bind(SoundItem model) {
            checkAndSetSelected(model);
            itemView.setTag(model);
            itemView.setTag(id.sound_icon_view, this);
            if (model.isRemote()) {
                ImageLoader.getInstance().displayImage(model.getPreviewUrl(), icon, mOptions);
            } else {
                Log.i(TAG, "bind, skip those are not remote item: " + model);
//                ImageLoader.getInstance().displayImage(SoundPickerUtils.getLargeIconUrl(model.getFolder()), holder.previewImageView, mOptions);
            }
        }

        // todo: not implement
        void checkAndSetSelected(SoundItem model) {
            Log.i(TAG, "checkAndSetSelected, skip not implement of selected remote item: " + model);
            //borderState.setSeled(SoundPickerUtils.isSelected(itemView.getContext(), model));
        }
    }
}
