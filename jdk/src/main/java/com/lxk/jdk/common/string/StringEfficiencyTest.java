package com.lxk.jdk.common.string;

import org.junit.Test;

import java.util.StringTokenizer;

/**
 * 字符串相关的性能测试
 *
 * @author LiXuekai on 2021/4/2
 */
public class StringEfficiencyTest {
    private static final String TTM = "";
    private static final char DOUBLE_QUOTATION_MARKS = '"';
    private static final char SINGLE_QUOTATION_MARK = '\'';


    /**
     * 测试字符串分割的效率问题 StringTokenizer 和 split 的对比。
     */
    @Test
    public void testSplitEfficiency() {
        while (true) {
            //29.4%
            split();
            //70.5%
            stringTokenizer();
        }
    }

    /**
     * 测试字符串拼接
     */
    @Test
    public void testStringContact() {
        String[] split = TTM.split("\\|");
        int length = split.length;
        while (true) {
            //56%
            String s = testAdd(split, length);
            //13.8%
            String s1 = testStringBuilderAppend(split, length);
            //14.4%
            String s2 = testStringBufferAppend(split, length);
            //15.8%
            String s3 = testContact(split, length);
        }
    }

    /**
     * 测试下面三个处理字符串的效率问题
     * cleanString ：cleanString2：cleanString3 = 32.9 : 23.9 : 30.2
     * 可见 cleanString2 效率高。
     */
    @Test
    public void testEfficiency() {
        while (true) {
            testCleanString();
            testCleanString2();
            testCleanString3();
        }
    }

    @Test
    public void test() {
        while (true) {
            test1();
            test2();
        }
    }







    private void test2() {
        String[] split = TTM.split("\\|");
        int length = split.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(split[i]);
        }
    }

    private void test1() {
        StringTokenizer stringTokenizer = new StringTokenizer(TTM, "\\|");
        StringBuilder stringBuilder = new StringBuilder();
        while (stringTokenizer.hasMoreElements()) {
            stringBuilder.append(stringTokenizer.nextToken());
        }
    }


    private static String testContact(String[] split, int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result = result.concat(split[i]);
        }
        return result;
    }

    private static String testStringBufferAppend(String[] split, int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(split[i]);
        }
        return stringBuffer.toString();
    }

    private static String testStringBuilderAppend(String[] split, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(split[i]);
        }
        return stringBuilder.toString();
    }

    private static String testAdd(String[] split, int length) {
        String result = null;
        for (int i = 0; i < length; i++) {
            result += split[i];
        }
        return result;
    }

    private static void stringTokenizer() {
        StringTokenizer st = new StringTokenizer(TTM, "\\|");
        StringBuilder stringBuilder = new StringBuilder();
        while (st.hasMoreTokens()) {
            stringBuilder.append(st.nextToken());
        }
        String s = stringBuilder.toString();
    }

    private static void split() {
        String[] split = TTM.split("\\|");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : split) {
            stringBuilder.append(s);
        }
        String s = stringBuilder.toString();
    }

    private static void testCleanString() {
        String s = "\"1234\"";
        cleanString(s);

        s = "'1234'";
        cleanString(s);

        s = "1234";
        cleanString(s);

        s = "123123'";
        cleanString(s);

        s = "12315623\'";
        cleanString(s);

        s = "123123\"\"";
        cleanString(s);
    }

    private static void testCleanString2() {
        String s = "\"1234\"";
        cleanString2(s);

        s = "'1234'";
        cleanString2(s);

        s = "1234";
        cleanString2(s);

        s = "123123'";
        cleanString2(s);

        s = "12315623\'";
        cleanString2(s);

        s = "123123\"\"";
        cleanString2(s);
    }

    private static void testCleanString3() {
        String s = "\"1234\"";
        cleanString3(s);

        s = "'1234'";
        cleanString3(s);

        s = "1234";
        cleanString3(s);

        s = "123123'";
        cleanString3(s);

        s = "12315623\'";
        cleanString3(s);

        s = "123123\"\"";
        cleanString3(s);
    }

    private static String cleanString3(String value) {
        if ((value == null) || (value.isEmpty())) {
            return null;
        }
        if ((value.startsWith("\"") && value.endsWith("\""))
                || (value.startsWith("'") && value.endsWith("'"))) {
            value = value.substring(1, value.length() - 1);
        }
        return value;
    }

    /**
     * 相比下面的，判断次数是减少了，效率提高啦。
     * 字符串如果包含了""或者''，去掉最外层的引号。
     *
     * @param value 值
     * @return 字符串值
     */
    private static String cleanString2(String value) {
        if ((value == null) || (value.isEmpty())) {
            return null;
        }
        char firstChar = value.charAt(0);
        if (firstChar == DOUBLE_QUOTATION_MARKS) {
            int lastIndex = value.length() - 1;
            char lastChar = value.charAt(lastIndex);
            if (lastChar == DOUBLE_QUOTATION_MARKS) {
                return value.substring(1, lastIndex);
            }
        } else if (firstChar == SINGLE_QUOTATION_MARK) {
            int lastIndex = value.length() - 1;
            char lastChar = value.charAt(lastIndex);
            if (lastChar == SINGLE_QUOTATION_MARK) {
                return value.substring(1, lastIndex);
            }
        }
        return value;
    }

    /**
     * 字符串如果包含了""或者''，去掉最外层的引号。
     *
     * @param value 值
     * @return 字符串值
     */
    private static String cleanString(String value) {
        if ((value == null) || (value.isEmpty())) {
            return null;
        }
        int length = value.length();
        int lastIndex = length - 1;
        char firstChar = value.charAt(0);
        char lastChar = value.charAt(lastIndex);
        //是被双引号引住的字符串
        boolean containDouble = firstChar == '"' && lastChar == '"';
        //是被单引号引住的字符串
        boolean containSingle = firstChar == '\'' && lastChar == '\'';
        if (containDouble || containSingle) {
            value = value.substring(1, lastIndex);
        }
        return value;
    }
}
