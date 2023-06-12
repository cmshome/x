package com.lxk.es.v8p2.query;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import org.junit.Test;

import java.io.IOException;

/**
 * @author LiXuekai on 2023/6/9
 */
public class HighlightTest extends Common {

    @Test
    public void highlight() throws IOException {
        Highlight.Builder high = new Highlight.Builder();
        high.fields("name", HighlightField.of(f -> f
                        .preTags("<font color='yellow'>")
                        .postTags("</font>")
                        .requireFieldMatch(false)
                        .highlightQuery(q -> q.term(t -> t.field("name").value("a")))
                )
        );

        Query matchAll = QueryBuilders.matchAll().build()._toQuery();
        SearchResponse<Product> response = client.search(s -> s
                        .index(getIndexName())
                        .query(matchAll)
                        .highlight(high.build())
                        .size(10000),
                Product.class
        );
        show(response);
    }
}
