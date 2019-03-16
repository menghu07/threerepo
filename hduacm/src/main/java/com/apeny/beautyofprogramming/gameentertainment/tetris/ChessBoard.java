package com.apeny.beautyofprogramming.gameentertainment.tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        synchronized (DRAW_LOCK) {
            Random r = new Random();
            Shape newedShape = ShapeConstants.SHAPES[r.nextInt(ShapeConstants.ALL_SHAPES_SIZE)];
//            Shape newedShape = ShapeConstants.SHAPES[17];
            int minY = newedShape.getMinY();
            x = (ShapeConstants.PAN_SIZE - ShapeConstants.BLOCK_SIZE) / 2;
            previousX = x;
            y = 0 - minY;
            previousY = y;
            current = newedShape;
            //校验是否满格
            if (isValid()) {
                updateEdge(DOWN_DIRECTION | RIGHT_LEFT_DIRECTION);
                clearAndWrite(ShapeConstants.BOX);
                printThis();
            } else {
                clearAndWrite(ShapeConstants.ASTERISK);
                printThis();
                current = null;
                continued.set(EXIT);
                System.out.println("Game Exit.");
            }
        }
    }

    /**
     * 通过旋转新建图形
     */
    public final void rotateNewShape() {
        //初始时previousX = x，previousY = y
        //先随机出一个图形，然后以图形最上边缘为与y=0重合为基准点
        synchronized (DRAW_LOCK) {
            if (current != null) {
                Shape newedShape = current.getNext();
                if (independentIsValid(x, y, newedShape, current)) {
                    previousX = x;
                    previousY = y;
                    setShapeMark(ShapeConstants.DOT);
                    current = newedShape;
                    setShapeMark(ShapeConstants.BOX);
                    //翻转触底，判断旋转后图形是否与先前的y坐标最小距离为1
                    if (isTouchBottom()) {
                        clearFullLine();
                        //创建新图形
                        newShape();
                    } else {
                        printThis();
                    }
                }
            }
        }
    }

    /**
     * 验证是否满格，即有没有某个元素与新生成的图形有重合#
     *
     * @return
     */
    private boolean isValid() {
        AbsoluteShape absoluteShape = toAbsoluteShape();
        int[][] absRange = absoluteShape.getRanges();
        for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
            for (int j = absRange[i][0]; j <= absRange[i][1]; j++) {
                if (isSafe(y + i, j)) {
                    if (ShapeConstants.BOX.equals(BIG_PAN[y + i][j])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * 验证是否满格，即有没有某个元素与新生成的图形有重合#
     *
     * @param ix
     * @param iy
     * @param icurrent
     * @param exceptShape
     * @return
     */
    private boolean independentIsValid(int ix, int iy, Shape icurrent, Shape exceptShape) {
        if (!isSafe(ix, iy)) {
            return false;
        }
        AbsoluteShape absoluteShape = independentToAbsoluteShape(ix, iy, icurrent);
        AbsoluteShape exceptAbsShape = independentToAbsoluteShape(ix, iy, exceptShape);
        int[][] exceptAbsRange = exceptAbsShape.getRanges();
        List<Integer[]> exceptIndexes = new ArrayList<>();
        for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
            for (int j = exceptAbsRange[i][0]; j <= exceptAbsRange[i][1]; j++) {
                if (isSafe(iy + i, j)) {
                    exceptIndexes.add(new Integer[]{iy + i, j});
                }
            }
        }
        int[][] absRange = absoluteShape.getRanges();
        for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
            LOOPTWO:
            for (int j = absRange[i][0]; j <= absRange[i][1]; j++) {
                if (isSafe(iy + i, j)) {
                    //去掉曾经占用过的点
                    for (Integer[] e : exceptIndexes) {
                        if (e[0] == iy + i && e[1] == j) {
                            continue LOOPTWO;
                        }
                    }
                    if (ShapeConstants.BOX.equals(BIG_PAN[iy + i][j])) {
                        return false;
                    }
                } else if (j != ShapeConstants.NIL) {
                    //保证所有元素显示在PAN中
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 触底条件
     *
     * @return
     */
    private boolean isTouchBottom() {
        AbsoluteShape absoluteShape = toAbsoluteShape();
        //取最小移动距离0、1、2、3列
        int[][] absRange = absoluteShape.getRanges();
        int moveY = ShapeConstants.PAN_SIZE - 1;
        for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
            if (absRange[i][3] != ShapeConstants.NIL) {
                int tempY = down[i] - absRange[i][3];
                if (tempY < moveY) {
                    moveY = tempY;
                }
            }
        }
        return moveY <= 1;
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
                    clearAndWrite(ShapeConstants.BOX);
                    printThis();
                }
                updateEdge(DOWN_DIRECTION);
                if (isTouchBottom()) {
                    newShape();
                }
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
                    clearAndWrite(ShapeConstants.BOX);
                }
                //如果触底，就生成一个新的图形
                if (moveY == 1 || moveY == 0) {
                    clearFullLine();
                    newShape();
                } else if (moveY > 1) {
                    printThis();
                    updateEdge(RIGHT_LEFT_DIRECTION);
                }
            }
        }
    }

    /**
     * 加速向下移动
     */
    public void speedupMoveDown() {
        synchronized (DRAW_LOCK) {
            if (current != null) {
                while (true) {
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
                        clearAndWrite(ShapeConstants.BOX);
                    }
                    //如果触底，就生成一个新的图形
                    if (moveY == 1 || moveY == 0) {
                        break;
                    } else if (moveY > 1) {
                        updateEdge(RIGHT_LEFT_DIRECTION);
                    }
                }
                clearFullLine();
                newShape();
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
                    clearAndWrite(ShapeConstants.BOX);
                    printThis();
                }
                updateEdge(DOWN_DIRECTION);
                if (isTouchBottom()) {
                    newShape();
                }
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
     * 消除满行
     */
    private void clearFullLine() {
        //一行一行消除
        int maxLine = ShapeConstants.PAN_SIZE - 1;
        boolean hasFullLine = true;
        while (hasFullLine) {
            hasFullLine = false;
            boolean full = false;
            for (int i = maxLine; i >= y && i >= 0; i--) {
                boolean subfull = true;
                for (int j = 0; subfull && j < ShapeConstants.PAN_SIZE; j++) {
                    subfull = ShapeConstants.BOX.equals(BIG_PAN[i][j]);
                }
                if (subfull) {
                    if (maxLine > i) {
                        maxLine = i;
                    }
                    full = true;
                    break;
                }
            }
            if (full) {
                //只是所有BOX下标+1，不更新down left right数组
                int i = maxLine - 1;
                for (; i >= 0; i--) {
                    boolean subAllBox = true;
                    boolean subAllDot = true;
                    for (int j = 0; j < ShapeConstants.PAN_SIZE; j++) {
                        if (ShapeConstants.BOX.equals(BIG_PAN[i][j])) {
                            subAllDot = false;
                        } else {
                            subAllBox = false;
                        }
                        BIG_PAN[i + 1][j] = BIG_PAN[i][j];
                    }
                    if (subAllBox) {
                        hasFullLine = true;
                    }
                    if (subAllDot) {
                        break;
                    }
                }
                if (i < 0) {
                    for (int j = 0; j < ShapeConstants.PAN_SIZE; j++) {
                        BIG_PAN[0][j] = ShapeConstants.DOT;
                    }
                }
            } else {
                break;
            }
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
                System.out.println(" (" + y + "," + x + ") Direction: left j, right l, down k, rotate c, speedup v, pause p, resume r,"
                        + " stop s, exit e, game over z" + ":: " + Arrays.toString(down));
            } else {
                System.out.println();
            }
        }
        System.out.println("--------------end----------------");
    }

    /**
     * 重新绘制Shape
     *
     * @param targetValue
     */
    private void clearAndWrite(String targetValue) {
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
                        safeSetBox(y + i, x + j, targetValue);
                    }
                }
            }
        }
    }

    /**
     * 设置图形mark
     *
     * @param targetValue
     */
    private void setShapeMark(String targetValue) {
        if (current != null) {
            int[][] relRanges = current.getRanges();
            //设置当前方块
            for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
                for (int j = relRanges[i][0]; j <= relRanges[i][1]; j++) {
                    if (isSafe(i, j)) {
                        safeSetBox(y + i, x + j, targetValue);
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

    /**
     * 独立的获取绝对图形
     *
     * @return
     */
    private AbsoluteShape independentToAbsoluteShape(int ix, int iy, Shape icurrent) {
        if (icurrent == null) {
            throw new UnsupportedOperationException("当前不存在图形");
        }
        AbsoluteShape absoluteShape = new AbsoluteShape(icurrent);
        int[][] relRanges = icurrent.getRanges();
        int[][] ranges = new int[ShapeConstants.BLOCK_SIZE][ShapeConstants.BLOCK_SIZE];
        for (int i = 0; i < ShapeConstants.BLOCK_SIZE; i++) {
            for (int j = 0; j < ShapeConstants.BLOCK_SIZE; j++) {
                if (relRanges[i][j] == ShapeConstants.NIL) {
                    ranges[i][j] = ShapeConstants.NIL;
                } else {
                    if (j < 2) {
                        ranges[i][j] = ix + relRanges[i][j];
                    } else {
                        ranges[i][j] = iy + relRanges[i][j];
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
     *
     * @param direct 1 down 2 right&left 3 down&right&left
     */
    private void updateEdge(int direct) {
        AbsoluteShape newAbsoluteShape = toAbsoluteShape();
        //取最小移动距离0、1、2、3列
        int[][] newAbsRange = newAbsoluteShape.getRanges();
        if ((direct & DOWN_DIRECTION) == DOWN_DIRECTION) {
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
        if ((direct & RIGHT_LEFT_DIRECTION) == RIGHT_LEFT_DIRECTION) {
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
            int currentInstruction;
            while ((currentInstruction = nextInstruction.get()) != EXIT) {
                if (currentInstruction == PAUSE) {
                    try {
                        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        //正常中断
                    }
                } else if (currentInstruction == CONTINUE) {
                    moveDown();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        //正常中断
                    }
                }
            }
            synchronized (DRAW_LOCK) {
                current = null;
                continued.set(EXIT);
            }
        }
    }
}