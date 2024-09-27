package com.lxk.json.gson;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lxk.tool.util.JsonUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * gson隐藏的问题
 * 1，转对象后，整数被改成小数点了
 * 2，json中key重复，【会异常】
 *
 * @author LiXuekai on 2019/12/31
 */
public class GsonTest {
    private static final Gson GSON = new Gson();

    /**
     * json中key重复，【会异常】
     */
    @Test
    public void repeatKeyTest() {
        String json = "{\n" +
                "\"s.os\":\"....#\",\n" +
                "\"s.os\":\"....#\",\n" +
                "\"ssass\":\"\",\n" +
                "\"lxk\":123467983337654345\n" +
                "}";
        //map 或者 hash map 都失败com.google.gson.JsonSyntaxException: duplicate key: s.os
        Map jsonObj;
        try {
            jsonObj = GSON.fromJson(json, Map.class);

        } catch (Exception e) {
            if (e instanceof JsonSyntaxException) {
                System.out.println("----");
            }
            System.out.println(e.getMessage());
            jsonObj = JsonUtils.parseJsonToObj(json, Map.class);
        }
        System.out.println(jsonObj);
    }

    /**
     * 转对象后，整数被改成小数点了
     */
    @Test
    public void cast() {
        String s= "{\"abc\": false, \"ddd\": null, \"fff\": 1234}";
        Map map = GSON.fromJson(s, Map.class);
        // {abc=false, ddd=null, fff=1234.0}
        System.out.println(map);
    }

    @Test
    public void test() {
        String s = "";
        Map map = GSON.fromJson(s, HashMap.class);
        System.out.println(map.size());
        String s1 = JsonUtils.parseObjToJson(map);
        System.out.println(s1);
    }

    @Test
    public void nullTest() {
        String s= "{\"abc\": false, \"ddd\": null}";
        Map map = GSON.fromJson(s, Map.class);
        System.out.println(map);
    }

}
