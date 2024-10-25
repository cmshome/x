package com.lxk.jdk8.date;

import org.junit.Test;

import java.time.Instant;

/**
 * @author LiXuekai on 2024/10/24
 */
public class InstantTest {

    @Test
    public void now() {
        Instant now = Instant.now();
        System.out.println(now.toEpochMilli());
        System.out.println(now.getEpochSecond());
    }
}
