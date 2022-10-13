package com.lxk.jdk8.base64;

import com.lxk.tool.util.FileIOUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 在Java 8中，Base64编码已经成为Java类库的标准。
 * Java 8 内置了 Base64 编码的编码器和解码器。
 * Base64工具类提供了一套静态方法获取下面三种BASE64编解码器：
 * <p>
 * 基本：输出被映射到一组字符A-Za-z0-9+/，编码不添加任何行标，输出的解码仅支持A-Za-z0-9+/。
 * URL：输出被映射到一组字符A-Za-z0-9+_，输出是URL和文件。
 * MIME：输出隐射到MIME友好格式。输出每行不超过76字符，并且使用'\r'并跟随'\n'作为分割。编码输出最后没有行分割。
 *
 * @author LiXuekai on 2019/6/27
 */
public class Base64Test {

    private static final int MAX = 10;

    @Test
    public void base64() throws UnsupportedEncodingException {
        test();
        basic();
        url();
        mime();

    }

    /**
     * 测试几个特殊字符
     */
    private void test() throws UnsupportedEncodingException {
        String ss = "星期五?/\\|";
        System.out.println("ordinal         : " + ss);
        byte[] encode = Base64.getEncoder().encode(ss.getBytes(UTF_8));
        System.out.println("basic encode    : " + new String(encode, UTF_8));

        String yy = "5pif5pyf5LqUPy9cfA==";
        byte[] decode = Base64.getDecoder().decode(yy);
        System.out.println("Using Basic     : " + new String(decode, UTF_8));

        byte[] decode1 = Base64.getUrlDecoder().decode(yy);
        System.out.println("Using URL       : " + new String(decode1, UTF_8));

        byte[] decode2 = Base64.getMimeDecoder().decode(yy);
        System.out.println("Using MIME      : " + new String(decode2, UTF_8));

        System.out.println();
    }


    @Test
    public void lxk() {
        String yy = "cGFja2V0IGxvc3M=";
        byte[] decode = Base64.getDecoder().decode(yy);
        System.out.println("Using Basic     : " + new String(decode, UTF_8));
    }

    /**
     * MIME编码器会使用基本的字母数字产生BASE64输出，
     * 而且对MIME格式友好：每一行输出不超过76个字符，而且每行以“\r\n”符结束
     */
    private void mime() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < MAX; ++t) {
            sb.append(UUID.randomUUID().toString());
        }

        byte[] toEncode = sb.toString().getBytes(UTF_8);
        String mimeEncoded = Base64.getMimeEncoder().encodeToString(toEncode);
        System.out.println("Using MIME      : ");
        System.out.println(mimeEncoded);
    }

    /**
     * 但由于URL对反斜线“/”有特殊的意义，因此URL编码需要替换掉它，使用下划线替换
     * 如果是使用基本的编码器，那么输出可能会包含反斜线“/”字符，
     * 但是如果使用URL编码器，那么输出的内容对URL来说是安全的。
     */
    private void url() throws UnsupportedEncodingException {
        String ordinal = "subjects?abcd";
        System.out.println("ordinal         : " + ordinal);
        // 输出为: Using Basic Alphabet: c3ViamVjdHM/YWJjZA==
        String basicEncoded = Base64.getEncoder().encodeToString(ordinal.getBytes(UTF_8));
        System.out.println("Using Basic     : " + basicEncoded);

        byte[] decode = Base64.getDecoder().decode(basicEncoded.getBytes(UTF_8));
        System.out.println("basic decode    : " + new String(decode, UTF_8));
        System.out.println();

        System.out.println("ordinal         : " + ordinal);
        // 输出为: Using URL Alphabet: c3ViamVjdHM_YWJjZA==
        String urlEncoded = Base64.getUrlEncoder().encodeToString(ordinal.getBytes(UTF_8));
        System.out.println("Using URL       : " + urlEncoded);

        byte[] decode1 = Base64.getUrlDecoder().decode(urlEncoded);
        System.out.println("url decode      : " + new String(decode1, UTF_8));
        System.out.println();
    }

    /**
     * Basic编码是标准的BASE64编码，用于处理常规的需求：输出的内容不添加换行符，而且输出的内容由字母加数字组成。
     */
    private void basic() throws UnsupportedEncodingException {
        String s = "some string";
        System.out.println("ordinal         : " + s);
        // 编码
        String asB64 = Base64.getEncoder().encodeToString(s.getBytes(UTF_8));
        // 输出为: c29tZSBzdHJpbmc=
        System.out.println("Using Basic     : " + asB64);

        // 解码
        byte[] asBytes = Base64.getDecoder().decode("c29tZSBzdHJpbmc=");
        // 输出为: some string
        System.out.println("basic decode    : " + new String(asBytes, UTF_8));

        System.out.println();
    }

    @Test
    public void ttt() {
        String s = "lxk20200923";
        System.out.println("ordinal         : " + s);
        // 编码
        String asB64 = Base64.getEncoder().encodeToString(s.getBytes(UTF_8));
        // 输出为: c29tZSBzdHJpbmc=
        System.out.println("Using Basic     : " + asB64);

        // 解码
        byte[] asBytes = Base64.getDecoder().decode("c29tZSBzdHJpbmc=");
        // 输出为: some string
        System.out.println("basic decode    : " + new String(asBytes, UTF_8));
    }

    @Test
    public void encode() throws IOException {

        String s = "新年好啊，tom and jerry。";

        String s1 = FileIOUtil.readFile("/Users/fang/Downloads/xxx.txt", "GB2312");
        String basicEncoded = Base64.getEncoder().encodeToString(s1.getBytes("GB2312"));

        System.out.println(basicEncoded);

        String s2 = new String(Base64.getDecoder().decode(basicEncoded.getBytes(UTF_8)), UTF_8);
        System.out.println(s2);

        byte[] bytes = s2.getBytes(UTF_8);

        String after = Base64.getEncoder().encodeToString(bytes);
        System.out.println(after);

        String gb2312 = new String(bytes, "GB2312");
        System.out.println(gb2312);
    }


    @Test
    public void castBase64Code() {
        String s = "lxk20200923";
        String ss = cast(s);
        String s1 = recast(ss);
        System.out.println(s1);

    }

    private String cast(String s) {
        String asB64 = Base64.getEncoder().encodeToString(s.getBytes(UTF_8));
        System.out.println(asB64);
        return asB64;
    }

    private String recast(String ss) {
        String s = new String(Base64.getDecoder().decode(ss.getBytes(UTF_8)), UTF_8);
        System.out.println(s);
        return s;
    }

}
