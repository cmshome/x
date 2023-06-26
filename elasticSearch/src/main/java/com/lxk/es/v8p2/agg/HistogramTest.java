package com.lxk.es.v8p2.agg;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.aggregations.HistogramAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.HistogramOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import com.google.common.collect.Maps;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import com.lxk.es.v8p2.util.QueryUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @author LiXuekai on 2023/6/8
 */
public class HistogramTest extends Common {

    private static final String AGG = "agg";
    private static final String SUB_AGG_1 = "subAgg1";
    private static final String SUB_AGG_2 = "subAgg2";
    private static final String SUB_AGG_3 = "subAgg3";


    @Test
    public void builder() throws IOException {
        Aggregation age = AggregationBuilders.histogram().field("age").interval(10.0).offset(100d).build()._toAggregation();
        Map<String, Aggregation> map = QueryUtil.aggregationMap(AGG, age);
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(getIndexName())
                .size(0)
                .query(QueryBuilders.bool().must(QueryUtil.termQuery("type", "test"), QueryUtil.rangeQuery("age", 100L, 200L)).build()._toQuery())
                .aggregations(map)
                .build();

        SearchResponse<Product> response = search(searchRequest);
        showHisBucket(response);
    }

    @Test
    public void histogram() throws IOException {
        HistogramOrder key = HistogramOrder.of(b -> b.key(SortOrder.Desc));
        HistogramOrder count = HistogramOrder.of(b -> b.count(SortOrder.Desc));

        SearchResponse<Product> response = client.search(b -> b
                        .index(getIndexName())
                        .size(0)
                        .query(QueryUtil.termQuery("type", "test"))
                        .trackTotalHits(QueryUtil.trackHits(true))
                        .aggregations(AGG, a -> a
                                .histogram(h -> h
                                        .field("age")
                                        .interval(1000.0)
                                                .order(count)
                                        //.extendedBounds(eb -> eb.min(1000.0).max(10000.0))
                                        //.offset(1000.0)
                                )
                                //.aggregations(SUB_AGG_1, AggregationBuilders.stats().field("age").build()._toAggregation())
                                //.aggregations(SUB_AGG_2, AggregationBuilders.valueCount().field("age").build()._toAggregation())

                        ),
                Product.class
        );

        showHisBucket(response);

    }

    @Test
    public void sub() throws IOException {
        Aggregation hisSubAgg = new Aggregation.Builder()
                .histogram(AggregationBuilders.histogram().field("age").interval(100.).build())
                .aggregations(SUB_AGG_1, AggregationBuilders.valueCount().field("age").build()._toAggregation())
                .aggregations(SUB_AGG_2, AggregationBuilders.max().field("age").build()._toAggregation())
                .aggregations(SUB_AGG_3, AggregationBuilders.filter(q -> q.term(t -> t.field("name").value("a"))))
                .build();
        // 不能是空或者null
        Map<String, Aggregation> map = QueryUtil.aggregationMap(AGG, hisSubAgg);

        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(getIndexName())
                .size(0)
                .query(QueryUtil.termQuery("type", "test"))
                .aggregations(map)
                .build();

        SearchResponse<Product> response = search(searchRequest);
        showHisBucket(response);
    }

    @Test
    public void subAgg() throws IOException {
        Map<String, Aggregation> map = Maps.newHashMap();
        map.put(SUB_AGG_1, AggregationBuilders.stats().field("age").build()._toAggregation());
        map.put(SUB_AGG_2, AggregationBuilders.cardinality().field("name").build()._toAggregation());

        HistogramAggregation.Builder builder = AggregationBuilders.histogram().field("age").interval(100.).offset(0.);
        Aggregation agg = QueryUtil.hisSubAgg(builder, map);
        Map<String, Aggregation> aggregationMap = QueryUtil.aggregationMap(AGG, agg);
        agg(aggregationMap);
    }

    private void agg(Map<String, Aggregation> aggregationMap) throws IOException {
        SearchRequest request = baseSearchRequest()
                .aggregations(aggregationMap)
                .size(1000)
                .query(QueryBuilders.range().field("age").gte(JsonData.of(9000)).build()._toQuery())
                .trackTotalHits(QueryUtil.trackHits(true))
                .build();

        SearchResponse<Product> response = search(request);

        showHisBucket(response);
    }


}
