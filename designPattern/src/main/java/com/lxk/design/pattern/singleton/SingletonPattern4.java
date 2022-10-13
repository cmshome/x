package com.lxk.design.pattern.singleton;

/**
 * 懒汉式(线程安全，同步代码块)
 *
 * 编译器优化，会有重排序的可能，导致bug的情况。
 *
 * @author LiXuekai on 2020/6/10
 */
public class SingletonPattern4 {
    private static SingletonPattern4 singletonInstance;

    private SingletonPattern4() {
    }

    public static SingletonPattern4 getSingletonInstance() {
        if (singletonInstance == null) {
            synchronized (SingletonPattern4.class) {
                singletonInstance = new SingletonPattern4();
            }
        }
        return singletonInstance;
    }
}
