package com.lxk.jdk.collection;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lxk on 2017/4/11
 */
public class HashMapTest {
    String SPLIT = ".";

    @Test
    public void putAllEmpty() {
        Map<String, String> map = Maps.newHashMap();
        HashMap<String, String> maps = Maps.newHashMap();

        map.putAll(maps);
        System.out.println(map);

    }

    /**
     * 测试Iterator迭代器
     */
    @Test
    public void testIterator() {
        HashMap<String, String> map = Maps.newHashMap();
        map.put("a", "one");
        map.put("b", "two");
        map.put("c", "three");
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 测试remove一个不存在的key，会发生什么情况。结果：正常运行，无异常。
     */
    @Test
    public void removeTest() {
        HashMap<String, String> map = Maps.newHashMap();
        map.put("a", "one");
        map.put("b", "two");
        map.put("c", "three");

        map.remove("sss");
        System.out.println(map.size());
    }

    @SuppressWarnings("unchecked")
    public Object getMapValue(Map<String, Object> map, String key) {
        // 指标中有点，表示有下一级
        if (key.contains(SPLIT)) {
            int pos = key.indexOf(SPLIT);
            String subKey = key.substring(0, pos);
            Map<String, Object> subMap = (Map<String, Object>) map.get(subKey);
            return getMapValue(subMap, key.substring(pos + 1));
        }
        return map != null ? map.get(key) : null;
    }

    @Test
    public void get() {
        HashMap<String, Object> subMap = Maps.newHashMap();
        subMap.put("dd", "sssss");

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("a", subMap);
        map.put("b", "two");
        map.put("c", "three");
        Object value = getMapValue(map, "a.dd");
        System.out.println(value);
    }

    @Test
    public void dots() {
        String s = "a.b";
        if (s.contains(".")){
            System.out.println(s);
        }
    }


}
