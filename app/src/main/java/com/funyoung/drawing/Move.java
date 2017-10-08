package com.funyoung.drawing;

import android.graphics.Path;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by yangfeng on 2017/10/8.
 */

/*
class Move : Action {

    val x: Float
    val y: Float

    constructor(data: String) {
        if (!data.startsWith("M"))
            throw InvalidParameterException("The Move data should start with 'M'.")

        try {
            val xy = data.substring(1).split(",".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
            x = xy[0].trim().toFloat()
            y = xy[1].trim().toFloat()
        } catch (ignored: Exception) {
            throw InvalidParameterException("Error parsing the given Move data.")
        }
    }

    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    override fun perform(path: Path) {
        path.moveTo(x, y)
    }

    override fun perform(writer: Writer) {
        writer.write("M$x,$y")
    }
}
 */
public class Move implements Action {
    private final float x;
    private final float y;

    public Move(String data) {
        if (null == data || !data.startsWith("M")) {
            throw new IllegalArgumentException("The Move data should start with 'M'.");
        }

        try {
            String[] xy = data.substring(1).split(",");
            x = Float.parseFloat(xy[0].trim());
            y = Float.parseFloat(xy[1].trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing the given Move data.");
        }
    }

    public Move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void perform(Path path) {
        path.moveTo(x, y);

    }

    @Override
    public void perform(Writer writer) throws IOException {
        writer.write("M" + x + "," + y);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}
