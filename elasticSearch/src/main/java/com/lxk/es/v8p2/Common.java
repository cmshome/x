package com.lxk.es.v8p2;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.TopHitsAggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.google.common.collect.Lists;
import com.lxk.tool.util.FileIOUtil;
import lombok.Getter;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.junit.Before;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author SHEN on 2018/8/13
 */
public class Common {

    protected ElasticsearchClient client;
    @Getter
    private String indexName;

    /**
     * 获取Client
     */
    @Before
    public void getTransportClient() {
        String path = "/Users/fang/Downloads/test/a.csv";

        List<String> list = FileIOUtil.readFileByLine(path, false);
        String[] split = list.get(0).split(",");
        indexName = split[0];
        String user = split[1];
        String pwd = split[2];
        String ip = split[3];
        Integer port = Integer.parseInt(split[4]);

        System.out.println("index name is " + getIndexName());

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, pwd));

        RestClientBuilder builder = RestClient.builder(
                        new HttpHost(ip, port))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    return httpClientBuilder
                            .setDefaultCredentialsProvider(credentialsProvider);
                });
        // Create the low-level client
        RestClient restClient = builder.build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // And create the API client
        client = new ElasticsearchClient(transport);


    }


    /**
     * 不设置size，只查10个，
     * 设置size，但是，最多只能查1w个
     * 要破解限制，使用 search_after 查询
     */
    public List<Product> all() {
        List<Product> all = Lists.newArrayList();
        try {
            SearchResponse<Product> response = client.search(s -> s
                            .index(getIndexName())
                            .size(10000)
                    ,
                    Product.class
            );

            List<Hit<Product>> hits = response.hits().hits();
            hits.forEach(hit -> {
                Product product = hit.source();
                all.add(product);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return all;
    }



    public void close(){
        try {
            client._transport().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public SortOptions sort(String field, SortOrder sortOrder) {
        return SortOptions.of(s -> s
                .field(FieldSort.of(f -> f
                        .field(field)
                        .order(sortOrder))
                )
        );
    }




    public void show(Query query) throws IOException {
        // Combine name and price queries to search the product index
        SearchResponse<Product> response = client.search(s -> s
                        .index(getIndexName())
                        .query(q -> q
                                .bool(b -> b
                                        .must(query)
                                )
                        )
                        .size(10000),
                Product.class
        );
        show(response);
    }




    public void show(SearchResponse<Product> response) {
        List<Hit<Product>> hits = response.hits().hits();
        System.out.println(hits.size());
        for (Hit<Product> hit : hits) {
            Product product = hit.source();
            Map<String, List<String>> highlight = hit.highlight();
            System.out.println("Found product " + product.toString() + ", score " + hit.score() + "  " + highlight);
        }
    }




    public void showAgg(SearchResponse<Product> response) {
        for (Map.Entry<String, Aggregate> entry : response.aggregations().entrySet()) {
            Aggregate value = entry.getValue();
            Aggregate.Kind kind = value._kind();
            switch (kind) {
                case Min:
                    System.out.println(kind + "   " + value.min().value());
                    break;
                case Max:
                    System.out.println(kind + "   " + value.max().value());
                    break;
                case Sum:
                    System.out.println(kind + "   " + value.sum().value());
                    break;
                case Avg:
                    System.out.println(kind + "   " + value.avg().value());
                    break;
                case ValueCount:
                    System.out.println(kind + "   " + value.valueCount().value());
                    break;
                case Stats:
                    System.out.println(kind + "   " + value.stats());
                    break;
                case Filter:
                    System.out.println(kind + "   " + value.filter());
                    break;
                case TopHits:
                    System.out.println(kind);
                    showTopHits(value);
                    break;
                default:
            }
        }
    }


    public void showTopHits(Aggregate aggregate) {
        TopHitsAggregate topHits = aggregate.topHits();
        HitsMetadata<JsonData> hits = topHits.hits();
        List<Hit<JsonData>> hitList = hits.hits();
        for (Hit<JsonData> jsonDataHit : hitList) {
            JsonData source = jsonDataHit.source();
            System.out.println(source);
        }
    }

}
