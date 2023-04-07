package com.lxk.jdk.common.number;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 测试 Integer 常量池问题。
 *
 * @author lxk on 2017/2/23
 */
public class IntegerTest {


    /**
     * int类型的时间戳最大只能表示到下面这个时间点
     * int max 的值当秒，转成时间只能到
     * 2147483647  ->  2038-01-19 11:14:07
     */
    @Test
    public void max() {
        System.out.println(Integer.MAX_VALUE);
    }

    /**
     * 测试一个int数字一直加，会变成负数
     */
    @Test
    public void testMax() {
        int start = 1;
        while (start > 0) {
            start++;
        }
        System.out.println(start);
    }

    /**
     * 考虑最后的那个邀请码的情况
     * 快来领支付宝红包！人人可领，天天可领！复制此消息，打开最新版支付宝就能领取！
     * 04QOHe67OA
     */
    @Test
    public void testRandom() {
        //一位上的数的情况：字母大小写，外加十个数字。
        System.out.println(26 * 2 + 10);
        BigDecimal all = new BigDecimal(Math.pow(62, 10) + "");
        String big = all.toPlainString();
        System.out.println(big);
        System.out.println(big.length());
        System.out.println(UUID.randomUUID());
    }


    /**
     * 测试 Integer 常量池问题，
     */
    @Test
    public void testIntegerCache() {
        Integer MaxCacheA = 127;
        Integer MaxCacheB = 127;
        Integer minCacheA = -128;
        Integer minCacheB = -128;
        Integer noCacheA = 128;
        Integer noCacheB = 128;
        System.out.println(MaxCacheA == MaxCacheB);
        System.out.println(minCacheA == minCacheB);
        System.out.println(noCacheA == noCacheB);
        System.out.println(noCacheA.equals(noCacheB));

    }

    /**
     * 测试 Integer的缓存 IntegerCache.cache
     */
    @Test
    public void testIntAndIntegerCache() {
        System.out.println("---int---");
        int a = 127, b = 127;
        System.out.println(a == b);         //true
        a = 128;
        b = 128;
        System.out.println(a == b);         //true

        System.out.println("---Integer---");
        Integer aa = 127, bb = 127;
        System.out.println(aa == bb);       //true
        aa = 128;
        bb = 128;
        System.out.println(aa == bb);       //false
        System.out.println(aa.equals(bb));  //true
    }

    @Test
    public void valueIsNumber() {
        try {
            Integer parseInt = Integer.parseInt("100");
            System.out.println(parseInt);
        } catch (Exception e) {
        }
    }


    /**
     * new 出来的对象==比较的就是地址了，要是默认的那种，就会有可能走自带的缓存
     */
    @Test
    public void a() {
        Integer a = new Integer(100);
        Integer b = new Integer(100);
        // false
        System.out.println(a == b);

        Integer x = 127;
        Integer y = 127;
        // true
        System.out.println(x == y);

        Integer m = 128;
        Integer n = 128;
        // false
        System.out.println(m == n);
    }

}
