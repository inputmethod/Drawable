package com.funyoung.drawing;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.funyoung.drawable.R;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by yangfeng on 2017/10/8.
 */
/*
class MyCanvas(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val MIN_ERASER_WIDTH = 20f

    var mPaths = LinkedHashMap<MyPath, PaintOptions>()
    var mBackgroundBitmap: Bitmap? = null
    private var mPaint = Paint()
    private var mPath = MyPath()
    private var mPaintOptions = PaintOptions()

    private var mListener: PathsChangedListener? = null
    private var mCurX = 0f
    private var mCurY = 0f
    private var mStartX = 0f
    private var mStartY = 0f
    private var mIsSaving = false
    private var mIsStrokeWidthBarEnabled = false
    private var mIsEraserOn = false
    private var mBackgroundColor = 0

    init {
        mPaint.apply {
            color = mPaintOptions.color
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = mPaintOptions.strokeWidth
            isAntiAlias = true
        }

        pathsUpdated()
    }

    fun setListener(listener: PathsChangedListener) {
        this.mListener = listener
    }

    fun undo() {
        if (mPaths.isEmpty())
            return

        val lastKey = mPaths.keys.lastOrNull()

        mPaths.remove(lastKey)
        pathsUpdated()
        invalidate()
    }

    fun toggleEraser(isEraserOn: Boolean) {
        mIsEraserOn = isEraserOn
        mPaintOptions.isEraser = isEraserOn
        invalidate()
    }

    fun setColor(newColor: Int) {
        mPaintOptions.color = newColor
        if (mIsStrokeWidthBarEnabled) {
            invalidate()
        }
    }

    fun updateBackgroundColor(newColor: Int) {
        mBackgroundColor = newColor
        setBackgroundColor(newColor)
        mBackgroundBitmap = null
    }

    fun setStrokeWidth(newStrokeWidth: Float) {
        mPaintOptions.strokeWidth = newStrokeWidth
        if (mIsStrokeWidthBarEnabled) {
            invalidate()
        }
    }

    fun setIsStrokeWidthBarEnabled(isStrokeWidthBarEnabled: Boolean) {
        mIsStrokeWidthBarEnabled = isStrokeWidthBarEnabled
        invalidate()
    }

    fun getBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        mIsSaving = true
        draw(canvas)
        mIsSaving = false
        return bitmap
    }

    fun drawBitmap(activity: Activity, path: String) {
        Thread({
            val size = Point()
            activity.windowManager.defaultDisplay.getSize(size)
            val options = RequestOptions()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .fitCenter()

            try {
                val builder = Glide.with(context)
                        .asBitmap()
                        .load(path)
                        .apply(options)
                        .into(size.x, size.y)

                mBackgroundBitmap = builder.get()
                activity.runOnUiThread {
                    invalidate()
                }
            } catch (e: ExecutionException) {
                val errorMsg = String.format(activity.getString(R.string.failed_to_load_image), path)
                activity.toast(errorMsg)
            }
        }).start()
    }

    fun addPath(path: MyPath, options: PaintOptions) {
        mPaths.put(path, options)
        pathsUpdated()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (mBackgroundBitmap != null) {
            val left = (width - mBackgroundBitmap!!.width) / 2
            val top = (height - mBackgroundBitmap!!.height) / 2
            canvas.drawBitmap(mBackgroundBitmap, left.toFloat(), top.toFloat(), null)
        }

        for ((key, value) in mPaths) {
            changePaint(value)
            canvas.drawPath(key, mPaint)
        }

        changePaint(mPaintOptions)
        canvas.drawPath(mPath, mPaint)

        if (mIsStrokeWidthBarEnabled && !mIsSaving) {
            drawPreviewCircle(canvas)
        }
    }

    private fun drawPreviewCircle(canvas: Canvas) {
        val res = resources
        mPaint.style = Paint.Style.FILL

        var y = height - res.getDimension(R.dimen.preview_dot_offset_y)
        canvas.drawCircle((width / 2).toFloat(), y, mPaintOptions.strokeWidth / 2, mPaint)
        mPaint.style = Paint.Style.STROKE
        mPaint.color = if (mPaintOptions.isEraser) mBackgroundColor.getContrastColor() else mPaintOptions.color.getContrastColor()
        mPaint.strokeWidth = res.getDimension(R.dimen.preview_dot_stroke_size)

        y = height - res.getDimension(R.dimen.preview_dot_offset_y)
        val radius = (mPaintOptions.strokeWidth + res.getDimension(R.dimen.preview_dot_stroke_size)) / 2
        canvas.drawCircle((width / 2).toFloat(), y, radius, mPaint)
        changePaint(mPaintOptions)
    }

    private fun changePaint(paintOptions: PaintOptions) {
        if (paintOptions.isEraser)
            mPaint.color = mBackgroundColor
        else
            mPaint.color = paintOptions.color

        mPaint.strokeWidth = paintOptions.strokeWidth
        if (paintOptions.isEraser && mPaint.strokeWidth < MIN_ERASER_WIDTH) {
            mPaint.strokeWidth = MIN_ERASER_WIDTH
        }
    }

    fun clearCanvas() {
        mBackgroundBitmap = null
        mPath.reset()
        mPaths.clear()
        pathsUpdated()
        invalidate()
    }

    private fun actionDown(x: Float, y: Float) {
        mPath.reset()
        mPath.moveTo(x, y)
        mCurX = x
        mCurY = y
    }

    private fun actionMove(x: Float, y: Float) {
        mPath.quadTo(mCurX, mCurY, (x + mCurX) / 2, (y + mCurY) / 2)
        mCurX = x
        mCurY = y
    }

    private fun actionUp() {
        mPath.lineTo(mCurX, mCurY)

        // draw a dot on click
        if (mStartX == mCurX && mStartY == mCurY) {
            mPath.lineTo(mCurX, mCurY + 2)
            mPath.lineTo(mCurX + 1, mCurY + 2)
            mPath.lineTo(mCurX + 1, mCurY)
        }

        mPaths.put(mPath, mPaintOptions)
        pathsUpdated()
        mPath = MyPath()
        mPaintOptions = PaintOptions(mPaintOptions.color, mPaintOptions.strokeWidth, mPaintOptions.isEraser)
    }

    private fun pathsUpdated() {
        mListener?.pathsChanged(mPaths.size)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = x
                mStartY = y
                actionDown(x, y)
            }
            MotionEvent.ACTION_MOVE -> actionMove(x, y)
            MotionEvent.ACTION_UP -> actionUp()
        }

        invalidate()
        return true
    }

    interface PathsChangedListener {
        fun pathsChanged(cnt: Int)
    }

    public override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val savedState = MyParcelable(superState)
        savedState.paths = mPaths
        return savedState
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is MyParcelable) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)
        mPaths = state.paths
        pathsUpdated()
    }

    internal class MyParcelable : View.BaseSavedState {
        var paths = LinkedHashMap<MyPath, PaintOptions>()

        constructor(superState: Parcelable) : super(superState)

        constructor(parcel: Parcel) : super(parcel) {
            val size = parcel.readInt()
            for (i in 0 until size) {
                val key = parcel.readSerializable() as MyPath
                val paintOptions = PaintOptions(parcel.readInt(), parcel.readFloat(), parcel.readInt() == 1)
                paths.put(key, paintOptions)
            }
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(paths.size)
            for ((path, paintOptions) in paths) {
                out.writeSerializable(path)
                out.writeInt(paintOptions.color)
                out.writeFloat(paintOptions.strokeWidth)
                out.writeInt(if (paintOptions.isEraser) 1 else 0)
            }
        }

        companion object {
            val CREATOR: Parcelable.Creator<MyParcelable> = object : Parcelable.Creator<MyParcelable> {
                override fun createFromParcel(source: Parcel) = MyParcelable(source)

                override fun newArray(size: Int) = arrayOf<MyParcelable>()
            }
        }
    }
}
 */
