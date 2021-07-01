package de.btu.swp.dominion.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import de.btu.swp.dominion.cards.*;
import de.btu.swp.dominion.network.Packets.*;
import de.btu.swp.dominion.gameLogic.PlayerService;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ArrayList;

public class ServerProgram {

	private LinkedList<Connection> players;
	private ArrayList<Cards> cardpool;
	private Server server;
	private ServerNetworkListener serverListener;
	private int ServerLimit = 0;

	// constructor
	public ServerProgram(int limit, PlayerService playerS) {
		this.server = new Server();
		// bind the ports open Server with the port
		try {
			this.server.bind(54555, 54777);
		} catch (IOException e) {
			System.out.println("err Serverprogram 01: Fehler bei öffnen eines Server mit 54555");
		}
		this.serverListener = new ServerNetworkListener(limit, playerS);
		this.server.addListener(serverListener);
		registerPackets();
		this.players = new LinkedList<Connection>();
		this.cardpool = new ArrayList<Cards>();
		this.ServerLimit = limit;
	}

	// getter and setter
	public Server getServer() {
		return this.server;
	}

	public int getServerLimit() {
		return this.ServerLimit;
	}

	public void setPlayers(LinkedList<Connection> list) {
		this.players = list;
	}

	public LinkedList<Connection> getPlayers() {
		return this.players;
	}

	public void setCardspool(ArrayList<Cards> cardpool) {
		this.cardpool = cardpool;
	}

	public ArrayList<Cards> getCardpool() {
		return this.cardpool;
	}

	private void registerPackets() {
		Kryo kryo = server.getKryo();
		kryo.register(Packet01Message.class);
		kryo.register(Packet00Request.class);
		kryo.register(Packet02Answer.class);
		kryo.register(Packet03GameLaunch.class);
		kryo.register(Packet04NextPlayer.class);
		kryo.register(Packet05Players.class);
		kryo.register(Packet06EndTurn.class);
		kryo.register(Packet07Card.class);
		kryo.register(Packet08DiscardDeck.class);
		kryo.register(Packet09HandCardNumber.class);
		kryo.register(Packet10RenderSpecator.class);
		kryo.register(Packet11MilizEffekt.class);
		kryo.register(Packet10HexeEffect.class);
		kryo.register(Packet12GameEnd.class);
		kryo.register(Packet13Points.class);
		kryo.register(Packet14Card2.class);
		kryo.register(Packet15log.class);
		kryo.register(Packet16ReadyCheck.class);
		kryo.register(Packet17RatsEffect.class);
		kryo.register(Packet19TurnZeiger.class);
		kryo.register(Packet20GlobalTrash.class);
		kryo.register(Packet21FirstDeckCard.class);
		kryo.register(Packet22SecondDeckCard.class);
		kryo.register(Packet23DiebEffekt.class);
		kryo.register(Packet26DeleteFirstDeck.class);
		kryo.register(Packet27DeleteSecondDeck.class);
	}
}