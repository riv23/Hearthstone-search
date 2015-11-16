package com.geminicode.hssc.utils;

import com.geminicode.hssc.model.Card;
import com.google.appengine.api.search.*;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class SearchUtil {

    private static final Logger LOGGER = Logger.getLogger(SearchUtil.class.getName());

    private static final String BASE_URL_IMAGE_FR = "http://wow.zamimg.com/images/hearthstone/cards/frfr/original/";
    private static final String BASE_URL_IMAGE_EN = "http://wow.zamimg.com/images/hearthstone/cards/enus/original/";

    private static final String CARDS = "cards";
    private static final String PNG = ".png";

    public static Query buildQuery(String queryString, QueryOptions options) {
        return Query.newBuilder()
                .setOptions(options)
                .build(queryString);
    }

    public static Card getCardFromField(Iterable<Field> fields) {

        final Card card = new Card();

        for (Field field : fields) {
            if (FieldString.NAME_FIELD.equals(field.getName())) {
                card.setName(field.getText());
            }
            if (FieldString.IMAGE_FIELD.equals(field.getName())) {
                card.setImage(field.getAtom());
            }
            if (FieldString.TYPE_FIELD.equals(field.getName())) {
                card.setType(field.getText());
            }
            if (FieldString.TEXT_FIELD.equals(field.getName())) {
                card.setText(field.getAtom());
            }
            if (FieldString.FLAVOR_FIELD.equals(field.getName())) {
                card.setFlavor(field.getAtom());
            }
            if (FieldString.ARTIST_FIELD.equals(field.getName())) {
                card.setArtist(field.getAtom());
            }
            if (FieldString.PLAYER_CLASS_FIELD.equals(field.getName())) {
                card.setPlayerClass(field.getText());
            }
            if (FieldString.FACTION_FIELD.equals(field.getName())) {
                card.setFaction(field.getText());
            }
            if (FieldString.RARITY_FIELD.equals(field.getName())) {
                card.setRarity(field.getText());
            }
            if (FieldString.COST_FIELD.equals(field.getName())) {
                card.setCost(field.getNumber().toString());
            }
            if (FieldString.ATTACK_FIELD.equals(field.getName())) {
                card.setAttack(field.getText());
            }
            if (FieldString.HEALTH_FIELD.equals(field.getName())) {
                card.setHealth(field.getText());
            }
            if (FieldString.COLLECTIBLE_FIELD.equals(field.getName())) {
                card.setCollectible(field.getText());
            }
            if (FieldString.RACE_FIELD.equals(field.getName())) {
                card.setRace(field.getText());
            }
            if (FieldString.EXPANSION_FIELD.equals(field.getName())) {
                card.setExpansionPack(field.getText());
            }
            if (FieldString.MECHANICS_FIELD.equals(field.getName())) {
                card.setMechanics(field.getText().split("\\|"));
            }
            if (FieldString.LANG_FIELD.equals(field.getName())) {
                card.setLanguage(field.getAtom());
            }
            if(FieldString.VERSION.equals(field.getName())) {
                card.setVersion(field.getAtom());
            }

        }

        return card;
    }

    public static String buildQueryString(String queryString, String lang, String cost) {
        if (!Strings.isNullOrEmpty(queryString)) {
            queryString = queryString.replaceAll("[^A-Za-z0-9äöüÄÖÜßéèáàúùóò=']", " ");
        } else {
            queryString = "";
        }
        if (Strings.isNullOrEmpty(lang) || !"fr".equals(lang)) {
            queryString += " lang=en";
        } else {
            queryString += " lang=" + lang;
        }

        if (!Strings.isNullOrEmpty(cost)) {
            if (Integer.valueOf(cost) >= 7) {
                queryString += " cost>=" + cost;
            } else {
                queryString += " cost=" + cost;
            }
        }

        return queryString;
    }

    public static SortOptions buildSortOptions() {
        return SortOptions.newBuilder()
                .addSortExpression(
                        SortExpression.newBuilder()
                                .setExpression(FieldString.COST_FIELD)
                                .setDirection(SortExpression.SortDirection.ASCENDING))
                .addSortExpression(
                        SortExpression.newBuilder()
                                .setExpression(FieldString.NAME_FIELD)
                                .setDirection(SortExpression.SortDirection.ASCENDING))
                .build();
    }



    protected static List<Card> removeUnWantedCards(List<Card> cards) {

        final List<Card> wantedCards = Lists.newArrayList();

        wantedCards.addAll(Collections2.filter(cards, new Predicate<Card>() {
            @Override
            public boolean apply(Card input) {
                return !Strings.isNullOrEmpty(input.getCollectible())
                        && "true".equals(input.getCollectible())
                        && !"Hero".equals(input.getType());
            }
        }));

        return wantedCards;
    }

    private static void addSpecifiedFields(List<Card> cards, String expansion, String version, Locale locale) {
        String baseUrl = BASE_URL_IMAGE_FR;
        if (Locale.FRENCH.equals(locale)) {
            baseUrl = BASE_URL_IMAGE_FR;
        }
        if (Locale.US.equals(locale)) {
            baseUrl = BASE_URL_IMAGE_EN;
        }
        for (Card card : cards) {
            card.setImage(baseUrl + card.getId() + PNG);
            card.setExpansionPack(expansion);
            card.setVersion(version);
        }
    }

    public static List<Document> buildDocumentsForIndex(List<Card> cards, String version, Locale locale) throws UnsupportedEncodingException {
        final List<Document> documents = Lists.newArrayList();
        for (Card card : cards) {
            final String docId = card.getId() + "_" + TranslateUtil.buildLanguageField(locale);
            final Document doc =
                    Document.newBuilder()
                            .setLocale(locale)
                            .setId(docId)
                            .addField(Field.newBuilder().setName(FieldString.NAME_FIELD)
                                    .setText(card.getName()))
                            .addField(Field.newBuilder().setName(FieldString.TEXT_FIELD)
                                    .setAtom(card.getText()))
                            .addField(Field.newBuilder().setName(FieldString.FLAVOR_FIELD)
                                    .setAtom(card.getFlavor()))
                            .addField(Field.newBuilder().setName(FieldString.ARTIST_FIELD)
                                    .setAtom(card.getArtist()))
                            .addField(Field.newBuilder()
                                    .setName(FieldString.TYPE_FIELD)
                                    .setText(TranslateUtil.translate(card.getType(), locale)))
                            .addField(Field.newBuilder().setName(FieldString.IMAGE_FIELD)
                                    .setAtom(card.getImage()))
                            .addField(Field.newBuilder()
                                    .setName(FieldString.PLAYER_CLASS_FIELD)
                                    .setText(TranslateUtil.translatePlayerClass(
                                            card.getPlayerClass(), locale)))
                            .addField(Field.newBuilder().setName(FieldString.FACTION_FIELD)
                                    .setText(card.getFaction()))
                            .addField(Field.newBuilder()
                                    .setName(FieldString.RARITY_FIELD)
                                    .setText(TranslateUtil.translate(card.getRarity(), locale)))
                            .addField(Field.newBuilder().setName(FieldString.COST_FIELD)
                                    .setNumber(getCost(card)))
                            .addField(Field.newBuilder().setName(FieldString.ATTACK_FIELD)
                                    .setText(card.getAttack()))
                            .addField(Field.newBuilder().setName(FieldString.HEALTH_FIELD)
                                    .setText(card.getHealth()))
                            .addField(Field.newBuilder().setName(FieldString.COLLECTIBLE_FIELD)
                                    .setText(card.getCollectible()))
                            .addField(Field.newBuilder().setName(FieldString.RACE_FIELD)
                                    .setText(TranslateUtil.translate(card.getRace(), locale)))
                            .addField(Field.newBuilder().setName(FieldString.EXPANSION_FIELD)
                                    .setText(card.getExpansionPack()))
                            .addField(Field.newBuilder().setName(FieldString.MECHANICS_FIELD)
                                    .setText(buildMechanicsValues(card.getMechanics(), locale)))
                            .addField(Field.newBuilder().setName(FieldString.LANG_FIELD)
                                    .setAtom(TranslateUtil.buildLanguageField(locale)))
                            .addField(Field.newBuilder().setName(FieldString.VERSION)
                                    .setAtom(version))
                            .build();

            documents.add(doc);

        }
        return documents;
    }


    private static Integer getCost(Card card) {
        if (Strings.isNullOrEmpty(card.getCost())) {
            return 0;
        }
        return Integer.valueOf(card.getCost());
    }

    /**
     * This code build all String[] mechanic values to String
     */
    private static String buildMechanicsValues(String[] mechanics, Locale locale) throws UnsupportedEncodingException {
        final StringBuilder stringBuilder = new StringBuilder();

        if (mechanics == null || mechanics.length == 0) {
            return "";
        }

        for (int i = 0; i < mechanics.length; i++) {
            final String mechanic = mechanics[i];
            stringBuilder.append(TranslateUtil.translate(mechanic, locale));
            if (i != mechanics.length - 1) {
                stringBuilder.append("|");
            }
        }

        return stringBuilder.toString();
    }

    public static Index getIndex() {
        final IndexSpec indexSpec = IndexSpec.newBuilder().setName(CARDS).build();
        return SearchServiceFactory.getSearchService().getIndex(indexSpec);
    }

    public static List<Card> buildToPersistCards(Map<String, List<Card>> mapCards, String version, Locale locale) {

        final List<Card> wantedCards = Lists.newArrayList();
        mapCards.remove("Credits");
        mapCards.remove("Debug");
        mapCards.remove("Hero Skins");
        mapCards.remove("Missions");
        mapCards.remove("Reward");
        mapCards.remove("System");
        mapCards.remove("Tavern Brawl");
        for (Map.Entry<String, List<Card>> entry : mapCards.entrySet()){
            final List<Card> wantedBasics = removeUnWantedCards(entry.getValue());
            addSpecifiedFields(wantedBasics, entry.getKey(), version, locale);
            wantedCards.addAll(wantedBasics);
        }

        return wantedCards;
    }
}
