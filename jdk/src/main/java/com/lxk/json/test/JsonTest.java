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
        if (map == null) {
            return;
        }
        // 2
        System.out.println(map.size());

        // value == null 的在序列化的时候，直接无了。运行结果：{"abc":false}
        System.out.println(JsonUtils.parseObjToJson(map));
    }
}
