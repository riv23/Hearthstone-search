package com.geminicode.hssc.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This class represents a card type. It's used by the JSON reader : CardReader.java
 */
public class CardType {

	@SerializedName("Basic")
	private List<Card> Basic;
	@SerializedName("Classic")
	private List<Card> Classic;
	@SerializedName("Curse of Naxxramas")
	private List<Card> CurseOfNaxxramas;
	@SerializedName("Goblins vs Gnomes")
	private List<Card> GobelinsVsGnomes;
	@SerializedName("Promotion")
	private List<Card> promotions;
	@SerializedName("Blackrock Mountain")
	private List<Card> blackrockMountain;
	@SerializedName("The Grand Tournament")
	private List<Card> grandTournament;

	public List<Card> getBasic() {
		return Basic;
	}

	public void setBasic(List<Card> basic) {
		Basic = basic;
	}

	public List<Card> getClassic() {
		return Classic;
	}

	public void setClassic(List<Card> classic) {
		Classic = classic;
	}

	public List<Card> getCurseOfNaxxramas() {
		return CurseOfNaxxramas;
	}

	public void setCurseOfNaxxramas(List<Card> curseOfNaxxramas) {
		CurseOfNaxxramas = curseOfNaxxramas;
	}

	public List<Card> getGobelinsVsGnomes() {
		return GobelinsVsGnomes;
	}

	public void setGobelinsVsGnomes(List<Card> gobelinsVsGnomes) {
		GobelinsVsGnomes = gobelinsVsGnomes;
	}

	public List<Card> getPromotions() {
		return promotions;
	}

	public void setPromotions(List<Card> promotions) {
		this.promotions = promotions;
	}

	public List<Card> getBlackrockMountain() {
		return blackrockMountain;
	}

	public void setBlackrockMountain(List<Card> blackrockMountain) {
		this.blackrockMountain = blackrockMountain;
	}

	public List<Card> getGrandTournament() {
		return grandTournament;
	}

	public void setGrandTournament(List<Card> grandTournament) {
		this.grandTournament = grandTournament;
	}
}
