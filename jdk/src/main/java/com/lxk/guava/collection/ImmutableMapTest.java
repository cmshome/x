package com.lxk.guava.collection;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * guava ImmutableMap 测试实例
 * <p>
 *
 * @author lxk on 2016/11/14
 */
public class ImmutableMapTest {
    Map<Integer, String> INTEGER_STRING_MAP =
            new ImmutableMap.Builder<Integer, String>()

                    .put(30, "IP地址或地址段")
                    .put(31, "端口号或范围")
                    .put(32, "IP地址或地址段")
                    .put(33, "端口号或范围")
                    .put(34, "代码值")
                    .put(38, "探针名称")
                    .put(39, "网络协议号(protocol)")
                    .put(40, "ipv6源IP(ipv6_src_addr)")
                    .put(41, "ipv6目标IP(ipv6_dst_addr)")
                    .put(42, "网络协议名称(protocol_map)")
                    .put(43, "输入接口snmp(input_snmp)")

                    .build();

    /**
     * 这个最多支持5对，不能再多啦。
     * 后期又扩展了，竟然支持10对了。
     */
    ImmutableMap<String, String> MAP = ImmutableMap.of("key1", "value1", "key2", "value2");

    ImmutableMap<String, String> MAP_ = ImmutableMap.copyOf(Maps.newHashMap());

    /**
     * 测试 guava ImmutableMap
     */
    @Test
    public void immutableMapTest() {
        Integer keyValue = 30;
        System.out.println("keyValue = " + keyValue + "的提示语是：" + INTEGER_STRING_MAP.get(keyValue));

        MAP.forEach((key, value) -> System.out.println("key：" + key + " value：" + value));
    }

    @Test
    public void initMap() {
        ImmutableMap<String, String> of = ImmutableMap.of("1", "1", "2", "2");

        // 不可变集合，不让写操作，只能读
        //of.put("3","3");
        //of.remove("3","3");

        HashMap<String, String> map = Maps.newHashMap();
        map.putAll(of);
        map.remove("1");
        map.remove("2");
        System.out.println(map.size());
    }

}