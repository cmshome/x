package com.lxk.jdk.collection;

import com.google.common.collect.Maps;
import com.lxk.bean.model.Bird;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * @author LiXuekai on 2024/5/11
 */
public class MapOrArrayTest {
    private Map<String, Object> map;


    @Before
    public void init(){
        map = map();

        array();
    }


    @Test
    public void run() {
        int max = 30000;
        String key = "6572d160d5dee6acfd12470c" + "20000" + "1715391817";
        long a = System.currentTimeMillis();
        while (max > 0){
            max--;
            map.get(key);
        }
        System.out.println("执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
    }

    private Map<String, Object> map() {
        Map<String, Object> map = Maps.newHashMap();
        for (int i = 0; i < 30000; i++) {
            map.put("6572d160d5dee6acfd12470c" + i + "1715391817", new Bird());
        }
        return map;
    }


    private void array() {

    }


}
