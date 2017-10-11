package com.funyoung.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 */

public class FlatCheckView extends View {
    private boolean checked;
//    private SeekBarUtils.SeekBarStateListDrawable mThumb;
    private SeekBarBackground mDrawableBkg;
    private SeekBarProgress mDrawableProgress;
    private int mStripMargin;
    private float trackWidth;
    private float thumbRadius;
    private OnCheckedListener mOnProgressChangeListener;

    private Paint paint;
    private int thumbColor = Color.WHITE;
    private int thumbShadowColor = Color.BLACK;

    public FlatCheckView(Context context) {
        super(context);
        setUp(context);
    }

    public FlatCheckView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUp(context);
    }

    private void setUp(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        trackWidth = density * 14f;
        thumbRadius = density * 10f;
        mStripMargin = 0; //Math.round(density * 10f);

        mDrawableBkg = new SeekBarBackground(context);
        mDrawableProgress = new SeekBarProgress(context);
        mDrawableBkg.setRadius(trackWidth / 2);
        mDrawableProgress.setRadius(trackWidth / 2);

        float thumbShadowRadius = density * 8;
        float thumbShadowDelta = density * 4;
        paint = new Paint();
        paint.setAntiAlias(true);
//        paint.setShadowLayer(thumbShadowRadius, 4, 6, thumbShadowColor);
    }

    public void setOnCheckedListener(OnCheckedListener onProgressChangeListener) {
        mOnProgressChangeListener = onProgressChangeListener;
    }

    public void setDrawableProgress(SeekBarProgress drawableProgress) {
        mDrawableProgress = drawableProgress;
    }

    public void setDrawableBkg(SeekBarBackground drawableBkg) {
        mDrawableBkg = drawableBkg;
    }

//    public SeekBarUtils.SeekBarStateListDrawable getThumb() {
//        return mThumb;
//    }
//
//    public void setThumb(SeekBarUtils.SeekBarStateListDrawable drawable) {
//        mThumb = drawable;
//    }

    public void setStripMargin(int margin) {
        mStripMargin = margin;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
//        if (this.checked) {
//            mThumb.setState(SeekBarUtils.State.STATE_NORMAL);
//        } else {
//            mThumb.setState(SeekBarUtils.State.STATE_DISABLED);
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int l = mStripMargin;
        int t = Math.round(height / 2 - trackWidth / 2);
        int r = width - mStripMargin;
        int b = Math.round(height / 2 + trackWidth / 2);

        if (checked) {
            if (mDrawableProgress != null) {
                mDrawableProgress.setBounds(l, t, r, b);
                mDrawableProgress.draw(canvas);
            }
        } else {
            if (mDrawableBkg != null) {
                mDrawableBkg.setBounds(l, t, r, b);
                mDrawableBkg.draw(canvas);
            }
        }

//        if (mThumb != null) {
            int centerY = height / 2;
//            Drawable drawable = mThumb.getCurrent();
//            int drawableWidth = drawable.getIntrinsicWidth();
//            int drawableHeight = drawable.getIntrinsicHeight();

//            if (checked) {
//                l = width - mStripMargin - drawableWidth;
//                r = width - mStripMargin;
//            } else {
//                l = mStripMargin;
//                r = mStripMargin + drawableWidth;
//            }

//            t = centerY - drawableHeight / 2;
//            b = centerY + drawableHeight / 2;

//            drawable.setBounds(l, t, r, b);
//            drawable.draw(canvas);

//            canvas.drawBitmap(drawableToBitmap(drawable), l, t, paint);

        paint.setColor(thumbColor);
            float centerX = checked ? width - mStripMargin - (thumbRadius - 1) : mStripMargin + (thumbRadius - 1);
//        setLayerType(LAYER_TYPE_SOFTWARE, null);//对单独的View在运行时阶段禁用硬件加速
            canvas.drawCircle(centerX, centerY, thumbRadius, paint);
//        }
    }
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

//    @Override
//    public void setEnabled(boolean enabled) {
//        super.setEnabled(enabled);
//        if (enabled) {
//            mThumb.setState(SeekBarUtils.State.STATE_NORMAL);
//        } else {
//            mThumb.setState(SeekBarUtils.State.STATE_DISABLED);
//        }
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleActionDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                handleActionMove(event);
                break;
            case MotionEvent.ACTION_UP:
                handleActionUp(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                handleActionCancel(event);
                break;
        }

        return true;
    }

    private void handleActionDown(MotionEvent event) {
//        mThumb.setState(SeekBarUtils.State.STATE_PRESSED);
        invalidate();
    }

    private void handleActionMove(MotionEvent event) {
        invalidate();
    }

    private void handleActionUp(MotionEvent event) {
        checked = !checked;
        mOnProgressChangeListener.onCheckedChanged(this, checked);

//        mThumb.setState(SeekBarUtils.State.STATE_NORMAL);
        invalidate();
    }

    private void handleActionCancel(MotionEvent event) {
//        mThumb.setState(SeekBarUtils.State.STATE_NORMAL);
        invalidate();
    }

    //    public void initSeekBar(Context context) {
//        mDrawableBkg = new SeekBarBackground(context);
//        mDrawableBkg.setRadius(trackWidth / 2);
//        mDrawableProgress = new SeekBarProgress(context);
//    }

    public void applyThumbColor(int color, int shadowColor) {
        thumbColor = color;
        if (thumbColor != shadowColor) {
            thumbShadowColor = shadowColor;
        } else {
            thumbShadowColor = color == Color.GRAY ? Color.BLACK : Color.GRAY;
        }
    }

    public void applyTrackColor(int normalColor, int highlightColor) {
        mDrawableBkg.setColor(normalColor);
        mDrawableProgress.setColor(highlightColor);
    }
}
