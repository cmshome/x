package com.lxk.json.fastjson.json.array;

import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * JSONArray test
 *
 * @author LiXuekai on 2019/9/24
 */
public class JSONArrayTest {


    @Test
    public void cast() {
        String s = "[a,d,f,g,r,h,t]";

        String s1 = s.substring(1, s.length()-1);
        System.out.println(Lists.newArrayList(s1.split(",")));
    }

}
