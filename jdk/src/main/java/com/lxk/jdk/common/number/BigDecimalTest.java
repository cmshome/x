package com.lxk.jdk.common.number;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author LiXuekai on 2020/8/17
 */
public class BigDecimalTest {
    @Test
    public void test() {
        double num = 11.3456723;
        System.out.println("数字构造器" + new BigDecimal(Double.valueOf(num)));
        System.out.println("字符构造器" + new BigDecimal(Double.valueOf(num).toString()));
        System.out.println(BigDecimal.valueOf(num));
    }

    @Test
    public void lxk() {
        String num = "6.21E+18";
        System.out.println(new BigDecimal(num).toPlainString());
    }


    @Test
    public void compare() {
        List<String> list = Lists.newArrayList(
                "2595859267661660161",
                "2595859267661660161",
                "3166439535",
                "3166439515",
                "3166439519",
                "3166439518",
                "3166439517",
                "3166439516",
                "3166439511"
        );

        while (true) {
            // 56%
            a(list);
            // 44%
            b(list);
        }
    }

    private void b(List<String> list) {
        for (String value : list) {
            long b = Long.parseLong(value.trim());
        }
    }

    private void a(List<String> list) {
        for (String value : list) {
            long a = new BigDecimal(value.trim()).longValue();
        }
    }
}
