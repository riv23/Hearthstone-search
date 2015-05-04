package com.geminicode.hssc.service;


import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.NameCard;

import java.util.List;
import java.util.Locale;

public interface DatastoreService {
    /**
     * This service allows auto-completion by name card.
     * @param query Taped name
     * @param lang Language (Default English)
     * @return List of related cards
     */
    List<NameCard> searchNameCards(String query, String lang);

    /**
     * This service saves all name cards for auto-completion
     * @param cards Card list
     * @param locale Language (mandatory)
     */
    void putCards(List<Card> cards, Locale locale);

    /**
     * This service removes all cards from datastore
     */
    void removeAllCards();

    /**
     * This service put special strings to the datastore.
     * @param locale Language (mandatory)
     */
    void putOtherString(Locale locale);
}
