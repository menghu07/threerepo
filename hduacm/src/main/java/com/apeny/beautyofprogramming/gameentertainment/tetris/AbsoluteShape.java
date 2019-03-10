package com.apeny.beautyofprogramming.gameentertainment.tetris;

/**
 * 绝对图形
 * Created by monis on 2019/3/3.
 */
public class AbsoluteShape {

    private Shape reference;

    /**
     * 0、1、2、3行和0、1、2、3列的范围，绝对坐标位置
     */
    private int[][] ranges;

    public AbsoluteShape(Shape reference) {
        this.reference = reference;
    }

    public int[][] getRanges() {
        return ranges;
    }

    public void setRanges(int[][] ranges) {
        this.ranges = ranges;
    }
}
