package com.lxk.es.v8p2.bulk;

import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperationBuilders;
import co.elastic.clients.elasticsearch.core.bulk.DeleteOperation;
import com.lxk.es.v8p2.model.Product;
import org.junit.Test;

import java.io.IOException;

/**
 * bulk 有四种操作
 * index、index a document, creating it if needed and replacing it if it exists,
 * create、create a document, indexing it after ensuring it doesn’t already exist,
 * update、update a document that already exists in place, either with a script or a partial document,
 * delete 、delete a document.
 *
 * @author LiXuekai on 2023/7/10
 */
public class BulkTest extends BulkBase {


    @Test
    public void builder() {
        DeleteOperation deleteOperation = BulkOperationBuilders.delete().id("2").index(getIndexName()).build();
    }


    @Test
    public void bulkIndex() throws IOException {
        Product product = getOne();
        BulkOperation index = new BulkOperation.Builder().index(i -> i
                .index(getIndexName())
                .id(product.getId())
                .document(product)
        ).build();

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


}
