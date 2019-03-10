package com.apeny.beautyofprogramming.gameentertainment.tetris;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 游戏主盘
 * Created by monis on 2019/3/3.
 */
public class ChessBoard {

    /**
     * 是否继续 0 继续 1 暂停 2 退出
     */
    private AtomicInteger continued = new AtomicInteger(0);

    /**
     * 绘制锁
     */
    private final Object DRAW_LOCK = new Object();

    private static final int CONTINUE = 0;

    private static final int PAUSE = 1;

    private static final int EXIT = 2;

    /**
     * 当前操作方块
     */
    private Shape current;

    /**
     * 前x坐标
     */
    private int previousX;

    /**
     * 前y坐标
     */
    private int previousY;

    /**
     * 横坐标，在主盘上绝对坐标
     */
    private int x;

    /**
     * 纵坐标，在主盘上绝对坐标
     */
    private int y;

    /**
     * 左侧mask最大x坐标
     */
    private int[] left = new int[ShapeConstants.BLOCK_SIZE];

    /**
     * 下方mask最小y坐标
     */
    private int[] down = new int[ShapeConstants.BLOCK_SIZE];

    /**
     * 右侧mask最小x坐标
     */
    private int[] right = new int[ShapeConstants.BLOCK_SIZE];

    private static final int DOWN_DIRECTION = 1;

    private static final int RIGHT_LEFT_DIRECTION = 2;

    private MoveDown moveDown;

    private Thread moveDownThread;

    public final String[][] BIG_PAN = new String[ShapeConstants.PAN_SIZE][ShapeConstants.PAN_SIZE];

