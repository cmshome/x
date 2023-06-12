package com.lxk.es.v8p2.query;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2023/6/9
 */
public class SearchAfterTest extends Common {

    @Test
    public void searchAfter() throws IOException {
        List<Product> all = Lists.newArrayList();

        SearchResponse<Product> response = client.search(getSearchBuilder().build(), Product.class);
        List<Hit<Product>> hits1 = response.hits().hits();
        while (hits1.size() > 0) {
            add(all, hits1);
            List<Hit<Product>> hits = response.hits().hits();
            Hit<Product> hit = hits.get(hits.size() - 1);
            String id = hit.id();
            SearchRequest.Builder builder = getSearchBuilder().searchAfter(id);
            response = client.search(builder.build(), Product.class);
            hits1 = response.hits().hits();
        }

        System.out.println(all.size());
        for (Product product : all) {
            System.out.println(product);
        }
    }

    private SearchRequest.Builder getSearchBuilder() {
        SearchRequest.Builder builderAfter = new SearchRequest.Builder();
        builderAfter.index(getIndexName());
        builderAfter.query(q -> q
                .matchAll(m -> m)
        );
        SortOptions sortOptions2 = SortOptions.of(s -> s.field(f -> f.field("id").order(SortOrder.Desc)));
        builderAfter.sort(sortOptions2);
        builderAfter.size(3);
        return builderAfter;
    }



    /**
     * 1w条数据以后的分页
     *
     * @param key
     * @param page
     * @param limit
     * @param lastId   上一页最后一条数据唯一id
     * @param lastTime 上一页最后一条数据时间
     * @return
     * @throws IOException
     */
    public List<Product> searchAfter(String key, Integer page, Integer limit, String lastId, long lastTime) throws IOException {
        SearchRequest.Builder builderAfter = new SearchRequest.Builder();
        builderAfter.index(getIndexName());
        BoolQuery.Builder boolQuery = new BoolQuery.Builder();
        //设置查询条件
        if (!Strings.isNullOrEmpty(key)) {
            boolQuery.should(q -> q.matchPhrasePrefix(m -> m
                    .query(key)
                    .field("title")
            ));
            boolQuery.should(q -> q.matchPhrasePrefix(m -> m.query(key).field("content")));
        }
        //时间范围查询 paramMap startTime endTime
        //boolQuery.must(q->q.range(r->r.field("createTime").gte(JsonData.of(param.get("startTime"))).lte(JsonData.of(param.get("endTime")))));
        builderAfter.query(q -> q.bool(boolQuery.build()));
        //设置排序
        List<SortOptions> sorts = new ArrayList<>();
        SortOptions sortOptions2 = SortOptions.of(s -> s.field(f -> f.field("id.keyword").order(SortOrder.Desc)));
        SortOptions sortOptions = SortOptions.of(s -> s.field(f -> f.field("createTime").order(SortOrder.Desc)));
        //注意先后顺序
        sorts.add(sortOptions);
        sorts.add(sortOptions2);
        builderAfter.sort(sorts);
        builderAfter.size(limit);
        //第一页不需要设置，当第二页时需要带排序字段
        if (page > 0) {
            builderAfter.searchAfter(
                    String.valueOf(lastTime),
                    lastId
            );
        }
        //设置所有字段匹配高亮。注：高亮标签可根据前端需求自定义
        if (!Strings.isNullOrEmpty(key)) {
            Highlight.Builder highlightBuilder = new Highlight.Builder();
            highlightBuilder.fields("*", new HighlightField.Builder().build()).preTags("<span style=\"color:red\">").postTags("</span>").requireFieldMatch(false);
            builderAfter.highlight(highlightBuilder.build());
        }
        //发送查询
        SearchResponse response = client.search(builderAfter.build(), Product.class);
        List<Product> list = new ArrayList<>();
        List<Hit<Product>> hits = response.hits().hits();
        for (Hit<Product> hit : hits) {
            Product esMess = hit.source();
            //获取高亮数据添加到实体类中返回
            Map<String, List<String>> highlightMap = hit.highlight();
            if (highlightMap != null && !highlightMap.isEmpty()) {
                highlightMap.keySet().forEach(k -> {
                    try {
                        if (k.contains("keyword")) {
                            k = k.replace(".keyword", "");
                        }
                        Field field = esMess.getClass().getDeclaredField(k);
                        field.setAccessible(true);
                        field.set(esMess, highlightMap.get(k).get(0));
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            list.add(esMess);
        }
        return list;
    }
}
