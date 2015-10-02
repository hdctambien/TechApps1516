package spacegame.engineer;

import java.net.InetAddress;

import spacegame.client.*;
import spacegame.engineer.EngineerGame;

public class EngineerProtocol extends AbstractProtocol
{
	private InetAddress iaddress;
	private int port;
	private EngineerGame eGame;
	private EngineerGUI gui;
	private Client client;
	private Thread tClient;
	private Thread tP;
	
	public EngineerProtocol(Client client, EngineerGame engiGame, EngineerGUI gui)
	{
		super(client);
		tClient = new Thread(client);
		tClient.start();
		tP = new Thread(this);
		tP.start();
		iaddress = client.getAddress();
		port = client.getPort();	
		this.eGame = engiGame;
		this.gui = gui;
		this.client= client;
		client.sendMessage("set job Engineer");
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
