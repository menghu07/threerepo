package com.apeny.nio;

import com.mysql.fabric.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2018/2/4.
 */
public class SelectorTest {
    public static void main(String[] args) {
//        serverSocketChannel();
        testSelector();
    }

    private static void testSelector() {
        try {

            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(9892));
//!            serverSocket.accept();
            SocketChannel server = serverSocketChannel.accept();
            System.out.println("server value is : " + server);
            int i = SelectionKey.OP_ACCEPT;
            server = serverSocketChannel.accept();
            boolean xi = (i & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
            int y = SelectionKey.OP_READ;
            int z = SelectionKey.OP_WRITE;
            int x = SelectionKey.OP_CONNECT;
            while (true) {
                int selectedCount = selector.select();
                if (selectedCount == 0) {
                    continue;
                }
                Set<SelectionKey> set = selector.selectedKeys();
                Iterator<SelectionKey> iterator = set.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        // a connection is accepted by a ServerSocketChannel
                    } else if (key.isConnectable()) {
                        // a connection is established with a remote Server

                    } else if (key.isReadable()) {
                        // a channel is ready for reading
                    } else if (key.isWritable()) {
                        // a channel is ready for writing
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void serverSocketChannel() {
        String greeting = "hello, im collecting u";
        ByteBuffer byteBuffer = ByteBuffer.wrap(greeting.getBytes());
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(9892));
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel == null) {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("local address: " + socketChannel.getLocalAddress() + "; remote address " + socketChannel.getRemoteAddress());
                    byteBuffer.rewind();
                    socketChannel.write(byteBuffer);
                    byteBuffer.rewind();
                    System.out.println(new String(byteBuffer.array()));
                    socketChannel.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
