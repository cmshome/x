package com.lxk.jdk8.lambda;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * Optional测试
 *
 * @author lxk on 2018/1/30
 */
public class OptionalTest {
    private final List<String> friends = Lists.newArrayList("NBA", "hanks", "jim", "jamie", "Bob", "lily", "trump");
    private final String startingLetter = "NBA";


    @Before
    public void init() {

    }

    @Test
    public void pickNameNow() {
        final Optional<String> foundName = friends.stream()
                .filter(name -> name.startsWith(startingLetter))
                .findFirst();
        System.out.printf("A name starting with %s: %s%n", startingLetter, foundName.orElse("No name found"));
        foundName.ifPresent(name -> System.out.println("Hello " + name));
    }

    @Test
    public void pickNameBefore() {
        String foundName = null;
        for (String name : friends) {
            if (name.startsWith(startingLetter)) {
                foundName = name;
                break;
            }
        }
    }

}
