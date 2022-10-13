package com.lxk.thread.volatileTest;

import org.junit.Test;

/**
 * Java 并发编程之 测试 可见性
 * 一个线程对共享变量的操作对其他线程是不可见的
 *
 * @author LiXuekai on 2021/3/31
 */
public class Volatile_2 {

    /**
     * 测试效果：
     * 1，不打开那行打印的注释，则输出结果就是run里面的一次打印，for循环竟然不打印。
     * 2，打开注释的那行打印代码，会稍微过一会儿，该打印的都打印了。
     */
    @Test
    public void test() {
        MyThread myThread = new MyThread();
        // 开启线程
        myThread.start();
        // 主线程执行
        while (true) {

            //System.out.println("s");//这个打开也会触发，醉了，怎么解释呢？

            if (myThread.isFlag()) {
                System.out.println("主线程访问到 flag 变量");
            }
        }
    }
}

/**
 * 子线程类
 */
class MyThread extends Thread {
    private boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 修改变量值
        flag = true;
        System.out.println("flag = " + flag);
    }

    /**
     * 打开和注释这行代码，对程序运行也有着bug级的影响，没点水平还真解释不了这个极为不常见的现象。
     */
    public boolean isFlag() {
        //System.out.println("调用了。。。" + flag);
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
