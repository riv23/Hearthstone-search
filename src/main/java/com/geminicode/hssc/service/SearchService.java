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

	public void addToSearch(CardType cardType, TypesEnum type) {

		deleteLastWeekEntries(CARDS);

		switch (type) {
			case BASIC:
				final List<Card> basics = cardType.getBasic();
				LOGGER.info("There are " + basics.size() + " " + TypesEnum.BASIC.getName() + " cards.");
				putIntoSearch(basics);
				break;
			case CLASSIC:
				final List<Card> classics = cardType.getClassic();
				LOGGER.info("There are " + classics.size() + " " + TypesEnum.CLASSIC.getName() + " cards.");
				putIntoSearch(classics);
				break;
			case CURSE_OF_NAXXRAMAS:
				final List<Card> curseOfNaxxramass = cardType.getCurseOfNaxxramas();
				LOGGER.info("There are " + curseOfNaxxramass.size() + " " + TypesEnum.CURSE_OF_NAXXRAMAS.getName() + " cards.");
				putIntoSearch(curseOfNaxxramass);
				break;
			case GOBLINS_VS_GNOMES:
				final List<Card> gobelinsVsGnomes = cardType.getGobelinsVsGnomes();
				LOGGER.info("There are " + gobelinsVsGnomes.size() + " " + TypesEnum.GOBLINS_VS_GNOMES.getName() + " cards.");
				putIntoSearch(gobelinsVsGnomes);
				break;
			case PROMOTION:
				final List<Card> promotions = cardType.getPromotions();
				LOGGER.info("There are " + promotions.size() + " " + TypesEnum.PROMOTION.getName() + " cards.");
				putIntoSearch(promotions);
				break;
			default:
				break;

		}


	}

	public List<Card> search(String query) throws SearchException {
		final List<Card> cards = Lists.newArrayList();
		final IndexSpec indexSpec = IndexSpec.newBuilder().setName(CARDS).build();
		final Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
		final Results<ScoredDocument> results = index.search(query);

		for (ScoredDocument document : results) {
			final Iterable<Field> fields = document.getFields();
			cards.add(generateCardFromField(fields));
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
					.addField(Field.newBuilder().setName("mechanics").setText(buildMechanics(basic))).setLocale(Locale.FRENCH)
					.build();

			IndexADocument(CARDS, doc);

		}
	}

	private Card generateCardFromField(Iterable<Field> fields) {

		final Card card = new Card();

		for (Field field : fields) {
			if ("id".equals(field.getName())) {
				card.setId(field.getName());
			}
			if ("name".equals(field.getName())) {
				card.setName(field.getText());
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
			if ("flavor".equals(field.getName())) {
				card.setFlavor(field.getText());
			}
			if ("artist".equals(field.getName())) {
				card.setArtist(field.getText());
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

	private String buildUrl(String id) {
		return BASE_URL_IMAGE + id + PNG;
	}


	private String buildMechanics(Card basic) {
		if (basic.getMechanics() == null) {
			return "";
		}
		return Arrays.toString(basic.getMechanics());
	}
}
