package de.btu.swp.dominion.gameLogic;

import de.btu.swp.dominion.cards.*;

public class OtherPlayers {
	private String playerName;
	private Boolean host;
	private Boolean ready;
	private int numberCardsInHand;
	private int points;
	private String firstDiscardCard;
	private Cards firstDeckCard;
	private Cards secondDeckCard;
	private Boolean defends = false;

	public OtherPlayers(String name) {
		this.playerName = name;
		ready = false;
		firstDiscardCard = "/src/main/resources/basis/Blanko.png";
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public Boolean getHost() {
		return this.host;
	}

	public Boolean getReady() {
		return this.ready;
	}

	public int getNumberCardsInHand() {
		return this.numberCardsInHand;
	}

	public int getPoints() {
		return this.points;
	}

	public String getFirstDiscardCard() {
		return this.firstDiscardCard;
	}

	public Cards getFirstDeckCard() {
		return this.firstDeckCard;
	}

	public Cards getSecondDeckCard() {
		return this.secondDeckCard;
	}

	public Boolean getDefends() {
		return this.defends;
	}

	public void setPlayerName(String name) {
		this.playerName = name;
	}

	public void setHost(Boolean host) {
		this.host = host;
	}

	public void setReady(Boolean ready) {
		this.ready = ready;
	}

	public void setNumberCardsInHand(int number) {
		this.numberCardsInHand = number;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setFirstDiscardCard(String imagePath) {
		this.firstDiscardCard = imagePath;
	}

	public void setFirstDeckCard(Cards imagePath) {
		this.firstDeckCard = imagePath;
	}

	public void setSecondDeckCard(Cards imagePath) {
		this.secondDeckCard = imagePath;
	}

	public void setDefends(Boolean status) {
		this.defends = status;
	}
}