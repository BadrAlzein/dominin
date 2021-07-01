package de.btu.swp.dominion.gameLogic;

import de.btu.swp.dominion.cards.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Player {

	private String playerName;
	private Boolean host;
	private Boolean ready; // ready to join the game after pressing ready in Lobby
	private Boolean end = false;
	private Boolean yourTurn = false;

	private int numberOfCardsInHand;
	private int buys;
	private int action;
	private int money;
	private int points;
	private boolean shutDownServer = false;
	/** kartenpool wird vom Host gewählt, liegen im Vorrat und von der wird gekauft */
	private ArrayList<Cards> cardpool = new ArrayList<>();
	private LinkedList<Cards> deck = new LinkedList<>();
	private LinkedList<Cards> hand = new LinkedList<>();
	private LinkedList<Cards> discardDeck = new LinkedList<>();
	private LinkedList<Cards> playedCards = new LinkedList<>();
	private LinkedList<Cards> trashCards = new LinkedList<>();
	private ArrayList<Cards> specCards = new ArrayList<>();
	private ArrayList<Cards> kapelleCards = new ArrayList<>();
	private Cards test = new Gold();
	private ArrayList<Cards> diebCards = new ArrayList<Cards>() {
		{
		add(test);
		}};
	private LinkedList<Integer> cardnumber = new LinkedList<Integer>() {
		{
			add(10); // pool 1
			add(10);
			add(10);
			add(10);
			add(10);
			add(10);
			add(10);
			add(10);
			add(10);
			add(10); // pool 10
			add(8); // anwesen
			add(8); // herzogtum
			add(8); // provinz
			add(30); // gold
			add(40); // silber
			add(46); // kupfer
		}
	};

	Player(String playerName, Boolean host) {
		this.playerName = playerName;
		this.host = host;
		this.action = 0;
		this.money = 0;
		this.buys = 0;
		this.ready = false;
	}

	Player() {
		this.action = 0;
		this.money = 0;
		this.buys = 0;
		this.ready = false;
	}

	public ArrayList<Cards> getSpecCards() {
		return this.specCards;
	}

	public ArrayList<Cards> getKapelleCards() {
		return this.kapelleCards;
	}

	public void setKapelleCards(ArrayList<Cards> kapelleCards) {
		this.kapelleCards = kapelleCards;
	}

	public ArrayList<Cards> getDiebCards() {
		return this.diebCards;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public void setPlayerName(String playername) {
		this.playerName = playername;
	}

	public Boolean getHost() {
		return this.host;
	}

	public void setHost(Boolean host) {
		this.host = host;
	}

	public Boolean getReady() {
		return this.ready;
	}

	public void setReady(Boolean ready) {
		this.ready = ready;
	}

	public Boolean getEnd() {
		return this.end;
	}

	public void setEnd(Boolean gameend) {
		this.end = gameend;
	}

	public Boolean getYourTurn() {
		return this.yourTurn;
	}

	public void setYourTurn(Boolean yourTurn) {
		this.yourTurn = yourTurn;
	}

	public int getNumberOfCardsInHand() {
		return this.numberOfCardsInHand;
	}

	public void setNumberOfCardsInHand(int NumberOfCardsInHand) {
		this.numberOfCardsInHand = NumberOfCardsInHand;
	}

	public int getBuys() {
		return this.buys;
	}

	public void setBuys(int buys) {
		this.buys = this.buys + buys;
	}

	public int getAction() {
		return this.action;
	}

	public void setAction(int action) {
		this.action = this.action + action;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int Points) {
		this.points = this.points + Points;
	}

	public int getMoney() {
		return this.money;
	}

	/** add the Money */
	public void setMoney(int money) {
		this.money = this.money + money;
	}

	public ArrayList<Cards> getCardspool() {
		return this.cardpool;
	}

	public void setCardspool(ArrayList<Cards> cardspool) {
		this.cardpool = cardspool;
	}

	public LinkedList<Cards> getDeck() {
		return this.deck;
	}

	public void setRightList(LinkedList<Cards> rightList) {
		this.deck = rightList;
		Collections.shuffle(this.deck);
	}

	public LinkedList<Cards> getHand() {
		return this.hand;
	}

	public void setHand(LinkedList<Cards> hand1) {
		this.hand = hand1;
	}

	public LinkedList<Cards> getDiscardDeck() {
		return this.discardDeck;
	}

	public void setDiscardDeck(LinkedList<Cards> discarddeck) {
		this.discardDeck = discarddeck;
	}

	public void addDiscardDeck(LinkedList<Cards> discarddeck) {
		this.discardDeck.addAll(discarddeck);
	}

	public LinkedList<Cards> getPlayedCardsList() {
		return this.playedCards;
	}

	public void setPlayedCardsList(LinkedList<Cards> playedCards) {
		this.playedCards = playedCards;
	}

	public LinkedList<Cards> getTrashCardsList() {
		return this.trashCards;
	}

	public void setTrashCardsList(LinkedList<Cards> trashCards) {
		this.trashCards = trashCards;
	}

	public LinkedList<Integer> getCardnumber() {
		return this.cardnumber;
	}

	// TODO maybe auslagern in Datenklasse Karten des Spielers
	public String getCardName(int i) {
		return this.hand.get(i).getName();
	}

	public String getCardNamePlayed(int i) {
		return this.playedCards.get(i).getName();
	}

	public String getCardImage(int i) {
		return this.hand.get(i).getImagePath();
	}

	public String getPoolImage(int i) {
		return this.cardpool.get(i).getImagePath();
	}

	public String getPlayedCardImage(int i) {
		return this.playedCards.get(i).getImagePath();
	}

	public String getDiscardCardImage(int i) {
		return this.discardDeck.get(i).getImagePath();
	}

	public String getCardpoolImage(int i) {
		return this.cardpool.get(i).getImagePath();
	}

	public String getTrashCardImage(int i) {
		return this.trashCards.get(i).getImagePath();
	}

	public void getStartValue() {
		this.action = 1;
		this.buys = 1;
		this.money = 0;
	}

	/** used to shut down the server and kick all players */
	public Boolean getShutDownServer() {
		return this.shutDownServer;
	}

	public void setShutDownServer(Boolean control) {
		this.shutDownServer = control;
	}

	public void setDiebCard(Cards card) {
		diebCards.set(0, card);
	}

}