package com.lxk.es.v8p2.agg;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import com.lxk.es.v8p2.util.QueryUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;


/**
 * @author LiXuekai on 2023/5/25
 */
public class AggTest extends Common {

    @Test
    public void terms() throws IOException {
        Aggregation aggregation = AggregationBuilders.terms().field("name").shardSize(1000).size(100).build()._toAggregation();
        agg(aggregation);
    }

    @Test
    public void percentiles() throws IOException {
        Aggregation aggregation = AggregationBuilders.percentiles().field("age").build()._toAggregation();
        agg(aggregation);
    }

    @Test
    public void percentileRanks() throws IOException {
        Aggregation aggregation = AggregationBuilders.percentileRanks().field("age").build()._toAggregation();
        agg(aggregation);
    }

    private void agg(Aggregation aggregation) throws IOException {
        SearchRequest request = baseSearchRequest()
                .aggregations("aggregation", aggregation)
                .trackTotalHits(trackHits())
                .size(0)
                .build();
        SearchResponse<Product> response = search(request);
        showAgg(response);
    }


    /**
     * The stats that are returned consist of: min, max, sum, count and avg.
     */
    @Test
    public void stats() throws IOException {
        Aggregation age = AggregationBuilders.stats().field("age").build()._toAggregation();
        SearchResponse<Product> response = client.search(s -> s
                        .index(getIndexName())
                        .aggregations("stats", age)
                        .trackTotalHits(trackHits())
                        .size(0),
                Product.class
        );
        showAgg(response);
    }

    @Test
    public void max() throws IOException {
        SearchResponse<Product> response = client.search(e -> e
                        .index(getIndexName())
                        .aggregations("age", a -> a.max(m -> m.field("age")))
                , Product.class);
        showAgg(response);
    }

    @Test
    public void min() throws IOException {
        SearchResponse<Product> response = client.search(e -> e
                        .index(getIndexName())
                        .aggregations("age", a -> a.min(m -> m.field("age")))
                , Product.class);
        showAgg(response);
    }

    @Test
    public void avg() throws IOException {
        SearchResponse<Product> response = client.search(e -> e
                        .index(getIndexName())
                        .aggregations("age", a -> a.avg(m -> m.field("age")))
                , Product.class);
        showAgg(response);
    }

    @Test
    public void maxWithQuery() throws IOException {
        Query query = RangeQuery.of(t -> t
                .field("age")
                .lte(JsonData.of(55))
        )._toQuery();

        SearchResponse<Product> response = client.search(e -> e
                        .index(getIndexName())
                        .query(query)
                        .aggregations("age", a -> a.max(m -> m.field("age")))
                , Product.class);
        showAgg(response);
    }


    @Test
    public void sum() throws IOException {
        SearchResponse<Product> response = client.search(e -> e
                        .index(getIndexName())
                        .aggregations("age", a -> a.sum(m -> m.field("age")))
                , Product.class);
        showAgg(response);
    }

    @Test
    public void topHits() throws IOException {
        SearchResponse<Product> response = client.search(e -> e
                        .index(getIndexName())
                        .aggregations("top", a -> a
                                .topHits(m -> m
                                        .docvalueFields("age")
                                        .sort(sort("age", SortOrder.Desc))
                                        .size(10)
                                )
                        )
                , Product.class);
        showAgg(response);
    }

    @Test
    public void filters() throws IOException {
        Aggregation filters = AggregationBuilders.filters().filters(b -> b.array(Lists.newArrayList(QueryUtil.termQuery("name", "a"), QueryUtil.termQuery("name", "b")))).build()._toAggregation();
        Map<String, Aggregation> map = new ImmutableMap.Builder<String, Aggregation>()
                .put("filters", filters)
                .build();
        agg(map);
    }

    private void agg(Map<String, Aggregation> map) throws IOException {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        Query query = QueryBuilders.matchAll().build()._toQuery();
        builder.index(getIndexName());
        builder.query(query);
        builder.aggregations(map);
        SearchRequest request = builder.build();

        SearchResponse<Product> response = search(request);
        showAgg(response);
    }

    @Test
    public void filter() throws IOException {
        Aggregation stats = AggregationBuilders.stats().field("age").build()._toAggregation();
        SearchResponse<Product> response = client.search(e -> e
                        .index(getIndexName())
                        .query(q -> q.bool(b -> b.filter(f -> f.term(t -> t.field("name").value("a")))))
                        .aggregations("stats", stats)
                , Product.class);
        showAgg(response);
    }


    /**
     * 这个是在一个聚合里面先filer聚合之后，再对过滤的数据进行二次聚合
     */
    @Test
    public void filter2() throws IOException {
        Aggregation age = AggregationBuilders.stats().field("age").build()._toAggregation();
        SearchResponse<Product> response = client.search(e -> e
                        .index(getIndexName())
                        .aggregations("stats", f -> f
                                .filter(TermQuery.of(t -> t.field("name").value("a"))._toQuery())
                                .aggregations("stats", age)
                        )
                , Product.class);
        showAgg(response);
    }

    @Test
    public void filter3() throws IOException {
        Aggregation aggregation = new Aggregation.Builder()
                .filter(QueryBuilders.bool().must(QueryUtil.termQuery("name", "a")).build()._toQuery())
                .aggregations("subAgg", AggregationBuilders.valueCount().field("age").build()._toAggregation()).build();

        Map<String, Aggregation> map = new ImmutableMap.Builder<String, Aggregation>()
                .put("agg", aggregation)
                .build();
        agg(map);
    }

