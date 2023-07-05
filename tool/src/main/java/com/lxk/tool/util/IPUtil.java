package com.lxk.tool.util;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author LiXuekai on 2023/7/5
 */
public class IPUtil {
    private final static int INADDR4SZ = 4;
    private static final int IP_6_LENGTH = 8;
    private static final String STRING_SPLIT = ",";
    private static final String STRING_SPLIT_2 = ":";


    public static String longToIP(Long ip) {
        if (ip == null) {
            return null;
        }
        long a = ip % 256;
        long b = (ip -= a) >> 24;
        long c = (ip -= b << 24) >> 16;
        long d = ip - (c << 16) >> 8;
        return b + "." + c + "." + d + "." + a;
    }

    public static int getIpInt(InetAddress ip) {
        return getIpInt(ip.getAddress());
    }

    public static long getIpLong(InetAddress ip) {
        return getIpLong(ip.getAddress());
    }

    public static int getIpInt(byte[] ipAddr) {
        return ((ipAddr[0] << 24) & 0xFF000000) | ((ipAddr[1] << 16) & 0xFF0000)
                | ((ipAddr[2] << 8) & 0xFF00) | (ipAddr[3] & 0xFF);
    }

    public static long getIpLong(byte[] ipAddr) {
        return 0xFFFFFFFFL & getIpInt(ipAddr);
    }

    /**
     * ip 变成对应的32位的int的数字
     */
    public static long getIpLong(String ip) throws UnknownHostException {
        InetAddress ipAddr = InetAddress.getByAddress(textToNumericFormatV4(ip));
        return 0xFFFFFFFFL & getIpInt(ipAddr);
    }

    /**
     * 把字符串形式的IP地址转成四个数字的数字(不是int，是byte类型的) 1.1.1.1 -> [1,1,1,1]
     *
     * @param ipv4 IPv4 的字符串形式的IP
     * @return a byte array representing the IPv4 numeric address
     */
    public static byte[] textToNumericFormatV4(String ipv4) {
        if (ipv4.length() == 0) {
            return null;
        }

        byte[] res = new byte[INADDR4SZ];
        String[] s = ipv4.split("\\.", -1);
        long val;
        try {
            switch(s.length) {
                case 4:
                    /*
                     * When four parts are specified, each is interpreted as a
                     * byte of data and assigned, from left to right, to the
                     * four bytes of an IPv4 address.
                     */
                    for (int i = 0; i < 4; i++) {
                        val = Integer.parseInt(s[i]);
                        if (val < 0 || val > 0xff) {
                            return null;
                        }
                        res[i] = (byte) (val & 0xff);
                    }
                    break;
                default:
                    return null;
            }
        } catch(NumberFormatException e) {
            return null;
        }
        return res;
    }

    public static InetAddress getIPAddress(long ip) throws UnknownHostException {
        byte[] a = new byte[4];
        a[0] = (byte)(ip >> 24);
        a[1] = (byte)(ip >> 16);
        a[2] = (byte)(ip >> 8);
        a[3] = (byte)ip;
        return InetAddress.getByAddress(a);
    }

    /**
     * 将带有*号的ip转换为带有掩码的ip，比如 192.168.1.* 转换为  192.168.1.0/24
     * @param ipAddress
     * @return
     */
    public static String getWildcardIpMask(String ipAddress) throws Exception {
        String[] ips = ipAddress.split("\\.");
        if (ips.length != 4) {
            throw new Exception("IP error:" + ipAddress);
        }
        int index = 0;
        for (; index < ips.length; index++) {
            if (!"*".equals(ips[3 - index])) {
                break;
            }
        }
        String ip = ipAddress.replace('*', '0') + "/" + (8 * (ips.length - index));
        for (int i = index + 1; i < ips.length; i++) {
            if ("*".equals(ips[3 - i])) {
                throw new Exception("IP error:" + ipAddress);
            }
        }
        return ip;
    }

