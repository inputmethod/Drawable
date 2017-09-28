package com.funyoung.views;

import android.graphics.Color;

/**
 * Created by yangfeng on 2017/9/27.
 */

class SoundPickerUtils {
    public static int getAlphaColor(int color, float ratio) {
        int alpha = (int) (Color.alpha(color) * ratio);
        return Color.argb(alpha, 0, 0, 0) | (0x00FFFFFF & color);
    }
}
