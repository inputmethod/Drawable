package com.funyoung.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.funyoung.drawable.R;

/**
 * Created by yangfeng on 2017/10/11.
 */
public class SoundCardLayout  extends RelativeLayout implements SoundBorderState {
    private static final int STATE_NORMAL = 0;
    private static final int STATE_DOWNLOADING = 1;
    private static final int STATE_SELECTED = 2;

    private int currentState = STATE_NORMAL;

    private Paint mBorderPaint;
    private Drawable mSeledImage4_4;
    private Drawable mSeledImageCheck4_4;
    private float mStrokeWidth;

    private Drawable downloadingImageMask;

    public SoundCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        mStrokeWidth = Math.round(getResources().getDisplayMetrics().density * 1);
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStrokeWidth(mStrokeWidth);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mSeledImage4_4 = ContextCompat.getDrawable(getContext(), R.drawable.skin_image_seled);
        mSeledImageCheck4_4 = ContextCompat.getDrawable(getContext(), R.drawable.sound_item_selected_white);
        downloadingImageMask = ContextCompat.getDrawable(getContext(), R.drawable.sound_item_dowloading);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        Rect bound = new Rect(0, 0, width, height);

        if (STATE_SELECTED == currentState) {
            mSeledImage4_4.setBounds(bound);
            mSeledImage4_4.draw(canvas);

            int w = mSeledImageCheck4_4.getIntrinsicWidth();
            int h = mSeledImageCheck4_4.getIntrinsicHeight();
            mSeledImageCheck4_4.setBounds(width - w, height - h, width, height);
            mSeledImageCheck4_4.draw(canvas);
        } else if (STATE_DOWNLOADING == currentState) {
            downloadingImageMask.setBounds(bound);
            downloadingImageMask.draw(canvas);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = width; // Math.round(width * 0.7128514056224899f);
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.getMode(heightMeasureSpec)));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (!changed) {
            return;
        }
    }

    @Override
    public void setSeled(boolean seled) {
        if (seled) {
            currentState = STATE_SELECTED;
        } else {
            currentState = STATE_NORMAL;
        }
        setSelected(seled);
    }

    @Override
    public void setDownloading(boolean downloading) {
        if (downloading) {
            currentState = STATE_DOWNLOADING;
        } else {
            currentState = STATE_NORMAL;
        }
    }

    private static final boolean TEST_DOWNLOADING = true;
}
