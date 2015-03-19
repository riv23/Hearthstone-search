package com.geminicode.hssc.service.impl;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.HSSCStrings;
import com.geminicode.hssc.utils.SearchUtil;
import com.geminicode.hssc.utils.TranslateUtil;
import com.google.appengine.api.search.*;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.repackaged.com.google.api.client.util.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

public class SearchApiServiceImpl implements SearchApiService {

    private static final Logger LOGGER = Logger.getLogger(SearchApiServiceImpl.class.getName());

    public static final String BASE_URL_IMAGE = "http://wow.zamimg.com/images/hearthstone/cards/frfr/original/";
    public static final String URL_API = "http://hearthstonejson.com/json/AllSets.frFR.json";
    public static final String URL_LOCAL_API = "http://localhost:8080/AllSets.frFR.json";
    public static final String PNG = ".png";
    public static final String CARDS = "cards";

    @Override
    public List<Card> search(String queryString) throws SearchException {

        final List<Card> cards = Lists.newArrayList();

        if (Strings.isNullOrEmpty(queryString)) {
            return cards;
        }

        final IndexSpec indexSpec = IndexSpec.newBuilder().setName(CARDS).build();
        final QueryOptions options = QueryOptions.newBuilder().setLimit(1000).build();

        final Query query = SearchUtil.buildQuery(queryString, options);

        final Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
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
        final Index index = getIndex(CARDS);
        final Document document = index.get(id);
        final Iterable<Field> fields = document.getFields();
        final Card card = SearchUtil.getCardFromField(fields);
        card.setId(document.getId());

        return card;
    }

    @Override
    public void checkNewCards() throws IOException {
        final Queue queue = QueueFactory.getDefaultQueue();
        try {
            final Index index = getIndex(CARDS);
            while (true) {
                final List<String> docIds = Lists.newArrayList();
                final GetRequest request = GetRequest.newBuilder().setReturningIdsOnly(true).build();

                final GetResponse<Document> response = index.getRange(request);
                if (response.getResults().isEmpty()) {
                    LOGGER.info("Loading cards : START");
                    final CardType cardType = readyCardFromJson();
                    addToSearch(cardType, TypesEnum.BASIC);
                    addToSearch(cardType, TypesEnum.CLASSIC);
                    addToSearch(cardType, TypesEnum.CURSE_OF_NAXXRAMAS);
                    addToSearch(cardType, TypesEnum.GOBLINS_VS_GNOMES);
                    addToSearch(cardType, TypesEnum.PROMOTION);
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

    private void addToSearch(CardType cardType, TypesEnum type) {

        switch (type) {
            case BASIC:
                final List<Card> basics = cardType.getBasic();
                removeUnWantedCards(basics);
                LOGGER.info("There are " + basics.size() + " " + TypesEnum.BASIC.getName() + " cards.");
                buildUrl(basics);
                putFullCardsIntoSearch(basics);
                break;
            case CLASSIC:
                final List<Card> classics = cardType.getClassic();
                removeUnWantedCards(classics);
                LOGGER.info("There are " + classics.size() + " " + TypesEnum.CLASSIC.getName() + " cards.");
                buildUrl(classics);
                putFullCardsIntoSearch(classics);
                break;
            case CURSE_OF_NAXXRAMAS:
                final List<Card> curseOfNaxxramass = cardType.getCurseOfNaxxramas();
                removeUnWantedCards(curseOfNaxxramass);
                LOGGER.info("There are " + curseOfNaxxramass.size() + " " + TypesEnum.CURSE_OF_NAXXRAMAS.getName()
                        + " cards.");
                buildUrl(curseOfNaxxramass);
                putFullCardsIntoSearch(curseOfNaxxramass);
                break;
            case GOBLINS_VS_GNOMES:
                final List<Card> gobelinsVsGnomes = cardType.getGobelinsVsGnomes();
                removeUnWantedCards(gobelinsVsGnomes);
                LOGGER.info("There are " + gobelinsVsGnomes.size() + " " + TypesEnum.GOBLINS_VS_GNOMES.getName()
                        + " cards.");
                buildUrl(gobelinsVsGnomes);
                putFullCardsIntoSearch(gobelinsVsGnomes);
                break;
            case PROMOTION:
                final List<Card> promotions = cardType.getPromotions();
                removeUnWantedCards(promotions);
                LOGGER.info("There are " + promotions.size() + " " + TypesEnum.PROMOTION.getName() + " cards.");
                buildUrl(promotions);
                putFullCardsIntoSearch(promotions);
                break;
            default:
                break;
        }
    }

    private void removeUnWantedCards(List<Card> cards) {
        final List<Card> cardsToRemove = Lists.newArrayList();
        for (Card card : cards) {
            if (Strings.isNullOrEmpty(card.getCollectible())) {
                cardsToRemove.add(card);
            }
        }
        cards.removeAll(cardsToRemove);
    }

    private void buildUrl(List<Card> basics) {
        for (Card basic : basics) {
            basic.setImage(BASE_URL_IMAGE + basic.getId() + PNG);
        }
    }

    private void putFullCardsIntoSearch(List<Card> cards) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

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

            IndexADocument(CARDS, doc);

        }
    }

    private void IndexADocument(String indexName, Document document) throws PutException {
        final Index index = getIndex(indexName);
        index.put(document);
    }

    private Index getIndex(String indexName) {
        final IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        return SearchServiceFactory.getSearchService().getIndex(indexSpec);
    }

    private CardType readyCardFromJson() throws IOException {
        URL url;
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
            url = new URL(URL_LOCAL_API);
        }else {
            url = new URL(URL_API);
        }

        final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        final Gson gson = new Gson();

        return gson.fromJson(br, CardType.class);
    }

}
