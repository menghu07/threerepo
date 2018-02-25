package com.apeny.nioservice.singlereactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2018/2/25.
 */
public class SingleReactor implements Runnable {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public static void main(String[] args) {
//        startServer();
        startReactor();
    }

    private static void startServer() {
        try {
            Thread thread = new Thread(new SingleReactor(9092));
            thread.start();
            TimeUnit.SECONDS.sleep(2);
            thread.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startReactor() {
        try {
            new Thread(new SingleReactor(9892)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startReactorClient() {
        ReactorClient.connectServer();
    }

    SingleReactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor());
    }

    /**
     * 其他线程设置这个线程的中断标志后这个线程就会终止循环
     */
    @Override
    public void run() {
        try {
            //检测是否有其他线程设置了中断标志位
            while (!Thread.interrupted()) {
                int count = selector.select();
                System.out.println("selector select something......" + count);
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    System.out.println("selection key readable:  " + selectionKey.isReadable() + ", acceptable: " + selectionKey.isAcceptable() + ", writable: " + selectionKey.isWritable());
                    dispatch(selectionKey);
                    iterator.remove();
                }
                //设置中断标志，线程中断自己或其他线程中断自己
//                Thread.currentThread().interrupt();
            }
            System.out.println("reactor thread is interrupted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey selectionKey) {
        Runnable runnable = (Runnable) selectionKey.attachment();
        //单线程，直接在Server线程处理请求
        if (runnable != null) {
            runnable.run();
        }
    }

    /**
     * 处理接受事件
     */
    class Acceptor implements Runnable {

        @Override
        public void run() {
            try {
                //因为是非阻塞的，要判断是否SocketChannel为空
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new ReactorHandler(selector, socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

final class ReactorHandler implements Runnable {
    private final SocketChannel socketChannel;
    private final SelectionKey selectionKey;

    private ByteBuffer input = ByteBuffer.allocate(1024);
    private ByteBuffer output = ByteBuffer.allocate(1024);
    private static final int READING = 0;
    private static final int SEND = 1;
    int state = READING;

    ReactorHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        selectionKey = socketChannel.register(selector, 0);
        selectionKey.attach(this);
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }


    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SEND) {
                send();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void process(String readData) {
        System.out.println("process read data: " + readData);
        // do something
    }

    private boolean inputIsCompleted() {
        return true;
    }

    private boolean outputIsCompleted() {
        return true;
    }

    private void read() throws IOException {
        socketChannel.read(input);
        if (inputIsCompleted()) {
            process(new String(input.array()));
            state = SEND;
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void send() throws IOException {
        input.flip();
        byte[] temp = new byte[input.limit()];
        input.get(temp);
        output.put(("i had receive message: [" + new String(temp) + "]; thank u").getBytes());
        output.flip();
        socketChannel.write(output);
        if (outputIsCompleted()) {
            selectionKey.cancel();
        }
    }
}

