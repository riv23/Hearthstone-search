package com.geminicode.hssc.utils;

import com.google.appengine.api.search.Field;
import com.google.appengine.repackaged.com.google.api.client.util.Lists;

import java.util.List;

public class TestUtils {

    public static Iterable<Field> buildFieldsOdJenkins() {
        final List<Field> fields = Lists.newArrayList();

        fields.add(buildTextField(FieldString.NAME_FIELD, "Leeroy Jenkins"));
        fields.add(buildAtomField(FieldString.IMAGE_FIELD, "http://wow.zamimg.com/images/hearthstone/cards/frfr/original/EX1_116.png"));
        fields.add(buildTextField(FieldString.TYPE_FIELD, "Minion"));
        fields.add(buildAtomField(FieldString.TEXT_FIELD, "Powerful card"));
        fields.add(buildAtomField(FieldString.FLAVOR_FIELD, "Flavor"));
        fields.add(buildAtomField(FieldString.ARTIST_FIELD, "GeminiCOde"));
        fields.add(buildTextField(FieldString.PLAYER_CLASS_FIELD, "All"));
        fields.add(buildTextField(FieldString.FACTION_FIELD, "All"));
        fields.add(buildTextField(FieldString.RARITY_FIELD, "Legendary"));
        fields.add(buildNumberField(FieldString.COST_FIELD, 7d));
        fields.add(buildTextField(FieldString.ATTACK_FIELD, "6"));
        fields.add(buildTextField(FieldString.HEALTH_FIELD, "2"));
        fields.add(buildTextField(FieldString.COLLECTIBLE_FIELD, "true"));
        fields.add(buildTextField(FieldString.RACE_FIELD, "Human"));
        fields.add(buildTextField(FieldString.EXPANSION_FIELD, "Basic"));
        fields.add(buildTextField(FieldString.MECHANICS_FIELD, "Charge|Taunt"));
        fields.add(buildAtomField(FieldString.LANG_FIELD, "en"));

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
