package com.lxk.jdk.common.string;

import org.junit.Test;

/**
 * 字符串常量池的测试
 *
 * @author LiXuekai on 2021/4/2
 */
public class StringPoolTest {
    private static final String HEL = "Hel";
    private static final String LO = "lo";
    private static final String HEL_;
    private static final String LO_;

    static {
        HEL_ = "Hel";
        LO_ = "lo";
    }


    /**
     * 测试经典的字符串常量池问题。
     */
    @Test
    public void testAddress() {
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = "Hel" + "lo";
        String s4 = "Hel" + new String("lo");
        String s5 = new String("Hello");
        String s6 = s5.intern();
        String s7 = "H";
        String s8 = "ello";
        String s9 = s7 + s8;
        String s10 = HEL + LO;
        String s11 = HEL_ + LO_;

        // true 都是字面量，编译期间就可以确定值
        System.out.println(s1 == s2);

        // true 同理，字面量，编译期间可以确定值
        System.out.println(s1 == s3);

        // false 但new String("lo")这部分不是已知字面量，是一个不可预料的部分，编译器不会优化，必须等到运行时才可以确定结果，
        System.out.println(s1 == s4);

        // true  ，intern方法会尝试将Hello字符串添加到常量池中，并返回其在常量池中的地址
        System.out.println(s1 == s6);

        // false  虽然s7、s8在赋值的时候使用的字符串字面量，但是拼接成s9的时候，s7、s8作为两个变量，都是不可预料的
        System.out.println(s1 == s9);

        // false 都是new出来的，肯定不会相等的
        System.out.println(s4 == s5);

        // true s10是虽然是加出来的，但是a0和a1都是static final 编译期间，就已经知道啦。所以，s10，也是常量。
        System.out.println(s1 == s10);

        // false s11也是加出来的，但是后面的2个是通过静态代码块赋值的，静态代码块，只有在类加载的时候，才执行。所以，后面的2个是不确定值。
        System.out.println(s1 == s11);
    }

    /**
     * intern方法测试，将字符串加入到常量池 native 方法。
     * s1 s2 声明完之后，就在常量池啦，因为这个常量声明方法。
     * 再intern方法，他已经存在啦，此方法就把池里面的返回啦。
     * 所以，比较地址，就是自己跟自己比，就相等啦。
     */
    @Test
    public void testStringIntern() {
        String s1 = "go" + "od";
        String s2 = "ja" + "va";
        //true
        System.out.println(s1.intern() == s1);
        //true
        System.out.println(s2.intern() == s2);
    }

    /**
     * str3.intern() 如果字符串常量中没有"helloworld"这个字符串，则将这个字符串对象存入字符串常量池中，然后返回这个字符串对象。
     * 如果有,则直接返回字符串常量池中的对应的那个对象的引用。
     */
    @Test
    public void test() {
        String str1 = new StringBuilder("ja").append("va").toString();
        String str2 = str1.intern();
        // 结果是false
        System.out.println(str1 == str2);

        String str3 = new StringBuilder("hello").append("world").toString();
        String str4 = str3.intern();
        // 结果是true
        System.out.println(str3 == str4);
    }

    /**
     * 还是测试字符串 ==
     */
    @Test
    public void testStringPool2() {
        String s0 = "ab";
        String s1 = "a";
        String s3 = "a" + "b";
        String s2 = s1 + "b";
        System.out.println(s0 == s3);
        System.out.println(s2 == s3);
    }

    /**
     * 测试字符串常量池的问题
     */
    @Test
    public void testStringPool() {
        String a = "abc";//字面量形式
        String b = "abc";//字面量形式
        String c = new String("abc");//使用new标准的构造对象
        /*
            注意：这个虽然看起来似乎要在常量池新建三个字符串对象：ab，c，和拼接生成的abc
            但是结果是内存中仅有生成的，前面的两个算是过程变量。这反编译得出来的结论，我没测试哟！
            这样做实际上是一种优化，避免了创建多余的字符串对象，也没有发生字符串拼接问题
         */
        String d = "ab" + "c";//字面量形式
        System.out.println("a == b " + (a == b));//true
        System.out.println("a == c " + (a == c));//false
        System.out.println("a == d " + (a == d));//true
        System.out.println("b == c " + (b == c));//false
        System.out.println("b == d " + (b == d));//true
        System.out.println("c == d " + (c == d));//false
        System.out.println("-----------------");
        System.out.println("abc" == ("ab" + "c"));//true
        System.out.println("-----------------");
        String e = c.intern();//将new出来的字符串对象加入字符串常量池
        System.out.println(a == e);//true
        /*
            Java中字符串对象创建有两种形式。
            一种为字面量形式，如String str = "droid";，
            另一种就是使用new这种标准的构造对象的方法，如String str = new String("droid");
            这两种方式我们在代码编写时都经常使用，尤其是字面量的方式。然而这两种实现其实存在着一些性能和内存占用的差别。
            这一切都是源于JVM为了减少字符串对象的重复创建，其维护了一个特殊的内存，这段内存被成为字符串常量池或者字符串字面量池。
            工作原理
            当代码中出现字面量形式创建字符串对象时，JVM首先会对这个字面量进行检查。
            如果字符串常量池中存在相同内容的字符串对象的引用，则将这个引用返回。
            否则新的字符串对象被创建，然后将这个引用放入字符串常量池，并返回该引用。
         */
        System.out.println("-----------new test -----");
        String aa = "hello2";
        final String bb = "hello";
        String dd = "hello";
        String cc = bb + 2;
        String ee = dd + 2;
        String ff = "hello" + 2;
        System.out.println("------aa 和 cc ee ff 比较--------");
        System.out.println("aa == cc " + (aa == cc));//true,因为bb是final类型(这个会因为bb是否是final而结果不同，)
        System.out.println("aa == ee " + (aa == ee));//false
        System.out.println("aa == ff " + (aa == ff));//true,因为ff是直接由字面量形式创建出来的，不经过中间变量。
        //ff为字符串直接拼出来的，不经过中间变量。
        System.out.println("------ff 和 aa cc ee 比较--------");
        System.out.println("ff == aa " + (ff == aa));//true,因为ff是直接由字面量形式创建出来的，不经过中间变量。
        System.out.println("ff == cc " + (ff == cc));//true,因为bb是final类型(这个会因为bb是否是final而结果不同，)
        System.out.println("ff == ee " + (ff == ee));//false
    }
}
