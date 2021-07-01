package de.btu.swp.dominion.network;

public class Packets {

	/** get request of a message */
	public static class Packet00Request {
	}

	/** send and receive a message */
	public static class Packet01Message {
		public String message;
		public String clientname;
		public int stage; // 1 == lobby , 2 == game
	}

	/** answer for request */
	public static class Packet02Answer {
		boolean accepted = false;
	}

	/** Host set this to true if he launched the game in lobby */
	public static class Packet03GameLaunch {
		public Boolean start;
	}

	/** the next player get this packet and start his turn */
	public static class Packet04NextPlayer {
		public String playerN;
		public Boolean turn;
	}

	public static class Packet05Players {
		public String player;
	}

	/**
	 * this packet will be send to the Host to say that the current player end his
	 * turn
	 */
	public static class Packet06EndTurn {
		public String name;
	}

	/** Karten von Pool zu anderen Spieler schicken */
	public static class Packet07Card {
		public String card;
	}

	/** first Card of the discard deck send to all Player */
	public static class Packet08DiscardDeck {
		public String cardName;
		public String playerName;
	}

	/** Number of Card in Hand of each Player will send to all Players */
	public static class Packet09HandCardNumber {
		public int NumberOfCardInHand;
		public String playerName;
	}

	public static class Packet10RenderSpecator {
		public String cardName;
	}

	public static class Packet11MilizEffekt {
		public String playPlayer;
	}

	public static class Packet10HexeEffect {
		public String ownerName; // the person who played the Card
	}

	public static class Packet12GameEnd {
		public Boolean end;
	}

	public static class Packet13Points {
		public int points;
		public String name;
	}

	public static class Packet14Card2 {
		public String name;
	}

	/** log message */
	public static class Packet15log {
		public String logMessage;
	}

	public static class Packet16ReadyCheck {
		public String playerName;
		public Boolean ready;
	}

	public static class Packet17RatsEffect {
		public String name;
	}

	public static class Packet19TurnZeiger {
		public String playerName;
	}
	
	public static class Packet20GlobalTrash {
		public String cardName;
	}

	public static class Packet21FirstDeckCard {
		public String cardname;
		public String playername;
	}

	public static class Packet22SecondDeckCard {
		public String cardname;
		public String playername;
	}

	public static class Packet23DiebEffekt {
		public String playername;
	}

	public static class Packet26DeleteFirstDeck {
		public String cardname;
		public String playername;
	}

	public static class Packet27DeleteSecondDeck {
		public String cardname;
		public String playername;
	}
}