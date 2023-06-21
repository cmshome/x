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
    /**
     * 造的数据的name都是有值的
     */
    private String name = "abc";
    /**
     * age值同id，从0到10000，继续往上增
     */
    private Integer age;
    private List<String> streams;
    /**
     * 弄个对象，测试 a.x 这种字段嵌套查询
     */
    private A a;
    /**
     * 弄个常量，可以根据这个条件，查询all，测试超过1w的查询
     */
    private String type = "test";
    /**
     * 给model加个double属性，做 d terms 测试
     */
    private Double d;
    /**
     * 测试list 对象嵌套
     */
    private List<Reseller> resellers;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class A {
        private String x;
        private String y;
        private String z;
    }

    /**
     * 经销商
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reseller {
        private String reseller;
        private Integer price;
    }

}
