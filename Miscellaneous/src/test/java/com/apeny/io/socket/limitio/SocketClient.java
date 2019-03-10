package com.apeny.io.socket.limitio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 重新ReuseAddress 重连其他服务器,9020 9021
 * Created by apeny on 2018/2/27.
 */
public class SocketClient {
    public static void main(String[] args) {
        Socket newClient = new Socket();
        byte[] bigBytes = new byte[1024];
        int port = 9082;
        int serverPort = 9020;
        int newServerPort = 9021;
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket client = new Socket();
                        client.bind(new InetSocketAddress(port));
                        client.connect(new InetSocketAddress("192.168.0.128", serverPort), 25000);
                        //注释部分表示重用连接地址在上个相同端口在占用的超时状态时
                        System.out.println("client already close");
//            newClient.setReuseAddress(true);
//            newClient.bind(new InetSocketAddress(port));
//            newClient.connect(new InetSocketAddress("192.168.0.100", newServerPort));
                        OutputStream outputStream = client.getOutputStream();
                        System.out.println("my socket : " + client.getPort() + " get localPort" + client.getLocalPort());
                        System.out.println(client.getSoLinger());
                        InputStream inputStream = client.getInputStream();
                        byte[] bytes = new byte[500];
                        outputStream.write(bigBytes);
                        int length = inputStream.read(bytes);
                        System.out.println("read length: "+ length + " i have receive some message from server" + new String(bytes));
                        System.out.println("i one write large bytes to server");
                        //在读入后面的内容
                        int secondLength = inputStream.read(bytes);
                        System.out.println("second read length: " + secondLength);
//            outputStream.write(bigBytes);
//            outputStream.write(bigBytes);
//            outputStream.write((System.nanoTime() + "," + Thread.currentThread() + "my name huhu, make friends?").getBytes());
                        client.shutdownOutput();
                        client = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            System.out.println("i two write large bytes to server");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class CloseClient implements Runnable {
        Socket socket;

        CloseClient(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
                try {
                    System.out.println("close begin: " + new Date());
                    socket.close();
                    System.out.println("close end: " + new Date());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
