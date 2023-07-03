package com.lxk.jdk.collection;

import com.google.common.collect.Lists;
import com.lxk.tool.util.CollectionUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * List 相关方法的测试
 *
 * @author lxk on 2017/3/13
 */
public class ListTest {

    /**
     * ConcurrentModificationException 异常的三种常见情况
     */
    private static void concurrentModificationException() {

    }

    /**
     * 左闭右开
     */
    @Test
    public void subListTest() {
        List<String> list = Lists.newArrayList("0", "1", "2", "3", "4", "5", "6");
        System.out.println(list.toString());
        List<String> subList = list.subList(0, 5);
        System.out.println(subList.toString());
    }


    /**
     * list转数组
     */
    @Test
    public void list2Array() {
        List<String> list = Lists.newArrayList("a", "b", "c", "d", "e");
        String[] array = list.toArray(new String[0]);
        System.out.println(Arrays.toString(array));
    }


    /**
     * 测试结果就是把集合中的n个数据串到一起
     */
    @Test
    public void iterator() {
        List<String> list = CollectionUtil.getArrayList(4);
        int size = list.size();
        Iterator<String> iterator = list.iterator();
        String s = "";
        while (iterator.hasNext()) {
            switch (size) {
                case 1:
                    s = iterator.next();
                    break;
                case 2:
                    s = iterator.next() + iterator.next();
                    break;
                case 3:
                    s = iterator.next() + iterator.next() + iterator.next();
                    break;
                case 4:
                    s = iterator.next() + iterator.next() + iterator.next() + iterator.next();
                    break;
            }
        }
        System.out.println(s);
    }

}
