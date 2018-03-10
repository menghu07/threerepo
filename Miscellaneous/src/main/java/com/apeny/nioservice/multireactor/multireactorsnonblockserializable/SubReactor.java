package com.apeny.nioservice.multireactor.multireactorsnonblockserializable;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * 子反应器，只处理read,write事件，使用线程池处理读写操作
 * Created by apeny on 2018/3/4.
 */
public class SubReactor implements Runnable {

    private Selector selector;
    private volatile boolean locked;

    SubReactor(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                //这一步select必须要调用，只有这样才会更新selectedKeys()返回的Set
                if (!locked && (selector.select() > 0)) {
                    //调用IO处理者
                    Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        dispatch(selectionKey);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取和写入仍然用当前线程，数据处理用另外一个线程
     *
     * @param key
     */
    private void dispatch(SelectionKey key) {
        Runnable runnable = (Runnable) key.attachment();
        if (runnable != null) {
            runnable.run();
        }
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
