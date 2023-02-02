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
}
