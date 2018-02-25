package com.apeny.nioservice.classicserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2018/2/25.
 */
public class ClassicServerSocket {
    public static void main(String[] args) throws IOException {
        startServer();
    }

    private static void startServer() {
        Thread serverThread = new Thread(new ClassicServer());
        serverThread.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serverThread.interrupt();
        System.out.println("main end.......");
    }
}

class ClassicServer implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9081);
            while (!Thread.interrupted()) {
                new Thread(new ClassicHandler(serverSocket.accept())).start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class ClassicHandler implements Runnable {
    private Socket socket;

    ClassicHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        byte[] input = new byte[1024];
        try {
            socket.getInputStream().read(input);
            byte[] output = procoss(input);
            socket.getOutputStream().write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 处理请求
     *
     * @param input
     * @return
     */
    private byte[] procoss(byte[] input) {
        return new byte[1024];
    }
}
