package com.lxk.es.v8p2.query;

import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.mget.MultiGetResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import co.elastic.clients.json.JsonData;
import com.google.common.collect.Lists;
import com.lxk.es.v8p2.base.Common;
import com.lxk.es.v8p2.model.Product;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author LiXuekai on 2023/5/25
 */
public class QueryTest extends Common {

    @Test
    public void terms2() throws IOException {
        List<FieldValue> list = Lists.newArrayList();
        list.add(new FieldValue.Builder().stringValue("test").build());
        Query query = TermsQuery.of(t -> t
                .field("type")
                .terms(terms -> terms.value(list))
        )._toQuery();
        show(query);
    }


    @Test
    public void terms() throws IOException {
        List<FieldValue> list = Lists.newArrayList();
        list.add(new FieldValue.Builder().stringValue("9").build());
        list.add(new FieldValue.Builder().stringValue("16").build());

        Query query = TermsQuery.of(t -> t
                .field("age")
                .terms(terms -> terms.value(list))
        )._toQuery();
        show(query);
    }

    @Test
    public void term() throws IOException {
        Query query = TermQuery.of(t -> t
                .field("id")
                .value("99")
        )._toQuery();
        show(query);
    }

    @Test
    public void search() throws IOException {
        SearchResponse<Product> response = client.search(s -> s
                        .index(getIndexName())
                        .query(q -> q
                                .term(t -> t
                                        .field("name")
                                        .value(v -> v.stringValue("a99"))
                                )),
                Product.class);

        show(response);
    }

    @Test
    public void match() throws IOException {
        String id = "99";
        SearchResponse<Product> response = client.search(s -> s
                        .index(getIndexName())
                        .query(q -> q
                                .match(t -> t
                                        .field("id")
                                        .query(id)
                                )
                        ),
                Product.class
        );

        TotalHits total = response.hits().total();
        boolean isExactResult = total.relation() == TotalHitsRelation.Eq;

        if (isExactResult) {
            System.out.println("There are " + total.value() + " results");
        } else {
            System.out.println("There are more than " + total.value() + " results");
        }
        show(response);
    }


    @Test
    public void many() throws IOException {
        String keyword = "a99";
        Long maxPrice = 50L;

        Query byProduct = MatchQuery.of(m -> m
                .field("name")
                .query(keyword)
        )._toQuery();

        Query byMaxPrice = RangeQuery.of(r -> r
                .field("age")
                .gte(JsonData.of(maxPrice))
        )._toQuery();

        SortOptions sortOptions = SortOptions.of(s -> s
                .field(FieldSort.of(f -> f
                        .field("id")
                        .order(SortOrder.Desc))
                )
        );

        SearchResponse<Product> response = client.search(s -> s
                        .index(getIndexName())
                        .query(q -> q
                                .bool(b -> b
                                        .must(byProduct)
                                        .must(byMaxPrice)
                                )
                        )
                        .from(0)
                        .size(10),
                Product.class
        );
        show(response);
    }


    /**
     * 不设置size，只查10个，
     * 设置size，但是，最多只能查1w个
     */
    @Test
    public void findAll() {
        List<Product> all = all();
        System.out.println(all.size());
        for (Product product : all) {
            System.out.println(product);
        }
    }


    @Test
    public void pageSearch() throws IOException {
        SearchResponse<Product> response = client.search(e -> e
                        .index(getIndexName())
                        .sort(sort -> sort
                                .field(f -> f
                                        .field("age")
                                        .order(SortOrder.Desc)
                                )
                        )
                        .from(0)
                        .size(10)
                , Product.class);

        show(response);
    }


    @Test
    public void queryBuilders() throws IOException {
        Query term = QueryBuilders.term(t -> t
                .field("name")
                .value(v -> v.stringValue("c")));
        show(term);
    }


    @Test
    public void many2() throws IOException {
        Query id = termQuery("id", "5");
        Query name = termQuery("name", "a");
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(getIndexName());
        builder.query(q -> q
                .bool(b -> b
                        .must(id)
                        .must(name))
        );
        show(builder);
    }


    private Query termQuery(String field, String value) {
        return TermQuery.of(t -> t
                .field(field)
                .value(value)
        )._toQuery();
    }


    /**
     * 设置size的查询，不然就是返回10个
     */
    @Test
    public void should() throws IOException {
        Query id = termQuery("id", "5");
        Query name = termQuery("name", "c");
        Query age = RangeQuery.of(r -> r
                .field("age")
                .gte(JsonData.of(30))
        )._toQuery();
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(getIndexName());
        builder.query(q -> q
                        .bool(b -> b
                                .must(age)
                                .should(id)
                                .should(name)
                                // 必须至少满足1个should
                                .minimumShouldMatch("1")
                        )
                )
                .size(10000);
        show(builder);
    }

