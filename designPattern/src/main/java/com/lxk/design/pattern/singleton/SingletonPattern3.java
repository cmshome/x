package com.lxk.design.pattern.singleton;

/**
 * 懒汉式(线程安全，同步方法)
 *
 * 优点：第一次调用才初始化，避免内存浪费。
 * 缺点：必须加锁 synchronized 才能保证单例，但加锁每次访问会影响效率。
 *
 * @author LiXuekai on 2020/6/10
 */
public class SingletonPattern3 {
    private static SingletonPattern3 singletonInstance;

    private SingletonPattern3() {
    }

    public static synchronized SingletonPattern3 getSingletonInstance() {
        if (singletonInstance == null) {
            singletonInstance = new SingletonPattern3();
        }
        return singletonInstance;
    }
}
