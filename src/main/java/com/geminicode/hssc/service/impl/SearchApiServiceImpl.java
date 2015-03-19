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

    public static final String BASE_URL_IMAGE = "http://wow.zamimg.com/images/hearthstone/cards/frfr/original/";

    public static final String PNG = ".png";
    public static final String CARDS = "cards";

    private static final Index index = getIndex(CARDS);

    private final DatastoreService datastoreService = ServiceFactory.get().getDatastoreService();

    @Override
    public List<Card> search(String queryString) throws SearchException {

        final List<Card> cards = Lists.newArrayList();

        if (Strings.isNullOrEmpty(queryString)) {
            return cards;
        }

        final QueryOptions options = QueryOptions.newBuilder().setLimit(1000).build();
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
    public Card searchById(String id) throws SearchException {
        final Document document = index.get(id);
        final Card card = SearchUtil.getCardFromField(document.getFields());
        card.setId(document.getId());

        return card;
    }

    @Override
    public void checkNewCards() throws IOException {
        final Queue queue = QueueFactory.getDefaultQueue();
        try {

            while (true) {
                final List<String> docIds = Lists.newArrayList();
                final GetRequest request = GetRequest.newBuilder().setReturningIdsOnly(true).build();

                final GetResponse<Document> response = index.getRange(request);
                if (response.getResults().isEmpty()) {
                    LOGGER.info("Loading cards : START");
                    final CardType cardType = CardReader.read();

                    datastoreService.removeAllCards();
                    persisteCards(cardType, TypesEnum.BASIC);
                    persisteCards(cardType, TypesEnum.CLASSIC);
                    persisteCards(cardType, TypesEnum.CURSE_OF_NAXXRAMAS);
                    persisteCards(cardType, TypesEnum.GOBLINS_VS_GNOMES);
                    persisteCards(cardType, TypesEnum.PROMOTION);

                    LOGGER.info("Loading cards : DONE");
                    break;
                }
                for (Document doc : response) {
                    LOGGER.info("Doc with id :" + doc.getId() + " will be deleted.");
                    docIds.add(doc.getId());
                }
                index.delete(docIds);
            }
        } catch (RuntimeException e) {
            LOGGER.info("A new delete task was launch due to :" + e.getLocalizedMessage());
            queue.add(withUrl("/check"));
        }
    }

    private void persisteCards(CardType cardType, TypesEnum type) {

        switch (type) {
            case BASIC:
                final List<Card> basics = cardType.getBasic();
                final List<Card> wantedBasics = removeUnWantedCards(basics);
                LOGGER.info("There are " + wantedBasics.size() + " " + TypesEnum.BASIC.getName() + " cards.");
                buildUrl(wantedBasics);
                putFullCardsIntoSearch(wantedBasics);
                datastoreService.putCards(wantedBasics);
                break;
            case CLASSIC:
                final List<Card> classics = cardType.getClassic();
                final List<Card> wantedClassics = removeUnWantedCards(classics);
                LOGGER.info("There are " + wantedClassics.size() + " " + TypesEnum.CLASSIC.getName() + " cards.");
                buildUrl(wantedClassics);
                putFullCardsIntoSearch(wantedClassics);
                datastoreService.putCards(wantedClassics);
                break;
            case CURSE_OF_NAXXRAMAS:
                final List<Card> curseOfNaxxramass = cardType.getCurseOfNaxxramas();
                final List<Card> wantedCurseOfNaxxramass = removeUnWantedCards(curseOfNaxxramass);
                LOGGER.info("There are " + wantedCurseOfNaxxramass.size() + " " + TypesEnum.CURSE_OF_NAXXRAMAS.getName()
                        + " cards.");
                buildUrl(wantedCurseOfNaxxramass);
                putFullCardsIntoSearch(wantedCurseOfNaxxramass);
                datastoreService.putCards(wantedCurseOfNaxxramass);
                break;
            case GOBLINS_VS_GNOMES:
                final List<Card> gobelinsVsGnomes = cardType.getGobelinsVsGnomes();
                final List<Card> wantedGobelinsVsGnomes = removeUnWantedCards(gobelinsVsGnomes);
                LOGGER.info("There are " + wantedGobelinsVsGnomes.size() + " " + TypesEnum.GOBLINS_VS_GNOMES.getName()
                        + " cards.");
                buildUrl(wantedGobelinsVsGnomes);
                putFullCardsIntoSearch(wantedGobelinsVsGnomes);
                datastoreService.putCards(wantedGobelinsVsGnomes);
                break;
            case PROMOTION:
                final List<Card> promotions = cardType.getPromotions();
                final List<Card> wantedPromotions = removeUnWantedCards(promotions);
                LOGGER.info("There are " + wantedPromotions.size() + " " + TypesEnum.PROMOTION.getName() + " cards.");
                buildUrl(wantedPromotions);
                putFullCardsIntoSearch(wantedPromotions);
                datastoreService.putCards(wantedPromotions);
                break;
            default:
                break;
        }
    }

    private List<Card> removeUnWantedCards(List<Card> cards) {

        final List<Card> wantedCards = Lists.newArrayList();

        wantedCards.addAll(Collections2.filter(cards, new Predicate<Card>() {
            @Override
            public boolean apply(Card input) {
                return !Strings.isNullOrEmpty(input.getCollectible());
            }
        }));

        return wantedCards;
    }

    private void buildUrl(List<Card> basics) {
        for (Card basic : basics) {
            basic.setImage(BASE_URL_IMAGE + basic.getId() + PNG);
        }
    }

    private void putFullCardsIntoSearch(List<Card> cards) {
        for (Card card : cards) {
            final String docId = card.getId();
            final Document doc =
                    Document.newBuilder()
                            .setLocale(Locale.FRENCH)
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
                                    .setText(TranslateUtil.translateTypeToFrench(
                                            card.getType(), Locale.FRENCH)))
                            .addField(Field.newBuilder().setName(HSSCStrings.IMAGE_FIELD)
                                    .setAtom(card.getImage()))
                            .addField(Field.newBuilder()
                                    .setName(HSSCStrings.PLAYER_CLASS_FIELD)
                                    .setText(TranslateUtil.translatePlayerClassToFrench(
                                            card.getPlayerClass(), Locale.FRENCH)))
                            .addField(Field.newBuilder().setName(HSSCStrings.FACTION_FIELD)
                                    .setText(card.getFaction()))
                            .addField(Field.newBuilder()
                                    .setName(HSSCStrings.RARITY_FIELD)
                                    .setText(TranslateUtil.translateRarityToFrench(
                                            card.getRarity(), Locale.FRENCH)))
                            .addField(Field.newBuilder().setName(HSSCStrings.COST_FIELD)
                                    .setText(card.getCost()))
                            .addField(Field.newBuilder().setName(HSSCStrings.ATTACK_FIELD)
                                    .setText(card.getAttack()))
                            .addField(Field.newBuilder().setName(HSSCStrings.HEALTH_FIELD)
                                    .setText(card.getHealth()))
                            .addField(Field.newBuilder().setName(HSSCStrings.COLLECTIBLE_FIELD)
                                    .setText(card.getCollectible()))
                            .addField(Field.newBuilder().setName(HSSCStrings.RACE_FIELD)
                                    .setText(card.getRace())).build();

            index.put(doc);

        }
    }

    private static Index getIndex(String indexName) {
        final IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        return SearchServiceFactory.getSearchService().getIndex(indexSpec);
    }

}
