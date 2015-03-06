package com.geminicode.hssc.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.TranslateUtil;
import com.google.appengine.api.search.*;
import com.google.appengine.repackaged.com.google.api.client.util.Strings;
import com.google.common.collect.Lists;

public class SearchApiServiceImpl implements SearchApiService {

    public static final String BASE_URL_IMAGE = "http://wow.zamimg.com/images/hearthstone/cards/frfr/original/";

    public static final String PNG = ".png";

    private static final Logger LOGGER = Logger.getLogger(SearchApiServiceImpl.class.getName());

    public static final String CARDS = "cards";

    @Override
    public void addToSearch(CardType cardType, TypesEnum type) {

        deleteEntries(CARDS);

        switch (type) {
            case BASIC:
                final List<Card> basics = cardType.getBasic();
                removeUnWantedCards(basics);
                LOGGER.info("There are " + basics.size() + " " + TypesEnum.BASIC.getName() + " cards.");
                buildUrl(basics);
                putFullCardsIntoSearch(basics);
                break;
            case CLASSIC:
                final List<Card> classics = cardType.getClassic();
                removeUnWantedCards(classics);
                LOGGER.info("There are " + classics.size() + " " + TypesEnum.CLASSIC.getName() + " cards.");
                buildUrl(classics);
                putFullCardsIntoSearch(classics);
                break;
            case CURSE_OF_NAXXRAMAS:
                final List<Card> curseOfNaxxramass = cardType.getCurseOfNaxxramas();
                removeUnWantedCards(curseOfNaxxramass);
                LOGGER.info("There are " + curseOfNaxxramass.size() + " " + TypesEnum.CURSE_OF_NAXXRAMAS.getName()
                                + " cards.");
                buildUrl(curseOfNaxxramass);
                putFullCardsIntoSearch(curseOfNaxxramass);
                break;
            case GOBLINS_VS_GNOMES:
                final List<Card> gobelinsVsGnomes = cardType.getGobelinsVsGnomes();
                removeUnWantedCards(gobelinsVsGnomes);
                LOGGER.info("There are " + gobelinsVsGnomes.size() + " " + TypesEnum.GOBLINS_VS_GNOMES.getName()
                                + " cards.");
                buildUrl(gobelinsVsGnomes);
                putFullCardsIntoSearch(gobelinsVsGnomes);
                break;
            case PROMOTION:
                final List<Card> promotions = cardType.getPromotions();
                removeUnWantedCards(promotions);
                LOGGER.info("There are " + promotions.size() + " " + TypesEnum.PROMOTION.getName() + " cards.");
                buildUrl(promotions);
                putFullCardsIntoSearch(promotions);
                break;
            default:
                break;
        }
    }

    @Override
    public List<Card> search(String queryString) throws SearchException {
        final List<Card> cards = Lists.newArrayList();
		
		if(Strings.isNullOrEmpty(queryString)) {
			return cards;
		}
		
        final IndexSpec indexSpec = IndexSpec.newBuilder().setName(CARDS).build();

        final QueryOptions options = QueryOptions.newBuilder()
                .setLimit(1000)
                .build();

        final Query query = Query.newBuilder().setOptions(options).build(queryString);

        final Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
        final Results<ScoredDocument> results = index.search(query);

        for (ScoredDocument document : results) {
            final Iterable<Field> fields = document.getFields();
            final Card card = generateFullCardFromField(fields);
            card.setId(document.getId());
            cards.add(card);
        }

        return cards;
    }

    @Override
    public Card searchById(String id) throws SearchException {
        final IndexSpec indexSpec = IndexSpec.newBuilder().setName(CARDS).build();

        final Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
        final Document document = index.get(id);
        final Iterable<Field> fields = document.getFields();
        final Card card = generateFullCardFromField(fields);
        card.setId(document.getId());

        return card;
    }

    @Override
    public void deleteEntries(String indexName) {
        final IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        final Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
        index.deleteSchema();
    }

