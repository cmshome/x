package com.lxk.jdk.common.number;

import com.lxk.tool.util.StackTraceCollectUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

/**
 * 所有的包装类（Integer、Long、Byte、Double、Float、Short）都是抽象类 Number 的子类。
 *
 * @author LiXuekai on 2021/10/29
 */
public class NumberTest {

    @Test
    public void test() {

        Number a = 1.3E3;
        Number b = 100;
        int compare = Objects.compare(a, b, Comparator.comparingInt(Number::intValue));

        System.out.println(compare);


        BigDecimal b1 = new BigDecimal("1.11");
        BigDecimal b2 = new BigDecimal("1.11");;
        double v = b1.subtract(b2).doubleValue();
        System.out.println(v >= 0);

    }

    @Test
    public void of() {
        try {
            BigDecimal b1 = new BigDecimal("1.1.1.1");
        } catch (Exception e) {
            String s = StackTraceCollectUtil.collectStackTrace(e);
            System.out.println(s);
        }
    }
}
