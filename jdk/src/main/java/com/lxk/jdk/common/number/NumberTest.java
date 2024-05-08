package com.lxk.jdk.common.number;

import com.google.common.collect.Maps;
import com.lxk.tool.util.StackTraceCollectUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
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
        BigDecimal b2 = new BigDecimal("1.11");
        ;
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


    @Test
    public void getNumber() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("a", 100);
        map.put("b", 200L);
        map.put("c", null);
        map.put("d", null);

        Long a = getLong(map, "c");
        System.out.println(a);

    }


    public Long getLong(Map<String, Object> map, String key) {
        try {
            Object o = map.get(key);
            if (o instanceof Long) {
                return (Long) o;
            }
            return Long.parseLong(o.toString());
        } catch (Exception ignored) {
            return 0L;
        }
    }

    public Integer getInteger_(Map<String, Object> map, String key) {
        try {
            Object o = map.get(key);
            return Integer.parseInt(o.toString());
        } catch (Exception ignored) {
            return 0;
        }
    }

    public Integer getInteger(Map<String, Object> map, String key) {
        try {
            Object o = map.get(key);
            if (o instanceof Integer) {
                return (Integer) o;
            }
            return Integer.parseInt(o.toString());
        } catch (Exception ignored) {
            return 0;
        }
    }

    /**
     * 在做类型转换的时候，先判断一下类型，而不是直接toString再parse
     */
    @Test
    public void cost() {
        String key = "a";
        Map<String, Object> map = Maps.newHashMap();
        map.put(key, 100);
        while (true) {
            getInteger(map, key);
            getInteger_(map, key);
        }
    }


    @Test
    public void cast() {
        Long a = 100L;
        if (a instanceof Long){
            double a1 = a;
            System.out.println(a1);
        }

        double s = (double) a;
        System.out.println(s);


        // 这种000开头的字符串也能正常解析成数字
        String ad="0000400";
        double v = Double.parseDouble(ad);
        System.out.println(v);
    }


    @Test
    public void sd() {
        int x=20000;
        float y=20000.0F;
        System.out.println(x == y);

    }


}
