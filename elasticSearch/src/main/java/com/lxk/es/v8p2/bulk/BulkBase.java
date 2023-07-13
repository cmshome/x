package com.lxk.es.v8p2.bulk;

import co.elastic.clients.elasticsearch._types.ErrorCause;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;

import java.io.IOException;
import java.util.List;

/**
 * 参考链接：
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.2/indexing-bulk.html">es bulk</a>
 *
 * @author LiXuekai on 2023/7/13
 */
public class BulkBase extends Common {

    public Product getOne() {
        Product product = new Product();
        product.setName("aaa");
        product.setAge(9999999);
        product.setId(9999999 + "");
        product.setD(12.D);
        return product;
    }

    public void bulk(BulkOperation bulkOperation) throws IOException {
        BulkRequest.Builder builder = new BulkRequest.Builder();
        builder.operations(bulkOperation);

        BulkResponse result = client.bulk(builder.build());
        showError(result);
    }

    public void showError(BulkResponse result) {
        boolean errors = result.errors();
        System.out.println("error = " + errors);
        List<BulkResponseItem> items = result.items();
        for (BulkResponseItem item : items) {
            ErrorCause error = item.error();
            if (error == null) {
                continue;
            }
            String reason = error.reason();
            System.out.println(reason);
            System.out.println(error);
        }
    }
}
