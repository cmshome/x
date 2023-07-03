package com.lxk.es.v8p2.model;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.TermsAggregation;
import com.google.common.collect.Maps;
import com.lxk.es.v8p2.util.QueryUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author LiXuekai on 2023/7/3
 */
@Data
@NoArgsConstructor
public class ZxTerms {
    private String name;
    private TermsAggregation.Builder termsAggregationBuilder;
    private Map<String, Aggregation> subAgg;


    public ZxTerms(String name, TermsAggregation.Builder termsAggregationBuilder, Map<String, Aggregation> subAgg) {
        this.name = name;
        this.termsAggregationBuilder = termsAggregationBuilder;
        this.subAgg = subAgg;
    }

    public ZxTerms subAggregation(Map<String, Aggregation> subAgg) {
        if (this.subAgg == null) {
            this.subAgg = Maps.newHashMap();
        }
        this.subAgg.putAll(subAgg);
        return this;
    }

    public Map<String, Aggregation> aggregations() {
        Aggregation terms = QueryUtil.termsSubAgg(termsAggregationBuilder.build(), subAgg);
        return QueryUtil.aggregationMap(name, terms);
    }
}
