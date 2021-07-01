package de.btu.swp.dominion.gameLogic;

import de.btu.swp.dominion.cards.*;
import de.btu.swp.dominion.gameLogic.Trigger;
import de.btu.swp.dominion.network.*;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class PlayerService {
	private Player player = null;
	private LinkedList<OtherPlayers> players = new LinkedList<>();
	private MPClient client;
	private ServerServices services;
	private Trigger trigger;

	/**
	 * constructor + give the playername and Host status
	 */
	public PlayerService(String PlayerName, Boolean Host) {
		this.player = new Player(PlayerName, Host);
		this.trigger = new Trigger();
		this.players = new LinkedList<OtherPlayers>();
	}

	public PlayerService() {
		this.player = new Player();
		this.trigger = new Trigger();
		this.players = new LinkedList<OtherPlayers>();
	}

	/**
	 * use to get Or set things into Class Player
	 */
	public Player getPlayer() {
		return this.player;
	}

	/** get all other Players connected */
	public LinkedList<OtherPlayers> getPlayers() {
		return this.players;
	}

	/**
	 * the player will connect to the Server as a Client
	 */
	public void initConnection() {
		client = new MPClient(this);
	}

	/**
	 * define the Server with the Players limit :: Used in the Haupt Menu Mussed be
	 * used once
	 *
	 * @throws IOException
	 */
	public void initServer(int PlayerLimit) throws IOException {
		services = new ServerServices(PlayerLimit, this);
		services.startServer();
	}

	/**
	 * get the Object from the ServerServices
	 *
	 * @return
	 */
	public ServerServices getServerServices() {
		return services;
	}

	/**
	 * get an Object from Class MPClient used to Send Objects To the Server
	 */
	public MPClient getClient() {
		return client;
	}

	/**
	 * Ziel: show a zeiger if a set of Cards are Chosen for single & multiplayer
	 *
	 * @return
	 */
	public Trigger getTrigger() {
		return this.trigger;
	}

	public void setPlayer(Player newplayer) {
		this.player = newplayer;
	}

	public void drawCard() {
		if (player.getDeck().size() == 0) {
			while (player.getDiscardDeck().size() > 0) {
				player.getDeck().add(player.getDiscardDeck().get(0));
				player.getDiscardDeck().remove(0);
			}
			Collections.shuffle(player.getDeck());
		}

		if (player.getDeck().size() > 0) {
			// card instanceof Money
			if (player.getDeck().get(0).getCardType().equals("Geld")) {
				player.setMoney(player.getDeck().get(0).getPoints());
			}
			player.getHand().add(player.getDeck().get(0));
			player.getDeck().remove(0);
		}
	}

	public Boolean isDefendsOn() {
		for (Cards card : getPlayer().getHand()) {
			if (card.getName().equals("Burggraben")) {
				return true;
			}
		}
		return false;
	}

	public Boolean allReady() {
		if (services.getServerProgram().getServerLimit() - 1 == getPlayers().size()) {
			for (int i = 0; i < getPlayers().size(); i++) {
				if (!getPlayers().get(i).getReady()) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public void clearAllList() {
		String name = player.getPlayerName();
		Boolean host = player.getHost();
		setPlayer(null);
		Player player = new Player(name, host);
		setPlayer(player);
	}

	public Cards stringtoCards(String name) {
		Cards card;
		switch (name) {
		case "Dorf":
			card = new Dorf();
			return card;
		case "Schmiede":
			card = new Schmiede();
			return card;
		case "Holzfaeller":
			card = new Holzfaeller();
			return card;
		case "Laboratorium":
			card = new Laboratorium();
			return card;
		case "Kanzler":
			card = new Kanzler();
			return card;
		case "Abenteurer":
			card = new Abenteurer();
			return card;
		case "Bibliothek":
			card = new Bibliothek();
			return card;
		case "Kupfer":
			card = new Kupfer();
			return card;
		case "Silber":
			card = new Silber();
			return card;
		case "Gold":
			card = new Gold();
			return card;
		case "Anwesen":
			card = new Anwesen();
			return card;
		case "Herzogtum":
			card = new Herzogtum();
			return card;
		case "Provinz":
			card = new Provinz();
			return card;
		case "Blanko":
			card = new Blanko();
			return card;
		case "Jahrmarkt":
			card = new Jahrmarkt();
			return card;
		case "Markt":
			card = new Markt();
			return card;
		case "Kapelle":
			card = new Kapelle();
			return card;
		case "Miliz":
			card = new Miliz();
			return card;
		case "Burggraben":
			card = new Burggraben();
			return card;
		case "Thronsaal":
			card = new Thronsaal();
			return card;
		case "Hexe":
			card = new Hexe();
			return card;
		case "Fluch":
			card = new Fluch();
			return card;
		case "Ratsversammlung" :
			card = new Ratsversammlung();
			return card;
		case "Dieb" :
			card = new Dieb();
		case "Werkstatt":
			card = new Werkstatt();
			return card;
		case "Umbau":
			card = new Umbau();
			return card;
		case "Gaerten":
			card = new Gaerten();
			return card;
		default:
			card = new Dorf();
			return card;
		}
	}
}
