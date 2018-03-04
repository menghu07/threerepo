package com.apeny.nioservice.multireactor.patern1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 1 多线程处理请求(包括解码 计算 编码)
 * 2  要在select 执行之前 设定SelectionKey.setInterestOps
 * 3 select()的read 事件触发条件：SelectionKey.setInterestOps是OP_READ且有数据可读
 *   select()的write事件触发条件：SelectionKey.setInterestOps是OP_WRITE就触发
 *   selector.select()往下执行条件：对端close()或对端程序崩溃或关机等
 *   selector.wakeup()作用唤醒selector,但selector上的[事件不会丢失]，wakeup当前阻塞selector.select()或下一个selector.select()的调用
 * Created by apeny on 2018/3/3.
 */
public class MultiServer {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public static void main(String[] args) {
        startReactor();
    }

    private static void startReactor() {
        try {
            new MultiServer(9892).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MultiServer(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor());
    }

    /**
     * 其他线程设置这个线程的中断标志后这个线程就会终止循环
     * ServerSocketChannel的选择键SelectionKey 一次只能有一个且仅有一个accept操作
     * SocketChannel的选择键SelectionKey一次只能有read和write任意组合操作
     * 注册一个accept SelectionKey 两个read write SelectionKey
     */
    public void run() {
        try {
            //检测是否有其他线程设置了中断标志位
            while (!Thread.interrupted()) {
                //如果立即返回(被wakeup唤醒,close掉,interrupte等)，返回值为0,选择集为原来的选择集
                int count = selector.select();
                //此处count值最大是n，因为只有n个注册的SelectionKey
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    int key = selectionKey.readyOps();
                    String eventName = null;
                    switch (key) {
                        case SelectionKey.OP_ACCEPT:
                            eventName = "ACCEPT";
                            break;
                        case SelectionKey.OP_CONNECT:
                            eventName = "CONNECT";
                            break;
                        case SelectionKey.OP_READ:
                            eventName = "READ";
                            break;
                        case SelectionKey.OP_WRITE:
                            eventName = "WRITE";
                            break;
                        default:
                            eventName = "NONE";
                            break;
                    }
                    System.out.println(new Date() + " selection key readable:  " + eventName + ", SelectionKey: " + selectionKey + " event name: " + eventName);
                    dispatch(selectionKey);
                    iterator.remove();
                }
            }
            System.out.println("reactor thread is interrupted");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
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
    public SelectionKey selectionKey;

    private static final int READING = 1;
    private static final int PROCESSING = 2;
    public static final int SENDING = 3;
    public int state = READING;

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    private ByteBuffer input = ByteBuffer.allocate(1024);
    private ByteBuffer output = ByteBuffer.allocate(1024);

    ReactorHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        selectionKey = socketChannel.register(selector, 0);
        selectionKey.attach(this);
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    void process(String readData) {
        System.out.println(new Date() + " process read data: " + readData);
    }

    private boolean inputIsCompleted() {
        return true;
    }

    private boolean outputIsCompleted() {
        return true;
    }

    private synchronized void read() throws IOException {
        input.clear();
        socketChannel.read(input);
        System.out.println(new Date() + " read method is invoked......" + " input: " + new String(input.array()));
        if (inputIsCompleted()) {
            state = PROCESSING;
            executorService.execute(new Processor());
        }
    }

    /**
     * 发送数据
     *
     * @throws IOException
     */
    private synchronized void send() throws IOException {
        input.flip();
        byte[] temp = new byte[input.limit()];
        input.get(temp);
        output.clear();
        output.put(("i had receive message: [" + new String(temp) + "]; thank u").getBytes());
        output.flip();
        System.out.println(new Date() + " send method is invoked......" + new String(temp));
        socketChannel.write(output);
        if (outputIsCompleted()) {
            socketChannel.close();
        }
    }

    /**
     * 处理并传递
     *
     * @throws IOException
     */
    private synchronized void processAndHandoff() throws IOException {
        process(new String(input.array()));
        try {
            System.out.println("mock process data......");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        state = SENDING;
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        selectionKey.selector().wakeup();
    }

    /**
     * 多线程处理器
     */
    class Processor implements Runnable {

        @Override
        public void run() {
            try {
                processAndHandoff();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}