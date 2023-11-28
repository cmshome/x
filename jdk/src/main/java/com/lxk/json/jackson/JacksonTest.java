package com.lxk.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.lxk.json.model.Cup;
import com.lxk.tool.util.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2019/12/31
 */
public class JacksonTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private String json;

    @Before
    public void init() {
        json = Cup.getArrayJson();
    }

    /**
     * json中key重复，【不会异常】
     */
    @Test
    public void repeatKeyTest() throws IOException {
        String json = "{\n" +
                "\"s.os\":\"....#\",\n" +
                "\"s.os\":\"..3456..#\",\n" +
                "\"ssass\":\"\",\n" +
                "\"lxk\":123467987654345\n" +
                "}";

        //map 或者 hash map 都 ok
        Map jsonObj = mapper.readValue(json, HashMap.class);
        System.out.println(jsonObj);
    }

    @Test
    public void emptyKey() throws JsonProcessingException {
        String s = "{\"\":\"asd\",\"aa\":\"\"}";
        Map jsonObj = mapper.readValue(s, Map.class);
        System.out.println(jsonObj);
    }

    /**
     * com.fasterxml.jackson.databind.JsonMappingException:
     * Null key for a Map not allowed in JSON (use a converting NullKeySerializer?)
     * (through reference chain: java.util.HashMap["null"])
     */
    @Test
    public void castToJson() throws JsonProcessingException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("aaa", 134);
        map.put("", 567);
        map.put(null, "adsad");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(map);
        System.out.println(jsonString);
    }

    @Test
    public void test() throws IOException {
        String s = "";
        Map map = mapper.readValue(s, HashMap.class);
        System.out.println(map.size());
    }


    @Test
    public void parseArray() throws IOException {
        List<Cup> list = mapper.readValue(json, new TypeReference<List<Cup>>() {
        });
        list.forEach(cup -> {
            System.out.println(cup.toString());
        });
    }

    @Test
    public void parseArray2() {
        List<Cup> list = JsonUtils.parseJsonToArrayObj(json, Cup.class);
        list.forEach(cup -> {
            System.out.println(cup.toString());
        });
    }

    /**
     * 自动转换之后，数据类型，有些会呗改成 BigDecimal，不是我想的double或者float。
     */
    @Test
    public void json2map() {
        String json = "{\n" +
                "    \"a\":0,\n" +
                "    \"b\":15,\n" +
                "    \"c\":15,\n" +
                "    \"dd\":3324.33,\n" +
                "    \"e\":0,\n" +
                "    \"f\":0,\n" +
                "    \"g\":1\n" +
                "}";

        /*
            "a" -> {Integer@1304} 0
            "b" -> {Integer@1321} 15
            "c" -> {Integer@1321} 15
            "dd" -> {Double@1324} 3324.33
            "e" -> {Integer@1304} 0
            "f" -> {Integer@1304} 0
            "g" -> {Integer@1328} 1
         */

        //自动转换之后，数据类型，有些会呗改成 BigDecimal，不是我想的double或者float。
        Map map = JsonUtils.jacksonCast(json, Map.class);
        Object a = map.get("a");
        System.out.println(a);
    }
}
