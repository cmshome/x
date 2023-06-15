package com.lxk.es.v8p2.query;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.MsearchRequest;
import co.elastic.clients.elasticsearch.core.MsearchResponse;
import co.elastic.clients.elasticsearch.core.msearch.MultiSearchItem;
import co.elastic.clients.elasticsearch.core.msearch.MultiSearchResponseItem;
import co.elastic.clients.elasticsearch.core.msearch.RequestItem;
import com.google.common.collect.Lists;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2023/6/9
 */
public class TestMultiSearch extends Common {

    @Test
    public void mSearch() throws IOException {
        Query a = QueryBuilders.term().field("name").value("a").build()._toQuery();
        Query c = QueryBuilders.term().field("name").value("c").build()._toQuery();

        RequestItem aItem = RequestItem.of(r -> r
                .header(h -> h.index(getIndexName()))
                .body(builder -> builder
                        .query(q -> q.bool(b -> b.must(a))).size(0)
                        .aggregations("stats", AggregationBuilders.stats().field("age").build()._toAggregation())
                )
        );

        RequestItem cItem = RequestItem.of(r -> r
                .header(h -> h.index(getIndexName()))
                .body(builder -> builder
                        .query(q -> q.bool(b -> b.must(c))).size(0)
                        .aggregations("stats", AggregationBuilders.stats().field("age").build()._toAggregation())
                )
        );

        MsearchRequest msearchRequest = new MsearchRequest.Builder()
                .searches(Lists.newArrayList(aItem, cItem))
                .build();

        MsearchResponse<Product> mSearchResponse = client.msearch(msearchRequest, Product.class);

        List<MultiSearchResponseItem<Product>> responses = mSearchResponse.responses();
        for (MultiSearchResponseItem<Product> response : responses) {
            MultiSearchItem<Product> result = response.result();
            long value = result.hits().total().value();
            System.out.println("total count is " + value);
            showAgg(response.result().aggregations());
            showHis(result.hits().hits());
        }
    }

    private void showAgg(Map<String, Aggregate> aggregations) {
        for (Map.Entry<String, Aggregate> entry : aggregations.entrySet()) {
            Aggregate value = entry.getValue();
            showAgg(value);
        }
    }

    @Test
    public void a2() throws IOException {
        final MsearchResponse<Product> msearch = client.msearch(_0 -> _0
                        .searches(_1 -> _1
                                .header(_3 -> _3.index(getIndexName()))
                                .body(_3 -> _3.query(_4 -> _4.matchAll(_5 -> _5)))
                        ).searches(_1 -> _1
                                .header(_3 -> _3.index("non-existing"))
                                .body(_3 -> _3.query(_4 -> _4.matchAll(_5 -> _5)))
                        )
                , Product.class);
    }


}
