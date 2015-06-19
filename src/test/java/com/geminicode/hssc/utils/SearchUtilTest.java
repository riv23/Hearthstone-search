package com.geminicode.hssc.utils;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.SortOptions;
import com.google.appengine.repackaged.com.google.api.client.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Locale;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchUtilTest {

    @Mock
    private SearchUtil searchUtil;

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
    public void should_build_query_with_all_param_cost_less_than_7() {
        //GIVEN
        final String queryString = "Leeroy Jenkins";
        final String fr = "fr";
        final String cost = "3";

        //WHEN
        final String query = SearchUtil.buildQueryString(queryString, fr, cost);

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
        final String query = SearchUtil.buildQueryString(queryString, fr, cost);

        //THEN
        assertThat(query).isNotNull();
        assertThat(query).isEqualTo("Leeroy Jenkins lang=fr cost>=7");
    }

    @Test
    public void should_build_card_from_fields() {
        //GIVEN
        final Iterable<Field> fields = TestUtils.buildFieldsOdJenkins();

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

    @Test
    public void should_buildToPersistCards_basics() {
        //GIVEN

        final CardType cardType = new CardType();
        cardType.setBasic(Lists.<Card>newArrayList());

        //WHEN
        final List<Card> cards = SearchUtil.buildToPersistCards(cardType, TypesEnum.BASIC, Locale.FRENCH);

        //THEN
        assertThat(cards).isNotNull().isEmpty();

    }

    @Test
    public void should_buildToPersistCards_classics() {
        //GIVEN

        final CardType cardType = new CardType();
        cardType.setClassic(Lists.<Card>newArrayList());

        //WHEN
        final List<Card> cards = SearchUtil.buildToPersistCards(cardType, TypesEnum.CLASSIC, Locale.FRENCH);

        //THEN
        assertThat(cards).isNotNull().isEmpty();

    }

    @Test
    public void should_buildToPersistCards_GvG() {
        //GIVEN

        final CardType cardType = new CardType();
        cardType.setGobelinsVsGnomes(Lists.<Card>newArrayList());

        //WHEN
        final List<Card> cards = SearchUtil.buildToPersistCards(cardType, TypesEnum.GOBLINS_VS_GNOMES, Locale.FRENCH);

        //THEN
        assertThat(cards).isNotNull().isEmpty();

    }

    @Test
    public void should_buildToPersistCards_Naxx() {
        //GIVEN

        final CardType cardType = new CardType();
        cardType.setCurseOfNaxxramas(Lists.<Card>newArrayList());

        //WHEN
        final List<Card> cards = SearchUtil.buildToPersistCards(cardType, TypesEnum.CURSE_OF_NAXXRAMAS, Locale.FRENCH);

        //THEN
        assertThat(cards).isNotNull().isEmpty();

    }

    @Test
    public void should_buildToPersistCards_Promotion() {
        //GIVEN

        final CardType cardType = new CardType();
        cardType.setPromotions(Lists.<Card>newArrayList());

        //WHEN
        final List<Card> cards = SearchUtil.buildToPersistCards(cardType, TypesEnum.PROMOTION, Locale.FRENCH);

        //THEN
        assertThat(cards).isNotNull().isEmpty();

    }

    @Test
    public void should_buildToPersistCards_Blackrock() {
        //GIVEN

        final CardType cardType = new CardType();
        cardType.setBlackrockMountain(Lists.<Card>newArrayList());

        //WHEN
        final List<Card> cards = SearchUtil.buildToPersistCards(cardType, TypesEnum.BLACKROCK_MOUNTAIN, Locale.FRENCH);

        //THEN
        assertThat(cards).isNotNull().isEmpty();

    }


}

