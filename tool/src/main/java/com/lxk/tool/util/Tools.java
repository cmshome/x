package com.lxk.tool.util; /**
 * Copyright 2010 Lennart Koopmann <lennart@socketfeed.com>
 * 
 * This file is part of Graylog2.
 *
 * Graylog2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog2.  If not, see <http://www.gnu.org/licenses/>.
 *
 */


import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * Utilty class for various tool/helper functions.
 *
 * @author Lennart Koopmann <lennart@socketfeed.com>
 */
public final class Tools {


    private static final int SECONDS_IN_MINUTE = 60;
    private static final int SECONDS_IN_5_MINUTE = SECONDS_IN_MINUTE * 5;
    private static final int SECONDS_IN_HOUR = SECONDS_IN_MINUTE * 60;
    private static final int SECONDS_IN_DAY = SECONDS_IN_HOUR * 24;
    private static final int DAYS_IN_WEEK = 7;
    private static final int IP_6_LENGTH = 8;
    private static final String STRING_SPLIT = ",";
    private static final String STRING_SPLIT_2 = ":";
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMATTER_MINUTE = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat DATE_FORMATTER_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private static final int NUMBER_0 = 0;
    private static final int NUMBER_2 = 2;
    private static final int NUMBER_3 = 3;
    private static final int NUMBER_4 = 4;
    private static final int NUMBER_8 = 8;
    private static final int NUMBER_16 = 16;

    
    private Tools() { }

    /**
     * Get the own PID of this process.
     *
     * @return PID of the running process
     */
    public static String getPID() {
        return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }

    /**
     * Converts integer syslog loglevel to human readable string
     *
     * @param level The level to convert
     * @return The human readable level
     */
    public static String syslogLevelToReadable(int level) {
        switch (level) {
            case 0:
                return "Emergency";
            case 1:
                return "Alert";
            case 2:
                return "Critical";
            case 3:
                return "Error";
            case 4:
                return "Warning";
            case 5:
                return "Notice";
            case 6:
                return "Informational";
            case 7:
                return "Debug";
            default:
        }

        return "Invalid";
    }

    /**
     * Converts integer syslog facility to human readable string
     *
     * @param facility The facility to convert
     * @return The human readable facility
     */
    public static String syslogFacilityToReadable(int facility) {
        switch (facility) {
            case 0:  return "kernel";
            case 1:  return "user-level";
            case 2:  return "mail";
            case 3:  return "system daemon";
            case 4: case 10: return "security/authorization";
            case 5:  return "syslogd";
            case 6:  return "line printer";
            case 7:  return "network news";
            case 8:  return "UUCP";
            case 9: case 15: return "clock";
            case 11: return "FTP";
            case 12: return "NTP";
            case 13: return "log audit";
            case 14: return "log alert";

            // TODO: Make user definable?
            case 16: return "local0";
            case 17: return "local1";
            case 18: return "local2";
            case 19: return "local3";
            case 20: return "local4";
            case 21: return "local5";
            case 22: return "local6";
            case 23: return "local7";
            default:
        }

        return "Unknown";
    }

    /**
     * Get a String containing version information of JRE, OS, ...
     * @return Descriptive string of JRE and OS
     */
    public static String getSystemInformation() {
        String ret = System.getProperty("java.vendor");
        ret += " " + System.getProperty("java.version");
        ret += " on " + System.getProperty("os.name");
        ret += " " + System.getProperty("os.version");
        return ret;
    }


