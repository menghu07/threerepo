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
        Socket client = new Socket();
        Socket newClient = new Socket();
        byte[] bigBytes = new byte[Integer.MAX_VALUE - 2];
        int port = 9082;
        int serverPort = 9020;
        try {
            client.setReuseAddress(true);
            client.bind(new InetSocketAddress(port));
            client.connect(new InetSocketAddress("192.168.0.100", serverPort));
            client.close();
//            new Thread(new CloseClient(client)).start();
            newClient.setReuseAddress(true);
            newClient.bind(new InetSocketAddress(port));
            newClient.connect(new InetSocketAddress("192.168.0.100", serverPort));
            OutputStream outputStream = client.getOutputStream();
            outputStream.write(bigBytes);
            outputStream.write((System.nanoTime() + "," + Thread.currentThread() + "my name huhu, make friends?").getBytes());
            client.close();
            System.out.println("my socket : " + client.getPort() + " get localPort" + client.getLocalPort());
            client.setSoTimeout(60 * 1000 * 1000);
            System.out.println(client.getSoLinger());
            client.setSoLinger(true, 3600);
            InputStream inputStream = client.getInputStream();
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
            System.out.println("i have receive some message from server" + new String(bytes));
        } catch (Exception e) {
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
