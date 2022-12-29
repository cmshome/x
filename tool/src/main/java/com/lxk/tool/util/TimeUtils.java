package com.lxk.tool.util;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间相关的工具。
 *
 * @author LiXuekai on 2021/1/27
 */
public final class TimeUtils {
    private static final DateTimeFormatter DATE_TIME_FORMATTER_SSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_S_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final ZoneId ZONE_ID = ZoneOffset.systemDefault();
    private static final String EMPTY = "";
    private static final int ZERO = 0;
    private static final long K = 1000L;
    /**
     * 8小时的秒数
     */
    private static final int OFFSET = 8 * 60 * 60;


    /**
     * turn 字符串 to LocalDateTime
     *
     * @param text 形如：2022-11-10 20:00:00
     * @return LocalDateTime
     */
    public static LocalDateTime parse(String text) {
        try {
            return LocalDateTime.parse(text, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取格式化后的时间：年月日时分秒
     *
     * @return 格式化后的时间
     */
    public static String now() {
        return format(LocalDateTime.now());
    }

    /**
     * 获取当前时间的秒数
     *
     * @return 当前时间戳 秒
     */
    public static long nowS() {
        return nowMs() / K;
    }

    /**
     * 获取当前时间的毫秒数
     *
     * @return 当前时间戳毫秒
     */
    public static long nowMs() {
        return System.currentTimeMillis();
    }

    /**
     * 默认格式化时间格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param localDateTime localDateTime
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format(LocalDateTime localDateTime) {
        return DATE_TIME_FORMATTER.format(localDateTime);
    }

    /**
     * 默认格式化时间格式为：yyyy-MM-dd
     *
     * @param localDate localDate
     * @return yyyy-MM-dd
     */
    public static String format(LocalDate localDate) {
        return DATE_FORMATTER.format(localDate);
    }

    /**
     * 默认格式化时间格式为：HH:mm
     *
     * @param localTime localDate
     * @return HH:mm
     */
    public static String format(LocalTime localTime) {
        return TIME_FORMATTER.format(localTime);
    }

    /**
     * 默认格式化时间格式为：HH:mm:ss
     *
     * @param localTime localDate
     * @return HH:mm:ss
     */
    public static String formatS(LocalTime localTime) {
        return TIME_S_FORMATTER.format(localTime);
    }

    /**
     * 格式化时间戳
     *
     * @param s 秒
     * @return 格式化后的时间
     */
    public static String formatS(long s) {
        try {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(s * K), ZONE_ID);
            return formatS(localDateTime);
        } catch (Exception ignore) {
        }
        return EMPTY;
    }

    /**
     * 格式化 localDateTime
     *
     * @param localDateTime localDateTime
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatS(LocalDateTime localDateTime) {
        return DATE_TIME_FORMATTER.format(localDateTime);
    }

    /**
     * 格式化时间戳
     *
     * @param ms 毫秒
     * @return 格式化后的时间
     */
    public static String formatMs(long ms) {
        try {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(ms), ZONE_ID);
            return formatMs(localDateTime);
        } catch (Exception ignore) {
        }
        return EMPTY;
    }

    /**
     * 格式化 localDateTime
     *
     * @param localDateTime localDateTime
     * @return yyyy-MM-dd HH:mm:ss:SSS
     */
    public static String formatMs(LocalDateTime localDateTime) {
        return DATE_TIME_FORMATTER_SSS.format(localDateTime);
    }

    /**
     * 秒 -> LocalDateTime
     *
     * @param s 秒
     * @return LocalDateTime
     */
    public static LocalDateTime s2LocalDateTime(long s) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(s), ZONE_ID);
    }

    /**
     * 毫秒 -> LocalDateTime
     *
     * @param ms 毫秒
     * @return LocalDateTime
     */
    public static LocalDateTime ms2LocalDateTime(long ms) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(ms), ZONE_ID);
    }

    /**
     * LocalDateTime -> 秒
     *
     * @param localDateTime localDateTime
     * @return 秒
     */
    public static long toS(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZONE_ID).toEpochSecond();
    }

    /**
     * LocalDateTime -> 毫秒
     * 竟然加了8小时
     *
     * @param localDateTime localDateTime
     * @return 毫秒
     */
    public static long toMs(LocalDateTime localDateTime) {
        // 比标准实际慢8小时，就是当前的时间了。
        return localDateTime.toInstant(ZoneOffset.ofTotalSeconds(OFFSET)).toEpochMilli();
    }

    /**
     * Date -> LocalDateTime
     *
     * @param date date
     * @return localDateTime
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalDateTime();
    }

    /**
     * 当天0点的秒
     *
     * @return 当前0点的时间戳秒
     */
    public static Long dayStartSecond() {
        return dayStartSecond(LocalDateTime.now());
    }

    /**
     * 置传入时间为整0点时间戳秒
     *
     * @param localDateTime LocalDateTime
     * @return 传入时间置为整0点时间戳秒
     */
    public static Long dayStartSecond(LocalDateTime localDateTime) {
        return localDateTime.withHour(ZERO).withMinute(ZERO).withSecond(ZERO).withNano(ZERO).atZone(ZONE_ID).toEpochSecond();
    }

    /**
     * 计算两个时间点之间的天数
     *
     * @return 2个时间间隔的天数
     */
    public static long betweenDay(LocalDate start, LocalDate end) {
        return end.toEpochDay() - start.toEpochDay();
    }

    /**
     * 计算两个时间点之间的天数
     *
     * @return 2个时间间隔的天数
     */
    public static long betweenYear(LocalDate start, LocalDate end) {
        return end.getYear() - start.getYear();
    }

    /**
     * 格式化2个时间的间隔
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 两个时间之间的间隔
     */
    public static String between(LocalDate start, LocalDate end) {
        long betweenDay = end.toEpochDay() - start.toEpochDay();
        if (betweenDay < 0) {
            return "开始时间大于结束时间，所传参数不OK。。。。";
        }

        int year = end.getYear() - start.getYear();
        int month = end.getMonth().getValue() - start.getMonth().getValue();
        int day = end.getDayOfMonth() - start.getDayOfMonth();

        if (day < 0) {
            month = month - 1;
            boolean leapYear = end.getYear() % 4 == 0;
            if (end.getMonth() == Month.JANUARY) {
                leapYear = (end.getYear() - 1) % 4 == 0;
            }
            day = day + end.getMonth().minus(1).length(leapYear);
        }

        if (month < 0) {
            year = year - 1;
            month = month + 12;
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (year > 0) {
            stringBuilder.append(year).append(" 年 ");
        }
        if (month > 0) {
            stringBuilder.append(month).append(" 个月 ");
        }
        if (day > 0) {
            stringBuilder.append(day).append(" 天");
        }
        return stringBuilder.toString();
    }

    /**
     * 当前时间戳 加/减 n天之后的时间戳
     *
     * @param second 时间，秒数
     * @param day    天数
     * @return 时间戳
     */
    static Long plusDays(Long second, int day) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(second), ZONE_ID).plusDays(day).atZone(ZONE_ID).toEpochSecond();
    }
}
