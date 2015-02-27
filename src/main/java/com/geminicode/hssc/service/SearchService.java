package com.geminicode.hssc.service;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.google.appengine.api.search.*;

import java.util.*;
import java.util.logging.Logger;

public class SearchService {

	public static final String BASE_URL_IMAGE = "http://wow.zamimg.com/images/hearthstone/cards/frfr/original/";
	public static final String PNG = ".png";

	private static final Logger LOGGER = Logger.getLogger(SearchService.class.getName());
	public static final String CARDS = "cards";

	public void addToSearch(CardType cardType, TypesEnum type) {

		deleteLastWeekEntries(CARDS);

		switch (type) {
			case BASIC:
				final List<Card> basics = cardType.getBasic();
				final int size = basics.size();
				LOGGER.info("il y a " + size + " basics de parsés.");
				putIntoSearch(basics);
				break;
			case CLASSIC:
				final List<Card> classics = cardType.getClassic();
				LOGGER.info("il y a " + classics.size() + " classics de parsés.");
				putIntoSearch(classics);
				break;
			case CURSE_OF_NAXXRAMAS:
				final List<Card> curseOfNaxxramass = cardType.getCurseOfNaxxramas();
				LOGGER.info("il y a " + curseOfNaxxramass.size() + " nax de parsés.");
				putIntoSearch(curseOfNaxxramass);
				break;
			case GOBLINS_VS_GNOMES:
				final List<Card> gobelinsVsGnomes = cardType.getGobelinsVsGnomes();
				LOGGER.info("il y a " + gobelinsVsGnomes.size() + " gvg de parsés.");
				putIntoSearch(gobelinsVsGnomes);
				break;
			case PROMOTION:
				final List<Card> promotions = cardType.getPromotions();
				LOGGER.info("il y a " + promotions.size() + " promotions de parsés.");
				putIntoSearch(promotions);
				break;
			default:
				break;

		}


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

		for (Card basic : cards) {
			final String docId = basic.getId();
			final Document doc = Document.newBuilder()
					.setId(docId)
					.addField(Field.newBuilder().setName("name").setText(basic.getName())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("version").setNumber(week)).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("type").setText(basic.getType())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("text").setText(basic.getText())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("image").setText(buildUrl(basic.getId()))).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("playerClass").setText(basic.getPlayerClass())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("faction").setText(basic.getFaction())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("rarity").setText(basic.getRarity())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("cost").setText(basic.getCost())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("attack").setText(basic.getAttack())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("health").setText(basic.getHealth())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("flavor").setText(basic.getFlavor())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("artist").setText(basic.getArtist())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("collectible").setText(basic.getCollectible())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("race").setText(basic.getRace())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("howToGetGold").setText(basic.getHowToGetGold())).setLocale(Locale.FRENCH)
					.addField(Field.newBuilder().setName("mechanics").setText(Arrays.toString(basic.getMechanics()))).setLocale(Locale.FRENCH)
					.build();

			IndexADocument(CARDS, doc);

		}
	}

	private void IndexADocument(String indexName, Document document) throws PutException {
		final IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
		final Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
		index.put(document);

	}

	private String buildUrl(String id) {
		return BASE_URL_IMAGE + id + PNG;
	}
}
