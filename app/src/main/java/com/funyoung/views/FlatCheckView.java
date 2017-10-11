package com.funyoung.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 */

public class FlatCheckView extends View {
    private boolean checked;
//    private SeekBarUtils.SeekBarStateListDrawable mThumb;
//    private SeekBarBackground mDrawableBkg;
//    private SeekBarProgress mDrawableProgress;
    private int mStripMargin;
    private float trackRadius;
    private float thumbRadius;
    private OnCheckedListener mOnProgressChangeListener;

    private Paint paint;
    private int thumbColor = Color.WHITE;
    private int thumbShadowColor = Color.BLACK;

    private int trackColorOff = Color.GRAY;
    private int trackColorOn = Color.RED;
    private final RectF roundRect = new RectF();

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
        trackRadius = density * 7f;
        thumbRadius = density * 10f;
        mStripMargin = 0; //Math.round(density * 10f);

//        mDrawableBkg = new SeekBarBackground(context);
//        mDrawableProgress = new SeekBarProgress(context);
//        mDrawableBkg.setRadius(trackWidth / 2);
//        mDrawableProgress.setRadius(trackWidth / 2);

        float thumbShadowRadius = density * 8;
        float thumbShadowDelta = density * 4;
        paint = new Paint();
        paint.setAntiAlias(true);
//        paint.setShadowLayer(thumbShadowRadius, 4, 6, thumbShadowColor);
    }

    public void setOnCheckedListener(OnCheckedListener onProgressChangeListener) {
        mOnProgressChangeListener = onProgressChangeListener;
    }

//    public void setDrawableProgress(SeekBarProgress drawableProgress) {
//        mDrawableProgress = drawableProgress;
//    }
//
//    public void setDrawableBkg(SeekBarBackground drawableBkg) {
//        mDrawableBkg = drawableBkg;
//    }

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
        int t = Math.round(height / 2 - trackRadius);
        int r = width - mStripMargin;
        int b = Math.round(height / 2 + trackRadius);

        roundRect.left = l;
        roundRect.top = t;
        roundRect.right = r;
        roundRect.bottom = b;

        final int centerY = height / 2;

        final float centerX;
        final int trackColor;
        if (checked) {
            trackColor = trackColorOn;
            centerX = width - mStripMargin - (thumbRadius - 1);
        } else {
            trackColor = trackColorOff;
            centerX = mStripMargin + (thumbRadius - 1);
        }

        paint.setColor(trackColor);
        canvas.drawRoundRect(roundRect, trackRadius, trackRadius, paint);

        paint.setColor(thumbColor);
        canvas.drawCircle(centerX, centerY, thumbRadius, paint);
    }

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

    public void applyThumbColor(int color, int shadowColor) {
        thumbColor = color;
        if (thumbColor != shadowColor) {
            thumbShadowColor = shadowColor;
        } else {
            thumbShadowColor = color == Color.GRAY ? Color.BLACK : Color.GRAY;
        }
    }

    public void applyTrackColor(int normalColor, int highlightColor) {
        trackColorOff = normalColor;
        trackColorOn = highlightColor;
    }
}
