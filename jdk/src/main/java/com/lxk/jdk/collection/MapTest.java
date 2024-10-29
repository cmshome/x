package com.lxk.jdk.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lxk.tool.util.CollectionUtil;
import com.lxk.tool.util.JsonUtils;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * map test
 *
 * @author LiXuekai on 2020/1/4
 */
public class MapTest {


    /**
     * 测试 replace 点
     */
    @Test
    public void replace() {
        Map<String, String> map = Maps.newHashMap();
        map.put("s.s", "yy");
        map.put("a.a", "yy");
        map.put("b.b", "yy");
        map.put("c.c", "yy");

        map.remove("a");
        map.remove("b");
        map.remove("c");
        Map<String, String> map2 = Maps.newHashMap();
        for (String key : map.keySet()) {
            map2.put(key.replace(".", "_"), map.get(key));
        }
        System.out.println(JsonUtils.parseObjToJson(map2));
    }

    /**
     * put return old value
     */
    @Test
    public void putTest() {
        Map<String, String> map = Maps.newHashMap();
        String put = map.put("ss", "yy");
        System.out.println(put);
        put = map.put("ss", "zz");
        System.out.println(put);
    }

    /**
     * 在遍历的时候删除元素会出现下面的异常
     * java.util.ConcurrentModificationException
     */
    @Test
    public void remove() {

        Map<String, String> map = CollectionUtil.getMap(5);
        System.out.println(map.toString());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals("1")) {
                map.remove("1");
            }
        }
        System.out.println(map.toString());
    }

    /**
     * ConcurrentHashMap 这个就不报异常了。。。
     */
    @Test
    public void remove1() {
        Map<String, String> map = Maps.newConcurrentMap();
        for (int i = 0; i < 5; i++) {
            map.put(Integer.toString(i), Integer.toString(i));
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals("1")) {
                map.remove("1");
            }
        }
        System.out.println(map);
    }

    /**
     * hashmap没有 list似的有个  removeIf
     */
    @Test
    public void removeIf() {
        Map<String, String> map = CollectionUtil.getMap(5);
        System.out.println(map.toString());
    }

    /**
     * 使用迭代器，在循环map的时候删除某个元素，线程安全。
     */
    @Test
    public void iteratorRemove() {
        Map<String, String> map = CollectionUtil.getMap(5);
        System.out.println(map.toString());
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            if ("1".equals(value)) {
                iterator.remove();
            }
        }
        System.out.println(map.toString());
    }

    @Test
    public void removeOne() {
        Map<String, String> map = CollectionUtil.getMap(5);
        map.remove("9");
        System.out.println(map.toString());
    }


    @Test
    public void test() {
        Map<String, String> map1 = Maps.newHashMap();
        map1.put("a", "a");
        Map<String, String> map2 = Maps.newHashMap();
        deadFor(map1, map2);
    }

    /**
     * 使用迭代器去循环的时候，竟然是死循环。。。
     * 因为迭代器一直呆在原地，没有移动。
     */
    private void deadFor(Map<String, String> map1, Map<String, String> map2) {
        Iterator<String> iterator = map1.keySet().iterator();
        while (iterator.hasNext()) {
            for (String s : map2.keySet()) {
                if (iterator.next().equals(s)) {
                    iterator.remove();
                }
            }
        }
    }

    @Test
    public void MapFindFirst() {
        Map<String, String> map = CollectionUtil.getMap(0);
        Optional<String> first = map.keySet().stream().findFirst();
        System.out.println(first.orElse("-1"));
    }


    /**
     * 把map拆成n个
     */
    @Test
    public void splitMap() {
        Map<String, String> statMap = CollectionUtil.getMap(16);
        List<List<String>> result = Lists.newArrayList();
        List<String> list = Lists.newArrayList();
        for (String value : statMap.values()) {
            list.add(value);
            if (list.size() % 10 == 0) {
                result.add(list);
                list = Lists.newArrayList();
            }
        }
        if (list.size() > 0){
            result.add(list);
        }
        System.out.println(result);
    }

    /**
     * key 在，value = null fastjson序列化后，null value的key就没了
     */
    @Test
    public void nullValue() {
        Map<String, String> map = Maps.newHashMap();
        String a = map.put("a", null);
        // {a=null}
        System.out.println(map);
        // {}
        System.out.println(JsonUtils.parseObjToJson(map));

    }
}
