package com.lxk.es.v8p2.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.elasticsearch.indices.stats.IndicesStats;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.google.common.collect.Lists;
import com.lxk.es.v8p2.base.Common;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author LiXuekai on 2023/5/25
 */
public class IndexTest extends Common {

    private final String indexName = "test_index";


    @Test
    public void getAllIndices() throws IOException {
        GetIndexRequest of = GetIndexRequest.of(g -> g.index("*"));
        GetIndexResponse response = client.indices().get(of);
        Set<String> strings = response.result().keySet();
        System.out.println(strings);
    }

    /**
     * * 查所有索引名称
     * xxx_* 前缀
     */
    @Test
    public void index() throws IOException {
        GetIndexResponse response = client.indices().get(s->s.index(Lists.newArrayList("*")));
        Set<String> strings = response.result().keySet();
        System.out.println(strings);
    }

    @Test
    public void stats() throws IOException {
        IndicesStatsResponse stats = client.indices().stats(s-> s.index("*"));
        Map<String, IndicesStats> indices = stats.indices();
        IndicesStats all = stats.all();
        long count = all.total().docs().count();
        System.out.println(count);
        for (String key : indices.keySet()) {
            IndicesStats indicesStats = indices.get(key);
            System.out.println(key + "  " + indicesStats);
        }
    }

    @Test
    public void exists() throws IOException {
        ExistsRequest existsRequest = ExistsRequest.of(e -> e.index(getIndexName()));
        BooleanResponse exists = client.indices().exists(existsRequest);
        System.out.println(exists.value());
    }


    @Test
    public void getIndexMsg() throws IOException {
        GetIndexResponse getIndexResponse = client
                .indices()
                .get(getIndex -> getIndex
                        .index(getIndexName())
                );
        Map<String, IndexState> result = getIndexResponse.result();
        System.out.println(result);
    }

    @Test
    public void create() throws IOException {
        CreateIndexResponse createIndexResponse = client.indices().create(c -> c
                .index("aaa_test2")
                .settings(s -> s
                        .index(i -> i
                                .numberOfShards("3")
                                .numberOfReplicas("0")
                        )
                )
        );
        boolean acknowledged = createIndexResponse.acknowledged();
        System.out.println("acknowledged = " + acknowledged);
    }

    @Test
    public void news() throws IOException {
        CreateIndexResponse createIndexResponse = client
                .indices()
                .create(index -> index.index(indexName)
                );
        boolean acknowledged = createIndexResponse.acknowledged();
        System.out.println("acknowledged = " + acknowledged);
    }

    @Test
    public void delete() throws Exception {
        boolean exists = exists(client, indexName);
        if (!exists) {
            //不存在就结束
            return;
        }
        DeleteIndexResponse deleteIndexResponse = client
                .indices()
                .delete(index -> index.index(indexName)
                );
        boolean acknowledged = deleteIndexResponse.acknowledged();
        System.out.println("acknowledged = " + acknowledged);
    }


    public boolean exists(ElasticsearchClient client, String indexName) throws Exception {
        boolean value = client
                .indices()
                .exists(e -> e
                        .index(indexName)
                ).value();
        System.out.println("index exists:  " + value);
        return value;
    }

    public boolean exists(ElasticsearchClient client, List<String> indexName) throws Exception {
        boolean value = client
                .indices()
                .exists(e -> e
                        .index(indexName)
                ).value();
        System.out.println("index exists:  " + value);
        return value;
    }
}
