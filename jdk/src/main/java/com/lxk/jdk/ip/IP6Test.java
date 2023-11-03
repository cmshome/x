//package com.lxk.jdk.ip;
//
//import com.lxk.tool.util.Tools;
//import org.junit.Test;
//import sun.net.util.IPAddressUtil;
//
//import java.math.BigDecimal;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//
///**
// * @author LiXuekai on 2021/4/28
// */
//public class IP6Test {
//
//    private static final String split = ":";
//    private static final String ipv6 = "ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff";
//
//    /**
//     * IPv4  x  4 =  IPv6
//     * 8 / 4 = 2 截取2段，就凑了个IPv4
//     */
//    @Test
//    public void test() {
//        long[] longs = Tools.ip2Longs(ipv6);
//        String long0 = Long.toBinaryString(longs[0]);
//        String long1 = Long.toBinaryString(longs[1]);
//        //64 个 1
//        System.out.println(long0);
//        System.out.println(long0.length());
//
//        //64 个 1
//        System.out.println(long1);
//        System.out.println(long1.length());
//
//        // 半个IPv6 是64个 1  8 x 8 = 64 ，8个段
//
//    }
//
//    @Test
//    public void testLongs2Io6(){
//        long[] longs = {9223372036854775807L,1L};
//        String s = Tools.longs2Ip(longs);
//        System.out.println(s);
//
//        System.out.println(Long.MAX_VALUE);
//        String s1 = Long.toBinaryString(9223372036854775807L);
//        System.out.println(s1);
//        System.out.println(s1.length());
//        long l = 9223372036854775807L + 1;
//        System.out.println(l);
//        String s2 = Long.toBinaryString(l);
//        System.out.println(s2);
//        System.out.println(s2.length());
//
//    }
//
//    @Test
//    public void test2(){
//        // 8 个1 就是255，即 8个二进制位，就是IPv4的一段IP。
//        System.out.println(Integer.toBinaryString(255));
//
//        //32 = 4 x 8 ，要4段数据来凑个IPv4
//
//
//
//        //将字符串按找16进制转成long 10进制数
//        long l = Long.parseLong("f", 16);
//        System.out.println(l);
//
//        l = Long.parseLong("10", 16);
//        System.out.println(l);
//
//        String replace = ipv6.replace(split, "");
//        String substring = replace.substring(24, 32);
//        System.out.println(substring);
//        String longToIP = Tools.longToIP(Long.parseLong(substring, 16));
//        System.out.println(longToIP);
//
//    }
//
//
//    @Test
//    public void a() {
//        // 还是64位的数，还能给转成long类型的数
//        BigDecimal bigDecimal = new BigDecimal("18230571300456429042");
//        System.out.println(bigDecimal.longValue());
//    }
//
//
//    @Test
//    public void b() {
//        // 64位全是1的正数long， 无符号位的数字，超过了Long.MAX_VALUE
//        BigDecimal bigDecimal = new BigDecimal("18446744073709551615");
//        long l = bigDecimal.longValue();
//        System.out.println(l);
//        System.out.println(Long.toBinaryString(l));
//        System.out.println(Long.toBinaryString(l).length());
//    }
//
//    @Test
//    public void c() {
//        String s = Tools.longs2Ip(new long[]{Long.MIN_VALUE, Long.MIN_VALUE});
//        System.out.println(s);
//        System.out.println(Long.toBinaryString(Long.MIN_VALUE));
//    }
//
//    /**
//     * long 支持处理无符号的long数字
//     */
//    @Test
//    public void d() {
//        String s = "18446744073709551615";
//        long l = Long.parseUnsignedLong(s);
//        System.out.println(l);
//        System.out.println(new BigDecimal(s).longValue());
//        System.out.println(Long.toBinaryString(l));
//        System.out.println(Long.toBinaryString(l).length());
//    }
//
//
//    @Test
//    public void cast() {
//        String ip6 = "fe80:1:1:1:14ce:647e:ee7d:1dd5";
//        boolean b = IPAddressUtil.isIPv6LiteralAddress(ip6);
//        System.out.println(b);
//
//        byte[] bytes = IPAddressUtil.textToNumericFormatV6(ip6);
//        String s = new String(bytes, UTF_8);
//        System.out.println(s);
//    }
//
//
//    @Test
//    public void ddd() {
//
//        byte[] bytes = s.getBytes(UTF_8);
//        System.out.println(bytes.length);
//        System.out.println(bytes.length * 549048L);
//        // 885 614 424
//    }
//}
