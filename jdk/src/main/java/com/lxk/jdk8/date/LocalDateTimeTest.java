package com.lxk.jdk8.date;

import com.lxk.tool.util.TimeUtils;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * LocalDateTime test
 * 在新的Java 8中，日期和时间被明确划分为LocalDate和LocalTime，
 * LocalDate无法包含时间；
 * LocalTime无法包含日期；
 * LocalDateTime才能同时包含日期和时间。
 *
 * @author LiXuekai on 2019/9/10
 */
public class LocalDateTimeTest {
    /**
     * 返回今年的双11零点的时间
     */
    private static final LocalDateTime currentDual11 = LocalDateTime.now().withMonth(11).withDayOfMonth(11).withHour(0).withMinute(0).withSecond(0);
    /**
     * 返回去年的双11零点的时间
     */
    private static final LocalDateTime lastDual11 = currentDual11.minusYears(1);

    @Test
    public void x() {

    }
    @Test
    public void format() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String format = TimeUtils.format(localDateTime);
        System.out.println(format);
    }

    @Test
    public void toEpochDay() {
        LocalTime localTime = LocalDateTime.now().toLocalTime();
        long l = LocalDateTime.now().toLocalDate().toEpochDay();
        System.out.println(l);
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate parse = LocalDate.parse("20191031", yyyyMMdd);
        System.out.println(parse.toEpochDay());
    }

    /**
     * 将秒数格式化成日期字符串输出
     */
    @Test
    public void turnSecondsToData() {
        LocalDateTime localDateTime = TimeUtils.s2LocalDateTime(TimeUtils.nowS());
        System.out.println(localDateTime.getDayOfMonth());
        LocalDateTime localDateTime1 = localDateTime.withDayOfMonth(11);

        System.out.println(TimeUtils.format(localDateTime1));
        System.out.println(TimeUtils.toS(localDateTime1));
        System.out.println(TimeUtils.toMs(localDateTime1));
    }

    /**
     * LocalDateTime 转秒和毫秒
     */
    @Test
    public void toSecondsAndMilliSecond() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(TimeUtils.toS(now));
        System.out.println(TimeUtils.toMs(now));
    }

    /**
     * 秒个毫秒 转 LocalDateTime
     */
    @Test
    public void toLocalDateTime() {
        long nowS = TimeUtils.nowS();
        long nowMs = TimeUtils.nowMs();
        LocalDateTime localDateTime1 = TimeUtils.s2LocalDateTime(nowS);
        LocalDateTime localDateTime2 = TimeUtils.ms2LocalDateTime(nowMs);
        System.out.println(TimeUtils.formatS(localDateTime1));
        System.out.println(TimeUtils.formatMs(localDateTime2));
    }

    /**
     * 由秒数转成Java8时间类操作对象
     */
    @Test
    public void secondToJava8Date() {
        long second = TimeUtils.nowS();
        System.out.println(second);

        LocalDateTime localDateTime = TimeUtils.s2LocalDateTime(second);
        LocalDateTime dateTime = localDateTime.plusDays(-1);

        long second1 = TimeUtils.toS(dateTime);
        System.out.println(second1);

        System.out.println(TimeUtils.format(localDateTime));
        System.out.println(TimeUtils.format(dateTime));

    }

    /**
     * jdk 1.8 中的 LocalDateTime 的使用
     */
    @Test
    public void localDateTimeTest() {
        System.out.println("-----------test java 8 LocalDateTime-----------");
        //当前时间，以秒为单位。
        long epochSecond = TimeUtils.nowS();
        //默认使用系统时区
        ZoneId zoneId = ZoneOffset.systemDefault();
        //之所以这么初始化，是因为根据传入的时间进行操作
        LocalDateTime localDateTime = TimeUtils.s2LocalDateTime(epochSecond);
        //LocalDateTime.now();//也可以这么获得当前时间
        System.out.println("localDateTime 初始化值：" + localDateTime);
        System.out.println("getYear：" + localDateTime.getYear());
        //方法返回值类型特殊，是枚举类型：Month类型
        System.out.println("getMonth：" + localDateTime.getMonth());
        System.out.println("getDayOfMonth：" + localDateTime.getDayOfMonth());
        System.out.println("getHour：" + localDateTime.getHour());
        System.out.println("getMinute：" + localDateTime.getMinute());
        System.out.println("getSecond：" + localDateTime.getSecond());
        System.out.println("getNano：" + localDateTime.getNano());
        System.out.println("getDayOfWeek：" + localDateTime.getDayOfWeek());

        /*
         * 获得传入时间的某一天的凌晨零分零秒的秒数
         */
        long dayStart = TimeUtils.dayStartSecond(localDateTime);
        System.out.println("dayStart 时间戳，秒数：" + dayStart);
        /*
         * 获得传入时间的周一的凌晨零分零秒的秒数
         */
        localDateTime = LocalDateTime.of(2017, 12, 2, 0, 0, 0);
        System.out.println("localDateTime 设置当前值：" + localDateTime);
        System.out.println("getDayOfWeek：" + localDateTime.getDayOfWeek());
        System.out.println("getDayOfWeek 的 ordinal 值：" + localDateTime.getDayOfWeek().ordinal());
        System.out.println("getDayOfWeek 的 value 就是周几的值：" + localDateTime.getDayOfWeek().getValue());
        LocalDateTime weekStart = localDateTime.minusDays(localDateTime.getDayOfWeek().ordinal()).withHour(0).withMinute(0).withSecond(0);
        System.out.println("weekStart：" + weekStart);
        /*
         * 获得传入时间的月份的第一天的凌晨零分零秒的秒数
         */
        long monthStart = localDateTime.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).atZone(zoneId).toEpochSecond();
        System.out.println("monthStart 时间戳，秒数：" + monthStart);

        /*
         * 传入时间的年的第一天
         */
        LocalDateTime firstDayOfYear = localDateTime.with(TemporalAdjusters.firstDayOfYear());
        System.out.println("firstDayOfYear：" + firstDayOfYear);

        /*
         * 当前时间，往后推一周的时间。plus   加
         */
        LocalDateTime plusWeeks = localDateTime.plusWeeks(1);
        System.out.println("plus one week：" + plusWeeks);
        /*
         * 当前时间，向前推一周的时间。minus  减
         */
        LocalDateTime minusWeeks = localDateTime.minusWeeks(1);
        System.out.println("minus one week：" + minusWeeks);

        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String startTime = "2016-11-13 23:59";
        localDateTime = LocalDateTime.parse(startTime, sf);
        System.out.println(localDateTime);
        //格式化一下
        System.out.println(localDateTime.format(sf));
    }


    /**
     * 对时间进行  加减操作。
     */
    @Test
    public void mathTest() {
        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.format(sf));

        LocalDateTime localDateTime1 = localDateTime.minusYears(1);
        System.out.println(localDateTime1.format(sf));
    }

    @Test
    public void parse() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, 9, 3, 16, 26, 0);
        long l = TimeUtils.toS(localDateTime);
        System.out.println(l);
    }


    @Test
    public void instance() {
        // 这毫秒的设置怎么不管用
        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
        LocalDateTime localDateTime = LocalDateTime.now().withMonth(11).withDayOfMonth(11).withHour(0).withMinute(0).withSecond(0).withNano(777);
        System.out.println(localDateTime.format(sf));

        LocalDateTime last = localDateTime.minusYears(1);
        System.out.println(last.format(sf));


        System.out.println(currentDual11.format(sf));
        System.out.println(lastDual11.format(sf));

    }

    @Test
    public void dual() {
        String s="11-10 20:00:00";
        int year = LocalDateTime.now().getYear();
        System.out.println(year);
        s = year + "-" + s;
        System.out.println(s);

        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime parse = LocalDateTime.parse(s, sf);
        System.out.println(TimeUtils.toS(parse));

    }

    @Test
    public void parse2() {
        String text = "2022-11-10 20:00:00";
        LocalDateTime parse = TimeUtils.parse(text);
        if (parse == null) {
            return;
        }
        long s = TimeUtils.toS(parse);
        System.out.println(s);
    }


}
