package com.lxk.jvm.gc;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 说是jdk1.8之后，没有了方法区，永久代最终被移除，方法区移至Metaspace，字符串常量移至Java Heap。
 * -XX:PermSize=10m -XX:MaxPermSize=10m   （jdk 1.7 的时候使用，1.8 无了）
 * -XX:+PrintGCDetails -Xms30M -Xmx30M -Xmn10M -XX:SurvivorRatio=8 -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:CompressedClassSpaceSize=5M
 *
 * @author LiXuekai on 2020/6/9
 */
public class StringConstantPoolTest {


    /**
     * 字符串常量池还是在堆里面
     */
    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        while (true) {
            //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
            //at java.util.Arrays.copyOf(Arrays.java:3210)
            //at java.util.Arrays.copyOf(Arrays.java:3181)
            //at java.util.ArrayList.grow(ArrayList.java:265)
            //at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:239)
            //at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:231)
            //at java.util.ArrayList.add(ArrayList.java:462)
            //at com.lxk.jdk.jvm.gc.StringConstantPoolTest.main(StringConstantPoolTest.java:23)

            list.add(String.valueOf(System.currentTimeMillis()).intern());


            //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
            //at java.lang.Long.toString(Long.java:399)
            //at java.lang.String.valueOf(String.java:3113)
            //at com.lxk.jdk.jvm.gc.StringConstantPoolTest.main(StringConstantPoolTest.java:24)

            //list.add(String.valueOf(System.currentTimeMillis()));
        }
    }
}
