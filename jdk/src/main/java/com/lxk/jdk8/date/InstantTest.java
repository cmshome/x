package com.lxk.jdk8.date;

import org.junit.Test;

import java.time.*;

/**
 * @author LiXuekai on 2024/10/24
 */
public class InstantTest {


    @Test
    public void parse() {
        String s = "2025-02-12T01:45:19.624Z";
        Instant parse = Instant.parse(s);
        System.out.println(parse);
    }

    @Test
    public void now() {
        // 获取的是标准时间，是不带时区的！如果想要获取到中国的时间，需要在这个基础上加8个小时。
        Instant now = Instant.now();
        // 2025-02-18T02:20:18.408Z
        System.out.println(now);
        // 1739845218408
        System.out.println(now.toEpochMilli());
        // 1739845218
        System.out.println(now.getEpochSecond());
    }

    @Test
    public void between() {
        Instant startTime = Instant.now();
        for (int i = 0; i < 10000; i++) {
            Instant.now();
        }
        Instant endTime = Instant.now();

        // 计算耗时
        Duration duration = Duration.between(startTime, endTime);
        System.out.println("操作耗时: " + duration.toMillis() + " 毫秒");
    }


    @Test
    public void second() {
        Instant now = Instant.now();
        long epochSecond = now.getEpochSecond();
        // 转换为从1970-01-01T00:00:00Z开始的秒数（Unix时间戳）
        System.out.println(epochSecond);

        // 转换为从1970-01-01T00:00:00Z开始的毫秒数（常用于Java中的时间戳）
        long epochMilli = now.toEpochMilli();
        System.out.println(epochMilli);

        //Instant 本身不包含时区信息，但你可以通过将其转换为其他日期时间对象（如 ZonedDateTime、LocalDateTime 等）来添加时区信息。这些转换通常涉及使用 ZoneId 来指定时区。

        ZonedDateTime time = now.atZone(ZoneId.of("Asia/Shanghai"));
        // 2025-02-18T10:29:37.809+08:00[Asia/Shanghai]
        System.out.println(time);
    }


}
