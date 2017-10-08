package com.funyoung.drawing;

import android.graphics.Path;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by yangfeng on 2017/10/8.
 */
/*
class Line : Action {

    val x: Float
    val y: Float

    constructor(data: String) {
        if (!data.startsWith("L"))
            throw InvalidParameterException("The Line data should start with 'L'.")

        try {
            val xy = data.substring(1).split(",".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
            x = xy[0].trim().toFloat()
            y = xy[1].trim().toFloat()
        } catch (ignored: Exception) {
            throw InvalidParameterException("Error parsing the given Line data.")
        }
    }

    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    override fun perform(path: Path) {
        path.lineTo(x, y)
    }

    override fun perform(writer: Writer) {
        writer.write("L$x,$y")
    }
}
 */
public class Line  implements Action {
    private final float x;
    private final float y;

    public Line(String data) {
        if (null == data || !data.startsWith("L")) {
            throw new IllegalArgumentException("The Move data should start with 'L'.");
        }

        try {
            String[] xy = data.substring(1).split(",");
            x = Float.parseFloat(xy[0].trim());
            y = Float.parseFloat(xy[1].trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing the given Line data.");
        }
    }

    public Line(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void perform(Path path) {
        path.lineTo(x, y);
    }

    @Override
    public void perform(Writer writer) throws IOException {
        writer.write("L" + x + "," + y);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}
