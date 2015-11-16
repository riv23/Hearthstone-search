package com.geminicode.hssc.utils;

import com.geminicode.hssc.model.Card;
import com.google.appengine.api.search.Field;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SearchUtilTest {

    @Mock
    private SearchUtil searchUtil;

    @Test
    public void should_build_return_default_query() {
        //GIVEN
        final String queryString = null;

        //WHEN
        final String query = SearchUtil.buildQueryString(queryString, "", "", "");

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
        final String query = SearchUtil.buildQueryString(queryString, fr, "", "");

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
        final String query = SearchUtil.buildQueryString(queryString, fr, "", "");

        //THEN
        assertThat(query).isNotNull();
        assertThat(query).isEqualTo("Leeroy Jenkins lang=fr");
    }

    @Test
    public void should_build_query_with_all_param_cost_less_than_7() {
        //GIVEN
        final String queryString = "Leeroy Jenkins";
        final String fr = "fr";
        final String cost = "3";

        //WHEN
        final String query = SearchUtil.buildQueryString(queryString, fr, cost, "");

        //THEN
        assertThat(query).isNotNull();
        assertThat(query).isEqualTo("Leeroy Jenkins lang=fr cost=3");
    }

    @Test
    public void should_build_query_with_all_param() {
        //GIVEN
        final String queryString = "Leeroy Jenkins";
        final String fr = "fr";
        final String cost = "7";

        //WHEN
        final String query = SearchUtil.buildQueryString(queryString, fr, cost, "");

        //THEN
        assertThat(query).isNotNull();
        assertThat(query).isEqualTo("Leeroy Jenkins lang=fr cost>=7");
    }

    @Test
    public void should_build_card_from_fields() {
        //GIVEN
        final Iterable<Field> fields = TestUtils.buildFieldsJenkins();

        //WHEN
        final Card card = SearchUtil.getCardFromField(fields);

        //THEN
        assertThat(card).isNotNull();
        assertThat(card.getId()).isEmpty();
        assertThat(card.getName()).isNotNull().isNotEmpty().isEqualTo("Leeroy Jenkins");
        assertThat(card.getImage()).isNotNull().isNotEmpty().isEqualTo("http://wow.zamimg.com/images/hearthstone/cards/frfr/original/EX1_116.png");
        assertThat(card.getType()).isNotNull().isNotEmpty().isEqualTo("Minion");
        assertThat(card.getText()).isNotNull().isNotEmpty().isEqualTo("Powerful card");
        assertThat(card.getPlayerClass()).isNotNull().isNotEmpty().isEqualTo("All");
        assertThat(card.getFaction()).isNotNull().isNotEmpty().isEqualTo("All");
        assertThat(card.getRarity()).isNotNull().isNotEmpty().isEqualTo("Legendary");
        assertThat(card.getCost()).isNotNull().isNotEmpty().isEqualTo("7.0");
        assertThat(card.getAttack()).isNotNull().isNotEmpty().isEqualTo("6");
        assertThat(card.getHealth()).isNotNull().isNotEmpty().isEqualTo("2");
        assertThat(card.getFlavor()).isNotNull().isNotEmpty().isEqualTo("Flavor");
        assertThat(card.getArtist()).isNotNull().isNotEmpty().isEqualTo("GeminiCOde");
        assertThat(card.getCollectible()).isNotNull().isNotEmpty().isEqualTo("true");
        assertThat(card.getRace()).isNotNull().isNotEmpty().isEqualTo("Human");
        final String[] mechanics = {"Charge", "Taunt"};
        assertThat(card.getMechanics()).isNotEmpty().isEqualTo(mechanics);
        assertThat(card.getLanguage()).isNotNull().isNotEmpty().isEqualTo("en");
        assertThat(card.getExpansionPack()).isNotNull().isNotEmpty().isEqualTo("Basic");

    }
}

