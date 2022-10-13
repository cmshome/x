package com.lxk.thread.sync;

import java.util.concurrent.TimeUnit;

/**
 * 所有同步在一个对象上的同步块在同时只能被一个线程进入并执行操作。
 * 所有其他等待进入该同步块的线程将被阻塞，直到执行该同步块中的线程退出。
 * 比如：线程1进去方法1的block code了，线程2在访问方法2的时候，也就block了
 *
 * @author LiXuekai on 2021/5/18
 */
public class Two {
    private static volatile Two two;

    private Two() {
    }


    public static void one() {
        if (two == null) {
            synchronized (Two.class) {
                if (two == null) {
                    System.out.println("one sleep");
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("one sleep over");
                    two = new Two();
                    System.out.println("one new instance.");
                } else {
                    System.out.println("two block else.");
                }
            }
        }
        System.out.println("one out.");

    }

    public static void two() {
        if (two == null) {
            System.out.println("two block");
            synchronized (Two.class) {
                if (two == null) {
                    System.out.println("two sleep");
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("two sleep over...");
                    two = new Two();
                    System.out.println("two new instance.");
                } else {
                    System.out.println("two block else.");
                }
            }
        }
        System.out.println("two out.");
    }


}
