package spacegame.communications;

import java.net.InetAddress;

import spacegame.client.*;


public class CommProtocol extends AbstractProtocol
{

	private InetAddress iaddress;
	private int port;
	private CommGame cGame;
	private CommGUI gui;
	private Client client;
	private Thread tClient;
	private Thread tP;
	
	public CommProtocol(Client client, CommGame engiGame, CommGUI gui)
	{
		super(client);
		tClient = new Thread(client);
		tClient.start();
		tP = new Thread(this);
		tP.start();
		iaddress = client.getAddress();
		port = client.getPort();	
		this.cGame = engiGame;
		this.gui = gui;
		this.client= client;
		client.sendMessage("set job Communication");
		client.sendMessage("set ship Ship.1");
		//client.sendMessage("subscribe all");
	}
	
	public void process(String stringIn)
	{
		System.out.println(stringIn);
		switch(stringIn)
		{
			
		}
	}
}