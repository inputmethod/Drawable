package com.funyoung.drawing;

import android.graphics.Path;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by yangfeng on 2017/10/8.
 */
/*
// https://stackoverflow.com/a/8127953
class MyPath : Path(), Serializable {

    private val actions = LinkedList<Action>()

    private fun readObject(inputStream: ObjectInputStream) {
        inputStream.defaultReadObject()

        for (action in actions) {
            action.perform(this)
        }
    }

    fun readObject(pathData: String, activity: Activity) {
        val tokens = pathData.split("\\s+".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
        var i = 0
        try {
            while (i < tokens.size) {
                when (tokens[i][0]) {
                    'M' -> addAction(Move(tokens[i]))
                    'L' -> addAction(Line(tokens[i]))
                    'Q' -> {
                        // Quad actions are of the following form:
                        // "Qx1,y1 x2,y2"
                        // Since we split the tokens by whitespace, we need to join them again
                        if (i + 1 >= tokens.size)
                            throw InvalidParameterException("Error parsing the data for a Quad.")

                        addAction(Quad(tokens[i] + " " + tokens[i + 1]))
                        ++i
                    }
                }
                ++i
            }
        } catch (e: Exception) {
            activity.toast(R.string.unknown_error_occurred)
        }
    }

    override fun reset() {
        actions.clear()
        super.reset()
    }

    private fun addAction(action: Action) {
        if (action is Move) {
            moveTo(action.x, action.y)
        } else if (action is Line) {
            lineTo(action.x, action.y)
        } else if (action is Quad) {
            val q = action
            quadTo(q.x1, q.y1, q.x2, q.y2)
        }
    }

    override fun moveTo(x: Float, y: Float) {
        actions.add(Move(x, y))
        super.moveTo(x, y)
    }

    override fun lineTo(x: Float, y: Float) {
        actions.add(Line(x, y))
        super.lineTo(x, y)
    }

    override fun quadTo(x1: Float, y1: Float, x2: Float, y2: Float) {
        actions.add(Quad(x1, y1, x2, y2))
        super.quadTo(x1, y1, x2, y2)
    }

    fun getActions() = actions
}
 */
public class MyPath extends Path implements Serializable {
    private final LinkedList actions = new LinkedList();

    @Override
    public void reset() {
        actions.clear();
        super.reset();
    }
    public void addAction(Action action) {
        if (action instanceof Line) {
            Line line = (Line) action;
            lineTo(line.getX(), line.getY());
        } else if (action instanceof Move) {
            Move move = (Move) action;
            moveTo(move.getX(), move.getY());
        } else if (action instanceof Quad) {
            Quad quad = (Quad) action;
            quadTo(quad.getX1(), quad.getY1(), quad.getX2(), quad.getY2());
        }
    }

    @Override
    public void moveTo(float x, float y) {
        actions.add(new Move(x, y));
        super.moveTo(x, y);
    }

    @Override
    public void lineTo(float x, float y) {
        actions.add(new Line(x, y));
        super.lineTo(x, y);
    }

    @Override
    public void quadTo(float x1, float y1, float x2, float y2) {
        actions.add(new Quad(x1, y1, x2, y2));
        super.quadTo(x1, y1, x2, y2);
    }
    public LinkedList<Action> getActions() {
        return actions;
    }
}
