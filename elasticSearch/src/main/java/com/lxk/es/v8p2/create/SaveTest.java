package com.lxk.es.v8p2.create;

import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.RefreshResponse;
import com.google.common.collect.Lists;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

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
        product.setId("10007");
        product.setName("防火系统第3套");
        product.setAge(10007);
        product.setStreams(Lists.newArrayList("44","55","66"));
        product.setA(new Product.A("12","23", "34"));
        product.setD(199.99);
        product.setResellers(Lists.newArrayList(new Product.Reseller("XO", 1000), new Product.Reseller("LG", 2000), new Product.Reseller("Nike", 3000)));
        IndexRequest<Product> request = IndexRequest.of(i -> i
                .index(getIndexName())
                .id(product.getId())
                .document(product)
        );

        IndexResponse indexResponse = client.index(request);

        String s = indexResponse.result().jsonValue();
        System.out.println(s);
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






}
