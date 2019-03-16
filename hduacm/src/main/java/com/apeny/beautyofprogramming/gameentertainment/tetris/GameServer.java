package com.apeny.beautyofprogramming.gameentertainment.tetris;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by monis on 2019/3/16.
 */
public class GameServer {
    public static void main(String[] args) {
        acceptClient();
    }

    /**
     * 接收客户端游戏连接
     */
    private static void acceptClient() {
        try {
            ServerSocket serverSocket = new ServerSocket(9090);
            while (true) {
                Socket server = null;
                try {
                    server = serverSocket.accept();
                    server.setSoTimeout(600_000);
                    InputStream inputStream = server.getInputStream();
                    TerisGame.playGame(inputStream);
                } finally {
                    if (server != null) {
                        server.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
