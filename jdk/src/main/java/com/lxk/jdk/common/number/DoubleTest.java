package com.lxk.jdk.common.number;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lxk.tool.util.DoubleUtil;
import com.lxk.tool.util.PrintUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/**
 * float和double只能用来做科学计算或者是工程计算.
 * 在商业计算中我们要用 java.math.BigDecimal
 *
 * @author lxk on 2017/9/27
 */
public class DoubleTest {

    /**
     * 超过16位，就会丢失
     * 1.234567898765432E16
     */
    @Test
    public void cast() {
        long l = 12345678987654321L;
        System.out.println(Double.valueOf(l));
        System.out.println(Double.valueOf(l + ""));
        System.out.println(new BigDecimal(l + "").doubleValue());
    }

    /**
     * double 2 long
     */
    @Test
    public void double2Long() {
        Double dd = 123.2D;
        long longValue = dd.longValue();
        System.out.println(longValue);

        int a = 1;

        Double value = Integer.valueOf(a).doubleValue();
        System.out.println(value);
    }

    @Test
    public void test() {
        Double add = DoubleUtil.add(1.11, 2.22);
        System.out.println(add);
        double sub = DoubleUtil.sub(1.11, 2.22);
        System.out.println(sub);
        //会自动转科学计数法：1.2345678998765123E13
        double bigData = 12345678998765.123D;
        System.out.println(bigData);
        long bigLong = 123456789987654L;
        System.out.println(bigLong);
    }

    /**
     * 科学计数法
     * double & float toString的时候，会被转成科学计数法。
     */
    @Test
    public void get() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("a", 387721472);
        map.put("b", 387721472D);
        map.put("c", 387721472131322L);
        map.put("d", 387721472131322f);
        System.out.println(map.get("a"));
        System.out.println(map.get("b"));
        System.out.println(map.get("c"));
        System.out.println(map.get("d"));

        System.out.println(-12345678);
        System.out.println(12345678);
        System.out.println(-12345678.0);
        System.out.println(12345678.0);
        //整数位为0,当小数位以0开始连续出现大于等于3时开始以科学计数法显示
        System.out.println(0.0001);
        System.out.println(-0.0001);
    }

    @Test
    public void getTotalAmount() {
        Double inData = 2.22D;
        Double outData = 1.11D;
        Double configData = 3.33D;
        Double sub = DoubleUtil.sub(inData, outData);
        Double add = DoubleUtil.add(sub, configData);
        System.out.println(add);
    }


    /**
     *
     */
    @Test
    public void infinity() {
        System.out.println(10/0D);
        System.out.println(10/0f);
        System.out.println(10/0);
    }

    /**
     * 测试值为 infinity（无穷 ∞ ） 的情况
     */
    @Test
    public void testInfinity() {
        Double ss = 1.0D / 0.0D;
        System.out.println(Double.isInfinite(ss));
        System.out.println(Double.isFinite(ss));

        System.out.println("-----");


        System.out.println(ss);
        System.out.println(ss.doubleValue());

        Double yy = 0.0D / 0.0D;
        System.out.println(yy);
        //0.0d / 0.0
        System.out.println(Double.NaN);
        //-1.0 / 0.0
        System.out.println(Double.NEGATIVE_INFINITY);
        //1.0 / 0.0
        System.out.println(Double.POSITIVE_INFINITY);

        if (ss.isInfinite()) {
            return;
        }

        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setGroupingUsed(false);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        formatter.setMaximumFractionDigits(2);
        BigDecimal bigDecimal = new BigDecimal(ss + "");

        System.out.println(formatter.format(bigDecimal));

    }

    @Test
    public void testAdd() {
        Number number = 12.1233;
        System.out.println(number.longValue());
        System.out.println(number.intValue());
        Double d1 = 1D;
        Double d2 = 1.22D;
        System.out.println(d1 + d2);
        System.out.println(DoubleUtil.add(d1, d2));
        String count = "100";
        Long totalCount = 200L;
        Double label = Double.parseDouble(count) / totalCount;
        System.out.println(label);
        System.out.println(Lists.newArrayList("ssssss".split(",")));

        //空集合，直接get 0 会 数组下标越界
        //List<String> a = Lists.newArrayList();
        //a.get(0);
    }


    /**
     * 测试结果不是很理想啊。
     */
    @Test
    public void testCompare() {
        Double d = 100D;
        //这个时候，运行结果，都是0，
        //Double d2 = 100.00000000000000001D;
        //这个时候，就能对比出来谁大谁小啦。
        Double d2 = 100.0001D;

        System.out.println(0.1D > 0);
        // 0
        System.out.println(d.compareTo(d2));
        System.out.println(d2.compareTo(d));
        BigDecimal b1 = new BigDecimal(d + "");
        BigDecimal b2 = new BigDecimal(d2 + "");
        // 0
        System.out.println(b1.compareTo(b2));
        // 0
        System.out.println(b2.compareTo(b1));
        System.out.println();
    }


    /**
     * BigDecimal的测试，要精确。
     * 还要使得科学计数法的数字，做完全的展示。
     */
    @Test
    public void testBigDecimal() {
        Double d = 1.6D;
        //不准确的初始化
        BigDecimal bigDecimal = new BigDecimal(d);
        System.out.println(bigDecimal);

        //使得结果精确的初始化姿势
        bigDecimal = new BigDecimal(d.toString());
        System.out.println(bigDecimal);

        bigDecimal = new BigDecimal("6.214822313132341212666E+18");
        System.out.println(bigDecimal.toPlainString());
    }

    /**
     * double的一些计算奇葩现象，试验一把，就印象深刻啦。
     */
    @Test
    public void testDouble() {
        Double d = 0.81d;
        System.out.println(d);
        PrintUtil.divideLine();
        //0.060000000000000005
        System.out.println("0.05 + 0.01 = " + (0.05 + 0.01));
        //0.5800000000000001
        System.out.println("1.0 - 0.42 = " + (1.0 - 0.42));
        //401.49999999999994
        System.out.println("4.015 * 100 = " + (4.015 * 100));
        //1.2329999999999999
        System.out.println("123.3 / 100 = " + (123.3 / 100));
        //4.03 四舍五入
        System.out.println(new DecimalFormat("0.00").format(4.025d));
    }

    /**
     * 精确计算
     */
    @Test
    public void testDoubleExact() {
        System.out.println("0.05 + 0.01 = " + DoubleUtil.add(0.05, 0.01));
        System.out.println("1.0 - 0.42 = " + DoubleUtil.sub(1.0, 0.42));
        System.out.println("4.015 * 100 = " + DoubleUtil.mul(4.015, 100d));
        //保留两位
        System.out.println("123.3 / 100 = " + DoubleUtil.divide(123.3, 100d));
        //保留三位
        System.out.println("123.3 / 100 = " + DoubleUtil.divide(123.3, 100d, 3));
        System.out.println(DoubleUtil.round(4.025d, 2));
    }


    @Test
    public void clean() {
        // 李涵涵看中的那个 152w   62.7/82.7 = 0.76
        String s = "5.1,1.5,3.3,25,4.8,12.9,10.1";
        String total = "82.7";

         //167.2W     85.0 / 112.87 =  0.75
        s = "7.7,7.4,4.2,13.8,30.8,4.4,10.5,6.2";
        total = "112.87";

        String[] split = s.split(",");
        List<String> list = Lists.newArrayList(split);
        Double sum = 0.0D;
        for (String s1 : list) {
            sum = DoubleUtil.add(Double.parseDouble(s1), sum);
        }
        System.out.println(sum + "   " + DoubleUtil.divide(sum, Double.parseDouble(total)));
    }

}
