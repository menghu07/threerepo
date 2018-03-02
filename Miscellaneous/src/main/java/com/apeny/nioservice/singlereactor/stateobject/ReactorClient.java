package com.apeny.nioservice.singlereactor.stateobject;

import com.sun.org.apache.bcel.internal.generic.Select;

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
        connectServer();
    }

    private static void connectServer() {
        try {
            Selector selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress(9892));
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                System.out.println("key : " + key + "event name connect: " + key.isConnectable() + " size: " + selectionKeys.size());
                socketChannel.finishConnect();
            }
            System.out.println("connect success: " + "ha ha ha" + socketChannel + "socket channel blocking: " + socketChannel.isBlocking());
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            selector.select();
            socketChannel.finishConnect();
            new Sender(socketChannel).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Sender implements Runnable {
        private SocketChannel socketChannel;

        Sender(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.put("yyyyyy".getBytes());
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                System.out.println("write yyyy x : " + socketChannel);
                byteBuffer.clear();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                socketChannel.read(readBuffer);
                System.out.println("i get response from Reactor Server : " + new String(readBuffer.array()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
