package com.geminicode.hssc.utils;

import com.google.appengine.api.search.Field;
import com.google.appengine.repackaged.com.google.api.client.util.Lists;

import java.util.List;

public class TestUtils {

    public static Iterable<Field> buildFieldsOdJenkins() {
        final List<Field> fields = Lists.newArrayList();

        fields.add(buildTextField(HSSCStrings.NAME_FIELD, "Leeroy Jenkins"));
        fields.add(buildAtomField(HSSCStrings.IMAGE_FIELD, "http://wow.zamimg.com/images/hearthstone/cards/frfr/original/EX1_116.png"));
        fields.add(buildTextField(HSSCStrings.TYPE_FIELD, "Minion"));
        fields.add(buildAtomField(HSSCStrings.TEXT_FIELD, "Powerful card"));
        fields.add(buildAtomField(HSSCStrings.FLAVOR_FIELD, "Flavor"));
        fields.add(buildAtomField(HSSCStrings.ARTIST_FIELD, "GeminiCOde"));
        fields.add(buildTextField(HSSCStrings.PLAYER_CLASS_FIELD, "All"));
        fields.add(buildTextField(HSSCStrings.FACTION_FIELD, "All"));
        fields.add(buildTextField(HSSCStrings.RARITY_FIELD, "Legendary"));
        fields.add(buildNumberField(HSSCStrings.COST_FIELD, 7d));
        fields.add(buildTextField(HSSCStrings.ATTACK_FIELD, "6"));
        fields.add(buildTextField(HSSCStrings.HEALTH_FIELD, "2"));
        fields.add(buildTextField(HSSCStrings.COLLECTIBLE_FIELD, "true"));
        fields.add(buildTextField(HSSCStrings.RACE_FIELD, "Human"));
        fields.add(buildTextField(HSSCStrings.EXPANSION_FIELD, "Basic"));
        fields.add(buildTextField(HSSCStrings.MECHANICS_FIELD, "Charge|Taunt"));
        fields.add(buildAtomField(HSSCStrings.LANG_FIELD, "en"));

        return fields;
    }

    private static Field buildTextField(String name, String value) {
        final Field.Builder builder = Field.newBuilder();
        builder.setName(name);
        builder.setText(value);
        return builder.build();
    }

    private static Field buildAtomField(String name, String value) {
        final Field.Builder builder = Field.newBuilder();
        builder.setName(name);
        builder.setAtom(value);
        return builder.build();
    }

    private static Field buildNumberField(String name, Double value) {
        final Field.Builder builder = Field.newBuilder();
        builder.setName(name);
        builder.setNumber(value);
        return builder.build();
    }
}
