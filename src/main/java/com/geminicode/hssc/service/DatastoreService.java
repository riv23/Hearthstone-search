package com.geminicode.hssc.service;


import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.NameCard;
import com.geminicode.hssc.model.Version;

import java.util.List;
import java.util.Locale;

public interface DatastoreService {
    /**
     * This service allows auto-completion by name card.
     * @param query Taped name
     * @param lang Language (Default US)
     * @return List of related cards
     */
    List<NameCard> searchNameCards(String query, String lang);

    /**
     * This service saves all name cards for auto-completion.
     * Id cards are transform : ID+"_"+trigram language. ex : test_en
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

    /**
     * This service update version of hearthstone.
     * @param version last version number
     */
    void updateVersion(Version version);

    /**
     * This service retrieve the lastest version number
     * @return lastest version number
     */
    Version getVersion();
}
