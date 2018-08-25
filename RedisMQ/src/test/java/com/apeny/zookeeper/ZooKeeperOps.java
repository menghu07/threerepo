package com.apeny.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.Watcher.Event.EventType.NodeChildrenChanged;
import static org.apache.zookeeper.Watcher.Event.EventType.NodeDataChanged;
import static org.apache.zookeeper.Watcher.Event.EventType.None;

/**
 * Created by monis on 2018/8/11.
 */
public class ZooKeeperOps {
    private final static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        create("/hox222", "configs".getBytes());
        exists("/hox222");
//        get("/hox222");
        getChildren("/hox222/hile2");
        setData("/hox222");
        setDelete("/hox222/hile3");

    }

    private static void create(String path, byte[] data) {
        ZooKeeper zooKeeper = ZookeeperConnection.zooKeeper("192.168.0.128:2181");
        try {
            zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    private static void exists(String path) {
        ZooKeeper zooKeeper = ZookeeperConnection.zooKeeper("192.168.0.128:2181");
        try {
            Stat stat = zooKeeper.exists(path, true);
            System.out.println("value={Czxid:" + Long.toHexString(stat.getCzxid()) + "\n,ctime:" + new Date(stat.getCtime()) + ",\nmZxid:" + Long.toHexString(stat.getMzxid())
                    + ",\nmtime:" + new Date(stat.getMtime()) + ",\npZxid:" + Long.toHexString(stat.getPzxid()) + ",\ncversion:" + stat.getCversion() + ",\nversion:" + stat.getVersion()
                    + ",\naversion:" + stat.getAversion() + ",\nephemeralOwner:" + stat.getEphemeralOwner() + ",\ndataLength:" + stat.getDataLength()
                    + ",\nnumLength:" + stat.getNumChildren() + "}");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    private static void get(String path) {
        ZooKeeper zooKeeper = ZookeeperConnection.zooKeeper("192.168.0.128:2181");
        try {
            Stat stat = zooKeeper.exists(path, true);
            if (stat != null) {
                byte[] b = zooKeeper.getData(path, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        if (event.getType() == None) {
                            switch (event.getState()) {
                                case Expired:
                                    countDownLatch.countDown();
                                    System.out.println("connection expired......");
                            }
                        } else if (event.getType() == NodeDataChanged) {
                            try {
                                byte[] changeData = zooKeeper.getData(path, false, null);
                                System.out.println("i watch path: " + path + ", new data: " + new String(changeData));
                                countDownLatch.countDown();
                            } catch (KeeperException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, null);
                System.out.println("getData: " + new String(b));
            } else {
                System.out.println("znode not existed.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    private static void getChildren(String path)  {
        ZooKeeper zooKeeper = ZookeeperConnection.zooKeeper("192.168.0.128:2181");
        try {
            Stat stat = zooKeeper.exists(path, true);
            if (stat != null) {
                List<String> children = zooKeeper.getChildren(path, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        if (event.getType() == None) {
                            switch (event.getState()) {
                                case Expired:
                                    countDownLatch.countDown();
                                    System.out.println("connection expired......");
                            }
                        } else if (event.getType() == NodeChildrenChanged) {
                            try {
                                List<String> newChildren = zooKeeper.getChildren(path, false);
                                System.out.println("i watch path: " + path + ", new children: " + newChildren);
                                countDownLatch.countDown();
                            } catch (KeeperException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                System.out.println("getChildren: " + children);
                countDownLatch.await();
            } else {
                System.out.println("znode not existed.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public static void setData(String path) {
        ZooKeeper zooKeeper = ZookeeperConnection.zooKeeper("192.168.0.128:2181");
        try {
            Stat stat = zooKeeper.exists(path, true);
            if (stat != null) {
                System.out.println("old stat" + printStat(stat));
                Stat newStat = zooKeeper.setData(path, "new hile2".getBytes(), stat.getVersion());
                System.out.println("new stat " + printStat(newStat));
            } else {
                System.out.println("znode not existed.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public static void setDelete(String path) {
        ZooKeeper zooKeeper = ZookeeperConnection.zooKeeper("192.168.0.128:2181");
        try {
            Stat stat = zooKeeper.exists(path, true);
            if (stat != null) {
                System.out.println("old stat" + printStat(stat));
                zooKeeper.delete(path, stat.getVersion());
            } else {
                System.out.println("znode not existed.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
    private static String printStat(Stat stat) {
        return ("value={Czxid:" + Long.toHexString(stat.getCzxid()) + "\n,ctime:" + new Date(stat.getCtime()) + ",\nmZxid:" + Long.toHexString(stat.getMzxid())
                + ",\nmtime:" + new Date(stat.getMtime()) + ",\npZxid:" + Long.toHexString(stat.getPzxid()) + ",\ncversion:" + stat.getCversion() + ",\nversion:" + stat.getVersion()
                + ",\naversion:" + stat.getAversion() + ",\nephemeralOwner:" + stat.getEphemeralOwner() + ",\ndataLength:" + stat.getDataLength()
                + ",\nnumLength:" + stat.getNumChildren() + "}");
    }
}
