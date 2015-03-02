package com.geminicode.hssc.service;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.google.appengine.api.search.*;

import java.util.*;

public interface SearchApiService {
	void addToSearch(CardType cardType, TypesEnum type);
    List<Card> search(String query) throws SearchException;
}
