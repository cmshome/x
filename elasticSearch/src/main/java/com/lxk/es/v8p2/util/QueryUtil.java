package com.lxk.es.v8p2.util;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.HistogramAggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.search.TrackHits;
import co.elastic.clients.json.JsonData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author LiXuekai on 2023/6/2
 */
public class QueryUtil {

    public static Query termQuery(String field, Object value) {
        FieldValue fieldValue;
        if (value instanceof Long) {
            fieldValue = FieldValue.of((Long) value);
        } else if (value instanceof Double) {
            fieldValue = FieldValue.of((Double) value);
        } else if (value instanceof Boolean) {
            fieldValue = FieldValue.of((Boolean) value);
        } else {
            fieldValue = FieldValue.of((String) value);
        }
        return QueryBuilders.term().field(field).value(fieldValue).build()._toQuery();
    }

    public static Query termsQuery(String field, List<String> value) {
        List<FieldValue> list = Lists.newArrayList();
        for (String s : value) {
            list.add(new FieldValue.Builder().stringValue(s).build());
        }
        return QueryBuilders.terms().field(field).terms(tf -> tf.value(list)).build()._toQuery();
    }

    /**
     * wildcardQuery 通配符查询
     */
    public static Query wildcardQuery(String field, String value) {
        return QueryBuilders.wildcard().field(field).wildcard(value).build()._toQuery();
    }

    public static Query existsQuery(String field) {
        return QueryBuilders.exists().field(field).build()._toQuery();
    }

    public static Query rangeQuery(String field, Long from, Long to) {
        return QueryBuilders.range().field(field).gte(JsonData.of(from)).lt(JsonData.of(to)).build()._toQuery();
    }

    public static Query idsQuery(List<String> ids) {
        return QueryBuilders.ids().values(ids).build()._toQuery();
    }

    public static Query prefixQuery(String field, String value) {
        return QueryBuilders.prefix().field(field).value(value).build()._toQuery();
    }

    public static Query queryStringQuery(String queryString) {
        return queryStringQuery(queryString, null);
    }

    public static Query queryStringQuery(String queryString, String defaultQueryField) {
        return QueryBuilders.queryString().query(queryString).allowLeadingWildcard(true).defaultField(defaultQueryField).build()._toQuery();
    }

    public static SortOptions sortOptions(String field, SortOrder sortOrder) {
        return SortOptionsBuilders.field(f -> f.field(field).order(sortOrder));
    }

    public static TrackHits trackHits(boolean enable) {
        return TrackHits.of(t -> t.enabled(enable));
    }

    public static Map<String, Aggregation> aggregationMap(String name, Aggregation aggregation) {
        Map<String, Aggregation> map = Maps.newHashMap();
        map.put(name, aggregation);
        return map;
    }

    public static Aggregation filterSubAgg(Query query, Map<String, Aggregation> subAggregation) {
        Aggregation aggregation;
        if (subAggregation == null || subAggregation.isEmpty()) {
            aggregation = new Aggregation.Builder()
                    .filter(query)
                    .build();
        } else {
            aggregation = new Aggregation.Builder()
                    .filter(query)
                    .aggregations(subAggregation)
                    .build();
        }
        return aggregation;
    }

    public static Aggregation hisSubAgg(HistogramAggregation.Builder histogramBuilder, Map<String, Aggregation> subAggregation) {
        Aggregation aggregation;
        if (subAggregation == null || subAggregation.isEmpty()) {
            aggregation = new Aggregation.Builder()
                    .histogram(histogramBuilder.build())
                    .build();
        } else {
            aggregation = new Aggregation.Builder()
                    .histogram(histogramBuilder.build())
                    .aggregations(subAggregation)
                    .build();
        }
        return aggregation;
    }

}
