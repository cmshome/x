package com.lxk.jdk.common;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * 测试final的用法
 * 修饰类 不能被继承
 * 修饰方法 final方法是不能够被子类重写的，可以被重载
 * 修饰参数 无法在方法中更改参数引用所指向的对象
 *
 * @author LiXuekai on 2018/12/6
 */
public class FinalTest {

    /**
     * 引用类型的final，虽然不能修改他的地址，但是可以修改这个变量的内容的啊。
     */
    @Test
    public void refFinal() {
        final List<String> list = Lists.newArrayList("12", "34");
        list.add("ssd");
        int ss = 100;
        System.out.println(ss);
        System.out.println(list);
        System.out.println(ss);
    }

    /**
     * 一般的变量，声明成final之后，是不可以再次赋值的
     * 也就是常说的值传递的参数的时候
     */
    @Test
    public void common() {
        final int ss = 100;
        //ss = 200;
        final String hi = "ghjkl";
        //hi = "sdf";
    }

}
