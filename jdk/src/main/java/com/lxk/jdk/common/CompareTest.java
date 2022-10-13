package com.lxk.jdk.common;

import org.junit.Test;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author LiXuekai on 2021/10/28
 */
public class CompareTest {

    @Test
    public void test() {
        System.out.println(Objects.compare("abc", "abcd", String::compareTo));
        System.out.println(Objects.compare(100, 200, Comparator.comparingInt(Number::intValue)));
        System.out.println(Objects.compare(10.0, 20.0, Comparator.comparingDouble(Number::doubleValue)));
        System.out.println(Objects.compare(10L, 20L, Comparator.comparingLong(Number::longValue)));
    }

}
