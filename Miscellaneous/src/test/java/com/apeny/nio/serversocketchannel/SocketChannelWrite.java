package com.apeny.nio.serversocketchannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2018/2/27.
 */
public class SocketChannelWrite {
    public static void main(String[] args) {
        testClient();
    }


    private static void testClient() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(9892));
            socketChannel.close();
            ByteBuffer byteBuffer = ByteBuffer.wrap("send to Server hello".getBytes());
            socketChannel.write(byteBuffer);
            System.out.println(new String(byteBuffer.array()) + " || end" + byteBuffer.array().length);
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
