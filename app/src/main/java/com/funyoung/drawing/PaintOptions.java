package com.funyoung.drawing;

import android.graphics.Color;

/**
 * Created by yangfeng on 2017/10/9.
 */
/*
data class PaintOptions(var color: Int = Color.BLACK, var strokeWidth: Float = 5f, var isEraser: Boolean = false) {
    fun getColorToExport() = if (isEraser) "none" else "#${Integer.toHexString(color).substring(2)}"
}
 */
public class PaintOptions {
    public int color = Color.BLACK;
    public float strokeWidth = 5f;
    public boolean isEraser = false;

    public PaintOptions(int color, float strokeWidth, boolean isEraser) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.isEraser = isEraser;
    }

    public PaintOptions() {
    }

    public String getColorToExport() {
        if (isEraser) {
            return "none";
        } else {
            return "#" + Integer.toHexString(color).substring(2);
        }
    }
}
