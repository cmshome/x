package com.lxk.jar;

import org.junit.Test;

import java.io.*;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class Client {

    @Test
    public void jar() {
        String dir = "/Users/fang/Downloads/test/temp";
        String jar = "/Users/fang/Downloads/test/test.jar";
        compressFileToJar(dir, jar);
    }

    /**
     * 读取文件内容并压缩
     *
     * @param filePath 文件路径
     */
    private static void compressFileToJar(String filePath, String jarFilePath) {
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFilePath))) {
            //递归的压缩文件夹和文件
            doCompress("", filePath, jos);
            //必须
            jos.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doCompress(String parentFilePath, String filePath, JarOutputStream jos) {
        File sourceFile = new File(filePath);
        if (!sourceFile.exists()) {
            return;
        }
        String jarEntryName = parentFilePath + "/" + sourceFile.getName();
        if (parentFilePath.isEmpty()) {
            jarEntryName = sourceFile.getName();
        }
        if (sourceFile.isDirectory()) {
            File[] childFiles = sourceFile.listFiles();
            if (Objects.isNull(childFiles)) {
                return;
            }
            for (File childFile : childFiles) {
                doCompress(jarEntryName, childFile.getAbsolutePath(), jos);
            }
        } else {
            int len = -1;
            byte[] buf = new byte[1024];
            jarEntryName = jarEntryName.substring(jarEntryName.indexOf("/") + 1);
            try (InputStream input = new BufferedInputStream(new FileInputStream(sourceFile))) {
                jos.putNextEntry(new JarEntry(jarEntryName));
                while ((len = input.read(buf)) != -1) {
                    jos.write(buf, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
