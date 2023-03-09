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

        //Java，哦，应该是jre目录
        System.out.println(System.getProperty("java.home"));


        // 获取JDK的位数
        // 包含 "64",即可64位 JDK , 否则 32位
        System.out.println(System.getProperty("java.vm.name"));
        // 64位JDK：amd64 ，32位JDK：x86
        System.out.println(System.getProperty("os.arch"));
        // 64位JDK：64 ，32位JDK：32
        System.out.println(System.getProperty("sun.arch.data.model"));
    }

    @Test
    public void x() {
        System.out.println(System.getProperty("java.version")); //java版本号
        System.out.println(System.getProperty("java.vendor")); //Java提供商名称
        System.out.println(System.getProperty("java.vendor.url")); //Java提供商网站
        System.out.println(System.getProperty("java.home")); //Java，哦，应该是jre目录
        System.out.println(System.getProperty("java.vm.specification.version")); //Java虚拟机规范版本号
        System.out.println(System.getProperty("java.vm.specification.vendor")); //Java虚拟机规范提供商
        System.out.println(System.getProperty("java.vm.specification.name")); //Java虚拟机规范名称
        System.out.println(System.getProperty("java.vm.version")); //Java虚拟机版本号
        System.out.println(System.getProperty("java.vm.vendor")); //Java虚拟机提供商
        System.out.println(System.getProperty("java.vm.name")); //Java虚拟机名称
        System.out.println(System.getProperty("java.specification.version")); //Java规范版本号
        System.out.println(System.getProperty("java.specification.vendor")); //Java规范提供商
        System.out.println(System.getProperty("java.specification.name")); //Java规范名称
        System.out.println(System.getProperty("java.class.version")); //Java类版本号
        System.out.println(System.getProperty("java.class.path")); //Java类路径
        System.out.println(System.getProperty("java.library.path")); //Java lib路径
        System.out.println(System.getProperty("java.io.tmpdir")); //Java输入输出临时路径
        System.out.println(System.getProperty("java.compiler")); //Java编译器
        System.out.println(System.getProperty("java.ext.dirs")); //Java执行路径
        System.out.println(System.getProperty("os.name")); //操作系统名称
        System.out.println(System.getProperty("os.arch")); //
        System.out.println(System.getProperty("os.version")); //版本号
        System.out.println(System.getProperty("file.separator")); //文件分隔符
        System.out.println(System.getProperty("path.separator")); //路径分隔符
        System.out.println(System.getProperty("line.separator")); //直线分隔符
        System.out.println(System.getProperty("user.name")); //用户名
        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty("user.dir"));
    }
}
