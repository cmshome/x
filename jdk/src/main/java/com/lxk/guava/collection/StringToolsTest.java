package com.lxk.guava.collection;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 测试 String处理相关的几个工具
 * <p>
 *
 * @author lxk on 2016/11/18
 */
public class StringToolsTest {

    @Test
    public void x() {
        int count = 800;
        int eachLength = 8;
        List<String> randomStringList = getRandomStringList(count, eachLength);
        System.out.println(randomStringList.toString());
    }

    @Test
    public void join() {
        Joiner joiner = Joiner.on("-").skipNulls();
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        String join = joiner.join(list);
        System.out.println(join);
    }

    /**
     * 获得一个包含 count 个字符串list，每个字符串有 eachLength 个字符
     *
     * @param count      返回多少个字符串的list
     * @param eachLength 每个字符串几个字符
     */
    private static List<String> getRandomStringList(int count, int eachLength) {

        List<Character> characters = getCharacterList();
        List<String> strings = Lists.newArrayListWithExpectedSize(count);
        for (int j = 0; j < count; j++) {
            StringBuilder ss = new StringBuilder();
            int i = 0;
            while (i < eachLength) {
                ss.append(characters.get(new Random().nextInt(characters.size())));
                i++;
            }
            strings.add(ss.toString());
        }
        return strings;
    }

    /**
     * 获得字符数组：[A-Z,a-z,0-1]
     */
    private static List<Character> getCharacterList() {
        char a = 'a';
        char z = 'z';
        char A = 'A';
        char Z = 'Z';
        char _0 = '0';
        char _9 = '9';

        List<Character> characters = Lists.newArrayList();

        while (A <= Z) {
            characters.add(A);
            A++;
        }
        while (a <= z) {
            characters.add(a);
            a++;
        }
        while (_0 <= _9) {
            characters.add(_0);
            _0++;
        }
        Collections.sort(characters);
        return characters;
    }

    /**
     * guava 工具用分隔符把字符串序列连接起来
     * (处理字符串序列中有null)
     */
    @Test
    public void toolJoinerTest() {
        //跳过null
        Joiner joiner = Joiner.on("-").skipNulls();
        //可以传集合类型参数
        String join = joiner.join(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println(join);
        //替换null为*
        Joiner useForNull = Joiner.on("-").useForNull("*");
        int[] numbers = {1, 2, 3, 4, 5};
        String numbersAsString = Joiner.on(";").join(Ints.asList(numbers));
        System.out.println(numbersAsString);
        String s = useForNull.join("大", null, "师", null, "胸", null, "！");
        System.out.println(s);
    }

}
