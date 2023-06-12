package com.lxk.es.v8p2.index;

import co.elastic.clients.elasticsearch.indices.ClearCacheResponse;
import co.elastic.clients.elasticsearch.indices.RefreshResponse;
import com.lxk.es.v8p2.base.Common;
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

    @Test
    public void clearCache() throws IOException {
        ClearCacheResponse response = client.indices().clearCache();
        System.out.println(response.toString());

    }
}
