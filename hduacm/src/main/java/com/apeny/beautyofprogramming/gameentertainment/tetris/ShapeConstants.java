package com.apeny.beautyofprogramming.gameentertainment.tetris;

/**
 * 形状常量
 * Created by monis on 2019/3/3.
 */
public class ShapeConstants {

    public static final String NULL = null;

    /**
     * 俄罗斯方块面积
     */
    public static final int BLOCK_SIZE = 4;

    /**
     * 不存在Shape
     */
    public static final int NONEXISTENT = Integer.MIN_VALUE;

    /**
     * mask图形
     */
    public static final String BOX = "#";

    /**
     * 空白图形
     */
    public static final String DOT = ".";

    public static final int NIL = -1;

    public static final int ZERO = 0;

    public static final int ONE = 1;

    public static final int TWO = 2;

    public static final int THREE = 3;

    public static final int PAN_SIZE = 16;

    /**
     * 所有图形个数
     */
    public static final int ALL_SHAPES_SIZE = 4;

    /**
     * 所有图形
     */
    public static final Shape[] SHAPES = new Shape[ALL_SHAPES_SIZE];

    public static final Shape BEAM_LEFT = new Shape(
            new String[][]{
                    {NULL, BOX, NULL, NULL},
                    {NULL, BOX, NULL, NULL},
                    {NULL, BOX, NULL, NULL},
                    {NULL, BOX, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {ONE, ONE, NIL, NIL},
                    {ONE, ONE, ZERO, THREE},
                    {ONE, ONE, NIL, NIL},
                    {ONE, ONE, NIL, NIL},
            });

    public static final Shape BEAM_DOWN = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, NULL, NULL, NULL},
                    {BOX, BOX, BOX, BOX},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, TWO, TWO},
                    {NIL, NIL, TWO, TWO},
                    {ZERO, THREE, TWO, TWO},
                    {NIL, NIL, TWO, TWO},
            }, BEAM_LEFT);

    public static final Shape BEAM_RIGHT = new Shape(
            new String[][]{
                    {NULL, NULL, BOX, NULL},
                    {NULL, NULL, BOX, NULL},
                    {NULL, NULL, BOX, NULL},
                    {NULL, NULL, BOX, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {TWO, TWO, NIL, NIL},
                    {TWO, TWO, NIL, NIL},
                    {TWO, TWO, ZERO, THREE},
                    {TWO, TWO, NIL, NIL},
            }, BEAM_DOWN);

    /**
     * NORMAL
     */
    public static final Shape BEAM = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {BOX, BOX, BOX, BOX},
                    {NULL, NULL, NULL, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, ONE, ONE},
                    {ZERO, THREE, ONE, ONE},
                    {NIL, NIL, ONE, ONE},
                    {NIL, NIL, ONE, ONE},
            }, BEAM_RIGHT);

    static {
        BEAM_LEFT.setNext(BEAM);
        SHAPES[0] = BEAM;
        SHAPES[1] = BEAM_RIGHT;
        SHAPES[2] = BEAM_DOWN;
        SHAPES[3] = BEAM_LEFT;
    }
}
