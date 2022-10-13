package com.lxk.jdk8.lambda.test;

/**
 * 函数式接口 AppleTest、BiAppleTest分别对应着java8提供的Predicate、BiPredicate
 * @author LiXuekai on 2020/4/29
 */
@FunctionalInterface
public interface BiAppleTest<T, U> {
    boolean test(T t, U u);
}

