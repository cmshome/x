package com.lxk.json.test;

import com.lxk.tool.util.JsonUtils;
import org.junit.Test;

import java.util.Map;

/**
 * 测试json转换
 *
 * @author LiXuekai on 2021/5/12
 */
public class JsonTest {

    @Test
    public void testNull() {
        String s= "{\"abc\": false, \"ddd\": null}";
        Map map = JsonUtils.parseJsonToObj(s, Map.class);
        System.out.println(map.size());
    }
}
