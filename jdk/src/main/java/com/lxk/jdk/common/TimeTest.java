package com.lxk.jdk.common;


import com.lxk.bean.model.TimeFormatModel;
import com.lxk.tool.util.TimeUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 时间相关的测试
 * <p>
 *
 * @author lxk on 2017/2/22
 */
public class TimeTest {


    /**
     * 1736327329
     */
    @Test
    public void timeUnixNano() {
        // 纳秒
        String s = "1736318760592209989";
        long l = Long.parseLong(s);
        System.out.println(l);
        long l1 = l / 1000_000L;
        System.out.println(l1);
        System.out.println(TimeUtils.formatMs(l1));

        String ms = s.substring(0, 13);
        System.out.println(ms);
    }

    /**
     * 对给的秒数取整分
     */
    @Test
    public void getIntegerMinute() {
        long now = 1551945564L;
        long yes = now / 60 * 60;
        System.out.println(now);
        System.out.println(yes);
        System.out.println(yes + 60);
    }

    /**
     * 编辑器检测代码，使用推荐方式初始化时间
     * 运行结果没啥不一样啊
     */
    @Test
    public void testGetTime() {
        // Positive example:
        long a = System.currentTimeMillis();
        // Negative example:
        long b = new Date().getTime();
        System.out.println("Positive example：" + a);
        System.out.println("Negative example：" + b);
    }

    /**
     * 时间戳 -> 格式化的时间字符串
     */
    @Test
    public void turnLongToDate() {
        Date ping = new Date(1506743427564L);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(ping));
    }

    /**
     * 字符串 -> 时间
     */
    @Test
    public void testStringToDate() {
        String s = "2017-05-25";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(date);
    }

    /**
     * 查看 Calendar 获得年月日时分秒
     */
    @Test
    public void testCalendar() {
        //1490084570

        //1490084570000L
        //1506582060292
        Date ping = new Date(1490084570000L);
        Calendar cal = Calendar.getInstance();
        TimeFormatModel t = new TimeFormatModel();
        t.setDate(cal.getTime());
        System.out.println(t.toString());
        t.setDate(ping);
        System.out.println(t.toString());
        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.get(Calendar.MONTH));
        System.out.println(cal.get(Calendar.DAY_OF_MONTH) + 1);
        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
        System.out.println(cal.get(Calendar.MINUTE));
        System.out.println(cal.get(Calendar.SECOND));

    }

    /**
     * 将Date类型的时间转成 2016-10-25 18:11:55 此格式
     * 时间戳转换为 2016-10-25 18:11:55 此格式
     *
     * @param ss Date类型的时间
     */
    private static void testTime(Date ss) {
        //打印一般的Date类型的时间
        System.out.println(ss);
        //这个是把当前时间转换成秒数，即时间戳。
        System.out.println(ss.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //这个就是把时间戳经过处理得到期望格式的时间
        String time = format.format(ss.getTime());
        System.out.println(time);
    }


    /**
     * 当用int类型来表示当前时间的秒的时候，最多能表达的时间是：2038-01-19 11:14:07。
     */
    @Test
    public void maxInt() {
        System.out.println(Integer.MAX_VALUE);
        int max = 2147483647;
        String s = TimeUtils.formatS(max);
        System.out.println(s);

    }

    @Test
    public void fileLineSeparator() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(TimeUtils.formatUs(now));
        System.out.println(TimeUtils.formatNs(now));
        System.out.println(now.getNano());
    }

    @Test
    public void random() {
        int span = 15;
        long a = TimeUtils.nowS() / span * span;
        String format = TimeUtils.formatS(a);
        System.out.println(format);
        int max = 6;
        for (int i = 0; i < max; i++) {
            int s = new Random().nextInt(span);
            int ms = new Random().nextInt(1000);
            long nowS = a + s;
            long nowMs = nowS * 1000 + ms;
            int ns = ms * 1000000 + new Random().nextInt(1000000);
            LocalDateTime localDateTime = TimeUtils.ms2LocalDateTime(nowMs).withNano(ns);
            System.out.println(TimeUtils.formatS(nowS) + "   " + TimeUtils.formatMs(nowMs) + "   " + TimeUtils.formatNs(localDateTime));
        }
    }


    /**
     * jprofiler 时间占比
     * instant          12.5
     * sys              13.1
     * localDate        19.5
     * localDateTime    20.9
     * calendar         33.6
     */
    @Test
    public void fast() {
        while (true) {
            instant();
            localDateTime();
            localDate();
            calendar();
            sys();
        }
    }

    private void sys() {
        long millis = System.currentTimeMillis();
    }

    private void calendar() {
        Date date = Calendar.getInstance().getTime();
    }

    private void localDate() {
        LocalDate localDate = LocalDate.now();
    }

    private void localDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
    }

    private void instant() {
        Instant instant = Instant.now();
    }
}