public class MyCanvas extends View {
    private static final float MIN_ERASER_WIDTH = 20f;

    private LinkedHashMap<MyPath, PaintOptions> mPaths = new LinkedHashMap<>();
    private Bitmap mBackgroundBitmap = null;
    private final Paint mPaint = new Paint();
    private MyPath mPath = new MyPath();
    private PaintOptions mPaintOptions = new PaintOptions();

    private PathsChangedListener mListener = null;
    private float mCurX = 0f;
    private float mCurY = 0f;
    private float mStartX = 0f;
    private float mStartY = 0f;
    private boolean mIsSaving = false;
    private boolean mIsStrokeWidthBarEnabled = false;
    private boolean mIsEraserOn = false;
    private int mBackgroundColor = 0;

    public MyCanvas(Context context) {
        this(context, null);
    }

    public MyCanvas(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(mPaintOptions.color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mPaintOptions.strokeWidth);
        mPaint.setAntiAlias(true);

        pathsUpdated();
    }

    public void setListener(PathsChangedListener listener) {
        this.mListener = listener;
    }

    // todo: is this iterator correct remove last path?
    public void undo() {
        if (mPaths.isEmpty()) {
            return;
        }

        MyPath lastKey = null;
        Iterator<MyPath> iterator = mPaths.keySet().iterator();
        while (iterator.hasNext()) {
            lastKey = iterator.next();
        }

        mPaths.remove(lastKey);
        pathsUpdated();
        invalidate();
    }

