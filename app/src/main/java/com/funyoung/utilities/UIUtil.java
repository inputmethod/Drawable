package com.funyoung.utilities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by sunhang on 15-5-22.
 */
public class UIUtil {
    private static Paint mPaintMeasure;
    private static Rect mBoundsMeasure;
    private static float mA,mB; // y = a * x + b; x represents text size, y represents font height
    static {
        mBoundsMeasure = new Rect();
        mPaintMeasure = new Paint();
        mPaintMeasure.setTypeface(Typeface.SANS_SERIF);
        Paint paintO = new Paint();
        paintO.setTextSize(0.0f);
        mB = paintO.getFontMetrics().bottom - paintO.getFontMetrics().top;
        paintO.setTextSize(50.f);
        mA = (paintO.getFontMetrics().bottom - paintO.getFontMetrics().top - mB) / 50.0f;
    }

    public static final int NIGHT_MODE_COLOR = 0x4C000000;

    public static float calcTextSize(final float desTextHeight){
       return  (desTextHeight - mB) / mA;
    }

    /**
     * calculate the textsize
     * @param text
     * @param defaultTextSize
     * @param width
     * @param height
     * @return
     */
    public static float calcTextSize(String text, float defaultTextSize, float width, float height) {
        float textSize = measureHeight(mPaintMeasure, defaultTextSize, height);

        mPaintMeasure.setTextSize(textSize);
        mPaintMeasure.getTextBounds(text, 0, text.length(), mBoundsMeasure);

        if (width < mBoundsMeasure.width()) {
            float desiredTextSizeW = textSize * width / mBoundsMeasure.width();
            mPaintMeasure.setTextSize(desiredTextSizeW);
        }

        return mPaintMeasure.getTextSize();
    }

    private static float measureHeight(Paint paint, float startTextSize, final float desHeight) {
        if (startTextSize <= 0.0f) {
            startTextSize = 1.0f;
        }

        // auto fit the height
        paint.setTextSize(startTextSize);
        if (paint.getFontMetrics().bottom - paint.getFontMetrics().top >= desHeight) {
            return (desHeight - mB) / mA;
        } else {
            return startTextSize;
        }
    }

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }


    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * get resolution of screen, regardless of the navigation bar
     * @param ctx
     * @return
     */
    public static int[] getResolution(Context ctx){
        final int[] result = new int[2];

        // 因为隐藏了导航栏，所以这里要这样计算高度
        // we need consider of the height of navigation bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                android.graphics.Point realSize = new android.graphics.Point();
                WindowManager w = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
                Display d = w.getDefaultDisplay();
                Display.class.getMethod("getRealSize", android.graphics.Point.class).invoke(d,realSize);
                result[0] = realSize.x;
                result[1] = realSize.y;
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }

        // if has not obtain the resolution by above code,need obtain it from 就从DisplayMetrics中获取长宽
        if (result[0] <= 10 || result[1] <= 10){
            result[0] = ctx.getResources().getDisplayMetrics().widthPixels;
            result[1] = ctx.getResources().getDisplayMetrics().heightPixels;
        }

        return result;
    }

//	private static float mHeightScale = 0.0f;
//	private static float mWidthScale = 0.0f;
	/**
	 *  reference height
	 */
	public static int REFER_HEIGHT = 1920;
	/**
	 * reference width
	 */
	public static int REFER_WIDTH = 1080;
	public static float getHeightScale(Context context){
//		if (mHeightScale != 0.0f){
//			return mHeightScale;
//		}

		Point point = new Point();
		try {
			WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display d = w.getDefaultDisplay();
			Display.class.getMethod("getRealSize", android.graphics.Point.class).invoke(d,point);
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}

		return /*mHeightScale = */(float)point.y / (float)REFER_HEIGHT;
	}

	public static float getWidthScale(Context context){
//		if (mWidthScale != 0.0f){
//			return mWidthScale;
//		}

		int width = context.getResources().getDisplayMetrics().widthPixels;

		return /*mWidthScale = */(float)width / (float)REFER_WIDTH;
	}

    private static final String TAG_PRINT_KEY_BOUNDS = "TAG_PRINT_KEY_BOUNDS";

