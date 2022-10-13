package com.lxk.jdk8.lambda;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * map（映射）和reduce(归约，化简）
 *
 * @author lxk on 2018/1/30
 */
public class MapReduceTest {
    private List<String> friends;

    @Before
    public void init() {
        friends = Lists.newArrayList("NBA", "hanks", "jim", "jamie", "Bob", "lily", "trump");
    }

    /**
     * lambda 求和
     */
    @Test
    public void sum() {
        int sum = friends.stream().mapToInt(String::length).sum();
        System.out.println("Total number of characters in all names: " + sum);
    }

    @Test
    public void reduce1() {
        final Optional<String> aLongName = friends.stream()
                .reduce((name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
        aLongName.ifPresent(name -> System.out.printf("A longest name: %s%n", name));
    }

    @Test
    public void reduce2() {
        final String steveOrLonger = friends.stream()
                .reduce("Steve", (name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
        System.out.println(steveOrLonger);
    }

    /**
     * A List of Strings to Uppercase
     */
    @Test
    public void map2List() {
        System.out.println(friends.stream().map(String::toUpperCase).collect(joining(", ")));
        System.out.println(friends.stream().map(String::toUpperCase).collect(Collectors.toList()));

        System.out.println(friends.stream().map(String::toLowerCase).collect(Collectors.toList()));
    }

}
