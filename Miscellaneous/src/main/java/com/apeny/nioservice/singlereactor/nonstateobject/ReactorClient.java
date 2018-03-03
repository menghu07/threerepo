package com.apeny.nioservice.singlereactor.nonstateobject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2018/2/25.
 */
public class ReactorClient {

    public static void main(String[] args) {
        connectServer();
//        multiClients();
    }

    private static void multiClients() {
        while (true) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    connectServer();
                }
            }).start();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void connectServer() {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(9892));
            socketChannel.configureBlocking(false);
            System.out.println("connect success: " + "ha ha ha" + socketChannel + "sockect channel blocking: " + socketChannel.isBlocking());
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put("yyyyyy".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            System.out.println("write yyyy x : " + socketChannel);
            byteBuffer.clear();
            socketChannel.read(byteBuffer);
            System.out.println("i get response from Reactor Server : " + new String(byteBuffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
