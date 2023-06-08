package com.lxk.es.v8p2;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2023/6/8
 */
public class GroupTest extends Common {

    /**
     * 查出name属性的n个值，以及每个值对应的count
     * 再聚合name为某个维度的所有数据的age的和
     */
    @Test
    public void group() throws IOException {
        SearchResponse<Product> searchResponse = client.search(e -> e
                        .index(getIndexName())
                        .size(200)
                        .aggregations("group", a -> a
                                .terms(t -> t
                                        .field("name")
                                )

                                .aggregations("subSum", t -> t
                                        .sum(m -> m
                                                .field("age")
                                        )
                                )
                        )
                , Product.class);

        Aggregate aggregate = searchResponse.aggregations().get("group");
        Object o = ((StringTermsAggregate) aggregate._get()).buckets()._get();
        for (StringTermsBucket bucket : (List<StringTermsBucket>) o) {

            Map<String, Aggregate> aggregations = bucket.aggregations();
            for (Map.Entry<String, Aggregate> entry : aggregations.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().sum().value());
            }

            System.out.println("name = " + bucket.key() + "; count = " + bucket.docCount());
        }
        System.out.println();
    }
}
