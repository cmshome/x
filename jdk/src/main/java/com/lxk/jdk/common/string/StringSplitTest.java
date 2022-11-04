package com.lxk.jdk.common.string;

import com.google.common.base.Strings;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author LiXuekai on 2020/7/27
 */
public class StringSplitTest {

    /**
     * 测试字符串的 split()
     * 返回的数组，若末尾有一连串空的，则舍弃
     */
    @Test
    public void testSplit() {
        String ss = ",aa,bb,cc,dd,,,";
        ss = "1|2|3||7|4";
        String[] array = ss.split("\\|");
        System.out.println(Arrays.toString(array));

        //结果是5，而不是预想中的8
        System.out.println(array.length);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    /**
     * 期望个数
     * 1，不带期望值，最后一个是空，会被直接丢掉
     * 2，带期望值，期望是7，实际值是7，最后是空，还保留
     * 3，带期望值，期望是6，实际值是7，最后的2个就不split了
     * 4，带期望值，期望是5，实际值是7，最后的3个就不split了
     */
    @Test
    public void expected() {
        String ss = "1|2|3||7|4|";
        String[] split = ss.split("\\|", 7);
        // [1, 2, 3, , 7, 4, ]
        System.out.println(Arrays.toString(split));

        split = ss.split("\\|");
        // [1, 2, 3, , 7, 4]
        System.out.println(Arrays.toString(split));

         split = ss.split("\\|", 6);
        // [1, 2, 3, , 7, 4|]
        System.out.println(Arrays.toString(split));

        split = ss.split("\\|", 5);
        // [1, 2, 3, , 7|4|]
        System.out.println(Arrays.toString(split));
    }

    /**
     * 字符串 split()新发现
     * 支持正则表达式的split
     */
    @Test
    public void testSplitPlus() {
        // n个数字来split
        String ss = "aa12sas32sasa223sas12as12wqe";
        String[] array = ss.split("[\\d]+");
        System.out.println(Arrays.toString(array));

        // n个逗号来split
        ss = "aa,,sas,,sasa,,,,sasas,,,";
        array = ss.split("[,]+");
        System.out.println(Arrays.toString(array));

        // n个空字符串
        ss = "aa  sas sa sa     sas  as  ";
        array = ss.split("[\\s]+");
        System.out.println(Arrays.toString(array));
    }

    /**
     * 1，| . 要转义
     * 2，若是有split之后数组的期望的值，则使用 split(String regex, int limit)
     */
    @Test
    public void split() {
        String s = "0|xxx|188.188.8.118|xxx";
        String[] split = s.split("\\|");
        // [0, xxx, 188.188.8.118, xxx]
        System.out.println(Arrays.toString(split));

        s = "0|xxx|188.188.8.118|";
        split = s.split("\\|");
        // [0, xxx, 188.188.8.118]
        System.out.println(Arrays.toString(split));

        split = s.split("\\|", 4);
        // [0, xxx, 188.188.8.118, ]
        System.out.println(Arrays.toString(split));

        s = "trans_ref.amount";
        split = s.split("\\.");
        // [trans_ref, amount]
        System.out.println(Arrays.toString(split));
    }

    /**
     * 点做分隔符的时候，需要转义一下，不能直接split
     */
    @Test
    public void splitByPoint() {
        String s = "trans_ref.amount";
        String[] split = s.split("\\.");
        System.out.println(Arrays.toString(split));
        System.out.println(s.contains("\\."));
    }

    @Test
    public void java8() {
        String[] split = "link:\"10.0.14.133:19291-10.0.2.87:10041\"".split(":");
        Arrays.stream(split).filter(s1 -> !"OR".equals(s1)).forEach(System.out::println);
        Arrays.stream("sda.sda".split("\\.")).forEach(System.out::println);
    }


    @Test
    public void xxx() {
        String s = "/cycle/fd37613a-b6fc-4d13-9f07-121cf970a84b/测试日报_日报_20220630-20220701.xlsx";
        String name = nameFromFilepath(s);
        System.out.println(name.contains("."));
        System.out.println(name.contains("."));
    }

    /**
     * 获取文件名称
     *
     * @param filePath /cycle/fd37613a-b6fc-4d13-9f07-121cf970a84b/测试日报_日报_20220630-20220701.xlsx
     * @return 测试日报_日报_20220630-20220701.xlsx
     */
    private String nameFromFilepath(String filePath) {
        if (!Strings.isNullOrEmpty(filePath)) {
            String[] split = filePath.split("/");
            int length = split.length;
            if (length > 0) {
                return split[length - 1];
            }
        }
        return null;
    }


    private static final String SPLIT = "-";
    private static final int MIN_LENGTH = 2;


    @Test
    public void xxxxx() {

        String s = "xxx-07-01-15-1656660900";
        String s1 = keyFromCacheMapKey(s);
        String s2 = timeFromCacheMapKey(s);
        System.out.println(s1);
        System.out.println(s2);
    }

    /**
     * 从 key + 时间戳秒 的字符串中解析出key的值
     *
     * @param fullKey 监控字段名称，比如：easy_2022-07-01-15-1656660900
     * @return key值
     */
    public String keyFromCacheMapKey(String fullKey) {
        if (Strings.isNullOrEmpty(fullKey)) {
            return null;
        }
        String[] split = fullKey.split(SPLIT);
        int length = split.length;
        if (length >= MIN_LENGTH) {
            String time = SPLIT + split[length - 1];
            return fullKey.replace(time, "");
        }
        return null;
    }

    /**
     * 从 key + 时间戳秒 的字符串中解析出时间戳的值
     *
     * @param fullKey 监控字段名称，比如：easy_2022-07-01-15-1656660900
     * @return time值
     */
    public String timeFromCacheMapKey(String fullKey) {
        if (Strings.isNullOrEmpty(fullKey)) {
            return null;
        }
        String[] split = fullKey.split(SPLIT);
        int length = split.length;
        if (length >= MIN_LENGTH) {
            return split[length - 1];
        }
        return null;
    }




}
