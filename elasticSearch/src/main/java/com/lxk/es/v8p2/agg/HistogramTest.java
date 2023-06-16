package com.lxk.es.v8p2.agg;

import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.util.QueryUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2023/6/8
 */
public class HistogramTest extends Common {

    private static final String HIS_NAME = "his";

    @Test
    public void builder() throws IOException {

        Aggregation age = AggregationBuilders.histogram().field("age").interval(1000.0).build()._toAggregation();


        Map<String, Aggregation> map = QueryUtil.aggregationMap(HIS_NAME, age);
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(getIndexName())
                .size(0)
                .query(QueryUtil.termQuery("type", "test"))
                .aggregations(map)
                .build();

        SearchResponse<Void> response = client.search(searchRequest, Void.class);
        showHisBucket(response);
    }

    @Test
    public void histogram() throws IOException {
        SearchResponse<Void> response = client.search(b -> b
                        .index(getIndexName())
                        .size(0)
                        .query(QueryUtil.termQuery("type", "test"))
                        .trackTotalHits(QueryUtil.trackHits(true))
                        .aggregations(HIS_NAME, a -> a
                                .histogram(h -> h
                                        .field("age")
                                        .interval(1000.0)
                                        .extendedBounds(eb->eb.min(1000.0).max(10000.0))
                                        .offset(1000.0)
                                )
                                .aggregations("subStats", AggregationBuilders.stats().field("age").build()._toAggregation())
                        ),
                Void.class
        );

        showHisBucket(response);

    }

    private void showHisBucket(SearchResponse<Void> response) {
        List<HistogramBucket> buckets = response
                .aggregations()
                .get(HIS_NAME)
                .histogram()
                .buckets()
                .array();

        for (HistogramBucket bucket : buckets) {
            System.out.println("There are " + bucket.docCount() + " product under " + bucket.key());
            Map<String, Aggregate> aggregations = bucket.aggregations();
            for (Map.Entry<String, Aggregate> entry : aggregations.entrySet()) {
                Aggregate aggregate = entry.getValue();
                showAgg(aggregate);
            }
        }
    }


}
