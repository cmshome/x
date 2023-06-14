package com.lxk.es.v8p2.create;

import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.indices.RefreshResponse;
import com.google.common.collect.Lists;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

/**
 * @author LiXuekai on 2023/5/25
 */
public class SaveTest extends Common {

    /**
     * 不明显的设置id，那下面的这个返回结果一直是Created，id属性相同，但是_id的值是不同的
     */
    @Test
    public void createByJson() throws IOException {
        String json = "{\"id\":\"1122\",\"name\":\"aaa\",\"age\":123}";
        Reader queryJson = new StringReader(json);
        IndexRequest<Product> request = IndexRequest.of(i -> i
                .index(getIndexName())
                .withJson(queryJson)
        );

        IndexResponse indexResponse = client.index(request);

        Result result = indexResponse.result();
        String s = result.jsonValue();
        System.out.println(s);

        refreshIndex();
    }

    private void refreshIndex() throws IOException {
        RefreshResponse refresh = client.indices().refresh(r->r.index(getIndexName()));
        System.out.println(refresh.toString());
    }


    @Test
    public void insert() throws IOException {
        Product product = new Product();
        product.setId("10001");
        product.setName("AAAA");
        product.setAge(10001);
        product.setStreams(Lists.newArrayList("111","222","333"));
        product.setA(new Product.A("x","y", "z"));

        IndexRequest<Product> request = IndexRequest.of(i -> i
                .index(getIndexName())
                .id(product.getId())
                .document(product)
        );

        IndexResponse indexResponse = client.index(request);

        String s = indexResponse.result().jsonValue();
        System.out.println(s);
    }


    /**
     * 要更新的数据必须得存在，才能走updaye请求，不然，抛异常。
     * [es/update] failed: [document_missing_exception] [56789765]: document missing
     */
    @Test
    public void update() throws IOException {
        Product product = new Product();
        product.setId("56789765");
        product.setName("ddd-");
        product.setAge(999);
        product.setStreams(Lists.newArrayList("111","222","333"));
        UpdateResponse<Object> userUpdateResponse = client
                .update(x -> x
                        .index(getIndexName())
                        .id("56789765")
                        .doc(product), Object.class);
        String s = userUpdateResponse.result().jsonValue();
        long version = userUpdateResponse.version();
        System.out.println(s);
        System.out.println(version);
    }


    @Test
    public void bulk() throws IOException {
        List<Product> products = fetchProducts();

        BulkRequest.Builder builder = new BulkRequest.Builder();

        for (Product product : products) {
            builder.operations(op -> op
                    .index(idx -> idx
                            .index(getIndexName())
                            .id(product.getId())
                            .document(product)
                    )
            );
        }

        BulkResponse result = client.bulk(builder.build());

        if (result.errors()) {
            System.out.println("Bulk had errors");
            for (BulkResponseItem item : result.items()) {
                String result1 = item.result();
                if (item.error() != null) {
                    System.out.println(item.error().reason());
                }
            }
        }
    }

    private List<Product> fetchProducts() {
        List<Product> list = Lists.newArrayList();
        for (int i = 100; i < 150; i++) {
            Product product = new Product();
            product.setName("d");
            product.setAge(i);
            product.setId(i + "");
            list.add(product);
        }
        return list;
    }


    //@Test
    //public void json() throws FileNotFoundException {
    //
    //    // List json log files in the log directory
    //    File[] logFiles = logDir.listFiles(
    //            file -> file.getName().matches("log-.*\\.json")
    //    );
    //
    //    BulkRequest.Builder br = new BulkRequest.Builder();
    //
    //    for (File file: logFiles) {
    //        JsonData json = readJson(new FileInputStream(file), client);
    //
    //        br.operations(op -> op
    //                .index(idx -> idx
    //                        .index("logs")
    //                        .document(json)
    //                )
    //        );
    //    }
    //}
    //
    //public static JsonData readJson(InputStream input, ElasticsearchClient esClient) {
    //    JsonpMapper jsonpMapper = esClient._transport().jsonpMapper();
    //    JsonProvider jsonProvider = jsonpMapper.jsonProvider();
    //
    //    return JsonData.from(jsonProvider.createParser(input), jsonpMapper);
    //}





    @Test
    public void upsert() throws IOException {
        Product product = new Product();
        product.setId("987789");
        product.setName("a9qq7-");
        product.setAge(987789);
        product.setStreams(Lists.newArrayList("1111","2222","3333"));
        UpdateResponse<Object> userUpdateResponse = client
                .update(x -> x
                        .index(getIndexName())
                        .id("987789")
                        .upsert(product)
                        .doc(product), Object.class);
        String s = userUpdateResponse.result().jsonValue();
        long version = userUpdateResponse.version();
        System.out.println(s);
        System.out.println(version);
    }
}
