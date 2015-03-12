package com.geminicode.hssc.utils;

import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;

public class SearchUtilTest {

    @Test
    public void should_build_query_from_string() {
        //GIVEN
        final String queryString = "test";

        //WHEN
        final Query query = SearchUtil.buildQuery(queryString, any(QueryOptions.class));

        //THEN
        final String oracle = "id:" + queryString + " OR name:" + queryString + " OR attack:" + queryString + " OR health:" + queryString + " OR cost:" + queryString + " OR race:" + queryString + " OR rarity:" + queryString + " OR type:" + queryString;
        assertThat(query.getQueryString()).isNotNull();
        assertThat(query.getQueryString()).isEqualTo(oracle);
    }
}