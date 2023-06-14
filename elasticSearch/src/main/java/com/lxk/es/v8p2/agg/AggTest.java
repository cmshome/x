package com.lxk.es.v8p2.agg;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.aggregations.StatsAggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import com.google.common.collect.Maps;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;


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


    @Test
    public void searchRequest() throws IOException {
        Aggregation max = AggregationBuilders.max().field("age").build()._toAggregation();
        Aggregation min = AggregationBuilders.min().field("age").build()._toAggregation();
        Aggregation sum = AggregationBuilders.sum().field("age").build()._toAggregation();
        Aggregation avg = AggregationBuilders.avg().field("age").build()._toAggregation();

        Map<String, Aggregation> map = Maps.newHashMap();
        map.put("max", max);
        map.put("min", min);
        map.put("sum", sum);
        map.put("avg", avg);

        SearchRequest.Builder builder = new SearchRequest.Builder();
        Query query = QueryBuilders.matchAll().build()._toQuery();
        builder.index(getIndexName());
        builder.query(query);
        builder.aggregations(map);

        SearchResponse<Product> response = client.search(builder.build(), Product.class);
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


}
