package com.lxk.es.v8p2.script;

import co.elastic.clients.elasticsearch._types.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.TrackHits;
import com.lxk.es.v8p2.base.Common;
import org.junit.Test;

import java.io.IOException;

/**
 * @author LiXuekai on 2023/6/21
 */
public class ScriptTest extends Common {

    /**
     * 看不出来这个sort是怎么排序的
     */
    @Test
    public void script() throws IOException {
        ScriptSort scriptSort = new ScriptSort.Builder()
                .script(s -> s.inline(x -> x.source("Math.random()")))
                .type(ScriptSortType.Number)
                .order(SortOrder.Desc)
                .build();
        SortOptions sortOptions = new SortOptions.Builder().script(scriptSort).build();
        TrackHits trackHits = new TrackHits.Builder().enabled(true).build();
        SearchRequest.Builder builder = new SearchRequest.Builder()
                .query(q -> q.matchAll(m -> m))
                .sort(sortOptions)
                .trackTotalHits(trackHits)
                .size(10);

        show(builder);

    }
}
