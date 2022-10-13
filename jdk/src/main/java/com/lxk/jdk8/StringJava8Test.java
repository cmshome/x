package com.lxk.jdk8;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.StringJoiner;

/**
 * Java8里面字符串相关新增的方法
 *
 * @author lxk on 2018/1/31
 */
public class StringJava8Test {

    /**
     * .chars()方法返回一个stream对象。
     */
    @Test
    public void testChars() {
        final String str = "w00t";
        //打印结果是数字
        str.chars().forEach(System.out::println);
        //输出char类型
        str.chars().mapToObj(ch -> ((char) ch)).forEach(System.out::println);
    }

    /**
     * StringJoiner类
     */
    @Test
    public void testStringJoiner() {
        int length = 5;
        StringJoiner stringJoiner = new StringJoiner(",");
        for (int i = 0; i < length; i++) {
            stringJoiner.add(i + "");
        }
        System.out.println(stringJoiner);
    }

    /**
     * Java 8 String类新方法：join() 以特地符号，拼接字符串。
     */
    @Test
    public void testStringJoin() {
        List<String> friends = Lists.newArrayList("NBA", "hanks", "jim", "jamie", "Bob", "lily", "trump");
        System.out.println(String.join(",", friends));
        System.out.println(String.join("-", "NBA", "hanks", "jim", "jamie", "Bob", "lily", "trump"));
    }

}
