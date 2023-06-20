package com.lxk.es.v8p2.agg;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.aggregations.TermsAggregation;
import com.google.common.collect.Maps;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.util.QueryUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @author LiXuekai on 2023/6/19
 */
public class TermsAggTest extends Common {

    @Test
    public void terms() throws IOException {
        Aggregation aggregation = AggregationBuilders.terms().field("name").shardSize(1000).size(100).build()._toAggregation();
        agg(aggregation);
    }


    @Test
    public void termsSubAgg() throws IOException {
        Map<String, SortOrder> sortMap = Maps.newHashMap();
        sortMap.put("sub", SortOrder.Desc);
        Aggregation sub = AggregationBuilders.sum().field("age").build()._toAggregation();
        TermsAggregation.Builder builder = new TermsAggregation.Builder();
        builder .field("name");
        builder.order(sortMap);
        builder.size(100);
        Aggregation aggregation = new Aggregation.Builder()
                .terms(builder.build())
                .aggregations(QueryUtil.aggregationMap("sub", sub))
                .build();

        agg(aggregation);
    }
}
