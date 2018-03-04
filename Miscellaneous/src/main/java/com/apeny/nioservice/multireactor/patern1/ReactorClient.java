package com.apeny.nioservice.multireactor.patern1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 使用Selector必须是非阻塞模式, SocketChannel OP_READ OP_WRITE OP_CONNECT这三个操作
 * 只有SocketChannel 注册到Selector 的read write 和对应的Selector非阻塞
 * Created by apeny on 2018/2/25.
 */
public class ReactorClient {

    public static void main(String[] args) {
//        connectServer();
        multiClientsWhile();
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
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void connectServer() {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(9892));
            System.out.println("connect success: " + "ha ha ha" + socketChannel + "sockect channel blocking: " + socketChannel.isBlocking());
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put("yyyyyy".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            System.out.println("write yyyy x : " + socketChannel);
            byteBuffer.clear();
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(readBuffer);
            System.out.println("i get response from Reactor Server : " + new String(readBuffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void multiClientsWhile() {
        int count = 100;
        int i = 0;
        while (i < count) {
            connectServer();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
