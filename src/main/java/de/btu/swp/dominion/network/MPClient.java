package de.btu.swp.dominion.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import de.btu.swp.dominion.gameLogic.PlayerService;
import de.btu.swp.dominion.network.Packets.*;
import java.io.IOException;
import java.net.InetAddress;

public class MPClient {

	// Kryonet
	private Client client;
	private ClientNetworkListener clientListener;

	public MPClient(PlayerService player) {
		this.client = new Client();
		this.clientListener = new ClientNetworkListener();
		clientListener.init(client, player);

		// add packets
		registerPackets();
		// add listener to client
		client.addListener(clientListener);

		// start Client
		new Thread(client).start();

		// connect
		try {
			InetAddress host = client.discoverHost(54777, 5000);
			client.connect(5000, host, 54555, 54777);
		} catch (IOException e) {
			System.out.println("err Client 01: failed to Connect!!");
		}
	}

	public Client getClient() {
		return this.client;
	}

	private void registerPackets() {
		Kryo kryo = client.getKryo();
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