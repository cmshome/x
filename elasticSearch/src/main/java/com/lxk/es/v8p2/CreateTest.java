package com.lxk.es.v8p2;

import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2023/6/12
 */
public class CreateTest extends Common {


    @Test
    public void createMoreThan1w() throws IOException {
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
        boolean errors = result.errors();
        System.out.println(errors);
    }


    private List<Product> fetchProducts() {
        List<Product> list = Lists.newArrayList();
        Map<Integer, String> nameMap = nameMap();
        for (int i = 0; i < 10001; i++) {
            Product product = new Product();
            product.setId(i + "");
            int s = i % 10;
            String name = nameMap.get(s);
            product.setName(name);
            product.setAge(i);
            product.setStreams(Lists.newArrayList(s + ""));
            list.add(product);
        }
        return list;
    }

    private Map<Integer, String> nameMap() {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(0, "a");
        map.put(1, "b");
        map.put(2, "c");
        map.put(3, "d");
        map.put(4, "e");
        map.put(5, "f");
        map.put(6, "g");
        map.put(7, "h");
        map.put(8, "i");
        map.put(9, "j");
        return map;
    }
}
