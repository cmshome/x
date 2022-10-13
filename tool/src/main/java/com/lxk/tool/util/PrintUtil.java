package com.lxk.tool.util;

/**
 * PrintUtil
 *
 * @author lxk on 2017/9/27
 */
public final class PrintUtil {
    public static void divideLine() {
        System.out.println("--------------分界符--------------");
        System.out.println();
    }

    /**
     * @param a        long a=System.currentTimeMillis();
     * @param describe 执行时间的描述
     */
    public static void printRunTime(long a, String describe) {
        System.out.println(describe + " 执行耗时 : " + (System.currentTimeMillis() - a) + " 毫秒 ");
    }
}
