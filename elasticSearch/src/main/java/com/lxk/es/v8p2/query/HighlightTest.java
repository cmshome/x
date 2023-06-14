package com.lxk.es.v8p2.query;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.HighlighterOrder;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.google.common.collect.Lists;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import com.lxk.es.v8p2.util.QueryUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
                        .trackTotalHits(QueryUtil.trackHits(true))
                        .size(2000),
                Product.class
        );

        showHighlight(response);

    }


    @Test
    public void builder() throws IOException {
        Highlight.Builder highlightBuilder = new Highlight.Builder();
        highlightBuilder.order(HighlighterOrder.Score)
                .preTags("<font color='yellow'>")
                .postTags("</font>")
                .fields("name", HighlightField.of(h -> h.matchedFields("a").requireFieldMatch(true)));


        Query matchAll = QueryBuilders.matchAll().build()._toQuery();
        Query termsQuery = QueryUtil.termsQuery("name", Lists.newArrayList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));

        SearchRequest.Builder builder = new SearchRequest.Builder()
                .index(getIndexName())
                .highlight(highlightBuilder.build())
                .query(termsQuery)
                .size(1000);

        SearchResponse<Product> response = client.search(builder.build(), Product.class);

        showHighlight(response);

    }


    private void showHighlight(SearchResponse<Product> response) {
        long value = response.hits().total().value();
        List<Hit<Product>> hits = response.hits().hits();
        System.out.println("total count is " + value + "  hits count is " + hits.size());

        int sumHigh = 0;
        for (Hit<Product> hit : hits) {
            Map<String, List<String>> highlight = hit.highlight();
            for (String s : highlight.keySet()) {
                sumHigh++;
                System.out.println("key is " + s + "   value is " + highlight.get(s));
            }
        }

        System.out.println(sumHigh);
    }
}
