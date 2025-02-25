package com.lxk.jdk.common.string;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * String字符串测试
 *
 * @author lxk on 2017/2/8
 */
public class StringCommonTest {

    /**
     * 计算字符串的大小
     * byte就是字节，最小的单位了。
     * kb就是千字节。
     * 1B = 8bit
     * 1KB = 1024B
     * 1MB = 1024KB
     * ...
     */
    @Test
    public void size() {
        String a = "abc";
        System.out.println(a.getBytes().length);
    }

    /**
     * 正则表达式 {1,} :出现一次或者多次 意义同 +
     */
    @Test
    public void regex() {

        String s = "a b  c    d    f  sad";
        // 把1或多个空格给替换成逗号，再按照逗号来split。
        String[] split = s.replaceAll(" +", ",").split(",");

        //String[] split = s.replaceAll(" {1,}", ",").split(",");

        System.out.println(Arrays.toString(split));
    }

    /**
     * replace方法，如果arg1不存在，那么原始值是不会被修改的。
     */
    @Test
    public void replaceTest() {
        String s = "*a.amount";
        System.out.println(s.replace("lxk", ""));
        System.out.println(s.replace("*", ""));
    }

    /**
     * 在replace的时候，点符号不需要转义。
     */
    @Test
    public void replaceTest2() {
        String s = "a.b.c.d.e.f";
        System.out.println(s.replace(".", "_"));
    }

    @Test
    public void subStringTest() {
        String s = "ab";
        System.out.println(s.substring(0, 2));
    }

