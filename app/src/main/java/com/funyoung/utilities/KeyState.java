package com.funyoung.utilities;

/**
 * Created by yangfeng on 2017/9/28.
 */

public class KeyState {
    /* States Array */
    public final static int[] KEY_STATE_PRESSED_ON = {
            android.R.attr.state_pressed,
            android.R.attr.state_checkable,
            android.R.attr.state_checked
    };

    public final static int[] KEY_STATE_NORMAL_ON = {
            android.R.attr.state_checkable,
            android.R.attr.state_checked
    };

    public final static int[] KEY_STATE_NORMAL_OFF = {
            android.R.attr.state_checkable
    };

    public final static int[] KEY_STATE_PRESSED_OFF = {
            android.R.attr.state_pressed,
            android.R.attr.state_checkable
    };

    public final static int[] KEY_STATE_SELECTED = {
            android.R.attr.state_selected
    };

    public final static int[] KEY_STATE_SELECTED_PRESSED = {
            android.R.attr.state_selected,
            android.R.attr.state_pressed
    };

    public final static int[] KEY_STATE_PRESSED = {
            android.R.attr.state_pressed
    };

    public final static int[] KEY_STATE_FOCUSED = {
            android.R.attr.state_focused
    };

    public final static int[] KEY_STATE_NORMAL = {
            android.R.attr.state_enabled
    };

    public final static int[] KEY_STATE_DISABLED = {};
}
