package com.lxk.tool.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lxk.tool.model.OrderedProperties;
import lombok.Cleanup;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * FileIOUtil
 *
 * @author lxk
 */
public final class FileIOUtil {
    private static final long kB = 1024L;
    private static final long MB = kB * 1024L;
    private static final long GB = MB * 1024L;
    private static final long TB = GB * 1024L;

    /**
     * 系统换行符
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    /**
     * 路径分隔符
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");


    public static void delete(String fileName) {
        File file = new File(fileName);
        delete(file);
    }

    public static void delete(File file) {
        if (!file.exists()) {
            return;
        }
        // deleteOnExit() 调用后,不会立即删除,会等到虚拟机正常运行结束后,才去删除
        boolean delete = file.delete();
    }

    /**
     * 删除目录以及子文件
     */
    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return true;
            }
            for (File f : files) {
                deleteFile(f);
            }
        }
        return file.delete();
    }

    /**
     * 获取目录下的所有文件名称集合
     *
     * @param path 路径
     * @return 文件名称集合
     */
    public static Set<String> fileNameUnderPath(String path) {
        Set<String> set = Sets.newHashSet();
        Set<File> files = fileUnderPath(path);
        for (File one : files) {
            set.add(one.getName());
        }
        return set;
    }

    /**
     * 获取目录下的所有文件集合
     *
     * @param path 路径
     * @return 文件名称集合
     */
    public static Set<File> fileUnderPath(String path) {
        Set<File> set = Sets.newHashSet();
        File file = new File(path);
        if (!file.exists()) {
            return set;
        }
        File[] files = file.listFiles();
        if (files == null) {
            return set;
        }
        if (files.length == 0) {
            return set;
        }
        set.addAll(Arrays.asList(files));
        return set;
    }

    public static String getPath(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }

        if (file.isDirectory()) {
            return file.getPath();
        }
        return file.getParent();
    }

    public static String readFile(String fileName, String charset) throws IOException {
        File f = new File(fileName);
        return readFile(f, charset);
    }

    public static String readFile(File f, String charset) throws IOException {
        try (RandomAccessFile readStream = new RandomAccessFile(f, "r")) {
            byte[] buffer = new byte[(int) f.length()];
            readStream.read(buffer, 0, buffer.length);
            return new String(buffer, charset);
        }
    }

    /**
     * 一行行的读文件
     *
     * @param path            path
     * @param ignoreFirstLine 第一行是标题不是数据，给剔除掉否？。
     * @param charset         charset
     */
    public static List<String> readFileByLine(String path, boolean ignoreFirstLine, String charset) {
        List<String> list = Lists.newArrayList();
        try (Scanner scanner = new Scanner(new File(path), charset)) {
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int size = list.size();
        return ignoreFirstLine ? list.subList(1, size) : list;

    }

    /**
     * 一行行的读文件
     *
     * @param path            path
     * @param ignoreFirstLine 第一行是标题不是数据，给剔除掉否？。
     */
    public static List<String> readFileByLine(String path, boolean ignoreFirstLine) {
        return readFileByLine(path, ignoreFirstLine, StandardCharsets.UTF_8.name());
    }

    public static void writeFile(String fileName, String text, String charset) throws IOException {
        File f = new File(fileName);
        try (RandomAccessFile readStream = new RandomAccessFile(f, "rw")) {
            readStream.setLength(0);
            byte[] buffer = text.getBytes(charset);
            readStream.write(buffer, 0, buffer.length);
        }
    }

    public static void appendFile(String fileName, String text, Charset charset) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            boolean hasFile = file.createNewFile();
            if (hasFile) {
                System.out.println("file not exists, create new file");
            }
        }
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset, StandardOpenOption.APPEND)) {
            writer.write(text);
        }
    }

    /**
     * 文件重命名
     *
     * @param oldName 旧名字
     * @param newName 新名字
     */
    public static boolean renameFile(String oldName, String newName) {
        File oldFile = new File(oldName);
        return oldFile.renameTo(new File(newName));
    }

    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        return loadProperties(properties, fileName);
    }

    public static Properties loadOrderedProperties(String fileName) {
        Properties orderedProperties = new OrderedProperties();
        return loadProperties(orderedProperties, fileName);
    }

    private static Properties loadProperties(Properties properties, String fileName) {
        try {
            @Cleanup InputStream inputStream = new FileInputStream(fileName);
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void mkdirIfNotExist(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
        }
    }

    /**
     * return 单位(B)
     */
    public static long getDirFileSize(File file) {
        long fileSize = 0L;
        //判断文件是否存在
        if (!file.exists()) {
            return fileSize;
        }
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File f : children) {
                    fileSize += getDirFileSize(f);
                }
            }
        } else {
            fileSize = file.length();
        }
        return fileSize;
    }

    /**
     * 获取文件大小
     *
     * @param file file
     * @return 文件大小 B KB MB GB TB
     */
    public static String getFileSize(File file) {
        if (file == null) {
            return null;
        }
        long length = file.length();
        return getFileSize(length);
    }

    public static String getFileSize(long length) {
        if (length < kB) {
            return length + " B";
        } else if (length < MB) {
            return divide(length, kB) + " K";
        } else if (length < GB) {
            return divide(length, MB) + " M";
        } else if (length < TB) {
            return divide(length, GB) + " G";
        } else {
            return divide(length, TB) + " G";
        }
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return 两个参数的商
     */
    public static Double divide(long dividend, long divisor) {
        BigDecimal b1 = BigDecimal.valueOf(dividend);
        BigDecimal b2 = BigDecimal.valueOf(divisor);
        return b1.divide(b2, 1, RoundingMode.UP).doubleValue();
    }
}
