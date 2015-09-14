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
	private Thread tC;
	private Thread tP;

	public groundControlProtocol(Client client, groundControlGame groundControlGame) 
	{
		super(client);
		tC = new Thread(client);
		tC.start();
		tP = new Thread(this);
		tP.start();
		iaddress = client.getAddress();
		port = client.getPort();	
		gcGame = groundControlGame;
		this.client= client;
		client.sendMessage("set job GroundControl");
	}
	Client getClient()
	{
		return client;
	}
	public void process(String stringIn) 
	{
		System.out.println(stringIn);
	}
}