    private void removeUnWantedCards(List<Card> cards) {
        final List<Card> cardsToRemove =  Lists.newArrayList();
        for (Card card : cards) {
            if(Strings.isNullOrEmpty(card.getCollectible())) {
                cardsToRemove.add(card);
            }
        }
        cards.removeAll(cardsToRemove);
    }

    private void buildUrl(List<Card> basics) {
        for (Card basic : basics) {
            basic.setImage(BASE_URL_IMAGE + basic.getId() + PNG);
        }
    }

    private void putFullCardsIntoSearch(List<Card> cards) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        final int week = cal.get(Calendar.WEEK_OF_YEAR);

        for (Card card : cards) {
            final String docId = card.getId();
            final Document doc =
                            Document.newBuilder()
                                            .setLocale(Locale.FRENCH)
                                            .setId(docId)
                                            .addField(Field.newBuilder().setName("name").setText(card.getName()))
                                            .addField(Field.newBuilder().setName("version").setNumber(week))
                                            .addField(Field.newBuilder().setName("text").setText(card.getText()))
                                            .addField(Field.newBuilder().setName("flavor").setText(card.getFlavor()))
                                            .addField(Field.newBuilder().setName("artist").setText(card.getArtist()))
                                            .addField(Field.newBuilder().setName("type").setText(TranslateUtil.translateType(card.getType(), Locale.FRENCH)))
                                            .addField(Field.newBuilder().setName("image").setText(card.getImage()))
                                            .addField(Field.newBuilder().setName("playerClass")
                                                            .setText(TranslateUtil.translatePlayerClass(card.getPlayerClass(), Locale.FRENCH)))
                                            .addField(Field.newBuilder().setName("faction").setText(card.getFaction()))
                                            .addField(Field.newBuilder().setName("rarity").setText(TranslateUtil.translateRarity(card.getRarity(), Locale.FRENCH)))
                                            .addField(Field.newBuilder().setName("cost").setText(card.getCost()))
                                            .addField(Field.newBuilder().setName("attack").setText(card.getAttack()))
                                            .addField(Field.newBuilder().setName("health").setText(card.getHealth()))
                                            .addField(Field.newBuilder().setName("collectible")
                                                            .setText(card.getCollectible()))
                                            .addField(Field.newBuilder().setName("race").setText(card.getRace()))
                                    .build();

            IndexADocument(CARDS, doc);

        }
    }

    private Card generateFullCardFromField(Iterable<Field> fields) {

        final Card card = new Card();

        for (Field field : fields) {
            if ("name".equals(field.getName())) {
                card.setName(field.getText());
            }
            if ("version".equals(field.getName())) {
                card.setVersion(field.getNumber().toString());
            }
            if ("image".equals(field.getName())) {
                card.setImage(field.getText());
            }
            if ("type".equals(field.getName())) {
                card.setType(field.getText());
            }
            if ("text".equals(field.getName())) {
                card.setText(field.getText());
            }
            if ("flavor".equals(field.getName())) {
                card.setFlavor(field.getText());
            }
            if ("artist".equals(field.getName())) {
                card.setArtist(field.getText());
            }
            if ("playerClass".equals(field.getName())) {
                card.setPlayerClass(field.getText());
            }
            if ("faction".equals(field.getName())) {
                card.setFaction(field.getText());
            }
            if ("rarity".equals(field.getName())) {
                card.setRarity(field.getText());
            }
            if ("cost".equals(field.getName())) {
                card.setCost(field.getText());
            }
            if ("attack".equals(field.getName())) {
                card.setAttack(field.getText());
            }
            if ("health".equals(field.getName())) {
                card.setHealth(field.getText());
            }
            if ("collectible".equals(field.getName())) {
                card.setCollectible(field.getText());
            }
            if ("race".equals(field.getName())) {
                card.setRace(field.getText());
            }

        }

        return card;
    }

    private void IndexADocument(String indexName, Document document) throws PutException {
        final IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        final Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
        index.put(document);
    }

}
