package com.lxk.es.v8p2;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.*;
import com.google.common.collect.Lists;
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
        System.out.println(stats);
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
        CreateIndexResponse createIndexResponse = client.indices().create(c -> c.index(indexName));
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
