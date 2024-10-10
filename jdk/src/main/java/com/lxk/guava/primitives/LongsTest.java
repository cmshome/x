package com.lxk.guava.primitives;

import com.google.common.primitives.Longs;
import org.junit.Test;

/**
 * @author LiXuekai on 2023/1/3
 */
public class LongsTest {

    @Test
    public void min() {
        long[] array = {1, 2, 3, 4, 5};
        long min = Longs.min(array);
        long max = Longs.max(array);
        System.out.println(min);
        System.out.println(max);
    }


    @Test
    public void max() {
        System.out.println("1058853744867524600");
        // 9223372036854775807
        System.out.println(Long.MAX_VALUE);
        // 2147483647
        System.out.println(Integer.MAX_VALUE);
        // 1.7976931348623157E308
        System.out.println(Double.MAX_VALUE);
    }
}
