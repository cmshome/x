package com.lxk.jdk8.date;

import com.lxk.tool.util.TimeUtils;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * 计算2个时间相差几年几个月几天。
 *
 * @author LiXuekai on 2019/9/10
 */
public class GetBetweenDaysTest {
    private LocalDate start;
    private LocalDate end;

    @Before
    public void init() {
        end = LocalDate.now();
    }

    @Test
    public void license() {
        start = LocalDate.of(2019, 9, 6);
        System.out.println("get drive license ： " + TimeUtils.between(start, end) + " 啦。");
    }

    @Test
    public void married() {
        start = LocalDate.of(2017, 7, 28);
        System.out.println("have marriage license ： " + TimeUtils.between(start, end) + " 啦。");

        start = LocalDate.of(2017, 9, 17);
        System.out.println("have been married ： " + TimeUtils.between(start, end) + " 啦。");
    }

    @Test
    public void mySon() {
        //阴历，腊月27早上出生🐣，28，29，30。距离过年三天
        start = LocalDate.of(2018, 2, 12);
        System.out.println("孩儿多大了： " + TimeUtils.between(start, end) + " 啦。");
        System.out.println("孩儿多大了： " + TimeUtils.betweenDay(start, end) + " 啦。");
    }

    @Test
    public void kidGarden() {
        start = LocalDate.of(2021, 9, 1);
        System.out.println("娃上学了： " + TimeUtils.between(start, end) + " 啦。");
    }

    @Test
    public void daughter() {
        start = LocalDate.of(2021, 4, 24);
        System.out.println("sql's daughter 多大了： " + TimeUtils.between(start, end) + " 啦。");
        System.out.println("sql's daughter 多大了： " + TimeUtils.betweenDay(start, end) + " 啦。");
    }

    @Test
    public void myMacPro() {
        start = LocalDate.of(2018, 6, 10);
        System.out.println("mac用了多久：" + TimeUtils.between(start, end));
    }

    @Test
    public void myWatchGT2() {
        start = LocalDate.of(2020, 6, 17);
        System.out.println("华为watch GT2用了多久： " + TimeUtils.between(start, end));
    }

    @Test
    public void iWatch() {
        start = LocalDate.of(2020, 9, 15);
        System.out.println("i watch 用了多久： " + TimeUtils.between(start, end));
    }

    @Test
    public void macAir() {
        start = LocalDate.of(2021, 4, 4);
        System.out.println("mac air 用了多久： " + TimeUtils.between(start, end));
    }

    @Test
    public void newBatteryDay() {
        start = LocalDate.of(2021, 4, 9);
        System.out.println("mac换键盘&电池多久： " + TimeUtils.between(start, end));
    }

    /**
     * 测试这个util方法OK不
     */
    @Test
    public void test() {
        // 边界测试
        start = LocalDate.of(2020, 2, 29);
        end = LocalDate.of(2020, 3, 1);
        System.out.println(TimeUtils.between(start, end));
    }

}
