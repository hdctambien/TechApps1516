package spacegame.groundcontrol;

import java.net.InetAddress;

import spacegame.client.AbstractProtocol;
import spacegame.client.Client;

public class groundControlProtocol extends AbstractProtocol
{
	private InetAddress iaddress;
	private int port;
	private groundControlGame gcGame;
	private Client client;

	public groundControlProtocol(Client client, groundControlGame groundControlGame) 
	{
		super(client);
		iaddress = client.getAddress();
		port = client.getPort();	
		gcGame = groundControlGame;
		this.client= client;
	}
	public void process(String stringIn) 
	{
		gcGame.processTextMessage(stringIn);
		client.sendMessage("Displaying " + stringIn);
	}

}
