package com.lxk.thread.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock经典案例就是try/finally，必须在finally块里释放锁。
 *
 * @author LiXuekai on 2021/4/28
 */
public class LockTest {
    /**
     * 默认构造的是不公平的锁 FairSync/NonfairSync
     */
    private final ReentrantLock reentrantLock = new ReentrantLock(true);
    int value;

    public void add() {

        // 获取锁
        reentrantLock.lock();
        try {
            // 若线程 t1 执行 value += 1，那后续线程 t2 能看到value的正确值吗？
            // 必须可以。
            value += 1;
        } finally {
            reentrantLock.unlock();
        }
    }

    /*

        Lock靠什么保证可见性呢？
        它是利用了volatile的Happens-Before规则。
        因为 ReentrantLock 的内部类继承了 AQS，其内部维护了一个volatile 变量state.

        所以，执行value+=1前，程序先 .lock() 读写一次volatile state，在执行value+=1后，.unlock() 又读写一次volatile state。
        根据Happens-Before的如下规则判定：
        1,顺序性规则
            线程t1的value+=1 Happens-Before 线程t1的unlock()
        2,volatile变量规则
            由于此时 state为1，会先读取state，所以线程t1的unlock() Happens-Before 线程t2的lock()
        3,传递性规则
            线程t的value+=1 Happens-Before 线程t2的lock()

     */

    /**
     * 获取锁时，会读写state
     * 解锁时，  也会读写state
     */
    class SimpleLock {
        volatile int state;

        /**
         * 获取锁时，会读写state
         */
        void lock() {
            // ...
            state = 1;
        }

        /**
         * 解锁时，  也会读写state
         */
        void unlock() {
            // ...
            state = 0;
        }
    }

}
