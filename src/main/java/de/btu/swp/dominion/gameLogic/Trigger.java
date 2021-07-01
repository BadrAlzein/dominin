package de.btu.swp.dominion.gameLogic;

public class Trigger {

	private Boolean triggerCardSets = false;
	private Boolean triggerChosen = false;
	private Boolean triggerRandom = false;
	private Boolean cardsAreSelected = false;
	private Boolean inLobbySettings = false;
	/** true = einzelSpieler, false = mehrSpieler */
	private Boolean source;
	private int state = 0;
	private int cardMenuState = 0;
	/** true if the Host have inserted a Ranking for the Players (Reihenfolge) */
	private Boolean isRankingSorted = false;

	/** used to see who have the turn */
	private String currentPlayerName = " ";

	// getter and setter for Trigger
	public void setTriggerCardSets(Boolean tregerVorgefertigt) {
		this.triggerCardSets = tregerVorgefertigt;
	}

	public Boolean getTriggerCardSets() {
		return this.triggerCardSets;
	}

	public void setTriggerChosen(Boolean tregerSelbstgewaehlt) {
		this.triggerChosen = tregerSelbstgewaehlt;
	}

	public Boolean getTriggerChosen() {
		return this.triggerChosen;
	}

	public void setTriggerRandom(Boolean tregerZufaelig) {
		this.triggerRandom = tregerZufaelig;
	}

	public Boolean getTriggerRandom() {
		return this.triggerRandom;
	}

	public void setCardsAreSelected(Boolean CardsAreSelected) {
		this.cardsAreSelected = CardsAreSelected;
	}

	public Boolean getCardsAreSelected() {
		return this.cardsAreSelected;
	}

	public void setIsInLobbySetting(Boolean InLobbySettings) {
		this.inLobbySettings = InLobbySettings;
	}

	public Boolean getIsInLobbySetting() {
		return this.inLobbySettings;
	}

	public Boolean getSource() {
		return this.source;
	}

	public void setSource(Boolean source) {
		this.source = source;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getCardMenuState() {
		return this.cardMenuState;
	}

	public void setCardMenuState(int cardMenuState) {
		this.cardMenuState = cardMenuState;
	}

	/** reset alle the triggers to false */
	public void reset() {
		this.triggerCardSets = false;
		this.triggerChosen = false;
		this.triggerRandom = false;
		this.cardsAreSelected = false;
	}

	/** if one of the booleans true is then return true */
	public Boolean isTriggered() {
		if (this.triggerCardSets) {
			return true;
		} else if (this.triggerChosen) {
			return true;
		} else if (this.triggerRandom) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean getIsRankingSorted() {
		return this.isRankingSorted;
	}

	public void seIsRankingSorted(Boolean isRankingSorted) {
		this.isRankingSorted = isRankingSorted;
	}

	public String getCurrentPlayerName() {
		return this.currentPlayerName;
	}

	public void setCurrentPlayerName(String currentPlayerName) {
		this.currentPlayerName = currentPlayerName;
	}
}