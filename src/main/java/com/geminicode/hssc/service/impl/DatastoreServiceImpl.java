package com.geminicode.hssc.service.impl;


import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.NameCard;
import com.geminicode.hssc.model.Version;
import com.geminicode.hssc.service.DatastoreService;
import com.geminicode.hssc.service.InternalizationService;
import com.geminicode.hssc.service.OfyService;
import com.geminicode.hssc.utils.ServiceFactory;
import com.geminicode.hssc.utils.TranslateUtil;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatastoreServiceImpl implements DatastoreService {

    private static final String EN = "en";
    private static final String FR = "fr";

    private static final Logger LOGGER = Logger.getLogger(SearchApiServiceImpl.class.getName());

    private final InternalizationService internalizationService = ServiceFactory.get().getInternalizationService();

    @Override
    public List<NameCard> searchNameCards(String query, String lang) {
        if (Strings.isNullOrEmpty(query)) {
            return Lists.newArrayList();
        }
        if (Strings.isNullOrEmpty(lang) || !lang.contains(FR)) {
            lang = EN;
        }
        return OfyService.ofy().load().type(NameCard.class).order("compute").filter("language", lang).filter("compute >=", query.toLowerCase()).filter("compute <", query.toLowerCase() + "\uFFFD").limit(5).list();
    }

    @Override
    public void putCards(List<Card> cards, final String version, final Locale locale) {
        final List<NameCard> nameCards = Lists.newArrayList();

        nameCards.addAll(Collections2.transform(cards, new Function<Card, NameCard>() {
            @Override
            public NameCard apply(Card card) {
                final NameCard nameCard = new NameCard();
                nameCard.setId(card.getId() + "_" + TranslateUtil.buildLanguageField(locale));
                nameCard.setName(card.getName());
                nameCard.setCompute(getComputeName(card.getName()));
                nameCard.setLanguage(TranslateUtil.buildLanguageField(locale));
                nameCard.setVersion(version);
                return nameCard;
            }
        }));

        OfyService.ofy().save().entities(nameCards).now();

    }

    @Override
    public void removeAllCards(String version) {
        final List<NameCard> list = OfyService.ofy().load().type(NameCard.class).list();
        final List<NameCard> listToRemove = Lists.newArrayList();
        for (NameCard nameCard : list) {
            if(!version.equals(nameCard.getVersion())) {
                listToRemove.add(nameCard);
            }
        }

        OfyService.ofy().delete().entities(listToRemove).now();
    }

    @Override
    public void putOtherString(final Locale locale, final String version) {

        final List<String> otherStyrings = Lists.newArrayList();
        otherStyrings.addAll(internalizationService.getKeys(locale));

        final List<NameCard> nameCards = Lists.newArrayList();

        nameCards.addAll(Collections2.transform(otherStyrings, new Function<String, NameCard>() {
            @Override
            public NameCard apply(String string) {
                final NameCard nameCard = new NameCard();
                nameCard.setId(string + "_" + TranslateUtil.buildLanguageField(locale));
                try {
                    nameCard.setName(TranslateUtil.translate(string, locale));
                    nameCard.setCompute(getComputeName(TranslateUtil.translate(string, locale)));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.log(Level.SEVERE, "Error when translate " + string + " to " + locale.getLanguage());
                }
                nameCard.setLanguage(TranslateUtil.buildLanguageField(locale));
                nameCard.setVersion(version);
                return nameCard;
            }
        }));
        OfyService.ofy().save().entities(nameCards).now();
    }

    @Override
    public void updateVersion(Version version) {
        OfyService.ofy().save().entity(version).now();
    }

    @Override
    public Version getVersion() {
        final List<Version> versions = OfyService.ofy().load().type(Version.class).limit(1).list();
        if(!versions.isEmpty()) {
            return versions.get(0);
        }

        final Version version = new Version();
        version.setVersion("0");
        return version;
    }

    private String getComputeName(String name) {
        final String string = Normalizer.normalize(name, Normalizer.Form.NFD).toLowerCase();
        return string.replaceAll("[^\\p{ASCII}]", "");
    }
}
