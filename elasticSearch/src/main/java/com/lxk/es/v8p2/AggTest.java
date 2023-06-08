package com.lxk.es.v8p2;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import org.junit.Test;

import java.io.IOException;


/**
 * @author LiXuekai on 2023/5/25
 */
public class AggTest extends Common {


    /**
     * The stats that are returned consist of: min, max, sum, count and avg.
     */
    @Test
    public void stats() throws IOException {
        Aggregation age = AggregationBuilders.stats().field("age").build()._toAggregation();
        SearchResponse<Product> response = client.search(s -> s
                        .index(getIndexName())
                        .aggregations("stats", age)
                        .size(10000),
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

        SearchResponse<Product> response = client.search(e -> e
                        .index(getIndexName())
                        .aggregations("min", min)
                        .aggregations("max", max)
                        .aggregations("sum", sum)
                        .aggregations("avg", avg)
                        .aggregations("valueCount", valueCount)
                        .aggregations("stats", stats)
                        .aggregations("topHits", topHits)
                        .aggregations("filter", filter)
                , Product.class);

        showAgg(response);
    }


    @Test
    public void filter() throws IOException {
        StatsAggregation stats = AggregationBuilders.stats().field("age").build();
        SearchResponse<Product> response = client.search(e -> e
                        .index(getIndexName())
                        .query(q -> q.bool(b -> b.filter(f -> f.term(t -> t.field("name").value("a")))))
                        .aggregations("stats", a -> a.stats(stats))
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


}