    /**
     * 用 search after 的方式查所有
     */
    @Test
    public void shouldAll() throws IOException {
        List<Product> all = Lists.newArrayList();
        SearchResponse<Product> response = search(shouldSearchBuild().build());
        List<Hit<Product>> hits1 = response.hits().hits();
        while (hits1.size() > 0) {
            add(all, hits1);
            List<Hit<Product>> hits = response.hits().hits();
            Hit<Product> hit = hits.get(hits.size() - 1);
            String id = hit.id();
            SearchRequest.Builder builder = shouldSearchBuild().searchAfter(id);
            response = client.search(builder.build(), Product.class);
            hits1 = response.hits().hits();
        }
        System.out.println(all.size());
        for (Product product : all) {
            System.out.println(product);
        }
    }


    private SearchRequest.Builder shouldSearchBuild() {
        Query id = termQuery("id", "5");
        Query name = termQuery("name", "c");
        Query age = RangeQuery.of(r -> r
                .field("age")
                .gte(JsonData.of(30))
        )._toQuery();
        Query exists = ExistsQuery.of(e -> e
                .field("name"))._toQuery();
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(getIndexName());
        builder.query(q -> q
                .bool(b -> b
                                //.must(age)
                                .must(exists)
                        //.should(id)
                        //.should(name)
                )

        );
        SortOptions sortOptions2 = SortOptions.of(s -> s.field(f -> f.field("id").order(SortOrder.Desc)));
        builder.sort(sortOptions2);
        builder.size(3);
        return builder;
    }

    @Test
    public void exists() throws IOException {
        Query query = ExistsQuery.of(e -> e
                .field("a.x"))._toQuery();
        show(query);
    }

    @Test
    public void wildcardQuery() throws IOException {
        Query query = WildcardQuery.of(w -> w
                .field("name")
                .wildcard("防火系统*")
        )._toQuery();
        show(query);
    }

    @Test
    public void matchQuery() throws IOException {
        Query query = MatchQuery.of(m -> m
                .field("name")
                .query("AAAA")
        )._toQuery();
        show(query);
    }

    @Test
    public void matchQuery2() throws IOException {
        Query query = MatchQuery.of(m -> m
                .field("a.x")
                .query("x")
        )._toQuery();
        show(query);
    }

    /**
     * 会自动的解析到a对象到x属性
     */
    @Test
    public void termQuery3() throws IOException {
        Query query = TermQuery.of(t -> t
                .field("a.x")
                .value("x")
        )._toQuery();
        show(query);
    }


    /**
     * 根据需要只查询某些字段
     */
    @Test
    public void fetchSource() throws IOException {
        List<String> excludes = Lists.newArrayList("id", "age");
        List<String> includes = Lists.newArrayList("name");

        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(getIndexName());
        builder.query(q -> q.matchAll(m -> m));
        builder.trackTotalHits(trackHits());
        builder.source(s -> s.filter(f -> f
                .excludes(excludes)
                .includes(includes)
        ));

        show(builder);
    }

    @Test
    public void idQuery() throws IOException {
        Query query = IdsQuery.of(i -> i.values(Lists.newArrayList("6", "8")))._toQuery();
        show(query);
    }

    @Test
    public void queryStringQuery() throws IOException {
        Query query = QueryStringQuery.of(q -> q
                .query("age:5 AND name:a")
                .allowLeadingWildcard(true)
                .defaultField("age")
        )._toQuery();
        show(query);
    }


    @Test
    public void matchAll() throws IOException {
        Query query = QueryBuilders.matchAll().build()._toQuery();
        show(query);
    }

    @Test
    public void prefixQuery() throws IOException {
        Query query = QueryBuilders.prefix().field("name").value("a").build()._toQuery();
        show(query);
    }


    @Test
    public void get() throws IOException {
        GetResponse<Product> response = client.get(GetRequest.of(g -> g
                        .index(getIndexName())
                        .id("5")
                ),
                Product.class
        );
        Product source = response.source();
        System.out.println(source);
    }

    @Test
    public void mGet() throws IOException {
        MgetResponse<Object> mgetResponse = client.mget(MgetRequest.of(mg -> mg
                        .index(getIndexName())
                        .ids(Lists.newArrayList("4","5"))
                ),
                Object.class
        );
        List<MultiGetResponseItem<Object>> docs = mgetResponse.docs();
        for (MultiGetResponseItem<Object> doc : docs) {
            Object source = doc.result().source();
            System.out.println(source);
        }
    }

}
