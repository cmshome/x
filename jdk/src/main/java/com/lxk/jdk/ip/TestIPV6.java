//package com.lxk.jdk.ip;
//
//import com.google.common.base.Joiner;
//import com.google.common.collect.Lists;
//import com.lxk.tool.util.Tools;
//import org.junit.Test;
//import sun.net.util.IPAddressUtil;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * 测试IPv6
// *
// * @author LiXuekai on 2020/2/13
// */
//public class TestIPV6 {
//    private static final String split = ":";
//    private static final String ipv6 = "ffff:ffff:ffff:ffff:ffff:ffff:ffff:fff";
//    private static final int IP_6_LENGTH = 8;
//    private static final String COLON = ":";
//
//    /**
//     * IPv4  x  4 =  IPv6
//     * 8 / 4 = 2 截取2段，就凑了个IPv4
//     */
//    @Test
//    public void test() {
//        long[] longs = Tools.ip2Longs(ipv6);
//        System.out.println(Arrays.toString(longs));
//        String long0 = Long.toBinaryString(longs[0]);
//        String long1 = Long.toBinaryString(longs[1]);
//        //64 个 1
//        System.out.println(long0);
//        System.out.println(long0.length());
//
//        //64 个 1
//        System.out.println(long1);
//        System.out.println(long1.length());
//        System.out.println(long0 + long1);
//
//        BigDecimal bigDecimal = new BigDecimal(long0 + long1);
//
//        // 高位就给丢了，失真了。。。
//        System.out.println(bigDecimal.intValue());
//
//        // 半个IPv6 是64个 1  8 x 8 = 64 ，8个段
//
//    }
//
//    @Test
//    public void ll() {
//        long l = System.currentTimeMillis();
//        long a = l / 1000 % 60;
//        long b = l / 1000 / 60;
//        System.out.println(b + " 分   " + a + " 秒" );
//    }
//
//    @Test
//    public void test2() {
//        // 8 个1 就是255，即 8个二进制位，就是IPv4的一段IP。
//        System.out.println(Integer.toBinaryString(255));
//
//        //32 = 4 x 8 ，要4段数据来凑个IPv4
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
//        //String substring = replace.substring(24, 32);
//        //System.out.println(substring);
//        //String longToIP = Tools.longToIP(Long.parseLong(substring, 16));
//        //System.out.println(longToIP);
//
//    }
//
//
//    @Test
//    public void cast() {
//        long[] longss = new long[]{8526721465200965204L, 1095252444514L};
//        System.out.println(Tools.longs2Ip(longss));
//        String ip6 = "7654:0:ffff:7654:562:222:ff:0";
//        long[] longs = Tools.ip2Longs(ip6);
//        System.out.println(Arrays.toString(longs));
//        String s = Tools.longs2Ip(longs);
//        System.out.println(ip6);
//        System.out.println(s);
//        System.out.println(ip6.equalsIgnoreCase(s));
//
//        System.out.println();
//
//        long ip6String2Long = Tools.castIp6String2Long(ip6);
//        System.out.println(ip6String2Long);
//        System.out.println(Tools.longToIP(ip6String2Long));
//
//        System.out.println(Tools.castIp6ToIp4(ip6));
//
//    }
//
//    /**
//     * 测试类型转换的异常，经常出现的问题。
//     */
//    @Test
//    public void testE() {
//        Object aa = 11;
//        String ss = String.valueOf(aa);
//
//
//        // 下面这行代码异常了，一眼看出来没？
//        ss = (String) aa;
//        System.out.println(ss);
//    }
//
//    /**
//     * 把IP6的a-d的范围给转成a|b|c|d
//     */
//    @Test
//    public void tt() {
//        String condition = "1.1.1.1|2.2.2.2|7654:0:ffff:7654:562:222:ab:ab0-7654:0:ffff:7654:562:222:ab:ab2";
//
//        String[] values = condition.split("\\|");
//        boolean flag = false;
//        List<String> list = Lists.newArrayList();
//        for (String value : values) {
//            if (value.contains(COLON) && value.contains("-")) {
//                flag = true;
//                list.addAll(turnIps(value));
//            } else {
//                list.add(value);
//            }
//        }
//
//        if (flag) {
//            condition = Joiner.on("|").join(list);
//            System.out.println(condition);
//        }
//    }
//
//    private List<String> turnIps(String value) {
//        String[] split = value.split("-");
//        String ip60 = split[0];
//        String ip61 = split[1];
//        long l1 = Tools.castIp6String2Long(ip60);
//        long l2 = Tools.castIp6String2Long(ip61);
//        String prefix0 = getPrefix(ip60);
//        String prefix1 = getPrefix(ip61);
//        System.out.println(prefix0.equalsIgnoreCase(prefix1));
//        List<String> ips = Lists.newArrayList();
//        while (l1 <= l2) {
//            String s = prefix0 + cast2Ip6(l1);
//            ips.add(s);
//            l1++;
//        }
//        return ips;
//    }
//
//    private String getPrefix(String ip6) {
//        String[] split1 = ip6.split(":");
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < 6; i++) {
//            stringBuilder.append(split1[i]).append(":");
//        }
//        return stringBuilder.toString();
//    }
//
//    @Test
//    public void ip6Range() {
//        String ip6s = "7654:0:ffff:7654:562:222:ab:ab0-7654:0:ffff:7654:562:222:ab:abf";
//        String[] split = ip6s.split("-");
//        String ip60 = split[0];
//        String ip61 = split[1];
//        long l1 = Tools.castIp6String2Long(ip60);
//        long l2 = Tools.castIp6String2Long(ip61);
//        System.out.println(l1);
//        System.out.println(l2);
//        // 16711680 16711695
//
//        String[] split1 = ip60.split(":");
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < 6; i++) {
//            stringBuilder.append(split1[i]).append(":");
//        }
//
//        String prefixIp6 = stringBuilder.toString();
//
//        System.out.println(prefixIp6);
//
//        List<String> ips = Lists.newArrayList();
//        while (l1 <= l2) {
//            String s = prefixIp6 + cast2Ip6(l1);
//            ips.add(s);
//            l1++;
//        }
//        ips.forEach(System.out::println);
//
//    }
//
//    private String cast2Ip6(long longNum) {
//        String hexString = Long.toHexString(longNum);
//        String before;
//        String substring;
//        int length = hexString.length();
//        if (length <= 4) {
//            substring = hexString;
//            before = "0";
//        } else {
//            substring = hexString.substring(length - 4);
//            before = hexString.substring(0, length - 4);
//        }
//        String aLong = Long.toHexString(Long.parseLong(substring, 16));
//        return before + ":" + aLong;
//    }
//
//    /**
//     * 16711680
//     * 16711695
//     */
//    @Test
//    public void ip() {
//        //8个1
//        System.out.println(Integer.toBinaryString(16711680));
//        // 一个f是4个1
//        System.out.println(Integer.toBinaryString(15));
//
//        System.out.println(Integer.toHexString(15));
//        System.out.println(Integer.toHexString(16711680));
//        System.out.println();
//        String string = Long.toHexString(-4294967296L);
//        System.out.println(string);
//
//        String substring = string.substring(string.length() - 4);
//        System.out.println(substring);
//
//        List<String> ips = Lists.newArrayList();
//        ips.addAll(Lists.newArrayList());
//
//    }
//
//
//    /**
//     * 下面几种形式的IP6都是合法的正确的IP地址
//     */
//    @Test
//    public void isRightIp6() {
//        List<String> ip6s = Lists.newArrayList("1::1",
//                "7654:0:ffff:7654:562:222:ab:ab2",
//                "7654:00:ffff:7654:562:222:ab:ab2",
//                "7654:000:ffff:7654:562:222:ab:ab2",
//                "7654:0000:ffff:7654:562:222:ab:ab2",
//                "::1",
//                "2001:0db8:85a3:000:0:8A2E::q");
//        //是否是正确的IP6
//        ip6s.forEach(s -> System.out.println(IPAddressUtil.isIPv6LiteralAddress(s)));
//
//
//        //转成byte数组，一个byte是8个二进制位，一个IP6转成数组之后，数组length = 16，总位 128 = 8 * 16
//        ip6s.forEach(s -> System.out.println(Arrays.toString(IPAddressUtil.textToNumericFormatV6(s))));
//
//
//    }
//
//
//    /**
//     * 0:0:0:0:0:0:0:1 这个IP6 目测应该转成[0, 1]的，结果却是 [0, 281474976710656]  10000 00000000
//     * 最终结果是，之前的tools中的代码有问题，经过修正后就好了。达到预期了。
//     */
//    @Test
//    public void ip6ToLongArray() {
//        String ip6 = "0:0:0:4:0:0:0:4";
//        // 期望 【4，4】，结果也是【4，4】
//        System.out.println(Arrays.toString(Tools.ip2Longs(ip6)));
//    }
//
//    /**
//     * （之前的错误代码）将 IPv6 地址转为 long 数组，只支持冒分十六进制表示法
//     */
//    public static long[] ip2Longs(String ipString) {
//        if (ipString == null || ipString.isEmpty()) {
//            throw new IllegalArgumentException("ipString cannot be null.");
//        }
//        String[] ipSlices = ipString.split(":");
//        if (ipSlices.length != IP_6_LENGTH) {
//            throw new IllegalArgumentException(ipString + " is not an ipv6 address.");
//        }
//        long[] ipv6 = new long[2];
//        for (int i = 0; i < IP_6_LENGTH; i++) {
//            String slice = ipSlices[i];
//            // 以 16 进制解析
//            long num = Long.parseLong(slice, 16);
//            // 每组 16 位
//            long right = num << (16 * (IP_6_LENGTH - i - 1));
//            // 每个 long 保存四组，i >> 2 = i / 4 ，i对4取余，其实就是前4个在数组0下标位置，后面4个在下标1位置。
//            ipv6[i >> 2] |= right;
//        }
//        return ipv6;
//    }
//
//    @Test
//    public void testError(){
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:0:0:0:0:0")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:0:0:0:0:1")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:0:0:0:0:2")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:0:0:0:0:3")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:0:0:0:0:4")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:0:0:0:0:5")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:1:0:0:0:5")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:2:0:0:0:5")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:3:0:0:0:5")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:4:0:0:0:5")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:5:0:0:0:5")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:f:0:0:0:F")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:10:0:0:0:F")));
//        System.out.println(Arrays.toString(ip2Longs("0:0:0:0:0:0:0:f")));
//    }
//
//    /**
//     * 数组转IP6
//     */
//    @Test
//    public void longArrayToIp6() {
//        String s = Tools.longs2Ip(new long[]{0, 4});
//        System.out.println(s);
//    }
//
//    /**
//     * （之前错误的代码，转成IP之后，每个都是前后反转了，比如应该是 1:2:3:4:a:b:c:d,结果却是 4:3:2:1:d:c:b:a）
//     * 将 long 数组转为冒分十六进制表示法的 IPv6 地址
//     */
//    public static String longs2Ip(long[] numbers) {
//        if (numbers == null || numbers.length != 2) {
//            throw new IllegalArgumentException(Arrays.toString(numbers) + " is not an IPv6 address.");
//        }
//        // 32 也不对呀，虽然是32个字符，但是还有 7个冒号呢
//        StringBuilder sb = new StringBuilder(32);
//        for (long numSlice : numbers) {
//            // 每个 long 保存四组
//            for (int j = 0; j < 4; j++) {
//                // 取最后 16 位
//                long current = numSlice & 0xFFFF;
//                sb.append(Long.toString(current, 16)).append(":");
//                numSlice >>= 16;
//            }
//        }
//        // 去掉最后的 :
//        return sb.substring(0, sb.length() - 1);
//    }
//
//
//    @Test
//    public void last() {
//        turn("0:0:0:0:0:0:0:0");
//        turn("0:0:0:0:0:0:0:1");
//        turn("0:0:0:0:0:0:0:2");
//        turn("0:0:0:0:0:0:0:3");
//        turn("0:0:0:0:0:0:0:4");
//        turn("0:0:0:0:0:0:0:5");
//        turn("0:0:0:1:0:0:0:5");
//        turn("0:0:0:2:0:0:0:5");
//        turn("0:0:0:3:0:0:0:5");
//        turn("0:0:0:4:0:0:0:5");
//        turn("0:0:0:5:0:0:0:5");
//        turn("0:0:0:f:0:0:0:F");
//        turn("0:0:0:10:0:0:0:F");
//    }
//
//    private void turn(String ip6) {
//        long[] longs = Tools.ip2Longs(ip6);
//        System.out.println(Arrays.toString(longs));
//        String s = Tools.longs2Ip(longs);
//        System.out.println(s);
//    }
//
//
//}
