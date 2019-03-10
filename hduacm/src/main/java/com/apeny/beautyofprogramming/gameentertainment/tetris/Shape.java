package com.apeny.beautyofprogramming.gameentertainment.tetris;

import sun.security.provider.SHA;

/**
 * 图形值对象
 * ----
 *  --
 * |  |
 * |  |
 *  --
 *  --
 *    |
 *     --
 *     --
 *    |
 *  --
 *  ---
 *     |
 *  ---
 *  |
 *  ---
 *   |
 * Created by monis on 2019/2/26.
 */
public class Shape {

    /**
     * 形状描述
     */
    private String[][] four2four;

    /**
     * 0、1、2、3行和0、1、2、3列的范围
     */
    private int[][] ranges;

    private Shape next;

    public Shape(String[][] four2four) {
        this.four2four = four2four;
    }

    public Shape(String[][] four2four, int[][] ranges) {
        this.four2four = four2four;
        this.ranges = ranges;
    }

    public Shape(String[][] four2four, int[][] ranges, Shape next) {
        this.four2four = four2four;
        this.ranges = ranges;
        this.next = next;
    }

    public String[][] getFour2four() {
        return four2four;
    }

    public int[][] getRanges() {
        return ranges;
    }

    public Shape getNext() {
        return next;
    }

    public void setNext(Shape next) {
        if (this.next != null) {
            throw new UnsupportedOperationException("不允许重置next");
        }
        this.next = next;
    }

    /**
     * 返回最小y坐标
     * @return
     */
    public int getMinY() {
        int y = ShapeConstants.BLOCK_SIZE;
        for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
            int tempY = ranges[i][2];
            if (tempY != ShapeConstants.NIL && y > tempY) {
                y = tempY;
            }
        }
        return y;
    }
}
