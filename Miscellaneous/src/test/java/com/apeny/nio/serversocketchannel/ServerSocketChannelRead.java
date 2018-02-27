package com.apeny.nio.serversocketchannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/** ServerSocketChannel/SocketChannel close后client可以写/读，半关闭的socket，另一端还可以写；关闭整个程序，client就不能写了
 *  client办关闭，server的读阻塞会终止，然后往下执行
 * Created by apeny on 2018/2/27.
 */
public class ServerSocketChannelRead {
    public static void main(String[] args) {
        serverSocketChannelRead();
    }

    private static void serverSocketChannelRead() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
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
//                    socketChannel.configureBlocking(false);
                    System.out.println("local address: " + socketChannel.getLocalAddress() + "; remote address " + socketChannel.getRemoteAddress());
                    socketChannel.read(byteBuffer);
                    System.out.println("number: " + new String(byteBuffer.array()) + ", has how much bytes");
                    byteBuffer.clear();
                    socketChannel.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
