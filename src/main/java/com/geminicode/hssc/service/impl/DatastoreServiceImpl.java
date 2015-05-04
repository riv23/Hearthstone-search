package com.geminicode.hssc.service.impl;


import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.NameCard;
import com.geminicode.hssc.service.DatastoreService;
import com.geminicode.hssc.service.OfyService;
import com.geminicode.hssc.utils.HSSCStrings;
import com.geminicode.hssc.utils.TranslateUtil;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

public class DatastoreServiceImpl implements DatastoreService {

    public static final String EN = "en";

    @Override
    public List<NameCard> searchNameCards(String query, String lang) {
        if (Strings.isNullOrEmpty(query)) {
            return Lists.newArrayList();
        }
        if (Strings.isNullOrEmpty(lang) || !lang.contains("fr")) {
            lang = EN;
        }
        return OfyService.ofy().load().type(NameCard.class).order("compute").filter("language", lang).filter("compute >=", query.toLowerCase()).filter("compute <", query.toLowerCase() + "\uFFFD").limit(5).list();
    }

    @Override
    public void putCards(List<Card> cards, final Locale locale) {
        final List<NameCard> nameCards = Lists.newArrayList();

        nameCards.addAll(Collections2.transform(cards, new Function<Card, NameCard>() {
            @Override
            public NameCard apply(Card card) {
                final NameCard nameCard = new NameCard();
                nameCard.setId(card.getId() + "_" + TranslateUtil.buildLanguageField(locale));
                nameCard.setName(card.getName());
                nameCard.setCompute(getComputeName(card.getName()));
                nameCard.setLanguage(TranslateUtil.buildLanguageField(locale));
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

    @Override
    public void putOtherString(final Locale locale) {

        final List<String> otherStyrings = Lists.newArrayList();

        if (Locale.FRENCH.equals(locale)) {
            otherStyrings.add(HSSCStrings.EPIC_FR);
            otherStyrings.add(HSSCStrings.LEGENDARY_FR);
            otherStyrings.add(HSSCStrings.COMMON_FR);
            otherStyrings.add(HSSCStrings.FREE_FR);
            otherStyrings.add(HSSCStrings.WARRIOR_FR);
            otherStyrings.add(HSSCStrings.ROGUE_FR);
            otherStyrings.add(HSSCStrings.HUNTER_FR);
            otherStyrings.add(HSSCStrings.DRUID_FR);
            otherStyrings.add(HSSCStrings.WARLOCK_FR);
            otherStyrings.add(HSSCStrings.PRIEST_FR);
            otherStyrings.add(HSSCStrings.MINION_FR);
            otherStyrings.add(HSSCStrings.SPELL_FR);
            otherStyrings.add(HSSCStrings.SHAMAN_FR);
            otherStyrings.add(HSSCStrings.BATTLECRY_FR);
            otherStyrings.add(HSSCStrings.FREEZE_FR);
            otherStyrings.add(HSSCStrings.DEATHRATTLE_FR);
            otherStyrings.add(HSSCStrings.STEALTH_FR);
            otherStyrings.add(HSSCStrings.WINDFURY_FR);
            otherStyrings.add(HSSCStrings.TAUNT_FR);
            otherStyrings.add(HSSCStrings.ENRAGE_FR);
            otherStyrings.add(HSSCStrings.DIVINESHIELD_FR);
            otherStyrings.add(HSSCStrings.MECH_FR);
        }

        if (Locale.ENGLISH.equals(locale)) {
            otherStyrings.add(HSSCStrings.EPIC_EN);
            otherStyrings.add(HSSCStrings.LEGENDARY_EN);
            otherStyrings.add(HSSCStrings.COMMON_EN);
            otherStyrings.add(HSSCStrings.FREE_EN);
            otherStyrings.add(HSSCStrings.WARRIOR_EN);
            otherStyrings.add(HSSCStrings.ROGUE_EN);
            otherStyrings.add(HSSCStrings.HUNTER_EN);
            otherStyrings.add(HSSCStrings.DRUID_EN);
            otherStyrings.add(HSSCStrings.SHAMAN_EN);
            otherStyrings.add(HSSCStrings.WARLOCK_EN);
            otherStyrings.add(HSSCStrings.MINION_EN);
            otherStyrings.add(HSSCStrings.SPELL_EN);
            otherStyrings.add(HSSCStrings.PRIEST_EN);
            otherStyrings.add(HSSCStrings.BATTLECRY_EN);
            otherStyrings.add(HSSCStrings.FREEZE_EN);
            otherStyrings.add(HSSCStrings.DEATHRATTLE_EN);
            otherStyrings.add(HSSCStrings.STEALTH_EN);
            otherStyrings.add(HSSCStrings.WINDFURY_EN);
            otherStyrings.add(HSSCStrings.TAUNT_EN);
            otherStyrings.add(HSSCStrings.ENRAGE_EN);
            otherStyrings.add(HSSCStrings.DIVINESHIELD_EN);
            otherStyrings.add(HSSCStrings.MECH_EN);

        }

        final List<NameCard> nameCards = Lists.newArrayList();

        nameCards.addAll(Collections2.transform(otherStyrings, new Function<String, NameCard>() {
            @Override
            public NameCard apply(String string) {
                final NameCard nameCard = new NameCard();
                nameCard.setId(string + "_" + TranslateUtil.buildLanguageField(locale));
                nameCard.setName(string);
                nameCard.setCompute(getComputeName(string));
                nameCard.setLanguage(TranslateUtil.buildLanguageField(locale));
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
