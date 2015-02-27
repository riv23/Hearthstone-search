package com.geminicode.hssc.model;


public enum TypesEnum {

	CLASSIC("Classic"),
	BASIC("Bassic"),
	CURSE_OF_NAXXRAMAS("Curse of Naxxramas"),
	GOBLINS_VS_GNOMES("Goblins vs Gnomes"),
	PROMOTION("Promotion");

	private String type;

	private TypesEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

}
