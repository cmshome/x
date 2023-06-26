package com.lxk.es.v8p2.agg;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.aggregations.TermsAggregation;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lxk.es.v8p2.base.Common;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2023/6/19
 */
public class TermsAggTest extends Common {

    /**
     * term的字段是字符串类型的
     */
    @Test
    public void sTerms() throws IOException {
        List<String> strings = Lists.newArrayList("streams", "name", "a.x", "a.y", "a.z", "id", "type");
        terms(strings);
    }

    /**
     * term的字段是long数字类型的
     */
    @Test
    public void lTerms() throws IOException {
        List<String> strings = Lists.newArrayList("age", "resellers.price");
        terms(strings);
    }

    /**
     * term的字段是double数字类型的
     */
    @Test
    public void dTerms() throws IOException {
        List<String> strings = Lists.newArrayList("d");
        terms(strings);
    }

    /**
     * 简单的terms就 long double string 3种类型实现
     */
    private void terms(List<String> strings) throws IOException {
        for (String string : strings) {
            System.out.println("---------------- terms   " + string);
            Aggregation aggregation = AggregationBuilders.terms().field(string).shardSize(1000).size(2).build()._toAggregation();
            agg(aggregation);
        }
    }

    /**
     * sort 的key 必须和 sub的key一致才行，不然要报错。
     * 可以按照不同的sub agg 的名称去sort
     * sub4 还不能排序，这个排序还是没弄明白怎么在这工作呢。
     */
    @Test
    public void termsSubAgg() throws IOException {
        Map<String, SortOrder> sortMap = Maps.newHashMap();
        sortMap.put("sub1", SortOrder.Desc);

        Map<String, Aggregation> subMap = Maps.newHashMap();
        subMap.put("sub1", AggregationBuilders.sum().field("age").build()._toAggregation());
        subMap.put("sub2", AggregationBuilders.max().field("age").build()._toAggregation());
        subMap.put("sub3", AggregationBuilders.min().field("age").build()._toAggregation());
        subMap.put("sub4", AggregationBuilders.avg().field("age").build()._toAggregation());
        subMap.put("sub5", AggregationBuilders.avg().field("age").build()._toAggregation());
        subMap.put("sub6", AggregationBuilders.terms().field("streams").build()._toAggregation());

        TermsAggregation.Builder builder = new TermsAggregation.Builder();
        builder.field("name");
        builder.order(sortMap);
        builder.size(100);

        Aggregation aggregation = new Aggregation.Builder()
                .terms(builder.build())
                .aggregations(subMap)
                .build();

        agg(aggregation);
    }
}
