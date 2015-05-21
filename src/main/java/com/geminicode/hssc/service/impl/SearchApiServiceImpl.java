package com.geminicode.hssc.service.impl;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.geminicode.hssc.service.DatastoreService;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.*;
import com.google.appengine.api.search.*;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

public class SearchApiServiceImpl implements SearchApiService {

    private static final Logger LOGGER = Logger.getLogger(SearchApiServiceImpl.class.getName());

    public static final String BASE_URL_IMAGE_FR = "http://wow.zamimg.com/images/hearthstone/cards/frfr/original/";
    public static final String BASE_URL_IMAGE_EN = "http://wow.zamimg.com/images/hearthstone/cards/enus/original/";

    public static final String PNG = ".png";
    public static final String CARDS = "cards";

    private static final Index index = getIndex(CARDS);

    private final DatastoreService datastoreService = ServiceFactory.get().getDatastoreService();

    @Override
    public List<Card> search(String queryString, String lang, String cost) throws SearchException {

        final List<Card> cards = Lists.newArrayList();

        queryString = SearchUtil.buildQueryString(queryString, lang, cost);

        final SortOptions sortOptions = SortOptions.newBuilder()
                .addSortExpression(
                        SortExpression.newBuilder()
                                .setExpression("cost")
                                .setDirection(SortExpression.SortDirection.ASCENDING))
                .addSortExpression(
                        SortExpression.newBuilder()
                                .setExpression("name")
                                .setDirection(SortExpression.SortDirection.ASCENDING))
                .build();
        final QueryOptions options = QueryOptions.newBuilder().setLimit(1000).setSortOptions(sortOptions).build();
        final Query query = SearchUtil.buildQuery(queryString, options);


        final Results<ScoredDocument> results = index.search(query);

        for (ScoredDocument document : results) {
            final Iterable<Field> fields = document.getFields();
            final Card card = SearchUtil.getCardFromField(fields);
            card.setId(document.getId());
            cards.add(card);
        }

        return cards;
    }

    @Override
    public Card searchById(String cardId) throws SearchException {
        final Document document = index.get(cardId);
        final Card card = SearchUtil.getCardFromField(document.getFields());
        card.setId(document.getId());

        return card;
    }

    @Override
    public void deleteAllCards() throws IOException {
        final Queue queue = QueueFactory.getDefaultQueue();
        try {

            while (true) {
                final List<String> docIds = Lists.newArrayList();
                final GetRequest request = GetRequest.newBuilder().setReturningIdsOnly(true).build();

                final GetResponse<Document> response = index.getRange(request);
                if (response.getResults().isEmpty()) {
                    LOGGER.info("No more indexed cards");
                    break;
                }
                for (Document doc : response) {
                    LOGGER.info("Doc with id : " + doc.getId() + " will be deleted.");
                    docIds.add(doc.getId());
                }
                index.delete(docIds);
            }
        } catch (RuntimeException e) {
            LOGGER.info("A new delete task was launch due to : " + e.getLocalizedMessage());
            queue.add(withUrl("/delete"));
        }
    }

    @Override
    public void checkNewCards(Locale locale) throws IOException {

        LOGGER.info("Loading cards for " + locale + " : START");
        final CardType cardType = CardReader.read(locale);

        persisteCards(cardType, TypesEnum.BASIC, locale);
        persisteCards(cardType, TypesEnum.CLASSIC, locale);
        persisteCards(cardType, TypesEnum.CURSE_OF_NAXXRAMAS, locale);
        persisteCards(cardType, TypesEnum.GOBLINS_VS_GNOMES, locale);
        persisteCards(cardType, TypesEnum.PROMOTION, locale);
        persisteCards(cardType, TypesEnum.BLACKROCK_MOUNTAIN, locale);
        datastoreService.putOtherString(locale);

        LOGGER.info("Loading cards " + locale + " : END");

    }

    private void persisteCards(CardType cardType, TypesEnum type, Locale locale) {

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
        putFullCardsIntoSearch(wantedCards, locale);
        datastoreService.putCards(wantedCards, locale);
    }

    private List<Card> removeUnWantedCards(List<Card> cards) {

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

    private void addSpecifiedFields(List<Card> cards, TypesEnum type, Locale locale) {
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

    private void putFullCardsIntoSearch(List<Card> cards, Locale locale) {
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

            index.put(doc);

        }
    }

    private Integer getCost(Card card) {
        if(Strings.isNullOrEmpty(card.getCost())) {
            return 0;
        }
        return Integer.valueOf(card.getCost());
    }

    /**
     * This code build all String[] mechanic values to String
     */
    private String buildMechanicsValues(String[] mechanics, Locale locale) {
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

    private static Index getIndex(String indexName) {
        final IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        return SearchServiceFactory.getSearchService().getIndex(indexSpec);
    }

}
