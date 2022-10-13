package com.lxk.jvm.memory;

import com.lxk.jvm.memory.model.JMM;
import com.lxk.tool.util.JsonUtils;
import org.junit.Test;

/**
 * main 方法
 *
 * @author LiXuekai on 2019/12/31
 */
public class MainTest {

    /**
     * 用对象的形式把JMM梳理一下
     */
    @Test
    public void jvm() {
        JMM jmm = new JMM();
        System.out.println(JsonUtils.parseObjToFormatJson(jmm));
    }
}
