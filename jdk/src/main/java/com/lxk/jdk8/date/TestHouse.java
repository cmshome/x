package com.lxk.jdk8.date;

import com.google.common.collect.Lists;
import com.lxk.tool.util.DoubleUtil;
import org.junit.Test;

import java.util.List;

/**
 * @author LiXuekai on 2023/8/16
 */
public class TestHouse {

    @Test
    public void y() {
        Integer total = 295500;
        Integer year = 25;
        Integer day = year * 365;
        Double divide = DoubleUtil.divide(total.doubleValue(), day.doubleValue());
        System.out.println("day:" + divide);

        divide = DoubleUtil.divide(total.doubleValue(), year.doubleValue());
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



}
