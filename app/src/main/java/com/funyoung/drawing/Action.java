package com.funyoung.drawing;

import android.graphics.Path;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

/**
 * Created by yangfeng on 2017/10/8.
 */

public interface Action extends Serializable {
    void perform(Path path);
    void perform(Writer writer) throws IOException;
}
