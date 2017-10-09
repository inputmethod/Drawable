package com.funyoung.drawing;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yangfeng on 2017/10/9.
 */
/*
class Config(context: Context) : BaseConfig(context) {
    companion object {
        fun newInstance(context: Context) = Config(context)
    }

    var showBrushSize: Boolean
        get() = prefs.getBoolean(SHOW_BRUSH_SIZE, false)
        set(showBrushSize) = prefs.edit().putBoolean(SHOW_BRUSH_SIZE, showBrushSize).apply()

    var brushColor: Int
        get() = prefs.getInt(BRUSH_COLOR, Color.BLACK)
        set(color) = prefs.edit().putInt(BRUSH_COLOR, color).apply()

    var brushSize: Float
        get() = prefs.getFloat(BRUSH_SIZE, 5.0f)
        set(brushSize) = prefs.edit().putFloat(BRUSH_SIZE, brushSize).apply()

    var canvasBackgroundColor: Int
        get() = prefs.getInt(CANVAS_BACKGROUND_COLOR, Color.WHITE)
        set(canvasBackgroundColor) = prefs.edit().putInt(CANVAS_BACKGROUND_COLOR, canvasBackgroundColor).apply()
}
 */
public class Config {
    private SharedPreferences preferences;
    public boolean isShowBrushSize() {
        return preferences.getBoolean("SHOW_BRUSH_SIZE", false);
    }

    public void setShowBrushSize(boolean showBrushSize) {
        preferences.edit().putBoolean("SHOW_BRUSH_SIZE", showBrushSize).apply();
    }

    public int getBrushColor() {
        return preferences.getInt("BRUSH_COLOR", Color.BLACK);
    }

    public void setBrushColor(int brushColor) {
        preferences.edit().putInt("BRUSH_COLOR", brushColor).apply();
    }

    public float getBrushSize() {
        return preferences.getFloat("BRUSH_SIZE", 5.0f);
    }

    public void setBrushSize(float brushSize) {
        preferences.edit().putFloat("BRUSH_SIZE", brushSize).apply();
    }

    public int getCanvasBackgroundColor() {
        return preferences.getInt("CANVAS_BACKGROUND_COLOR", Color.WHITE);
    }

    public void setCanvasBackgroundColor(int canvasBackgroundColor) {
        preferences.edit().putInt("CANVAS_BACKGROUND_COLOR", canvasBackgroundColor).apply();
    }

    public Config(Context context) {
        preferences = context.getSharedPreferences("Config", MODE_PRIVATE);
    }
}
