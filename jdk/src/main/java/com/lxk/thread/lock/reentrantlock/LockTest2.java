package com.lxk.thread.lock.reentrantlock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁 可重入 的例子
 *
 * @author LiXuekai on 2021/4/28
 */
public class LockTest2 {
    /**
     * 默认构造的是不公平的锁 FairSync/NonfairSync
     * 锁都对应一个等待队列，如果一个线程没有获得锁，就会进入等待队列，当有线程释放锁的时候，就需要从等待队列中唤醒一个等待的线程。
     * 若是公平锁，唤醒策略就是谁等待的时间长，就唤醒谁，这很公平
     * 若是非公平锁，则不提供这个公平保证，所以可能等待时间短的线程被先唤醒。
     * 非公平锁的场景应该是线程释放锁之后，如果来了一个线程获取锁，他不必去排队直接获取到，不会入队。获取不到才入队。
     */
    private final Lock reentrantLock = new ReentrantLock(true);

    int value;


    public int get() {
        // 2，获取锁
        // 此时，若锁可重入，则t1 可再次加锁
        //      若不可重入，则t1 此时会被阻塞
        reentrantLock.lock();
        try {
            return value;
        } finally {
            // 保证锁的释放
            reentrantLock.unlock();
        }
    }


    public void add() {
        // t1 获取锁
        reentrantLock.lock();
        try {
            // 1，线程t1 已经获取锁了，
            value = get() + 1;
        } finally {
            // 保证锁的释放
            reentrantLock.unlock();
        }
    }


    @Test
    public void testReentrant() {
        LockTest2 lockTest2 = new LockTest2();
        lockTest2.add();
        int i = lockTest2.get();
        System.out.println(i);
    }

}
