package com.apeny.nioservice.singlereactor;

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
    }

    public static void connectServer() {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(9892));
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put("yyyyyy".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
            socketChannel.read(byteBuffer);
            System.out.println("i get response from Reactor Server : " + new String(byteBuffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
