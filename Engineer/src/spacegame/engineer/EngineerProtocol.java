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
	}
	
	public void process(String stringIn)
	{
		//eGame.processTextMessage(stringIn);
		System.out.println(stringIn);
		switch(stringIn)
		{
			case "set buttonStatus false": gui.setColor("red");break;
			case "set buttonStatus true" : gui.setColor("green");break;
		}
	}
}
