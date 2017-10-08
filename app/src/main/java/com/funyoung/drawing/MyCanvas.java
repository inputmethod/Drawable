package com.funyoung.drawing;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yangfeng on 2017/10/8.
 */

public class MyCanvas extends View {
    private final Paint mPaint = new Paint();

    public MyCanvas(Context context) {
        this(context, null);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
}
