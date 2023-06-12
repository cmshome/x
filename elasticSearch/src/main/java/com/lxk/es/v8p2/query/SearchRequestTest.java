package com.lxk.es.v8p2.query;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.TrackHits;
import com.lxk.es.v8p2.base.Common;
import org.junit.Test;

import java.io.IOException;

/**
 * @author LiXuekai on 2023/6/9
 */
public class SearchRequestTest extends Common {


    @Test
    public void searchRequest() throws IOException {
        TrackHits trackHits = new TrackHits.Builder().enabled(true).build();
        SearchRequest.Builder builder = new SearchRequest.Builder()
                .query(q -> q.matchAll(m -> m))
                .sort(sort("name", SortOrder.Desc))
                .trackTotalHits(trackHits)
                .size(0)
                ;

        show(builder);
    }




}
