package com.lxk.tool;

import com.lxk.tool.util.TimeUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author LiXuekai on 2021/7/19
 */
public class TestTimeUtil {
    @Test
    public void test() {
        //1626696905595
        System.out.println(TimeUtils.nowMs());
    }

    @Test
    public void datetime() {
        LocalDate localDate = LocalDate.of(2022, 11, 21);
        LocalTime localTime = LocalTime.of(22, 30, 50);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        System.out.println(TimeUtils.format(localDateTime));
    }

    @Test
    public void date() {
        LocalDate start = LocalDate.of(2022, 11, 21);
        LocalDate localDate = start.plusDays(45);
        System.out.println(TimeUtils.format(localDate));
    }

    @Test
    public void time() {
        LocalTime localTime = LocalTime.of(22, 30, 50);
        System.out.println(TimeUtils.format(localTime));
        System.out.println(TimeUtils.formatS(localTime));
    }

    @Test
    public void zero() {
        String s = TimeUtils.formatS(0);
        System.out.println(s);
        s = TimeUtils.formatMs(0);
        System.out.println(s);
    }

    @Test
    public void format() {
        String s = TimeUtils.formatS(1713904680);
        System.out.println(s);

        s = TimeUtils.formatS(1714009860);
        System.out.println(s);
    }

    @Test
    public void sameDay() {
        // 时间戳，是从1970年1月1日（UTC/GMT的午夜）开始所经过的秒数（不考虑闰秒），用于表示一个时间点
        LocalDateTime a = LocalDateTime.of(1970, 1, 1, 10, 12);
        LocalDateTime b = LocalDateTime.now();
        boolean sameDay = TimeUtils.sameDay(a, b);
        System.out.println(sameDay);
    }

    @Test
    public void betweenSecond() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusMinutes(1);
        long l = TimeUtils.betweenSecond(now, end);
        System.out.println(l);
    }

}
