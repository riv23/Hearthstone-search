package com.geminicode.hssc.service;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.geminicode.hssc.service.impl.CardDatastoreServiceImpl;
import com.google.appengine.api.search.*;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.logging.Logger;

public interface SearchApiService {
	void addToSearch(CardType cardType, TypesEnum type);
    List<Card> search(String query) throws SearchException;
}
