package com.lxk.es.v8p2;

import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.google.common.collect.Lists;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiXuekai on 2023/5/25
 */
public class DeleteTest extends Common {


    @Test
    public void deleteById() throws IOException {
        List<Product> all = all();
        for (Product product : all) {
            DeleteRequest request = DeleteRequest.of(s -> s
                    .index(getIndexName())
                    .id(product.getId())
            );
            DeleteResponse delete = client.delete(request);
            String s = delete.result().jsonValue();
            System.out.println(s);
        }
    }


    @Test
    public void bulk() throws IOException {
        BulkRequest.Builder builder = new BulkRequest.Builder();
        ArrayList<String> list = Lists.newArrayList("1", "2", "3");
        for (String s : list) {
            builder.operations(op -> op
                    .delete(idx -> idx
                            .index(getIndexName())
                            .id(s)
                    )
            );
        }

        BulkResponse result = client.bulk(builder.build());

        for (BulkResponseItem item : result.items()) {
            System.out.println(item.result());
        }

    }


}
