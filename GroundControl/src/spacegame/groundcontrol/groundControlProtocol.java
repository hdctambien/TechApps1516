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
	}
	Client getClient()
	{
		return client;
	}
	public void process(String stringIn) 
	{
		String[] splitStringIn = stringIn.split(" ");
			switch(splitStringIn[1])
			{
				case "rocketVelocity":gcGame.setRocketVelocity(splitStringIn[2]);break;
				case "rocketHeading" :gcGame.setRocketHeading(splitStringIn[2]); break;
				case "rocketPosX"    :gcGame.setRocketPosX(splitStringIn[2]); break;
				case "rocketPosY"    :gcGame.setRocketPosY(splitStringIn[2]); break;
				case "hasLink"       :gcGame.setHasLink(splitStringIn[2]); break;
				case "throttle"      :gcGame.setThrottle(splitStringIn[2]); break;
				
				default:break;
			}
	}
}
