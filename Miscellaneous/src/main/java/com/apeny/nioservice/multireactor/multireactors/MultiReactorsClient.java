package com.apeny.nioservice.multireactor.multireactors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * 使用Selector必须是非阻塞模式, SocketChannel OP_READ OP_WRITE OP_CONNECT这三个操作
 * 只有SocketChannel 注册到Selector 的read write 和对应的Selector非阻塞
 * Created by apeny on 2018/2/25.
 */
public class MultiReactorsClient {

    public static void main(String[] args) {
        connectServer();
    }

    public static void connectServer() {
        try {
            byte[] dataFromConsole = new byte[1024];
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(9892));
            System.out.println("connect success: " + "ha ha ha" + socketChannel + "sockect channel blocking: " + socketChannel.isBlocking());
            System.out.print("hello apy:>");
            int count;
            while ((count = System.in.read(dataFromConsole)) != -1) {
                if (count == 1 && dataFromConsole[0] == '\n') {
                    continue;
                }
                ByteBuffer byteBuffer = ByteBuffer.wrap(dataFromConsole, 0, count - 1);
                socketChannel.write(byteBuffer);
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                socketChannel.read(readBuffer);
                System.out.println(new String(readBuffer.array()));
                System.out.print("hello apy:>");
            }
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
