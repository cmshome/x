package com.lxk.design.pattern.singleton;

import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * 单例模式
 * 意图：保证一个类仅有一个实例，并提供一个访问它的全局访问点。
 * 主要解决：一个全局使用的类频繁地创建与销毁。
 * 何时使用：当想控制实例数目，节省系统资源的时候。
 * 如何解决：判断系统是否已经有这个单例，如果有则返回，如果没有则创建。
 * 关键代码：构造函数是私有的。
 * <p>
 * 1,饿汉式(静态常量)
 * 2,饿汉式（静态代码块）
 * 3,懒汉式(线程不安全)
 * 4,懒汉式(线程安全，同步方法)
 * 5,懒汉式(线程安全，同步代码块)
 * 6,双重检查
 * 7,静态内部类
 * 8,枚举
 *
 * @author lxk on 2017/3/23
 */
public class Main {


    /**
     * 全部放在一起 1-8
     */
    @Test
    public void allTest() {
        // 对，但不好。类加载就会创建这个单例。❌
        SingletonPattern1 singletonPattern1 = SingletonPattern1.getSingletonInstance();

        SingletonPattern1_1 singletonPattern1_1 = SingletonPattern1_1.getSingletonPattern1_1();

        // 错，未考虑多线程的情况。
        SingletonPattern2 singletonPattern2 = SingletonPattern2.getSingletonInstance();

        // 对，但不好。同步方法只能互斥访问。
        SingletonPattern3 singletonPattern3 = SingletonPattern3.getSingletonInstance();

        // 错。虽然考虑多线程，且有双重旋锁来保证同步，但是jvm可能会重排序，导致异常。
        SingletonPattern4 singletonPattern4 = SingletonPattern4.getSingletonInstance();


        // 双旋锁 且 volatile
        SingletonPattern5 singletonPattern5 = SingletonPattern5.getSingletonInstance();

        // 类加载机制保证单例
        SingletonPattern6 singletonPattern6 = SingletonPattern6.getSingletonInstance();

        // 对于枚举，JVM 会自动进行实例的创建，反射拿不到构造函数的。
        SingletonPattern7 singletonPattern7 = SingletonPattern7.getSingletonInstance();
    }

    /**
     * 测试内部类方式实现单例的情况下，外边的类被加载了之后，内部类会加载吗？
     */
    @Test
    public void test() {
        SingletonPattern6.test();
        System.out.println("调用获取单例的方法开始。");
        SingletonPattern6 singletonPattern6 = SingletonPattern6.getSingletonInstance();
        System.out.println("调用获取单例的方法完成。");

    }


    /**
     * 通过反射来再次初始化单例，使得单例不再单例。
     */
    @Test
    public void specialWay() throws Exception {
        SingletonPattern5 singletonPattern5 = SingletonPattern5.getSingletonInstance();
        Constructor<SingletonPattern5> constructor = SingletonPattern5.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SingletonPattern5 reflect3 = constructor.newInstance();
        // false
        System.out.println(singletonPattern5 == reflect3);
    }

}
