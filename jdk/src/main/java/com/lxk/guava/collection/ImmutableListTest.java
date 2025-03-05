package com.lxk.guava.collection;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

/**
 * guava ImmutableMap 测试实例
 *
 * @author lxk on 2017/11/7
 */
public class ImmutableListTest {

    /**
     * 有时候，我们可能需要在代码里面使用一个包含许多常量的list
     * 不可变集合。。。
     */
    List<String> CONSTANT_LIST =
            new ImmutableList.Builder<String>()
                    .add("a")
                    .add("d")
                    .add("f")
                    .add("g")
                    .build();

    /*
     * 推荐使用上面的写法，样式好看，且好维护，也就是说，你现在要删除或者添加一条，直接添加一行就好。常量整体也看着条理清晰。
     */
    //List<String> CONSTANT_LIST = ImmutableList.of("a","d","f","g");

    //List<String> CONSTANT_LIST = ImmutableList.copyOf(Lists.newArrayList("a","d","f","g"));


    @Test
    public void abc() {
        String string = "最大值";
        if (CONSTANT_LIST.contains(string)) {
            System.out.println("常量list集合包含此 String");
        }
        //此常量list不能add,remove,不然会抛异常的。
        //CONSTANT_LIST.add("sss");
        //CONSTANT_LIST.remove(string);
        System.out.println();
    }
}