    /**
     * ip的long类型数字转成对应的IP字符串
     *
     * @param ipLong ip的数字，可能IPv4的IP的long 也可能是IPv6的2个long的数字的字符串
     * @return 转成对应的IPv4或者IPv6
     */
    public static String ipLongStringToIP(String ipLong) {
        String ip;
        //IPv6的数字
        if (ipLong.contains(STRING_SPLIT)) {
            long[] longs = new long[2];
            String[] split = ipLong.split(STRING_SPLIT);
            longs[0] = Long.parseLong(split[0]);
            longs[1] = Long.parseLong(split[1]);
            ip = longs2Ip(longs);
        } else {
            ip = longToIP(Long.parseLong(ipLong));
        }
        return ip;
    }


    /**
     * IPv4 本来就能转成一个int，对于IPv6太长，截取某几位来凑个IPv4
     * @param ip6 IPv6
     * @return IP的数字
     */
    public static long castIp6String2Long(String ip6){
        String[] split = ip6.split(STRING_SPLIT_2);
        for (int i = 0; i < split.length; i++) {
            split[i] = concat(split[i]);
        }
        //IPv6的字符串IP
        return Long.parseLong(split[6] + split[7], 16);
    }

    public static String castIp6ToIp4(String ip6) {
        String[] split = ip6.split(STRING_SPLIT_2);
        for (int i = 0; i < split.length; i++) {
            split[i] = concat(split[i]);
        }
        //IPv6的字符串IP
        long l = Long.parseLong(split[6] + split[7], 16);
        return longToIP(l);
    }
    /**
     * 把不够四位的IPv6的，给补齐到4位
     */
    public static String concat(String s) {
        if (Strings.isNullOrEmpty(s)) {
            return "0000";
        }
        if (s.length() == 4) {
            return s;
        }
        StringBuilder builder = new StringBuilder(s);
        while (builder.length() < 4) {
            builder.insert(0, "0");
        }
        s = builder.toString();
        return s;
    }

    /**
     * 将 IPv6 地址转为 long 数组，只支持冒分十六进制表示法
     */
    public static long[] ip2Longs(String ipString) {
        if (ipString == null || ipString.isEmpty()) {
            throw new IllegalArgumentException("ipString cannot be null.");
        }
        String[] ipSlices = ipString.split(STRING_SPLIT_2);
        if (ipSlices.length != IP_6_LENGTH) {
            throw new IllegalArgumentException(ipString + " is not an ipv6 address.");
        }
        long[] ipv6 = new long[2];

        for (int i = 0; i < IP_6_LENGTH; i++) {
            String slice = ipSlices[i];
            // 以 16 进制解析
            long num = Long.parseLong(slice, 16);
            // 每组 16 位
            long right = num << (16 * (IP_6_LENGTH - i - 1));
            // 每个 long 保存四组，i >> 2 = i / 4 ，i对4取余，其实就是前4个在数组0下标位置，后面4个在下标1位置。
            ipv6[i >> 2] |= right;
        }
        return ipv6;
    }

    /**
     * 将 long 数组转为冒分十六进制表示法的 IPv6 地址
     */
    public static String longs2Ip(long[] numbers) {
        if (numbers == null || numbers.length != 2) {
            throw new IllegalArgumentException(Arrays.toString(numbers) + " is not an IPv6 address.");
        }
        Joiner joiner = Joiner.on(STRING_SPLIT_2);
        StringBuilder sb = new StringBuilder();
        for (long numSlice : numbers) {
            String[] ip = new String[4];
            for (int j = 0; j < 4; j++) {
                // 取最后 16 位
                long current = numSlice & 0xFFFF;
                ip[3 - j] = Long.toString(current, 16);
                // 右移 16 位，即去除掉已经处理过的 16 位
                numSlice >>= 16;
            }
            sb.append(joiner.join(ip)).append(STRING_SPLIT_2);
        }
        // 去掉最后的 :
        return sb.substring(0, sb.length() - 1);
    }
}
