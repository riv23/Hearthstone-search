package com.geminicode.hssc.model;


public enum TypesEnum {

	CLASSIC("Classic"),
	BASIC("Bassic"),
	CURSE_OF_NAXXRAMAS("Curse of Naxxramas"),
	GOBLINS_VS_GNOMES("Goblins vs Gnomes"),
	PROMOTION("Promotion"),
	BLACKROCK_MOUNTAIN("Blackrock Mountain");

	private final String type;

	TypesEnum(String type) {
		this.type = type;
	}

	public String getName() {
		return this.type;
	}

}