    /**
     * Decompress ZLIB (RFC 1950) compressed data
     *
     * @return A string containing the decompressed data
     */
    public static String decompressZlib(byte[] compressedData) throws IOException {
        byte[] buffer = new byte[compressedData.length];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(compressedData));
        for (int bytesRead = 0; bytesRead != -1; bytesRead = in.read(buffer)) {
            out.write(buffer, 0, bytesRead);
        }
        return new String(out.toByteArray(), "UTF-8");
    }

    /**
     * Decompress GZIP (RFC 1952) compressed data
     * 
     * @return A string containing the decompressed data
     */
    public static String decompressGzip(byte[] compressedData) throws IOException {
        byte[] buffer = new byte[compressedData.length];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(compressedData));
        for (int bytesRead = 0; bytesRead != -1; bytesRead = in.read(buffer)) {
            out.write(buffer, 0, bytesRead);
        }
        return new String(out.toByteArray(), "UTF-8");
    }

    /**
     *
     * @return The current UTC UNIX timestamp.
     */
    public static int getUTCTimestamp() {
       return (int) (System.currentTimeMillis()/1000);
    }

    /**
     * Get the current UNIX epoch with milliseconds of the system
     *
     * @return The current UTC UNIX timestamp with milliseconds.
     */
    public static double getUTCTimestampWithMilliseconds() {
        return getUTCTimestampWithMilliseconds(System.currentTimeMillis());
    }

    /**
     * Get the UNIX epoch with milliseconds of the provided millisecond timestamp
     *
     * @param timestamp a millisecond timestamp (milliseconds since UNIX epoch)
     * @return The current UTC UNIX timestamp with milliseconds.
     */
    public static double getUTCTimestampWithMilliseconds(long timestamp) {
        return timestamp / 1000.0;
    }
    
    public static String getLocalHostname() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            return "Unknown";
        }

        return addr.getHostName();
    }
    
    public static String getLocalCanonicalHostname() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            return "Unknown";
        }

        return addr.getCanonicalHostName();
    }

    public static int getTimestampDaysAgo(int ts, int days) {
        return (ts - (days*86400));
    }

    public static String rdnsLookup(InetAddress socketAddress) throws UnknownHostException {
        return socketAddress.getCanonicalHostName();
    }
    
    public static String generateServerId() {
        UUID id = UUID.randomUUID();
        return getLocalHostname() + "-" + id.toString();
    }
    
    public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        Collections.sort(list);
        return list;
    }
    
    public static String buildElasticSearchTimeFormat(double timestamp) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis((long) (1000 * timestamp));
        return String.format("%1$tY-%1$tm-%1$td %1$tH-%1$tM-%1$tS", cal);
    }


    /**
     * 获取当前时刻的整点分钟数的Timestamp
     */
    public static int getUTCTimestampByMinute()
    {
    	int ret = getUTCTimestamp();
    	ret -= ret % Tools.SECONDS_IN_MINUTE;
    	return ret;
    }
    /**
     * 获取当前时间的整数分钟时间戳
     */
    public static int getUTCTimestampByMinute(int timestamp)
    {
 	   return timestamp - timestamp % Tools.SECONDS_IN_MINUTE;
    }
    
    /**
    *
    * 获取当前时刻在当天的分钟数
    * @return minute of day.
    */
   public static int getMinuteOfDay(Calendar cal) {
   	if (cal == null) {
        cal = Calendar.getInstance();
    }
   	return (int) (cal.get(Calendar.HOUR_OF_DAY)*60 + cal.get(Calendar.MINUTE));
   }

    /**
     * 获取当前时刻在当前周的第几天
     */
    public static int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前时刻在当天的秒数
     */
    public static int getSecondsOfDay(Calendar cal) {
        if (cal == null) {
            cal = Calendar.getInstance();
        }
        return getMinuteOfDay(cal) * 60 + cal.get(Calendar.SECOND);
    }

    /**
     * 获取时间戳的格式化字符串
     */
    public static String getFormatedDateTime(long datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(datetime * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
        return simpleDateFormat.format(cal.getTime());
    }
   
   /**
    * 当前时间戳
    */
   public static int now()
   {
   	return getUTCTimestamp();
   }
   
	public static String getMD5(String string) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
		}
		messageDigest.update(string.getBytes());
		byte[] bytes = messageDigest.digest();
		StringBuilder stringBuffer = new StringBuilder();
		int b;
		for (int i = 0; i < bytes.length; i++) {
			b = bytes[i];
			if (b < 0) {
				b += 256;
			}
			if (b < 16) {
				stringBuffer.append(0);
			}
			stringBuffer.append(Integer.toHexString(b));
		}
		return stringBuffer.toString();
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
	
	public static InetAddress getIPAddress(long ip) throws UnknownHostException {
		byte[] a = new byte[4];
		a[0] = (byte)(ip >> 24);
		a[1] = (byte)(ip >> 16);
		a[2] = (byte)(ip >> 8);
		a[3] = (byte)ip;
		return InetAddress.getByAddress(a);
	}

