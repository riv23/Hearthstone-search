package com.geminicode.hssc.service.impl;


import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.NameCard;
import com.geminicode.hssc.service.DatastoreService;
import com.geminicode.hssc.service.OfyService;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.text.Normalizer;
import java.util.List;

public class DatastoreServiceImpl implements DatastoreService {

    @Override
    public List<NameCard> searchNameCards(String query) {
        if(Strings.isNullOrEmpty(query)) {
            return Lists.newArrayList();
        }
        return OfyService.ofy().load().type(NameCard.class).order("compute").filter("compute >=", query.toLowerCase()).filter("compute <", query.toLowerCase() + "\uFFFD").limit(5).list();
    }

    @Override
    public void putCards(List<Card> basics) {
        final List<NameCard> nameCards = Lists.newArrayList();

        nameCards.addAll(Collections2.transform(basics, new Function<Card, NameCard>() {
            @Override
            public NameCard apply(Card card) {
                final NameCard nameCard = new NameCard();
                nameCard.setId(card.getId());
                nameCard.setName(card.getName());
                nameCard.setCompute(getComputeName(card.getName()));
                return nameCard;
            }
        }));

        OfyService.ofy().save().entities(nameCards).now();

    }

    @Override
    public void removeAllCards() {
        final List<NameCard> list = OfyService.ofy().load().type(NameCard.class).list();
        OfyService.ofy().delete().entities(list).now();
    }

    private String getComputeName(String name) {
        final String string = Normalizer.normalize(name, Normalizer.Form.NFD).toLowerCase();
        return string.replaceAll("[^\\p{ASCII}]", "");
    }
}
