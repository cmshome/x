package com.lxk.jdk.format;


import com.lxk.tool.util.TimeUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式化测试
 *
 * @author lxk on 2016/11/4
 */
public class DateFormat {

    /**
     * 格式化输出时间 _2019-07-18_15
     */
    @Test
    public void formatTime() {
        String formatString = "_yyyy-MM-dd_HH";
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        String dateString = sdf.format(new Date());
        //_2019-07-18_15
        System.out.println(dateString);
    }

    /*
        yyyy：年
        MM：月
        dd：日
        hh：1~12小时制(1-12)
        HH：24小时制(0-23)
        mm：分
        ss：秒
        S：毫秒
        E：星期几
        D：一年中的第几天
        F：一月中的第几个星期(会把这个月总共过的天数除以7)
        w：一年中的第几个星期
        W：一月中的第几星期(会根据实际情况来算)
        a：上下午标识
        k：和HH差不多，表示一天24小时制(1-24)。
        K：和hh差不多，表示一天12小时制(0-11)。
        z：表示时区 general time zone
        Z：表示时区 rfz 822 time zone
     */

    @Test
    public void formatDataTest() {
        /*
         * 日期转期望格式的字符串
         */
        //HH 和 hh 的差别：前者是24小时制，后者是12小时制。
        String formatString = "yyyy年MM月dd日 HH:mm:ss" +
                " 上下午标志 a" +
                " E" +
                " 一年中的第D天" +
                " 一月中的第F个星期" +
                " 一年中的第w个星期" +
                " 一月中的第W个星期" +
                " Z" +
                " z";
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        String dateString = sdf.format(new Date());
        System.out.println(dateString);
        /*
         * 字符串转日期
         */
        Date date;
        try {
            date = sdf.parse(dateString);
            System.out.println(date);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void t() {
        // 2017-10-12T07:43:46
        String formatString = "yyyy-MM-dd 'T' HH:mm:ss 'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        String dateString = sdf.format(new Date());
        System.out.println(dateString);
    }

    @Test
    public void noGroup() {
        Long dayStartSecond = TimeUtils.dayStartSecond();
        long nowDate = Calendar.getInstance().getTimeInMillis();
        //result.put("date", nowDate);//服务器时间 1478793600
        long minute = (nowDate / 1000 - dayStartSecond) / 60;

        Date s = new Date();
        //System.out.println(minute);

        String date = "2016-12-07T16:00:00.000Z";
        //注意在 UTC 字符串前面还有个空格。不然异常。
        date = date.replace("Z", " UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            Date d = sdf.parse(date);
            System.out.println("Z日期: " + d);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Calendar aw = Calendar.getInstance();//获得时间的另一种方式，测试效果一样
        //System.out.println(aw.getTime().getTime());
        //BigDecimal bd = new BigDecimal(11.11922);
        //1478656368669
        //1478793600000
        //1478793600000
        //Double totalBytes = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //System.out.println(totalBytes);
    }

    /**
     * SimpleDateFormat 格式化日期
     * 日期 - >  格式化过的字符串
     */
    @Test
    public void dateTest() {
        Date ss = new Date();
        System.out.println("一般日期输出：" + ss);
        System.out.println("时间戳 获得的是毫秒：" + ss.getTime());
        //Date aw = Calendar.getInstance().getTime();//获得时间的另一种方式，测试效果一样
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //这个就是把时间戳经过处理得到期望格式的时间
        String time = format0.format(ss.getTime());
        System.out.println("格式化结果0：" + time);

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        time = format1.format(ss.getTime());
        System.out.println("格式化结果1：" + time);
    }

    @Test
    public void out() {
        Date ss = new Date(2021,8,10);
        // Fri Aug 06 14:16:41 CST 2021
        System.out.println(ss);

        LocalDate localDate = LocalDate.now();
        // 2021-08-06
        System.out.println(localDate);

        LocalTime localTime = LocalTime.now();
        // 14:16:41.484
        System.out.println(localTime);
    }



    @Test
    public void tTime() {
         int OFFSET = 8 * 60 * 60;
         final ZoneId ZONE_ID = ZoneOffset.systemDefault();
         // 这种数据存es的type是date类型
         DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        String string = "2023-04-24T00:00:08";
        LocalDateTime localDateTime = LocalDateTime.parse(string, FORMATTER);
        long s = localDateTime.atZone(ZONE_ID).toEpochSecond();
        long ms = localDateTime.toInstant(ZoneOffset.ofTotalSeconds(OFFSET)).toEpochMilli();

        // 1682265608    2023-04-24 00:00:08
        System.out.println(s);
        // 1682265608.867
        System.out.println(ms);


        LocalDateTime l = TimeUtils.s2LocalDateTime(1682265608);
        String format = FORMATTER.format(l);
        System.out.println(format);


    }

}
