package com.lxk.jdk.common.number;

import com.lxk.tool.util.NumberFormatUtil;
import org.junit.Test;

/**
 * @author LiXuekai on 2023/8/18
 */
public class FloatTest {


    /**
     * 测试一下float数据不管，看看小数点后面会出现几位。
     */
    @Test
    public void f() {
        float f = 10 / 3f;
        showF(f);

        f = 100 / 3f;
        showF(f);
    }

    private void showF(float f) {
        System.out.println(f);
        System.out.println(NumberFormatUtil.format(f));
    }


}
