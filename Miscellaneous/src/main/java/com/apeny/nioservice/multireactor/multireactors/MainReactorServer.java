package com.apeny.nioservice.multireactor.multireactors;

import com.sun.org.apache.bcel.internal.generic.Select;

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
 * 功能：接受客户端连接，只有一个ServerSocketChannel SelectionKey Acceptor,把任务分配给SubReactor
 * 只处理accept事件
 */
public class MainReactorServer {

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    private static final Selector[] SELECTS = new Selector[3];
    private static final SubReactor[] SUB_REACTORS = new SubReactor[3];
    private static int selectIndex = 0;
    static {
        for (int i = 0; i < SELECTS.length; i++) {
            try {
                SELECTS[i] = Selector.open();
                SUB_REACTORS[selectIndex++] = new SubReactor(SELECTS[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        selectIndex = 0;
    }

    public static void main(String[] args) {
        startReactor();
    }

    private static void startReactor() {
        try {
            new MainReactorServer(9892).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MainReactorServer(int port) throws IOException {
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

    /**
     * 请求派发给acceptor
     * @param selectionKey
     */
    private void dispatch(SelectionKey selectionKey) {
        Runnable runnable = (Runnable) selectionKey.attachment();
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
                SocketChannel socketChannel = serverSocketChannel.accept();
                //因为是非阻塞的，要判断是否为空
                if (socketChannel != null) {
                    Selector selector = SELECTS[selectIndex];
                    SubReactor subReactor = SUB_REACTORS[selectIndex];
                    SystemEnvironment.SUBREACTOR_POOL.execute(subReactor);
                    socketChannel.configureBlocking(false);
                    subReactor.setLocked(true);
                    selector.wakeup();
                    SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    selectionKey.attach(new SubReactorHandler(selectionKey, socketChannel));
                    subReactor.setLocked(false);
                    selector.wakeup();
                    System.out.println("one client access: " + socketChannel);
                    selectIndex++;
                    if (selectIndex == SELECTS.length) {
                        selectIndex = 0;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}