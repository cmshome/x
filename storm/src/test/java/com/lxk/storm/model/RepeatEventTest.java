package com.lxk.storm.model;

import com.lxk.tool.util.JsonUtils;
import org.apache.storm.shade.com.google.common.collect.Maps;
import org.junit.Test;

import java.util.TreeMap;

/**
 * @author LiXuekai on 2020/9/25
 */
public class RepeatEventTest {

    @Test
    public void test() {

        TreeMap<String, Integer> treeMap = Maps.newTreeMap();

        treeMap.put("account", 3);
        treeMap.put("amountRange", 2);
        treeMap.put("amountSpecial", 1);
        treeMap.put("time", 4);
        RepeatEvent event = RepeatEvent.builder()
                .dataId("xxx")
                .count(10)
                .risk(treeMap)
                .build();
        System.out.println(JsonUtils.parseObjToJson(event));


    }
}
