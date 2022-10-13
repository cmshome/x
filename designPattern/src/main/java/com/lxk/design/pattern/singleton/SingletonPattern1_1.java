package com.lxk.design.pattern.singleton;

/**
 * 饿汉式（静态代码块）
 *
 * @author LiXuekai on 2021/4/1
 */
public class SingletonPattern1_1 {
    private static SingletonPattern1_1 singletonPattern1_1;

    {
        singletonPattern1_1 = new SingletonPattern1_1();
    }

    private SingletonPattern1_1() {

    }

    public static SingletonPattern1_1 getSingletonPattern1_1() {
        return singletonPattern1_1;
    }

}
