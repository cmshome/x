package com.lxk.es.v8p2.create;

import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.google.common.collect.Lists;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import org.junit.Test;

import java.io.IOException;

/**
 * @author LiXuekai on 2023/6/19
 */
public class UpdateTest extends Common {

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
        product.setStreams(Lists.newArrayList("111", "222", "333"));
        UpdateResponse<Product> response = client
                .update(x -> x
                        .index(getIndexName())
                        .id("56789765")
                        .doc(product), Product.class);

        show(response);

    }

    private void show(UpdateResponse<Product> response) {
        String s = response.result().jsonValue();
        long version = response.version();
        System.out.println(s);
        System.out.println(version);
    }


    @Test
    public void request() throws IOException {
        UpdateRequest.Builder<Product, Product> builder = new UpdateRequest.Builder<Product, Product>()
                .index(getIndexName());

        UpdateResponse<Product> response = client.update(builder.build(), Product.class);
        show(response);
    }


}
