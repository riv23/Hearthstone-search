package com.geminicode.hssc.utils;

import com.geminicode.hssc.model.Card;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;

public class SearchUtil {
    public static Query buildQuery(String queryString, QueryOptions options) {
        return Query.newBuilder()
                .setOptions(options)
                .build(queryString);
    }

    public static  Card getCardFromField(Iterable<Field> fields) {

        final Card card = new Card();

        for (Field field : fields) {
            if (HSSCStrings.NAME_FIELD.equals(field.getName())) {
                card.setName(field.getText());
            }
            if (HSSCStrings.IMAGE_FIELD.equals(field.getName())) {
                card.setImage(field.getAtom());
            }
            if (HSSCStrings.TYPE_FIELD.equals(field.getName())) {
                card.setType(field.getText());
            }
            if (HSSCStrings.TEXT_FIELD.equals(field.getName())) {
                card.setText(field.getAtom());
            }
            if (HSSCStrings.FLAVOR_FIELD.equals(field.getName())) {
                card.setFlavor(field.getAtom());
            }
            if (HSSCStrings.ARTIST_FIELD.equals(field.getName())) {
                card.setArtist(field.getAtom());
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
            if (HSSCStrings.EXPANSION_FIELD.equals(field.getName())) {
                card.setExpansionPack(field.getText());
            }
            if (HSSCStrings.MECHANICS_FIELD.equals(field.getName())) {
                card.setMechanics(field.getText().split("\\|"));
            }
            if(HSSCStrings.LANG_FIELD.equals(field.getName())) {
                card.setLanguage(field.getText());
            }

        }

        return card;
    }
}
