package com.lxk.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.lxk.json.model.Cup;
import com.lxk.tool.util.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2019/12/31
 */
public class FastJsonTest {

    @Before
    public void init(){
        // 关闭 BigDecimal 使用 double  fastjson缺省反序列化带小数点的数值类型为 BigDecimal； 整数，默认为Integer
        JSON.DEFAULT_PARSER_FEATURE &= ~Feature.UseBigDecimal.getMask();
    }

    /**
     * json中key重复，【不会异常】
     */
    @Test
    public void repeatKeyTest() {
        String json = "{\n" +
                "\"s.os\":\"....#\",\n" +
                "\"s.os\":\".23456...#\",\n" +
                "\"ssass\":\"\",\n" +
                "\"lxk\":123467987654345\n" +
                "}";
        //Map 类可以， hash map 不 OK。
        Map map = JsonUtils.parseJsonToObj(json, Map.class);
        System.out.println(map);
    }


    /**
     * 因为key之前多了个 \ 导致解码失败了。
     * fastjson 右斜杠 简单粗暴的直接给他replace了吧。
     */
    @Test
    public void unclosedString() {
        String json = "{\"s.os\":\"李\\学23456...#\",\"ssass\":\"\",\"lxk\":123467987654345}";
        System.out.println(json);
        //Map 类可以， hash map 不 OK。
        Map map = JsonUtils.parseJsonToObj(json, Map.class);
        System.out.println(map);
    }

    /**
     * 因为key之前多了个 \ 导致解码失败了。
     * 不管多少个反斜杠，简单粗暴的给他全替换了得了
     * StringEscapeUtils.unescapeJavaScript(json);
     * 这个不太好使。
     */
    @Test
    public void unclosedString2() {
        String json = "{\"s.os\":\"李\\\\\\\\\\\\学\\十大!@#$%^&*()))_+{}:?><{}][|||||{{{}}}}[][][][[[]]]:::;;;''''''\\~`表代表///////23456...#\",\"ssass\":\"\",\"lxk\":123467987654345}";
        System.out.println(json);
        //Map 类可以， hash map 不 OK。
        Map map = JsonUtils.parseJsonToObj(json, Map.class);
        System.out.println(map);
    }

    /**
     * json中key是 @type 的，fastjson会解析异常。
     */
    @Test
    public void t() {
        String s = "";
        Map map = JsonUtils.parseJsonToObj(s, Map.class);
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        System.out.println(map.size());
    }

    /**
     * json中key是 @type 的，fastjson会解析异常。
     */
    @Test
    public void ss() {
        String json = "";
        JSONObject jsonObject = JSON.parseObject(json);
    }

    /**
     * json中key是 @type 的，fastjson会解析异常。这个对象的map里面的可以是 @type导致的异常。
     * fastjson 升级之后，这个问题不报错了
     */
    @Test
    public void parseArray() {
        String json = Cup.getArrayJson();
        List<Cup> list = JSON.parseArray(json, Cup.class);
        list.forEach(cup -> {
            System.out.println(cup.toString());
        });
    }


    /**
     * 自动转换之后，数据类型，有些会被改成 BigDecimal，不是我想的double或者float。
     * 原因：
     * fastjson缺省反序列化带小数点的数值类型为 BigDecimal； 整数，默认为Integer
     */
    @Test
    public void json2map() {
        String json = "{\n" +
                "    \"a\":0,\n" +
                "    \"b\":2236322850,\n" +
                "    \"bb\":15.,\n" +
                "    \"c\":2147483647,\n" +
                "    \"dd\":3324.33,\n" +
                "    \"e\":2147483647000,\n" +
                "    \"f\":0,\n" +
                "    \"g\":1\n" +
                "}";

        /*
            "dd" -> {BigDecimal@1195} "3324.33"
            "a" -> {Integer@1181} 0
            "b" -> {Integer@1198} 15
            "c" -> {Integer@1198} 15
            "e" -> {Integer@1181} 0
            "f" -> {Integer@1181} 0
            "g" -> {Integer@1203} 1
         */

        //自动转换之后，小数类型的值会被改成 BigDecimal，不是我想的double或者float。
        Map map = JsonUtils.fastjsonCast(json, Map.class);
        Object a = map.get("b");
        System.out.println(a);
    }
}
