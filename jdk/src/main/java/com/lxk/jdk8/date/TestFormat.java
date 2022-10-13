package com.lxk.jdk8.date;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author LiXuekai on 2022/1/13
 */
public class TestFormat {
    /**
     * 8小时的秒数
     */
    private static final int OFFSET = 8 * 60 * 60;
    private static final ZoneId ZONE_ID = ZoneOffset.systemDefault();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss,SSS");

    @Test
    public void test() {
        String string = "2021-10-01T04:37:34,703";
        LocalDateTime localDateTime = LocalDateTime.parse(string, FORMATTER);
        long s = localDateTime.atZone(ZONE_ID).toEpochSecond();
        long ms = localDateTime.toInstant(ZoneOffset.ofTotalSeconds(OFFSET)).toEpochMilli();
        System.out.println(s);
        System.out.println(ms);
    }
}
