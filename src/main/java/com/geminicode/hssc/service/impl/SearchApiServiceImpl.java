package com.geminicode.hssc.service.impl;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.Version;
import com.geminicode.hssc.service.DatastoreService;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.CardReader;
import com.geminicode.hssc.utils.SearchUtil;
import com.geminicode.hssc.utils.ServiceFactory;
import com.google.appengine.api.search.*;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
        if (Strings.isNullOrEmpty(queryString)) {
            return cards;
        }

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
    public Card searchById(String cardId, String lang) throws SearchException {
        if(Strings.isNullOrEmpty(lang) || !"fr".equals(lang)) {
            lang = "en";
        }
        final Document document = INDEX.get(cardId + "_" + lang);
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
                    LOGGER.info("All cards are been deleted from index");
                    break;
                }
                for (Document doc : response) {
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
        final Map<String, List<Card>> mapCards = CardReader.read(locale);
        final List<Card> wantedCards = Lists.newArrayList();

        wantedCards.addAll(SearchUtil.buildToPersistCards(mapCards, locale));
        putFullCardsIntoSearch(wantedCards, locale);
        datastoreService.putCards(wantedCards, locale);
        datastoreService.putOtherString(locale);

        LOGGER.info("Loading cards " + locale + " : END");
    }

    @Override
    public Boolean isLastedVersion() throws SearchException {
        URL url = null;
        try {
            url = new URL("http://hearthstonejson.com/json/patchVersion.json");
        } catch (MalformedURLException e) {
            throw new SearchException("Mal formed url : " + url.toString());
        }

        final BufferedReader bufferedReader;
        try {
            bufferedReader =  CardReader.getBufferReader(url);
        } catch (IOException e) {
            LOGGER.warning(e.getLocalizedMessage());
            return false;
        }
        final Gson gson = new Gson();
        final Version versionFromAPI = gson.fromJson(bufferedReader, Version.class);
        final Version versionFromDB = datastoreService.getVersion();

        final int result = versionFromAPI.getVersion().compareTo(versionFromDB.getVersion());

        if(result > 0) {
            versionFromAPI.setId("1");
            datastoreService.updateVersion(versionFromAPI);
            return true;
        } else {
            return false;
        }

    }

    private void putFullCardsIntoSearch(List<Card> cards, Locale locale) throws UnsupportedEncodingException {
        final List<List<Card>> partition = Lists.partition(cards, MAX_DOC_PER_INDEX);
        for (List<Card> cardList : partition) {
            INDEX.put(SearchUtil.buildDocumentsForIndex(cardList, locale));
        }
    }

}
