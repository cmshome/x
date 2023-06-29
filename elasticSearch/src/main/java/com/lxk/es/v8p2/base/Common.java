package com.lxk.es.v8p2.base;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TrackHits;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lxk.es.v8p2.model.Product;
import com.lxk.es.v8p2.util.QueryUtil;
import com.lxk.tool.util.FileIOUtil;
import jakarta.json.JsonValue;
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
import java.util.TreeMap;

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
                            .trackTotalHits(trackHits())
                            .size(10000)
                    ,
                    Product.class
            );

            // 总的个数，也许比下面返回的hits总数大
            System.out.println("response.hits().total().value() = " + response.hits().total().value());
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

    public SearchRequest.Builder baseSearchRequest() {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(getIndexName());
        return builder;
    }

    public void close() {
        try {
            client._transport().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SearchResponse<Product> search(SearchRequest searchRequest) throws IOException {
        System.out.println(searchRequest);
        return client.search(searchRequest, Product.class);
    }


    public void add(List<Product> all, List<Hit<Product>> hits1) {
        for (Hit<Product> hit : hits1) {
            all.add(hit.source());
        }
    }

    public TrackHits trackHits() {
        return QueryUtil.trackHits(true);
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
                        // 查询所有，最终的 total().value()的值能大于1w，但是，详情还是最大1w
                        .trackTotalHits(trackHits())
                        .size(10000),
                Product.class
        );
        show(response);
    }


    public void show(SearchRequest.Builder builder) throws IOException {
        SearchResponse<Product> response = search(builder.build());
        show(response);
    }

    public void show(SearchResponse<Product> response) {
        HitsMetadata<Product> hits1 = response.hits();
        List<Hit<Product>> hits = hits1.hits();
        System.out.println("total count = " + hits1.total().value());
        showHis(hits);
    }

    public void showHis(List<Hit<Product>> hits) {
        for (Hit<Product> hit : hits) {
            Product product = hit.source();
            Map<String, List<String>> highlight = hit.highlight();
            System.out.println("Found product " + product.toString() + ", score " + hit.score() + "  " + highlight);
        }
    }

    public void agg(Aggregation aggregation) throws IOException {
        SearchRequest request = baseSearchRequest()
                .aggregations("aggs", aggregation)
                .trackTotalHits(trackHits())
                .query(q -> q.matchAll(m -> m))
                .size(3)
                .build();
        SearchResponse<Product> response = search(request);
        showAgg(response);
    }

    public void showAgg(SearchResponse<Product> response) {
        for (Map.Entry<String, Aggregate> entry : response.aggregations().entrySet()) {
            Aggregate value = entry.getValue();
            showAgg(value);
        }
    }

    public void showAgg(Aggregate aggregate) {
        if (aggregate == null) {
            return;
        }
        Aggregate.Kind kind = aggregate._kind();
        switch (kind) {
            case Min:
                System.out.println(kind + "   " + aggregate.min().value());
                break;
            case Max:
                System.out.println(kind + "   " + aggregate.max().value());
                break;
            case Sum:
                System.out.println(kind + "   " + aggregate.sum().value());
                break;
            case Avg:
                System.out.println(kind + "   " + aggregate.avg().value());
                break;
            case ValueCount:
                System.out.println(kind + "   " + aggregate.valueCount().value());
                break;
            case Stats:
                System.out.println(kind + "   " + aggregate.stats());
                break;
            case Filter:
                System.out.println(kind + "   " + aggregate.filter());
                FilterAggregate filter = aggregate.filter();
                Map<String, Aggregate> aggregations = filter.aggregations();
                subAgg(aggregations);
                break;
            case Filters:
                System.out.println(kind);
                FiltersAggregate filters = aggregate.filters();
                Buckets<FiltersBucket> buckets = filters.buckets();
                List<FiltersBucket> list = buckets.array();
                for (FiltersBucket filtersBucket : list) {
                    System.out.println(filtersBucket);
                }
                break;
            case TopHits:
                System.out.println(kind);
                showTopHits(aggregate);
                break;
            case Cardinality:
                System.out.println(kind + "   " + aggregate.cardinality().value());
                break;
            case Sterms:
                System.out.println(kind);
                List<StringTermsBucket> array = aggregate.sterms().buckets().array();
                for (StringTermsBucket bucket : array) {
                    System.out.println(bucket.key() + "  " + bucket.docCount());
                    Map<String, Aggregate> map = bucket.aggregations();
                    subAgg(map);
                }
                break;
            case Lterms:
                System.out.println(kind);
                List<LongTermsBucket> array1 = aggregate.lterms().buckets().array();
                for (LongTermsBucket bucket : array1) {
                    System.out.println(bucket.key() + "  " + bucket.docCount());
                    Map<String, Aggregate> map = bucket.aggregations();
                    subAgg(map);
                }
                break;
            case Dterms:
                System.out.println(kind);
                List<DoubleTermsBucket> array2 = aggregate.dterms().buckets().array();
                for (DoubleTermsBucket bucket : array2) {
                    System.out.println(bucket.key() + "  " + bucket.docCount());
                    Map<String, Aggregate> map = bucket.aggregations();
                    subAgg(map);
                }
                break;
            case Range:
                System.out.println(kind);
                List<RangeBucket> bucketList = aggregate.range().buckets().array();
                for (RangeBucket rangeBucket : bucketList) {
                    System.out.println(rangeBucket.key() + "   " + rangeBucket);
                }
                break;
            case TdigestPercentiles:
                System.out.println(kind);
                Percentiles values = aggregate.tdigestPercentiles().values();
                show(values);
                break;
            case TdigestPercentileRanks:
                System.out.println(kind);
                Percentiles percentiles = aggregate.tdigestPercentileRanks().values();
                show(percentiles);
                break;
            case Nested:
                System.out.println(kind + "   " + aggregate.nested());
                NestedAggregate nested = aggregate.nested();
                Map<String, Aggregate> subAgg = nested.aggregations();
                subAgg(subAgg);
                break;
            default:
                System.out.println(kind + "   default...");
        }
    }

    private void subAgg(Map<String, Aggregate> aggregations) {
        if (aggregations != null && !aggregations.isEmpty()) {
            for (Map.Entry<String, Aggregate> entry : aggregations.entrySet()) {
                showAgg(entry.getValue());
            }
        }
    }

    private void show(Percentiles values) {
        Map<String, String> keyed = values.keyed();
        TreeMap<String, String> tree = Maps.newTreeMap();
        tree.putAll(keyed);
        for (Map.Entry<String, String> entry : tree.entrySet()) {
            System.out.println(entry.getKey() + "   " + entry.getValue());
        }
    }

    public void showTopHits(Aggregate aggregate) {
        TopHitsAggregate topHits = aggregate.topHits();
        HitsMetadata<JsonData> hits = topHits.hits();
        List<Hit<JsonData>> hitList = hits.hits();
        for (Hit<JsonData> hit : hitList) {
            JsonData source = hit.source();
            System.out.println(source);
            Map<String, JsonData> fields = hit.fields();
            for (Map.Entry<String, JsonData> entry : fields.entrySet()) {
                String entryKey = entry.getKey();
                JsonData value = entry.getValue();
                JsonValue json = value.toJson();
                System.out.println(entryKey + "  " + json);
            }
        }
    }

    public void showHisBucket(SearchResponse<Product> response) {
        Map<String, Aggregate> aggregateMap = response.aggregations();
        for (Map.Entry<String, Aggregate> entry : aggregateMap.entrySet()) {
            Aggregate aggregate = entry.getValue();
            List<HistogramBucket> buckets = aggregate
                    .histogram()
                    .buckets()
                    .array();
            for (HistogramBucket bucket : buckets) {
                System.out.println("bucket.key " + bucket.key() + "   bucket.docCount " + bucket.docCount());

                Map<String, Aggregate> map = bucket.aggregations();
                for (Map.Entry<String, Aggregate> subMap : map.entrySet()) {
                    Aggregate subAgg = subMap.getValue();
                    showAgg(subAgg);
                }
            }
        }
    }


}
