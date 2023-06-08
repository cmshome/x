package com.lxk.es.v8p2;

import co.elastic.clients.elasticsearch.indices.RefreshResponse;
import org.junit.Test;

import java.io.IOException;

/**
 * @author LiXuekai on 2023/5/29
 */
public class RefreshTest extends Common {

    @Test
    public void refresh() throws IOException {
        RefreshResponse refresh = client.indices().refresh(r->r.index(getIndexName()));
        System.out.println(refresh.toString());
    }
}
