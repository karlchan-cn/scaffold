package com.scaffold.ds.bitmap;

/**
 * Created by Karl on 2021/11/30
 **/
public class BitMap {
    /**
     * value数组
     */
    private int[] values;
    /**
     * 最大值
     */
    private int ceiling;

    public BitMap(int maxVal) {
        ceiling = maxVal;
        values = new int[(maxVal / 32) + 1];
    }

    public boolean setValue(int value) {
        return false;
    }

    public int getValue(int value) {
        if (value > ceiling) {
            return 0;
        }
        value = value >> 5;
        int indexValue = values[value];

        return 0;
    }
}
