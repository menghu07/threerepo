package com.apeny.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 两次连续发送，三次连续接收
 * Created by apeny on 2018/2/4.
 */
public class SelectorClientTest {
    public static void main(String[] args) {
        testClient();
    }

    private static void testClient() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(9892));
            ByteBuffer byteBuffer = ByteBuffer.wrap("223".getBytes());
            socketChannel.write(byteBuffer);
            byteBuffer.rewind();
            socketChannel.write(byteBuffer);
            byteBuffer.flip();
            int i = 0;
            System.out.println(new String(byteBuffer.array()) + " || end" + byteBuffer.array().length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
