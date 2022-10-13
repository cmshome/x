package com.lxk.thread.volatileTest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程之volatile关键字
 * 一旦一个共享变量（类的成员变量、类的静态成员变量）被volatile修饰之后，那么就具备了两层语义：
 * 1）保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
 * 2）禁止进行指令重排序。
 *
 * @author lxk on 2018/3/22
 */
public class Volatile_1 {
    public volatile int inc = 0;

    Lock lock = new ReentrantLock();

    /**
     * 3，使用原子操作类：原子操作类是通过CAS循环的方式来保证其原子性的
     */
    //public volatile AtomicInteger inc = 0;

    /**
     * 1，线程不安全的方法
     */
    public void increase() {
        inc++;
    }

    /**
     * 2，使用lock来保证线程安全
     */
    public void increaseLock() {
        lock.lock();
        try {
            inc++;

        } finally {
            lock.unlock();
        }
    }

    /**
     * 测试目的：volatile是不能保证线程安全的。
     * 测试现象：期望值是 1000 x 1000 = 1000000，但是，结果却比这个小，比如 999988。
     * 解决方式：使用2、3 方式解决线程安全问题。
     */
    public static void main(String[] args) {
        final Volatile_1 test = new Volatile_1();
        for (int i = 0; i < 1000; i++) {
            new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        test.increase();
                        System.out.println(test.inc);
                    }
                }
            }.start();
        }

        //保证前面的线程都执行完
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(test.inc);
    }

}