    public void toggleEraser(boolean isEraserOn) {
        mIsEraserOn = isEraserOn;
        mPaintOptions.isEraser = isEraserOn;
        invalidate();
    }

    public void setColor(int newColor) {
        mPaintOptions.color = newColor;
        if (mIsStrokeWidthBarEnabled) {
            invalidate();
        }
    }

    public void updateBackgroundColor(int newColor) {
        mBackgroundColor = newColor;
        setBackgroundColor(newColor);
        mBackgroundBitmap = null;
    }

    public void setStrokeWidth(float newStrokeWidth) {
        mPaintOptions.strokeWidth = newStrokeWidth;
        if (mIsStrokeWidthBarEnabled) {
            invalidate();
        }
    }

    public void setIsStrokeWidthBarEnabled(boolean isStrokeWidthBarEnabled) {
        mIsStrokeWidthBarEnabled = isStrokeWidthBarEnabled;
        invalidate();
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        mIsSaving = true;
        draw(canvas);
        mIsSaving = false;
        return bitmap;
    }

    public void drawBitmap(final Activity activity, final String path) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Point size = new Point();
                activity.getWindowManager().getDefaultDisplay().getSize(size);
//                RequestOptions options = new RequestOptions()
//                        .format(DecodeFormat.PREFER_ARGB_8888)
//                        .fitCenter();
                try {
                    mBackgroundBitmap = Glide.with(getContext()).load(path).asBitmap().into(size.x, size.y).get();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            invalidate();
                        }
                    });
                } catch (Exception e) {
                    String errorMsg = String.format(activity.getString(R.string.failed_to_load_image), path);
                    Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        new Thread(runnable, "MyCanvas-Drawing-thread").start();
    }

    public void addPath(MyPath path, PaintOptions options) {
        mPaths.put(path, options);
        pathsUpdated();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (null != mBackgroundBitmap) {
            int left = (getWidth() - mBackgroundBitmap.getWidth()) / 2;
            int top = (getHeight() - mBackgroundBitmap.getHeight()) / 2;
            canvas.drawBitmap(mBackgroundBitmap, left, top, null);
        }

        Iterator<MyPath> iterator = mPaths.keySet().iterator();
        while (iterator.hasNext()) {
            MyPath key = iterator.next();
            changePaint(mPaths.get(key));
            canvas.drawPath(key, mPaint);
        }

        changePaint(mPaintOptions);
        canvas.drawPath(mPath, mPaint);

        if (mIsStrokeWidthBarEnabled && !mIsSaving) {
            drawPreviewCircle(canvas);
        }
    }

    private void drawPreviewCircle(Canvas canvas) {
        Resources res = getResources();
        mPaint.setStyle(Paint.Style.FILL);

        float y = getHeight() - res.getDimension(R.dimen.preview_dot_offset_y);
        canvas.drawCircle(getWidth() / 2f, y, mPaintOptions.strokeWidth / 2, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getContrastColor(mPaintOptions.isEraser ? mBackgroundColor : mPaintOptions.color));
        mPaint.setStrokeWidth(res.getDimension(R.dimen.preview_dot_stroke_size));

        y = getHeight() - res.getDimension(R.dimen.preview_dot_offset_y);
        float radius = (mPaintOptions.strokeWidth + res.getDimension(R.dimen.preview_dot_stroke_size)) / 2;
        canvas.drawCircle(getWidth() / 2, y, radius, mPaint);
        changePaint(mPaintOptions);
    }

    public static int getContrastColor(int colorToInvert) {
        int a = ((colorToInvert >> 24) & 0xff);
        int r = 255 - ((colorToInvert >> 16) & 0xff);
        int g = 255 - ((colorToInvert >> 8) & 0xff);
        int b = 255 - ((colorToInvert) & 0xff);
        return (a << 24) + (r << 16) + (g << 8) + b;
    }

    private void changePaint(PaintOptions paintOptions) {
        if (paintOptions.isEraser)
            mPaint.setColor(mBackgroundColor);
        else
            mPaint.setColor(paintOptions.color);

        mPaint.setStrokeWidth(paintOptions.strokeWidth);
        if (paintOptions.isEraser && mPaint.getStrokeWidth() < MIN_ERASER_WIDTH) {
            mPaint.setStrokeWidth(MIN_ERASER_WIDTH);
        }
    }

    public void clearCanvas() {
        mBackgroundBitmap = null;
        mPath.reset();
        mPaths.clear();
        pathsUpdated();
        invalidate();
    }

    private void actionDown(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mCurX = x;
        mCurY = y;
    }

    private void actionMove(float x, float y) {
        mPath.quadTo(mCurX, mCurY, (x + mCurX) / 2, (y + mCurY) / 2);
        mCurX = x;
        mCurY = y;
    }

    private void actionUp() {
        mPath.lineTo(mCurX, mCurY);

        // draw a dot on click
        if (mStartX == mCurX && mStartY == mCurY) {
            mPath.lineTo(mCurX, mCurY + 2);
            mPath.lineTo(mCurX + 1, mCurY + 2);
            mPath.lineTo(mCurX + 1, mCurY);
        }

        mPaths.put(mPath, mPaintOptions);
        pathsUpdated();
        mPath = new MyPath();
        mPaintOptions = new PaintOptions(mPaintOptions.color, mPaintOptions.strokeWidth, mPaintOptions.isEraser);
    }

    private void pathsUpdated() {
        if (null != mListener) {
            mListener.pathsChanged(mPaths.size());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN : {
                mStartX = x;
                mStartY = y;
                actionDown(x, y);
                break;
            }
            case MotionEvent.ACTION_MOVE:
                actionMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                actionUp();
                break;
            default:
                break;
        }

        invalidate();
        return true;
    }

    public interface PathsChangedListener {
        void pathsChanged(int cnt);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        MyParcelable savedState = new MyParcelable(superState);
        savedState.paths = mPaths;
        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (! (state instanceof MyParcelable)) {
            super.onRestoreInstanceState(state);
            return;
        }

        MyParcelable savedState = (MyParcelable) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mPaths = savedState.paths;
        pathsUpdated();
    }

    static class MyParcelable extends View.BaseSavedState {
        public LinkedHashMap<MyPath, PaintOptions> paths = new LinkedHashMap<>();

        public MyParcelable(Parcelable superState) {
            super(superState);
        }

        public MyParcelable(Parcel parcel) {
            super(parcel);
            int size = parcel.readInt();
            for (int i = 0; i < size; i++) {
                MyPath key = (MyPath) parcel.readSerializable();
                PaintOptions paintOptions = new PaintOptions(parcel.readInt(), parcel.readFloat(), parcel.readInt() == 1);
                paths.put(key, paintOptions);
            }
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(paths.size());
            Iterator<MyPath> iterator = paths.keySet().iterator();
            while (iterator.hasNext()) {
                MyPath path = iterator.next();
                PaintOptions paintOptions = paths.get(path);
                out.writeSerializable(path);
                out.writeInt(paintOptions.color);
                out.writeFloat(paintOptions.strokeWidth);
                out.writeInt(paintOptions.isEraser ? 1 : 0);
            }
        }

        public static final Creator<MyParcelable> CREATOR = new Creator<MyParcelable>() {
            @Override
            public MyParcelable createFromParcel(Parcel in) {
                return new MyParcelable(in);
            }

            @Override
            public MyParcelable[] newArray(int size) {
                return new MyParcelable[size];
            }
        };
    }
}
