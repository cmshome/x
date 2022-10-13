package com.lxk.jdk.v;

import org.junit.Test;

/**
 * @author LiXuekai on 2022/10/13
 */
public class JDKTest {

    /**
     * 获取jak的version
     */
    @Test
    public void jdk() {
        // 获取jdk的详细版本号， 例如：1.8.0_91 ， 1.7.0_79，1.6.0
        System.out.println(System.getProperty("java.version"));
        // 获取 jdk的标准版本 ，例如： 1.8 , 1.7 , 1.6
        System.out.println(System.getProperty("java.specification.version"));


        // 获取JDK的位数
        // 包含 "64",即可64位 JDK , 否则 32位
        System.out.println(System.getProperty("java.vm.name"));
        // 64位JDK：amd64 ，32位JDK：x86
        System.out.println(System.getProperty("os.arch"));
        // 64位JDK：64 ，32位JDK：32
        System.out.println(System.getProperty("sun.arch.data.model"));
    }
}
