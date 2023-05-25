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
        String s= "{\"abc\": false, \"ddd\": null, null:null}";
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
    public void x() {
        String x = "[\n" +
                "\"5563dc7af045ceb2c69808d0b955\"\n" +
                ",\n" +
                "\"5163dc7af045ceb2c69808d0cb51\"\n" +
                "]";


        System.out.println(x);
        List<String> strings = JsonUtils.parseJsonToArrayObj(x, String.class);
        System.out.println(strings.size());
    }
}
