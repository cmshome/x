package com.lxk.jdk8.lambda;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Function 使用
 *
 * @author lxk on 2018/1/30
 */
public class FunctionTest {
    private List<String> friends;

    @Before
    public void init() {
        friends = Lists.newArrayList("NBA", "hanks", "jim", "jamie", "Bob", "lily", "trump");
    }

    /**
     * Predicate的使用
     */
    @Test
    public void test0() {
        final Predicate<String> startsWithN = name -> name.startsWith("N");
        final Predicate<String> startsWithB = name -> name.startsWith("B");
        final long countFriendsStartN = friends.stream().filter(startsWithN).count();
        final long countFriendsStartB = friends.stream().filter(startsWithB).count();
        System.out.println(countFriendsStartN);
        System.out.println(countFriendsStartB);
    }

    /**
     * Predicate的使用
     */
    @Test
    public void test1() {
        final long countFriendsStartN = friends.stream().filter(checkIfStartsWith("N")).count();
        final long countFriendsStartB = friends.stream().filter(checkIfStartsWith("B")).count();
        System.out.println(countFriendsStartN);
        System.out.println(countFriendsStartB);
    }

    /**
     * 返回一个Predicate给filter用
     */
    private static Predicate<String> checkIfStartsWith(final String letter) {
        return name -> name.startsWith(letter);
    }

    /**
     *
     */
    @Test
    public void test2() {
        //完全体
        //final Function<String, Predicate<String>> startsWithLetter = (String letter) -> {
        //    Predicate<String> checkStarts = (String name) -> name.startsWith(letter);
        //    return checkStarts;
        //};

        //究极省略简洁体
        final Function<String, Predicate<String>> startsWithLetter = letter -> name -> name.startsWith(letter);
        final long countFriendsStartN = friends.stream().filter(startsWithLetter.apply("N")).count();
        final long countFriendsStartB = friends.stream().filter(startsWithLetter.apply("B")).count();
        System.out.println(countFriendsStartN);
        System.out.println(countFriendsStartB);
    }


}
