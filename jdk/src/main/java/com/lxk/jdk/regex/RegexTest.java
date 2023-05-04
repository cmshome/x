package com.lxk.jdk.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式测试
 * 常用正则表达式
 * 用户名	        /^[a-z0-9_-]{3,16}$/
 * 密码	            /^[a-z0-9_-]{6,18}$/
 * 十六进制值	        /^#?([a-f0-9]{6}|[a-f0-9]{3})$/
 * 电子邮箱	        /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/
 *                  /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/
 * URL	            /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/
 * IP 地址	        /((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)/
 *                  /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/
 * HTML 标签	        /^<([a-z]+)([^<]+)*(?:>(.*)<\/\1>|\s+\/>)$/
 * 删除代码\\注释	    (?<!http:|\S)//.*$
 * Unicode编码中的汉字范围	/^[\u2E80-\u9FFF]+$/
 *
 * @author LiXuekai on 2019/9/25
 */
public class RegexTest {

    /**
     *
     */
    private static Pattern EXISTS_PATTERN = Pattern.compile("[^\\\\]\"");
    /**
     *
     */
    private static Pattern REPLACE_PATTERN = Pattern.compile("[^\\\\]\"(([^\"])|(\\\\\"))*[^\\\\]\"");
    /**
     *
     */
    private static Pattern RANGE_PATTERN = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
    /**
     * * 点代表任意字符，，，包含这个的字符串都行能匹配上
     */
    private Pattern pattern = Pattern.compile("123.456");


    @Test
    public void test() {
        Matcher matcher = pattern.matcher("sadasdasd/1233456/dasdad");

        boolean matches = matcher.matches();
        //false
        System.out.println(matches);

        boolean b = matcher.find();
        //true
        System.out.println(b);


    }

    /**
     * \d	匹配一个数字字符。等价于[0-9]。
     * {n}	n是一个非负整数。匹配确定的n次。例如，“o{2}”不能匹配“Bob”中的“o”，但是能匹配“food”中的两个o。
     * .	匹配除“\n”之外的任何单个字符。要匹配包括“\n”在内的任何字符，请使用像“(.|\n)”的模式。
     * *	匹配前面的子表达式零次或多次。例如，zo*能匹配“z”以及“zoo”。*等价于{0,}。
     */
    @Test
    public void regex() {
        String text = "13811229209";
        String regex = "(\\d{4}).*(\\d{4})";
        String replaceAll = text.replaceAll(regex, "$1 **** **** $2");
        //1381 **** **** 9209
        System.out.println(replaceAll);

    }


    /**
     * 字符串中带有 \r\n\t，这个正则表达式就执行不成功了。
     */
    @Test
    public void lxk() {
        String text = "INSERT INTO NWRGWTSJ (TXLS.AA.BB.CCC.DD.EE.FF)from 123456789012345678901234567890 hx_zs.zs_sky_kkxx \r\n\t a left join dm_gy_swjg b on a.sjgsdq= b.SWJG_DM\n\t\t\t\t\t  from hx_zs.zs_sky_kkxx a left join dm_gy_swjg b on a.sjgsdq= b.SWJG_DM from hx_zs.zs_sky_kkxx a left join dm_gy_swjg b on a.sjgsdq= b.SWJG_DM  from hx_zs.zs_sky_kkxx a left join \n\t\t\t\t\tdm_gy_swjg b on a.sjgsdq= b.SWJG_DM  DM from hx_zs.zs_sky_kkxx a left join dm_gy_swjg b on a.sjgsdq= b.SWJG_DM  from hx_zs.zs_sky_kkxx \t\t a left join dm_gy_swjg b on a.sjgsdq= b.SWJG_DM \n\t\t\t\t";
        String regex = "INSERT INTO NWRGWTSJ.*";

        //text = "INSERT INTO NWRGWTSJ (TXLS.AA.BB.CCC.DD.EE.FF)from 123456789012345678901234567890";
        //text = text.replaceAll("\r|\n|\t", "");

        Pattern pattern = Pattern.compile(regex);
        boolean matches = pattern.matcher(text).matches();


        // 运行结果是：false。打开replace注释，就是true了。
        System.out.println(matches);
    }


    @Test
    public void chinese() {
        String s = "asd无fghjkl";
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(s);
        if (m.find()) {
            System.out.println("m.find()判断为true，表示字符串中含有中文");
        } else {
            System.out.println("无汉字");
        }
    }


}
