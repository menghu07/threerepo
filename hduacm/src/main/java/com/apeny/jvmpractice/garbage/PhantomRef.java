package com.apeny.jvmpractice.garbage;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;

/**
 * 虚引用，作用是跟踪对象的回收，可以将资源释放操作与其关联
 * 这个类的对象是可复活的^-^
 * Created by ahu on 2017年10月04日.
 */
public class PhantomRef {
    private static PhantomRef ref;

    public static void main(String[] args) throws InterruptedException {
        phantomRefCollect();
    }

    /**
     * 虚引用必须与引用队列一起使用
     */
    private static void phantomRefCollect() throws InterruptedException {
        ReferenceQueue<PhantomRef> queue = new ReferenceQueue<>();
        PhantomReference<PhantomRef> reference = new PhantomReference<>(new PhantomRef(), queue);
        System.out.println("不会抛异常，但是就是引用不到，" + reference.get());
        CheckPhantomRef checkPhantomRef = new CheckPhantomRef(queue);
        checkPhantomRef.start();
        ref = null;
        System.gc();
        TimeUnit.NANOSECONDS.sleep(10);
        if (ref != null) {
            System.out.println("第一次回收没成功");
        } else {
            System.out.println("第一次回收成功");
        }
        TimeUnit.NANOSECONDS.sleep(10);
        ref = null;
        System.gc();
    }

    static class CheckPhantomRef extends Thread {
        private ReferenceQueue<PhantomRef> queue;

        CheckPhantomRef(ReferenceQueue<PhantomRef> queue) {
            this.queue = queue;
        }

        public void run() {
            if (queue != null) {
                try {
                    //这里会等待。。。。
                    Reference<PhantomRef> refReference = (Reference<PhantomRef>) queue.remove();
                    if (refReference != null) {
                        System.out.println("已经被回收，#############" + refReference);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize is called");
        ref = this;
    }
}
