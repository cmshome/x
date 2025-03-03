package com.lxk.jdk.file;

import com.google.common.collect.Lists;
import com.lxk.tool.util.FileIOUtil;
import org.junit.Test;

import java.math.BigDecimal;
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
        BigDecimal sum = new BigDecimal("0");
        for (String s : list) {
            try {
                BigDecimal bigDecimal = new BigDecimal(s);
                sum = sum.add(bigDecimal);
            } catch (Exception e) {
                fail.add(s);
            }
        }
        System.out.println(sum.doubleValue());
        System.out.println(fail);
    }
}
