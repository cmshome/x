package com.lxk.other;

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
    private LocalDate from;
    private LocalDate to;

    @Before
    public void init() {
        to = LocalDate.now();
    }

    @Test
    public void license() {
        from = LocalDate.of(2019, 9, 6);
        System.out.println("get drive license ： " + TimeUtils.between(from, to) + " 啦。");
    }

    @Test
    public void married() {
        from = LocalDate.of(2017, 7, 28);
        System.out.println("have marriage license ： " + TimeUtils.between(from, to) + " 啦。");

        from = LocalDate.of(2017, 9, 17);
        System.out.println("have been married ： " + TimeUtils.between(from, to) + " 啦。");
    }

    @Test
    public void mySon() {
        //阴历，腊月27早上出生🐣，28，29，30。距离过年三天
        from = LocalDate.of(2018, 2, 12);
        System.out.println("孩儿多大了： " + TimeUtils.between(from, to) + " 啦。");
        System.out.println("孩儿多大了： " + TimeUtils.betweenDay(from, to) + " 啦。");
    }

    @Test
    public void kidGarden() {
        from = LocalDate.of(2021, 9, 1);
        System.out.println("娃上学了： " + TimeUtils.between(from, to) + " 啦。");
    }

    @Test
    public void daughter() {
        from = LocalDate.of(2021, 4, 24);
        System.out.println("sql's daughter 多大了： " + TimeUtils.between(from, to) + " 啦。");
        System.out.println("sql's daughter 多大了： " + TimeUtils.betweenDay(from, to) + " 啦。");
    }

    @Test
    public void myMacPro() {
        from = LocalDate.of(2018, 6, 22);
        System.out.println("mac用了多久：" + TimeUtils.between(from, to));
    }

    @Test
    public void myWatchGT2() {
        from = LocalDate.of(2020, 6, 17);
        System.out.println("华为watch GT2用了多久： " + TimeUtils.between(from, to));
    }

    @Test
    public void iWatch() {
        from = LocalDate.of(2020, 9, 15);
        System.out.println("i watch 用了多久： " + TimeUtils.between(from, to));
    }

    @Test
    public void macAir() {
        from = LocalDate.of(2021, 4, 4);
        System.out.println("mac air 用了多久： " + TimeUtils.between(from, to));
    }

    @Test
    public void newBatteryDay() {
        from = LocalDate.of(2021, 4, 9);
        System.out.println("mac换键盘&电池多久： " + TimeUtils.between(from, to));
        from = LocalDate.of(2024, 3, 22);
        System.out.println("mac又换新电池多久： " + TimeUtils.between(from, to));
    }

    @Test
    public void work() {
        from = LocalDate.of(2015, 8, 31);
        System.out.println("stay at work ： " + TimeUtils.between(from, to));
    }

    /**
     * 测试这个util方法OK不
     */
    @Test
    public void test() {
        // 边界测试
        from = LocalDate.of(2023, 2, 22);
        to = LocalDate.of(2023, 3, 30);
        System.out.println(TimeUtils.between(from, to));
        System.out.println(TimeUtils.betweenDay(from, to));
    }

}