    /**
     * 套娃
     */
    @Test
    public void filterSubAgg() throws IOException {
        Query query1 = QueryUtil.termQuery("type", "test");
        Aggregation sub1 = AggregationBuilders.filter().term(t -> t.field("name").value("a")).build()._toAggregation();
        Map<String, Aggregation> sub1Map = QueryUtil.aggregationMap("sub1", sub1);

        Query query2 = QueryBuilders.matchAll().build()._toQuery();
        Aggregation sub2 = QueryUtil.filterSubAgg(query1, sub1Map);
        Map<String, Aggregation> sub2Map = QueryUtil.aggregationMap("sub2", sub2);

        Aggregation aggregation = QueryUtil.filterSubAgg(query2, sub2Map);
        Map<String, Aggregation> map = new ImmutableMap.Builder<String, Aggregation>()
                .put("filterSubAgg", aggregation)
                .build();
        agg(map);
    }


    /**
     * 没有嵌套，启到过滤数据的作用。
     */
    @Test
    public void errorFilter() throws IOException {
        Aggregation filter = AggregationBuilders.filter(q -> q.term(t -> t.field("name").value("a")));
        Aggregation age = AggregationBuilders.stats().field("age").build()._toAggregation();
        SearchResponse<Product> response = client.search(s -> s
                        .index(getIndexName())
                        .aggregations("f", filter)
                        .aggregations("all", age)
                        .size(10000),
                Product.class
        );
        showAgg(response);
    }


    @Test
    public void searchRequest() throws IOException {
        Aggregation max = AggregationBuilders.max().field("age").build()._toAggregation();
        Map<String, Aggregation> map1 = Maps.newHashMap();
        map1.put("max", max);

        Aggregation min = AggregationBuilders.min().field("age").build()._toAggregation();
        Map<String, Aggregation> map2 = Maps.newHashMap();
        map2.put("min", min);

        Aggregation sum = AggregationBuilders.sum().field("age").build()._toAggregation();
        Aggregation avg = AggregationBuilders.avg().field("age").build()._toAggregation();

        Map<String, Aggregation> map = Maps.newHashMap();
        map.put("sum", sum);
        map.put("avg", avg);

        SearchRequest.Builder builder = new SearchRequest.Builder();
        Query query = QueryBuilders.matchAll().build()._toQuery();
        builder.index(getIndexName());
        builder.query(query);
        builder.aggregations(map1);
        builder.aggregations(map2);
        builder.aggregations(map);

        SearchResponse<Product> response = search(builder.build());
        Map<String, Aggregate> aggregations = response.aggregations();
        Aggregate max1 = aggregations.get("max");
        Aggregate min1 = aggregations.get("min");
        Aggregate sum1 = aggregations.get("sum");
        Aggregate avg1 = aggregations.get("avg");

        System.out.println(max1.max().value());
        System.out.println(min1.min().value());
        System.out.println(sum1.sum().value());
        System.out.println(avg1.avg().value());

    }

    /**
     *
     */
    @Test
    public void together() throws IOException {
        Aggregation sum = AggregationBuilders.sum().field("age").build()._toAggregation();
        Aggregation avg = AggregationBuilders.avg().field("age").build()._toAggregation();
        Aggregation max = AggregationBuilders.max().field("age").build()._toAggregation();
        Aggregation min = AggregationBuilders.min().field("age").build()._toAggregation();
        Aggregation valueCount = AggregationBuilders.valueCount().field("age").build()._toAggregation();

        // The stats that are returned consist of: min, max, sum, count and avg.
        Aggregation stats = AggregationBuilders.stats().field("age").build()._toAggregation();

        Aggregation filter = AggregationBuilders.filter().term(t -> t.field("name").value("a")).build()._toAggregation();

        Aggregation topHits = AggregationBuilders.topHits().docvalueFields("age").sort(sort("age", SortOrder.Desc)).size(5).build()._toAggregation();

        Aggregation cardinality = AggregationBuilders.cardinality().field("name").precisionThreshold(40000).build()._toAggregation();

        // 计算name维度的所有值，以及对应的count。
        Aggregation terms = AggregationBuilders.terms().field("streams").build()._toAggregation();

        Aggregation range = AggregationBuilders.range().field("age").ranges(r -> r.from(String.valueOf(100)).to(String.valueOf(109))).build()._toAggregation();

        Aggregation filterSubAgg = new Aggregation.Builder()
                .filter(QueryBuilders.bool().must(QueryUtil.termQuery("name", "a")).build()._toQuery())
                .aggregations("subAgg", AggregationBuilders.valueCount().field("age").build()._toAggregation()).build();


        SearchResponse<Product> response = client.search(e -> e
                        .index(getIndexName())
                        .aggregations("sum", sum)
                        .aggregations("avg", avg)
                        .aggregations("max", max)
                        .aggregations("min", min)
                        .aggregations("valueCount", valueCount)
                        .aggregations("stats", stats)
                        .aggregations("filter", filter)
                        .aggregations("topHits", topHits)
                        .aggregations("cardinality", cardinality)
                        .aggregations("terms", terms)
                        .aggregations("range", range)
                        .aggregations("filterSubAgg", filterSubAgg)
                , Product.class);

        showAgg(response);
    }

}
