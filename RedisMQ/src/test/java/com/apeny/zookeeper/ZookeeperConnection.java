package com.apeny.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by monis on 2018/8/11.
 */
public class ZookeeperConnection {

    public static ZooKeeper zooKeeper(String connectString) {
        final CountDownLatch countdownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(connectString, 60000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    countdownLatch.countDown();
                }
            });
            countdownLatch.await();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }
}
