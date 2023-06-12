package com.lxk.es.v8p2.agg;

import co.elastic.clients.elasticsearch._types.aggregations.HistogramBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.lxk.es.v8p2.base.Common;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author LiXuekai on 2023/6/8
 */
public class HistogramTest extends Common {

    @Test
    public void histogram() throws IOException {
        String searchText = "bike";

        Query query = MatchQuery.of(m -> m
                .field("name")
                .query(searchText)
        )._toQuery();

        SearchResponse<Void> response = client.search(b -> b
                        .index(getIndexName())
                        .size(0)
                        //.query(query)
                        .aggregations("age-histogram", a -> a
                                .histogram(h -> h
                                        .field("age")
                                        .interval(5.0)
                                )
                        ),
                Void.class
        );

        List<HistogramBucket> buckets = response
                .aggregations()
                .get("age-histogram")
                .histogram()
                .buckets()
                .array();

        for (HistogramBucket bucket : buckets) {
            System.out.println("There are " + bucket.docCount() + " bikes under " + bucket.key());
        }
    }



}
