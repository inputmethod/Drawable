package com.funyoung.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.funyoung.drawable.R;

/**
 * Created by sunhang on 17-8-17.
 */

public class UtilsForSh {
    public static final String TAG_EMOJI_UI = "TAG_EMOJI_UI";
    public static final String TAG_ADS_SCROLL = "TAG_ADS_SCROLL";
    public static final String TAG_REWARDS_ADS = "TAG_REWARDS_ADS";
    public static final String TAG_ADVANCED_ADS = "TAG_ADVANCED_ADS";

    public static FrameLayout initiateAdsFrameLayout(Context context) {
        FrameLayout fl = new InterceptFocusRequestFrameLayout(context);
        fl.setClickable(false);

        return fl;
    }

    static class InterceptFocusRequestFrameLayout extends FrameLayout {

        public InterceptFocusRequestFrameLayout(@NonNull Context context) {
            super(context);
        }

        @Override
        protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
//            return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
            return false;
        }
    }


    public static PlaceHolderDrawable generatePlaceHolderDrawable(Context context) {
        return new PlaceHolderDrawable(context);
    }

    public static class PlaceHolderDrawable extends BaseDrawable {
        private Drawable base;

        public PlaceHolderDrawable(Context context) {
            base = ContextCompat.getDrawable(context, R.mipmap.ic_launcher);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            base.draw(canvas);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);

            base.setBounds(bounds);
        }
    }

    public static class BaseAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {}
    }
}
