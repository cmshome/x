package com.lxk.jdk.file.write;

import com.lxk.tool.util.FileIOUtil;
import com.lxk.tool.util.JsonUtils;
import com.lxk.tool.util.StackTraceCollectUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author LiXuekai on 2024/7/11
 */
public class TestBigFile {
    private Map<String, Object> map;
    private final Charset charset = StandardCharsets.UTF_8;
    private final String fileName = "/Users/fang/Downloads/x.txt";
    private final String dirName = "/Users/fang/Downloads/infos";
    private final String timeField = "Timestamp";


    @Before
    public void init() {
        String s = "";
        map = JsonUtils.parseJsonToObj(s, Map.class);
    }

    /**
     * 读一行100万个a的文件，能读出来，下面程序正常执行。
     */
    @Test
    public void load() {
        List<String> list = FileIOUtil.readFileByLine(fileName, false);
        System.out.println(list.size());
        String s = list.get(0);
        System.out.println(s.length());
    }

    /**
     * 一行写100万个a，是1M大小的文件。
     */
    @Test
    public void writeBig() throws IOException {
        long start = 1577808000000L;
        int i = 0;
        while (i < 100000000) {
            map.put(timeField, start + i + "");
            String json = JsonUtils.parseObjToJson(map) + "\r\n";
            FileIOUtil.appendFile(fileName, json, charset);
            i++;
        }
    }


    @Test
    public void readByLine() {
        long interval = 60 * 1000;
        FileIOUtil.mkdirIfNotExist(dirName);
        try (Scanner scanner = new Scanner(new File(fileName), StandardCharsets.UTF_8.name())) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Map json = JsonUtils.parseJsonToObj(line, Map.class);
                if (json == null) {
                    continue;
                }
                String s = (String) json.get(timeField);
                long time = Long.parseLong(s);
                long l = time / interval * interval;
                String temp = dirName + "/" + l + ".json";
                FileIOUtil.appendFile(temp, line + "\r\n", charset);
            }
        } catch (Exception e) {
            System.out.println(StackTraceCollectUtil.collectStackTrace(e));
        }
    }
}
