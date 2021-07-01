package de.btu.swp.dominion.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.btu.swp.dominion.cards.*;
import de.btu.swp.dominion.game.ChatCon;
import de.btu.swp.dominion.game.GameCon;
import de.btu.swp.dominion.game.LobbyCon;
import de.btu.swp.dominion.gameLogic.*;
import de.btu.swp.dominion.network.Packets.*;
import java.util.Collections;

public class ClientNetworkListener extends Listener {
	private Client client;
	private PlayerService playerService;

	public void init(Client client, PlayerService player) {
		this.client = client;
		this.playerService = player;
	}

	@Override
	public void connected(Connection c) {
		Packet00Request ClientMessage = new Packet00Request();
		client.sendTCP(ClientMessage);
		Packet01Message test = new Packet01Message();
		test.clientname = playerService.getPlayer().getPlayerName();
		test.message = (" ist beigetreten!");
		client.sendTCP(test);
	}

	@Override
	public void disconnected(Connection c) {
		System.out.print("[" + playerService.getPlayer().getPlayerName() + "]: " + "I logged out ");
		// exit if you lose connection
		// Launcher restart = new Launcher();
		// Platform.runLater(() -> restart.start(new Stage()));
	}

	public Boolean isPlayerinList(String name) {
		for (int i = 0; i < playerService.getPlayers().size(); i++) {
			OtherPlayers others = playerService.getPlayers().get(i);
			if (others.getPlayerName().equals(name)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void received(Connection c, Object o) {

		// reciving the message from server
		if (o instanceof Packet01Message) {
			Packet01Message MessagePacket = (Packet01Message) o;
			if (MessagePacket.stage == 1) {
				LobbyCon lobby = new LobbyCon();
				lobby.addTextToList(MessagePacket.message);
			}
			if (MessagePacket.stage == 2) {
				ChatCon chat = new ChatCon();
				chat.addTextToList(MessagePacket.message);
			}

		} else if (o instanceof Packet03GameLaunch) {
			// reciving the message from server
			Packet03GameLaunch StartPacket = (Packet03GameLaunch) o;
			playerService.getPlayer().setReady(StartPacket.start);

		} else if (o instanceof Packet04NextPlayer) {
			Packet04NextPlayer TurnPacket = (Packet04NextPlayer) o;

			if (TurnPacket.playerN.equals(playerService.getPlayer().getPlayerName())) {
				playerService.getPlayer().setYourTurn(true);

				/** show how have the turn */
				Packet19TurnZeiger turnZeiger = new Packet19TurnZeiger();
				turnZeiger.playerName = playerService.getPlayer().getPlayerName();
				playerService.getClient().getClient().sendTCP(turnZeiger);
			}

		} else if (o instanceof Packet05Players) {
			Packet05Players player = (Packet05Players) o;

			if (!isPlayerinList(player.player) && !playerService.getPlayer().getPlayerName().equals(player.player)) {
				OtherPlayers other = new OtherPlayers(player.player);
				playerService.getPlayers().add(other);
			}

		} else if (o instanceof Packet06EndTurn) {
			Packet06EndTurn player = (Packet06EndTurn) o;

			if (playerService.getPlayer().getHost() == true) {
				GameCon.changeTurn(player.name, playerService);
			}
		} else if (o instanceof Packet07Card) {
			Packet07Card pool = (Packet07Card) o;

			if (playerService.getPlayer().getCardspool().size() < 10) {
				playerService.getPlayer().getCardspool().add(playerService.stringtoCards(pool.card));
			}
		} else if (o instanceof Packet08DiscardDeck) {
			Packet08DiscardDeck firstCard = (Packet08DiscardDeck) o;
			for (int i = 0; i < playerService.getPlayers().size(); i++) {
				if (playerService.getPlayers().get(i).getPlayerName().equals(firstCard.playerName)) {
					playerService.getPlayers().get(i).setFirstDiscardCard(firstCard.cardName);
				}
			}

		} else if (o instanceof Packet09HandCardNumber) {
			Packet09HandCardNumber handCard = (Packet09HandCardNumber) o;
			for (int i = 0; i < playerService.getPlayers().size(); i++) {
				OtherPlayers others = playerService.getPlayers().get(i);
				if (others.getPlayerName().equals(handCard.playerName)) {
					others.setNumberCardsInHand(handCard.NumberOfCardInHand);
				}
			}

		} else if (o instanceof Packet10RenderSpecator) {
			Packet10RenderSpecator zug = (Packet10RenderSpecator) o;
			playerService.getPlayer().getSpecCards().add(playerService.stringtoCards(zug.cardName));

		} else if (o instanceof Packet11MilizEffekt) {
			Packet11MilizEffekt effect = (Packet11MilizEffekt) o;
			if (!effect.playPlayer.equals(playerService.getPlayer().getPlayerName())) {
				if (playerService.getPlayer().getHand().size() > 3 && !playerService.isDefendsOn()) {
					GameLogic.setMilizActive(true);
				}
			}
		} else if (o instanceof Packet10HexeEffect) {
			Packet10HexeEffect hexePacket = (Packet10HexeEffect) o;
			if (!hexePacket.ownerName.equals(playerService.getPlayer().getPlayerName())
					&& !playerService.isDefendsOn()) {
				Cards fluch = new Fluch();
				playerService.getPlayer().getDiscardDeck().add(fluch);
			}
		} else if (o instanceof Packet12GameEnd) {
			Packet12GameEnd gameend = (Packet12GameEnd) o;
			playerService.getPlayer().setEnd(gameend.end);
			GuiDesigner gameEndSound = new GuiDesigner();
			MetaData metaData = new MetaData();
			if (metaData.getSoundOnMeta()) {
				gameEndSound.getGameEndAudio().play();
			}

		} else if (o instanceof Packet13Points) {
			Packet13Points points = (Packet13Points) o;
			for (int i = 0; i < playerService.getPlayers().size(); i++) {
				if (!playerService.getPlayers().get(i).getPlayerName().equals(points.name)) {
					playerService.getPlayers().get(i).setPoints(points.points);
				}
			}

		} else if (o instanceof Packet14Card2) {
			Packet14Card2 card = (Packet14Card2) o;
			GameLogic.changeNumb(card.name, playerService);

		} else if (o instanceof Packet16ReadyCheck) {
			Packet16ReadyCheck check = (Packet16ReadyCheck) o;
			for (int i = 0; i < playerService.getPlayers().size(); i++) {
				if (playerService.getPlayers().get(i).getPlayerName().equals(check.playerName)) {
					playerService.getPlayers().get(i).setReady(check.ready);
				}
			}
		} else if (o instanceof Packet15log) {
			Packet15log logMessage = (Packet15log) o;
			GameCon game = new GameCon();
			game.addTextToLogList(logMessage.logMessage);
		} else if (o instanceof Packet17RatsEffect) {
			Packet17RatsEffect effect = (Packet17RatsEffect) o;
			if (!effect.name.equals(playerService.getPlayer().getPlayerName())) {
				playerService.drawCard();
			}
		}

		else if (o instanceof Packet19TurnZeiger) {
			Packet19TurnZeiger turnChack = (Packet19TurnZeiger) o;
			playerService.getTrigger().setCurrentPlayerName(turnChack.playerName);

		} else if (o instanceof Packet21FirstDeckCard) {
			Packet21FirstDeckCard firstCard = (Packet21FirstDeckCard) o;
			for (int i = 0; i < playerService.getPlayers().size(); i++) {
				if (playerService.getPlayers().get(i).getPlayerName().equals(firstCard.playername)) {
					playerService.getPlayers().get(i).setFirstDeckCard(playerService.stringtoCards(firstCard.cardname));
				}
			}
			
		} else if (o instanceof Packet22SecondDeckCard) {
			Packet22SecondDeckCard secondCard = (Packet22SecondDeckCard) o;
			for (int i = 0; i < playerService.getPlayers().size(); i++) {
				if (playerService.getPlayers().get(i).getPlayerName().equals(secondCard.playername)) {
					playerService.getPlayers().get(i).setSecondDeckCard(playerService.stringtoCards(secondCard.cardname));
				}
			}
			
		} else if (o instanceof Packet23DiebEffekt) {
			Packet23DiebEffekt effekt = (Packet23DiebEffekt) o;
			if (!playerService.getPlayer().getPlayerName().equals(effekt.playername) && !playerService.isDefendsOn()) {
				if (playerService.getPlayer().getDeck().size() <= 1) {
					while (playerService.getPlayer().getDiscardDeck().size() > 0) {
						playerService.getPlayer().getDeck().add(playerService.getPlayer().getDiscardDeck().get(0));
						playerService.getPlayer().getDiscardDeck().remove(0);
					}
					Collections.shuffle(playerService.getPlayer().getDeck());
				}
				Packet21FirstDeckCard carddeck = new Packet21FirstDeckCard();
		    	carddeck.cardname = playerService.getPlayer().getDeck().get(0).getName();
		    	carddeck.playername = playerService.getPlayer().getPlayerName();
				playerService.getClient().getClient().sendTCP(carddeck);
				Packet22SecondDeckCard carddeck2 = new Packet22SecondDeckCard();
		    	carddeck2.cardname = playerService.getPlayer().getDeck().get(1).getName();
		    	carddeck2.playername = playerService.getPlayer().getPlayerName();
		    	playerService.getClient().getClient().sendTCP(carddeck2);
			} else {
				Packet21FirstDeckCard carddeck = new Packet21FirstDeckCard();
		    	carddeck.cardname = "Blanko";
		    	carddeck.playername = playerService.getPlayer().getPlayerName();
				playerService.getClient().getClient().sendTCP(carddeck);
				Packet22SecondDeckCard carddeck2 = new Packet22SecondDeckCard();
		    	carddeck2.cardname = "Blanko";
		    	carddeck2.playername = playerService.getPlayer().getPlayerName();
		    	playerService.getClient().getClient().sendTCP(carddeck2);
			}
		} else if (o instanceof Packet26DeleteFirstDeck) {
			Packet26DeleteFirstDeck card = (Packet26DeleteFirstDeck) o;
			if (playerService.getPlayer().getPlayerName().equals(card.playername)) {
				playerService.getPlayer().getDeck().remove(0);
			}
		} else if (o instanceof Packet27DeleteSecondDeck) {
			Packet27DeleteSecondDeck card = (Packet27DeleteSecondDeck) o;
			if (playerService.getPlayer().getPlayerName().equals(card.playername)) {
				if (playerService.getPlayer().getDeck().size() <= 1) {
					while (playerService.getPlayer().getDiscardDeck().size() > 0) {
						playerService.getPlayer().getDeck().add(playerService.getPlayer().getDiscardDeck().get(0));
						playerService.getPlayer().getDiscardDeck().remove(0);
					}
					Collections.shuffle(playerService.getPlayer().getDeck());
				}
				playerService.getPlayer().getDeck().remove(1);
			}
		}

		else if (o instanceof Packet20GlobalTrash) {
			Packet20GlobalTrash trashPacket = (Packet20GlobalTrash) o;
			playerService.getPlayer().getTrashCardsList().add(playerService.stringtoCards(trashPacket.cardName));
		}
	}
}