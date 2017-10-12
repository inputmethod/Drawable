package com.typany.keyboard.sound;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by yangfeng on 2017/10/12.
 */

public class SoundUtils {
    private final static String TAG = SoundUtils.class.getSimpleName();

    public static String requestSoundApiSuffix(Context context) {
//        String tail = new BasicInfo(context).getDownLoadParam();
//        String suffix = "notifications?id=" + getLastRequestId(context)+"&"+tail;
//        Log.d(TAG, "requestAlarmApiSuffix, suffix = " + suffix);
//        return suffix;
        return "soundres";
    }

    public static SoundItem convert(SoundSkinItem item) {
        SoundItem soundItem = new SoundItem(item.getId(), item.getSn(), false);
        soundItem.setRemote(true);
        soundItem.setRemoteFileSize(item.getFs());
        soundItem.setRemoteUrl(item.getSdu());
        soundItem.setPreviewUrl(item.getPpu());
        return soundItem;
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
