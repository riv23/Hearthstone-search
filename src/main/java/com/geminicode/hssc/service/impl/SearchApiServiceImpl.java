package com.geminicode.hssc.service.impl;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.geminicode.hssc.service.DatastoreService;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.CardReader;
import com.geminicode.hssc.utils.SearchUtil;
import com.geminicode.hssc.utils.ServiceFactory;
import com.google.appengine.api.search.*;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

public class SearchApiServiceImpl implements SearchApiService {

    private static final Logger LOGGER = Logger.getLogger(SearchApiServiceImpl.class.getName());

    private static final Index INDEX = SearchUtil.getIndex();
    private static final int MAX_PER_PAGE = 1000;
    private static final int MAX_DOC_PER_INDEX = 200;

    private final DatastoreService datastoreService = ServiceFactory.get().getDatastoreService();

    @Override
    public List<Card> search(String queryString, String lang, String cost) throws SearchException {
        final List<Card> cards = Lists.newArrayList();

        queryString = SearchUtil.buildQueryString(queryString, lang, cost);

        final SortOptions sortOptions = SearchUtil.buildSortOptions();
        final QueryOptions options = QueryOptions.newBuilder().setLimit(MAX_PER_PAGE).setSortOptions(sortOptions).build();
        final Query query = SearchUtil.buildQuery(queryString, options);
        final Results<ScoredDocument> results = INDEX.search(query);

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
        final Document document = INDEX.get(cardId);
        return SearchUtil.getCardFromField(document.getFields());
    }

    @Override
    public void deleteAllCards() throws RuntimeException {
        final Queue queue = QueueFactory.getDefaultQueue();
        try {
            while (true) {
                final List<String> docIds = Lists.newArrayList();
                final GetRequest request = GetRequest.newBuilder().setReturningIdsOnly(true).build();

                final GetResponse<Document> response = INDEX.getRange(request);
                if (response.getResults().isEmpty()) {
                    LOGGER.info("No more indexed cards.");
                    break;
                }
                for (Document doc : response) {
                    LOGGER.info("Doc with id : " + doc.getId() + " will be deleted.");
                    docIds.add(doc.getId());
                }
                INDEX.delete(docIds);
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
        final List<Card> wantedCards = Lists.newArrayList();
        wantedCards.addAll(SearchUtil.buildToPersistCards(cardType, TypesEnum.BASIC, locale));
        wantedCards.addAll(SearchUtil.buildToPersistCards(cardType, TypesEnum.CLASSIC, locale));
        wantedCards.addAll(SearchUtil.buildToPersistCards(cardType, TypesEnum.CURSE_OF_NAXXRAMAS, locale));
        wantedCards.addAll(SearchUtil.buildToPersistCards(cardType, TypesEnum.GOBLINS_VS_GNOMES, locale));
        wantedCards.addAll(SearchUtil.buildToPersistCards(cardType, TypesEnum.PROMOTION, locale));
        wantedCards.addAll(SearchUtil.buildToPersistCards(cardType, TypesEnum.BLACKROCK_MOUNTAIN, locale));
        wantedCards.addAll(SearchUtil.buildToPersistCards(cardType, TypesEnum.GRAND_TOURNAMENT, locale));

        putFullCardsIntoSearch(wantedCards, locale);
        datastoreService.putCards(wantedCards, locale);
        datastoreService.putOtherString(locale);

        LOGGER.info("Loading cards " + locale + " : END");
    }

    private void putFullCardsIntoSearch(List<Card> cards, Locale locale) {
        final List<List<Card>> partition = Lists.partition(cards, MAX_DOC_PER_INDEX);
        for (List<Card> cardList : partition) {
            INDEX.put(SearchUtil.buildDocumentsForIndex(cardList, locale));
        }
    }

}
