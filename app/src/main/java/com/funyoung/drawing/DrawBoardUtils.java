package com.funyoung.drawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.funyoung.utilities.SLog;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by yangfeng on 2017/10/20.
 */

public class DrawBoardUtils {
    private static final String TAG = DrawBoardUtils.class.getSimpleName();

    private static final String DRAWBOARD_FOLDER_NAME = "drawboard";
    private static final String DRAFT_DRAWING_FILE_NAME = ".draft.jpeg";
    private static final int BITMAP_TARGET_WIDTH = 280;

    public static void reportDrawBoardEntry() {
//        EngineStaticsManager.draw_board_enter_b208++;
    }
    public static void reportDrawBoardSend() {
//        EngineStaticsManager.draw_board_enter_b208++;
    }

    public static File getExternalDrawBoardDir(Context context) {
        File dataDir = null;
        try {
            dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        }catch (Exception e) {
            dataDir = context.getFilesDir();
        }
        File appFilesDir = new File(new File(dataDir, context.getPackageName()), "files");
        if (!appFilesDir.exists()) {
            if (!appFilesDir.mkdirs()) {
                SLog.w(TAG, "Unable to create external drawboard directory");
                appFilesDir = new File(context.getFilesDir(), DRAWBOARD_FOLDER_NAME);
                return appFilesDir;
            }
        }

        File drawboardDir = new File(appFilesDir, DRAWBOARD_FOLDER_NAME);
        if (!drawboardDir.exists()) {
            if (!drawboardDir.mkdirs()) {
                SLog.w(TAG, "Unable to create external drawboard directory");
                appFilesDir = new File(context.getFilesDir(), DRAWBOARD_FOLDER_NAME);
                return appFilesDir;
//                return null;
            }
        }

        return drawboardDir;
    }

    public static String saveDraftBitmap(Context context, Bitmap bitmap) {
        File rootPath = getExternalDrawBoardDir(context) ;
        if (null == rootPath) {
            SLog.e(TAG, "saveDraftBitmap, failed to get folder path.");
            return null;
        }

        if (!rootPath.exists()) {
            if (!rootPath.mkdir()) {
                SLog.e(TAG, "saveDraftBitmap, failed to create path: " + rootPath.getPath());
                return null;
            }
        }

//        Bitmap bitmap;
//        int originWidth = originBitmap.getWidth();
//        if (originWidth > BITMAP_TARGET_WIDTH) {
//            int originHeight = originBitmap.getHeight();
//            int width = BITMAP_TARGET_WIDTH;
//            int height = (int) (0.5 + 1f * BITMAP_TARGET_WIDTH * originHeight / originWidth);
//            Matrix matrix = new Matrix();
//            matrix.postScale(width, height);
//            bitmap = Bitmap.createBitmap(originBitmap, 0, 0, width, height, matrix, false);
//            SLog.i(TAG, "saveDraftBitmap, scale bitmap from " + originWidth + "x" + originHeight
//                    + " to " + width + "x" + height);
//            originBitmap.recycle();
//        } else {
//            bitmap = originBitmap;
//        }

        String path = new File(rootPath, DRAFT_DRAWING_FILE_NAME).getAbsolutePath();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.close();
            }

            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