//    public static void printBounds(KeyboardData data){
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 50; i ++){
//            builder.append("*");
//        }
//        for (LatinRow row : data){
//            SLog.i(TAG_PRINT_KEY_BOUNDS, builder.toString());
//
//            StringBuilder keyBuilder = new StringBuilder();
//            for (LatinKey key : row){
////                keyBuilder.append("[");
////                keyBuilder.append(key.getX() - key.getLeftTouchLimit());
////                keyBuilder.append(",");
////                keyBuilder.append(key.getX() + key.getWidth() + key.getRightTouchLimit());
////                keyBuilder.append("]");
//
//                keyBuilder.append("[");
//                keyBuilder.append(key.getY() - key.getTopTouchLimit());
//                keyBuilder.append(",");
//                keyBuilder.append(key.getY() + key.getHeight() + key.getBottomTouchLimit());
//                keyBuilder.append("]");
//            }
//            SLog.i(TAG_PRINT_KEY_BOUNDS, keyBuilder.toString());
//        }
//
//        SLog.i(TAG_PRINT_KEY_BOUNDS, builder.toString());
//    }
//
//    public static void printTouchLimit(KeyboardData data){
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 50; i ++){
//            builder.append("*");
//        }
//        for (LatinRow row : data){
//            SLog.i(TAG_PRINT_KEY_BOUNDS, builder.toString());
//
//            StringBuilder keyBuilder = new StringBuilder();
//            for (LatinKey key : row){
//                keyBuilder.append("[");
//                keyBuilder.append(key.getLeftTouchLimit());
//                keyBuilder.append(",");
//                keyBuilder.append(key.getRightTouchLimit());
//                keyBuilder.append("]");
//            }
//            SLog.i(TAG_PRINT_KEY_BOUNDS, keyBuilder.toString());
//        }
//
//        SLog.i(TAG_PRINT_KEY_BOUNDS, builder.toString());
//    }
//
//    public static void initImageLoader(Context context) {
//        // This configuration tuning is custom. You can tune every option, you may tune some of them,
//        // or you can create default configuration by
//        //  ImageLoaderConfiguration.createDefault(this);
//        // method.
//        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
//        config.threadPriority(Thread.NORM_PRIORITY - 2);
//        config.denyCacheImageMultipleSizesInMemory();
//        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
//        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
//        config.tasksProcessingOrder(QueueProcessingType.LIFO);
////        config.writeDebugLogs(); // Remove for release app
//
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config.build());
//    }

    public static int mixColor(int originColor, int topColor) {
        float r1 = Color.red(originColor);
        float g1 = Color.green(originColor);
        float b1 = Color.blue(originColor);
        float alpha = Color.alpha(topColor) / 255f;
        float r2 = Color.red(topColor);
        float g2 = Color.red(topColor);
        float b2 = Color.red(topColor);

        float a3 = r1 * (1 - alpha) + r2 * alpha;
        float g3 = g1 * (1 - alpha) + g2 * alpha;
        float b3 = b1 * (1 - alpha) + b2 * alpha;

        return Color.rgb((int)a3, (int)g3, (int)b3);
    }

    /**
     * make the color more bright
     * @param color
     * @param brightness
     * @return
     */
    public static int brightColor(int color, int brightness) {
        int a = Color.alpha(color);
        int r = Color.red(color) + brightness;
        int g = Color.green(color) + brightness;
        int b = Color.blue(color) + brightness;

        if (r > 255) r = 255;
        if (g > 255) g = 255;
        if (b > 255) b = 255;

        return Color.argb(a, r, g, b);
    }

    public static String modeName(int mode) {
        if (mode == View.MeasureSpec.EXACTLY) {
            return "EXACTLY";
        } else if (mode == View.MeasureSpec.AT_MOST) {
            return "AT_MOST";
        } else if (mode == View.MeasureSpec.UNSPECIFIED) {
            return "UNSPECIFIED";
        }

        return "";
    }

    public static String touchName(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_POINTER_DOWN:
                return "ACTION_POINTER_DOWN";
            case MotionEvent.ACTION_POINTER_UP:
                return "ACTION_POINTER_UP";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            default:
                return "";
        }
    }

//    public static final Point getDefaultKeyWidthAndHeightSize(Context context) {
//        final Resources res = context.getResources();
//        Point ret = new Point();
//
//        final int referenceWidth = res.getInteger(R.integer.reference_screen_width);
//        final int referenceHeight = res.getInteger(R.integer.reference_screen_height);
//
//        final int screenWidth = res.getDisplayMetrics().widthPixels;
//        final int screenHeight = UIUtil.getResolution(context)[1];
//
//        ret.x = res.getInteger(R.integer.key_width)  * screenWidth / referenceWidth;
//        ret.y = res.getInteger(R.integer.key_height) * screenHeight / referenceHeight;
//
//        return ret;
//    }
//
//    public static boolean isDefaultTheme(){
////        if (SkinConstants.CurrentSkinName.equalsIgnoreCase(SkinConstants.DEFAULT_SKIN_NAME1)
////                || SkinConstants.CurrentSkinName.equalsIgnoreCase(SkinConstants.DEFAULT_SKIN_NAME2)){
//        if (SkinConstants.isWhiteSkin() || SkinConstants.isDarkSkin() || SkinConstants.isBlueSkin()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}