    @Test
    public void lengthTest() {
        String ss = "我是一个小学生";
        System.out.println(ss.length());
        byte[] bytes = ss.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(bytes));
        System.out.println(bytes.length);
    }


    static List<String> METRIC_IN_VISUAL_QUERY_STRING =
            new ImmutableList.Builder<String>()

                    .add("x")

                    .build();

    private String adapterQueryString(String queryString) {
        if (Strings.isNullOrEmpty(queryString)) {
            return queryString;
        }
        String splitString = " ";
        String prefix = "_";
        List<String> notMetricList = Lists.newArrayList("AND", "OR", "NOT");
        //准备删除的旧数据
        List<String> remove = Lists.newArrayList();

        String[] split = queryString.split(splitString);
        for (int i = 0; i < split.length; i++) {
            //value可能是配置的值或者连接符（AND、OR、NOT）
            String value = split[i];
            if (Strings.isNullOrEmpty(value)) {
                continue;
            }
            //连接符
            if (notMetricList.contains(value)) {
                continue;
            }
            //不管指标怎么样，都给去掉开头的下划线
            if (value.startsWith(prefix)) {
                value = value.replaceFirst(prefix, "");
            }

            //下面考虑删除掉4.1不存在的指标.
            String[] split1 = value.split(":");
            String metric = split1[0];


            if (metric.contains(".")) {
                metric = metric.split("\\.")[0];
            }
            //既不是4.1的指标，也不是连接符。
            if (!METRIC_IN_VISUAL_QUERY_STRING.contains(metric)) {
                if (i - 1 > -1) {
                    String before = split[i - 1];
                    remove.add(" " + before + " " + value);
                } else {
                    remove.add(value);
                }
            }
            //更新数组的值
            split[i] = value;
        }

        Joiner joiner = Joiner.on(" ").skipNulls();
        queryString = joiner.join(split);

        for (String s : remove) {
            queryString = queryString.replace(s, "");
        }

        List<String> special = Lists.newArrayList(" OR ", " NOT ", " AND ");
        for (String s : special) {
            if (queryString.startsWith(s)) {
                queryString = queryString.replaceFirst(s, "");
            }
        }

        return queryString;
    }

    @Test
    public void testReplace() {
        String s = "";

        List<String> list = Lists.newArrayList();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        String string = adapterQueryString(s);
        System.out.println(s);
        System.out.println(string);

        //String[] split = s.split(" ");
        //Arrays.stream(split).filter(s1 -> !"OR".equals(s1)).forEach(System.out::println);
        //for (int i = 0; i < split.length; i++) {
        //    String s1 = split[i];
        //    if (s1.startsWith("_")) {
        //        s1 = s1.replaceFirst("_", "");
        //        split[i] = s1;
        //    }
        //}
        //System.out.println();
        //Arrays.stream(split).filter(s1 -> !"OR".equals(s1)).forEach(System.out::println);
        //Joiner joiner = Joiner.on(" ").skipNulls();
        //String join = joiner.join(split);
        //
        //System.out.println(s);
        //System.out.println(join);

    }


    /**
     * 测试：trim返回新的字符串，不修改原来的字符串。
     */
    @Test
    public void testTrim() {
        String s = "          ";
        String trim = s.trim();
        System.out.println(trim);
        System.out.println(s);
    }

    /**
     * 可变参数的测试
     * 可变参数的定义，必须是放在参数列表的最后面
     */
    @Test
    public void testManyArgs() {
        int d = 1;
        System.out.println(isNotNullOrEmpty(d, "a"));
        System.out.println(isNotNullOrEmpty(d, "a", "b"));
        System.out.println(isNotNullOrEmpty(d, "a", "b", ""));
        String str = concatString(new String[]{"a", "b", "c", "d"});
        System.out.println(str);
    }

    private boolean isNotNullOrEmpty(int d, String... arg) {
        System.out.println(d);
        for (String s : arg) {
            if (Strings.isNullOrEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    public String concatString(String... strings) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            str.append(strings[i]);
            if (i != strings.length - 1) {
                str.append("--");
            }
        }
        return str.toString();
    }

    /**
     * 测试一个新建字符串的不常见姿势
     */
    @Test
    public void testNewStringArray() {
        String[] command = new String[]{"sh", "-c", "ps -ef | grep \""
                + "d:test" + "\" | grep \"" + "d:test" + "\" | grep -v \"grep\" | awk '{ print $2}'"};
        System.out.println(Arrays.toString(command));
        //结果如下：
        //[sh, -c, ps -ef | grep "d:test" | grep "d:test" | grep -v "grep" | awk '{ print $2}']
    }

    /**
     * 使用indexOf()来拆分字符串：D:\Android\sdk\add-ons
     */
    @Test
    public void testIndexOf() {
        String string = "aaa456ac";

        //查找指定字符是在字符串中的下标。在则返回所在字符串下标；不在则返回-1.
        //indexOf(String str)；返回结果：-1，"b"不存在
        System.out.println(string.indexOf("b"));

        // 从第四个字符位置开始往后继续查找，包含当前位置
        //indexOf(String str, int fromIndex)；返回结果：6
        System.out.println(string.indexOf("a", 3));

        //（与之前的差别：上面的参数是 String 类型，下面的参数是 int 类型）参考数据：a-97,b-98,c-99

        // 从头开始查找是否存在指定的字符
        //indexOf(int ch)；返回结果：7
        System.out.println(string.indexOf(99));
        //indexOf(int ch)；返回结果：7
        System.out.println(string.indexOf('c'));

        //从fromIndex查找ch，这个是字符型变量，不是字符串。字符a对应的数字就是97。
        //indexOf(int ch, int fromIndex)；返回结果：6
        System.out.println(string.indexOf(97, 3));
        //indexOf(int ch, int fromIndex)；返回结果：6
        System.out.println(string.indexOf('a', 3));

        //这个就是灵活运用String类提供的方法，拆分提供的字符串。
        String s = "D:\\Android\\sdk\\add-ons";
        System.out.println(s);
        while (s.lastIndexOf("\\") > 0) {
            s = s.substring(0, s.lastIndexOf("\\"));
            System.out.println(s);
        }
    }

    /**
     * 对 . index of的时候，不需要转移符号。
     * 对 . split   的时候，就需要转义符号。
     */
    @Test
    public void index() {
        String a = "s.ABC.A";
        int i = a.indexOf(".");

        String pre = a.substring(0, i);
        String post = a.substring(i + 1);
        String[] strings = new String[2];
        strings[0] = pre;
        strings[1] = post;
        System.out.println(i);
        System.out.println(Arrays.toString(a.split(".")));
        System.out.println(Arrays.toString(a.split("\\.")));

        System.out.println(pre);
        System.out.println(post);

        System.out.println(Arrays.toString(strings));

    }

    /**
     * 将字符串倒序
     */
    @Test
    public void testReverseString() {
        String string = "please call me big brother";
        char[] chars = string.toCharArray();
        int length = chars.length;
        for (int i = 0; i < length / 2; i++) {
            char temp;
            temp = chars[i];
            chars[i] = chars[length - 1 - i];
            chars[length - 1 - i] = temp;
        }
        System.out.println(chars);
    }

    /**
     * 将list的内容以逗号间隔，最后不应该多个逗号。
     */
    @Test
    public void testListToString() {
        List<String> s = Lists.newArrayList("1", "2", "3");
        StringBuilder sb = new StringBuilder();
        for (String s1 : s) {
            sb.append(s1).append(",");
        }
        System.out.println(sb.toString().substring(0, sb.lastIndexOf(",")));
        System.out.println("等效的快捷方式");
        //跳过null
        Joiner joiner = Joiner.on(",").skipNulls();
        //System.out.println(joiner.join(s));
        System.out.println(joiner.join(s.toArray()));
    }

    /**
     * 测试string转换成char类型
     */
    @Test
    public void testStringToChar() {
        String s = "abcdefghi";
        char result[] = s.toCharArray();
        char ss = s.toCharArray()[0];
        System.out.println(ss);
        System.out.println(result);
        char char0 = s.charAt(0);
        System.out.println(char0);
    }

    @Test
    public void testSubstring() {
        String ss = "-1,-0";
        String[] split = ss.split(",");
        String split1 = split[0];
        String split2 = split[1];
        String data = "0123456789876543210";

        int length = data.toString().length();
        String value = null;
        try {
            int from = Integer.parseInt(split1);
            if (from < 0) {
                from = length + from;
            }
            int to = Integer.parseInt(split2);
            if (split2.startsWith("-")) {
                to = to + length;
            }
            value = data.toString().substring(from, to);
            System.out.println(value);
        } catch (Exception e) {
        }
    }

    /**
     * 测试代码中的换行："\r\n"和<br>
     */
    @Test
    public void testStringNewLine() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("大").append("\r\n");
        }
        System.out.println(sb.toString());
    }

    /**
     * StringBuffer和StringBuilder的使用
     */
    @Test
    public void testStringBufferAndStringBuilder() {
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sbf.append("这是第").append(i).append("个; ");
        }
        System.out.println(sbf);
        System.out.println(sbf.indexOf("7"));
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sbd.append("这是第").append(i).append("个; ");
        }
        System.out.println(sbd);
        System.out.println(sbd.indexOf("7"));
    }

    /**
     * 测试值传递和地址传递
     * 结论。
     * 字符串：不管是new出来的对象，还是直接用=声明的都是值传递
     * 基本数据类型：同理，也是值传递。
     */
    @Test
    public void testValueAndAddressTransmit() {
        String transmitValue = "初始值";
        String transmitValueNew = new String("new 出来的字符串");
        Integer integer = new Integer(900);
        List<String> list = Lists.newArrayList();
        list.add("0");
        testTransmitValue(transmitValue, transmitValueNew, list, integer);
        System.out.println(transmitValue);
        System.out.println(transmitValueNew);
        System.out.println(list.toString());
        System.out.println(integer);
    }

    /**
     * 测试字符串和集合在函数之间的传值问题,解决值传递和地址传递的疑惑.
     */
    private void testTransmitValue(String transmitValue, String transmitValueNew, List<String> list, Integer integer) {
        transmitValue += "修改的痕迹";
        transmitValueNew += "assss修改的痕迹";
        list.add("1");
        list.add("2");
        list.add("3");
        integer = 9999;
    }

    @Test
    public void eq() {
        boolean b = Objects.equals(null, null);
        System.out.println(b);
        String a = "1234567";
        String sa = sa(a);
        System.out.println(sa == a);
    }

    private String sa(String a) {
        return a + "";
    }


    @Test
    public void empty() {
        String s = "     ";
        boolean empty = s.isEmpty();
        System.out.println(empty);
        s = s.trim();
        empty = s.isEmpty();
        System.out.println(empty);
    }

    @Test
    public void trimNull() {
        showTrimEnd("?.o.Q!a?    ");
        showTrimEnd("    ");
        showTrimEnd("   a d ");
        showTrimEnd("     123     ");
        showTrimEnd(null);
        showTrimEnd("   cdd   ");
    }

    private void showTrimEnd(Object value) {
        System.out.println("改之前：|" + value + "|");
        Object o = trimEnd(value);
        System.out.println("改之后：|" + o + "|");
    }

    private Object trimEnd(Object value) {
        try {
            if (value instanceof String) {
                String s = value.toString();
                return s.replaceAll("\\s+$", "");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return value;
    }


}
