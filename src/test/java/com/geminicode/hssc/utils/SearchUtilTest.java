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
        assertThat(query.getQueryString()).isNotNull();
        assertThat(query.getQueryString()).isEqualTo(queryString);
    }
}