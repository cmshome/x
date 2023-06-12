package com.lxk.es.v8p2;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.*;
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
    public void deleteAll() throws IOException {
        List<Product> all = all();
        for (Product product : all) {
            deleteById(product.getId());
        }
    }

    @Test
    public void deleteById() throws IOException {
        String id = "139";
        deleteById(id);
    }

    private void deleteById(String id) throws IOException {
        DeleteRequest request = DeleteRequest.of(s -> s
                .index(getIndexName())
                .id(id)
        );
        DeleteResponse delete = client.delete(request);
        String s = delete.result().jsonValue();
        System.out.println(s);
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

    @Test
    public void deleteByQuery() throws IOException {
        Query query = TermQuery.of(t -> t.field("id").value("9997"))._toQuery();
        DeleteByQueryResponse response = client.deleteByQuery(d -> d
                .index(getIndexName())
                .query(query)
        );
        Long deleted = response.deleted();
        System.out.println(deleted);
    }


}
