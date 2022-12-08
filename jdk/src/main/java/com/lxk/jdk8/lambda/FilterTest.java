package com.lxk.jdk8.lambda;

import com.lxk.tool.util.CollectionUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
}
