package com.lxk.jdk.common.string;

import org.junit.Before;
import org.junit.Test;

/**
 * @author LiXuekai on 2021/9/22
 */
public class FindChildString {

    private String a;
    private String b;

    @Before
    public void init() {

    }

    @Test
    public void test() {
        a = "abc123";
        b = "abc123";

        lcs(a, b);

        a = "abc123";
        b = "4567";
        lcs(a, b);

        a = "abc123                  ";
        b = "              4567       ";
        lcs(a, b);

        a = "noresponse";
        b = "no-response";
        lcs(a, b);

        a = "abcdef";
        b = "ABCDEF";
        lcs(a, b);

        a = "abcdef-6789-abcdef-GHIJK";
        b = "abcdef-6789-GHIJK";
        lcs(a, b);

        a = "ABCDEF";
        b = "zxcvbn-qwert-ASDFG";
        lcs(a, b);

        a = "20042922222123";
        b = "ACQS20210903162553011103212492130";
        lcs(a, b);

        a = "249213";
        b = "ACQS20210903162553011103212492130";
        lcs(a, b);

        a = "1234abc";
        b = "1234-abc-xx";
        lcs(a, b);

        a = "abc1234";
        b = "1234-abc-xx";
        lcs(a, b);

        a = "0913150500";
        b = "ACQS20210913150553011103212492130";
        lcs(a, b);

        a = "KKKKK";
        b = "KKKKK";
        lcs(a, b);

    }

    private void lcs(String a, String b) {
        System.out.println("最长公共子串： " + longestCommonSubstring(a, b)
                + "   最长公共子序列：" + longestCommonSubsequence(a, b)
                +  "         "+a + "|" +  b);
    }

    /**
     * 最长公共子串：要求在原字符串中是连续的
     */
    public int longestCommonSubstring(String A, String B) {
        int a_len = A.length();
        int b_len = B.length();
        int max = 0;
        //dp[i][j] 为 A取前i个，B取前j个，看这两个的LCS长度，各个的最后一个不等，肯定是0了
        int[][] dp = new int[a_len + 1][b_len + 1];
        for (int i = 1; i <= a_len; ++i) {
            for (int j = 1; j <= b_len; ++j) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > max) max = dp[i][j];
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return max;
    }

    /**
     * 最长公共子序列：只需要保持相对顺序一致，并不要求连续
     */
    public int longestCommonSubsequence(String A, String B) {
        int a_len = A.length();
        int b_len = B.length();
        int max = 0;
        int[][] dp = new int[a_len + 1][b_len + 1];
        for (int i = 1; i <= a_len; i++) {
            for (int j = 1; j <= b_len; j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    //区别在这里
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }
}
