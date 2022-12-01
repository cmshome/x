package com.lxk.tool.monitor.model;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.lxk.tool.util.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author LiXuekai on 2020/12/15
 */
public class MonitorThread implements Runnable {
    private final Map<String, AtomicLong> map = Maps.newConcurrentMap();
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private boolean stop = false;


    public MonitorThread() {
    }

    public MonitorThread(String name) {
        this.name = name;
    }

    public void getAndIncrement(String key) {
        if (Strings.isNullOrEmpty(key)) {
            return;
        }
        AtomicLong atomicLong = map.get(key);
        if (atomicLong == null) {
            atomicLong = new AtomicLong();
        }
        atomicLong.getAndIncrement();
        map.put(key, atomicLong);
    }

    @SneakyThrows
    @Override
    public void run() {
        if (stop) {
            System.out.println("i will stop right now.");
            throw new Exception("xxxxx");
        }

        mapLog();
        clearMap();
    }

    /**
     * 打印监测日志
     */
    private void mapLog() {
        TreeMap<String, Long> info = Maps.newTreeMap();
        map.forEach((k, v) -> info.put(k, v.get()));
        System.out.println(name + " monitor info is " + JsonUtils.parseObjToJson(info));
    }

    /**
     * 日志输出之后，清空缓存的统计信息。
     */
    private void clearMap() {
        map.clear();
    }

}
