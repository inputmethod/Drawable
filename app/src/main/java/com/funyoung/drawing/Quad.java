package com.funyoung.drawing;

import android.graphics.Path;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by yangfeng on 2017/10/8.
 */

/*
class Quad : Action {

    val x1: Float
    val y1: Float
    val x2: Float
    val y2: Float

    constructor(data: String) {
        if (!data.startsWith("Q"))
            throw InvalidParameterException("The Quad data should start with 'Q'.")

        try {
            val parts = data.split("\\s+".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
            val xy1 = parts[0].substring(1).split(",".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
            val xy2 = parts[1].split(",".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()

            x1 = xy1[0].trim().toFloat()
            y1 = xy1[1].trim().toFloat()
            x2 = xy2[0].trim().toFloat()
            y2 = xy2[1].trim().toFloat()
        } catch (ignored: Exception) {
            throw InvalidParameterException("Error parsing the given Quad data.")
        }
    }

    constructor(x1: Float, y1: Float, x2: Float, y2: Float) {
        this.x1 = x1
        this.y1 = y1
        this.x2 = x2
        this.y2 = y2
    }

    override fun perform(path: Path) {
        path.quadTo(x1, y1, x2, y2)
    }

    override fun perform(writer: Writer) {
        writer.write("Q$x1,$y1 $x2,$y2")
    }
}
 */
public class Quad implements Action {
    private final float x1;
    private final float y1;
    private final float x2;
    private final float y2;

    public Quad(String data) {
        if (null == data || !data.startsWith("Q")) {
            throw new IllegalArgumentException("The Move data should start with 'Q'.");
        }

        try {
            String [] parts = data.split(" ");

            String[] xy1 = parts[0].substring(1).split(",");
            x1 = Float.parseFloat(xy1[0].trim());
            y1 = Float.parseFloat(xy1[1].trim());

            String[] xy2 = parts[1].split(",");
            x2 = Float.parseFloat(xy2[0].trim());
            y2 = Float.parseFloat(xy2[1].trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing the given Quad data.");
        }
    }

    public Quad(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void perform(Path path) {
        path.quadTo(x1, y1, x2, y2);
    }

    @Override
    public void perform(Writer writer) throws IOException {
        writer.write("Q" + x1 + "," + y1 + " " + x2 + "," + y2);
    }

    public float getX1() {
        return this.x1;
    }

    public float getY1() {
        return this.y1;
    }

    public float getX2() {
        return this.x2;
    }

    public float getY2() {
        return this.y2;
    }
}
