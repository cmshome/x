package com.lxk.jdk.common;

import com.lxk.tool.util.TimeUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author LiXuekai on 2025/1/2
 */
public class TimeFormatTest {

    @Test
    public void formatZ() {
        String s = "2025-01-02T03:17:43.353Z";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime parse = LocalDateTime.parse(s, formatter);
        long l = TimeUtils.toMs(parse);
        System.out.println(l);
        l = TimeUtils.toS(parse);
        System.out.println(l);
    }
}
