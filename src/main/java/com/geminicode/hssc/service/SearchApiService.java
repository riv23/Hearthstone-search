package com.geminicode.hssc.service;

import com.geminicode.hssc.model.Card;
import com.google.appengine.api.search.SearchException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public interface SearchApiService {

    /**
     * This service allows search indexed cards from indexes
     * @param query AppEngine Search valid query
     * @param lang Language (Default English)
     * @param cost Cost (nullable)
     * @return List of matched cards
     * @throws SearchException throws if the search failed
     */
    List<Card> search(String query, String lang, String cost) throws SearchException;

    /**
     * This service allows search card by ID
     * ID card are built like = ID from Blizzard + "_" + "en" (for english description) or "fr" (for french description)
     * @param cardId card ID as described above
     * @return Matched card
     * @throws SearchException throws if the search failed
     */
    Card searchById(String cardId) throws SearchException;

    /**
     * This service delete all card from index
     * @throws IOException throws if delete failed
     */
    void deleteAllCards() throws IOException;

    /**
     * This service check new cards from the referred JSON API and put them to the index
     * @param locale Language (mandatory)
     * @throws IOException throws if check failed
     */
    void checkNewCards(Locale locale) throws IOException;
}