//	public static int getWildcardIpInt(String ip_address) {
//		String[] ips = ip_address.split("\\.");
//		byte[] ipByte = new byte[4];
//		for (int i = 0; i < ipByte.length; i++) {
//			if (ips[i].equals("*"))
//				ipByte[i] = (byte) 0xFF;
//			else
//				ipByte[i] = (byte) Integer.parseInt(ips[i]);
//		}
//		System.out.println(ipByte[0] << 24);
//		System.out.println(ipByte[1] << 16);
//		System.out.println(ipByte[2] << 8);
//		System.out.println(ipByte[3]);
//		return ((ipByte[0] << 24) & 0xFF000000) | ((ipByte[1] << 16) & 0xFF0000)
//				| ((ipByte[2] << 8) & 0xFF00) | (ipByte[3] & 0xFF);
//	}
	
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
	
	public static int getRangeTimeByTime(int start, int interval) {
		return start / interval * interval;
	}
	
	public static int getIndex(String str, char ch, int from, char s, char e) {
		int flag = 0;
		for (int i = from; i < str.length(); i++) {
			if (flag == 0 && str.charAt(i) == ch) {
                return i;
            }
			if (str.charAt(i) == s) {
                flag++;
            } else if (str.charAt(i) == e) {
                flag--;
            }
		}
		return -1;
	}
	
	private final static int INADDR4SZ = 4;

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

	public static String getKey(Object... o) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < o.length; i++) {
			if (i != 0) {
                buffer.append('|');
            }
			buffer.append(o[i]);
		}
		return buffer.toString();
	}

    public static long getHash(String text) {
        HashFunction gfh = Hashing.murmur3_128();
        return gfh.newHasher().putString(text, Charsets.UTF_8).hash().asLong();
    }

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
        if (numbers == null || numbers.length != NUMBER_2) {
            throw new IllegalArgumentException(Arrays.toString(numbers) + " is not an IPv6 address.");
        }
        Joiner joiner = Joiner.on(STRING_SPLIT_2);
        StringBuilder sb = new StringBuilder();
        for (long numSlice : numbers) {
            String[] ip = new String[4];
            for (int j = 0; j < NUMBER_4; j++) {
                // 取最后 16 位
                long current = numSlice & 0xFFFF;
                ip[NUMBER_3 - j] = Long.toString(current, 16);
                // 右移 16 位，即去除掉已经处理过的 16 位
                numSlice >>= 16;
            }
            sb.append(joiner.join(ip)).append(STRING_SPLIT_2);
        }
        // 去掉最后的 :
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 使用jdk的hash会快点。
     */
    public static int getHashFast(String text) {
        return Objects.hash(text);
    }

	public static void main(String[] args) throws UnsupportedEncodingException {
		try {
			System.out.println(getWildcardIpMask("192.168.1.1"));
			System.out.println(getWildcardIpMask("192.168.1.*"));
			System.out.println(getWildcardIpMask("192.168.*.*"));
			System.out.println(getWildcardIpMask("192.*.*.*"));
			System.out.println(getWildcardIpMask("*.*.*.*"));
			System.out.println(getWildcardIpMask("192.*.1.*"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
}
