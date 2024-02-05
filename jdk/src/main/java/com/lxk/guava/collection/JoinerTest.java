package com.lxk.guava.collection;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import com.lxk.tool.util.TimeUtils;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Joiner test
 *
 * @author LiXuekai on 2019/10/25
 */
public class JoinerTest {

    @Test
    public void test() {
        int[] numbers = {1, 2, 3, 4, 5};
        String numbersAsString = Joiner.on(";").join(Ints.asList(numbers));
        System.out.println(numbersAsString);
    }


    @Test
    public void tree() {
        LocalDateTime now = LocalDateTime.now();
        long end = TimeUtils.dayStartSecond(now);
        LocalDateTime time = TimeUtils.s2LocalDateTime(end);
        for (int i = 1; i < 8; i++) {
            LocalDateTime dateTime = time.minusDays(i);
            long from = TimeUtils.toS(dateTime);
            LocalDateTime a = dateTime.plusDays(1);
            long to = TimeUtils.toS(a);
            System.out.println("startTime=" + from + "&endTime=" + to + "     " + TimeUtils.formatS(from) + " - " + TimeUtils.formatS(to));
        }

    }
}
