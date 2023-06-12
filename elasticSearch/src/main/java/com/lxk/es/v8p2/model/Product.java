package com.lxk.es.v8p2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LiXuekai on 2023/5/23
 */

@Data
public class Product {
    private String id;
    private String name = "abc";
    private Integer age;
    private List<String> streams;
    private A a;
    private String type = "test";

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class A {
        private String x;
        private String y;
        private String z;
    }
}
