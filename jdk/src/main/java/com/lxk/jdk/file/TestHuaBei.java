package com.lxk.jdk.file;

import com.google.common.collect.Lists;
import com.lxk.tool.util.FileIOUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 计算所有花销的sum
 *
 * @author LiXuekai on 2025/3/3
 */
public class TestHuaBei {
    private static final String path = "/Users/fang/Downloads/sum.txt";


    @Test
    public void total() {
        List<String> fail = Lists.newArrayList();
        List<String> list = FileIOUtil.readFileByLine(path, false);
        // [2.7, 0.07, 19.37, 17.77, 24.67, 17.63, 47, 15, 3.35, 36, 15, 28.9, 36, 32, 54.5, 2.7, 110.2, 12.9, 25.9, 10.7, 97.9, 2.37, 3.9, 29, 11.82, 2.7, 17.9, 22.4, 21, 62.01, 34, 19, 15, 17.9, 27.9, 12.8, 19, 31, 15, 2.7, 10.9, 9.82, 2.7, 17, 32, 13, 16.77, 219, 15.02, 17, 31, 1, 1.09, 15, 32, 18, 19, 14.59, 13.45, 32, 15, 2.7, 10, 5, 2, 1.93, 83, 1.9, 0.01, 17.9, 0.17, 2.7, 15, 13.33, 0.12]
        System.out.println(list);
        BigDecimal sum = new BigDecimal("0");
        for (String s : list) {
            try {
                BigDecimal bigDecimal = new BigDecimal(s);
                sum = sum.add(bigDecimal);
            } catch (Exception e) {
                fail.add(s);
            }
        }
        // 1678.76
        System.out.println(sum.doubleValue());
        // []
        System.out.println(fail);
    }

    @Test
    public void sum() {
        List<Number> list = Lists.newArrayList(15, 32, 3, 19, 42, 15, 32, 47.5, 11.9, 12.9, 32.05, 0.3, 7.2, 5.9, 28, 2.7, 0.07, 19.37, 17.77, 24.67, 17.63, 47, 15, 3.35, 36, 15, 28.9, 36, 32, 54.5, 2.7, 110.2, 12.9, 25.9, 10.7, 97.9, 2.37, 3.9, 29, 11.82, 2.7, 17.9, 22.4, 21, 62.01, 34, 19, 15, 17.9, 27.9, 12.8, 19, 31, 15, 2.7, 10.9, 9.82, 2.7, 17, 32, 13, 16.77, 219, 15.02, 17, 31, 1, 1.09, 15, 32, 18, 19, 14.59, 13.45, 32, 15, 2.7, 10, 5, 2, 1.93, 83, 1.9, 0.01, 17.9, 0.17, 2.7, 15, 13.33, 0.12);
        list.sort(Comparator.comparingDouble(Number::doubleValue));
        Collections.reverse(list);
        System.out.println(list);

        double sum = list.stream().mapToDouble(Number::doubleValue).sum();
        System.out.println(sum);
    }
}
