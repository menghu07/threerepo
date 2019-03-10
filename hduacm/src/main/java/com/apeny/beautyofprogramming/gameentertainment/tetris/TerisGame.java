package com.apeny.beautyofprogramming.gameentertainment.tetris;

import java.util.Scanner;

/**
 * Created by monis on 2019/3/10.
 */
public class TerisGame {

    private static final int RIGHT = 108;

    private static final int DOWN = 107;

    private static final int LEFT = 106;

    private static final int START = 110;

    private static final int PAUSE = 112;

    private static final int RESUME = 114;

    private static final int EXIT = 101;

    private static final int GAME_OVER = 122;

    public static void main(String[] args) {
        playGame();
    }

    /**
     * 游戏主程序
     */
    private static void playGame() {
        System.out.println("Instruction: Teris Game Made by Monis, Congratulations to you, Enjoy it.");
        System.out.println("Direction: left j, right l, down k, start n, pause p, resume r, exit e, game over z");
        ChessBoard currentChessBoard = null;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String instructionStr = scanner.next();
            int instruction = instructionStr.charAt(0);
            switch (instruction) {
                case START:
                    if (currentChessBoard == null || currentChessBoard.isExited()) {
                        currentChessBoard = new ChessBoard();
                        currentChessBoard.newShape();
                        currentChessBoard.start();
                    }
                    break;
                case RIGHT:
                    if (currentChessBoard != null) {
                        currentChessBoard.moveRight();
                    }
                    break;
                case DOWN:
                    if (currentChessBoard != null) {
                        currentChessBoard.moveDown();
                    }
                    break;
                case LEFT:
                    if (currentChessBoard != null) {
                        currentChessBoard.moveLeft();
                    }
                    break;
                case PAUSE:
                case RESUME:
                case EXIT:
                    if (currentChessBoard != null) {
                        currentChessBoard.sendInstruction(instruction);
                    }
                    break;
                case GAME_OVER:
                    System.out.println("you had exited game.");
                    System.exit(0);
                default:
                    break;
            }
        }
    }
}