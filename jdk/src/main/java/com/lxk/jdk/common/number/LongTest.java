package com.lxk.jdk.common.number;

import com.lxk.tool.util.TimeUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author LiXuekai on 2021/4/28
 */
public class LongTest {


    @Test
    public void parse() {
        long rawKafkaTimeString = TimeUtils.nowMs();

        //  -1.0 下面的转换就异常了！！！
        long rawKafkaTime = Long.parseLong(new BigDecimal(rawKafkaTimeString).toPlainString());

        System.out.println(rawKafkaTime);
        double v = Double.parseDouble(new BigDecimal(rawKafkaTimeString).toPlainString());
        System.out.println(((Double) v).longValue());
    }


    @Test
    public void cast() {
        long longValue = new BigDecimal("0.a").longValue();
        System.out.println(longValue);
        long l = Long.parseLong("0.00".trim());
        System.out.println(l);
    }

    public Double getDouble(Map<String, Object> map, String key) {
        try {
            Object o = map.get(key);
            if (o instanceof Long) {
                // unbox 一下，就能转了
                long l = (long) o;
                return (double) l;
            }
            return Double.parseDouble(o.toString());
        } catch (Exception ignored) {
            return 0.;
        }
    }

    @Test
    public void long2Double() {
        long s = 123456789L;
        double a = (double) s;
        System.out.println(a);

        long max = Long.MAX_VALUE;
        double d = (double) max;

        System.out.println("double Long Max " + d);
        System.out.println("double Long Max toPlainString " + new BigDecimal(d).toPlainString());
        System.out.println("Long.MAX_VALUE " + Long.MAX_VALUE);

        long l = Double.doubleToLongBits(max);
        System.out.println("Double.doubleToLongBits(max) " + l);
        int i = 100;
        double aa = (double) i;
        System.out.println(aa);

        System.out.println(1 + 1 + 2 * 3 + "");
    }

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

    @Test
    public void nanosecond() {
        // 原始值：     9223372036854775807
        // es中的最大值：9223372036854776000
        long maxValue = Long.MAX_VALUE;
        System.out.println(maxValue);
        long na = 1703320042083237750L;
        System.out.println(na);
        System.out.println(maxValue - na);

        Instant instant = Instant.ofEpochSecond(1703320042L, 83237750L);
        System.out.println(instant);
        System.out.println(instant.getEpochSecond());
        System.out.println(instant.getNano());
        System.out.println(instant.getEpochSecond() * 1_000_000_000 + instant.getNano());
    }

    /**
     * 经常说的 一万 X 一万，结果是一亿
     */
    @Test
    public void mm() {
        long s = 10000 * 10000;
        BigDecimal bigDecimal = new BigDecimal(s);
        System.out.println(bigDecimal.toPlainString());
    }

    @Test
    public void max() {
        long l = Long.MAX_VALUE / 60L / 24L / 365L;
        System.out.println(l);
    }

    @Test
    public void cast2() {
        String s = "  0.00 ";
        s = s.trim();
        long l = new BigDecimal(s).longValue();
        double v = new BigDecimal(s).doubleValue();
        System.out.println(l == v);

    }

}
