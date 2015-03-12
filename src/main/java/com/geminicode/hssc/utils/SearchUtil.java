package com.geminicode.hssc.utils;

import com.geminicode.hssc.model.Card;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;

public class SearchUtil {
    public static Query buildQuery(String queryString, QueryOptions options) {
        return Query.newBuilder()
                .setOptions(options)
                .build("id:" + queryString + " OR " + HSSCStrings.NAME_FIELD + ":" + queryString + " OR "
                        + HSSCStrings.ATTACK_FIELD + ":" + queryString + " OR "
                        + HSSCStrings.HEALTH_FIELD + ":" + queryString + " OR "
                        + HSSCStrings.COST_FIELD + ":" + queryString + " OR " + HSSCStrings.RACE_FIELD
                        + ":" + queryString + " OR " + HSSCStrings.RARITY_FIELD + ":" + queryString
                        + " OR " + HSSCStrings.TYPE_FIELD + ":" + queryString);
    }

    public static  Card getCardFromField(Iterable<Field> fields) {

        final Card card = new Card();

        for (Field field : fields) {
            if (HSSCStrings.NAME_FIELD.equals(field.getName())) {
                card.setName(field.getText());
            }
            if (HSSCStrings.VERSION_FIELD.equals(field.getName())) {
                card.setVersion(field.getNumber().toString());
            }
            if (HSSCStrings.IMAGE_FIELD.equals(field.getName())) {
                card.setImage(field.getText());
            }
            if (HSSCStrings.TYPE_FIELD.equals(field.getName())) {
                card.setType(field.getText());
            }
            if (HSSCStrings.TEXT_FIELD.equals(field.getName())) {
                card.setText(field.getText());
            }
            if (HSSCStrings.FLAVOR_FIELD.equals(field.getName())) {
                card.setFlavor(field.getText());
            }
            if (HSSCStrings.ARTIST_FIELD.equals(field.getName())) {
                card.setArtist(field.getText());
            }
            if (HSSCStrings.PLAYER_CLASS_FIELD.equals(field.getName())) {
                card.setPlayerClass(field.getText());
            }
            if (HSSCStrings.FACTION_FIELD.equals(field.getName())) {
                card.setFaction(field.getText());
            }
            if (HSSCStrings.RARITY_FIELD.equals(field.getName())) {
                card.setRarity(field.getText());
            }
            if (HSSCStrings.COST_FIELD.equals(field.getName())) {
                card.setCost(field.getText());
            }
            if (HSSCStrings.ATTACK_FIELD.equals(field.getName())) {
                card.setAttack(field.getText());
            }
            if (HSSCStrings.HEALTH_FIELD.equals(field.getName())) {
                card.setHealth(field.getText());
            }
            if (HSSCStrings.COLLECTIBLE_FIELD.equals(field.getName())) {
                card.setCollectible(field.getText());
            }
            if (HSSCStrings.RACE_FIELD.equals(field.getName())) {
                card.setRace(field.getText());
            }

        }

        return card;
    }
}
