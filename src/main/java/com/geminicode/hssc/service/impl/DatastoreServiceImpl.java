package com.geminicode.hssc.service.impl;


import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.NameCard;
import com.geminicode.hssc.service.DatastoreService;
import com.geminicode.hssc.service.OfyService;
import com.geminicode.hssc.utils.HSSCStrings;
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

    /**
     * Only french strings for the moment
     */
    @Override
    public void putOtherString() {

        final List<String> otherStyrings = Lists.newArrayList();

        otherStyrings.add(HSSCStrings.EPIC_FR);
        otherStyrings.add(HSSCStrings.LEGENDARY_FR);
        otherStyrings.add(HSSCStrings.COMMON_FR);
        otherStyrings.add(HSSCStrings.FREE_FR);
        otherStyrings.add(HSSCStrings.WARRIOR_FR);
        otherStyrings.add(HSSCStrings.ROGUE_FR);
        otherStyrings.add(HSSCStrings.HUNTER_FR);
        otherStyrings.add(HSSCStrings.DRUID_FR);
        otherStyrings.add(HSSCStrings.SHAMAN_FR);
        otherStyrings.add(HSSCStrings.WARLOCK_FR);
        otherStyrings.add(HSSCStrings.PRIEST_FR);
        otherStyrings.add(HSSCStrings.MINION_FR);
        otherStyrings.add(HSSCStrings.SPELL_FR);
        otherStyrings.add(HSSCStrings.EPIC_FR);
        otherStyrings.add(HSSCStrings.LEGENDARY_FR);
        otherStyrings.add(HSSCStrings.COMMON_FR);
        otherStyrings.add(HSSCStrings.FREE_FR);
        otherStyrings.add(HSSCStrings.WARRIOR_FR);
        otherStyrings.add(HSSCStrings.ROGUE_FR);
        otherStyrings.add(HSSCStrings.HUNTER_FR);
        otherStyrings.add(HSSCStrings.DRUID_FR);
        otherStyrings.add(HSSCStrings.SHAMAN_FR);
        otherStyrings.add(HSSCStrings.WARLOCK_FR);
        otherStyrings.add(HSSCStrings.PRIEST_FR);
        otherStyrings.add(HSSCStrings.MINION_FR);
        otherStyrings.add(HSSCStrings.SPELL_FR);
        otherStyrings.add(HSSCStrings.BATTLECRY_FR);
        otherStyrings.add(HSSCStrings.FREEZE_FR);
        otherStyrings.add(HSSCStrings.DEATHRATTLE_FR);
        otherStyrings.add(HSSCStrings.STEALTH_FR);
        otherStyrings.add(HSSCStrings.WINDFURY_FR);
        otherStyrings.add(HSSCStrings.TAUNT_FR);
        otherStyrings.add(HSSCStrings.ENRAGE_FR);
        otherStyrings.add(HSSCStrings.DIVINESHIELD_FR);
        otherStyrings.add(HSSCStrings.MECH_FR);

        final List<NameCard> nameCards = Lists.newArrayList();

        nameCards.addAll(Collections2.transform(otherStyrings, new Function<String, NameCard>() {
            @Override
            public NameCard apply(String string) {
                final NameCard nameCard = new NameCard();
                nameCard.setId(string);
                nameCard.setName(string);
                nameCard.setCompute(getComputeName(string));
                return nameCard;
            }
        }));
        OfyService.ofy().save().entities(nameCards).now();
    }

    private String getComputeName(String name) {
        final String string = Normalizer.normalize(name, Normalizer.Form.NFD).toLowerCase();
        return string.replaceAll("[^\\p{ASCII}]", "");
    }
}
