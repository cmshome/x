package com.lxk.guava.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author lxk on 2017/2/28
 */
public class SetsTest {
    private final List<String> big = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9");
    private final List<String> small = Lists.newArrayList("1", "2", "3", "3", "2", "1", "0");

    @Before
    public void init() {

    }

    @Test
    public void getDifferenceSetByGuava() {
        long a = System.currentTimeMillis();
        List<String> guava1 = getDifferenceSetByGuava(big, small);
        List<String> guava2 = getDifferenceSetByGuava(small, big);
        System.out.println("执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
        //为了显示一致，我给集合排个序，因为guava是按newHashSet集合来整的，newHashSet又是无序的,so ...
        Collections.sort(guava1);
        System.out.println(guava1.toString());
    }

    @Test
    public void testAddNull() {
        Set<String> set = Sets.newHashSet();
        set.add(null);
        set.add("1");
        System.out.println(set.toString());

        Map<String, Object> map = Maps.newHashMap();
        map.put(null, "lxk");
        map.put("", "sq");
        System.out.println(map.toString());
        System.out.println(map.get(null));
        System.out.println(map.get(""));
    }

    /**
     * set add失败返回false，成功true。
     */
    @Test
    public void testSetAddFailed() {
        Set<String> set = Sets.newHashSet("1", "2");
        String a = "a";
        set.add(a);
        System.out.println(set.add("1"));
        System.out.println(2 == 1 + 1);//运算符优先级的简单测试

        List<String> list1 = Lists.newArrayList("1", "2", "3", "1", "2", "3");
        List<String> list2 = Lists.newArrayList("1", "2", "3", "1", "2", "3", "6");
        Set<String> set1 = Sets.newHashSet(list1);
        System.out.println(set1);
    }

    /**
     * 测试一下set的addAll方法，重复的就不添加，不重复的还是可以添加的。
     */
    @Test
    public void testAddAllToSet() {
        List<String> small = Lists.newArrayList("1", "2", "3", "7", "2", "1");
        Set<String> set = Sets.newHashSet("1", "2");
        set.addAll(small);
        set.forEach(System.out::println);
    }

    /**
     * 使用guava工具类来取List集合的差集--专业轮子谷歌造
     *
     * @param big   大集合
     * @param small 小集合
     * @return 两个集合的差集
     */
    private static List<String> getDifferenceSetByGuava(List<String> big, List<String> small) {
        System.out.println(big.toString());
        System.out.println(small.toString());

        //差集，针对第一个参数，说的是第一个参数特有的。第二个集合是没有的。
        Set<String> differenceSet = Sets.difference(Sets.newHashSet(big), Sets.newHashSet(small));
        System.out.println("差集：第一个集合特有的元素" + Arrays.toString(differenceSet.toArray()));

        Set<String> union = Sets.union(Sets.newHashSet(big), Sets.newHashSet(small));
        System.out.println("并集：" + Arrays.toString(union.toArray()));

        Set<String> intersection = Sets.intersection(Sets.newHashSet(big), Sets.newHashSet(small));
        System.out.println("交集：" + intersection.toString());

        return Lists.newArrayList(intersection);
    }

    @Test
    public void aVoid() {
        Set<String> set = Sets.newHashSet();
        // true
        System.out.println(set.add("a"));
        // false
        System.out.println(set.add("a"));
        // false
        System.out.println(set.add("a"));
    }

}