    public ChessBoard() {
        for (int i = 0; i < ShapeConstants.PAN_SIZE; i++) {
            for (int j = 0; j < ShapeConstants.PAN_SIZE; j++) {
                BIG_PAN[i][j] = ShapeConstants.DOT;
            }
        }
        //left init -1 -1 -1 -1
        for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
            left[i] = -1;
        }
        //down init max max max max
        for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
            down[i] = ShapeConstants.PAN_SIZE;
        }
        //right init max max max max
        for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
            right[i] = ShapeConstants.PAN_SIZE;
        }
        moveDown = new MoveDown();
        moveDownThread = new Thread(new MoveDown());
    }

    /**
     * 新创建一个图形并绘制
     */
    public final void newShape() {
        //初始时previousX = x，previousY = y
        //先随机出一个图形，然后以图形最上边缘为与y=0重回为基准点
        Random r = new Random();
        Shape newedShape = ShapeConstants.SHAPES[r.nextInt(ShapeConstants.ALL_SHAPES_SIZE)];
        int minY = newedShape.getMinY();
        x = (ShapeConstants.PAN_SIZE - ShapeConstants.BLOCK_SIZE) / 2;
        previousX = x;
        y = 0 - minY;
        previousY = y;
        current = newedShape;
        updateEdge(DOWN_DIRECTION | RIGHT_LEFT_DIRECTION);
        clearAndWrite();
        printThis();
    }

    /**
     * 向左移动
     */
    public void moveLeft() {
        synchronized (DRAW_LOCK) {
            if (current != null) {
                AbsoluteShape absoluteShape = toAbsoluteShape();
                //取最小移动距离0、1、2、3行
                int[][] absRange = absoluteShape.getRanges();
                int moveX = ShapeConstants.PAN_SIZE - 1;
                for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
                    if (absRange[i][0] != ShapeConstants.NIL) {
                        int tempX = absRange[i][0] - left[i] - 1;
                        if (tempX < moveX) {
                            moveX = tempX;
                        }
                    }
                }
                if (moveX > 0) {
                    previousX = this.x;
                    previousY = this.y;
                    this.x--;
                    clearAndWrite();
                    printThis();
                }
                updateEdge(DOWN_DIRECTION);
            }
        }
    }

    /**
     * 向下移动
     */
    public void moveDown() {
        synchronized (DRAW_LOCK) {
            if (current != null) {
                AbsoluteShape absoluteShape = toAbsoluteShape();
                //取最小移动距离0、1、2、3列
                int[][] absRange = absoluteShape.getRanges();
                int moveY = ShapeConstants.PAN_SIZE - 1;
                for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
                    if (absRange[i][3] != ShapeConstants.NIL) {
                        int tempY = down[i] - absRange[i][3] - 1;
                        if (tempY < moveY) {
                            moveY = tempY;
                        }
                    }
                }
                if (moveY > 0) {
                    previousX = this.x;
                    previousY = this.y;
                    this.y++;
                    clearAndWrite();
                    printThis();
                }
                //如果触底，就生成一个新的图形
                if (moveY == 1) {
                    newShape();
                } else {
                    updateEdge(RIGHT_LEFT_DIRECTION);
                }
            }
        }
    }

    /**
     * 向右移动
     */
    public void moveRight() {
        synchronized (DRAW_LOCK) {
            if (current != null) {
                AbsoluteShape absoluteShape = toAbsoluteShape();
                //取最小移动距离0、1、2、3行
                int[][] absRange = absoluteShape.getRanges();
                int moveX = ShapeConstants.PAN_SIZE - 1;
                for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
                    if (absRange[i][1] != ShapeConstants.NIL) {
                        int tempX = right[i] - absRange[i][1] - 1;
                        if (tempX < moveX) {
                            moveX = tempX;
                        }
                    }
                }
                if (moveX > 0) {
                    previousX = this.x;
                    previousY = this.y;
                    this.x++;
                    clearAndWrite();
                    printThis();
                }
                updateEdge(DOWN_DIRECTION);
            }
        }
    }

    /**
     * 游戏是否结束
     *
     * @return
     */
    public final boolean isExited() {
        return continued.get() == EXIT;
    }

    /**
     * 给后台线程发送指令
     *
     * @param instruction
     */
    public final void sendInstruction(Integer instruction) {
        if (instruction == CONTINUE || instruction == PAUSE || instruction == EXIT) {
            moveDown.nextInstruction.set(instruction);
            moveDownThread.interrupt();
        }
    }

    /**
     * 打印当前主盘
     */
    private void printThis() {
        System.out.print("  ");
        for (int i = 0; i < ShapeConstants.PAN_SIZE; i++) {
            System.out.print(leftPadding(i));
        }
        System.out.println();
        for (int i = 0; i < ShapeConstants.PAN_SIZE; i++) {
            System.out.print(leftPadding(i));
            for (int j = 0; j < ShapeConstants.PAN_SIZE; j++) {
                System.out.print(" " + BIG_PAN[i][j]);
            }
            if (i == 0) {
                //输出换行符
                System.out.println(" ("+ x + "," + y +") Direction: left j, right l, down k, pause p, resume r, stop s");
            } else {
                System.out.println();
            }
        }
        System.out.println("--------------end----------------");
    }

    /**
     * 重新绘制Shape
     */
    private void clearAndWrite() {
        if (current != null) {
            int[][] relRanges = current.getRanges();
            //清除之前方块，按行清除
            if (previousX != x || previousY != y) {
                for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
                    for (int j = relRanges[i][0]; j <= relRanges[i][1]; j++) {
                        if (isSafe(i, j)) {
                            safeSetBox(previousY + i, previousX + j, ShapeConstants.DOT);
                        }
                    }
                }
            }
            //设置当前方块
            for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
                for (int j = relRanges[i][0]; j <= relRanges[i][1]; j++) {
                    if (isSafe(i, j)) {
                        safeSetBox(y + i, x + j, ShapeConstants.BOX);
                    }
                }
            }
        }
    }

    /**
     * 相对坐标转换为绝对坐标
     *
     * @return
     */
    private AbsoluteShape toAbsoluteShape() {
        if (current == null) {
            throw new UnsupportedOperationException("当前不存在图形");
        }
        AbsoluteShape absoluteShape = new AbsoluteShape(current);
        int[][] relRanges = current.getRanges();
        int[][] ranges = new int[ShapeConstants.BLOCK_SIZE][ShapeConstants.BLOCK_SIZE];
        for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
            for (int j = 0; j < ShapeConstants.BLOCK_SIZE; j++) {
                if (relRanges[i][j] == ShapeConstants.NIL) {
                    ranges[i][j] = ShapeConstants.NIL;
                } else {
                    if (j < 2) {
                        ranges[i][j] = x + relRanges[i][j];
                    } else {
                        ranges[i][j] = y + relRanges[i][j];
                    }
                }
            }
        }
        absoluteShape.setRanges(ranges);
        return absoluteShape;
    }

    private void safeSetBox(int x, int y, String box) {
        if (x >= 0 && x < ShapeConstants.PAN_SIZE && y >= 0 && y < ShapeConstants.PAN_SIZE) {
            BIG_PAN[x][y] = box;
        }
    }

    /**
     * 是否数组越界
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isSafe(int x, int y) {
        return (x >= 0 && x < ShapeConstants.PAN_SIZE && y >= 0 && y < ShapeConstants.PAN_SIZE);
    }

    /**
     * 更新down right left边界
     * @param direct 1 down 2 right&left 3 down&right&left
     */
    private void updateEdge(int direct) {
        AbsoluteShape newAbsoluteShape = toAbsoluteShape();
        //取最小移动距离0、1、2、3列
        int[][] newAbsRange = newAbsoluteShape.getRanges();
        if ((direct & 1) == 1) {
            //更新down
            for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
                down[i] = ShapeConstants.PAN_SIZE;
                for (int j = ShapeConstants.PAN_SIZE - 1; j > newAbsRange[i][3] && j >= y; j--) {
                    if (isSafe(j, x + i)) {
                        if (ShapeConstants.BOX.equals(BIG_PAN[j][x + i])) {
                            down[i] = j;
                        }
                    }
                }
            }
        }
        if ((direct & 2) == 2) {
            //更新左max右min
            for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
                left[i] = -1;
                for (int j = 0; j < newAbsRange[i][0]; j++) {
                    if (isSafe(y + i, j)) {
                        if (ShapeConstants.BOX.equals(BIG_PAN[y + i][j])) {
                            left[i] = j;
                        }
                    }
                }
            }
            //更新左max右min
            for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
                right[i] = ShapeConstants.PAN_SIZE;
                for (int j = ShapeConstants.PAN_SIZE - 1; j > newAbsRange[i][1] && j >= x; j--) {
                    if (isSafe(y + i, j)) {
                        if (ShapeConstants.BOX.equals(BIG_PAN[y + i][j])) {
                            right[i] = j;
                        }
                    }
                }
            }
        }
    }

    private static String leftPadding(int i) {
        String istr = String.valueOf(i);
        while (istr.length() < 2) {
            istr = " " + istr;
        }
        return istr;
    }

    /**
     * 启动moveDown
     */
    public void start() {
        moveDownThread.start();
    }

    /**
     * 后台执行向下移动
     */
    class MoveDown implements Runnable {
        private AtomicInteger nextInstruction = new AtomicInteger(CONTINUE);

        @Override
        public void run() {
            int current;
            while ((current = nextInstruction.get()) != EXIT) {
                if (current == PAUSE) {
                    try {
                        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        //正常中断
                    }
                } else if (current == CONTINUE) {
                    moveDown();
                    try {
                        TimeUnit.SECONDS.sleep(10000);
                    } catch (InterruptedException e) {
                        //正常中断
                    }
                }
            }
            continued.set(EXIT);
        }
    }
}