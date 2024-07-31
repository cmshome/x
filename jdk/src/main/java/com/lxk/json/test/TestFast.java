package com.lxk.json.test;

import com.google.common.collect.Lists;
import com.lxk.tool.util.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2024/7/31
 */
public class TestFast {

    private final List<String> list = Lists.newArrayList();
    private final int max = 10000000;

    @Before
    public void init() {
        list.add("{\"dubbo_reg\":true,\"dubbo_2way\":true,\"dubbo_event\":false,\"dubbo_status\":0,\"dubbo_id\":92319085,\"dubbo_version\":\"2.0.2\",\"dubbo_service_name\":\"com.pactera.servicecenter.cif.microservice.CifsignQry\",\"dubbo_service_version\":\"1.0.0\",\"dubbo_method_name\":\"$invoke\",\"dubbo2_reg\":true,\"dubbo_22way\":true,\"dubbo_2event\":false,\"dubbo_s2tatus\":0,\"dubbo_2d\":92319085,\"dubbo_v2ersion\":\"2.0.2\",\"dubbo_se2rvice_name\":\"com.pactera.servicecenter.cif.microservice.CifsignQry\",\"dubbo_se2rvice_version\":\"1.0.0\",\"dubbo_method2_name\":\"$invoke\",\"transSeqNo\":\"1681904618967049048939\"}");
        list.add("{\"dubbo_req\":false,\"dubbo_2way\":false,\"dubbo_status\":20,\"dubbo_id\":92319085,\"RC\":\"00000000\",\"message\":\"交易成功\"}");
    }

    /**
     * gson         1.7
     * fastjson     18.1
     * jackson      80
     */
    @Test
    public void jprofiler() {
        while (true) {
            for (String s : list) {
                Map jack = JsonUtils.jacksonCast(s, HashMap.class);
                Map map = JsonUtils.fastjsonCast(s, Map.class);
                HashMap gson = JsonUtils.gsonCast(s, HashMap.class);
            }
        }
    }

    /**
     * fastJson 执行耗时 :      17.594 秒
     * jackson 执行耗时 :       25.565 秒
     * gson 执行耗时 :          29.741 秒
     */
    @Test
    public void fastByRunTime() {
        jackson();
        fastJson();
        gson();
    }

    private void jackson() {
        long a = System.currentTimeMillis();
        int i = max;
        while (i > 0) {
            for (String s : list) {
                Map jack = JsonUtils.jacksonCast(s, HashMap.class);
            }
            i--;
        }
        System.out.println("jackson 执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
    }

    private void fastJson() {
        long a = System.currentTimeMillis();
        int i = max;
        while (i > 0) {
            for (String s : list) {
                Map map = JsonUtils.fastjsonCast(s, Map.class);
            }
            i--;
        }
        System.out.println("fastJson 执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
    }

    private void gson() {
        long a = System.currentTimeMillis();
        int i = max;
        while (i > 0) {
            for (String s : list) {
                HashMap gson = JsonUtils.gsonCast(s, HashMap.class);
            }
            i--;
        }
        System.out.println("gson 执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
    }
}
