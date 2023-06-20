package com.lxk.es.v8p2.create;

import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.google.common.collect.Lists;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import org.junit.Test;

import java.io.IOException;

/**
 * @author LiXuekai on 2023/6/19
 */
public class UpsertTest extends Common {

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
