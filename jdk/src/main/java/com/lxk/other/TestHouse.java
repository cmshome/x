package com.lxk.other;

import com.google.common.collect.Lists;
import com.lxk.tool.util.DoubleUtil;
import org.junit.Test;

import java.util.List;

/**
 * @author LiXuekai on 2023/8/16
 */
public class TestHouse {

    @Test
    public void interest() {
        int total = 295500;
        int year = 25;
        int day = year * 365;
        Double divide = DoubleUtil.divide((double) total, (double) day);
        System.out.println("day:" + divide);

        divide = DoubleUtil.divide((double) total, (double) year);
        System.out.println("year:" + divide);
    }


    @Test
    public void clean() {
        // 李涵涵看中的那个标150w，没办好户口呢，被别人捷足先登了,143W成交了。  62.7 / 82.7 = 0.76
        String s = "5.1,1.5,3.3,25,4.8,12.9,10.1";
        String total = "82.7";
        compute(s, total);


        // 媳妇儿看中的标147，最终142成交了。  63.2 / 91.85 = 0.69
        s = "3.1,5.9,10.7,4.9,21.7,13.5,3.4";
        total = "91.85";
        compute(s, total);
    }

    /**
     * 计算得房率
     */
    private void compute(String s, String total) {
        Double sum = 0.0D;
        String[] split = s.split(",");
        List<String> list = Lists.newArrayList(split);
        for (String s1 : list) {
            sum = DoubleUtil.add(Double.parseDouble(s1), sum);
        }
        System.out.println(sum + " / " + total + " = " + DoubleUtil.divide(sum, Double.parseDouble(total)) + "%");
    }

    @Test
    public void window() {
        // 83宽 忠旺 1.8厚 隐形内开内倒 304 平开金刚网纱窗 3层真空玻璃 6扇窗户
        double m = 11600D;
        double s = 13D;
        //892.31 每平方
        System.out.println(DoubleUtil.divide(m, s));

        // 72宽 实德 1.4厚 隐形内开内倒 304 平开金刚网纱窗 3层真空玻璃
        m = 20610;
        s = 24.5;
        //841.22 每平方
        System.out.println(DoubleUtil.divide(m, s));
    }

    @Test
    public void door() {
        int a = 800;
        System.out.println(a*2 + 886);
    }

    @Test
    public void tv() {
        System.out.println(7617);
    }

    @Test
    public void sofa() {
        System.out.println();
    }

    @Test
    public void train() {
        Double oneDay = DoubleUtil.mul(54.5, 2.0);
        System.out.println("one day：" + oneDay);
        Double month = DoubleUtil.mul(oneDay, 20.0);
        System.out.println("each day in every month：" + month);
        Double week = DoubleUtil.mul(oneDay, 4.0);
        System.out.println("each week in every month：" + week);
        System.out.println(DoubleUtil.sub(month, week));
    }


}
