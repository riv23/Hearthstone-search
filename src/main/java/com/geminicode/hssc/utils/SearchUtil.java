package com.geminicode.hssc.utils;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.google.appengine.api.search.*;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Locale;
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

    public static  Card getCardFromField(Iterable<Field> fields) {

        final Card card = new Card();

        for (Field field : fields) {
            if (HSSCStrings.NAME_FIELD.equals(field.getName())) {
                card.setName(field.getText());
            }
            if (HSSCStrings.IMAGE_FIELD.equals(field.getName())) {
                card.setImage(field.getAtom());
            }
            if (HSSCStrings.TYPE_FIELD.equals(field.getName())) {
                card.setType(field.getText());
            }
            if (HSSCStrings.TEXT_FIELD.equals(field.getName())) {
                card.setText(field.getAtom());
            }
            if (HSSCStrings.FLAVOR_FIELD.equals(field.getName())) {
                card.setFlavor(field.getAtom());
            }
            if (HSSCStrings.ARTIST_FIELD.equals(field.getName())) {
                card.setArtist(field.getAtom());
            }
            if (HSSCStrings.PLAYER_CLASS_FIELD.equals(field.getName())) {
                card.setPlayerClass(field.getText());
            }
            if (HSSCStrings.FACTION_FIELD.equals(field.getName())) {
                card.setFaction(field.getText());
            }
            if (HSSCStrings.RARITY_FIELD.equals(field.getName())) {
                card.setRarity(field.getText());
            }
            if (HSSCStrings.COST_FIELD.equals(field.getName())) {
                card.setCost(field.getNumber().toString());
            }
            if (HSSCStrings.ATTACK_FIELD.equals(field.getName())) {
                card.setAttack(field.getText());
            }
            if (HSSCStrings.HEALTH_FIELD.equals(field.getName())) {
                card.setHealth(field.getText());
            }
            if (HSSCStrings.COLLECTIBLE_FIELD.equals(field.getName())) {
                card.setCollectible(field.getText());
            }
            if (HSSCStrings.RACE_FIELD.equals(field.getName())) {
                card.setRace(field.getText());
            }
            if (HSSCStrings.EXPANSION_FIELD.equals(field.getName())) {
                card.setExpansionPack(field.getText());
            }
            if (HSSCStrings.MECHANICS_FIELD.equals(field.getName())) {
                card.setMechanics(field.getText().split("\\|"));
            }
            if(HSSCStrings.LANG_FIELD.equals(field.getName())) {
                card.setLanguage(field.getAtom());
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
                                .setExpression(HSSCStrings.COST_FIELD)
                                .setDirection(SortExpression.SortDirection.ASCENDING))
                .addSortExpression(
                        SortExpression.newBuilder()
                                .setExpression(HSSCStrings.NAME_FIELD)
                                .setDirection(SortExpression.SortDirection.ASCENDING))
                .build();
    }

    public static List<Card> buildToPersistCards(CardType cardType, TypesEnum type, Locale locale) {

        final List<Card> wantedCards = Lists.newArrayList();

        switch (type) {
            case BASIC:
                final List<Card> basics = cardType.getBasic();
                final List<Card> wantedBasics = removeUnWantedCards(basics);
                addSpecifiedFields(wantedBasics, type, locale);
                wantedCards.addAll(wantedBasics);
                LOGGER.info("There are " + wantedBasics.size() + " " + TypesEnum.BASIC.getName() + " cards.");
                break;
            case CLASSIC:
                final List<Card> classics = cardType.getClassic();
                final List<Card> wantedClassics = removeUnWantedCards(classics);
                addSpecifiedFields(wantedClassics, type, locale);
                wantedCards.addAll(wantedClassics);
                LOGGER.info("There are " + wantedClassics.size() + " " + TypesEnum.CLASSIC.getName() + " cards.");
                break;
            case CURSE_OF_NAXXRAMAS:
                final List<Card> curseOfNaxxramass = cardType.getCurseOfNaxxramas();
                final List<Card> wantedCurseOfNaxxramass = removeUnWantedCards(curseOfNaxxramass);
                addSpecifiedFields(wantedCurseOfNaxxramass, type, locale);
                wantedCards.addAll(wantedCurseOfNaxxramass);
                LOGGER.info("There are " + wantedCurseOfNaxxramass.size() + " " + TypesEnum.CURSE_OF_NAXXRAMAS.getName()
                        + " cards.");
                break;
            case GOBLINS_VS_GNOMES:
                final List<Card> gobelinsVsGnomes = cardType.getGobelinsVsGnomes();
                final List<Card> wantedGobelinsVsGnomes = removeUnWantedCards(gobelinsVsGnomes);
                addSpecifiedFields(wantedGobelinsVsGnomes, type, locale);
                wantedCards.addAll(wantedGobelinsVsGnomes);
                LOGGER.info("There are " + wantedGobelinsVsGnomes.size() + " " + TypesEnum.GOBLINS_VS_GNOMES.getName()
                        + " cards.");
                break;
            case PROMOTION:
                final List<Card> promotions = cardType.getPromotions();
                final List<Card> wantedPromotions = removeUnWantedCards(promotions);
                addSpecifiedFields(wantedPromotions, type, locale);
                wantedCards.addAll(wantedPromotions);
                LOGGER.info("There are " + wantedPromotions.size() + " " + TypesEnum.PROMOTION.getName() + " cards.");
                break;
            case BLACKROCK_MOUNTAIN:
                final List<Card> blackrockMountain = cardType.getBlackrockMountain();
                final List<Card> wantedBlackrockMountain = removeUnWantedCards(blackrockMountain);
                addSpecifiedFields(wantedBlackrockMountain, type, locale);
                wantedCards.addAll(wantedBlackrockMountain);
                LOGGER.info("There are " + wantedBlackrockMountain.size() + " " + TypesEnum.BLACKROCK_MOUNTAIN.getName() + " cards.");
                break;
            default:
                break;
        }

        return wantedCards;
    }

    private static List<Card> removeUnWantedCards(List<Card> cards) {

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

    private static void addSpecifiedFields(List<Card> cards, TypesEnum type, Locale locale) {
        String baseUrl = BASE_URL_IMAGE_FR;
        if (Locale.FRENCH.equals(locale)) {
            baseUrl = BASE_URL_IMAGE_FR;
        }
        if(Locale.ENGLISH.equals(locale)) {
            baseUrl = BASE_URL_IMAGE_EN;
        }
        for (Card card : cards) {
            card.setImage(baseUrl + card.getId() + PNG);
            card.setExpansionPack(type.getName());
        }
    }

    public static List<Document> buildDocumentsForIndex(List<Card> cards, Locale locale) {
        final List<Document> documents = Lists.newArrayList();
        for (Card card : cards) {
            final String docId = card.getId() + "_" + TranslateUtil.buildLanguageField(locale);
            final Document doc =
                    Document.newBuilder()
                            .setLocale(locale)
                            .setId(docId)
                            .addField(Field.newBuilder().setName(HSSCStrings.NAME_FIELD)
                                    .setText(card.getName()))
                            .addField(Field.newBuilder().setName(HSSCStrings.TEXT_FIELD)
                                    .setAtom(card.getText()))
                            .addField(Field.newBuilder().setName(HSSCStrings.FLAVOR_FIELD)
                                    .setAtom(card.getFlavor()))
                            .addField(Field.newBuilder().setName(HSSCStrings.ARTIST_FIELD)
                                    .setAtom(card.getArtist()))
                            .addField(Field.newBuilder()
                                    .setName(HSSCStrings.TYPE_FIELD)
                                    .setText(TranslateUtil.translateType(
                                            card.getType(), locale)))
                            .addField(Field.newBuilder().setName(HSSCStrings.IMAGE_FIELD)
                                    .setAtom(card.getImage()))
                            .addField(Field.newBuilder()
                                    .setName(HSSCStrings.PLAYER_CLASS_FIELD)
                                    .setText(TranslateUtil.translatePlayerClass(
                                            card.getPlayerClass(), locale)))
                            .addField(Field.newBuilder().setName(HSSCStrings.FACTION_FIELD)
                                    .setText(card.getFaction()))
                            .addField(Field.newBuilder()
                                    .setName(HSSCStrings.RARITY_FIELD)
                                    .setText(TranslateUtil.translateRarity(
                                            card.getRarity(), locale)))
                            .addField(Field.newBuilder().setName(HSSCStrings.COST_FIELD)
                                    .setNumber(getCost(card)))
                            .addField(Field.newBuilder().setName(HSSCStrings.ATTACK_FIELD)
                                    .setText(card.getAttack()))
                            .addField(Field.newBuilder().setName(HSSCStrings.HEALTH_FIELD)
                                    .setText(card.getHealth()))
                            .addField(Field.newBuilder().setName(HSSCStrings.COLLECTIBLE_FIELD)
                                    .setText(card.getCollectible()))
                            .addField(Field.newBuilder().setName(HSSCStrings.RACE_FIELD)
                                    .setText(TranslateUtil.translateRace(card.getRace(), locale)))
                            .addField(Field.newBuilder().setName(HSSCStrings.EXPANSION_FIELD)
                                    .setText(card.getExpansionPack()))
                            .addField(Field.newBuilder().setName(HSSCStrings.MECHANICS_FIELD)
                                    .setText(buildMechanicsValues(card.getMechanics(), locale)))
                            .addField(Field.newBuilder().setName(HSSCStrings.LANG_FIELD)
                                    .setAtom(TranslateUtil.buildLanguageField(locale))).build();

            documents.add(doc);

        }
        return documents;
    }



    private static Integer getCost(Card card) {
        if(Strings.isNullOrEmpty(card.getCost())) {
            return 0;
        }
        return Integer.valueOf(card.getCost());
    }

    /**
     * This code build all String[] mechanic values to String
     */
    private static String buildMechanicsValues(String[] mechanics, Locale locale) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (mechanics == null || mechanics.length == 0) {
            return "";
        }

        for (int i = 0; i < mechanics.length; i++) {
            final String mechanic = mechanics[i];
            stringBuilder.append(TranslateUtil.translateMechanic(mechanic, locale));
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
}
