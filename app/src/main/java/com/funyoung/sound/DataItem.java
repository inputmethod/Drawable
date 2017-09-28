package com.funyoung.sound;

import java.io.Serializable;

/**
 * Created by sunhang on 17-5-16.
 */
public class DataItem implements Serializable {
    public boolean isTips;
    public String str;

    @Override
    public boolean equals(Object o) {
        if ((o instanceof DataItem) == false) {
            return false;
        }

        DataItem item = (DataItem) o;
        return this.isTips == item.isTips && this.str.contentEquals(item.str);
    }
}
