package com.lxk.json.test;

import com.google.common.collect.Lists;
import com.lxk.tool.util.FileIOUtil;
import com.lxk.tool.util.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2024/7/31
 */
public class TestFast {

    private List<String> list = Lists.newArrayList();
    private final int max = 10;

    @Before
    public void init() {
        // 10万行json字符串，每行的value都不一样
        String path = "/Users/fang/Downloads/x.txt";
        list = FileIOUtil.readFileByLine(path, false);
        System.out.println("list size = " + list.size());
    }

    /**
     * gson         1.7
     * fastjson     18.1
     * jackson      80
     */
    @Test
    public void jprofiler() {
        while (true) {
            for (String s : list) {
                Map jack = JsonUtils.jacksonCast(s, HashMap.class);
                Map map = JsonUtils.fastjsonCast(s, Map.class);
                HashMap gson = JsonUtils.gsonCast(s, HashMap.class);
            }
        }
    }

    /**
     * fastJson 执行耗时 : 3.056 秒
     * jackson 执行耗时 : 3.732 秒
     * gson 执行耗时 : 3.845 秒
     */
    @Test
    public void fastByRunTime() {
        fastJson();
        jackson();
        gson();
    }

    private void jackson() {
        long a = System.currentTimeMillis();
        int i = max;
        while (i > 0) {
            for (String s : list) {
                Map jack = JsonUtils.jacksonCast(s, HashMap.class);
            }
            i--;
        }
        System.out.println("jackson 执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
    }

    private void fastJson() {
        long a = System.currentTimeMillis();
        int i = max;
        while (i > 0) {
            for (String s : list) {
                Map map = JsonUtils.fastjsonCast(s, Map.class);
            }
            i--;
        }
        System.out.println("fastJson 执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
    }

    private void gson() {
        long a = System.currentTimeMillis();
        int i = max;
        while (i > 0) {
            for (String s : list) {
                HashMap gson = JsonUtils.gsonCast(s, HashMap.class);
            }
            i--;
        }
        System.out.println("gson 执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
    }
}
