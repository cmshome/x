package com.lxk.jdk8.date;

import com.lxk.tool.util.TimeUtils;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * DateTimeFormatter test
 *
 * @author LiXuekai on 2019/9/20
 */
public class DateTimeFormatterTest {

    /**
     * 将ms/s数格式化成日期字符串输出
     */
    @Test
    public void format() {
        long currentTimeMillis = System.currentTimeMillis();
        String formatMs = TimeUtils.formatMs(currentTimeMillis);
        System.out.println(formatMs);
        System.out.println(currentTimeMillis);
    }

    @Test
    public void toEpochDay() {
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate parse = LocalDate.parse("20191031", yyyyMMdd);
        System.out.println(parse.toEpochDay());
    }

    /**
     * 转秒和毫秒
     */
    @Test
    public void seconds() {
        LocalDateTime now = LocalDateTime.now();
        long l = now.atZone(ZoneId.systemDefault()).toEpochSecond();
        System.out.println(l);

        long l1 = now.toEpochSecond(ZoneOffset.ofTotalSeconds(0));
        System.out.println(l1);

        Long milliSecond = now.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
        System.out.println(milliSecond);
    }
}
