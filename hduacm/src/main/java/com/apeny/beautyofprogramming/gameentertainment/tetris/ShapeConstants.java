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

    /**
     * 星号
     */
    public static final String ASTERISK = "*";

    public static final int NIL = -1;

    public static final int ZERO = 0;

    public static final int ONE = 1;

    public static final int TWO = 2;

    public static final int THREE = 3;

    public static final int PAN_SIZE = 16;

    /**
     * 所有图形个数
     */
    public static final int ALL_SHAPES_SIZE = 29;

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

    /**
     * BLOCK
     */
    public static final Shape BLOCK = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {ONE, TWO, ONE, TWO},
                    {ONE, TWO, ONE, TWO},
                    {NIL, NIL, NIL, NIL},
            });

    public static final Shape RIGHTN_LEFT = new Shape(
            new String[][]{
                    {NULL, BOX, NULL, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, NULL, BOX, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {ONE, ONE, NIL, NIL},
                    {ONE, TWO, ZERO, ONE},
                    {TWO, TWO, ONE, TWO},
                    {NIL, NIL, NIL, NIL},
            });

    public static final Shape RIGHTN_DOWN = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, BOX, BOX, NULL},
                    {BOX, BOX, NULL, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, TWO, TWO},
                    {ONE, TWO, ONE, TWO},
                    {ZERO, ONE, ONE, ONE},
                    {NIL, NIL, NIL, NIL},
            }, RIGHTN_LEFT);

    public static final Shape RIGHTN_RIGHT = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, BOX, NULL, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, NULL, BOX, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {ONE, ONE, ONE, TWO},
                    {ONE, TWO, TWO, THREE},
                    {TWO, TWO, NIL, NIL},
            }, RIGHTN_DOWN);


    /**
     * NORMAL
     */
    public static final Shape RIGHTN = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, NULL, BOX, BOX},
                    {NULL, BOX, BOX, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {TWO, THREE, TWO, TWO},
                    {ONE, TWO, ONE, TWO},
                    {NIL, NIL, ONE, ONE},
            }, RIGHTN_RIGHT);

    public static final Shape LEFTN_LEFT = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, NULL, BOX, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, BOX, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {TWO, TWO, TWO, THREE},
                    {ONE, TWO, ONE, TWO},
                    {ONE, ONE, NIL, NIL},
            });

    public static final Shape LEFTN_DOWN = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, NULL, BOX, BOX},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {ONE, TWO, ONE, ONE},
                    {TWO, THREE, ONE, TWO},
                    {NIL, NIL, TWO, TWO},
            }, LEFTN_LEFT);

    public static final Shape LEFTN_RIGHT = new Shape(
            new String[][]{
                    {NULL, NULL, BOX, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, BOX, NULL, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {TWO, TWO, NIL, NIL},
                    {ONE, TWO, ONE, TWO},
                    {ONE, ONE, ZERO, ONE},
                    {NIL, NIL, NIL, NIL},
            }, LEFTN_DOWN);

    /**
     * NORMAL
     */
    public static final Shape LEFTN = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {BOX, BOX, NULL, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, ONE, ONE},
                    {ZERO, ONE, ONE, TWO},
                    {ONE, TWO, TWO, TWO},
                    {NIL, NIL, NIL, NIL},
            }, LEFTN_RIGHT);

    public static final Shape RIGHTL_LEFT = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, BOX, NULL, NULL},
                    {NULL, BOX, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {ONE, TWO, ONE, THREE},
                    {ONE, ONE, ONE, ONE},
                    {ONE, ONE, NIL, NIL},
            });

    public static final Shape RIGHTL_DOWN = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, BOX, NULL, NULL},
                    {NULL, BOX, BOX, BOX},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {ONE, ONE, ONE, TWO},
                    {ONE, THREE, TWO, TWO},
                    {NIL, NIL, TWO, TWO},
            }, RIGHTL_LEFT);

    public static final Shape RIGHTL_RIGHT = new Shape(
            new String[][]{
                    {NULL, NULL, BOX, NULL},
                    {NULL, NULL, BOX, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {TWO, TWO, NIL, NIL},
                    {TWO, TWO, TWO, TWO},
                    {ONE, TWO, ZERO, TWO},
                    {NIL, NIL, NIL, NIL},
            }, RIGHTL_DOWN);

    /**
     * NORMAL
     */
    public static final Shape RIGHTL = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {BOX, BOX, BOX, NULL},
                    {NULL, NULL, BOX, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, ONE, ONE},
                    {ZERO, TWO, ONE, ONE},
                    {TWO, TWO, ONE, TWO},
                    {NIL, NIL, NIL, NIL},
            }, RIGHTL_RIGHT);

    public static final Shape LEFTL_LEFT = new Shape(
            new String[][]{
                    {NULL, BOX, NULL, NULL},
                    {NULL, BOX, NULL, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {ONE, ONE, NIL, NIL},
                    {ONE, ONE, ZERO, TWO},
                    {ONE, TWO, TWO, TWO},
                    {NIL, NIL, NIL, NIL},
            });

    public static final Shape LEFTL_DOWN = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, NULL, BOX, NULL},
                    {BOX, BOX, BOX, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, TWO, TWO},
                    {TWO, TWO, TWO, TWO},
                    {ZERO, TWO, ONE, TWO},
                    {NIL, NIL, NIL, NIL},
            }, LEFTL_LEFT);

    public static final Shape LEFTL_RIGHT = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, NULL, BOX, NULL},
                    {NULL, NULL, BOX, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {ONE, TWO, ONE, ONE},
                    {TWO, TWO, ONE, THREE},
                    {TWO, TWO, NIL, NIL},
            }, LEFTL_DOWN);

    /**
     * NORMAL
     */
    public static final Shape LEFTL = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, BOX, BOX, BOX},
                    {NULL, BOX, NULL, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {ONE, THREE, ONE, TWO},
                    {ONE, ONE, ONE, ONE},
                    {NIL, NIL, ONE, ONE},
            }, LEFTL_RIGHT);


    public static final Shape LEFTT_LEFT = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, BOX, NULL, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, BOX, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {ONE, ONE, ONE, THREE},
                    {ONE, TWO, TWO, TWO},
                    {ONE, ONE, NIL, NIL},
            });

    public static final Shape LEFTT_DOWN = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, NULL, BOX, NULL},
                    {NULL, BOX, BOX, BOX},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {TWO, TWO, TWO, TWO},
                    {ONE, THREE, ONE, TWO},
                    {NIL, NIL, TWO, TWO},
            }, LEFTT_LEFT);

    public static final Shape LEFTT_RIGHT = new Shape(
            new String[][]{
                    {NULL, NULL, BOX, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, NULL, BOX, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {TWO, TWO, NIL, NIL},
                    {ONE, TWO, ONE, ONE},
                    {TWO, TWO, ZERO, TWO},
                    {NIL, NIL, NIL, NIL},
            }, LEFTT_DOWN);

    /**
     * NORMAL
     */
    public static final Shape LEFTT = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {BOX, BOX, BOX, NULL},
                    {NULL, BOX, NULL, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, ONE, ONE},
                    {ZERO, TWO, ONE, TWO},
                    {ONE, ONE, ONE, ONE},
                    {NIL, NIL, NIL, NIL},
            }, LEFTT_RIGHT);

    public static final Shape UPT_LEFT = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, BOX, NULL, NULL},
                    {BOX, BOX, BOX, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, TWO, TWO},
                    {ONE, ONE, ONE, TWO},
                    {ZERO, TWO, TWO, TWO},
                    {NIL, NIL, NIL, NIL},
            });

    public static final Shape UPT_DOWN = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, NULL, BOX, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, NULL, BOX, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {TWO, TWO, TWO, TWO},
                    {ONE, TWO, ONE, THREE},
                    {TWO, TWO, NIL, NIL},
            }, UPT_LEFT);

    public static final Shape UPT_RIGHT = new Shape(
            new String[][]{
                    {NULL, NULL, NULL, NULL},
                    {NULL, BOX, BOX, BOX},
                    {NULL, NULL, BOX, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {NIL, NIL, NIL, NIL},
                    {ONE, THREE, ONE, ONE},
                    {TWO, TWO, ONE, TWO},
                    {NIL, NIL, ONE, ONE},
            }, UPT_DOWN);

    /**
     * NORMAL
     */
    public static final Shape UPT = new Shape(
            new String[][]{
                    {NULL, BOX, NULL, NULL},
                    {NULL, BOX, BOX, NULL},
                    {NULL, BOX, NULL, NULL},
                    {NULL, NULL, NULL, NULL},
            },
            //0行左右 1行左右、... | 0列上下、1列上下、...
            new int[][]{
                    {ONE, ONE, NIL, NIL},
                    {ONE, TWO, ZERO, TWO},
                    {ONE, ONE, ONE, ONE},
                    {NIL, NIL, NIL, NIL},
            }, UPT_RIGHT);
    static {
        BEAM_LEFT.setNext(BEAM);
        BLOCK.setNext(BLOCK);
        RIGHTN_LEFT.setNext(RIGHTN);
        LEFTN_LEFT.setNext(LEFTN);
        RIGHTL_LEFT.setNext(RIGHTL);
        LEFTL_LEFT.setNext(LEFTL);
        LEFTT_LEFT.setNext(LEFTT);
        UPT_LEFT.setNext(UPT);
        SHAPES[0] = BEAM;
        SHAPES[1] = BEAM_RIGHT;
        SHAPES[2] = BEAM_DOWN;
        SHAPES[3] = BEAM_LEFT;
        SHAPES[4] = BLOCK;
        SHAPES[5] = RIGHTN;
        SHAPES[6] = RIGHTN_RIGHT;
        SHAPES[7] = RIGHTN_DOWN;
        SHAPES[8] = RIGHTN_LEFT;
        SHAPES[9] = LEFTN;
        SHAPES[10] = LEFTN_RIGHT;
        SHAPES[11] = LEFTN_DOWN;
        SHAPES[12] = LEFTN_LEFT;
        SHAPES[13] = RIGHTL;
        SHAPES[14] = RIGHTL_RIGHT;
        SHAPES[15] = RIGHTL_DOWN;
        SHAPES[16] = RIGHTL_LEFT;
        SHAPES[17] = LEFTL;
        SHAPES[18] = LEFTL_RIGHT;
        SHAPES[19] = LEFTL_DOWN;
        SHAPES[20] = LEFTL_LEFT;
        SHAPES[21] = LEFTT;
        SHAPES[22] = LEFTT_RIGHT;
        SHAPES[23] = LEFTT_DOWN;
        SHAPES[24] = LEFTT_LEFT;
        SHAPES[25] = UPT;
        SHAPES[26] = UPT_RIGHT;
        SHAPES[27] = UPT_DOWN;
        SHAPES[28] = UPT_LEFT;
    }
}