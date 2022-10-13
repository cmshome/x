package com.lxk.thread.mianShiTest.one;


import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 子线程循环 10 次，接着主线程循环 100 次，接着又回到子线程循环 10 次，
 * 接着再回到主线程又循环 100 次，如此循环50次，试写出代码。
 * <p>
 * @author lxk on 2017/7/14
 */
public class Main {

    @Test
    public void test() throws InterruptedException {
        int i = 0;
        Object o = new Object();
        new Thread(new Child(o)).start();
        while (i < 100) {
            synchronized (o) {
                try {
                    o.wait();
                } catch (InterruptedException ignore) {
                }
                System.out.println("第" + (i + 1) + "...主...100");
                i++;
                o.notify();
            }
        }
        System.out.println("结束");

        TimeUnit.MINUTES.sleep(5);
    }

}

class Child implements Runnable {
    private final Object object;

    public Child(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 10) {
            synchronized (object) {
                System.out.println("第" + (i + 1) + "...子50");
                i++;
                object.notify();
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
