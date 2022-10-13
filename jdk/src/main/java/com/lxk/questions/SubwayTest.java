package com.lxk.questions;

import com.google.common.collect.Lists;
import com.lxk.tool.util.DoubleUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author LiXuekai on 2021/12/6
 */
public class SubwayTest {
    /**
     * 累计满100元后，从下一次乘车时给予8折优惠
     */
    private static final int FIRST_MAX = 100;
    /**
     * 累计满150元后，从下一次乘车时给予5折优惠
     */
    private static final int SECOND_MAX = 150;
    /**
     * 乘车的天数
     */
    private int day;
    /**
     * 金额范围【3，9】
     */
    private double each;
    /**
     * 第一次打折的实际金额
     */
    private double first;
    /**
     * 第一次打折的次数
     */
    private int firstTime;
    /**
     * 第二次打折的实际金额
     */
    private double second;
    /**
     * 第二次打折的次数
     */
    private int secondTime;


    @Before
    public void init() {
        day = 31;
        each = 6;
        findFirst();
        findSecond();
        System.out.println("累计满100的时候，是第" + firstTime + " 次，实际金额是：" + first);
        System.out.println("累计满150的时候，是第" + secondTime + " 次，实际金额是：" + second);
    }

    /**
     * 找第一次到达满减的阈值的实际金额值和乘车次数
     */
    private void findFirst() {
        for (int i = 1; true; i++) {
            double s = i * each;
            if (s >= FIRST_MAX) {
                first = s;
                firstTime = i;
                return;
            }
        }
    }

    /**
     * 找第二次到达满减的阈值的实际金额值和乘车次数
     */
    private void findSecond() {
        for (int i = 1; true; i++) {
            double s = i * each * 0.7 + first;
            if (s >= SECOND_MAX) {
                second = s;
                secondTime = i + firstTime;
                return;
            }
        }
    }

    @Test
    public void subway() {
        List<Double> list = Lists.newArrayList();
        for (int i = 1; i <= day * 2; i++) {
            double total = total(i);
            list.add(total);
            System.out.println("day: " + Double.valueOf(Math.ceil(i / 2.0)).intValue() + " time: " + i + " total: " + total);
        }

        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            double a = list.get(i);
            double b = list.get(i + 1);
            // 会出现4.2000000001的值，所以用util给修饰一下
            double sub = DoubleUtil.sub(b, a);
            System.out.println(sub);
        }
    }

    /**
     * 计算乘车总额
     *
     * @param time 乘车次数，计划是一天两次
     * @return 累计总额
     */
    private double total(int time) {
        if (time <= firstTime) {
            return time * each;
        }
        if (time <= secondTime) {
            return (time - firstTime) * each * 0.7 + first;
        }
        return (time - secondTime) * each * 0.5 + second;
    }
}
