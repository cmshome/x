package com.lxk.bean.model;

import java.util.Random;

/**
 * @author LiXuekai on 2021/10/12
 */
public class FinalDemo {

    //编译期常量
    final int i = 1;
    final static int J = 1;
    final int[] x = {1,2,3,4};
    //非编译期常量
    Random r = new Random();
    final int k = r.nextInt();


    // final方法是可以被重载的
    public final void test() {
    }

    public final void test(String str) {
    }




    //final static int y; //由static和final修饰，仅能在定义处赋值，因为该字段不属于对象，属于这个类

    private int a;  //普通域

    //Java允许生成空白final  /在定义处进行赋值(这不叫空白final)  /在构造器中进行赋值，保证了该值在被使用前赋值。
    private final int b; //final域

    private static FinalDemo finalDemo;

    public FinalDemo() {
        a = 1; // 1. 写普通域
        b = 2; // 2. 写final域
    }

    public static void writer() {
        finalDemo = new FinalDemo();
    }

    public static void reader() {
        FinalDemo demo = finalDemo; // 3.读对象引用
        int a = demo.a;    //4.读普通域
        int b = demo.b;    //5.读final域
    }


}
