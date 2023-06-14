package com.lxk.es.v8p2.query;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch.core.ScrollResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.google.common.collect.Lists;
import com.lxk.es.v8p2.base.Common;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2023/6/12
 */
public class ScrollTest extends Common {

    /**
     * 不再推荐使用scroll的查询了，推荐使用 search_after
     */
    @Test
    public void scrollSearch() throws IOException {
        SearchRequest.Builder builder = new SearchRequest.Builder()
                .query(q -> q.matchAll(m -> m))
                .sort(sort("name", SortOrder.Desc))
                .scroll(Time.of(t -> t.time("1m")))
                .size(1000);

        SearchResponse<Object> response = client.search(builder.build(), Object.class);
        System.out.println(response.hits().total().value());
        int num = response.hits().hits().size();
        List<Object> list = Lists.newArrayList();
        for (Hit<Object> hit : response.hits().hits()) {
            Map<String, JsonData> fields = hit.fields();

            for (Map.Entry<String, JsonData> entry : fields.entrySet()) {
                String entryKey = entry.getKey();
                JsonData data = entry.getValue();
            }
            list.add(hit.source());
        }

        String scrollId = response.scrollId();
        while (num > 0) {
            String finalScrollId = scrollId;
            ScrollResponse<Object> scroll = client.scroll(s -> s
                            .scrollId(finalScrollId)
                            .scroll(Time.of(t -> t.time("1m")))
                    , Object.class);
            scrollId = scroll.scrollId();
            num = scroll.hits().hits().size();
            for (Hit<Object> hit : scroll.hits().hits()) {
                list.add(hit.source());
            }
        }

        System.out.println(list.size());

    }


}
