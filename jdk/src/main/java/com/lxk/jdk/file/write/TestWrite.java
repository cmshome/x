package com.lxk.jdk.file.write;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lxk.tool.util.FileIOUtil;
import com.lxk.tool.util.JsonUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author LiXuekai on 2022/4/6
 */
public class TestWrite {
    private final String fileName = "/Users/fang/Downloads/x.txt";
    private final String path = "/Users/fang/Downloads/";


    /**
     * 覆盖的写
     */
    @Test
    public void write() throws IOException {
        String s = "123456789";
        FileIOUtil.writeFile(fileName, s, StandardCharsets.UTF_8.toString());
    }


    /**
     * 追加的写
     */
    @Test
    public void append() throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        for (int i = 0; i < 10; i++) {
            FileIOUtil.appendFile(fileName, "" + i + "\r\n", charset);
        }
    }


    /**
     * 写了5M的文件，没发现一行有俩数字的情况。目测线程安全。
     */
    @Test
    public void manyWrite() throws InterruptedException {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("CachedThreadPool-%d").build();
        ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        Charset charset = StandardCharsets.UTF_8;

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executor.submit(()->{
                while (true){
                    FileIOUtil.appendFile(fileName, "" + finalI + "\r\n", charset);
                }
            });
        }
        TimeUnit.MINUTES.sleep(5);
    }






    /**
     * 删除
     */
    @Test
    public void delete() {
        FileIOUtil.delete(fileName);
    }

    /**
     * 获取文件的路径
     */
    @Test
    public void getPath() {
        String path = FileIOUtil.getPath(fileName);
        System.out.println(path);

        path = FileIOUtil.getPath(this.path);
        System.out.println(path);
    }

    /**
     * 重命名
     */
    @Test
    public void rename() {
        FileIOUtil.renameFile(fileName, "a.txt");
    }


    /**
     * 获取目录下的所有文件名称
     */
    @Test
    public void fileUnderPath() {
        String path = "/Users/fang/Downloads/log/4.x/production/audit";
        Set<String> set = FileIOUtil.fileNameUnderPath(path);
        System.out.println(JsonUtils.parseObjToFormatJson(set));

        Set<File> fileUnderPath = FileIOUtil.fileUnderPath(path);
        fileUnderPath.forEach(file -> {
            String fileSize = FileIOUtil.getFileSize(file);
            String info = file.getName() + "   " + file.getAbsolutePath() + "  " + file.length() + "   " + fileSize;
            System.out.println(info);
        });
    }

    @Test
    public void out() {
        System.out.println(FileIOUtil.FILE_SEPARATOR);
        System.out.println(FileIOUtil.LINE_SEPARATOR);
    }

}
