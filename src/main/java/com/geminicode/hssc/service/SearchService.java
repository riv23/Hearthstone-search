package com.geminicode.hssc.service;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.google.appengine.api.search.*;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.logging.Logger;

public class SearchService {

	public static final String BASE_URL_IMAGE = "http://wow.zamimg.com/images/hearthstone/cards/frfr/original/";
	public static final String PNG = ".png";

	private static final Logger LOGGER = Logger.getLogger(SearchService.class.getName());
	public static final String CARDS = "cards";

    public final CardDataStoreService cardDataStoreService = new CardDataStoreService();

	public void addToSearch(CardType cardType, TypesEnum type) {

		deleteLastWeekEntries(CARDS);

		switch (type) {
			case BASIC:
				final List<Card> basics = cardType.getBasic();
				LOGGER.info("There are " + basics.size() + " " + TypesEnum.BASIC.getName() + " cards.");
                buildUrl(basics);
				putIntoSearch(basics);
                cardDataStoreService.putIntoDataStore(basics);
                break;
			case CLASSIC:
				final List<Card> classics = cardType.getClassic();
				LOGGER.info("There are " + classics.size() + " " + TypesEnum.CLASSIC.getName() + " cards.");
                buildUrl(classics);
				putIntoSearch(classics);
                cardDataStoreService. putIntoDataStore(classics);
				break;
			case CURSE_OF_NAXXRAMAS:
				final List<Card> curseOfNaxxramass = cardType.getCurseOfNaxxramas();
				LOGGER.info("There are " + curseOfNaxxramass.size() + " " + TypesEnum.CURSE_OF_NAXXRAMAS.getName() + " cards.");
                buildUrl(curseOfNaxxramass);
				putIntoSearch(curseOfNaxxramass);
                cardDataStoreService.putIntoDataStore(curseOfNaxxramass);
				break;
			case GOBLINS_VS_GNOMES:
				final List<Card> gobelinsVsGnomes = cardType.getGobelinsVsGnomes();
				LOGGER.info("There are " + gobelinsVsGnomes.size() + " " + TypesEnum.GOBLINS_VS_GNOMES.getName() + " cards.");
                buildUrl(gobelinsVsGnomes);
				putIntoSearch(gobelinsVsGnomes);
                cardDataStoreService.putIntoDataStore(gobelinsVsGnomes);
				break;
			case PROMOTION:
				final List<Card> promotions = cardType.getPromotions();
				LOGGER.info("There are " + promotions.size() + " " + TypesEnum.PROMOTION.getName() + " cards.");
                buildUrl(promotions);
				putIntoSearch(promotions);
                cardDataStoreService.putIntoDataStore(promotions);
				break;
			default:
				break;
		}
	}

    private void buildUrl(List<Card> basics) {
        for (Card basic : basics) {
            basic.setImage(BASE_URL_IMAGE + basic.getId() + PNG);
        }
    }

    public List<Card> search(String query) throws SearchException {
		final List<Card> cards = Lists.newArrayList();
		final IndexSpec indexSpec = IndexSpec.newBuilder().setName(CARDS).build();
		final Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
		final Results<ScoredDocument> results = index.search(query);

		for (ScoredDocument document : results) {
			final Iterable<Field> fields = document.getFields();
			final Card card = generateCardFromField(fields);
			card.setId(document.getId());
			cards.add(card);
		}

		return cards;
	}

	private void deleteLastWeekEntries(String indexName) {
		final IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
		final Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
		index.deleteSchema();
	}

	private void putIntoSearch(List<Card> cards) {

		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		final int week = cal.get(Calendar.WEEK_OF_YEAR);

		for (Card card : cards) {
			final String docId = card.getId();
			final Document doc = Document.newBuilder()
					.setId(docId)
					.addField(Field.newBuilder().setName("name").setText(card.getName())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("version").setNumber(week)).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("type").setText(card.getType())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("image").setText(card.getImage())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("playerClass").setText(card.getPlayerClass())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("faction").setText(card.getFaction())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("rarity").setText(card.getRarity())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("cost").setText(card.getCost())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("attack").setText(card.getAttack())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("health").setText(card.getHealth())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("collectible").setText(card.getCollectible())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("race").setText(card.getRace())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("howToGetGold").setText(card.getHowToGetGold())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("mechanics").setText(Arrays.toString(card.getMechanics()))).setLocale(Locale.FRENCH)
                            .build();

			IndexADocument(CARDS, doc);

		}
	}

	private Card generateCardFromField(Iterable<Field> fields) {

		final Card card = new Card();

		for (Field field : fields) {
			if ("name".equals(field.getName())) {
				card.setName(field.getText());
			}
			if ("image".equals(field.getName())) {
				card.setImage(field.getText());
			}
			if ("type".equals(field.getName())) {
				card.setType(field.getText());
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
			if ("howToGetGold".equals(field.getName())) {
				card.setHowToGetGold(field.getText());
			}
			if("mechanics".equals(field.getName())) {
                //TODO review
				card.setMechanics(new String[]{field.getText()});
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
