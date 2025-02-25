package com.lxk.jdk.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2025/2/25
 */
public class TestTreeMap {

    @Test
    public void treeMap() {
        Map<String, Object> tree = Maps.newTreeMap();
        tree.put("a", null);
        tree.put("b", 100);
        tree.put("c", "200");
        tree.put("d", "    ");
        List<String> list = Lists.newArrayList("a","b","c","d","e");
        for (String s : list) {
            Object o = tree.get(s);
            if (o == null) {
                tree.put(s, "");
            } else {
                if (o instanceof String){
                    String trim = o.toString().trim();
                    tree.put(s, trim);
                }
            }
        }

        System.out.println(tree);
    }
}
