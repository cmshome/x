package com.lxk.es.v8p2.agg;

import co.elastic.clients.elasticsearch._types.aggregations.*;
import com.google.common.collect.Lists;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.ZxTerms;
import com.lxk.es.v8p2.util.QueryUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
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
     * sort order
     * https://www.elastic.co/guide/en/elasticsearch/reference/8.2/search-aggregations-bucket-terms-aggregation.html#search-aggregations-bucket-terms-aggregation-order
     */
    //@Test
    //public void termsSubAgg() throws IOException {
    //    Map<String, SortOrder> sortMap = Maps.newHashMap();
    //    // 按terms结果的count排序
    //    sortMap.put("_count", SortOrder.Desc);
    //    // 按terms结果的key排序
    //    sortMap.put("_key", SortOrder.Desc);
    //    sortMap.put("sub1", SortOrder.Desc);
    //    // 按照 sub5 子聚合的max排序
    //    sortMap.put("sub5.max", SortOrder.Desc);
    //
    //    Map<String, Aggregation> subMap = Maps.newHashMap();
    //    subMap.put("sub1", AggregationBuilders.sum().field("age").build()._toAggregation());
    //    subMap.put("sub2", AggregationBuilders.max().field("age").build()._toAggregation());
    //    subMap.put("sub3", AggregationBuilders.min().field("age").build()._toAggregation());
    //    subMap.put("sub4", AggregationBuilders.avg().field("age").build()._toAggregation());
    //    subMap.put("sub5", AggregationBuilders.stats().field("age").build()._toAggregation());
    //    subMap.put("sub6", AggregationBuilders.terms().field("streams").build()._toAggregation());
    //
    //    TermsAggregation.Builder builder = new TermsAggregation.Builder();
    //    builder.field("name");
    //    builder.order(sortMap);
    //    builder.size(100);
    //
    //    Aggregation aggregation = new Aggregation.Builder()
    //            .terms(builder.build())
    //            .aggregations(subMap)
    //            .build();
    //
    //    agg(aggregation);
    //}

    @Test
    public void terms() throws IOException {
        Aggregation aggregation = AggregationBuilders.terms().field("name").shardSize(1000).size(100).build()._toAggregation();
        agg(aggregation);
    }

    @Test
    public void termsInclude() throws IOException {
        TermsInclude termsInclude = TermsInclude.of(t -> t.terms(Lists.newArrayList("a", "b", "j", "lxk")));
        Aggregation aggregation = AggregationBuilders.terms().field("name").shardSize(1000).size(100).include(termsInclude).build()._toAggregation();
        agg(aggregation);
    }

    @Test
    public void termsExclude() throws IOException {
        TermsExclude termsExclude = TermsExclude.of(t->t.terms(Lists.newArrayList("lxk")));
        Aggregation aggregation = AggregationBuilders.terms().field("name").shardSize(1000).size(100).exclude(termsExclude).build()._toAggregation();
        agg(aggregation);
    }

    @Test
    public void termsNumber() throws IOException {
        TermsInclude termsInclude = TermsInclude.of(t -> t.terms(Lists.newArrayList("101", "201", "301", "401")));
        TermsExclude termsExclude = TermsExclude.of(t->t.terms(Lists.newArrayList("100", "200", "300", "400")));
        Aggregation aggregation = AggregationBuilders.terms().field("age").shardSize(1000).size(100)
                .include(termsInclude)
                .exclude(termsExclude).build()._toAggregation();
        agg(aggregation);
    }

    @Test
    public void testExecutionHint() throws IOException {
        Aggregation aggregation = AggregationBuilders.terms().field("name").shardSize(1000).executionHint(TermsAggregationExecutionHint.Map).size(100).build()._toAggregation();
        agg(aggregation);
    }


    @Test
    public void _5_subTerms() throws IOException {
        List<ZxTerms> list = Lists.newArrayList();
        list.add(new ZxTerms("1", AggregationBuilders.terms().field("b"), QueryUtil.aggregationMap("1", AggregationBuilders.max().field("b").build()._toAggregation())));
        list.add(new ZxTerms("2", AggregationBuilders.terms().field("c"), QueryUtil.aggregationMap("2", AggregationBuilders.max().field("c").build()._toAggregation())));
        list.add(new ZxTerms("3", AggregationBuilders.terms().field("d"), QueryUtil.aggregationMap("3", AggregationBuilders.max().field("d").build()._toAggregation())));
        list.add(new ZxTerms("4", AggregationBuilders.terms().field("e"), QueryUtil.aggregationMap("4", AggregationBuilders.max().field("e").build()._toAggregation())));

        Map<String, Aggregation> subAggs = null;


        int builderSize = list.size();
        Iterator<ZxTerms> iterator = list.iterator();
        while (iterator.hasNext()) {
            //组合termsBuilder 子聚合语句，最多支持5层termsBuilder
            switch (builderSize) {
                case 1:
                    subAggs = iterator.next().aggregations();
                    break;
                case 2:
                    subAggs = iterator.next().subAggregation(iterator.next().aggregations()).aggregations();
                    break;
                case 3:
                    subAggs = iterator.next().subAggregation(
                            iterator.next().subAggregation(iterator.next().aggregations()).aggregations()
                    ).aggregations();
                    break;
                case 4:
                    subAggs = iterator.next().subAggregation(
                            iterator.next().subAggregation(iterator.next().subAggregation(iterator.next().aggregations()).aggregations()).aggregations()
                    ).aggregations();
                    break;
            }
        }

        TermsAggregation.Builder builder = new TermsAggregation.Builder();
        builder.field("name");
        builder.size(100);

        Aggregation aggregation = new Aggregation.Builder()
                .terms(builder.build())
                .aggregations(subAggs)
                .build();

        agg(aggregation);
    }
}
