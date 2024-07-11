package com.lxk.jdk.file.write;

import com.lxk.tool.util.FileIOUtil;
import com.lxk.tool.util.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

/**
 * @author LiXuekai on 2024/7/11
 */
public class TestBigFile {
    private Map<String, Object> map;
    private final Charset charset = StandardCharsets.UTF_8;
    private final String fileName = "/Users/fang/Downloads/x.txt";


    @Before
    public void init() {
        String s = "";
        map = JsonUtils.parseJsonToObj(s, Map.class);
    }

    @Test
    public void writeBig() throws IOException {
        long start = 1577808000000L;
        int i = 0;
        while (i < 100000000) {
            map.put("timestamp", start + i);
            String json = JsonUtils.parseObjToJson(map) + "\r\n";
            FileIOUtil.appendFile(fileName, json, charset);
            i++;
        }
    }


    @Test
    public void readByLine() {
        try (Scanner scanner = new Scanner(new File(fileName), StandardCharsets.UTF_8.name())) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Map json = JsonUtils.parseJsonToObj(line, Map.class);
                System.out.println(json.get("timestamp"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
