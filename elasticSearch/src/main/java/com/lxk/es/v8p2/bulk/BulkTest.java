package com.lxk.es.v8p2.bulk;

import co.elastic.clients.elasticsearch._types.ErrorCause;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import com.lxk.tool.util.JsonUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * bulk 有四种操作
 * index、create、update、delete
 *
 * @author LiXuekai on 2023/7/10
 */
public class BulkTest extends Common {


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


    @Test
    public void bulkIndex() throws IOException {
        Product product = getOne();
        BulkOperation index = new BulkOperation.Builder().index(i -> i.index(getIndexName()).id(product.getId()).document(product)).build();

        bulk(index);
    }

    /**
     * update 请求，id 必须存在
     */
    @Test
    public void bulkUpdate() throws IOException {
        Product product = getOne();
        product.setName("asddsadad");

        BulkOperation update = new BulkOperation.Builder().update(i -> i
                .index(getIndexName())
                .id(product.getId())
                .action(a -> a.doc(product))
        ).build();

        bulk(update);
    }

    /**
     *
     */
    @Test
    public void bulkUpsert() throws IOException {
        Product product = getOne();
        product.setId(9999999 + "a");
        product.setName("lxk");

        BulkOperation upsert = new BulkOperation.Builder().update(i -> i
                .index(getIndexName())
                .id(product.getId())
                .action(a -> a
                        .doc(product)
                        .docAsUpsert(true)
                )
        ).build();

        bulk(upsert);
    }

    @Test
    public void bulkDelete() throws IOException {
        BulkOperation upsert = new BulkOperation.Builder().delete(i -> i
                .index(getIndexName())
                .id("2")
        ).build();

        bulk(upsert);
    }

    private void bulk(BulkOperation bulkOperation) throws IOException {
        BulkRequest.Builder builder = new BulkRequest.Builder();
        builder.operations(bulkOperation);

        BulkResponse result = client.bulk(builder.build());
        showError(result);
    }

    private void showError(BulkResponse result) {
        boolean errors = result.errors();
        System.out.println("error = " + errors);
        List<BulkResponseItem> items = result.items();
        for (BulkResponseItem item : items) {
            ErrorCause error = item.error();
            if (error == null) {
                continue;
            }
            String reason = error.reason();
            System.out.println(error);
        }
    }

    private Product getOne() {
        Product product = new Product();
        product.setName("aaa");
        product.setAge(9999999);
        product.setId(9999999 + "");
        product.setD(12.D);
        return product;
    }


}
