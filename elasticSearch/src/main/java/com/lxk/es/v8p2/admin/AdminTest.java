package com.lxk.es.v8p2.admin;

import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import com.lxk.es.v8p2.base.Common;
import org.junit.Test;

import java.io.IOException;

/**
 * @author LiXuekai on 2023/7/11
 */
public class AdminTest extends Common {


    @Test
    public void getClusterHealth() throws IOException {
        HealthResponse health = client.cluster().health();
        System.out.println(health.clusterName());
        System.out.println(health.status());
        System.out.println(health.initializingShards());
        System.out.println(health.numberOfNodes());

    }
}
