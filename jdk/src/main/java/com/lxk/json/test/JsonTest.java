package com.lxk.json.test;

import com.lxk.tool.util.JsonUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * 测试json转换
 *
 * @author LiXuekai on 2021/5/12
 */
public class JsonTest {

    @Test
    public void testNull() {
        String s = "{\"abc\": false, \"ddd\": null, null:null}";
        Map map = JsonUtils.parseJsonToObj(s, Map.class);
        if (map == null) {
            return;
        }
        // 2
        System.out.println(map.size());

        // value == null 的在序列化的时候，直接无了。运行结果：{"abc":false}
        // key == null 运行结果：key的null给干成 字符串的null，因value也是null，所以也不展示。
        System.out.println(JsonUtils.parseObjToJson(map));
    }


    @Test
    public void list() {
        String json = "[\"650155d684ae59bb236ce3fa\",\"655c602995ca5efa5c8a0d5f\",\"65487cdf84ae4dde4f6d7fd9\"]";
        List<String> strings = JsonUtils.parseJsonToArrayObj(json, String.class);
        System.out.println(strings);
    }

    @Test
    public void array() {
        String json = "{\"d\":[\"5b514dc421acdd3860d29fd1\"],\"e\":2000}";
        Map map = JsonUtils.parseJsonToObj(json, Map.class);
        Object o = map.get("d");
        if (o instanceof List) {
            List<String> streamIds = (List<String>) o;
            System.out.println();

        }
    }

}
