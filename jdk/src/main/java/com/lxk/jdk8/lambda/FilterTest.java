package com.lxk.jdk8.lambda;

import com.lxk.tool.util.CollectionUtil;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LiXuekai on 2022/12/8
 */
public class FilterTest {

    @Test
    public void mapFilter() {
        Map<String, String> map = CollectionUtil.getMap(5);
        map.put("", "");
        System.out.println(map.size());
        map.remove("");
        System.out.println(map.size());
    }


    @Test
    public void listFilter() {
        List<String> list = CollectionUtil.getArrayList(5);
        list.add("");
        list.add(null);
        System.out.println(list.size());
        List<String> collect = list.stream().filter(Objects::nonNull).collect(Collectors.toList());
        System.out.println(collect.size());
    }

    @Test
    public void filterTreeMap() {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("A", 1);
        treeMap.put("B", 2);
        treeMap.put("C", 3);
        treeMap.put("D", 4);

        // 使用lambda过滤掉值小于3的条目
        Map<String, Integer> filteredMap = treeMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue() >= 3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        TreeMap::new
                ));

        System.out.println(filteredMap);
    }


    @Test
    public void filterTreeMap2() {
        TreeMap<String, Integer> originalMap = new TreeMap<>();
        originalMap.put("A", 5);
        originalMap.put("B", 3);
        originalMap.put("C", 8);

        TreeMap<String, Integer> modifiedMap = originalMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() - 1,
                        (oldValue, newValue) -> oldValue,
                        TreeMap::new
                ));

        System.out.println("原始Map: " + originalMap);
        System.out.println("修改后Map: " + modifiedMap);
    }

    @Test
    public void filterTreeMap3() {
        TreeMap<String, Integer> originalMap = new TreeMap<>();
        originalMap.put("A", 5);
        originalMap.put("B", 3);
        originalMap.put("C", 8);
        originalMap.put("D", 1);

        // 先减1再过滤值大于0的条目
        TreeMap<String, Integer> resultMap = originalMap.entrySet()
                .stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey(),
                        entry.getValue() - 1))
                .filter(entry -> entry.getValue() > 0)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        TreeMap::new
                ));

        System.out.println("处理后结果: " + resultMap);
    }


    /**
     * 先过滤再计算，减少不必要的计算
     * 移除了中间SimpleEntry对象的创建
     * 显式传递原TreeMap的比较器
     * 更简洁的lambda表达式
     */
    @Test
    public void better() {
        TreeMap<String, Integer> originalMap = new TreeMap<>();
        originalMap.put("A", 5);
        originalMap.put("B", 3);
        originalMap.put("C", 8);
        originalMap.put("D", 1);

        TreeMap<String, Integer> resultMap = originalMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)  // 先过滤减少后续操作
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() - 1,  // 直接计算新值
                        (oldValue, newValue) -> oldValue,
                        () -> new TreeMap<>(originalMap.comparator())  // 保持原比较器
                ));
    }
}
