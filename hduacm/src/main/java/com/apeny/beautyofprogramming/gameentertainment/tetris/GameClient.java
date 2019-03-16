package com.apeny.beautyofprogramming.gameentertainment.tetris;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by monis on 2019/3/16.
 */
public class GameClient {
    public static void main(String[] args) {
        connect2GameServer();
    }

    private static void connect2GameServer() {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 9090);
            OutputStream outputStream = socket.getOutputStream();
            System.out.println("Instruction: Teris Game Made by Monis, Congratulations to you, Enjoy it.");
            System.out.println("Direction: left j, right l, down k, rotate c, speedup v, pause p, resume r, stop s, exit e, game over z");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String instructionStr = scanner.next();
                outputStream.write((instructionStr + "\n").getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
