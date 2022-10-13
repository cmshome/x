package com.lxk.jar;

import org.junit.Test;

import java.io.File;

/**
 * @author LiXuekai on 2022/7/11
 */
public class TestJarOutputStream {
    private static final String path = "/Users/fang/Downloads/test/";
    private static final String name = "lxk.jar";

    /**
     * 实验结论:
     * 1.JarInputStream的getNextJarEntry()和jarOutputStream的putNextJarEntry()中没有包括"META-INF/MANIFEST.MF"这一项,因此复制和解压都	要注意
     * 2.JarFile的entries()方法包含了全部Entry,也包括"META-INF/MANIFEST.MF",没有"META-INF/"这一项,因此在解压的时候要先检测父文件存不存在
     * 4.复制jar文件有3中方法,
     * A，是直接用BufferedInputStream和BufferedOutputStream复制,
     * B，是用JarInputStream的getNextJarEntry()和jarOutputStream的putNextJarEntry()
     * C，是用JarFile的entries()方法,遍寻JarEntry的InputStream,以此写出
     * 5.解压jar的话推荐使用JarFile,当前实例方法只支持解压jar文件
     * 6.在复制的时候,src文件只可以是jar文件,但des文件可以是带zip或rar后缀的文件
     */
    @Test
    public void jar() throws Exception {
        File file1 = new File(path + name);
        File file2 = new File(path + "copy.jar");
    }

}
