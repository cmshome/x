package com.lxk.jvm;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

/**
 * @author LiXuekai on 2021/11/17
 */
public class GetJVMInfo {

    @Test
    public void a() {

        MemoryMXBean mxBean = ManagementFactory.getMemoryMXBean();
        System.out.println("堆内存信息: " + mxBean.getHeapMemoryUsage());
        System.out.println("方法区内存信息: " + mxBean.getNonHeapMemoryUsage());


        List<String> inputArgs = ManagementFactory.getRuntimeMXBean().getInputArguments();
        inputArgs.forEach(System.out::println);

        long totle = Runtime.getRuntime().totalMemory();
        System.out.println("总的内存量 [" + totle + "]");
        long free = Runtime.getRuntime().freeMemory();
        System.out.println("空闲的内存量 [" + free + "]");
        long max = Runtime.getRuntime().maxMemory();
        System.out.println("最大的内存量 [" + max + "]");
    }

    @Test
    public void b() {
        MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memorymbean.getHeapMemoryUsage();
        System.out.println("=======================通过 management 来获取相关系统状态============================ ");
        System.out.println("INIT HEAP: " + usage.getInit() / 1024 / 1024 + "m");
        System.out.println("MAX HEAP: " + usage.getMax() / 1024 / 1024 + "m");
        System.out.println("USE HEAP: " + usage.getUsed() / 1024 / 1024 + "m");
        System.out.println("\nFull Information:");
        System.out.println("Heap Memory Usage: " + memorymbean.getHeapMemoryUsage());
        System.out.println("Non-Heap Memory Usage: " + memorymbean.getNonHeapMemoryUsage());

        System.out.println("=======================通过 Runtime 来获取相关系统状态============================ ");
        System.out.println("当前堆内存大小totalMemory " + (int) Runtime.getRuntime().totalMemory() / 1024 / 1024 + "m");// 当前堆内存大小
        System.out.println("空闲堆内存大小freeMemory " + (int) Runtime.getRuntime().freeMemory() / 1024 / 1024 + "m");// 空闲堆内存大小
        System.out.println("最大可用总堆内存maxMemory " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "m");// 最大可用总堆内存大小

    }
}
