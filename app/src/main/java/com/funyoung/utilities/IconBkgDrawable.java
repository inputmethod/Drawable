package com.funyoung.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by sunhang on 17-3-21.
 */

public class IconBkgDrawable extends StateListDrawable {
    private Context mContext;
    private int mPressedBkgColor;
    private int mNormalBkgColor;
    private boolean mOuterColor;

    public IconBkgDrawable(Context context) {
        mContext = context;
    }

    public IconBkgDrawable(int normalColor, int pressedColor) {
        mOuterColor = true;
        mNormalBkgColor = normalColor;
        mPressedBkgColor = pressedColor;
    }

    public void setIconBkgColor(int normal, int pressed) {
        mOuterColor = true;
        mNormalBkgColor = normal;
        mPressedBkgColor = pressed;
    }

    public IconBkgDrawable assemble() {
        Drawable seled = new SeledDrawable();
        Drawable normal = new NormalDrawable();
        addState(KeyState.KEY_STATE_SELECTED, seled);
        addState(KeyState.KEY_STATE_PRESSED, seled);
        addState(KeyState.KEY_STATE_NORMAL, normal);

        if (!mOuterColor) {
            applyTheme();
        }

        return this;
    }

    public void applyTheme() {
        mPressedBkgColor = Color.GRAY; //SkinAccessor.FunctionView.pressedBackgroundColor();
        mNormalBkgColor = Color.TRANSPARENT;
    }

    class SeledDrawable extends BaseDrawable {
        private Paint mBkgPaint = new Paint();

        @Override
        public void draw(Canvas canvas) {
            Rect r = getBounds();

            mBkgPaint.setColor(mPressedBkgColor);
            canvas.drawRect(r.left, r.top, r.right, r.bottom, mBkgPaint);
        }
    }

    class NormalDrawable extends BaseDrawable {
        private Paint mBkgPaint = new Paint();

        @Override
        public void draw(Canvas canvas) {
            Rect r = getBounds();

            mBkgPaint.setColor(mNormalBkgColor);
            canvas.drawRect(r.left, r.top, r.right, r.bottom, mBkgPaint);
        }
    }

    abstract static class BaseDrawable extends Drawable {
        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
