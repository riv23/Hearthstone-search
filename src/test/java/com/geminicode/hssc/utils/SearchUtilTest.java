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

    @Test
    public void should_build_return_default_query() {
        //GIVEN
        final String queryString = null;

        //WHEN
        final String query = SearchUtil.buildQueryString(queryString, "", "");

        //THEN
        assertThat(query).isNotNull();
        assertThat(query).isEqualTo(" lang=en");
    }

    @Test
    public void should_build_query_with_param_lang() {
        //GIVEN
        final String queryString = null;
        final String fr = "fr";

        //WHEN
        final String query = SearchUtil.buildQueryString(queryString, fr, "");

        //THEN
        assertThat(query).isNotNull();
        assertThat(query).isEqualTo(" lang=fr");
    }

    @Test
    public void should_build_query_with_lang_and_query_param() {
        //GIVEN
        final String queryString = "Leeroy Jenkins";
        final String fr = "fr";

        //WHEN
        final String query = SearchUtil.buildQueryString(queryString, fr, "");

        //THEN
        assertThat(query).isNotNull();
        assertThat(query).isEqualTo("Leeroy Jenkins lang=fr");
    }

    @Test
    public void should_build_query_with_all_param() {
        //GIVEN
        final String queryString = "Leeroy Jenkins";
        final String fr = "fr";
        final String cost = "7";

        //WHEN
        final String query = SearchUtil.buildQueryString(queryString, fr, cost);

        //THEN
        assertThat(query).isNotNull();
        assertThat(query).isEqualTo("Leeroy Jenkins lang=fr cost>=7");
    }
}