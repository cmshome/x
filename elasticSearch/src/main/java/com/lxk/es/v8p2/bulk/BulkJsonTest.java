package com.lxk.es.v8p2.bulk;

import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.JsonpMapper;
import com.lxk.es.v8p2.model.Product;
import com.lxk.tool.util.JsonUtils;
import jakarta.json.spi.JsonProvider;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * bulk 有四种操作
 * index、create、update、delete
 *
 * @author LiXuekai on 2023/7/13
 */
public class BulkJsonTest extends BulkBase {

    /**
     * 直接传json数据，就报错如下：
     * Compressor detection can only be called on some xcontent bytes or compressed xcontent bytes
     */
    @Test
    public void bulkJson() throws IOException {
        Product product = getOne();
        product.setId("112233445566");
        product.setName("abc");

        String json = JsonUtils.parseObjToJson(product);

        BulkOperation index = new BulkOperation.Builder().index(i -> i
                .index(getIndexName())
                .id(product.getId())
                .document(json)
        ).build();
        bulk(index);
    }


    /**
     * 这么写就可以正常入es，但是，会不会oom、以及效率问题，有待确认。
     */
    @Test
    public void bulkIndex() throws IOException {
        Product product = getOne();
        product.setId("112233445566");
        product.setName("abc");

        String json = JsonUtils.parseObjToJson(product);

        BulkOperation index = new BulkOperation.Builder().index(i -> i
                .index(getIndexName())
                .id(product.getId())
                .document(jsonData(json))
        ).build();

        bulk(index);
    }


    /**
     * id要是存在了，就报错。
     * [9999999]: version conflict, document already exists (current version [6])
     */
    @Test
    public void bulkCreate() throws IOException {
        Product product = getOne();
        product.setId("98098989898");

        String json = JsonUtils.parseObjToJson(product);

        BulkOperation index = new BulkOperation.Builder().create(i -> i
                .index(getIndexName())
                .id(product.getId())
                .document(jsonData(json))
        ).build();

        bulk(index);
    }

    /**
     * update 请求，id 必须存在
     */
    @Test
    public void bulkUpdate() throws IOException {
        Product product = getOne();
        product.setName("ls");

        String json = JsonUtils.parseObjToJson(product);

        BulkOperation update = new BulkOperation.Builder().update(i -> i
                .index(getIndexName())
                .id(product.getId())
                .action(a -> a.doc(jsonData(json)))
        ).build();

        bulk(update);
    }

    /**
     *
     */
    @Test
    public void bulkUpsert() throws IOException {
        Product product = getOne();
        product.setId(9999999 + "b");
        product.setName("asd");

        String json = JsonUtils.parseObjToJson(product);

        BulkOperation upsert = new BulkOperation.Builder().update(i -> i
                .index(getIndexName())
                .id(product.getId())
                .action(a -> a
                        .doc(jsonData(json))
                        .docAsUpsert(true)
                )
        ).build();

        bulk(upsert);
    }

    @Test
    public void bulkDelete() throws IOException {
        BulkOperation upsert = new BulkOperation.Builder().delete(i -> i
                .index(getIndexName())
                .id("9999999b")
        ).build();

        bulk(upsert);
    }

    private JsonData jsonData(String data) {
        JsonpMapper jsonpMapper = client._transport().jsonpMapper();
        JsonProvider jsonProvider = jsonpMapper.jsonProvider();
        return JsonData.from(jsonProvider.createParser(new ByteArrayInputStream(data.getBytes())), jsonpMapper);
    }
}
