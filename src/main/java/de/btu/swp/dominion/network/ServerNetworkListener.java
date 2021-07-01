package de.btu.swp.dominion.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.btu.swp.dominion.gameLogic.PlayerService;
import de.btu.swp.dominion.network.Packets.*;

public class ServerNetworkListener extends Listener {
	private int playersLimit;
	private int numberOfConnectedPLayers;
	private PlayerService playerService;

	public ServerNetworkListener(int playersLimit, PlayerService playerS) {
		this.numberOfConnectedPLayers = 0;
		this.playersLimit = playersLimit;
		this.playerService = playerS;
	}

	@Override
	public void connected(Connection player) {
		// refuse entry if the party Completed
		if (numberOfConnectedPLayers >= playersLimit) {
			player.close();
		} else {
			numberOfConnectedPLayers++;
			System.out.print(player.getRemoteAddressTCP() + " is connected" + "\n");
			playerService.getServerServices().getAllPlayers().add(player);
		}
	}

	@Override
	public void disconnected(Connection c) {
		numberOfConnectedPLayers--;

		System.out.print(c.getRemoteAddressTCP() + " signed off " + "\n");
		// if the Host is disconnected then kick all the players
		if (playerService.getPlayer().getHost() && playerService.getPlayer().getShutDownServer()) {
			playerService.getPlayer().setShutDownServer(false);
			for (Connection player : playerService.getServerServices().getAllPlayers()) {
				player.close();
			}
		}
	}

	@Override
	public void received(Connection c, Object o) {
		if (o instanceof Packet01Message) {
			Packet01Message MessagePacket = (Packet01Message) o;
			playerService.getServerServices()
					.sendMessage("[" + MessagePacket.clientname + "]: " + MessagePacket.message, MessagePacket.stage);
		}

		if (o instanceof Packet03GameLaunch) {
			Packet03GameLaunch startPacket = (Packet03GameLaunch) o;
			playerService.getServerServices().sendReady(startPacket);
		}

		if (o instanceof Packet04NextPlayer) {
			Packet04NextPlayer turnPacket = (Packet04NextPlayer) o;
			playerService.getServerServices().sendTurn(turnPacket);
		}

		if (o instanceof Packet05Players) {
			Packet05Players pn = (Packet05Players) o;
			playerService.getServerServices().setName(pn);
		}

		if (o instanceof Packet06EndTurn) {
			Packet06EndTurn player = (Packet06EndTurn) o;
			playerService.getServerServices().sendEnd(player);
		}

		if (o instanceof Packet08DiscardDeck) {
			Packet08DiscardDeck playerCard = (Packet08DiscardDeck) o;
			playerService.getServerServices().sendFirstCard(playerCard);
		}

		if (o instanceof Packet09HandCardNumber) {
			Packet09HandCardNumber numberOfCards = (Packet09HandCardNumber) o;
			playerService.getServerServices().sendNumberOfCards(numberOfCards);
		}

		if (o instanceof Packet10RenderSpecator) {
			Packet10RenderSpecator zug = (Packet10RenderSpecator) o;
			playerService.getServerServices().sendCard(zug);
		}

		if (o instanceof Packet11MilizEffekt) {
			Packet11MilizEffekt effect = (Packet11MilizEffekt) o;
			playerService.getServerServices().sendEffect(effect);
		}

		if (o instanceof Packet10HexeEffect) {
			Packet10HexeEffect hexeCard = (Packet10HexeEffect) o;
			playerService.getServerServices().sendhexeCard(hexeCard);
		}

		if (o instanceof Packet12GameEnd) {
			Packet12GameEnd gameend = (Packet12GameEnd) o;
			playerService.getServerServices().sendEnd(gameend);
		}

		if (o instanceof Packet13Points) {
			Packet13Points points = (Packet13Points) o;
			playerService.getServerServices().sendPoints(points);
		}

		if (o instanceof Packet14Card2) {
			Packet14Card2 boughtCard = (Packet14Card2) o;
			playerService.getServerServices().sendBoughtCard(boughtCard);
		}

		if (o instanceof Packet15log) {
			Packet15log logMessage = (Packet15log) o;
			playerService.getServerServices().sendlog(logMessage);
		}

		if (o instanceof Packet16ReadyCheck) {
			Packet16ReadyCheck check = (Packet16ReadyCheck) o;
			playerService.getServerServices().sendReadyCheck(check);
		}

		if (o instanceof Packet17RatsEffect) {
			Packet17RatsEffect effect = (Packet17RatsEffect) o;
			playerService.getServerServices().sendRatsCard(effect);
		}

		/** zeiger who have the Turn */
		if (o instanceof Packet19TurnZeiger) {
			Packet19TurnZeiger TurnZeiger = (Packet19TurnZeiger) o;
			playerService.getServerServices().sendTurnZeiger(TurnZeiger);
		}
		
		/** send the trashed Card to all Players */
		if (o instanceof Packet20GlobalTrash) {
			Packet20GlobalTrash globalTrash = (Packet20GlobalTrash) o;
			playerService.getServerServices().sendglobalTrash(globalTrash);
		}

		if (o instanceof Packet21FirstDeckCard) {
			Packet21FirstDeckCard card = (Packet21FirstDeckCard) o;
			playerService.getServerServices().sendFirstDeckCard(card);
		}

		if (o instanceof Packet22SecondDeckCard) {
			Packet22SecondDeckCard card = (Packet22SecondDeckCard) o;
			playerService.getServerServices().sendSecondDeckCard(card);
		}

		if (o instanceof Packet23DiebEffekt) {
			Packet23DiebEffekt effekt = (Packet23DiebEffekt) o;
			playerService.getServerServices().sendDiebeffekt(effekt);
		}

		if (o instanceof Packet26DeleteFirstDeck) {
			Packet26DeleteFirstDeck deletcard = (Packet26DeleteFirstDeck) o;
			playerService.getServerServices().sendDeleteCardFirst(deletcard);
		}

		if (o instanceof Packet27DeleteSecondDeck) {
			Packet27DeleteSecondDeck deletcard = (Packet27DeleteSecondDeck) o;
			playerService.getServerServices().sendDeleteCardSecond(deletcard);
		}
	}
}