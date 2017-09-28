package com.funyoung.utilities;


import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Locale;

public final class LayoutUtils {
    public static int dip2px(float density, float dpValue) {
        final float scale = density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float density, float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }

    public static int floatToInt(float val, boolean needCarry) {
        int roundingMode = needCarry ? BigDecimal.ROUND_HALF_UP : BigDecimal.ROUND_DOWN;
        BigDecimal bd = new BigDecimal(Float.toString(val));
        bd = bd.setScale(0, roundingMode);
        return bd.intValue();
    }

    public static float getDensity(Context context) {
        final Resources res = context.getResources();
        final DisplayMetrics dm = res.getDisplayMetrics();

        return dm.density;
    }

    public static boolean hasVirtualKey(Context context)
    {
        boolean result = false;
        if(getAndroidSDKVersion()>14 && !isMiUIV6()){
            result = !ViewConfiguration.get(context).hasPermanentMenuKey();
            SLog.i("LayoutUtils","current virtual key status: "+ result);
        }
        return result;
    }

    public static boolean isMiUIV6(){
        Method methodGetter = null;
        try{
            Class<?> sysClass = Class.forName("android.os.SystemProperties");
            methodGetter = sysClass.getDeclaredMethod("get",String.class);
            boolean result = "V6".equals((String)methodGetter.invoke(sysClass,"ro.miui.ui.version.name"));
            SLog.i("LayoutUtils","is miui v6: "+ result);
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static int getAndroidSDKVersion(){
        int version=0;
        try{
            version = Integer.valueOf(Build.VERSION.SDK_INT);
            SLog.i("LayoutUtils","get current sdk version "+version);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return version;
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        SLog.e("test", "scaledDensity = " + fontScale);
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static boolean isRTL() {
        return isRTL(Locale.getDefault());
    }

    public static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }
}
