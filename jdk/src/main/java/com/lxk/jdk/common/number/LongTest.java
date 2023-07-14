package com.lxk.jdk.common.number;

import com.lxk.tool.util.TimeUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author LiXuekai on 2021/4/28
 */
public class LongTest {

    /**
     * 无符号64个1 unsigned long 数字
     */
    private static final String s = "18446744073709551615";

    @Test
    public void MaxAndMin() {
        outLongInfo(Long.MAX_VALUE);
        outLongInfo(Long.MIN_VALUE);

        System.out.println(3092530332L);

    }

    /**
     * int.max + 1 的变化：31个1，变成1和31个0
     */
    @Test
    public void out() {
        // 2147483647  ->  2038-01-19 11:14:07
        String string = Integer.toBinaryString(Integer.MAX_VALUE);
        System.out.println(string);
        System.out.println(string.length());

        System.out.println();

        String sss = Long.toBinaryString(2147483648L);
        System.out.println(sss);
        System.out.println(sss.length());

        System.out.println();

        // int.max + 1 直接变成 int.min 了
        int i = Integer.MAX_VALUE + 1;
        System.out.println(i);
        System.out.println(Integer.MIN_VALUE);
        String zz = Integer.toBinaryString(i);
        System.out.println(zz);
    }

    @Test
    public void test() {
        // 大于MAX，是无符号正数，但，还是64位的。
        outLongInfo(unsigned2Long1(s));
        outLongInfo(unsigned2Long2(s));

        System.out.println(longParseUnsigned(-216172773253120239L));
        System.out.println(longParseUnsigned(-1));
    }

    /**
     * 打印long的 value 二进制value 二进制value长度
     *
     * @param aLong java long value
     */
    private void outLongInfo(long aLong) {
        System.out.println("              long value is \t" + aLong);
        System.out.println("       long binary value is \t" + Long.toBinaryString(aLong));
        System.out.println("long binary value length is \t" + Long.toBinaryString(aLong).length());
    }

    /**
     * 方法1：unsigned long 2 signed long
     *
     * @param s unsigned long string
     */
    private long unsigned2Long1(String s) {
        return Long.parseUnsignedLong(s);
    }

    /**
     * 方法2：unsigned long 2 signed long
     *
     * @param s unsigned long string
     */
    private long unsigned2Long2(String s) {
        return new BigDecimal(s).longValue();
    }

    /**
     * long转成无符号数
     */
    public static BigDecimal longParseUnsigned(long value) {
        if (value >= 0) {
            return new BigDecimal(value);
        }
        // 按位与操作，就是把负数给转成相应的正数，比如-10 转成 10
        long lowValue = value & Long.MAX_VALUE;
        // 然后再左移 1 位，然后在最低位 + 1 。跟下面的 dd() 一样的逻辑。
        return BigDecimal.valueOf(lowValue).add(BigDecimal.valueOf(Long.MAX_VALUE)).add(BigDecimal.valueOf(1));
    }

    @Test
    public void dd() {
        // 乘以2，相当于左移 1 位，最后一位是0，然后再加个1，就64个1了。
        BigDecimal multiply = new BigDecimal(Long.MAX_VALUE).multiply(new BigDecimal(2));
        System.out.println("Long.MAX_VALUE * 2 = " + multiply.toPlainString());
        BigDecimal subtract = multiply.add(new BigDecimal(1));
        System.out.println("Long.MAX_VALUE * 2 + 1 = " + subtract.toPlainString());
        System.out.println("64 个 1 的 无符号数 = " + s);
    }

    /**
     * 整的分钟数
     */
    @Test
    public void jay() throws InterruptedException {
        int span = 60;
        while (true) {
            long nowS = TimeUtils.nowS();
            long l = nowS / span * span;
            System.out.println(l + "  " + TimeUtils.formatS(l));
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
