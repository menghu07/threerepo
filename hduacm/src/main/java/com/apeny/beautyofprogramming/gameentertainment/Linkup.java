package com.apeny.beautyofprogramming.gameentertainment;

import java.util.*;

/**
 * 连连看，使两个相同的图形消除掉，他们的最短路径最多有2个弯，在路径弯个数相同情况下，要求路径长度最短
 * Created by monis on 2019/2/24.
 */
public class Linkup {

    //存放的图片
    private static final String[] pictures = {"A", "B", "C", "D", "E"};

    private static final int SIZE = 8;

    private static final String NIL = "-";

    private static final Node[][] mainInterface = new Node[SIZE][SIZE];

    private static final int UP_DOWN = 1;

    private static final int LEFT_RIGHT = 2;

    private static final int ALL = 3;

    //可以存子节点最大拐点层级
    private static final int MAX_TURNING_LEVEL = 1;

    public static void main(String[] args) {
        startup();
    }

    private static void startup() {
        init();
        printState();
        Scanner scanner = new Scanner(System.in);
        System.out.println("消除两个相同元素，请输入参数：x1 y1 x2 y2");
        System.out.print("> ");
        while (scanner.hasNextLine()) {
            String nextValue = scanner.nextLine();
            nextValue = nextValue.trim();
            nextValue = nextValue.replaceAll("\\s+", " ");
            String[] fourIndexes = nextValue.split(" ");
            if (fourIndexes.length != 4) {
                System.out.println("输入参数个数必须等于4");
            } else {
                try {
                    int x1 = Integer.parseInt(fourIndexes[0]);
                    int y1 = Integer.parseInt(fourIndexes[1]);
                    int x2 = Integer.parseInt(fourIndexes[2]);
                    int y2 = Integer.parseInt(fourIndexes[3]);
                    if (check(x1, y1, x2, y2)) {
                        link(x1, y1, x2, y2);
                        printState();
                        System.out.println("请输入消除元素坐标:");
                    } else {
                        System.out.println("参数校验错误，请检查输入参数: "
                                + x1 + " " + y1 + " " + x2 + " " + y2);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("参数输入个数错误");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.print("> ");
            }
        }
    }

    /**
     * 检查参数正确性
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private static boolean check(int x1, int y1, int x2, int y2) {
        if (x1 < 0 || x1 >= SIZE || y1 < 0 || y1 >= SIZE) {
            System.out.println("数组越界,source=" + x1 + ", " + y1);
            return false;
        }
        if (x2 < 0 || x2 >= SIZE || y2 < 0 || y2 >= SIZE) {
            System.out.println("数组越界,target=" + x2 + ", " + y2);
            return false;
        }
        return !(mainInterface[x1][y1] == null || mainInterface[x2][y2] == null || mainInterface[x1][y1].isNil()
                || mainInterface[x2][y2].isNil() || !mainInterface[x1][y1].equalsValue(mainInterface[x2][y2]));
    }

    /**
     * 消除两个相同元素
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    private static void link(int x1, int y1, int x2, int y2) {
        Node source = mainInterface[x1][y1];
        Node target = mainInterface[x2][y2];
        //从源开始广度优先遍历顺时针方向开始上右下左
        //第一个拐点
        Queue<Node> s1 = new LinkedList<>();
        //第二个拐点
        Queue<Node> s2 = new LinkedList<>();
        Node zeroSource = new Node(source.x, source.y, source.value, null, ALL);
        boolean found = traverse(zeroSource, target, 0, s1);
        if (found) {
            return;
        }
        while (!s1.isEmpty()) {
            Node firstTurning = s1.remove();
            //第一个拐点
            found = traverse(firstTurning, target, 1, s2);
            if (found) {
                return;
            }
        }
        while (!s2.isEmpty()) {
            Node secondTurning = s2.remove();
            //第二个拐点
            found = traverse(secondTurning, target, 2, new LinkedList<>());
            if (found) {
                return;
            }
        }
        System.out.println("不能消除" + x1 + " " + y1 + " " + x2 + " " + y2);
    }

    /**
     * 遍历界面
     * @param tempSource
     * @param target
     * @param turningLevel
     * @param nils
     * @return
     */
    private static boolean traverse(final Node tempSource, Node target, int turningLevel, Queue<Node> nils) {
        int x1 = tempSource.x;
        int y1 = tempSource.y;
        Node source;
        if (tempSource.canDirection == UP_DOWN || tempSource.canDirection == ALL) {
            for (int x = x1 - 1; x >= 0; x--) {
                Node curNode = mainInterface[x][y1];
                if (curNode.equals(target)) {
                    //消除这个两个元素
                    System.out.println("success! Find path: " + tempSource + "->" + curNode);
                    source = tempSource;
                    while (source.previous != null) {
                        source = source.previous;
                    }
                    mainInterface[source.x][source.y] = new Node(source.x, source.y, NIL);
                    mainInterface[target.x][target.y] = new Node(target.x, target.y, NIL);
                    return true;
                }
                if (turningLevel <= MAX_TURNING_LEVEL && curNode.isNil()) {
                    nils.add(curNode.copyThis(tempSource, LEFT_RIGHT));
                } else {
                    //遇到非空的且不是目标的元素
                    break;
                }
            }
            for (int x = x1 + 1; x < SIZE; x++) {
                Node curNode = mainInterface[x][y1];
                if (curNode.equals(target)) {
                    //消除这个两个元素
                    System.out.println("success! Find path: " + tempSource + "->" + curNode);
                    source = tempSource;
                    while (source.previous != null) {
                        source = source.previous;
                    }
                    mainInterface[source.x][source.y] = new Node(source.x, source.y, NIL);
                    mainInterface[target.x][target.y] = new Node(target.x, target.y, NIL);
                    return true;
                }
                if (turningLevel <= MAX_TURNING_LEVEL && curNode.isNil()) {
                    nils.add(curNode.copyThis(tempSource, LEFT_RIGHT));
                } else {
                    //遇到非空的且不是目标的元素
                    break;
                }
            }
        }
        if (tempSource.canDirection == LEFT_RIGHT || tempSource.canDirection == ALL) {
            for (int y = y1 - 1; y >= 0; y--) {
                Node curNode = mainInterface[x1][y];
                if (curNode.equals(target)) {
                    System.out.println("success! Find path: " + tempSource + "->" + curNode);
                    //消除这个两个元素
                    source = tempSource;
                    while (source.previous != null) {
                        source = source.previous;
                    }
                    mainInterface[source.x][source.y] = new Node(source.x, source.y, NIL);
                    mainInterface[target.x][target.y] = new Node(target.x, target.y, NIL);
                    return true;
                }
                if (curNode.isNil()) {
                    nils.add(curNode.copyThis(tempSource, UP_DOWN));
                } else {
                    //遇到非空的且不是目标的元素
                    break;
                }
            }
            for (int y = y1 + 1; y < SIZE; y++) {
                Node curNode = mainInterface[x1][y];
                if (curNode.equals(target)) {
                    System.out.println("success! Find path: " + tempSource + "->" + curNode);
                    //消除这个两个元素
                    source = tempSource;
                    while (source.previous != null) {
                        source = source.previous;
                    }
                    mainInterface[source.x][source.y] = new Node(source.x, source.y, NIL);
                    mainInterface[target.x][target.y] = new Node(target.x, target.y, NIL);
                    return true;
                }
                if (curNode.isNil()) {
                    nils.add(curNode.copyThis(tempSource, UP_DOWN));
                } else {
                    //遇到非空的且不是目标的元素
                    break;
                }
            }
        }
        return false;
    }

    private static void printState() {
        boolean allNil = true;
        for (int i = 0; i < SIZE; i++) {
            System.out.print(leftPadding(i) + " [");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(mainInterface[i][j].value + "(" + leftPadding(i) + "," + leftPadding(j) + ")");
                if (!mainInterface[i][j].isNil()) {
                    allNil = false;
                }
            }
            System.out.println("]");
        }
        if (allNil) {
            System.out.println("congratulations, u win.");
            System.exit(0);
        }
    }

    private static String leftPadding(int i) {
        String istr = String.valueOf(i);
        while (istr.length() < 1) {
            istr = " " + istr;
        }
        return istr;
    }

    /**
     * 初始化游戏界面
     */
    private static void init() {
        Random random = new Random();
        List<Integer[]> allElements = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                allElements.add(new Integer[]{i, j});
            }
        }
        int nilSize = (int) (allElements.size() * 0.3);
        int i = 0;
        if (nilSize % 2 != 0) {
            nilSize++;
        }
        while (i < nilSize) {
            Integer[] nilElement = allElements.remove(random.nextInt(allElements.size()));
            mainInterface[nilElement[0]][nilElement[1]] = new Node(nilElement[0], nilElement[1], NIL);
            i++;
        }
        while (!allElements.isEmpty()) {
            String elementValue = pictures[random.nextInt(pictures.length)];
            Integer[] firstElement = allElements.remove(random.nextInt(allElements.size()));
            Integer[] secondElement = allElements.remove(random.nextInt(allElements.size()));
            mainInterface[firstElement[0]][firstElement[1]] = new Node(firstElement[0], firstElement[1], elementValue);
            mainInterface[secondElement[0]][secondElement[1]] = new Node(secondElement[0], secondElement[1], elementValue);
        }
    }

    /**
     * 元素
     */
    private static class Node {
        private final int x;

        private final int y;

        private final String value;

        private Node previous;

        private int canDirection;

        private Node(int x, int y, String value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        private Node(int x, int y, String value, Node previous, int canDirection) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.previous = previous;
            this.canDirection = canDirection;
        }

        /**
         * 是否为空
         * @return
         */
        private boolean isNil() {
            return NIL.equals(value);
        }

        private Node copyThis(Node previous, int canDirection) {
            return new Node(x, y, value, previous, canDirection);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            if (previous != null) {
                sb.append(previous + "->");
            }
            sb.append("Node{");
            sb.append("x=").append(x);
            sb.append(", y=").append(y);
            sb.append(", value='").append(value).append('\'');
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return x == node.x && y == node.y && value != null && value.equals(node.value);
        }

        /**
         * 只比较value值
         * @param node
         * @return
         */
        public boolean equalsValue(Node node) {
            if (this == node) {
                return true;
            }
            return value != null && value.equals(node.value);
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }
    }
}
