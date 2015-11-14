package com.geminicode.hssc.model;


public enum TypesEnum {

	CLASSIC("Classic"),
	BASIC("Bassic"),
	CURSE_OF_NAXXRAMAS("Curse of Naxxramas"),
	GOBLINS_VS_GNOMES("Goblins vs Gnomes"),
	PROMOTION("Promotion"),
	BLACKROCK_MOUNTAIN("Blackrock Mountain"),
	GRAND_TOURNAMENT("The Grand Tournament"),
	LEAGUE_OF_EXPLORERS("League of Explorers");

	private final String type;

	TypesEnum(String type) {
		this.type = type;
	}

	public String getName() {
		return this.type;
	}

}
