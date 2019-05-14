package com.apeny.beautyofprogramming.digitcharm;

/**
 * 斐波那契数列
 * Created by monis on 2019/5/12.
 */
public class FibonacciSeries {

    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        System.out.println("2 value = " + fabonacci(8));
    }

    private static int fabonacci(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        FMatrix fMatrix = new FMatrix(2);
        fMatrix.setAll(new int[][] {{1, 1}, {1, 0}});
        fMatrix.pow(n - 1);
        return fMatrix.getElement(0, 0);
    }
}

class FMatrix {

    private int[][] matrix;

    private final int m;

    FMatrix(int m) {
        if (m <= 0) {
            throw new IllegalArgumentException("参数错误");
        }
        matrix = new int[m][m];
        this.m = m;
    }

    public void setAll(int[][] arr) {
        if (arr == null || arr.length != m || arr[0].length != m) {
            throw new IllegalArgumentException("参数错误");
        }
        matrix = arr;
    }

    /**
     * 设置某个元素
     * @param x
     * @param y
     * @param value
     */
    public void setElement(int x, int y, int value) {
        if (x < 0 || y < 0 || x > m || y > m) {
            throw new IllegalArgumentException("参数错误");
        }
        matrix[x][y] = value;
    }

    /**
     * 获取指定位置元素值
     * @param x
     * @param y
     * @return
     */
    public int getElement(int x, int y) {
        if (x < 0 || y < 0 || x > m || y > m) {
            throw new IllegalArgumentException("参数错误");
        }
        return matrix[x][y];
    }

    /**
     * 矩阵乘法
     * @param other
     * @return
     */
    public final FMatrix multiply(FMatrix other) {
        if (other == null || other.matrix == null || other.m != m) {
            throw new IllegalArgumentException("参数错误");
        }
        FMatrix result = new FMatrix(m);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                int element = 0;
                for (int column = 0; column < m; column++) {
                    element += matrix[i][column] * other.matrix[column][j];
                }
                result.matrix[i][j] = element;
            }
        }
        matrix = result.matrix;
        return result;
    }

    private FMatrix copyThis() {
        FMatrix copy = new FMatrix(m);
        for (int i = 0; i < m; i++) {
            System.arraycopy(matrix[i], 0, copy.matrix[i], 0, m);
        }
        return copy;
    }

    /**
     * 矩阵n次幂
     * @param n
     * @return
     */
    public FMatrix pow(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("参数错误");
        }
        if (n == 1) {
            return copyThis();
        }
        FMatrix temp = copyThis();
        FMatrix result = unit();
        for (int k = n; k > 0; k >>>= 1) {
            //此位置存在A2^i
            if ((k & 0x1) == 1) {
                result.multiply(temp);
            }
            temp.multiply(temp);
        }
        matrix = result.matrix;
        return result;
    }

    /**
     * 单位矩阵
     * @return
     */
    public final FMatrix unit() {
        FMatrix unitOne = new FMatrix(m);
        for (int i = 0; i < m; i++) {
            unitOne.matrix[i][i] = 1;
        }
        return unitOne;
    }
}
