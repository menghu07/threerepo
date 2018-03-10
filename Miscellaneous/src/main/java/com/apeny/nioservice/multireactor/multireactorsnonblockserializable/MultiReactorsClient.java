package com.apeny.nioservice.multireactor.multireactorsnonblockserializable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 使用Selector必须是非阻塞模式, SocketChannel OP_READ OP_WRITE OP_CONNECT这三个操作
 * 只有SocketChannel 注册到Selector 的read write
 * Created by apeny on 2018/2/25.
 */
public class MultiReactorsClient {

    public static void main(String[] args) {
        connectServer();
    }

    public static void connectServer() {
        SocketChannel socketChannel = null;
        Selector selector = null;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            SelectionKey key = connect(selector, socketChannel);
            handle(selector, key, socketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 连接到服务器
     *
     * @param selector
     * @param client
     * @throws IOException
     */
    private static SelectionKey connect(Selector selector, SocketChannel client) throws IOException {
        client.configureBlocking(false);
        SelectionKey key = client.register(selector, SelectionKey.OP_CONNECT);
        client.connect(new InetSocketAddress(9892));
        //带等待时间的多路选
        while (selector.select(1000) == 0) {
            // do nothing
        }
        if (key.isConnectable()) {
            client.finishConnect();
            System.out.println("connect success: " + "ha ha ha" + client + "sockect channel blocking: " + client.isBlocking());
        }
        return key;
    }

    /**
     * 使用非阻塞模式实现write和read
     *
     * @param selector 多路复用器
     * @param key      感兴趣键
     * @param client   客户端socketChannel
     */
    private static void handle(Selector selector, SelectionKey key, SocketChannel client) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //先注册写，再注册读
        key.interestOps(SelectionKey.OP_WRITE);
        key.attach(new Sender(key, client, byteBuffer));
        outLoop:
        while (selector.select() != 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isWritable()) {
                    int count;
                    byte[] dataFromConsole = new byte[1024];
                    System.out.print("hello apy:>");
                    while ((count = System.in.read(dataFromConsole)) != -1 && (count == 1 && dataFromConsole[0] == '\n')) {
                        System.out.print("hello apy:>");
                    }
                    if ("EXIT\n".equalsIgnoreCase(new String(dataFromConsole, 0, count))) {
                        break outLoop;
                    }
                    byteBuffer.clear();
                    byteBuffer.put(dataFromConsole, 0, count);
                }
                Runnable runnable = (Runnable) selectionKey.attachment();
                runnable.run();
            }
        }
    }

    /**
     * 消息接收者
     */
    static class Receiver implements Runnable {
        private SelectionKey key;
        private SocketChannel client;
        private ByteBuffer source;

        Receiver(SelectionKey key, SocketChannel client, ByteBuffer source) {
            this.key = key;
            this.client = client;
            this.source = source;
        }

        @Override
        public void run() {
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            try {
                int length = client.read(readBuffer);
                System.out.print("receive data: " + new String(readBuffer.array(), 0, length));
                key.interestOps(SelectionKey.OP_WRITE);
                key.attach(new Sender(key, client, source));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 消息发送者
     */
    static class Sender implements Runnable {
        private SelectionKey key;
        private SocketChannel client;
        private ByteBuffer source;

        Sender(SelectionKey key, SocketChannel client, ByteBuffer source) {
            this.key = key;
            this.client = client;
            this.source = source;
        }

        @Override
        public void run() {
            //先写数据，然后设置OP_READ
            try {
                source.flip();
                client.write(source);
                key.interestOps(SelectionKey.OP_READ);
                key.attach(new Receiver(key, client, source));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
