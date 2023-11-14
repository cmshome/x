package com.lxk.other;

import com.google.common.collect.Lists;
import com.lxk.tool.util.DoubleUtil;
import org.junit.Test;

import java.util.List;

/**
 * @author LiXuekai on 2023/11/14
 */
public class LadderTest {


    @Test
    public void ladder() {
        String s = "29.97,29.04,29.04,11.6,11.6,11.6";
        Double sum = 0.0D;
        String[] split = s.split(",");
        List<String> list = Lists.newArrayList(split);
        for (String s1 : list) {
            sum = DoubleUtil.add(Double.parseDouble(s1), sum);
        }
        System.out.println(sum);
    }
}
